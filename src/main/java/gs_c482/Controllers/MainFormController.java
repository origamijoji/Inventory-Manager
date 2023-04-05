package gs_c482.Controllers;

import gs_c482.Models.Inventory;
import gs_c482.Models.Part;
import gs_c482.Models.Product;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;

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
     * ERROR: couldn't locate file with current maven file structure
     */
    protected EventHandler<ActionEvent> onActionOpenNewAddPartMenu() {
        return event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gs_c482/part-form.fxml"));
                loader.setController(new AddPartFormController());
                Scene scene = new Scene(loader.load(), 600, 400);

                Stage stage = new Stage();
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.setTitle("Add Part");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An unknown error has occurred", ButtonType.OK);
                alert.showAndWait();
                e.printStackTrace(System.out);
            }
        };
    }

    /**
     * Handles an event that opens a new window for modifying the currently selected part
     *
     * @return an EventHandler to be passed to an onAction
     */
    protected EventHandler<ActionEvent> onActionOpenNewModifyPartMenu() {
        return event -> {
            try {
                Part selectedPart = partsTableView.getSelectionModel().selectedItemProperty().getValue();
                if (selectedPart == null) {
                    throw new MissingValueException();
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gs_c482/part-form.fxml"));
                loader.setController(new ModifyPartFormController());
                Scene scene = new Scene(loader.load(), 600, 400);

                Stage stage = new Stage();
                stage.setUserData(selectedPart);
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.setTitle("Add Part");
                stage.setScene(scene);
                stage.show();

                ModifyPartFormController controller = loader.getController();
                controller.loadData(selectedPart);
            } catch (MissingValueException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "A part is not selected!");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    protected EventHandler<ActionEvent> onActionDeletePart() {
        return event -> {
            try {
                Part selectedPart = partsTableView.getSelectionModel().selectedItemProperty().getValue();
                if (selectedPart == null) {
                    throw new MissingValueException();
                }
                Inventory.deletePart(selectedPart);
                queryPart();

            } catch (MissingValueException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "A part is not selected!", ButtonType.OK);
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

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
     * Action that queries and updates the parts table
     * Acts as an eventHandler version of queryPart
     * @return an event
     */
    protected EventHandler<KeyEvent> onActionQueryPart() {
        return event -> {
            queryPart();
        };
    }

    protected EventHandler<ActionEvent> onActionOpenNewAddProductMenu() {
        return event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gs_c482/product-form.fxml"));
                loader.setController(new AddProductFormController());
                Scene scene = new Scene(loader.load(), 864, 557);

                Stage stage = new Stage();
                stage.setMinWidth(857);
                stage.setMinHeight(557);
                stage.setTitle("Add Product");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    protected EventHandler<ActionEvent> onActionOpenNewModifyProductMenu() {
        return event -> {
            try {
                Product selectedProduct = productsTableView.getSelectionModel().selectedItemProperty().getValue();

            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    protected EventHandler<ActionEvent> onActionDeleteProduct() {
        return event -> {
            try {
                Product selectedProduct = productsTableView.getSelectionModel().selectedItemProperty().getValue();
                Inventory.deleteProduct(selectedProduct);
                productsTableView.refresh();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        };
    }

    protected EventHandler<KeyEvent> onActionQueryProduct() {
        return event -> {
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
        };
    }

    protected EventHandler<ActionEvent> onActionExit() {
        return event -> {
            Platform.exit();
        };
    }
}
