/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.components;

/*====================================================================================================================*/

import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.FOLDER;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SAVE;
import static javafx.scene.control.ContentDisplay.CENTER;

/*====================================================================================================================*/

@Getter
@Setter
public class Icons {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Icons.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public static Label FA_FOLDER =
            GlyphsDude.createIconLabel(
                    FOLDER,
                    "",
                    "12px",
                    "12px",
                    CENTER);

    public static Label FA_SAVE =
            GlyphsDude.createIconLabel(
                    SAVE,
                    "",
                    "12px",
                    "12px",
                    CENTER);

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
