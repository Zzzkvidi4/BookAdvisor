package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.litres.LitresReviewRetriever;
import com.zzzkvidi4.bookadvisor.searcher.litres.LitresSearcher;
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
    private LitresSearcher litresSearcher;

    @Autowired
    public void setOzonSearcher(OzonSearcher ozonSearcher){
        this.ozonSearcher = ozonSearcher;
    }

    @Autowired
    public void setLitresSearcher(LitresSearcher litresSearcher) {
        this.litresSearcher = litresSearcher;
    }

    @RequestMapping(path = "/reviews/{id}", method = RequestMethod.GET)
    public List<Review> getReviews(@PathVariable String id){
        logger.info("Started fetching reviews about book: " + id);
        LitresReviewRetriever retriever = new LitresReviewRetriever();
        List<Review> reviews = retriever.getReviews(id);
        //List<Review> reviews = ozonSearcher.getReviews(book);
        logger.info("Successfully retrieved " + reviews.size() + " reviews");
        return reviews;
    }
}
