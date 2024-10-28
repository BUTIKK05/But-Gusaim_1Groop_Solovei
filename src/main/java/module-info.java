module com.example.lab1_solov_kg_nechet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab1_solov_kg_nechet to javafx.fxml;
    exports com.example.lab1_solov_kg_nechet;
}