package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.annotation.Logged;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.SearchQuery;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController
public class BookSearchingController {
    private BookSearcherService bookSearcherService;

    @Autowired
    public void setBookSearcherService(BookSearcherService bookSearcherService) {
        this.bookSearcherService = bookSearcherService;
    }

    @Logged(message = "Fetching books")
    @RequestMapping(path = "/books", method = RequestMethod.POST)
    public Collection<Book> getBooks(@RequestBody SearchQuery searchQuery) {
        return bookSearcherService.getBooks(searchQuery);
    }
}
