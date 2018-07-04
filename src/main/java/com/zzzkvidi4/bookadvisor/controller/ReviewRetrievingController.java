package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonReviewRetriever;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class ReviewRetrievingController {
    private Logger logger = Logger.getLogger(getClass().getName());
    private OzonSearcher ozonSearcher;

    @Autowired
    public void setOzonSearcher(OzonSearcher ozonSearcher){
        this.ozonSearcher = ozonSearcher;
    }

    @RequestMapping(path = "/reviews/{id}", method = RequestMethod.GET)
    public List<Review> getReviews(@RequestBody Book book){
        logger.info("Started fetching reviews about book: " + book);
        List<Review> reviews = ozonSearcher.getReviews(book);
        logger.info("Successfully retrieved " + reviews.size() + " reviews");
        return reviews;
    }
}
