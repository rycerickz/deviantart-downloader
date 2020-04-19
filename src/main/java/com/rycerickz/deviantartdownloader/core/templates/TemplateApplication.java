/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.templates;

/*====================================================================================================================*/

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class TemplateApplication extends Application {

    /*----------------------------------------------------------------------------------------------------------------*/

    private Stage mainStage;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void start(Stage primaryStage) {
        setMainStage(primaryStage);

        initializeVariables();
        initializeViews();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    protected void initializeVariables() {

    }

    protected void initializeViews() {
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
