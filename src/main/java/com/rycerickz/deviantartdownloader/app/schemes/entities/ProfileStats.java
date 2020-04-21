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
public class ProfileStats {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = ProfileStats.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    @Expose
    @SerializedName("user_deviations")
    private Integer deviations;

    @Expose
    @SerializedName("user_favourites")
    private Integer favourites;

    @Expose
    @SerializedName("user_comments")
    private Integer comments;

    @Expose
    @SerializedName("profile_pageviews")
    private Integer pageviews;

    @Expose
    @SerializedName("profile_comments")
    private Integer profileComments;

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
