package com.technolite.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class View_customer extends AppCompatActivity {
    Dialog mDialog;
    FloatingActionButton floatingActionButton;
    ImageView filter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        mDialog = new Dialog(this);
        filter=findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardViewDialog();
            }
        });

        // Retrieve the title from the intent extras
        String titlev = getIntent().getStringExtra("title");
        TextView title=findViewById(R.id.titlev);

        // Set the title to the TextView
        title.setText(titlev);
        floatingActionButton = findViewById(R.id.floating_Btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(View_customer.this, Add_customer.class);
                send.putExtra("title", titlev);
                startActivity(send);
            }
        });
    }

    private void showAddCardViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select any one");
        View view = getLayoutInflater().inflate(R.layout.activity_filter_popup, null);
        builder.setView(view);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}