package com.technolite.adminapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "my_pref";
    private static final String KEY_CARDVIEWS = "cardviews";
    private static final String KEY_LAST_CARD_TITLE = "last_card_title";
    private static final String KEY_LAST_CARD_IMAGE = "last_card_image";

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveCardViews(List<CardViewItem> cardViewItems) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(cardViewItems);
        editor.putString(KEY_CARDVIEWS, json);
        editor.apply();
    }

    public List<CardViewItem> getCardViews(Context context) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = sharedPreferences.getString(KEY_CARDVIEWS, "");

        Type type = new TypeToken<List<CardViewItem>>() {}.getType();

        List<CardViewItem> cardViewItems = gson.fromJson(json, type);
        if (cardViewItems == null) {
            cardViewItems = new ArrayList<>();
        }

        return cardViewItems;
    }

    public void saveLastCardTitle(String title) {
        editor.putString(KEY_LAST_CARD_TITLE, title);
        editor.apply();
    }

    public String getLastCardTitle() {
        return sharedPreferences.getString(KEY_LAST_CARD_TITLE, "");
    }

    public void saveLastCardImage(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        editor.putString(KEY_LAST_CARD_IMAGE, imageString);
        editor.apply();
    }

    public Bitmap getLastCardImage() {
        String imageString = sharedPreferences.getString(KEY_LAST_CARD_IMAGE, "");
        byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return image;
    }
}