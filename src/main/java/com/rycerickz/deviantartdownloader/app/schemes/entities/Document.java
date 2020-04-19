/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.entities;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;


/*====================================================================================================================*/

@Getter
@Setter
public class Document {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Document.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("deviationid")
    private String idDeviation;

    @Expose
    @SerializedName("printid")
    private String idPrint;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("category_path")
    private String categoryPath;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("is_mature")
    private String isMature;

    @Expose
    @SerializedName("is_downloadable")
    private String isDownloadable;

    @Expose
    @SerializedName("is_favourited")
    private String isFavourited;

    @Expose
    @SerializedName("is_deleted")
    private String isDeleted;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("author")
    private Author author;

    @Expose
    @SerializedName("preview")
    private Preview preview;

    /*----------------------------------------------------------------------------------------------------------------*/

    private Image image;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
