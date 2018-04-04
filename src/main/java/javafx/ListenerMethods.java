package javafx;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * <p>ListenerMethods class.</p>
 *
 * @author Kamil Cieślik
 * @version $Id: $Id
 */
public class ListenerMethods {
    /**
     * <p>Constructor for ListenerMethods.</p>
     */
    public ListenerMethods() {

    }

    /**
     * <p>changeLabelTextField.</p>
     *
     * @param regex      a {@link String} object.
     * @param textField  a javafx.scene.control.TextField object.
     * @param label      a javafx.scene.control.Label object.
     * @param isEmpty    a {@link String} object.
     * @param doesNotFit a {@link String} object.
     */
    public void changeLabelTextField(String regex, TextField textField, Label label, String isEmpty, String doesNotFit) {
        if (textField.getText().isEmpty())
            label.setText(isEmpty);
        else if (!textField.getText().matches(regex))
            label.setText(doesNotFit);
        else
            label.setText("");
    }

    /**
     * <p>changeLabelComboBox.</p>
     *
     * @param comboBox a javafx.scene.control.ComboBox object.
     * @param label    a javafx.scene.control.Label object.
     * @param isEmpty  a {@link String} object.
     */
    public void changeLabelComboBox(ComboBox comboBox, Label label, String isEmpty) {
        if (comboBox.getSelectionModel().getSelectedItem() == null ||
                comboBox.getSelectionModel().getSelectedItem().toString().equals(""))
            label.setText(isEmpty);
        else
            label.setText("");
    }
}
