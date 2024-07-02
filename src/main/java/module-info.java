module com.example.tugas6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.library.books to javafx.base;
    opens com.library to javafx.fxml;
    exports com.library;
}