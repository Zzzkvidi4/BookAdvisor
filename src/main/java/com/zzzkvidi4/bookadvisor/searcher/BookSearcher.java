package com.zzzkvidi4.bookadvisor.searcher;

import com.zzzkvidi4.bookadvisor.model.Book;

import java.util.List;

public interface BookSearcher {
    List<Book> getBooks(String pattern);
}
