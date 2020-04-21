/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.rycerickz.deviantartdownloader.app.schemes.EntityManager;
import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.rycerickz.deviantartdownloader.MainConfiguration.DEFAULT_DOWNLOAD_FOLDER;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.FOLDER;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SAVE;
import static javafx.scene.control.ContentDisplay.CENTER;

/*====================================================================================================================*/

@Getter
@Setter
public class AnchorPaneUserController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private AnchorPane anchorPaneUser;

    @FXML
    private TreeTableView<Document> treeTableViewDocuments;

    @FXML
    private ScrollPane scrollPaneGallery;

    @FXML
    private JFXMasonryPane jfxMasonryPaneGallery;

    @FXML
    private TextField textFieldSaveDirectory;

    @FXML
    private Button buttonDirectoryChooser;

    @FXML
    private Button buttonSave;

    /*----------------------------------------------------------------------------------------------------------------*/

    private TreeTableColumn<Document, String> treeTableColumnName;
    private TreeTableColumn<Document, String> treeTableColumnCategory;

    /*----------------------------------------------------------------------------------------------------------------*/

    private TreeItem treeItemRoot;

    private ArrayList<Image> images;

    private ObjectProperty<User> user;

    private String defaultDirectory;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();

        setImages(new ArrayList<>());

        setUser(new SimpleObjectProperty<>());
        getUser().addListener((observableValue, oldUser, newUser) -> {
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
        });

        setDefaultDirectory(Paths.get("").toAbsolutePath().toString() + "\\" +  DEFAULT_DOWNLOAD_FOLDER );
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();

        initializeTreeTableViewDocuments();

        Label iconFolder = GlyphsDude.createIconLabel(
                FOLDER,
                "",
                "12px",
                "12px",
                CENTER);
        getButtonDirectoryChooser().setGraphic(iconFolder);

        Label iconSave = GlyphsDude.createIconLabel(
                SAVE,
                "",
                "12px",
                "12px",
                CENTER);
        getButtonSave().setGraphic(iconSave);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void initializeTreeTableViewDocuments() {
        setTreeTableColumnName(new TreeTableColumn<>("Nombre"));
        setTreeTableColumnCategory(new TreeTableColumn<>("Categor\u00EDa"));

        getTreeTableColumnName().setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        getTreeTableColumnCategory().setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));

        getTreeTableViewDocuments().getColumns().add(getTreeTableColumnName());
        getTreeTableViewDocuments().getColumns().add(getTreeTableColumnCategory());

        setTreeItemRoot(new TreeItem(EntityManager.getDocumentRoot()));
        getTreeItemRoot().setExpanded(true);

        getTreeTableViewDocuments().setRoot(getTreeItemRoot());
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void actionDirectoryChooser() {
        File file = new File(getDefaultDirectory());
        if(!file.exists()){
            file.mkdirs();
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(file);

        File selectedDirectory = directoryChooser.showDialog(null);

        getTextFieldSaveDirectory().setText(selectedDirectory.toString());
    }

    public void actionSave() {
        // TODO: bloquear la pantalla y poner un progressbar.
        new Thread(() -> {
            getUser().get().getDocuments().forEach(document -> {
                if (document.getIsDownloadable()) {
                    try {
                        // TODO: hacer que el nombre del archivo sea configurable.

                        String username = getUser().get().getUsername().get();
                        String filename = document.getFilename();

                        String fullFilename = "By " + username + " - " + filename;

                        File file = new File(getTextFieldSaveDirectory().getText() + "\\" + fullFilename);

                        URL url = new URL(document.getContent().getSrc());
                        FileUtils.copyURLToFile(url, file);

                    } catch (IOException iOException) {
                        // TODO: hacer un logs de los que no se lograron guardar.
                    }
                }
            });
        }).start();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void addDocument(Document document) {
        new Thread(() -> {
            Image image = new Image(document.getFile().getSrc());

            Platform.runLater(() -> {
                TreeItem<Document> treeItemDocument = new TreeItem<>(document);
                getTreeItemRoot().getChildren().add(treeItemDocument);

                getImages().add(image);
                document.setImage(image);

                double ratio = image.getWidth() / image.getHeight();

                double width = Math.min(Math.min(image.getWidth(), 300), image.getWidth() / ratio);
                double height = Math.min(Math.min(image.getHeight(), 300), image.getHeight() / ratio);

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
                imageView.setPreserveRatio(true);

                BorderPane borderPane = new BorderPane();
                borderPane.setStyle("-fx-background-color: #dddddd");
                borderPane.setPrefSize(width, height);
                borderPane.setCenter(imageView);

                getJfxMasonryPaneGallery().getChildren().add(borderPane);

                getJfxMasonryPaneGallery().setHSpacing(10);
                getJfxMasonryPaneGallery().setVSpacing(10);

                getScrollPaneGallery().requestLayout();
                JFXScrollPane.smoothScrolling(getScrollPaneGallery());
            });
        }).start();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
}

/*====================================================================================================================*/
