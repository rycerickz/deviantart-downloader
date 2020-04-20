/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.app.controllers.TabPaneTermsController;
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
import javafx.scene.control.ToggleButton;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;

import java.util.HashMap;

import static com.jfoenix.controls.JFXSpinner.INDETERMINATE_PROGRESS;
import static com.rycerickz.deviantartdownloader.MainConfiguration.*;
import static com.rycerickz.deviantartdownloader.MainController.SEARCH_TYPE.*;

/*====================================================================================================================*/

@Getter
@Setter
public class MainController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    protected enum SEARCH_TYPE {
        GALLERY_ALL,
        BROWSE_NEWEST,
        BROWSE_POPULAR,
        BROWSE_RECOMMENDED
    }

    private SEARCH_TYPE searchType;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldClientId;

    @FXML
    private TextField textFieldClientSecret;

    @FXML
    private TextField textFieldOffsetMaximum;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private ToggleButton toggleButtonGallery;

    @FXML
    private ToggleButton toggleButtonBrowseNewest;

    @FXML
    private ToggleButton toggleButtonBrowsePopular;

    @FXML
    private ToggleButton toggleButtonBrowseRecommended;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldTerm;

    @FXML
    private Button buttonSearch;

    @FXML
    private ProgressBar progressBarLoadingByTerm;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TabPaneTermsController tabPaneTermsController;

    /*----------------------------------------------------------------------------------------------------------------*/

    private int offset;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();

        getTextFieldClientId().setText(DEFAULT_CUSTOMER_ID);
        getTextFieldClientSecret().setText(DEFAULT_CUSTOMER_SECRET);
        getTextFieldOffsetMaximum().setText(String.valueOf(DEFAULT_OFFSET_MAXIMUM));
        getTextFieldTerm().setText(DEFAULT_TERM);

        getToggleButtonGallery().setSelected(true);
        setSearchType(GALLERY_ALL);

        setOffset(0);
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();

        getProgressBarLoadingByTerm().setProgress(INDETERMINATE_PROGRESS);
        getProgressBarLoadingByTerm().setVisible(false);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private int getOffsetMaximum() {
        try {
            return Integer.parseInt(getTextFieldOffsetMaximum().getText());

        } catch (Exception exception) {
            return DEFAULT_OFFSET_MAXIMUM;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionToggleButtonGalleryAll() {
        setSearchType(GALLERY_ALL);
    }

    public void actionToggleButtonBrowseNewest() {
        setSearchType(BROWSE_NEWEST);
    }

    public void actionToggleButtonBrowsePopular() {
        setSearchType(BROWSE_POPULAR);
    }

    public void actionToggleButtonBrowseRecommended() {
        setSearchType(BROWSE_RECOMMENDED);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionMenuClose() {
        Platform.exit();
        System.exit(0);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionSearch() {
        // TODO: agregar form validador (campos requeridos para la solicitud).
        switch (getSearchType()) {
            case GALLERY_ALL:
                getTabPaneTermsController().addUser(getTextFieldTerm().getText());
                break;
            case BROWSE_NEWEST:
            case BROWSE_POPULAR:
            case BROWSE_RECOMMENDED:
                // TODO: de momento agrega un usuario, pero debe ser un term.
                getTabPaneTermsController().addUser(getTextFieldTerm().getText());
                break;
        }
        setSearchType(getSearchType());
        tryLogin();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void tryLogin() {
        // TODO: validar la expiracion del token, en realidad no debe loguearse a cada vez.
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
                            case BROWSE_POPULAR:
                                tryGetBrowsePopular();
                                break;
                            case BROWSE_RECOMMENDED:
                                tryGetBrowseRecommended();
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
        getProgressBarLoadingByTerm().setVisible(true);

        // TODO: agregar mature content al formulario (checkbox).
        HashMap<String, String> params = new HashMap();
        params.put("username", getTextFieldTerm().getText());
        params.put("offset", String.valueOf(getOffset()));
        params.put("limit", String.valueOf(10));
        params.put("mature_content", "true");
        DeviantartRestRequest.getInstance().getGalleryAll(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseDocuments responseDocuments = Json.parse(ResponseDocuments.class, response);

                Platform.runLater(() -> {
                    getTabPaneTermsController()
                            .getTermSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseDocuments.getDocuments());
                });

                if (responseDocuments.getHasMore() && responseDocuments.getNextOffset() <= getOffsetMaximum()) {
                    setOffset(responseDocuments.getNextOffset());
                    tryGetGalleryAll();

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
                    getTabPaneTermsController()
                            .getTermSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseDocuments.getDocuments());
                });

                if (responseDocuments.getHasMore() && responseDocuments.getNextOffset() <= getOffsetMaximum()) {
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

    private void tryGetBrowsePopular() {
        getProgressBarLoadingByTerm().setVisible(true);

        // TODO: agregar mature content al formulario (checkbox).
        HashMap<String, String> params = new HashMap();
        params.put("category_path ", "");
        params.put("q", getTextFieldTerm().getText());
        params.put("timerange", "alltime");
        params.put("offset", String.valueOf(getOffset()));
        params.put("limit", String.valueOf(10));
        params.put("mature_content", "true");
        DeviantartRestRequest.getInstance().getBrowsePopular(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseDocuments responseDocuments = Json.parse(ResponseDocuments.class, response);

                Platform.runLater(() -> {
                    getTabPaneTermsController()
                            .getTermSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseDocuments.getDocuments());
                });

                if (responseDocuments.getHasMore() && responseDocuments.getNextOffset() <= getOffsetMaximum()) {
                    setOffset(responseDocuments.getNextOffset());
                    tryGetBrowsePopular();

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

    private void tryGetBrowseRecommended() {
        // TODO: no hace falta el term.
        getProgressBarLoadingByTerm().setVisible(true);

        // TODO: agregar mature content al formulario (checkbox).
        HashMap<String, String> params = new HashMap();
        params.put("offset", String.valueOf(getOffset()));
        params.put("limit", String.valueOf(10));
        params.put("mature_content", "true");
        DeviantartRestRequest.getInstance().getBrowseRecommended(params, new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                ResponseDocuments responseDocuments = Json.parse(ResponseDocuments.class, response);

                Platform.runLater(() -> {
                    getTabPaneTermsController()
                            .getTermSelected()
                            .get()
                            .getDocuments()
                            .addAll(responseDocuments.getDocuments());
                });

                if (responseDocuments.getHasMore() && responseDocuments.getNextOffset() <= getOffsetMaximum()) {
                    setOffset(responseDocuments.getNextOffset());
                    tryGetBrowseRecommended();

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
