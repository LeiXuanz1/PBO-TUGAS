package com.library.data;

import com.library.books.Book;
import com.library.util.iMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class Student extends User implements iMenu {
    public String name;
    public String nim;
    public String faculty;
    public String programStudy;
    public static ArrayList<String> borrowedBooks = new ArrayList<>();
    public static int borrowedBooksjumlah = 0;
    public static boolean isStudent;
    private static ObservableList<Book> borrowedBooksData = FXCollections.observableArrayList();

    public Student(String name, String nim, String faculty, String programStudy) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.programStudy = programStudy;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getNim() {
        return nim;
    }

    public String getProgramStudy() {
        return programStudy;
    }

    public static String displayInfo(Student student) {
        return student.getName();
    }

    public void showBorrowedBooks() {
        Stage primaryStage = new Stage();
        ObservableList<Book> borrowedBooksData = FXCollections.observableArrayList();

        for (String bookId : borrowedBooks) {
            for (Book book : User.bookList) {
                if (book.getBookId().equals(bookId)) {
                    borrowedBooksData.add(book);
                    break;
                }
            }
        }

        primaryStage.setTitle("Borrowed Books");

        BorderPane borderPane = new BorderPane();

        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableView.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, categoryColumn, durationColumn);

        tableView.setItems(borrowedBooksData);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tableView);

        borderPane.setCenter(vBox);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void choiceBook() {
        Stage primaryStage = new Stage();
        BorderPane root = new BorderPane();

        // TableView setup
        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> bookIdColumn = new TableColumn<>("Book Id");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableView.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, stockColumn);

        ObservableList<Book> bookData = FXCollections.observableArrayList(User.bookList);
        tableView.setItems(bookData);

        root.setCenter(tableView);

        // Input fields setup
        TextField idInput = new TextField();
        idInput.setPromptText("Masukkan ID buku");

        TextField durationInput = new TextField();
        durationInput.setPromptText("Masukkan durasi peminjaman (hari)");

        // Button setup
        Button borrowButton = new Button("Pinjam");
        borrowButton.setOnAction(event -> {
            String inputId = idInput.getText();
            int duration;

            try {
                duration = Integer.parseInt(durationInput.getText());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Input lama peminjaman harus berupa angka.");
                return;
            }

            boolean bookFound = false;
            for (Book book : User.bookList) {
                if (book.getBookId().equals(inputId)) {
                    bookFound = true;
                    if (book.getStock() == 0) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Stock buku kosong!");
                    } else {
                        if (duration < 1 || duration > 14) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Lama peminjaman harus antara 1 sampai 14 hari.");
                            return;
                        }
                        borrowedBooks.add(book.getBookId());
                        book.setDuration(duration);
                        int stockNow = book.getStock();
                        book.setStock(stockNow - 1);
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Buku berhasil dipinjam.");

                        bookData.setAll(User.bookList);
                    }
                    break;
                }
            }
            if (!bookFound) {
                showAlert(Alert.AlertType.ERROR, "Error", "Buku dengan ID tersebut tidak ditemukan.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.close());

        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        btnBox.getChildren().addAll(borrowButton, backBtn);

        // Layout setup
        VBox inputVBox = new VBox(10);
        inputVBox.setPadding(new Insets(10));
        inputVBox.setAlignment(Pos.CENTER);
        inputVBox.getChildren().addAll(idInput, durationInput, btnBox);

        root.setTop(inputVBox);

        // Scene and Stage setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Daftar Buku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void returnBook() {
        // Show borrowed books first
        Stage stage = new Stage();
        ObservableList<Book> borrowedBooksData = FXCollections.observableArrayList();

        BorderPane borderPane = new BorderPane();
        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableView.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, categoryColumn, durationColumn);

        for (String bookId : borrowedBooks) {
            for (Book book : User.bookList) {
                if (book.getBookId().equals(bookId)) {
                    borrowedBooksData.add(book);
                    break;
                }
            }
        }

        tableView.setItems(borrowedBooksData);
        borderPane.setCenter(tableView);

        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Borrowed Books");
        stage.show();

        while (!borrowedBooks.isEmpty()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Kembalikan Buku");
            dialog.setHeaderText("Masukkan ID Buku yang ingin dikembalikan:");
            dialog.setContentText("ID Buku:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String inputId = result.get();
                Iterator<String> iterator = borrowedBooks.iterator();
                boolean bookFound = false;
                while (iterator.hasNext()) {
                    String bookId = iterator.next();
                    if (bookId.equals(inputId)) {
                        for (Book book : User.bookList) {
                            if (book.getBookId().equals(inputId)) {
                                int stockNow = book.getStock();
                                if (stockNow == 1) {
                                    User.bookList.remove(book);
                                } else {
                                    book.setStock(stockNow + 1);
                                }
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Buku dengan judul '" + book.getTitle() + "' berhasil dikembalikan.");
                                iterator.remove();
                                borrowedBooksjumlah--;
                                bookFound = true;
                                borrowedBooksData.remove(book);

                                if (borrowedBooksData.isEmpty()) {
                                    stage.close();
                                }
                                break;
                            }
                        }
                    }
                }
                if (!bookFound) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Buku dengan ID '" + inputId + "' tidak ditemukan.");
                }
            } else {
                stage.close();
                break;
            }
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void menu() {
        Stage stage = new Stage();
        stage.setTitle("Student Menu");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Student Menu");
        scenetitle.setFont(javafx.scene.text.Font.font("Times New Roman", javafx.scene.text.FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button borrowedBooksButton = new Button("Buku terpinjam");
        GridPane.setConstraints(borrowedBooksButton, 0, 1);

        Button borrowBookButton = new Button("Pinjam Buku");
        GridPane.setConstraints(borrowBookButton, 1, 1);

        Button returnBookButton = new Button("Kembalikan Buku");
        GridPane.setConstraints(returnBookButton, 0, 2);

        Button logoutButton = new Button("Logout");
        GridPane.setConstraints(logoutButton, 1, 2);

        grid.getChildren().addAll(borrowedBooksButton, borrowBookButton, returnBookButton, logoutButton);

        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(10));
        mainVBox.getChildren().add(grid);

        borrowBookButton.setOnAction(e -> choiceBook());

        returnBookButton.setOnAction(e -> {
            if (borrowedBooks.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Tidak ada buku yang dipinjam.");
            } else {
                returnBook();
            }
        });

        borrowedBooksButton.setOnAction(e -> showBorrowedBooks());

        logoutButton.setOnAction(e -> stage.close());

        Scene scene = new Scene(mainVBox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
