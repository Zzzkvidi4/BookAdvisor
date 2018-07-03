package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonReviewRetriever;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class ReviewRetrievingController {
    private Logger logger = Logger.getLogger(getClass().getName());

    @RequestMapping(path = "/reviews/{id}", method = RequestMethod.GET)
    public List<Review> getReviews(@PathVariable String id){
        logger.info("Started fetching reviews by id: " + id);
        OzonReviewRetriever retriever = new OzonReviewRetriever();
        List<Review> reviews = retriever.getReviews(id);
        logger.info("Successfully retrieved " + reviews.size() + " reviews");
        return reviews;
    }
}
