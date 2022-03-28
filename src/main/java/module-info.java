module com.example.had {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.had to javafx.fxml;
    exports com.example.had;
}