import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tugas {
    private static HashMap<String, String> adminUsers = new HashMap<String, String>();
    private static HashMap<String, String> studentUsers = new HashMap<String, String>();

    public static void main(String[] args) {
        // Add sample admin users
        adminUsers.put("admin", "admin");

        // Add sample mahasiswa users
        studentUsers.put("202310370311402", "");

        Scanner objInput = new Scanner(System.in);
        int pilihan = 0;

        while (pilihan != 3) {
            System.out.println("===== Library System =====");
            System.out.println("1. Login as student");
            System.out.println("2. Login as admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            pilihan = objInput.nextInt();
            objInput.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Enter your NIM: ");
                    String nim = objInput.nextLine();
                    if (nim.length() == 15) {
                        System.out.print("Enter your password: ");
                        String password = objInput.nextLine();
                        if (studentUsers.containsKey(nim)) {
                            if (studentUsers.get(nim).equals(password)) {
                                System.out.println("Successful login as Student");
                            } else {
                                System.out.println("User not Found");
                            }
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter your username (Admin) : ");
                    String username = objInput.nextLine();
                    System.out.print("Enter your password (Admin): ");
                    String pass = objInput.nextLine();
                    if (adminUsers.containsKey(username)) {
                        if (adminUsers.get(username).equals(pass)) {
                            System.out.println("Successful login as Admin");
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("adios");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }
}