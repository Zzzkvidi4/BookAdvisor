package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetrieverService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ReviewRetrievingController {
    private Logger logger = Logger.getLogger(getClass().getName());

    private ReviewRetrieverService reviewRetrieverService;

    @Resource(name = "reviewRetrieverService")
    public void setReviewRetrieverService(ReviewRetrieverService reviewRetrieverService) {
        this.reviewRetrieverService = reviewRetrieverService;
    }

    @RequestMapping(path = "/reviews", method = RequestMethod.POST)
    public List<Review> getReviews(@RequestBody Book book){
        logger.info("Started fetching reviews about book: " + book);
        List<Review> reviews = reviewRetrieverService.getReviews(book);
        logger.info("Successfully retrieved " + reviews.size() + " reviews");
        return reviews;
    }
}
