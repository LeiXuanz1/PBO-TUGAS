import java.util.Scanner;

public class Student extends User {
    String name, nim, faculty, programStudy;
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
                if(borrowedBooks[j] != null && User.bookList[i].getBookId().equals(borrowedBooks[j])) {
                    System.out.printf("|| %-3d ||  %-17s ||  %-30s ||  %-10s  ||  %-10s ||  %-5d ||%n", no, User.bookList[i].getBookId(), User.bookList[i].getTitle(), User.bookList[i].getAuthor(), User.bookList[i].getCategory(), User.bookList[i].getDuration());
                    no++;
                }
            }
        }
        System.out.println("=============================================================================================================");
    }


    @Override
    public void displayBook() {
        if(Admin.bookJumlah != 0) {
            System.out.println("==========================================================================================================");
            System.out.printf("|| %-3s ||   %-17s||            %-20s ||  %-10s  ||  %-10s || %-5s ||%n", "No", "Book ID", "Title", "Author", "Category", "Stock");
            System.out.println("==========================================================================================================");

            for (int i = 0; i < Admin.bookJumlah; i++) {
                System.out.printf("|| %-3d ||  %-17s ||  %-30s ||  %-10s ||  %-10s ||  %-4d||%n", i + 1, User.bookList[i].getBookId(), User.bookList[i].getTitle(), User.bookList[i].getAuthor(), User.bookList[i].getCategory(), User.bookList[i].getStock());
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
                        if (User.bookList[j].getBookId().equals(inputId)) {
                            int stockNow = User.bookList[j].getStock();
                            User.bookList[j].setStock(stockNow + 1);
                            System.out.println("Successfully to return the book with title '" + User.bookList[j].getTitle() + "'");
                            for (int k = i; k < borrowedBooksjumlah - 1; k++) {
                                borrowedBooks[k] = borrowedBooks[k + 1];
                            }
                            borrowedBooksjumlah--;
                            bookFound = true;
                            break;
                        }
                        if (!bookFound) {
                            System.out.println("Book with ID '" + inputId + "' is not found in the available books.");
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Nama: " + name + ", NIM: " + nim + ", Fakultas: " + faculty + ", Program Studi: " + programStudy;
    }
}