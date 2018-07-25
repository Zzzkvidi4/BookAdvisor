package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.annotation.Logged;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetrieverService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ReviewRetrievingController {
    private ReviewRetrieverService reviewRetrieverService;

    @Resource(name = "reviewRetrieverService")
    public void setReviewRetrieverService(ReviewRetrieverService reviewRetrieverService) {
        this.reviewRetrieverService = reviewRetrieverService;
    }

    @Logged(message = "Reviews retrieving")
    @RequestMapping(path = "/reviews", method = RequestMethod.POST)
    public List<Review> getReviews(@RequestBody Book book){
        return reviewRetrieverService.getReviews(book);
    }
}
