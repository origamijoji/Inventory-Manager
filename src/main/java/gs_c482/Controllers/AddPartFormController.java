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
    @FXML
    private Label errorLabel;

    // BUTTONS
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;

    // INPUT FIELDS
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

    protected EventHandler<ActionEvent> onActionCloseWindow() {
        return event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        };
    }

    protected EventHandler<ActionEvent> onActionSavePart() {
        return event -> {
            try {
                int id = generatePartID();
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

            } catch (MissingValueException e) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("*Please fill out all fields");
            } catch (Exception e) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("The provided values are incorrect/exceed size");
                e.printStackTrace(System.out);
            }
        };
    }

    protected void validateInputs() throws MissingValueException {
        if (!nameField.getText().isEmpty()) return;
        if (!priceField.getText().isEmpty()) return;
        if (!invField.getText().isEmpty()) return;
        if (!minField.getText().isEmpty()) return;
        if (!maxField.getText().isEmpty()) return;
        if (!bonusField.getText().isEmpty()) return;
        throw new MissingValueException();
    }

    /**
     * Generates a unique continuous ID
     * O(n) time. O(1) space
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

}

