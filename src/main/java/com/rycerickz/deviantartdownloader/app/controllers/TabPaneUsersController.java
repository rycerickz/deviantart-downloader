/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class TabPaneUsersController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TabPane tabPaneUsers;

    /*----------------------------------------------------------------------------------------------------------------*/

    private ObservableList<User> users;
    private ObjectProperty<User> userSelected;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        getTabPaneUsers().getTabs().clear();
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
        setUsers(FXCollections.observableArrayList());
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void addUser(){
        getTabPaneUsers().getTabs().add(new Tab("Usuario 1"));
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
