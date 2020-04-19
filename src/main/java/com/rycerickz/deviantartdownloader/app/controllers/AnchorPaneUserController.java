/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.google.gson.internal.$Gson$Preconditions;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.app.schemes.EntityManager;
import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import com.rycerickz.deviantartdownloader.core.components.Is;
import com.rycerickz.deviantartdownloader.core.components.Logs;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        DeviantartRestRequest.getInstance().getImage(document.getPreview().getSrc(), new RestRequestCallbackInterface() {
            @Override
            public void success(Call call, String response) {
                // TODO: manejar respuesta.
            }

            @Override
            public void error(Call call, String response) {
                // TODO: manejar respuesta.
            }

            @Override
            public void response(Call call, byte[] bytes) {
                Platform.runLater(() -> {
                    TreeItem<Document> treeItemDocument = new TreeItem<>(document);
                    getTreeItemRoot().getChildren().add(treeItemDocument);

                    Image image = new Image(new ByteArrayInputStream(bytes));
                    getImages().add(image);

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(Math.min(400, image.getWidth()));
                    imageView.setPreserveRatio(true);

                    Pane pane = new Pane();
                    pane.getChildren().add(imageView);

                    getJfxMasonryPaneGallery().getChildren().add(pane);

                    getJfxMasonryPaneGallery().setHSpacing(10);
                    getJfxMasonryPaneGallery().setVSpacing(10);

                    getScrollPaneGallery().requestLayout();
                    JFXScrollPane.smoothScrolling(getScrollPaneGallery());
                });
            }
        });
    }

    /*----------------------------------------------------------------------------------------------------------------*/
}

/*====================================================================================================================*/
