/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.controllers;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.EntityManager;
import com.rycerickz.deviantartdownloader.app.schemes.properties.Term;
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
public class TabPaneTermsController extends TemplateController {

    /*----------------------------------------------------------------------------------------------------------------*/

    @FXML
    private TabPane tabPaneTerms;

    /*----------------------------------------------------------------------------------------------------------------*/

    private ObservableList<Term> terms;
    private ObjectProperty<Term> termSelected;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void initializeVariables() {
        super.initializeVariables();

        getTabPaneTerms().setTabClosingPolicy(ALL_TABS);
        getTabPaneTerms().getTabs().clear();

        setTerms(FXCollections.observableArrayList());
        setTermSelected(new SimpleObjectProperty<>());

        // TODO: cuando se elimine un tabs, se debe parar el proceso de descarga.
        getTerms().addListener((ListChangeListener<Term>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Term term : change.getAddedSubList()) {
                       if(term instanceof  User){
                           addUserTab((User) term);

                       }else{
                           // TODO: por ahora funciona como usuario.
                           addUserTab((User) term);
                       }
                    }

                } else if (change.wasRemoved()) {
                    for (Term term : change.getRemoved()) {
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
        getTerms().add(user);
        getTermSelected().set(user);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void addUserTab(User user) {
        Tab tab = new Tab(user.getUsername().get());

        try {
            initializeAnchorPaneUserLayout(tab, user);
        } catch (Exception exception) {
            Logs.exception(exception);
        }

        getTabPaneTerms().getTabs().add(tab);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

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
