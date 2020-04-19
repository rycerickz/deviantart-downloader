/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.schemes.entities;

/*====================================================================================================================*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*====================================================================================================================*/

@Getter
@Setter
public class DeviantartResponseToken {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = DeviantartResponseToken.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("token_type")
    private String tokenType;

    @Expose
    @SerializedName("expires_in")
    private Long expiresIn;

    @Expose
    @SerializedName("status")
    private String status;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
