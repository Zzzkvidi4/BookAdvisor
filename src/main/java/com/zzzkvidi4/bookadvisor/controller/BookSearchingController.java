package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonBookSearcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class BookSearchingController {
    private Logger logger = Logger.getLogger(BookSearchingController.class.getName());

    @RequestMapping(path = "/books_search/{pattern}", method = RequestMethod.GET)
    public List<Book> getBooks(@PathVariable String pattern) {
        logger.info("Started fetching books by \"" + pattern + "\"");
        OzonBookSearcher searcher = new OzonBookSearcher();
        List<Book> books = searcher.getBooks(pattern);
        logger.info("Successfully fetched " + books.size() + " books.");
        return books;
    }
}
