package com.zzzkvidi4.bookadvisor.searcher.ozon;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.condition.Conditions;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Condition.appear;

public class OzonReviewRetriever implements ReviewRetriever {
    @Override
    public List<Review> getReviews(String id) {
        List<Review> reviewsObjList = new LinkedList<>();
        open("https://www.ozon.ru/context/detail/id/" + id + "/");
        SelenideElement reviewsBtn = $(".eItemRatingStars_text");
        if (!reviewsBtn.exists()) {
            return reviewsObjList;
        }

        reviewsBtn.click();
        $(".bCommentsList").waitUntil(appear, 10000);
        SelenideElement showMoreComments;
        while ((showMoreComments = $(".eCommentsList_More")).exists() && showMoreComments.attr("class").contains("mShow")) {
            showMoreComments.scrollTo();
            showMoreComments.click();
        }

        ElementsCollection reviews = $(".bCommentsList").findAll(".bComment");

        reviews.forEach(review -> reviewsObjList.add(toReview(review)));
        close();
        return reviewsObjList;
    }

    private Review toReview(SelenideElement review){
        Review reviewObj = new Review();
        reviewObj.setResource(Book.Resource.OZON);
        reviewObj.setText(review.find(".eComment_Text_Text").getText());
        reviewObj.setDate(review.find(".eComment_Info_Date").getText());
        return reviewObj;
    }
}
