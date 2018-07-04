package com.zzzkvidi4.bookadvisor.searcher.ozon;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("ozonSearcher")
public class OzonSearcher {
    private BookSearcher bookSearcher;
    private ReviewRetriever reviewRetriever;

    @Resource(name = "ozonBookSearcher")
    public void setBookSearcher(BookSearcher bookSearcher){
        this.bookSearcher = bookSearcher;
    }

    @Resource(name = "ozonReviewRetriever")
    public void setReviewRetriever(ReviewRetriever reviewRetriever){
        this.reviewRetriever = reviewRetriever;
    }

    public List<Book> getBooks(String query){
        return bookSearcher.getBooks(query);
    }

    public List<Review> getReviews(Book book){
        List<Review> reviews = new LinkedList<>();
        for(Pair<Book.Resource, String> pair: book.getIds()){
            if (pair.getLeft().equals(Book.Resource.OZON)) {
                reviews.addAll(reviewRetriever.getReviews(pair.getRight()));
            }
        }
        return reviews;
    }
}
