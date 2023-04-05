package gs_c482;

import gs_c482.Controllers.MainFormController;
import gs_c482.Models.InHouse;
import gs_c482.Models.Inventory;
import gs_c482.Models.Outsourced;
import gs_c482.Models.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gs_c482/main-form.fxml"));
        loader.setController(new MainFormController());
        Scene scene = new Scene(loader.load(), 1020, 360);
        stage.setMinHeight(400);
        stage.setMinWidth(1020);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Inventory.addPart(new InHouse(1, "Courier 4500d Airflow Case", 250.00, 32, 11, 45, 3213));
        Inventory.addPart(new InHouse(2, "MSO B665M", 220.00, 26, 6, 32, 3214));
        Inventory.addPart(new InHouse(3, "Risen 5 3620x", 360.00, 5, 1, 13, 3215));
        Inventory.addPart(new Outsourced(4, "Coal i6 12th gen", 299.00, 5, 1, 13, "Intelligence"));
        Inventory.addPart(new Outsourced(5, "JForce 3030 Ti", 299.00, 5, 1, 13, "Nvideo"));
        Product gamerPc = new Product(1, "Gamer PC", 1400.00, 4, 1, 6);
//        gamerPc.addAssociatedPart(Inventory.lookupPart(1));
//        gamerPc.addAssociatedPart(Inventory.lookupPart(2));
//        gamerPc.addAssociatedPart(Inventory.lookupPart(3));
//        gamerPc.addAssociatedPart(Inventory.lookupPart(5));
        launch();
    }
}
