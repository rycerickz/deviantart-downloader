/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.properties;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class Preview {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Preview.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("src")
    private StringProperty src;

    @Expose
    @SerializedName("width")
    private IntegerProperty width;

    @Expose
    @SerializedName("height")
    private IntegerProperty height;

    @Expose
    @SerializedName("transparency")
    private BooleanProperty transparency;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/

