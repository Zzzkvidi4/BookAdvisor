package com.zzzkvidi4.bookadvisor.searcher;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverConfigurator {
    public static void setUpFirefoxHeadless(){
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        WebDriver driver = new FirefoxDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }
}
