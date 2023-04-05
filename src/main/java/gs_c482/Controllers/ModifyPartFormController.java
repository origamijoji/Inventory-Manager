package gs_c482.Controllers;

import gs_c482.Models.InHouse;
import gs_c482.Models.Inventory;
import gs_c482.Models.Outsourced;
import gs_c482.Models.Part;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController implements Initializable {
    private PartType type;

    private enum PartType {
        InHouse, Outsourced
    }

    private int index;

    // LABELS
    @FXML
    private Label menuLabel;
    @FXML
    private Label bonusLabel;
    @FXML
    private Label errorLabel;

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

    protected EventHandler<ActionEvent> onActionSetFormInHouse() {
        return event -> {
            this.type = PartType.InHouse;
            bonusLabel.setText("Machine ID");
        };
    }

    protected EventHandler<ActionEvent> onActionSetFormOutsourced() {
        return event -> {
            this.type = PartType.Outsourced;
            bonusLabel.setText("Company Name");
        };
    }

    public void loadData(Part part) {
        this.index = 0;
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

    protected EventHandler<ActionEvent> onActionSavePartCloseMenu() {
        return event -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(invField.getText());
                int min = Integer.parseInt(minField.getText());
                int max = Integer.parseInt(maxField.getText());

                validateInputs();
                if (min >= max) {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Min cannot equal or exceed max!");
                    return;
                }
                if (stock > max) {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Stock cannot be greater than max!");
                    return;
                }
                if (stock < min) {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Stock cannot be less than min!");
                    return;
                }
                if (this.type.equals(PartType.InHouse)) {
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(bonusField.getText()));
                    Inventory.updatePart(id, newPart);
                } else {
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, bonusField.getText());
                    Inventory.updatePart(id, newPart);
                }
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Part Added!");
            } catch (Exception e) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("The provided values are incorrect/exceed size");
                e.printStackTrace(System.out);
            }
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        };
    }

    protected EventHandler<ActionEvent> onActionCloseMenu() {
        return event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        };
    }

    protected void validateInputs() {
        if (!nameField.getText().isEmpty()) return;
        if (!priceField.getText().isEmpty()) return;
        if (!invField.getText().isEmpty()) return;
        if (!minField.getText().isEmpty()) return;
        if (!maxField.getText().isEmpty()) return;
        if (!bonusField.getText().isEmpty()) return;
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("*Please fill out all fields");
    }
}
