package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("litresSearcher")
public class LitresSearcher {
    private BookSearcher bookSearcher;
    private ReviewRetriever reviewRetriever;

    @Resource(name = "litresBookSearcher")
    public void setBookSearcher(BookSearcher bookSearcher) {
        this.bookSearcher = bookSearcher;
    }

    @Resource(name = "litresReviewRetriever")
    public void setReviewRetriever(ReviewRetriever reviewRetriever){
        this.reviewRetriever = reviewRetriever;
    }
}
