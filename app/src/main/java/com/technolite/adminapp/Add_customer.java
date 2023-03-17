package com.technolite.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class Add_customer extends AppCompatActivity {

    TextView productName;
    EditText custName, custMob, custAddress, custPayment;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        productName = findViewById(R.id.product_name);

        custName = findViewById(R.id.cname);
        custMob = findViewById(R.id.cnumber);
        custAddress = findViewById(R.id.caddress);
        custPayment = findViewById(R.id.cpayment);




    }
}