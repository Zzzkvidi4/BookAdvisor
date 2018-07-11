package com.zzzkvidi4.bookadvisor.searcher;

import com.zzzkvidi4.bookadvisor.model.Book;

import java.util.HashMap;
import java.util.List;

public abstract class BookSearcher {
    public abstract void getBooks(String pattern, HashMap<String, Book> storage);

    protected void pushBook(Book book, HashMap<String, Book> storage){
        String key = (book.getAuthor() + " " + book.getTitle()).toLowerCase();
        if (storage.containsKey(key)) {
            storage.get(key).addIds(book.getIds());
        } else {
            storage.put(key, book);
        }
    }
}
