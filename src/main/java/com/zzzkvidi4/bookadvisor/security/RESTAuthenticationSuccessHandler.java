package com.zzzkvidi4.bookadvisor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzzkvidi4.bookadvisor.dbservice.UserService;
import com.zzzkvidi4.bookadvisor.model.db.User;
import com.zzzkvidi4.bookadvisor.response.ResponseContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RESTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        ResponseContainer<User> response = new ResponseContainer<>();
        response.setAuthenticated(authentication.isAuthenticated());
        response.setData(userService.getUserByLogin(authentication.getName()));
        mapper.writeValue(httpServletResponse.getWriter(), response);
    }
}
