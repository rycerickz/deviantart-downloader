/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
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

        Scene scene = new Scene(getBorderPaneMain(),  900, 700);
        getPrimaryStage().setScene(scene);

        getPrimaryStage().show();
    }

    private void initializeInterfaceLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/application_interface.fxml"));
        AnchorPane anchorPaneIntercace = fxmlLoader.load();

        getBorderPaneMain().setCenter(anchorPaneIntercace);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        launch(args);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
