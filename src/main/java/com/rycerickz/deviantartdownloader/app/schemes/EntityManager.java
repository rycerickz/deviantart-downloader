/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import com.rycerickz.deviantartdownloader.app.schemes.properties.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/*====================================================================================================================*/

public class EntityManager {

    /*----------------------------------------------------------------------------------------------------------------*/

    public static User getUser(String username) {
        User user = new User();
        user.setUsername(new SimpleStringProperty(username));
        user.setDocuments(FXCollections.observableArrayList());
        return user;
    }

    public static Document getDocumentRoot() {
        Document document = new Document();
        document.setIdDeviation("Todos");
        return document;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
