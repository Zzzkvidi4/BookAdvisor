package com.zzzkvidi4.bookadvisor.searcher.ozon;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.condition.Conditions;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;
import com.zzzkvidi4.bookadvisor.searcher.WebDriverConfigurator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Condition.appear;

public class OzonReviewRetriever implements ReviewRetriever {
    private static final String OZON_DETAIL_URL = "https://www.ozon.ru/context/detail/id/";
    private static final String OZON_REVIEWS_BTN_CSS_CLASS = ".eItemRatingStars_text";
    private static final String OZON_REVIEWS_BLOCK_CSS_CLASS = ".bCommentsList";
    private static final String OZON_SHOW_MORE_COMMENTS_BTN_CSS_CLASS = ".eCommentsList_More";
    private static final String OZON_REVIEW_BLOCK_CSS_CLASS = ".bComment";
    private static final String OZON_REVIEW_TEXT_BLOCK_CSS_CLASS = ".eComment_Text_Text";
    private static final String OZON_REVIEW_DATE_BLOCK_CSS_CLASS = ".eComment_Info_Date";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public List<Review> getReviews(String id) {
        WebDriverConfigurator.setUpFirefoxHeadless();
        List<Review> reviewsObjList = new LinkedList<>();
        open(OZON_DETAIL_URL + id + "/");
        try {
            logger.info("Successfully started reviews retrieving");
            SelenideElement reviewsBtn = $(OZON_REVIEWS_BTN_CSS_CLASS);
            if (!reviewsBtn.exists()) {
                return reviewsObjList;
            }

            reviewsBtn.click();
            $(OZON_REVIEWS_BLOCK_CSS_CLASS).waitUntil(appear, 10000);
            SelenideElement showMoreComments;
            while ((showMoreComments = $(OZON_SHOW_MORE_COMMENTS_BTN_CSS_CLASS)).exists() && showMoreComments.attr("class").contains("mShow")) {
                showMoreComments.scrollTo();
                showMoreComments.click();
            }

            ElementsCollection reviews = $(OZON_REVIEWS_BLOCK_CSS_CLASS).findAll(OZON_REVIEW_BLOCK_CSS_CLASS);

            reviews.forEach(review -> reviewsObjList.add(toReview(review)));
            close();
        }
        catch (Throwable t) {
            logger.severe("Exception occurred while retrieving reviews");
            logger.severe(t.getMessage());
        }
        return reviewsObjList;
    }

    private Review toReview(SelenideElement review){
        Review reviewObj = new Review();
        reviewObj.setResource(Book.Resource.OZON);
        reviewObj.setText(review.find(OZON_REVIEW_TEXT_BLOCK_CSS_CLASS).getText());
        reviewObj.setDate(review.find(OZON_REVIEW_DATE_BLOCK_CSS_CLASS).getText());
        return reviewObj;
    }
}
