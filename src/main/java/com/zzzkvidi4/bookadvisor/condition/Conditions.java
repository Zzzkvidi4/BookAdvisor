package com.zzzkvidi4.bookadvisor.condition;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class Conditions {
    public static Condition uploaded(final ElementsCollection target, final String containerSelector, final String elementSelector){
        return new Condition("uploaded") {
            private int size = target.size();
            @Override
            public boolean apply(WebElement webElement) {
                return size != $(containerSelector).findAll(elementSelector).size();
            }

            public boolean test(WebElement input) {
                return apply(input);
            }
        };
    }
}
