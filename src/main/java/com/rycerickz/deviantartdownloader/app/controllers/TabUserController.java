/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.properties.Document;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class TabUserController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TreeTableView<Document> treeTableViewDocuments;

    @FXML
    private TreeTableColumn<Document, String> treeTableColumnName;

    @FXML
    private TreeTableColumn<Document, String> treeTableColumnDate;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
