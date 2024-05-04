package com.main.books;

public class HistoryBook extends Book {
    private String category = "History";

    public HistoryBook (String bookId, String title, String author, int stock) {
        super(bookId, title, author, stock);
        setCategory(category);
    }
}
