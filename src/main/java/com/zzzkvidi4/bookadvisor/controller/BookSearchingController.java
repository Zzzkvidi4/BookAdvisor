package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.SearchQuery;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController
public class BookSearchingController {
    private Logger logger = Logger.getLogger(BookSearchingController.class.getName());
    private BookSearcherService bookSearcherService;

    @Autowired
    public void setBookSearcherService(BookSearcherService bookSearcherService) {
        this.bookSearcherService = bookSearcherService;
    }

    @RequestMapping(path = "/books", method = RequestMethod.POST)
    public Collection<Book> getBooks(@RequestBody SearchQuery searchQuery) {
        logger.info("Started fetching books by \"" + searchQuery + "\"");
        Collection<Book> books = bookSearcherService.getBooks(searchQuery);
        logger.info("Successfully fetched " + books.size() + " books.");
        return books;
    }
}
