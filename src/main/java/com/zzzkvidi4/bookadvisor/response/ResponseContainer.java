package com.zzzkvidi4.bookadvisor.response;

public class ResponseContainer<T> {
    private boolean isAuthenticated;
    private T data;

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
