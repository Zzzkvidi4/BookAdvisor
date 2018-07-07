package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.searcher.litres.LitresSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonBookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class BookSearchingController {
    private Logger logger = Logger.getLogger(BookSearchingController.class.getName());
    private OzonSearcher ozonSearcher;
    private LitresSearcher litresSearcher;

    @Autowired
    public void setOzonSearcher(OzonSearcher ozonSearcher){
        this.ozonSearcher = ozonSearcher;
    }

    @Autowired
    public void setLitresSearcher(LitresSearcher litresSearcher) {
        this.litresSearcher = litresSearcher;
    }

    @RequestMapping(path = "/books_search", method = RequestMethod.GET)
    public List<Book> getBooks(@RequestParam(name = "query") String query) {
        logger.info("Started fetching books by \"" + query + "\"");
        //List<Book> books = ozonSearcher.getBooks(query);
        List<Book> books = litresSearcher.getBooks(query);
        logger.info("Successfully fetched " + books.size() + " books.");
        return books;
    }
}
