package com.zzzkvidi4.bookadvisor.searcher;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class WebDriverConfigurator {
    public static void setUpFirefoxHeadless() {
        /*FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        WebDriver driver = new FirefoxDriver(options);
        WebDriverRunner.setWebDriver(driver);*/
        Logger.getLogger(WebDriverConfigurator.class.getName()).info(Configuration.remote);
        try {
            WebDriver firefox = new RemoteWebDriver(new URL("http://selenium_hub:4444/wd/hub"), DesiredCapabilities.firefox());
            WebDriverRunner.setWebDriver(firefox);
        }
        catch(MalformedURLException e){
            Logger.getLogger(WebDriverConfigurator.class.getName()).warning(e.getMessage());
        }
        //Configuration.remote = "http://localhost:4444/wd/hub";
        /*Configuration.browserCapabilities = DesiredCapabilities.firefox();
        Configuration.headless = true;*/
    }
}
