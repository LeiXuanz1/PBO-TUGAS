import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Mahasiswa {
    private String nama;
    private String nim;
    private String jurusan;

    public Mahasiswa(String nama, String nim, String jurusan) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
    }

    public void tampilDataMahasiswa() {
        System.out.println("Nama: " + nama);
        System.out.println("NIM: " + nim);
        System.out.println("Jurusan: " + jurusan);
        System.out.println();
    }

    public static void tampilUniversitas(String universitas) {
        System.out.println("Universitas " + universitas);
    }
}

public class CodeLab {
    public static void main(String[] args) {
        Scanner objInput = new Scanner(System.in);
        List<Mahasiswa> listMahasiswa = new ArrayList<>();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Data Mahasiswa");
            System.out.println("2. Tampilkan Data Mahasiswa");
            System.out.println("3. Exit");
            System.out.print("Pilihan Anda: ");
            int pilihan = objInput.nextInt();

            if (pilihan == 1) {
                objInput.nextLine();
                System.out.print("Masukkan nama mahasiswa: ");
                String nama = objInput.nextLine();
                System.out.print("Masukkan NIM mahasiswa: ");
                String nim = objInput.nextLine();
                if (nim.length() != 15) {
                    System.out.println("NIM Harus 15 Digit !!!");
                }
                System.out.println("Masukkan jurusan mahasiswa: ");
                String jurusan = objInput.nextLine();
                System.out.println("Data mahasiswa berhasil ditambahkan.");
                Mahasiswa mahasiswa = new Mahasiswa(nama, nim, jurusan);
                listMahasiswa.add(mahasiswa);
            } else if (pilihan == 2) {
                Mahasiswa.tampilUniversitas("Muhammadiyah Malang");
                for (Mahasiswa mahasiswa : listMahasiswa) {
                    mahasiswa.tampilDataMahasiswa();
                }
            } else {
                System.out.println("Dadah");
                break;
            }
        }
    }
}