/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.entities;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

import static com.rycerickz.deviantartdownloader.MainConfiguration.PATTERN_ILLEGAL_CHARACTERS;


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
    private Boolean isMature;

    @Expose
    @SerializedName("is_downloadable")
    private Boolean isDownloadable;

    @Expose
    @SerializedName("is_favourited")
    private Boolean isFavourited;

    @Expose
    @SerializedName("is_deleted")
    private Boolean isDeleted;

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("author")
    private Author author;

    @Expose
    @SerializedName("preview")
    private File file;

    @Expose
    @SerializedName("content")
    private File content;

    /*----------------------------------------------------------------------------------------------------------------*/

    public String getFilename() {
        return getTitle().replaceAll(PATTERN_ILLEGAL_CHARACTERS, "") + getExtension();
    }

    public String getExtension() {
        return "." + FilenameUtils.getExtension(getContent().getSrc().split("\\?")[0]);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
