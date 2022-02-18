module com.example.jocsjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jocsjavafx to javafx.fxml;
    exports com.example.jocsjavafx;
}