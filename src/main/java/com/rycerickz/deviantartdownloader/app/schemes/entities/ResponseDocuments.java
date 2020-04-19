/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.entities;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/*====================================================================================================================*/

@Getter
@Setter
public class ResponseDocuments {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = ResponseDocuments.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("has_more")
    private Boolean hasMore;

    @Expose
    @SerializedName("next_offset")
    private Integer nextOffset;

    @Expose
    @SerializedName("estimated_total")
    private Integer estimatedTotal;

    @Expose
    @SerializedName("results")
    private ArrayList<Document> documents;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
