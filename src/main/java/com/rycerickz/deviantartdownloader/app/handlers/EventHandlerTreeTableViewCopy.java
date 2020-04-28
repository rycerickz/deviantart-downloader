/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.handlers;

/*====================================================================================================================*/

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;

import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.CONTROL_ANY;

/*====================================================================================================================*/

@Getter
@Setter
public class EventHandlerTreeTableViewCopy implements EventHandler<KeyEvent> {

    /*----------------------------------------------------------------------------------------------------------------*/

    private KeyCodeCombination keyCodeCombinationCopy = new KeyCodeCombination(C, CONTROL_ANY);
    private KeyCodeCombination keyCodeCombinationPaste = new KeyCodeCombination(V, CONTROL_ANY);

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void handle(KeyEvent keyEvent) {
        if (getKeyCodeCombinationCopy().match(keyEvent)) {
            if (keyEvent.getSource() instanceof TreeTableView) {
                copySelectionToClipboard((TreeTableView<?>) keyEvent.getSource());
                keyEvent.consume();
            }
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void copySelectionToClipboard(TreeTableView<?> treeTableView) {
        StringBuilder clipboardString = new StringBuilder();

        int prevRow = -1;

        for (TreeTablePosition position : treeTableView.getSelectionModel().getSelectedCells()) {

            int row = position.getRow();
            int col = position.getColumn();

            if (prevRow == row) {
                clipboardString.append('\t');

            } else if (prevRow != -1) {
                clipboardString.append('\n');
            }

            String text = "";

            Object observableValue = treeTableView.getColumns().get(col).getCellObservableValue(row);

            if (observableValue == null) {
                text = "";

            } else if (observableValue instanceof DoubleProperty) {
                text = NumberFormat.getNumberInstance().format(((DoubleProperty) observableValue).get());

            } else if (observableValue instanceof IntegerProperty) {
                text = NumberFormat.getNumberInstance().format(((IntegerProperty) observableValue).get());

            } else if (observableValue instanceof StringProperty) {
                text = ((StringProperty) observableValue).get();

            } else if (observableValue instanceof ObjectProperty) {
                text = ((ObjectProperty) observableValue).getValue().toString();
            }

            clipboardString.append(text);
            prevRow = row;
        }

        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());

        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
