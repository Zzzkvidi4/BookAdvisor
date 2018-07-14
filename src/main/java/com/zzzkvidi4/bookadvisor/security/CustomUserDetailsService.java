package com.zzzkvidi4.bookadvisor.security;

import com.zzzkvidi4.bookadvisor.dbservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.zzzkvidi4.bookadvisor.model.db.User user = userService.getUserByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        User.UserBuilder builder = User.builder();
        builder.username(user.getUsername());
        builder.password(user.getPassword());
        builder.roles("USER");
        return builder.build();
    }
}
