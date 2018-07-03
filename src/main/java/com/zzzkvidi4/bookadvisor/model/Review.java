package com.zzzkvidi4.bookadvisor.model;

import java.util.Date;

public class Review {
    private String date;
    private String text;
    private Book.Resource resource;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Book.Resource getResource() {
        return resource;
    }

    public void setResource(Book.Resource resource) {
        this.resource = resource;
    }
}
