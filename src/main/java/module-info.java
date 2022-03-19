module com.example.golestan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.golestan to javafx.fxml;
    exports com.example.golestan;
}