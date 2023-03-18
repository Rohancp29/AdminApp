package com.technolite.adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Add_customer extends AppCompatActivity {

    TextView productName;
    EditText cname, cmob, caddress, cpayment;

    TextInputEditText cjoindate;

    RadioGroup group;
    Button submit;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("Product Details");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        productName = findViewById(R.id.product_name);

        // Retrieve the title from the intent extras
        String title = getIntent().getStringExtra("title");
        // Set the title of the activity
        productName.setText(title);

        cname = findViewById(R.id.cname);
        cmob = findViewById(R.id.cnumber);
        caddress = findViewById(R.id.caddress);
        cpayment = findViewById(R.id.cpayment);
        cjoindate = findViewById(R.id.cjoindate);
        submit = findViewById(R.id.csubmit);

        cjoindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Add_customer.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                cjoindate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        String csName = cname.getText().toString();
        String csMob = cmob.getText().toString();
        String csAddress = caddress.getText().toString();
        String csPayment = cpayment.getText().toString();
        String csJoinDate = cjoindate.getText().toString();


        Map<String, Object> user = new HashMap<>();
        user.put("Name", csName);
        user.put("Mobile", csMob);
        user.put("Address", csAddress);
        user.put("Payment", csPayment);
        user.put("Join Date", csJoinDate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(cname.getText().toString()) && TextUtils.isEmpty(cmob.getText().toString()) 
                        && TextUtils.isEmpty(caddress.getText().toString()) && TextUtils.isEmpty(cpayment.getText().toString())
                        && TextUtils.isEmpty(cjoindate.getText().toString())){
                    cname.setError("Please enter your name.");
                    cmob.setError("Please enter your mobile number.");
                    caddress.setError("Please enter your address.");
                    cpayment.setError("Please enter your fees paid.");
                    cjoindate.setError("Please enter your joining date.");
                } else if (cname.getText().toString().isEmpty()) {
                    cname.setError("Please enter your name.");
                } else if (cmob.getText().toString().isEmpty()) {
                    cmob.setError("Please enter your mobile number.");
                } else if (caddress.getText().toString().isEmpty()) {
                    caddress.setError("Please enter your address.");
                } else if (cpayment.getText().toString().isEmpty()) {
                    cpayment.setError("Please enter your fees paid.");
                } else if (cjoindate.getText().toString().isEmpty()) {
                    cjoindate.setError("Please enter your joining date.");
                } else {
                    reference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            reference.child(title).child("Customer"+title).child(cname.getText().toString()).child("Name").setValue(cname.getText().toString());
                            reference.child(title).child("Customer"+title).child(cname.getText().toString()).child("Mobile").setValue(cmob.getText().toString());
                            reference.child(title).child("Customer"+title).child(cname.getText().toString()).child("Address").setValue(caddress.getText().toString());
                            reference.child(title).child("Customer"+title).child(cname.getText().toString()).child("Payment").setValue(cpayment.getText().toString());
                            reference.child(title).child("Customer"+title).child(cname.getText().toString()).child("Join Date").setValue(cjoindate.getText().toString());
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                startActivity(new Intent(getApplicationContext(),View_customer.class));
                finish();
            }
        });
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Add_customer.this,
                (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}