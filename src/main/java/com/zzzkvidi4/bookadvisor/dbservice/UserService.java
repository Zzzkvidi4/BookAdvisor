package com.zzzkvidi4.bookadvisor.dbservice;

import com.zzzkvidi4.bookadvisor.model.db.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserService {
    private SessionFactory sessionFactory;
    private PasswordEncoder encoder;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<User> getUsers(){
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login){
        try {
            return sessionFactory
                    .getCurrentSession()
                    .createQuery("from User u where u.username = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        }
        catch (Throwable t){
            return null;
        }
    }

    public void createUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        sessionFactory.getCurrentSession().save(user);
    }
}
