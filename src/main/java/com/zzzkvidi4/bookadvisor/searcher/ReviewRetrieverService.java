package com.zzzkvidi4.bookadvisor.searcher;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Pair;
import com.zzzkvidi4.bookadvisor.model.Review;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service("reviewRetrieverService")
public class ReviewRetrieverService {
    private HashMap<Book.Resource, ReviewRetriever> factory = new HashMap<>();

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
            reviews.addAll(factory.get(id.getLeft()).getReviews(id.getRight()));
        }
        return reviews;
    }
}
