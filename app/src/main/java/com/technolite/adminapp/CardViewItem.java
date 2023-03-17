package com.technolite.adminapp;

import android.graphics.drawable.Drawable;

public class CardViewItem {
    private String title;
    private String image;

    public CardViewItem(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
