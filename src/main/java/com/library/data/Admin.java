package com.library.data;

import com.library.books.HistoryBook;
import com.library.books.StoryBook;
import com.library.books.TextBook;
import com.library.util.iMenu;
import com.library.exception.custom.illegalAdminAccess;
import com.library.books.Book;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.UUID;
import java.util.ArrayList;

public class Admin extends User implements iMenu {
    private static String username = "admin";
    private static String password = "admin";
    private Scene addStudentScene;
    private static ArrayList<Student> StudentList = new ArrayList<>();
    public static int bookJumlah = 0;
    public static int stuJumlah = 0;

    public static boolean isAdmin(String username, String password) {
        if (Admin.username.equals(username) && Admin.password.equals(password)) {
            return true;
        } else {
            try {
                throw new illegalAdminAccess("Invalid credentials");
            } catch (illegalAdminAccess e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
    }

    public ArrayList<Student> getStudentList() {
        return StudentList;
    }

    public void addStudent(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add Student");
        scenetitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label nameLabel = new Label("Nama:");
        GridPane.setConstraints(nameLabel, 0, 1);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 1);

        Label nimLabel = new Label("NIM:");
        GridPane.setConstraints(nimLabel, 0, 2);
        TextField nimInput = new TextField();
        GridPane.setConstraints(nimInput, 1, 2);

        Label facultyLabel = new Label("Fakultas:");
        GridPane.setConstraints(facultyLabel, 0, 3);
        TextField facultyInput = new TextField();
        GridPane.setConstraints(facultyInput, 1, 3);

        Label programStudyLabel = new Label("Program Studi:");
        GridPane.setConstraints(programStudyLabel, 0, 4);
        TextField programStudyInput = new TextField();
        GridPane.setConstraints(programStudyInput, 1, 4);

        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1,5);
        saveButton.setAlignment(Pos.BASELINE_CENTER);

        saveButton.setOnAction(e -> {
            String nama = nameInput.getText();
            String nim = nimInput.getText();
            String fakultas = facultyInput.getText();
            String programStudi = programStudyInput.getText();

            try {
                if (nama.isEmpty()) {
                    throw new IllegalArgumentException("Nama tidak boleh kosong");
                } else if (nim.isEmpty()) {
                    throw new IllegalArgumentException("NIM tidak boleh kosong");
                } else if (fakultas.isEmpty()) {
                    throw new IllegalArgumentException("Fakultas tidak boleh kosong");
                } else if (programStudi.isEmpty()) {
                    throw new IllegalArgumentException("Program Studi tidak boleh kosong");
                } else if (nim.length() != 15) {
                    throw new IllegalArgumentException("Panjang NIM harus 15 angka.");
                } else if (!nim.matches("\\d+")) {
                    throw new IllegalArgumentException("NIM tidak valid.");
                } else if (nim.length() == 15) {
                    primaryStage.close();
                }

                // Simpan data mahasiswa
                StudentList.add(new Student(nama, nim, fakultas, programStudi));
                stuJumlah++;
                showAlert(Alert.AlertType.INFORMATION, "Success", "Data mahasiswa berhasil ditambahkan.");

                // Kembali ke menu admin tanpa menutup stage
                primaryStage.close();
                AdminMenu();
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", ex.getMessage());
            }
        });

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 2,5);
        backBtn.setAlignment(Pos.BASELINE_CENTER);

        backBtn.setOnAction(e -> {
            primaryStage.close();
            AdminMenu();
                });

        grid.getChildren().addAll(nameLabel, nameInput, nimLabel, nimInput, facultyLabel, facultyInput, programStudyLabel, programStudyInput, saveButton, backBtn);

        Scene addStudentScene = new Scene(grid, 400, 375);
        primaryStage.setScene(addStudentScene);
        primaryStage.show();
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void AdminMenu() {
        // Panggil menu dari instance saat ini
        this.menu();
    }

    public void addBook(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add Book");
        scenetitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label categoryLabel = new Label("Category:");
        GridPane.setConstraints(categoryLabel, 0, 1);
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Story Book", "History Book", "Text Book");
        GridPane.setConstraints(categoryComboBox, 1, 1);

        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 2);
        TextField titleInput = new TextField();
        GridPane.setConstraints(titleInput, 1, 2);

        Label authorLabel = new Label("Author:");
        GridPane.setConstraints(authorLabel, 0, 3);
        TextField authorInput = new TextField();
        GridPane.setConstraints(authorInput, 1, 3);

        Label stockLabel = new Label("Stock:");
        GridPane.setConstraints(stockLabel, 0, 4);
        TextField stockInput = new TextField();
        GridPane.setConstraints(stockInput, 1, 4);

        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1, 5);
        saveButton.setAlignment(Pos.BASELINE_CENTER);

        Button backBtn = new Button("Back");
        GridPane.setConstraints(backBtn, 2, 5);
        backBtn.setAlignment(Pos.BASELINE_CENTER);

        backBtn.setOnAction(e -> {
            primaryStage.close();
            AdminMenu();
                });

        saveButton.setOnAction(e -> {
            String category = categoryComboBox.getValue();
            String title = titleInput.getText();
            String author = authorInput.getText();
            String stockB = stockInput.getText();

            try {
                if (category == null) {
                    throw new IllegalArgumentException("Please select a category.");
                }
                if (title.isEmpty() || author.isEmpty() || stockB.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }
                int stock = Integer.parseInt(stockB);
                if (stock < 1) {
                    throw new IllegalArgumentException("Stock must be at least 1.");
                }

                switch (category) {
                    case "Story Book":
                        User.bookList.add(new HistoryBook(generateId(), title, author, stock));
                        break;
                    case "History Book":
                        User.bookList.add(new StoryBook(generateId(), title, author, stock));
                        break;
                    case "Text Book":
                        User.bookList.add(new TextBook(generateId(), title, author, stock));
                        break;
                }
                bookJumlah++;
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book successfully added to library.");

                // Kembali ke menu admin tanpa menutup stage
                primaryStage.close();
                AdminMenu();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Stock must be a number.");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", ex.getMessage());
            }
        });

        grid.getChildren().addAll(categoryLabel, categoryComboBox, titleLabel, titleInput, authorLabel, authorInput, stockLabel, stockInput, saveButton, backBtn);

        Scene addBookScene = new Scene(grid, 400, 375);
        primaryStage.setScene(addBookScene);
        primaryStage.show();
    }

    @Override
    public void displayBook() {
        GridPane grid = new GridPane();
        Stage stage = new Stage();
        stage.setTitle("Book List");

        TableView<Book> table = new TableView<>();
        ObservableList<Book> books = FXCollections.observableArrayList(User.bookList);

        TableColumn<Book, String> idColumn = new TableColumn<>("Book Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.setItems(books);
        table.getColumns().addAll(idColumn, titleColumn, authorColumn, categoryColumn, stockColumn);

        Button backbtn = new Button("Back");
        backbtn.setAlignment(Pos.BASELINE_RIGHT);
        backbtn.setOnAction(e -> stage.close());

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table, backbtn);

        Scene scene = new Scene(vbox, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void displayStudent() {
        if (stuJumlah != 0) {
            StringBuilder studentInfo = new StringBuilder("List of Registered Students:\n");
            for (Student student : StudentList) {
                studentInfo.append("Name: ").append(student.getName()).append("\nNIM: ").append(student.getNim())
                        .append("\nFakultas: ").append(student.getFaculty()).append("\nProgram: ").append(student.getProgramStudy()).append("\n\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "Registered Students", studentInfo.toString());
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Students Registered", "There is no student registered.");
        }
    }

    public String generateId() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        String formattedID = uuidString.substring(0, 4) + "-" +
                uuidString.substring(9, 13) + "-" +
                uuidString.substring(14, 18);

        return formattedID;
    }

    @Override
    public void menu() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Admin Menu");
        scenetitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button addStudentButton = new Button("Add Student");
        HBox addStudentHB = new HBox(10);
        addStudentHB.setAlignment(Pos.CENTER);
        addStudentHB.getChildren().add(addStudentButton);
        grid.add(addStudentHB, 0, 1);

        Button addBookButton = new Button("Add Book");
        HBox addBookHB = new HBox(10);
        addBookHB.setAlignment(Pos.CENTER);
        addBookHB.getChildren().add(addBookButton);
        grid.add(addBookHB, 1, 1);

        Button displayStudentButton = new Button("Display Registered Student");
        HBox displayStudentHB = new HBox(10);
        displayStudentHB.setAlignment(Pos.CENTER);
        displayStudentHB.getChildren().add(displayStudentButton);
        grid.add(displayStudentHB, 0, 2);

        Button displayBookButton = new Button("Display Available Books");
        HBox displayBookHB = new HBox(10);
        displayBookHB.setAlignment(Pos.CENTER);
        displayBookHB.getChildren().add(displayBookButton);
        grid.add(displayBookHB, 1, 2);

        Button logoutButton = new Button("Logout");
        HBox logoutHB = new HBox(10);
        logoutHB.setAlignment(Pos.CENTER);
        logoutHB.getChildren().add(logoutButton);
        grid.add(logoutHB, 0, 3, 2, 1);

        addStudentButton.setOnAction(e -> addStudent(stage));
        addBookButton.setOnAction(e -> addBook(stage));
        displayStudentButton.setOnAction(e -> displayStudent());
        displayBookButton.setOnAction(e -> displayBook());
        logoutButton.setOnAction(e -> {
            stage.close();
        });

        Scene scene = new Scene(grid, 400, 275);
        stage.setScene(scene);
        stage.show();
    }
}
