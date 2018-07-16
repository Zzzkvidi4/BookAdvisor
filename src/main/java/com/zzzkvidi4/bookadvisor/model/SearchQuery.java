package com.zzzkvidi4.bookadvisor.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SearchQuery {
    private String selector;
    private Set<Book.Resource> resources = new HashSet<>();

    public SearchQuery(){}

    public SearchQuery(String selector, Book.Resource... resources){
        this.selector = selector;
        this.resources.addAll(Arrays.asList(resources));
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public Set<Book.Resource> getResources(){
        return this.resources;
    }
}
