package com.zzzkvidi4.bookadvisor.dbservice;

import com.zzzkvidi4.bookadvisor.model.db.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserService {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<User> getUsers(){
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login){
        return sessionFactory
                .getCurrentSession()
                .createQuery("from User u where u.username = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
    }
}
