package com.zzzkvidi4.bookadvisor.searcher.ozon;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcher;
import com.zzzkvidi4.bookadvisor.searcher.WebDriverConfigurator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.*;
import static com.zzzkvidi4.bookadvisor.condition.Conditions.uploaded;

@Component("ozonBookSearcher")
public class OzonBookSearcher extends BookSearcher {
    private static final String OZON_SEARCH_URL = "https://www.ozon.ru/?context=search&group=div_book&text=";

    private static final String OZON_FOOTER_CSS_CLASS = ".ePage_Footer";
    private static final String OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS = ".bSearchResult";
    private static final String OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS = ".bOneTile";
    private static final String OZON_SEARCH_PAGE_DIVIDER_CSS_CLASS = ".eTileSeparator_Total";
    private static final String OZON_BOOK_AUTHOR_CSS_CLASS = ".bOneTileProperty";

    private static final String OZON_BOOK_TITLE_ATTRIBUTE_NAME = "data-name";
    private static final String OZON_BOOK_PRICE_ATTRIBUTE_NAME = "data-price";
    private static final String OZON_BOOK_ID_ATTRIBUTE_NAME = "data-itemid";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void getBooks(String pattern, HashMap<String, Book> storage) {
        WebDriverConfigurator.setUpFirefoxHeadless();
        open(OZON_SEARCH_URL + pattern);
        try {
            logger.info("Successfully opened url: " + OZON_SEARCH_URL + pattern);
            int pages;
            try {
                $(OZON_FOOTER_CSS_CLASS).scrollTo().shouldBe(hidden);
            } catch (Throwable e) {
                pages = 1;
            }
            SelenideElement divider = $(OZON_SEARCH_PAGE_DIVIDER_CSS_CLASS);
            if (divider.exists()) {
                pages = Integer.parseInt(divider.getText());

                for (int i = 2; i < pages; ++i) {
                    /*boolean isUploaded = false;
                    int iteration = 0;
                    while (!isUploaded && (iteration < 10)) {
                        try {
                            $(OZON_FOOTER_CSS_CLASS).scrollTo().waitUntil(
                                    uploaded(
                                            $(OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS).findAll(OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS),
                                            OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS,
                                            OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS
                                    ),
                                    10000
                            );
                            isUploaded = true;
                        } catch (Throwable t) {
                            isUploaded = false;
                            ++iteration;
                            logger.warning("Exception occurred while waiting for uploading. Current iteration is " + iteration);
                            logger.warning(t.getMessage());
                        }
                    }*/
                    int resultListSize = $(OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS).findAll(OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS).size();
                    while (resultListSize == $(OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS).findAll(OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS).size()) {
                        try {
                            $(OZON_FOOTER_CSS_CLASS).shouldBe(
                                    uploaded(
                                            $(OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS).findAll(OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS),
                                            OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS,
                                            OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS
                                    )

                            );
                        } catch (Exception e) {
                            logger.warning(e.getMessage());
                        }
                    }
                }
            }
            ElementsCollection books = $(OZON_SEARCH_RESULT_CONTAINER_CSS_CLASS).findAll(OZON_SEARCH_RESULT_ELEMENT_CSS_CLASS);
            books.forEach(b -> pushBook(toBook(b), storage));
            close();
        }
        catch (Throwable t) {
            logger.severe("Exception occurred while searching for books");
            logger.severe(t.toString());
        }
    }

    private Book toBook(SelenideElement book){
        Book bookObj = new Book();
        bookObj.setTitle(book.attr(OZON_BOOK_TITLE_ATTRIBUTE_NAME));
        SelenideElement authorBlock = book.find(OZON_BOOK_AUTHOR_CSS_CLASS);
        if (authorBlock.exists()) {
            bookObj.setAuthor(authorBlock.getText());
        }
        bookObj.setPrice(new BigDecimal(book.attr(OZON_BOOK_PRICE_ATTRIBUTE_NAME).replace(",", ".")));
        bookObj.addId(Book.Resource.OZON, book.attr(OZON_BOOK_ID_ATTRIBUTE_NAME));
        return bookObj;
    }
}
