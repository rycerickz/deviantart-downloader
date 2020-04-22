/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.components.apis.DeviantartRestRequest;
import com.rycerickz.deviantartdownloader.core.components.Logs;
import com.rycerickz.deviantartdownloader.core.templates.TemplateApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/*====================================================================================================================*/

@Getter
@Setter
public class MainApplication extends TemplateApplication {

    /*----------------------------------------------------------------------------------------------------------------*/

    private BorderPane borderPaneMain;

    /*----------------------------------------------------------------------------------------------------------------*/

    private DeviantartRestRequest deviantartRestRequests;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        setDeviantartRestRequests(new DeviantartRestRequest());
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();

        getMainStage().setTitle("DeviantArt Downloader");
        getMainStage().getIcons().add(new Image(getClass().getResourceAsStream(("/icons/logotype-deviantart.png"))));

        try {
            initializeMainLayout();
        } catch (Exception exception) {
            Logs.exception(exception);
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void initializeMainLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/application_main.fxml"));
        setBorderPaneMain(fxmlLoader.load());

        getBorderPaneMain()
                .getStylesheets();
                //.add(getClass().getResource("/styles/application-main.css").toExternalForm());

        Scene scene = new Scene(getBorderPaneMain());
        getMainStage().setScene(scene);

        getMainStage().show();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        launch(args);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
