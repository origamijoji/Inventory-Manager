module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens gs_c482 to javafx.fxml;
    exports gs_c482;
    exports gs_c482.Models;
    opens gs_c482.Models to javafx.fxml;
    exports gs_c482.Controllers;
    opens gs_c482.Controllers to javafx.fxml;
}