module com.example.rentaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.rentaproject to javafx.fxml;
    exports com.example.rentaproject;
}