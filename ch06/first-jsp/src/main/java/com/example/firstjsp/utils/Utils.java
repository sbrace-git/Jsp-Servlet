package com.example.firstjsp.utils;

import java.util.Collection;

public class Utils {
    public static int length(Collection collection) {
        if (null == collection) {
            return 0;
        }
        return collection.size();
    }
}
