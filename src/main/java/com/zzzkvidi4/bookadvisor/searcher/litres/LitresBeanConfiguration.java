package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LitresBeanConfiguration {
    @Bean
    public BookSearcher litresBookSearcher(){
        return new LitresBookSearcher();
    }

    @Bean
    public ReviewRetriever litresReviewRetriever(){
        return new LitresReviewRetriever();
    }
}
