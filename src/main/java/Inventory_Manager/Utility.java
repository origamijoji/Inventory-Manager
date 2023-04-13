package Inventory_Manager;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class Utility {

    public static Alert errorAlert(String dialog) {
        Alert alert = new Alert(Alert.AlertType.ERROR, dialog, ButtonType.OK);
        alert.showAndWait();
        return alert;
    }
    public static Alert confirmAlert(String dialog) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, dialog, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert;
    }
}
