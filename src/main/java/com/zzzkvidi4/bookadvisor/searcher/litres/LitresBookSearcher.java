package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.WebDriverConfigurator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.close;

import static com.zzzkvidi4.bookadvisor.condition.Conditions.uploaded;

public class LitresBookSearcher implements BookSearcher {
    private static final String LITRES_SEARCH_URL = "https://www.litres.ru/pages/rmd_search_arts/?q=";
    private static final String LITRES_BOOK_LOADER_ID = "#arts_loader_bottom";
    private static final String LITRES_BOOK_LOAD_BTN = "#arts_loader_button";
    private static final String LITRES_SEARCH_RESULT_CONTAINER_ID = "#searchresults";
    private static final String LITRES_SEARCH_RESULT_ELEMENT_CLASS = ".search__item";

    @Override
    public List<Book> getBooks(String pattern) {
        WebDriverConfigurator.setUpFirefoxHeadless();
        open(LITRES_SEARCH_URL + pattern);
        List<Book> booksObjList = new LinkedList<>();
        SelenideElement loader = $(LITRES_BOOK_LOADER_ID);
        SelenideElement loadMoreBtn = $(LITRES_BOOK_LOAD_BTN);
        while (loader.isDisplayed() || loadMoreBtn.isDisplayed()) {
            if (loader.isDisplayed()) {
                loader.scrollTo();
            } else if (loadMoreBtn.isDisplayed()) {
                loadMoreBtn.click();
            }
            loader.waitUntil(
                    uploaded(
                            $(LITRES_SEARCH_RESULT_CONTAINER_ID).findAll(LITRES_SEARCH_RESULT_ELEMENT_CLASS),
                            LITRES_SEARCH_RESULT_CONTAINER_ID,
                            LITRES_SEARCH_RESULT_ELEMENT_CLASS
                    ),
                    10000
            );
        }

        ElementsCollection books = $(LITRES_SEARCH_RESULT_CONTAINER_ID).findAll(LITRES_SEARCH_RESULT_ELEMENT_CLASS);
        books.forEach(book -> booksObjList.add(toBook(book)));

        close();
        return booksObjList;
    }

    private Book toBook(SelenideElement book){
        Book bookObj = new Book();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            Map<String, String> map = mapper.readValue(book.find(".cover").attr("data-obj"), HashMap.class);
            bookObj.setTitle(map.get("alt"));
            bookObj.setAuthor(map.get("author"));
            bookObj.setPrice(new BigDecimal(map.get("price")));
            String[] path = book.find(".art-item__name a").attr("href").split("/");
            int length = path.length;
            bookObj.addId(Book.Resource.LITRES, path[length - 2] + "~" + path[length - 1]);
        }
        catch (IOException e) {
            Logger.getLogger(getClass().getName()).warning("Error occurred while parsing litres json book object");
        }
        return bookObj;
    }
}
