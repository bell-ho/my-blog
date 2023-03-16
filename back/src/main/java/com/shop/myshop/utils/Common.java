package com.shop.myshop.utils;

public class Common {
    public static String keywordValidation(String keyword) {
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) {
                keyword = null;
            }
        }
        return keyword;
    }
}
