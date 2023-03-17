package com.technolite.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class View_customer extends AppCompatActivity {

    FloatingActionButton floatingActionButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        floatingActionButton = findViewById(R.id.floating_Btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(View_customer.this, Add_customer.class);
                startActivity(send);
            }
        });


        // Retrieve the title from the intent extras
        String titlev = getIntent().getStringExtra("title");
        TextView title=findViewById(R.id.titlev);

        // Set the title to the TextView
        title.setText(titlev);


    }
}