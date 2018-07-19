package com.zzzkvidi4.bookadvisor.searcher;

import com.codeborne.selenide.WebDriverRunner;
import com.zzzkvidi4.bookadvisor.SystemConfiguration;
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
        if (!SystemConfiguration.isRemote) {
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            WebDriver driver = new FirefoxDriver(options);
            WebDriverRunner.setWebDriver(driver);
        } else {
            try {
                WebDriver firefox = new RemoteWebDriver(new URL("http://" + SystemConfiguration.remoteIp + "/wd/hub"), DesiredCapabilities.firefox());
                WebDriverRunner.setWebDriver(firefox);
            } catch (MalformedURLException e) {
                Logger.getLogger(WebDriverConfigurator.class.getName()).warning(e.getMessage());
            }
        }
    }
}
