module Inventory_Manager {
    requires javafx.controls;
    requires javafx.fxml;


    opens Inventory_Manager to javafx.fxml;
    exports Inventory_Manager;
    exports Inventory_Manager.Models;
    opens Inventory_Manager.Models to javafx.fxml;
    exports Inventory_Manager.Controllers;
    opens Inventory_Manager.Controllers to javafx.fxml;
}