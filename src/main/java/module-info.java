module com.example.xo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.xo to javafx.fxml;
    exports com.example.xo;
}