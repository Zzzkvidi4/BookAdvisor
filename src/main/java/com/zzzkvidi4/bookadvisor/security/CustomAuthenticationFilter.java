package com.zzzkvidi4.bookadvisor.security;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication auth = null;
        try {
            BufferedReader reader = request.getReader();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(reader);
            auth = new UsernamePasswordAuthenticationToken(node.findValue("username").asText(), node.findValue("password").asText());
        }
        catch (IOException e){

        }

        return getAuthenticationManager().authenticate(auth);
    }
}
