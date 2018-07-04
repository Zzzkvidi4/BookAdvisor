package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;

import java.util.List;

public class LitresBookSearcher implements BookSearcher {
    @Override
    public List<Book> getBooks(String pattern) {
        return null;
    }

    private Book toBook(SelenideElement book){
        return null;
    }
}
