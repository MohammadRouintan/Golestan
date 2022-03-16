module com.example.golestan {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.golestan to javafx.fxml;
    exports com.example.golestan;
}