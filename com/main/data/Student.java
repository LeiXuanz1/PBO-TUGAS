package com.main.data;

import com.main.util.iMenu;

import java.util.Scanner;

public class Student extends User implements iMenu{
    public String name;
    public String nim;
    String faculty;
    String programStudy;
    public static Object[] borrowedBooks = new Object[15];
    public static int borrowedBooksjumlah = 0;
    public static boolean isStudent;

    public Student(String name, String nim, String faculty, String programStudy) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.programStudy = programStudy;
    }

    public Student() {

    }

    public static void displayInfo(String name) {
        System.out.println("Login as " + name);
    }

    public static void showBorrowedBooks() {
        System.out.println("=============================================================================================================");
        System.out.printf("|| %-3s ||   %-17s||            %-20s ||  %-10s  ||  %-10s || %-5s  ||%n", "No", "Book ID", "Title", "Author", "Category", "Duration");
        System.out.println("=============================================================================================================");
        int no = 1;
        for (int i = 0; i < Admin.bookJumlah; i++) {
            for (int j = 0; j < borrowedBooksjumlah; j++) {
                if(borrowedBooks[j] != null && bookList[i].getBookId().equals(borrowedBooks[j])) {
                    System.out.printf("|| %-3d ||  %-17s ||  %-30s ||  %-10s  ||  %-10s ||  %-5d ||%n", no, bookList[i].getBookId(), bookList[i].getTitle(), bookList[i].getAuthor(), bookList[i].getCategory(), bookList[i].getDuration());
                    no++;
                }
            }
        }
        System.out.println("=============================================================================================================");
    }

    public void choiceBook() {
        if(Admin.bookJumlah != 0) {
            System.out.println("==========================================================================================================");
            System.out.printf("|| %-3s ||   %-17s||            %-20s ||  %-10s  ||  %-10s || %-5s ||%n", "No", "Book ID", "Title", "Author", "Category", "Stock");
            System.out.println("==========================================================================================================");

            for (int i = 0; i < Admin.bookJumlah; i++) {
                System.out.printf("|| %-3d ||  %-17s ||  %-30s ||  %-10s ||  %-10s ||  %-4d||%n", i + 1, bookList[i].getBookId(), bookList[i].getTitle(), bookList[i].getAuthor(), bookList[i].getCategory(), bookList[i].getStock());
            }
            System.out.println("==========================================================================================================");
        }
    }

    public void logout() {
        System.out.println("Logging out...");
    }

    public static void returnBook() {
        Scanner scanner = new Scanner(System.in);
        showBorrowedBooks();
        System.out.print("Input ID Buku yang ingin dihapus (Input 99 untuk back): ");
        String inputId = scanner.next();
        scanner.nextLine();
        if (inputId.equals("99")) {
            return;
        } else {
            boolean bookFound = false;
            for (int i = 0; i < borrowedBooksjumlah; i++) {
                if (borrowedBooks[i].equals(inputId)) {
                    for (int j = 0; j < Admin.bookJumlah; j++) {
                        if (bookList[j].getBookId().equals(inputId)) {
                            int stockNow = bookList[j].getStock();
                            bookList[j].setStock(stockNow + 1);
                            System.out.println("Successfully to return the book with title '" + bookList[j].getTitle() + "'");
                            for (int k = i; k < borrowedBooksjumlah - 1; k++) {
                                borrowedBooks[k] = borrowedBooks[k + 1];
                            }
                            borrowedBooksjumlah--;
                            bookFound = true;
                            break;
                        }
                        if (!bookFound) {
                            System.out.println("Book with ID '" + inputId + "' is not found in the available Book");
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void menu () {
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
}