package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.dbservice.UserService;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.db.User;
import com.zzzkvidi4.bookadvisor.response.ResponseContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public int getUsers(){
        return userService.getUsers().size();
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseContainer<Boolean> createUser(@RequestBody User user){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setData(userService.createUser(user));
        return response;
    }

    @RequestMapping(path = "/users/check-login", method = RequestMethod.GET)
    public ResponseContainer<Boolean> checkLoginUnique(@RequestParam("login") String login){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setData(userService.getUserByLogin(login) == null);
        return response;
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public ResponseContainer<User> getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ResponseContainer<User> response = new ResponseContainer<>();
        response.setAuthenticated(auth.isAuthenticated());
        if ((user != null) && (!user.getUsername().equals(auth.getName()))){
            response.setData(null);
        } else {
            response.setData(user);
        }
        return response;
    }

    @RequestMapping(path = "/users/{id}/favourites", method = RequestMethod.POST)
    public ResponseContainer<Boolean> addToFavourite(@PathVariable int id, @RequestBody Book book){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setAuthenticated(true);
        response.setData(isCurrentUser(id));
        if (response.getData()) {
            userService.addToFavourites(id, book);
        }
        return response;
    }

    @RequestMapping(path = "/users/{id}/favourites/is-in", method = RequestMethod.POST)
    public ResponseContainer<Boolean> isInFavourite(@PathVariable int id, @RequestBody Book book){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setAuthenticated(true);
        response.setData(isCurrentUser(id));
        if (response.getData()) {
            response.setData(userService.isInFavourite(id, book));
        }
        return response;
    }

    private boolean isCurrentUser(int id){
        User user = userService.getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return user != null && user.getUsername().equals(auth.getName());
    }
}
