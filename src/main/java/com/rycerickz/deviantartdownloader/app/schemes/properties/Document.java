/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.properties;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
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
    private StringProperty idDeviation;

    @Expose
    @SerializedName("printid")
    private StringProperty idPrint;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("title")
    private StringProperty title;

    @Expose
    @SerializedName("url")
    private StringProperty url;

    @Expose
    @SerializedName("category")
    private StringProperty category;

    @Expose
    @SerializedName("category_path")
    private StringProperty categoryPath;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("is_mature")
    private StringProperty isMature;

    @Expose
    @SerializedName("is_downloadable")
    private StringProperty isDownloadable;

    @Expose
    @SerializedName("is_favourited")
    private StringProperty isFavourited;

    @Expose
    @SerializedName("is_deleted")
    private StringProperty isDeleted;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("author")
    private ObjectProperty<Author> author;

    @Expose
    @SerializedName("preview")
    private ObjectProperty<Preview> preview;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
