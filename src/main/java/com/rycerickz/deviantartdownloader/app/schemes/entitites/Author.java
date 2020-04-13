/*------------------------------------------------------------------------------------------------*/

package com.rycerickz.deviantartdownloader.app.schemes.entitites;

/*------------------------------------------------------------------------------------------------*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/*------------------------------------------------------------------------------------------------*/

@Data
public class Author {

    /*--------------------------------------------------------------------------------------------*/

    private static final String TAG = Author.class.getSimpleName();

    /*--------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("userid")
    private String idUser;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("usericon")
    private String icon;

    @Expose
    @SerializedName("type")
    private String type;

    /*--------------------------------------------------------------------------------------------*/

}

/*------------------------------------------------------------------------------------------------*/

