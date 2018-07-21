package com.zzzkvidi4.bookadvisor;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.logging.Logger;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan
public class Application {
    private static Logger logger = Logger.getLogger(Application.class.getName());
    static{
        logger = Logger.getLogger(Application.class.getName());
        logger.info("Started configuration of headless mode");
        WebDriverManager.firefoxdriver().setup();
    }

    public static void main(String... args){
        for(String arg: args){
            String[] argParts = arg.split("=");
            if ((argParts.length == 2) && (argParts[0].equals("-Dremote"))) {
                SystemConfiguration.isRemote = true;
                SystemConfiguration.remoteIp = argParts[1];
            }
        }
        if (SystemConfiguration.isRemote){
            logger.info("Started work in remote mode with remote ip: " + SystemConfiguration.remoteIp);
        } else {
            logger.info("Started work in local mode.");
        }
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CorsFilter corsFilter(){
        Logger.getLogger(getClass().getName()).info("Applying bean to configure corsFilter");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedOrigin("http://localhost:4200/**");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
