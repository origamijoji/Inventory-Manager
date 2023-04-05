package gs_c482.Controllers;

import gs_c482.Models.Inventory;
import gs_c482.Models.Part;
import gs_c482.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * bonus feature: make products able to hold several of each association
 */
public class AddProductFormController implements Initializable {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private Label menuLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button addAssociationButton;
    @FXML
    private Button removeAssociationButton;
    @FXML
    private TextField partTextField;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;

    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdTableColumn;
    @FXML
    private TableColumn<Part, String> partNameTableColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelTableColumn;
    @FXML
    private TableColumn<Part, Double> partPriceTableColumn;

    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdTableColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameTableColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryLevelTableColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceTableColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLabel.setText("Add Product");

        saveButton.setOnAction(onActionAddProduct());
        cancelButton.setOnAction(onActionCancel());
        addAssociationButton.setOnAction(onActionAddAssociation());
        removeAssociationButton.setOnAction(onActionRemoveAssociation());

        partsTableView.setPlaceholder(new Label("No Results Found"));
        partsTableView.setItems(Inventory.getAllParts());
        partIdTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        associatedPartsTableView.setPlaceholder(new Label("No Results Found"));
        associatedPartsTableView.setItems(associatedParts);
        associatedPartIdTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }

    protected EventHandler<ActionEvent> onActionAddAssociation() {
        return event -> {
            try {
                Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
                if(associatedParts.contains(selectedPart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "This part is already associated!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
                associatedParts.add(selectedPart);
                associatedPartsTableView.refresh();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "A part is not selected!", ButtonType.OK);
                alert.showAndWait();
            }

        };
    }

    protected EventHandler<ActionEvent> onActionRemoveAssociation() {
        return event -> {
            try {
                Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
                associatedParts.remove(selectedPart);
                associatedPartsTableView.refresh();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "A part is not selected!", ButtonType.OK);
                alert.showAndWait();
            }
        };
    }

    protected EventHandler<ActionEvent> onActionAddProduct() {
        return event -> {
            try {

                int id = generateProductID();
                String name = nameField.getText();
                int stock = Integer.parseInt(invField.getText());
                double price = Double.parseDouble(priceField.getText());
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

            } catch (Exception e){

            }

        };
    }

    protected EventHandler<ActionEvent> onActionCancel() {
        return event -> {

        };
    }

    protected void validateInputs() throws MissingValueException {
        if (!nameField.getText().isEmpty()) return;
        if (!priceField.getText().isEmpty()) return;
        if (!invField.getText().isEmpty()) return;
        if (!minField.getText().isEmpty()) return;
        if (!maxField.getText().isEmpty()) return;
        throw new MissingValueException();
    }

    private int generateProductID() {
        int id = 1;
        for (Product product : Inventory.getAllProducts()) {
            if (!(id == product.getId())) return id;
            id += 1;
        }
        return id;
    }
}
