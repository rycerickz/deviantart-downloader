/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.properties;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class Term {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Term.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    private StringProperty username;

    private ObservableList<Document> documents;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/

