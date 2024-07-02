package com.library.data;

import com.library.books.Book;
import com.library.books.HistoryBook;
import com.library.books.StoryBook;
import com.library.books.TextBook;

import java.util.ArrayList;

public class User {
    public static ArrayList<Book> bookList = new ArrayList<>();

    public void displayBook() {
        User.bookList.add(new HistoryBook("388c-e681-9152", "Snow Prince", "John Squartz", 4));
        Admin.bookJumlah++;
        User.bookList.add(new StoryBook("ed90-be30-5cdb", "Autumn Winter", "John William", 0));
        Admin.bookJumlah++;
        User.bookList.add(new TextBook("d95e-0c4a-9523", "Sakura Swirl", "John Phoenix", 1));
        Admin.bookJumlah++;
    }
}