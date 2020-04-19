/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.templates;

/*====================================================================================================================*/

import javafx.fxml.Initializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

/*====================================================================================================================*/

@Getter
@Setter
public class TemplateController implements Initializable {

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
