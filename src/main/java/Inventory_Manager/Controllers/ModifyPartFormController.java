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

public class ModifyPartFormController implements Initializable {
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
        menuLabel.setText("Modify Part");

        ToggleGroup typeToggleGroup = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(typeToggleGroup);
        outsourcedRadioButton.setToggleGroup(typeToggleGroup);
        inHouseRadioButton.setOnAction(onActionSetFormInHouse());
        outsourcedRadioButton.setOnAction(onActionSetFormOutsourced());

        inHouseRadioButton.setSelected(true);
        type = PartType.InHouse;

        cancelButton.setOnAction(onActionCloseMenu());
        saveButton.setOnAction(onActionSavePartCloseMenu());

    }

    /**
     * Sets the form to InHouse and changes the bonus field text
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionSetFormInHouse() {
        return event -> {
            this.type = PartType.InHouse;
            bonusLabel.setText("Machine ID");
        };
    }

    /**
     * Sets the form to Outsourced and changes the bonus field text
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionSetFormOutsourced() {
        return event -> {
            this.type = PartType.Outsourced;
            bonusLabel.setText("Company Name");
        };
    }

    /**
     * Imports data to be called from another controller
     * RUNTIME ERROR: I ran into quite a few errors trying to detect what the part was and importing the data,
     * I resolved this by checking if the part is InHouse or Outsourced and then cast it to its respective type before
     * fetching its specialized data
     *
     * @param part to be updated to
     */
    public void loadData(Part part) {
        idField.setText(Integer.toString(part.getId()));
        nameField.setText(part.getName());
        invField.setText(Integer.toString(part.getStock()));
        priceField.setText(Double.toString(part.getPrice()));
        maxField.setText(Integer.toString(part.getMax()));
        minField.setText(Integer.toString(part.getMin()));
        if (part instanceof InHouse) {
            bonusField.setText(Integer.toString(((InHouse) part).getMachineId()));
            this.type = PartType.InHouse;
            bonusLabel.setText("Machine ID");
            inHouseRadioButton.setSelected(true);
        } else {
            bonusField.setText(((Outsourced) part).getCompanyName());
            this.type = PartType.Outsourced;
            bonusLabel.setText("Company Name");
            outsourcedRadioButton.setSelected(true);
        }

        Inventory.lookupPart(part.getId());
    }

    /**
     * Update the current part based on the given input
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionSavePartCloseMenu() {
        return event -> {
            try {
                if(!inputValid()) return;
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int min = Integer.parseInt(minField.getText());
                int max = Integer.parseInt(maxField.getText());

                if (this.type.equals(PartType.InHouse)) {
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(bonusField.getText()));
                    Inventory.updatePart(id, newPart);
                } else {
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, bonusField.getText());
                    Inventory.updatePart(id, newPart);
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
     * Close the current stage
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionCloseMenu() {
        return event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        };
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
