package com.zzzkvidi4.bookadvisor.dbservice;

import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.db.BookUser;
import com.zzzkvidi4.bookadvisor.model.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("userService")
@Transactional
public class UserService {
    private PasswordEncoder encoder;
    private EntityManager entityManager;

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<User> getUsers(){
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login){
        try {
            return entityManager
                    .createQuery("select new User(u.id, u.username, u.password) from User u where u.username = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        }
        catch (Throwable t){
            return null;
        }
    }

    @Transactional
    public boolean createUser(User user){
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            entityManager.persist(user);
            return true;
        }
        catch(Throwable t) {
            return false;
        }
    }

    public User getUserById(int id){
        try {
            return entityManager
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
            dbBook = entityManager
                    .createQuery("from Book b where lower(b.author) = :author and lower(b.title) = :title and lower(b.selector) = :selector", com.zzzkvidi4.bookadvisor.model.db.Book.class)
                    .setParameter("author", book.getAuthor().toLowerCase())
                    .setParameter("title", book.getTitle().toLowerCase())
                    .setParameter("selector", book.getSelector().toLowerCase())
                    .getSingleResult();
        }
        catch (Throwable t){
            dbBook = book;
            entityManager.persist(dbBook);
        }
        BookUser bookUser = new BookUser();
        bookUser.setUserId(id);
        bookUser.setBookId(dbBook.getBookId());
        entityManager.persist(bookUser);
    }

    public boolean isInFavourite(int id, Book book){
        User user = entityManager
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
            User user = entityManager
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
