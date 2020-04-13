/*------------------------------------------------------------------------------------------------*/

package com.rycerickz.deviantartdownloader.app.schemes.entitites;

/*------------------------------------------------------------------------------------------------*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/*------------------------------------------------------------------------------------------------*/

@Data
public class Preview {

    /*--------------------------------------------------------------------------------------------*/

    private static final String TAG = Preview.class.getSimpleName();

    /*--------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("src")
    private String src;

    @Expose
    @SerializedName("width")
    private Integer width;

    @Expose
    @SerializedName("height")
    private Integer height;

    @Expose
    @SerializedName("transparency")
    private Boolean transparency;

    /*--------------------------------------------------------------------------------------------*/

}

/*------------------------------------------------------------------------------------------------*/

