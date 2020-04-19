/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.properties;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class Author {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Author.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("userid")
    private StringProperty idUser;

    @Expose
    @SerializedName("username")
    private StringProperty username;

    @Expose
    @SerializedName("usericon")
    private StringProperty icon;

    @Expose
    @SerializedName("type")
    private StringProperty type;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
