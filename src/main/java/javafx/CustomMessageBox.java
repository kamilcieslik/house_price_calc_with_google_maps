package javafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * <p>CustomMessageBox class.</p>
 *
 * @author Kamil Cieślik
 * @version $Id: $Id
 */
public class CustomMessageBox {
    private String iconImagePath;

    /**
     * <p>Constructor for CustomMessageBox.</p>
     *
     * @param iconImagePath a {@link String} object.
     */
    public CustomMessageBox(String iconImagePath) {
        this.iconImagePath = iconImagePath;
    }

    /**
     * <p>showMessageBox.</p>
     *
     * @param alertType a javafx.scene.control.Alert$AlertType object.
     * @param title a {@link String} object.
     * @param header a {@link String} object.
     * @param content a {@link String} object.
     * @return a javafx.scene.control.Alert object.
     */
    public Alert showMessageBox(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(iconImagePath));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    /**
     * <p>showConfirmMessageBox.</p>
     *
     * @param alertType a javafx.scene.control.Alert$AlertType object.
     * @param title a {@link String} object.
     * @param header a {@link String} object.
     * @param content a {@link String} object.
     * @param confirmText a {@link String} object.
     * @param cancelText a {@link String} object.
     * @return a javafx.scene.control.Alert object.
     */
    public Alert showConfirmMessageBox(Alert.AlertType alertType, String title, String header, String content,
                                       String confirmText, String cancelText) {
        ButtonType confirmButton = new ButtonType(confirmText, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType(cancelText, ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(alertType, content, confirmButton, cancelButton);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(iconImagePath));
        alert.setTitle(title);
        alert.setHeaderText(header);
        return alert;
    }
}
