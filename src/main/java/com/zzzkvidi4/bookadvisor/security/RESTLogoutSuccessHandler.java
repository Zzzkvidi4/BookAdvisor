package com.zzzkvidi4.bookadvisor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class RESTLogoutSuccessHandler implements LogoutSuccessHandler {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("Using custom logout success handler: logout as " + (authentication != null ? authentication.getName() : "unknown"));
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
