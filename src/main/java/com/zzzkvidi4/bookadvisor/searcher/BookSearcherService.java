package com.zzzkvidi4.bookadvisor.searcher;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.SearchQuery;
import com.zzzkvidi4.bookadvisor.searcher.litres.LitresBookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.ozon.OzonBookSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service("bookSearcherService")
public class BookSearcherService {
    private HashMap<Book.Resource, BookSearcher> factory = new HashMap<>();

    @Resource(name = "litresBookSearcher")
    private void setLitresBookSearcher(BookSearcher searcher){
        factory.put(Book.Resource.LITRES, searcher);
    }

    @Resource(name = "ozonBookSearcher")
    private void setOzonBookSearcher(BookSearcher searcher){
        factory.put(Book.Resource.OZON, searcher);
    }

    public Collection<Book> getBooks(SearchQuery query){
        WebDriverConfigurator.setUpFirefoxHeadless();
        HashMap<String, Book> storage = new LinkedHashMap<>();
        for(Book.Resource resource: query.getResources()){
            factory.get(resource).getBooks(query.getSelector(), storage);
        }
        return storage.values();
    }
}
