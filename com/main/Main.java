package com.main;

import com.main.books.HistoryBook;
import com.main.books.StoryBook;
import com.main.books.TextBook;
import com.main.data.Admin;
import com.main.data.Student;
import com.main.data.User;

import java.util.Scanner;

public class Main {
    public void Menu() {
        Scanner scanner = new Scanner(System.in);
        int pilihan;
        Admin admin = new Admin();
        Student student = new Student();

        while (true) {
            System.out.println("===== Library System =====");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Choose Option (1-3): ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    inputNim(scanner);
                    student.menu();
                    break;
                case 2:
                    System.out.print("Enter your username (Admin): ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password (Admin): ");
                    String password = scanner.nextLine();
                    if (Admin.isAdmin(username, password)) {
                        menuAdmin(scanner);
                    } else {
                        System.out.println("Username atau password admin salah.");
                    }
                    break;
                case 3:
                    System.out.println("Thank you, Exiting program");
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }

    public void inputNim(Scanner scanner) {
        System.out.print("Enter your NIM (input 99 untuk kembali): ");
        String nim = scanner.next();
        checkNim(nim);
    }

    public void checkNim(String nim) {
        if (nim.equals("99")) {
            return;
        }

        for (int i = 0; i < Admin.stuJumlah; i++) {
            if(Admin.StudentList[i].nim.equals(nim)) {
                Student.isStudent = true;
                Student.displayInfo(Admin.StudentList[i].name);
                menuStudent();
                return;
            }
        }

        System.out.println("NIM not found.");
    }

    public void menuStudent() {
        Student student = new Student();
        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("==== Student Menu ====");
            System.out.println("1. Buku terpinjam");
            System.out.println("2. Pinjam Buku");
            System.out.println("3. Kembalikan Buku");
            System.out.println("4. Logout");
            System.out.print("Choose Option (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    if (Student.borrowedBooksjumlah == 0) {
                        System.out.println("Tidak ada buku yang dipinjam");
                        System.out.println("Silahkan pinjam buku terlebih dahulu");
                    } else {
                        Student.showBorrowedBooks();
                    }
                    break;
                case 2:
                    int loop = 1;
                    while (loop == 1) {
                        student.choiceBook();
                        System.out.println("Input Id buku yang ingin dipinjam (input 99 untuk back)");
                        System.out.print("Input: ");
                        String inputId = scanner.nextLine();
                        if(inputId.equals("99")) {
                            loop = 0;
                            System.out.println("Kembali ke menu awal...");
                        }
                        for (int i = 0; i < Admin.bookJumlah; i++) {
                            if (User.bookList[i].getBookId().equals(inputId)) {
                                if (User.bookList[i].getStock() == 0) {
                                    System.out.println("Stock buku kosong!");
                                    System.out.println("Silahkan pilih yang lain.");
                                } else {
                                    System.out.println("Berapa lama buku akan dipinjam? (maksimal 14 hari)");
                                    System.out.print("Input lama (hari): ");
                                    int duration = scanner.nextInt();
                                    scanner.nextLine();
                                    User.bookList[i].setDuration(duration);
                                    Student.borrowedBooks[Student.borrowedBooksjumlah] = User.bookList[i].getBookId();
                                    Student.borrowedBooksjumlah++;
                                    int stockNow = User.bookList[i].getStock();
                                    User.bookList[i].setStock(stockNow-1);
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if(Student.borrowedBooksjumlah == 0) {
                        System.out.println("Belum ada buku yang dipilih");
                        System.out.println("Silahkan pilih buku terlebih dahulu");
                    }else {
                        Student.returnBook();
                    }
                    break;
                case 4:
                    student.logout();
                    keepRunning = false; // Menghentikan pengulangan
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public void menuAdmin (Scanner scanner){
        System.out.println("==== Admin Menu ====");
        System.out.println("1. Add Student");
        System.out.println("2. Add Book");
        System.out.println("3. Display Registered Student");
        System.out.println("4. Display Available Books");
        System.out.println("5. Logout");
        System.out.print("Choose Option (1-5): ");
        int pilihan1 = scanner.nextInt();
        scanner.nextLine();

        switch (pilihan1) {
            case 1:
                Admin.addStudent(scanner);
                menuAdmin(scanner);
                break;
            case 2:
                System.out.print("Select book category: ");
                Admin.inputBook(scanner);
                menuAdmin(scanner);
                break;
            case 3:
                Admin.displayStudent();
                menuAdmin(scanner);
                break;
            case 4:
                Admin admin = new Admin();
                admin.displayBook();
                menuAdmin(scanner);
                break;
            case 5:
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    public static boolean exit = false;

    public static void main(String[] args) {
        Main main = new Main();
        User.bookList[Admin.bookJumlah] = new HistoryBook("388c-e681-9152", "Snow Prince", "John Squartz", 4);
        Admin.bookJumlah++;
        User.bookList[Admin.bookJumlah] = new StoryBook("ed90-be30-5cdb", "Autumn Winter", "John William", 0);
        Admin.bookJumlah++;
        User.bookList[Admin.bookJumlah] = new TextBook("d95e-0c4a-9523", "Sakura Swirl", "John Phoenix", 1);
        Admin.bookJumlah++;
        while (!exit) {
            main.Menu();
        }
    }
}