import java.util.Scanner;
import java.util.UUID;

public class Admin extends User {
    private static String username = "admin";
    private static String password = "admin";

    public static Student[] StudentList = new Student[15];

    public static int bookJumlah = 0;
    public static int stuJumlah = 0;

    public static boolean isAdmin(String username, String password) {
        return Admin.username.equals(username) && Admin.password.equals(password);
    }

    public static void addStudent(Scanner scanner) {
        System.out.print("Masukkan nama mahasiswa: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan NIM mahasiswa: ");
        String nim = scanner.nextLine();
        if (nim.length() != 15) {
            System.out.println("Panjang NIM harus 15 angka.");
            return;
        } else if (!nim.matches("\\d+")) {
            System.out.println("NIM tidak valid");
            return;
        }
        System.out.print("Masukkan Fakultas: ");
        String faculty = scanner.nextLine();
        System.out.print("Program Studi: ");
        String programStudy = scanner.nextLine();

        StudentList[stuJumlah] = new Student(nama, nim, faculty, programStudy);
        stuJumlah++;

        System.out.println("Data mahasiswa berhasil ditambahkan.");
    }


    public static void inputBook(Scanner scanner) {
        System.out.println("Pilih jenis buku:");
        System.out.println("1. Story Book");
        System.out.println("2. History Book");
        System.out.println("3. Text Book");
        System.out.print("Choose category (1-3): ");
        int choice = scanner.nextInt();
        String title, author;
        int stock;
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter book title: ");
                title = scanner.nextLine();
                System.out.print("Enter author: ");
                author = scanner.nextLine();
                System.out.print("Enter book stock: ");
                stock = scanner.nextInt();
                if (stock < 1) {
                    System.out.println("Minimum stock is 1");
                    return;
                }
                User.bookList[bookJumlah] = new StoryBook(generateId(), title, author, stock);
                bookJumlah++;
                System.out.println("Book succesfully addedd to library");
                break;
            case 2:
                System.out.print("Enter book title: ");
                title = scanner.nextLine();
                System.out.print("Enter author: ");
                author = scanner.nextLine();
                System.out.print("Enter book stock: ");
                stock = scanner.nextInt();
                if (stock < 1) {
                    System.out.println("Minimum stock is 1");
                    return;
                }
                User.bookList[bookJumlah] = new HistoryBook(generateId(), title, author, stock);
                bookJumlah++;
                System.out.println("Book succesfully addedd to library");
                break;
            case 3:
                System.out.print("Enter book title: ");
                title = scanner.nextLine();
                System.out.print("Enter author: ");
                author = scanner.nextLine();
                System.out.print("Enter book stock: ");
                stock = scanner.nextInt();
                if (stock < 1) {
                    System.out.println("Minimum stock is 1");
                    return;
                }
                User.bookList[bookJumlah] = new TextBook(generateId(), title, author, stock);
                bookJumlah++;
                System.out.println("Book succesfully addedd to library");
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }
    }


    @Override
    public void displayBook() {
        if(Admin.bookJumlah != 0) {
            System.out.println("=====================================================================================================");
            System.out.println("|| No  ||      Book Id       ||         Title          ||     Author      ||  Category  ||  Stock  || ");
            System.out.println("=====================================================================================================");
            for (int i = 0; i < Admin.bookJumlah; ++i) {
                System.out.printf("||  %-3d ||  %-17s ||    %-18s ||  %-10s   ||  %-10s||    %-4d ||%n", i + 1, User.bookList[i].getBookId(), User.bookList[i].getTitle(), User.bookList[i].getAuthor(), User.bookList[i].getCategory(), User.bookList[i].getStock());
            }
            System.out.println("=====================================================================================================");
        }
    }


    public static void displayStudent() {
        if(stuJumlah != 0) {
            System.out.println("List of Registered Students: ");
            for (int i = 0; i < stuJumlah; i++) {
                System.out.print("Name: " + StudentList[i].name + "\nFaculty: " + StudentList[i].faculty + "\nNIM: " + StudentList[i].nim + "\nProgram: " + StudentList[i].programStudy + "\n\n");
            }
        }else {
            System.out.println("There is no student registered.");
        }
    }

    private static String generateId() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        String formattedID = uuidString.substring(0, 4) + "-" +
                uuidString.substring(9, 13) + "-" +
                uuidString.substring(14, 18);

        return formattedID;
    }
}