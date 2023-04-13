package Inventory_Manager.Controllers;

import Inventory_Manager.Models.InHouse;
import Inventory_Manager.Models.Inventory;
import Inventory_Manager.Models.Outsourced;
import Inventory_Manager.Models.Part;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static Inventory_Manager.Utility.errorAlert;

public class AddPartFormController implements Initializable {
    private PartType type;

    private enum PartType {
        InHouse, Outsourced
    }

    // LABELS
    @FXML
    private Label menuLabel;
    @FXML
    private Label bonusLabel;

    // BUTTONS
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;

    // INPUT FIELDS
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField bonusField;

    // OTHER UI
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLabel.setText("Add Part");
        idField.setText(Integer.toString(generatePartID()));

        ToggleGroup typeToggleGroup = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(typeToggleGroup);
        outsourcedRadioButton.setToggleGroup(typeToggleGroup);
        inHouseRadioButton.setOnAction(onActionSetFormInHouse());
        outsourcedRadioButton.setOnAction(onActionSetFormOutsourced());

        inHouseRadioButton.setSelected(true);
        type = PartType.InHouse;

        cancelButton.setOnAction(onActionCloseWindow());
        saveButton.setOnAction(onActionSavePart());
    }

    /**
     * Sets the form to InHouse and changes the extra field text
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionSetFormInHouse() {
        return event -> {
            this.type = PartType.InHouse;
            bonusLabel.setText("Machine ID");
        };
    }

    /**
     * Sets the form to Outsourced and changes the extra field text
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionSetFormOutsourced() {
        return event -> {
            this.type = PartType.Outsourced;
            bonusLabel.setText("Company Name");
        };
    }

    /**
     * Close the current stage
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionCloseWindow() {
        return event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        };
    }

    /**
     * Create a new part based on the given input
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionSavePart() {
        return event -> {
            try {
                if(!inputValid()) return;
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int min = Integer.parseInt(minField.getText());
                int max = Integer.parseInt(maxField.getText());

                switch (this.type) {
                    case InHouse:
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, Integer.parseInt(bonusField.getText())));
                        break;
                    case Outsourced:
                        Inventory.addPart(new Outsourced(generatePartID(), name, price, stock, min, max, bonusField.getText()));
                        break;
                }
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                errorAlert("The provided values are incorrect");
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Generate a unique continuous ID
     * O(n) time; n = # of parts in Inventory
     *
     * @return a unique id for an Inventory part
     */
    public static int generatePartID() {
        int id = 1;
        for (Part part : Inventory.getAllParts()) {
            if (!(id == part.getId())) return id;
            id += 1;
        }
        return id;
    }

    /**
     * Validate the given input, send alert if incorrect
     * @return a boolean representing whether the input is valid or not
     */
    protected boolean inputValid() {
        if (nameField.getText().isEmpty()) {
            errorAlert("The name field cannot be empty!");
            return false;
        }
        else if (priceField.getText().isEmpty()) {
            errorAlert("The price field cannot be empty!");
            return false;
        }
        else if (invField.getText().isEmpty()) {
            errorAlert("The inv field cannot be empty!");
            return false;
        }
        else if (minField.getText().isEmpty()) {
            errorAlert("The min field cannot be empty!");
            return false;
        }
        if (maxField.getText().isEmpty()) {
            errorAlert("The max field cannot be empty!");
            return false;
        }
        if (bonusField.getText().isEmpty()) {
            if (type == PartType.InHouse) {
                errorAlert("The machine ID field cannot be empty!");
            } else if (type == PartType.Outsourced) {
                errorAlert("The company name field cannot be empty!");
            }
            return false;
        }

        int stock = Integer.parseInt(invField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        if(min < 0 || max < 0 || stock < 0) {
            errorAlert("Min, max, and stock must equal or exceed 0");
            return false;
        }

        if (min >= max) {
            errorAlert("Min cannot equal or exceed max!");
            return false;
        }
        if (stock > max) {
            errorAlert("Current inv cannot exceed max!");
            return false;
        }
        if (stock < min) {
            errorAlert("Min cannot exceed the current inv!");
            return false;
        }
        return true;
    }

}

