package com.zzzkvidi4.bookadvisor.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.*;

@Entity
@Table(name = "users_books")
@IdClass(BookUserPK.class)
public class BookUser {
    @Id
    @Column(name = "user_id")
    @JsonIgnore
    private int userId;

    @Id
    @Column(name = "book_id")
    @JsonIgnore
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    @JsonUnwrapped
    private Book book;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }
}
