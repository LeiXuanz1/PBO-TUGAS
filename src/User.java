abstract class User {
    public static Book[] bookList = new Book[15];

    public void displayBook() {
        if (Admin.bookJumlah != 0) {
            System.out.println("======================================================================================");
            System.out.println("|| No. ||    Id Buku     ||     Nama Buku    ||     Author     || Category || Stock ||");
            System.out.println("======================================================================================");

            for (int i = 0; i < Admin.bookJumlah; i++) {
                System.out.println("|| No. ||    Id Buku     ||     Nama Buku    ||     Author     || Category || Stock ||");
            }
            System.out.println("======================================================================================");
        }
    }
}