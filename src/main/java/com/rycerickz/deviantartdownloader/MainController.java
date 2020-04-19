/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.app.controllers.TabPaneUsersController;
import com.rycerickz.deviantartdownloader.app.schemes.entities.ResponseDocuments;
import com.rycerickz.deviantartdownloader.app.schemes.entities.ResponseToken;
import com.rycerickz.deviantartdownloader.core.components.Is;
import com.rycerickz.deviantartdownloader.core.components.Json;
import com.rycerickz.deviantartdownloader.core.components.Logs;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;

import java.util.HashMap;

import static com.jfoenix.controls.JFXSpinner.INDETERMINATE_PROGRESS;
import static com.rycerickz.deviantartdownloader.MainConfiguration.*;
import static com.rycerickz.deviantartdownloader.MainController.SEARCH_TYPE.BROWSE_NEWEST;
import static com.rycerickz.deviantartdownloader.MainController.SEARCH_TYPE.GALLERY_ALL;

/*====================================================================================================================*/

@Getter
@Setter
public class MainController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    protected enum SEARCH_TYPE {
        GALLERY_ALL,
        BROWSE_NEWEST
    }

    private SEARCH_TYPE searchType;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldClientId;

    @FXML
    private TextField textFieldClientSecret;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldUser;

    @FXML
    private Button buttonScan;

    @FXML
    private ProgressBar progressBarLoadingByUser;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldTerm;

    @FXML
    private Button buttonSearch;

    @FXML
    private ProgressBar progressBarLoadingByTerm;

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

        getProgressBarLoadingByUser().setProgress(INDETERMINATE_PROGRESS);
        getProgressBarLoadingByUser().setVisible(false);

        getProgressBarLoadingByTerm().setProgress(INDETERMINATE_PROGRESS);
        getProgressBarLoadingByTerm().setVisible(false);
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
        setSearchType(GALLERY_ALL);
        tryLogin();
    }

    public void actionSearch() {
        // TODO: agregar form validador (campos requeridos para la solicitud).
        // TODO: por ahora funciona como un usuario, pero se debe cambiar el controlador y todo a un tipo de busqueda por termino.
        getTabPaneUsersController().addUser(getTextFieldTerm().getText());
        setSearchType(BROWSE_NEWEST);
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
                        switch (getSearchType()) {
                            case GALLERY_ALL:
                                tryGetGalleryAll();
                                break;
                            case BROWSE_NEWEST:
                                tryGetBrowseNewest();
                                break;
                        }
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

    /*----------------------------------------------------------------------------------------------------------------*/

    private void tryGetGalleryAll() {
        getProgressBarLoadingByUser().setVisible(true);

        // TODO: agregar mature content al formulario (checkbox).
        HashMap<String, String> params = new HashMap();
        params.put("username", getTextFieldUser().getText());
        params.put("offset", String.valueOf(getOffset()));
        params.put("limit", String.valueOf(10));
        params.put("mature_content", "true");
        DeviantartRestRequest.getInstance().getGalleryAll(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseDocuments responseDocuments = Json.parse(ResponseDocuments.class, response);

                Platform.runLater(() -> {
                    getTabPaneUsersController()
                            .getUserSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseDocuments.getDocuments());
                });

                if (responseDocuments.getHasMore()) {
                    setOffset(responseDocuments.getNextOffset());
                    tryGetGalleryAll();

                } else {
                    getProgressBarLoadingByUser().setVisible(false);
                    setOffset(0);
                }
            }

            @Override
            public void error(Call call, String response) {
                getProgressBarLoadingByUser().setVisible(false);
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

    private void tryGetBrowseNewest() {
        getProgressBarLoadingByTerm().setVisible(true);

        // TODO: agregar mature content al formulario (checkbox).
        HashMap<String, String> params = new HashMap();
        params.put("category_path ", "");
        params.put("q", getTextFieldTerm().getText());
        params.put("offset", String.valueOf(getOffset()));
        params.put("limit", String.valueOf(10));
        params.put("mature_content", "true");
        DeviantartRestRequest.getInstance().getBrowseNewest(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseDocuments responseDocuments = Json.parse(ResponseDocuments.class, response);

                Platform.runLater(() -> {
                    getTabPaneUsersController()
                            .getUserSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseDocuments.getDocuments());
                });

                if (responseDocuments.getHasMore()) {
                    setOffset(responseDocuments.getNextOffset());
                    tryGetBrowseNewest();

                } else {
                    getProgressBarLoadingByTerm().setVisible(false);
                    setOffset(0);
                }
            }

            @Override
            public void error(Call call, String response) {
                getProgressBarLoadingByTerm().setVisible(false);
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
