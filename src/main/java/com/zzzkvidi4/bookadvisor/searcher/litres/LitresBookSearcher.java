package com.zzzkvidi4.bookadvisor.searcher.litres;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;

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
    @Override
    public List<Book> getBooks(String pattern) {
        open("https://www.litres.ru/pages/rmd_search_arts/?q=" + pattern);
        List<Book> booksObjList = new LinkedList<>();
        SelenideElement loader = $("#arts_loader_bottom");
        SelenideElement loadMoreBtn = $("#arts_loader_button");
        while (loader.isDisplayed() || loadMoreBtn.isDisplayed()) {
            if (loader.isDisplayed()) {
                loader.scrollTo();
            } else if (loadMoreBtn.isDisplayed()) {
                loadMoreBtn.click();
            }
            /*loader = $("#arts_loader_bottom");
            loadMoreBtn = $("#arts_loader_button");*/
            loader.waitUntil(
                    uploaded(
                            $("#searchresults").findAll(".search__item"),
                            "#searchresults",
                            ".search__item"
                    ),
                    10000
            );
        }

        ElementsCollection books = $("#searchresults").findAll(".search__item");
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
            bookObj.addId(Book.Resource.LITRES, book.find(".art-item__name a").attr("href"));
        }
        catch (IOException e) {
            Logger.getLogger(getClass().getName()).warning("Error occurred while parsing litres json book object");
        }
        return bookObj;
    }
}
