package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;
import com.zzzkvidi4.bookadvisor.searcher.WebDriverConfigurator;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.close;

public class LitresReviewRetriever implements ReviewRetriever {
    private static final String LITRES_DETAIL_URL = "https://www.litres.ru/";
    private static final String LITRES_REVIEWS_LINK_ID = "#book_reviews_link";
    private static final String LITRES_REVIEWS_COUNT_CLASS = ".counts";
    private static final String LITRES_REVIEWS_BLOCK_ID = "#recenses";
    private static final String LITRES_REVIEWS_MORE_BTN_ID = "#recense__more_button";
    private static final String LITRES_REVIEW_ITEM_CLASS = ".recense_body";
    private static final String LITRES_REVIEW_TEXT_CLASS = ".recense_content";
    private static final String LITRES_REVIEW_DATE_CLASS = ".recense_date";

    private static final String LITRES_BOOK_ID_DELIMITER = "~";

    @Override
    public List<Review> getReviews(String id) {
        WebDriverConfigurator.setUpFirefoxHeadless();
        String[] path = id.split(LITRES_BOOK_ID_DELIMITER);
        open(LITRES_DETAIL_URL + path[0] + "/" + path[1] + "/" + "#recenses");
        List<Review> reviewsObjList = new LinkedList<>();
        SelenideElement reviewsCount = $(LITRES_REVIEWS_LINK_ID).find(LITRES_REVIEWS_COUNT_CLASS);
        if (!reviewsCount.exists()){
            close();
            return reviewsObjList;
        }
        SelenideElement reviewsBlock = $(LITRES_REVIEWS_BLOCK_ID);
        SelenideElement reviewsMoreBtn = $(LITRES_REVIEWS_MORE_BTN_ID);
        try {
            while (reviewsMoreBtn.exists() && reviewsMoreBtn.isDisplayed()) {
                reviewsMoreBtn.click();
                reviewsMoreBtn.waitUntil(Condition.visible, 10000);
            }
        }
        catch(Throwable e){
            Logger.getLogger(getClass().getName()).info("Scrolled page up to the end");
        }

        ElementsCollection reviews = reviewsBlock.findAll(LITRES_REVIEW_ITEM_CLASS);
        reviews.forEach(review -> reviewsObjList.add(toReview(review)));
        close();
        return reviewsObjList;
    }

    private Review toReview(SelenideElement review){
        Review reviewObj = new Review();
        reviewObj.setDate(review.find(LITRES_REVIEW_DATE_CLASS).getText());
        reviewObj.setResource(Book.Resource.LITRES);
        reviewObj.setText(review.find(LITRES_REVIEW_TEXT_CLASS).getText());
        return reviewObj;
    }
}
