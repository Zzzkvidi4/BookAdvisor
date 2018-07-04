package com.zzzkvidi4.bookadvisor.searcher.ozon;


import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OzonBeanConfiguration {
    @Bean
    public BookSearcher ozonBookSearcher(){
        return new OzonBookSearcher();
    }

    @Bean
    public ReviewRetriever ozonReviewRetriever(){
        return new OzonReviewRetriever();
    }
}
