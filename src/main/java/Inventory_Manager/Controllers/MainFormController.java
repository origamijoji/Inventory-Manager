package Inventory_Manager.Controllers;

import Inventory_Manager.Models.Inventory;
import Inventory_Manager.Models.Part;
import Inventory_Manager.Models.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static Inventory_Manager.Utility.confirmAlert;
import static Inventory_Manager.Utility.errorAlert;

public class MainFormController implements Initializable {

    // PARTS UI
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private TextField partTextField;

    // PARTS TABLE
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

    // PRODUCTS UI
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;

    @FXML
    private TextField productTextField;

    // PRODUCTS TABLE
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Part, Integer> productIdTableColumn;
    @FXML
    private TableColumn<Part, String> productNameTableColumn;
    @FXML
    private TableColumn<Part, Integer> productInventoryLevelTableColumn;
    @FXML
    private TableColumn<Part, Double> productPriceTableColumn;

    // OTHER UI
    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartButton.setOnAction(onActionOpenNewAddPartMenu());
        modifyPartButton.setOnAction(onActionOpenNewModifyPartMenu());
        deletePartButton.setOnAction(onActionDeletePart());
        partTextField.setOnKeyTyped(onActionQueryPart());

        partsTableView.setPlaceholder(new Label("No Results Found"));
        partsTableView.setItems(Inventory.getAllParts());
        partIdTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        addProductButton.setOnAction(onActionOpenNewAddProductMenu());
        modifyProductButton.setOnAction(onActionOpenNewModifyProductMenu());
        deleteProductButton.setOnAction(onActionDeleteProduct());
        productTextField.setOnKeyTyped(onActionQueryProduct());

        productsTableView.setPlaceholder(new Label("No Results Found"));
        productsTableView.setItems(Inventory.getAllProducts());
        productIdTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        exitButton.setOnAction(onActionExit());

    }

    /**
     * Open new add part form
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionOpenNewAddPartMenu() {
        return event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory_Manager/part-form.fxml"));
                loader.setController(new AddPartFormController());
                Scene scene = new Scene(loader.load(), 600, 400);

                Stage stage = new Stage();
                stage.setTitle("Add Part");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An unknown error has occurred", ButtonType.OK);
                alert.showAndWait();
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Open new modify part form, pass selected input to new controller
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionOpenNewModifyPartMenu() {
        return event -> {
            try {
                Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
                if (selectedPart == null) {
                    errorAlert("A part is not selected!");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory_Manager/part-form.fxml"));
                loader.setController(new ModifyPartFormController());
                Scene scene = new Scene(loader.load(), 600, 400);

                Stage stage = new Stage();
                stage.setUserData(selectedPart);
                stage.setTitle("Modify Part");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.show();

                ModifyPartFormController controller = loader.getController();
                controller.loadData(selectedPart);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Delete part based on selected part
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionDeletePart() {
        return event -> {
            try {
                Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
                if (selectedPart == null) {
                    errorAlert("A part is not selected!");
                    return;
                }
                Alert alert = confirmAlert("Are you sure you would like to delete " +  "'" + selectedPart.getName() +"'" + "?");
                if(alert.getResult() == ButtonType.YES) {
                    Inventory.deletePart(selectedPart);
                    queryPart();
                }

            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Query parts based on the search field input
     */
    protected void queryPart() {
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
    }

    /**
     * EventHandler that returns the queryPart() function
     * @return an event
     */
    protected EventHandler<KeyEvent> onActionQueryPart() {
        return event -> {
            queryPart();
        };
    }

    /**
     * Open new add product form
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionOpenNewAddProductMenu() {
        return event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory_Manager/product-form.fxml"));
                loader.setController(new AddProductFormController());
                Scene scene = new Scene(loader.load(), 857, 557);

                Stage stage = new Stage();
                stage.setTitle("Add Product");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Open new modify product form, pass selected input to new controller
     * @return
     */
    protected EventHandler<ActionEvent> onActionOpenNewModifyProductMenu() {
        return event -> {
            try {
                Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
                if (selectedProduct == null) {
                    errorAlert("A product is not selected!");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory_Manager/product-form.fxml"));
                loader.setController(new ModifyProductFormController());
                Scene scene = new Scene(loader.load(), 857, 557);

                Stage stage = new Stage();
                stage.setUserData(selectedProduct);
                stage.setTitle("Modify Product");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.show();

                ModifyProductFormController controller = loader.getController();
                controller.loadData(selectedProduct);
            }  catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Delete product based on selected product
     * FUTURE ENHANCEMENT: I think it would have made way more sense to prevent deletion of parts that are being used
     * rather than products that are using parts.
     *
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionDeleteProduct() {
        return event -> {
            try {
                Product selectedProduct = productsTableView.getSelectionModel().selectedItemProperty().getValue();
                if (selectedProduct == null) {
                    errorAlert("A product is not selected!");
                    return;
                }
                if(!selectedProduct.getAllAssociatedParts().isEmpty()) {
                    errorAlert("A product with associated parts cannot be deleted!");
                    return;
                }
                Alert alert = confirmAlert("Are you sure you would like to delete " +  "'" + selectedProduct.getName() +"'" + "?");
                if(alert.getResult() == ButtonType.YES) {
                    Inventory.deleteProduct(selectedProduct);
                    queryProduct();
                }
            } catch (Exception e) {
                errorAlert("An unknown error has occurred!");
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * EventHandler that returns the queryProduct() function
     * @return an event
     */
    protected EventHandler<KeyEvent> onActionQueryProduct() {
        return event -> {
            queryProduct();
        };
    }

    /**
     * Query products based on the search field input
     */
    protected void queryProduct() {
        try {
            String query = productTextField.getText().trim();
            if (query.isEmpty()) {
                productsTableView.setItems(Inventory.getAllProducts());
                return;
            }
            if (query.chars().allMatch(Character::isDigit)) {
                ObservableList<Product> idQuery = FXCollections.observableArrayList();
                idQuery.add(Inventory.lookupProduct(Integer.parseInt(query)));
                productsTableView.setItems(idQuery);
                productsTableView.refresh();
                return;
            }
            productsTableView.setItems(Inventory.lookupProduct(query));
            productsTableView.refresh();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Close the application
     * @return an event
     */
    protected EventHandler<ActionEvent> onActionExit() {
        return event -> {
            Platform.exit();
        };
    }
}
