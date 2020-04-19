/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.app.controllers.TabPaneUsersController;
import com.rycerickz.deviantartdownloader.app.schemes.entities.ResponseGallery;
import com.rycerickz.deviantartdownloader.app.schemes.entities.ResponseToken;
import com.rycerickz.deviantartdownloader.core.components.Is;
import com.rycerickz.deviantartdownloader.core.components.Json;
import com.rycerickz.deviantartdownloader.core.components.Logs;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.Response;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rycerickz.deviantartdownloader.MainConfiguration.*;

/*====================================================================================================================*/

@Getter
@Setter
public class MainController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldUser;

    @FXML
    private TextField textFieldClientId;

    @FXML
    private TextField textFieldClientSecret;

    @FXML
    private Button buttonScan;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TabPaneUsersController tabPaneUsersController;

    /*----------------------------------------------------------------------------------------------------------------*/

    private int offset;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        getTextFieldClientId().setText(CUSTOMER_ID);
        getTextFieldClientSecret().setText(CUSTOMER_SECRET);
        getTextFieldUser().setText(DEFAULT_USER);
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionMenuClose() {
        Platform.exit();
        System.exit(0);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionScan() {
        // TODO: agregar form validador (campos requeridos para la solicitud).
        getTabPaneUsersController().addUser(getTextFieldUser().getText());
        tryLogin();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void tryLogin() {
        // TODO: validar la expiracion del token.
        HashMap<String, String> params = new HashMap();
        params.put("client_id", getTextFieldClientId().getText());
        params.put("client_secret", getTextFieldClientSecret().getText());
        params.put("grant_type", "client_credentials");
        DeviantartRestRequest.getInstance().getToken(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseToken responseToken = Json.parse(ResponseToken.class, response);
                DeviantartRestRequest.setResponseToken(responseToken);

                Platform.runLater(() -> {
                    if (Is.validString(responseToken.getAccessToken())) {
                        tryGetGallery();
                    }
                });
            }

            @Override
            public void error(Call call, String response) {
                Logs.error(response);
                // TODO: manejar respuesta.
            }

            @Override
            public void response(Call call, byte[] bytes) {
                // TODO: manejar respuesta.
            }
        });
    }

    private void tryGetGallery() {
        // TODO: agregar mature content al formulario (checkbox).
        HashMap<String, String> params = new HashMap();
        params.put("username", getTextFieldUser().getText());
        params.put("offset", String.valueOf(getOffset()));
        params.put("limit", String.valueOf(10));
        params.put("mature_content", "true");
        DeviantartRestRequest.getInstance().getGalleryAll(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseGallery responseGallery = Json.parse(ResponseGallery.class, response);

                Platform.runLater(() -> {
                    getTabPaneUsersController()
                            .getUserSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseGallery.getDocuments());
                });

                if(responseGallery.getHasMore()){
                    setOffset(responseGallery.getNextOffset());
                    tryGetGallery();

                }else{
                    setOffset(0);
                }
            }

            @Override
            public void error(Call call, String response) {
                Logs.error(response);
                // TODO: manejar respuesta.
            }

            @Override
            public void response(Call call, byte[] bytes) {
                // TODO: manejar respuesta.
            }
        });
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
