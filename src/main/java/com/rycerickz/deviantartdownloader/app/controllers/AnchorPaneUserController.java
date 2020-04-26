/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.rycerickz.deviantartdownloader.app.cells.TreeTableCellDocumentDownloadStatus;
import com.rycerickz.deviantartdownloader.app.components.Generate;
import com.rycerickz.deviantartdownloader.app.components.Icons;
import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.app.schemes.EntityManager;
import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import com.rycerickz.deviantartdownloader.app.schemes.entities.ResponseProfile;
import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import com.rycerickz.deviantartdownloader.core.components.Is;
import com.rycerickz.deviantartdownloader.core.components.Json;
import com.rycerickz.deviantartdownloader.core.components.Logs;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import static com.rycerickz.deviantartdownloader.MainConfiguration.DEFAULT_DOWNLOAD_FOLDER;
import static com.rycerickz.deviantartdownloader.app.schemes.entities.Document.DOCUMENT_DOWNLAOD_STATUS.DOWNLOADED;
import static com.rycerickz.deviantartdownloader.app.schemes.entities.Document.DOCUMENT_DOWNLAOD_STATUS.ERROR;
import static com.rycerickz.deviantartdownloader.app.schemes.entities.Document.DOCUMENT_DOWNLAOD_STATUS;

/*====================================================================================================================*/

@Getter
@Setter
public class AnchorPaneUserController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private AnchorPane anchorPaneUser;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TreeTableView<Document> treeTableViewDocuments;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private ScrollPane scrollPaneGallery;

    @FXML
    private JFXMasonryPane jfxMasonryPaneGallery;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private ImageView imageViewAvatar;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelDeviations;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TextField textFieldSaveDirectory;

    @FXML
    private Button buttonDirectoryChooser;

    @FXML
    private Button buttonSave;

    /*----------------------------------------------------------------------------------------------------------------*/

    private TreeTableColumn<Document, String> treeTableColumnId;
    private TreeTableColumn<Document, String> treeTableColumnName;
    private TreeTableColumn<Document, String> treeTableColumnCategory;
    private TreeTableColumn<Document, DOCUMENT_DOWNLAOD_STATUS> treeTableColumnDownloadStatus;

    /*----------------------------------------------------------------------------------------------------------------*/

    private TreeItem treeItemRoot;

    private ObjectProperty<User> user;

    private String defaultDirectory;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();

        setUser(new SimpleObjectProperty<>());
        getUser().addListener((observableValue, oldUser, newUser) -> {
            getLabelUsername().setText(newUser.getUsername().get());
            getTextFieldSaveDirectory().setText(getDefaultDirectory() + newUser.getUsername().get());

            newUser.getDocuments().addListener((ListChangeListener<Document>) change -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Document document : change.getAddedSubList()) {
                            addDocument(document);
                        }

                    } else if (change.wasRemoved()) {
                        for (Document document : change.getRemoved()) {
                            // TODO: remover documento del arbol y de la vista en miniatura.
                        }
                    }
                }
            });

            tryGetProfile();
        });

        setDefaultDirectory(Paths.get("").toAbsolutePath().toString() + "\\" + DEFAULT_DOWNLOAD_FOLDER);
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();

        initializeTreeTableViewDocuments();

        getButtonDirectoryChooser().setGraphic(Icons.getFaFolder());
        getButtonSave().setGraphic(Icons.getFaSave());
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void initializeTreeTableViewDocuments() {
        setTreeTableColumnId(new TreeTableColumn<>("ID"));
        setTreeTableColumnName(new TreeTableColumn<>("Nombre"));
        setTreeTableColumnCategory(new TreeTableColumn<>("Categor\u00EDa"));
        setTreeTableColumnDownloadStatus(new TreeTableColumn<>("Estado de descarga"));

        getTreeTableColumnId().setCellValueFactory(new TreeItemPropertyValueFactory<>("idDeviation"));
        getTreeTableColumnName().setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        getTreeTableColumnCategory().setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        getTreeTableColumnDownloadStatus().setCellValueFactory(new TreeItemPropertyValueFactory<>("documentDownlaodStatus"));

        getTreeTableColumnDownloadStatus().setCellFactory(StdocumentringTreeTableColumn -> {
            TreeTableCellDocumentDownloadStatus treeTableCellDocumentDownloadStatus = new TreeTableCellDocumentDownloadStatus();
            treeTableCellDocumentDownloadStatus.setStyle("-fx-alignment: center");
            return treeTableCellDocumentDownloadStatus;
        });

        getTreeTableViewDocuments().getColumns().add(getTreeTableColumnId());
        getTreeTableViewDocuments().getColumns().add(getTreeTableColumnName());
        getTreeTableViewDocuments().getColumns().add(getTreeTableColumnCategory());
        getTreeTableViewDocuments().getColumns().add(getTreeTableColumnDownloadStatus());

        setTreeItemRoot(new TreeItem(EntityManager.getDocumentRoot()));
        getTreeItemRoot().setExpanded(true);

        getTreeTableViewDocuments().setRoot(getTreeItemRoot());
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void tryGetProfile() {
        // TODO: agregar un loading.

        HashMap<String, String> params = new HashMap();
        params.put("ext_collections", "no");
        params.put("ext_galleries", "no");
        params.put("mature_content", "true");

        DeviantartRestRequest.getInstance().getProfile(
                getUser().get().getUsername().get(),
                params,
                new RestRequestCallbackInterface() {
                    @Override
                    public void success(Call call, String response) {
                        ResponseProfile responseProfile = Json.parse(ResponseProfile.class, response);

                        Image image = new Image(responseProfile.getUser().getIcon());
                        String deviations = String.format("%d Deviations.", responseProfile.getStats().getDeviations());

                        Platform.runLater(() -> {
                            getImageViewAvatar().setImage(image);
                            getLabelDeviations().setText(deviations);
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

    public void actionDirectoryChooser() {
        File file = new File(getDefaultDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(file);

        File selectedDirectory = directoryChooser.showDialog(null);

        getTextFieldSaveDirectory().setText(selectedDirectory.toString());
    }

    public void actionSave() {
        // TODO: bloquear la pantalla y poner un progressbar.
        getUser().get().getDocuments().forEach(document -> {
            new Thread(() -> {

                if (document.getIsDownloadable()) {
                    try {
                        // TODO: hacer que el nombre del archivo sea configurable.

                        String username = getUser().get().getUsername().get();
                        String filename = document.getFilename();

                        String fullFilename = "By " + username + " - " + filename;

                        File file = new File(getTextFieldSaveDirectory().getText() + "\\" + fullFilename);

                        URL url = new URL(document.getContent().getSrc());
                        FileUtils.copyURLToFile(url, file);

                        document.setDocumentDownlaodStatus(DOWNLOADED);

                    } catch (IOException iOException) {
                        document.setDocumentDownlaodStatus(ERROR);
                        // TODO: hacer un logs de los que no se lograron guardar.
                    }

                } else {
                    document.setDocumentDownlaodStatus(ERROR);
                }

                Platform.runLater(() -> getTreeTableViewDocuments().refresh());

            }).start();
        });
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void addDocument(Document document) {
        new Thread(() -> {

            // TODO: esto esta momentaneo.
            if (document == null || document.getContent() == null || !Is.validString(document.getContent().getSrc())) {
                System.err.println("DOCUMENT => " + document.getIdDeviation());

            } else {
                System.out.println("DOCUMENT => " + document.getTitle() + " => " + document.getContent().getSrc());
            }

            Image image = new Image(document.getFile().getSrc());

            Platform.runLater(() -> {
                TreeItem<Document> treeItemDocument = new TreeItem<>(document);
                getTreeItemRoot().getChildren().add(treeItemDocument);

                getJfxMasonryPaneGallery().getChildren()
                        .add(Generate.generateBorderPaneGallery(document, image));

                getScrollPaneGallery().requestLayout();
                JFXScrollPane.smoothScrolling(getScrollPaneGallery());
            });
        }).start();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
}

/*====================================================================================================================*/
