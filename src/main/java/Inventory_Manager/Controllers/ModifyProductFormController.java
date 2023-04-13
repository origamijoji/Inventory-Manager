package Inventory_Manager.Controllers;

import Inventory_Manager.Models.Inventory;
import Inventory_Manager.Models.Part;
import Inventory_Manager.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static Inventory_Manager.Utility.confirmAlert;
import static Inventory_Manager.Utility.errorAlert;

public class ModifyProductFormController implements Initializable {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private Label menuLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
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
        menuLabel.setText("Modify Product");

        saveButton.setOnAction(onActionAddProduct());
        cancelButton.setOnAction(onActionCancel());
        addAssociationButton.setOnAction(onActionAddAssociation());
        removeAssociationButton.setOnAction(onActionRemoveAssociation());
        partTextField.setOnKeyTyped(onActionQueryParts());

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

    /**
     * Set selected part as associated part
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionAddAssociation() {
        return event -> {
            try {
                Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
                if(selectedPart == null) {
                    errorAlert("A part is not selected!");
                    return;
                }
                if(associatedParts.contains(selectedPart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "This part is already associated!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
                associatedParts.add(selectedPart);
                associatedPartsTableView.refresh();

            } catch (Exception e) {
                errorAlert("An unknown error has occurred");
                e.printStackTrace(System.out);
            }

        };
    }

    /**
     * Remove selected part as associated part
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionRemoveAssociation() {
        return event -> {
            try {
                Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
                if(selectedPart == null) {
                    errorAlert("A part is not selected!");
                    return;
                }
                Alert alert = confirmAlert("Are you sure you would like to remove" + "'" + selectedPart.getName() + "'" + "?");
                if(alert.getResult() == ButtonType.YES) {
                    associatedParts.remove(selectedPart);
                    associatedPartsTableView.refresh();
                }
            } catch (Exception e) {
                errorAlert("An unknown error has occurred");
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Update product based on given input
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionAddProduct() {
        return event -> {
            try {
                if(!inputValid()) return;
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int stock = Integer.parseInt(invField.getText());
                double price = Double.parseDouble(priceField.getText());
                int min = Integer.parseInt(minField.getText());
                int max = Integer.parseInt(maxField.getText());

                Product newProduct = new Product(id, name, price, stock, min, max);
                for(Part aPart : associatedParts) {
                    newProduct.addAssociatedPart(aPart);
                }
                Inventory.updateProduct(id, newProduct);
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            } catch (Exception e){
                errorAlert("The provided values are incorrect");
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Close the current stage
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionCancel() {
        return event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        };
    }

    /**
     * Query the parts table based on the search field input
     * @return an event
     */
    protected EventHandler<KeyEvent> onActionQueryParts() {
        return event -> {
            try {
                String query = partTextField.getText().trim();
                if (query.isEmpty()) {
                    partsTableView.setItems(Inventory.getAllParts());
                    return;
                }
                if (query.chars().allMatch(Character::isDigit)) {
                    ObservableList<Part> idQuery = FXCollections.observableArrayList();
                    idQuery.add(Inventory.lookupPart(Integer.parseInt(query)));
                    partsTableView.setItems(idQuery);
                    partsTableView.refresh();
                    return;
                }
                partsTableView.setItems(Inventory.lookupPart(query));
                partsTableView.refresh();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
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
        } else if (priceField.getText().isEmpty()) {
            errorAlert("The price field cannot be empty!");
            return false;
        } else if (invField.getText().isEmpty()) {
            errorAlert("The inv field cannot be empty!");
            return false;
        } else if (minField.getText().isEmpty()) {
            errorAlert("The min field cannot be empty!");
            return false;
        }
        if (maxField.getText().isEmpty()) {
            errorAlert("The max field cannot be empty!");
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

    public void loadData(Product product) {
        idField.setText(Integer.toString(product.getId()));
        nameField.setText(product.getName());
        invField.setText(Integer.toString(product.getStock()));
        priceField.setText(Double.toString(product.getPrice()));
        maxField.setText(Integer.toString(product.getMax()));
        minField.setText(Integer.toString(product.getMin()));
        associatedParts.addAll(product.getAllAssociatedParts());
        associatedPartsTableView.refresh();
    }
}
