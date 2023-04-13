package Inventory_Manager;

import Inventory_Manager.Controllers.MainFormController;
import Inventory_Manager.Models.InHouse;
import Inventory_Manager.Models.Inventory;
import Inventory_Manager.Models.Outsourced;
import Inventory_Manager.Models.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory_Manager/main-form.fxml"));
        loader.setController(new MainFormController());
        Scene scene = new Scene(loader.load(), 1020, 360);
        stage.setMinHeight(400);
        stage.setMinWidth(1020);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Inventory.addPart(new InHouse(1, "Servo Motor", 1.99, 32, 11, 45, 3213));
        Inventory.addPart(new InHouse(2, "Spring", 0.12, 78, 2, 129, 3214));
        Inventory.addPart(new InHouse(3, "Core i9 12th gen", 359.99, 5, 1, 13, 3215));
        Inventory.addPart(new Outsourced(4, "Microcontroller", 12.49, 21, 1, 49, "Component Company"));
        Inventory.addPart(new Outsourced(5, "Lincoln Log", 1.79, 9, 2, 10, "Wood inc."));
        Inventory.addPart(new InHouse(6,"Large Metal Case", 149.99, 4, 1, 12, 3216));
        Inventory.addPart(new Outsourced(7, "Wooden Toy Block", 0.49, 24, 6, 32, "Wood inc."));

        Product lock = new Product(1, "Electronic Lock", 49.99, 4, 1, 6);
        lock.addAssociatedPart(Inventory.lookupPart(1));
        lock.addAssociatedPart(Inventory.lookupPart(4));
        lock.addAssociatedPart(Inventory.lookupPart(2));
        Inventory.addProduct(lock);

        Product computer = new Product(2, "Computer", 1599.99, 2, 1, 4);
        computer.addAssociatedPart(Inventory.lookupPart(3));
        computer.addAssociatedPart(Inventory.lookupPart(6));
        Inventory.addProduct(computer);

        Product toySet = new Product(3, "Toy Set", 29.99, 17, 6, 54);
        toySet.addAssociatedPart(Inventory.lookupPart(5));
        toySet.addAssociatedPart(Inventory.lookupPart(7));
        Inventory.addProduct(toySet);

        launch();
    }
}
