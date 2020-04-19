/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.EntityManager;
import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import com.rycerickz.deviantartdownloader.core.components.Logs;
import com.rycerickz.deviantartdownloader.core.templates.TemplateController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static javafx.scene.control.TabPane.TabClosingPolicy.ALL_TABS;

/*====================================================================================================================*/

@Getter
@Setter
public class TabPaneUsersController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TabPane tabPaneUsers;

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private AnchorPaneUserController anchorPaneUserController;

    /*----------------------------------------------------------------------------------------------------------------*/

    private ObservableList<User> users;
    private ObjectProperty<User> userSelected;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();

        getTabPaneUsers().setTabClosingPolicy(ALL_TABS);
        getTabPaneUsers().getTabs().clear();

        setUsers(FXCollections.observableArrayList());
        setUserSelected(new SimpleObjectProperty<>());

        getUsers().addListener((ListChangeListener<User>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (User user : change.getAddedSubList()) {
                        addUserTab(user);
                    }

                } else if (change.wasRemoved()) {
                    for (User user : change.getRemoved()) {
                        // TODO: remover tabla.
                    }
                }
            }
        });
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void addUser(String username) {
        User user = EntityManager.getUser(username);
        getUsers().add(user);
        getUserSelected().set(user);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void addUserTab(User user) {
        Tab tab = new Tab(user.getUsername().get());

        try {
            initializeAnchorPaneUserLayout(tab, user);
        } catch (Exception exception) {
            Logs.exception(exception);
        }

        getTabPaneUsers().getTabs().add(tab);
    }

    private void initializeAnchorPaneUserLayout(Tab tab, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/application_anchor_pane_user.fxml"));

        tab.setContent(fxmlLoader.load());

        AnchorPaneUserController anchorPaneUserController = fxmlLoader.getController();
        anchorPaneUserController.getUser().set(user);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
