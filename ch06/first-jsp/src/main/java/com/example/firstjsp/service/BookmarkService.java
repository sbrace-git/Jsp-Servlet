package com.example.firstjsp.service;

import com.example.firstjsp.model.Bookmark;

import java.util.Arrays;
import java.util.List;

public class BookmarkService {

    private static List<Bookmark> bookmarkList;

    static {
        bookmarkList = Arrays.asList(
                new Bookmark("url1", "title1", "category1"),
                new Bookmark("url2", "title2", "category2"),
                new Bookmark("url3", "title3", "category3"),
                new Bookmark("url4", "title4", "category4"),
                new Bookmark("url5", "title5", "category5")
        );
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }
}
