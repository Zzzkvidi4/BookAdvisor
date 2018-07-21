package com.zzzkvidi4.bookadvisor.controller;


import com.zzzkvidi4.bookadvisor.annotation.Logged;
import com.zzzkvidi4.bookadvisor.dbservice.UserService;
import com.zzzkvidi4.bookadvisor.model.Book;
import com.zzzkvidi4.bookadvisor.model.Review;
import com.zzzkvidi4.bookadvisor.model.SearchQuery;
import com.zzzkvidi4.bookadvisor.model.db.User;
import com.zzzkvidi4.bookadvisor.response.ResponseContainer;
import com.zzzkvidi4.bookadvisor.searcher.BookSearcherService;
import com.zzzkvidi4.bookadvisor.searcher.ReviewRetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;
    private ReviewRetrieverService reviewRetrieverService;
    private BookSearcherService bookSearcherService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setReviewRetrieverService(ReviewRetrieverService reviewRetrieverService) {
        this.reviewRetrieverService = reviewRetrieverService;
    }

    @Autowired
    public void setBookSearcherService(BookSearcherService bookSearcherService) {
        this.bookSearcherService = bookSearcherService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public int getUsers(){
        return userService.getUsers().size();
    }

    @Logged(message = "User creation")
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseContainer<Boolean> createUser(@RequestBody User user){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setData(userService.createUser(user));
        return response;
    }

    @Logged(message = "Checking login")
    @RequestMapping(path = "/users/check-login", method = RequestMethod.GET)
    public ResponseContainer<Boolean> checkLoginUnique(@RequestParam("login") String login){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setData(userService.getUserByLogin(login) == null);
        return response;
    }

    @Logged(message = "Getting user by id")
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

    @Logged(message = "Adding to favourite")
    @RequestMapping(path = "/users/{id}/favourites", method = RequestMethod.POST)
    public ResponseContainer<Boolean> addToFavourite(@PathVariable int id, @RequestBody com.zzzkvidi4.bookadvisor.model.db.Book book){
        ResponseContainer<Boolean> response = new ResponseContainer<>();
        response.setAuthenticated(true);
        response.setData(isCurrentUser(id));
        if (response.getData()) {
            userService.addToFavourites(id, book);
        }
        return response;
    }

    @Logged(message = "Checking is in favourite")
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

    @Logged(message = "Retrieving reviews form favourite book")
    @RequestMapping(path = "/users/{userId}/favourites/{bookId}", method = RequestMethod.GET)
    public ResponseContainer<Collection<Review>> getReviewsFromFavourite(@PathVariable(name = "userId") int userId, @PathVariable(name = "bookId") int bookId){
        com.zzzkvidi4.bookadvisor.model.db.Book dbBook = userService.getBookFromFavourite(userId, bookId);
        ResponseContainer<Collection<Review>> response = new ResponseContainer<>();
        response.setAuthenticated(true);
        if ((dbBook == null) || (!isCurrentUser(userId))) {
            return response;
        }
        Collection<Book> books = bookSearcherService.getBooks(new SearchQuery(dbBook.getSelector(), Book.Resource.LITRES, Book.Resource.OZON));
        books.removeIf(book -> !dbBook.getAuthor().equals(book.getAuthor().toLowerCase()) || !dbBook.getTitle().equals(book.getTitle().toLowerCase()));
        List<Review> reviews = new LinkedList<>();
        for(Book book: books){
            reviews.addAll(reviewRetrieverService.getReviews(book));
        }
        response.setData(reviews);
        return response;
    }

    private boolean isCurrentUser(int id){
        User user = userService.getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return user != null && user.getUsername().equals(auth.getName());
    }
}
