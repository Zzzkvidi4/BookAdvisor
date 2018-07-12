package com.zzzkvidi4.bookadvisor.controller;

import com.zzzkvidi4.bookadvisor.dbservice.UserService;
import com.zzzkvidi4.bookadvisor.model.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }
}
