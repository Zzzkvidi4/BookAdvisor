package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.close;

public class LitresReviewRetriever implements ReviewRetriever {
    @Override
    public List<Review> getReviews(String id) {
        open(id + "#recenses");
        close();
        return null;
    }

    private Review toReview(SelenideElement review){
        return null;
    }
}
