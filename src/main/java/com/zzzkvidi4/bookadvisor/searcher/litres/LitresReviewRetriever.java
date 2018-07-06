package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;

import java.util.LinkedList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.close;

import static com.zzzkvidi4.bookadvisor.condition.Conditions.uploaded;

public class LitresReviewRetriever implements ReviewRetriever {
    @Override
    public List<Review> getReviews(String id) {
        open(id + "#recenses");
        List<Review> reviewsObjList = new LinkedList<>();
        SelenideElement reviewsBtn = $("#book_reviews_link");
        SelenideElement reviewsCount = reviewsBtn.find(".count");
        if (!reviewsCount.exists()){
            close();
            return reviewsObjList;
        }
        SelenideElement reviewsBlock = $("#recenses");
        SelenideElement reviewsMoreBtn = $("#recense__more_button");
        while (reviewsMoreBtn.exists()){
            reviewsMoreBtn.click();
            reviewsBlock.waitUntil(
                    uploaded(
                            reviewsBlock.findAll(".recense"),
                            "#recenses",
                            ".recense"
                    ),
                    10000
            );
        }

        ElementsCollection reviews = reviewsBlock.findAll(".recense_body");
        reviews.forEach(review -> reviewsObjList.add(toReview(review)));
        close();
        return reviewsObjList;
    }

    private Review toReview(SelenideElement review){
        Review reviewObj = new Review();
        reviewObj.setDate(review.find(".recense_date").getText());
        reviewObj.setResource(Book.Resource.LITRES);
        reviewObj.setText(review.find(".recense_content").getText());
        return reviewObj;
    }
}
