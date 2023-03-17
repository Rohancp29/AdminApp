package com.technolite.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class View_customer extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        // Retrieve the title from the intent extras
        String titlev = getIntent().getStringExtra("title");

        TextView title=findViewById(R.id.titlev);


        // Set the title to the TextView
        title.setText(titlev);


    }
}