package com.zzzkvidi4.bookadvisor.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Book {
    public enum Resource {OZON, LITRES;

        @Override
        public String toString() {
            return this.name();
        }
    }
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
        ids.add(new Pair<>(resource, id));
    }

    public void addIds(Collection<Pair<Resource, String>> ids){
        this.ids.addAll(ids);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)){
            return false;
        }
        Book b = (Book)obj;
        return (this.title.toLowerCase() + " " + this.author.toLowerCase()).equals(b.title.toLowerCase() + " " + b.author.toLowerCase());
    }

    @Override
    public int hashCode() {
        return title.hashCode() + author.hashCode();
    }

    @Override
    public String toString() {
        return "Book[" + "author: " + getAuthor() + ", title: " + getTitle() + ", " + "ids: " + getIds().toString() + "]";
    }
}
