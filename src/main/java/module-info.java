module com.example.golestan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.golestan to javafx.fxml;
    exports com.example.golestan;
    exports com.example.golestan.Controller;
    opens com.example.golestan.Controller to javafx.fxml;
    exports com.example.golestan.Box;
    opens com.example.golestan.Box to javafx.fxml;
}