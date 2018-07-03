package com.zzzkvidi4.bookadvisor.searcher;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;

import java.util.List;

public interface ReviewRetriever {
    List<Review> getReviews(String id);
}
