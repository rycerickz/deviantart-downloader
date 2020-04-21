/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.components;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.FOLDER;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SAVE;
import static javafx.geometry.Pos.CENTER;

/*====================================================================================================================*/

@Getter
@Setter
public class Generate {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Generate.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public static BorderPane generateBorderPaneGallery(Document document, Image image){
        double ratio = image.getWidth() / image.getHeight();

        double width = Math.min(Math.min(image.getWidth(), 300), image.getWidth() / ratio);
        double height = Math.min(Math.min(image.getHeight(), 300), image.getHeight() / ratio);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        // TODO: hacer un hover que muestre la informacion de la imagen.
        //Label label = new Label(document.getTitle());
        //label.setPadding(new Insets(10));
        //label.setAlignment(CENTER);
        //label.setPrefWidth(width - 20);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #dddddd");
        borderPane.setPrefSize(width, height);
        borderPane.setCenter(imageView);
        // borderPane.setBottom(label);

        return borderPane;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
