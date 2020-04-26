/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.components;

/*====================================================================================================================*/

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.*;
import static javafx.scene.control.ContentDisplay.CENTER;

/*====================================================================================================================*/

@Getter
@Setter
public class Icons {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Icons.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public static Label getFaFolder(){
        return GlyphsDude.createIconLabel(
                FOLDER,
                "",
                "12px",
                "12px",
                CENTER);
    }

    public static Label getFaSave(){
        return GlyphsDude.createIconLabel(
                SAVE,
                "",
                "12px",
                "12px",
                CENTER);
    }

    public static Label getFaCheckCircle(){
        return GlyphsDude.createIconLabel(
                CHECK_CIRCLE,
                "",
                "12px",
                "12px",
                CENTER);
    }

    public static Label getFaTimesCircle(){
        return GlyphsDude.createIconLabel(
                TIMES_CIRCLE,
                "",
                "12px",
                "12px",
                CENTER);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
