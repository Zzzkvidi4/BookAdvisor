package com.zzzkvidi4.bookadvisor.model.db;

import javax.persistence.*;

@Entity
@Table(name = "users_books")
@IdClass(BookUserPK.class)
class BookUser {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "book_id")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;
}
