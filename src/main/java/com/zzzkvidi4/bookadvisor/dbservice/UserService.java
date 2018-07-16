package com.zzzkvidi4.bookadvisor.dbservice;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.db.BookUser;
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
                    .createQuery("select new User(u.id, u.username, u.password) from User u where u.username = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        }
        catch (Throwable t){
            return null;
        }
    }

    public boolean createUser(User user){
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            sessionFactory.getCurrentSession().save(user);
            return true;
        }
        catch(Throwable t) {
            return false;
        }
    }

    public User getUserById(int id){
        try {
            return sessionFactory
                    .getCurrentSession()
                    .createQuery("from User u left outer join fetch u.bookUser bu left outer join fetch bu.book where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch(Throwable t){
            return null;
        }
    }

    public void addToFavourites(int id, com.zzzkvidi4.bookadvisor.model.db.Book book){
        com.zzzkvidi4.bookadvisor.model.db.Book dbBook;
        try {
            dbBook = sessionFactory
                    .getCurrentSession()
                    .createQuery("from Book b where b.author = :author and b.title = :title and b.selector = :selector", com.zzzkvidi4.bookadvisor.model.db.Book.class)
                    .setParameter("author", book.getAuthor().toLowerCase())
                    .setParameter("title", book.getTitle().toLowerCase())
                    .setParameter("selector", book.getSelector().toLowerCase())
                    .getSingleResult();
        }
        catch (Throwable t){
            dbBook = new com.zzzkvidi4.bookadvisor.model.db.Book();
            dbBook.setAuthor(book.getAuthor().toLowerCase());
            dbBook.setTitle(book.getTitle().toLowerCase());
            sessionFactory.getCurrentSession().save(dbBook);
        }
        BookUser bookUser = new BookUser();
        bookUser.setUserId(id);
        bookUser.setBookId(dbBook.getBookId());
        sessionFactory.getCurrentSession().save(bookUser);
    }

    public boolean isInFavourite(int id, Book book){
        User user = sessionFactory
                .getCurrentSession()
                .createQuery("from User u left outer join fetch u.bookUser bu left outer join fetch bu.book where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        for(BookUser bookUser: user.getBookUser()){
            com.zzzkvidi4.bookadvisor.model.db.Book dbBook = bookUser.getBook();
            boolean isInFavourite = dbBook.getAuthor().equals(book.getAuthor().toLowerCase()) && dbBook.getTitle().equals(book.getTitle().toLowerCase());
            if (isInFavourite) {
                return true;
            }
        }
        return false;
    }

    public com.zzzkvidi4.bookadvisor.model.db.Book getBookFromFavourite(int userId, int bookId){
        try {
            User user = sessionFactory
                    .getCurrentSession()
                    .createQuery("from User u left outer join fetch u.bookUser bu left outer join fetch bu.book b where u.id = :userId and b.id = :bookId", User.class)
                    .setParameter("userId", userId)
                    .setParameter("bookId", bookId)
                    .getSingleResult();
            if (user.getBookUser().iterator().hasNext()){
                return user.getBookUser().iterator().next().getBook();
            } else {
                return null;
            }
        }
        catch(Throwable t){
            return null;
        }
    }
}
