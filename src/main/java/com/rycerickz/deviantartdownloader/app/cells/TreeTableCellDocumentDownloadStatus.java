/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.cells;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.components.Icons;
import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;
import com.rycerickz.deviantartdownloader.app.schemes.entities.Document.DOCUMENT_DOWNLAOD_STATUS;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

import static javafx.geometry.HPos.CENTER;
import static javafx.geometry.Pos.BASELINE_CENTER;
import static javafx.geometry.Pos.BOTTOM_CENTER;

/*====================================================================================================================*/

@Getter
@Setter
public class TreeTableCellDocumentDownloadStatus extends TreeTableCell<Document, DOCUMENT_DOWNLAOD_STATUS> {

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void updateItem(DOCUMENT_DOWNLAOD_STATUS documentDownlaodStatus, boolean empty) {
        super.updateItem(documentDownlaodStatus, empty);

        if (documentDownlaodStatus == null || empty) {
            setGraphic(null);
            return;
        }

        Label label = null;

        switch (documentDownlaodStatus) {
            case UNKNOWN:
                break;
            case DOWNLOADED:
                label= Icons.getFaCheckCircle();
                break;
            case ERROR:
                label = Icons.getFaTimesCircle();
                break;
        }

        setGraphic(label);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
