package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.SearchResult;
import com.zzzkvidi4.bookadvisor.searcher.litres.LitresSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonBookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @RequestMapping(path = "/books_search", method = RequestMethod.POST)
    public Collection<Book> getBooks(@RequestBody SearchResult searchResult) {
        logger.info("Started fetching books by \"" + searchResult + "\"");
        List<Book> books = new LinkedList<>();
        if (searchResult.isUseLitres()) {
            books.addAll(litresSearcher.getBooks(searchResult.getSelector()));
        }
        if (searchResult.isUseOzon()) {
            books.addAll(ozonSearcher.getBooks(searchResult.getSelector()));
        }
        HashMap<String, Book> booksMap = new LinkedHashMap<>();
        for(Book book: books) {
            String key = (book.getAuthor() + " " + book.getTitle()).toLowerCase();
            if (booksMap.containsKey(key)) {
                booksMap.get(key).addIds(book.getIds());
            } else {
                booksMap.put(key, book);
            }
        }
        logger.info("Successfully fetched " + booksMap.values().size() + " books.");
        return booksMap.values();
    }
}
