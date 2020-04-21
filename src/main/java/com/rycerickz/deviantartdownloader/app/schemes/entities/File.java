/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.entities;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class File {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = File.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

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

    @Expose
    @SerializedName("filesize")
    private Integer size;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/

