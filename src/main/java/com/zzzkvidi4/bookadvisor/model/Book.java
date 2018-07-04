package com.zzzkvidi4.bookadvisor.model;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Book {
    public enum Resource {OZON, CHITAI_GOROD, LITRES}
    private String author;
    private String title;
    private BigDecimal price;
    private List<Pair<Resource, String>> ids = new LinkedList<>();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Pair<Resource, String>> getIds() {
        return ids;
    }

    public void addId(Resource resource, String id){
        ids.add(Pair.of(resource, id));
    }
}
