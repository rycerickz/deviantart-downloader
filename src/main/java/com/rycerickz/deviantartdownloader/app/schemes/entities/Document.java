/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.entities;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

import static com.rycerickz.deviantartdownloader.MainConfiguration.PATTERN_ILLEGAL_CHARACTERS;
import static com.rycerickz.deviantartdownloader.app.schemes.entities.Document.DOCUMENT_DOWNLAOD_STATUS.UNKNOWN;


/*====================================================================================================================*/

@Getter
@Setter
public class Document {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Document.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public enum DOCUMENT_DOWNLAOD_STATUS {
        UNKNOWN,
        DOWNLOADED,
        ERROR
    }

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

    private DOCUMENT_DOWNLAOD_STATUS documentDownlaodStatus;

    /*----------------------------------------------------------------------------------------------------------------*/

    public String getFilename() {
        return getFilename("");
    }

    public String getFilename(String concatenate) {
        return getTitle().replaceAll(PATTERN_ILLEGAL_CHARACTERS, "") + concatenate + getExtension();
    }

    public String getExtension() {
        return "." + FilenameUtils.getExtension(getContent().getSrc().split("\\?")[0]);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public DOCUMENT_DOWNLAOD_STATUS getDocumentDownlaodStatus() {
        if (documentDownlaodStatus == null) {
            documentDownlaodStatus = UNKNOWN;
        }
        return documentDownlaodStatus;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
