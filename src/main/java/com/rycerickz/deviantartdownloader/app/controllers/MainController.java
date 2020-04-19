/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.app.schemes.entities.DeviantartResponseToken;
import com.rycerickz.deviantartdownloader.core.components.Json;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;

import java.util.HashMap;

import static com.rycerickz.deviantartdownloader.MainConfiguration.CUSTOMER_ID;
import static com.rycerickz.deviantartdownloader.MainConfiguration.CUSTOMER_SECRET;

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
    private TabPane tabPaneUsers;

    @FXML
    private TabPaneUsersController tabPaneUsersController;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        getTextFieldClientId().setText(CUSTOMER_ID);
        getTextFieldClientSecret().setText(CUSTOMER_SECRET);
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionScan() {
        getTabPaneUsersController().addUser();
        System.out.println("scanear a " + getTextFieldUser().getText());
        tryLogin();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void tryLogin() {
        HashMap<String, String> params = new HashMap();
        params.put("client_id", getTextFieldClientId().getText());
        params.put("client_secret", getTextFieldClientSecret().getText());
        params.put("grant_type", "client_credentials");
        DeviantartRestRequest.getInstance().getToken(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                DeviantartResponseToken deviantartResponseToken = Json.parse(DeviantartResponseToken.class, response);
                deviantartResponseToken.getAccessToken();

                tryGetGallery();
            }

            @Override
            public void error(Call call, Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    private void tryGetGallery() {
//        HashMap<String, String> params = new HashMap();
//        params.put("username", getTextFieldUser().getText());
//        params.put("offset", "0");
//        params.put("mature_content", "true");
//        getDeviantartRestRequests().getGalleryAll(params, new RestRequestCallbackInterface() {
//            @Override
//            public void success(Call call, String response) {
//                DeviantartResponseToken deviantartResponseToken = Json.parse(DeviantartResponseToken.class, response);
//                deviantartResponseToken.getAccessToken();
//            }
//
//            @Override
//            public void error(Call call, Exception exception) {
//                System.out.println(exception.getMessage());
//            }
//        });
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
