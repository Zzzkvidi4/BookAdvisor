package com.zzzkvidi4.bookadvisor.model;

public class Pair<LEFT, RIGHT> {
    private LEFT left;
    private RIGHT right;

    public LEFT getLeft(){
        return left;
    }

    public RIGHT getRight(){
        return right;
    }

    public Pair(){}

    public Pair(LEFT left, RIGHT right) {
        this.left = left;
        this.right = right;
    }
}
