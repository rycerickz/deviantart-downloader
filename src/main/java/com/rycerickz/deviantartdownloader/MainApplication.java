/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/*====================================================================================================================*/

public class MainApplication extends Application {

    /*----------------------------------------------------------------------------------------------------------------*/

    @Getter
    @Setter
    private Stage primaryStage;

    @Getter
    @Setter
    private BorderPane borderPaneMain;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);

        getPrimaryStage().setTitle("DeviantArt Downloader");
        getPrimaryStage().getIcons().add(new Image(getClass().getResourceAsStream(("/icons/logotype-deviantart.png"))));

        try {
            initializeMainLayout();
            initializeInterfaceLayout();

        } catch (IOException iOException) {

        } catch (Exception exception) {

        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void initializeMainLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/application_main.fxml"));
        setBorderPaneMain(fxmlLoader.load());

        Scene scene = new Scene(getBorderPaneMain(), 900, 700);

        getPrimaryStage().setScene(scene);

        getPrimaryStage().show();
    }

    private void initializeInterfaceLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/application_interface.fxml"));
        VBox vBoxIntercace = fxmlLoader.load();

        getBorderPaneMain().setCenter(vBoxIntercace);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        launch(args);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
