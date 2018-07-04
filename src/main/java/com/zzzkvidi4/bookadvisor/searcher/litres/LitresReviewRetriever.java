package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;

import java.util.List;

public class LitresReviewRetriever implements ReviewRetriever {
    @Override
    public List<Review> getReviews(String id) {
        return null;
    }

    private Review toReview(SelenideElement review){
        return null;
    }
}
