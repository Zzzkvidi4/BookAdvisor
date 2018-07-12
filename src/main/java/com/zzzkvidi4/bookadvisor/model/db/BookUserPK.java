package com.zzzkvidi4.bookadvisor.model.db;

import java.io.Serializable;

public class BookUserPK implements Serializable{
    private int userId;
    private int bookId;

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }
}
