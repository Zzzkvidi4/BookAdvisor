package com.zzzkvidi4.bookadvisor.searcher;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Pair;
import com.zzzkvidi4.bookadvisor.model.Review;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Service("reviewRetrieverService")
public class ReviewRetrieverService {
    private HashMap<Book.Resource, ReviewRetriever> factory = new HashMap<>();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "ozonReviewRetriever")
    private void setOzonReviewRetriever(ReviewRetriever retriever){
        factory.put(Book.Resource.OZON, retriever);
    }

    @Resource(name = "litresReviewRetriever")
    private void setLitresReviewRetriever(ReviewRetriever retriever){
        factory.put(Book.Resource.LITRES, retriever);
    }

    public List<Review> getReviews(Book book){
        //WebDriverConfigurator.setUpFirefoxHeadless();
        List<Review> reviews = new LinkedList<>();
        for(Pair<Book.Resource, String> id: book.getIds()){
            try {
                reviews.addAll(factory.get(id.getLeft()).getReviews(id.getRight()));
            }
            catch (Exception e) {
                logger.severe("Exception occurred with review retriever!");
                logger.severe(e.getMessage());
            }
        }
        return reviews;
    }
}
