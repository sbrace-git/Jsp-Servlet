package com.example.firstjsp.model;

public class Bookmark {
    private String url;
    private String title;
    private String category;

    public Bookmark() {
    }

    public Bookmark(String url, String title, String category) {
        this.url = url;
        this.title = title;
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
