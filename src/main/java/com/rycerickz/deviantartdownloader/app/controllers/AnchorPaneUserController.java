/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.rycerickz.deviantartdownloader.app.schemes.EntityManager;
import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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
    private JFXMasonryPane jfxMasonryPaneGallery;

    @FXML
    private ScrollPane scrollPaneGallery;

    /*----------------------------------------------------------------------------------------------------------------*/

    private TreeTableColumn<Document, String> treeTableColumnName;
    private TreeTableColumn<Document, String> treeTableColumnCategory;

    /*----------------------------------------------------------------------------------------------------------------*/

    private ArrayList<Image> images;

    private ObjectProperty<User> user;

    private TreeItem treeItemRoot;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();

        setImages(new ArrayList<>());

        setUser(new SimpleObjectProperty<>());
        getUser().addListener((observableValue, oldUser, newUser) -> {
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
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
        initializeTreeTableViewDocuments();
    }

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

    private void addDocument(Document document) {
        new Thread(() -> {
            Image image = new Image(document.getPreview().getSrc());

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
