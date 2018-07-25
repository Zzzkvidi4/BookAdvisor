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
import java.util.logging.Logger;

@Service("bookSearcherService")
public class BookSearcherService {
    private HashMap<Book.Resource, BookSearcher> factory = new HashMap<>();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "litresBookSearcher")
    private void setLitresBookSearcher(BookSearcher searcher){
        factory.put(Book.Resource.LITRES, searcher);
    }

    @Resource(name = "ozonBookSearcher")
    private void setOzonBookSearcher(BookSearcher searcher){
        factory.put(Book.Resource.OZON, searcher);
    }

    public Collection<Book> getBooks(SearchQuery query){
        //WebDriverConfigurator.setUpFirefoxHeadless();
        HashMap<String, Book> storage = new LinkedHashMap<>();
        for(Book.Resource resource: query.getResources()){
            try {
                factory.get(resource).getBooks(query.getSelector(), storage);
            }
            catch (Exception e) {
                logger.severe("Exception occurred with book searcher!");
                logger.severe(e.getMessage());
            }
        }
        return storage.values();
    }
}
