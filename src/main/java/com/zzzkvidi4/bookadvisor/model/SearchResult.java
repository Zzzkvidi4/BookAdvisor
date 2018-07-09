package com.zzzkvidi4.bookadvisor.model;

public class SearchResult {
    private String selector;
    private boolean useLitres;
    private boolean useOzon;

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public boolean isUseLitres() {
        return useLitres;
    }

    public void setUseLitres(boolean useLitres) {
        this.useLitres = useLitres;
    }

    public boolean isUseOzon() {
        return useOzon;
    }

    public void setUseOzon(boolean useOzon) {
        this.useOzon = useOzon;
    }
}
