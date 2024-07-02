package com.library;

import com.library.books.HistoryBook;
import com.library.books.StoryBook;
import com.library.books.TextBook;
import com.library.data.Admin;
import com.library.data.Student;
import com.library.data.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
            if (User.bookList.isEmpty()) {
                // Menambahkan buku-buku ke dalam User.bookList
                User.bookList.add(new HistoryBook("388c-e681-9152", "Snow Prince", "John Squartz", 4));
                Admin.bookJumlah++;
                User.bookList.add(new StoryBook("ed90-be30-5cdb", "Autumn Winter", "John William", 0));
                Admin.bookJumlah++;
                User.bookList.add(new TextBook("d95e-0c4a-9523", "Sakura Swirl", "John Phoenix", 1));
                Admin.bookJumlah++;
            }
        primaryStage.setTitle("Library App");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene loginScene = new Scene(grid, 400, 375);
        primaryStage.setScene(loginScene);

        Text scenetitle = new Text("Halaman Login");
        scenetitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 1);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 1);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 2);

        Button loginButton = new Button("Log in");
        GridPane.setConstraints(loginButton, 1, 3);

        // ChoiceBox for selecting user type
        ChoiceBox<String> userTypeChoiceBox = new ChoiceBox<>();
        userTypeChoiceBox.getItems().addAll("Student", "Admin");
        userTypeChoiceBox.setValue("Student"); // default selection
        GridPane.setConstraints(userTypeChoiceBox, 1, 4);

        // Event handler for user type choice box
        userTypeChoiceBox.setOnAction(e -> {
            String userType = userTypeChoiceBox.getValue();
            if (userType.equals("Student")) {
               usernameInput.setPromptText("Enter Username");
                passwordInput.setPromptText("Enter Password");
            } else {
                if (!grid.getChildren().contains(usernameLabel)) {
                    grid.add(usernameLabel, 0, 1);
                    grid.add(usernameInput, 1, 1);
                }
                usernameInput.setPromptText("Enter Username");
                passwordInput.setPromptText("Enter Password");
            }
        });

        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String userType = userTypeChoiceBox.getValue();

            if (userType.equals("Student")) {
                checkNim(primaryStage, password);
            } else if (userType.equals("Admin")) {
                if (Admin.isAdmin(username, password)) {
                    showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome Admin!");
                    menuAdmin(primaryStage);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
                }
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, userTypeChoiceBox);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void menuStudent(Stage primaryStage, Student student) {
        student.menu();
    }

    private void menuAdmin(Stage primaryStage) {
        Admin admin = new Admin();
        admin.menu();
    }

    private void checkNim(Stage primaryStage, String nim) {
        Admin admin = new Admin();
        ArrayList<Student> studentList = admin.getStudentList();
        boolean found = false;
        if (studentList != null) {
            for (Student student : studentList) {
                if (student.getNim().equals(nim)) {
                    found = true;
                    Student.isStudent = true;
                    showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + Student.displayInfo(student));
                    menuStudent(primaryStage, student);
                    break;
                }
            }
        }

        if (!found) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "User/Mahasiswa tidak terdaftar");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
