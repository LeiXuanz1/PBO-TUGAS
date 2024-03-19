import java.util.Scanner;
import java.util.ArrayList;
class Student {
    String nama;
    String faculty;
    String programStudy;
    String nim;

    public Student(String nama, String nim, String faculty, String programStudy) {
        this.nama = nama;
        this.faculty = faculty;
        this.programStudy = programStudy;
        this.nim = nim;
    }

    String[][] bookData = {
            {"388c-e681-9152", "title", "author", "Sejarah", "4"},
            {"ed90-be30-5cdb", "title", "author", "Sejarah", "0"},
            {"d95e-0c4a-9523", "title", "author", "Sejarah", "2"}
    };
    void displayBook() {
        // Menampilkan header
        System.out.println("==================================================================");
        System.out.println("|| No. || Id Buku || Nama Buku || Author || Category || Stock ||");
        System.out.println("==================================================================");
        // Menampilkan data buku
        for (int i = 0; i < bookData.length; i++) {
            System.out.printf("|| %d || %s || %s || %s || %s || %s Stock ||\n",
                    i + 1, bookData[i][0], bookData[i][1], bookData[i][2], bookData[i][3], bookData[i][4]);
        }
    }

    int Bukuterpinjam(Scanner scanner, ArrayList<String> bukuterpinjam) {
        System.out.println("Buku yang sudah dipinjam:");
        for (String bookId : bukuterpinjam) {
            System.out.println("- " + bookId);
        } return 0;
    }

    int pinjamBuku(Scanner scanner, ArrayList<String> bukuDipinjam) {
        System.out.print("Masukkan ID Buku yang ingin Anda pinjam: ");
        String bookId = scanner.nextLine();

        // Mencari ID buku yang dimasukkan dalam data buku yang tersedia
        boolean isValidBook = false;
        for (String[] book : bookData) {
            if (book[0].equals(bookId)) {
                isValidBook = true;
                break;
            }
        }
        if (isValidBook) {
            if (!bukuDipinjam.contains(bookId)) {
                bukuDipinjam.add(bookId);
                System.out.println("Buku berhasil dipinjam.");
            } else {
                System.out.println("Buku sudah dipinjam sebelumnya.");
            }
        } else {
            System.out.println("ID Buku tidak valid.");
        }return 0;
    }


    public void logout() {
        System.out.println("System logout...");
    }

    @Override
    public String toString() {
        return "Nama: " + nama + ", NIM: " + nim + ", Fakultas: " + faculty + ", Program Studi: " + programStudy;
    }

}

class Main {
    static ArrayList<Student> daftarStu = new ArrayList<>();

    public static void Menu() {

        Scanner scanner = new Scanner(System.in);
        int pilihan;

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
                    break;
                case 2:
                    System.out.print("Enter your username (Admin): ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password (Admin): ");
                    String password = scanner.nextLine();
                    if (username.equals(Admin.usernameAdmin) && password.equals(Admin.adminPassword)) {
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

    public static String inputNim(Scanner scanner) {
        System.out.print("Enter your NIM (input 99 untuk kembali): ");
        String nim = scanner.nextLine();
        if (nim.length() != 15) {
            Menu();
        } else {
            menuStudent(daftarStu, scanner);
        }
        return nim;
    }

    public static boolean checkNim(String nim) {
        // Check keberadaan NIM di daftarStu
        for (Student student : daftarStu) {
            if (student.nim.equals(nim)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Menu();
        ArrayList<Student> daftarStu = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String pilihan = scanner.nextLine();

        if (pilihan.equals("1")) {
            String nim = inputNim(scanner);
            if (checkNim(nim)) {
                menuStudent(daftarStu, scanner);
            } else {
                System.out.println("NIM tidak ditemukan");
            }
        }
    }

    public static void menuStudent(ArrayList<Student> daftarStu, Scanner scanner) {
        ArrayList<String> arrStu = new ArrayList<>();
        Student stu = new Student("Nama", "NIM", "Fakultas", "Program Studi");

        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("==== Student Menu ====");
            System.out.println("1. Buku terpinjam");
            System.out.println("2. Pinjam Buku");
            System.out.println("3. Logout");
            System.out.print("Choose Option (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    stu.Bukuterpinjam(scanner, arrStu);
                    break;
                case 2:
                    stu.displayBook();
                    stu.pinjamBuku(scanner, arrStu);
                    menuStudent(daftarStu, scanner);
                    break;
                case 3:
                    stu.logout();
                    keepRunning = false; // Menghentikan pengulangan
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void menuAdmin(Scanner scanner) {
        int pilihan1;
        System.out.println("==== Admin Menu ====");
        System.out.println("1. Add Student");
        System.out.println("2. Display Registered Student");
        System.out.println("3. Logout");
        System.out.print("Choose Option (1-3): ");
        pilihan1 = scanner.nextInt();
        scanner.nextLine();

        switch (pilihan1) {
            case 1:
                Admin.addStudent();
                menuAdmin(scanner);
                break;
            case 2:
                Admin.displayStudent();
                menuAdmin(scanner);
                break;
            case 3:
                Menu();
                break;
        }
    }
}

class Admin {
    static String usernameAdmin = "admin", adminPassword = "admin";

    public static void displayStudent() {
        System.out.println("Daftar Mahasiswa yang Terdaftar:");
        for (Student student : Main.daftarStu) {
            System.out.println(student);
        }
    }

    public static void addStudent(){
        Scanner scanner = new Scanner(System.in);
        Student Stu = new Student("Nama", "NIM", "Fakultas", "Program Studi");
        System.out.print("Masukkan nama mahasiswa: ");
        Stu.nama = scanner.nextLine();
        System.out.print("Masukkan NIM mahasiswa: ");
        Stu.nim = scanner.nextLine();
        if (Stu.nim.length() != 15) {
            System.out.println("Panjang NIM harus 15 angka.");
            return;
        }
        System.out.print("Masukkan Faculty: ");
        Stu.faculty = scanner.nextLine();
        System.out.print("Program Studi: ");
        Stu.programStudy = scanner.nextLine();
        System.out.println("Data mahasiswa berhasil ditambahkan.");
        Main.daftarStu.add(Stu);
    }
}