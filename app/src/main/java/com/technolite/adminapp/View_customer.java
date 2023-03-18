package com.technolite.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class View_customer extends AppCompatActivity {
    Dialog mDialog;
    FloatingActionButton floatingActionButton;
    ImageView filter;

    private RecyclerView recyclerView;
    private MemberAdapter adapter;


    private List<Member> memberList=new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        EditText searchname=findViewById(R.id.searchname);
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(View_customer.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MemberAdapter(memberList,View_customer.this);
        recyclerView.setAdapter(adapter);

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



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Product Details");

// Attach a listener to read the data
        databaseReference.child(titlev).child("Customer" + titlev).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get all children at this level
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // Loop through all children

                for (DataSnapshot memberSnapshot : children) {
                    String member_name = memberSnapshot.child("Name").getValue(String.class);
                    String member_addrs = memberSnapshot.child("Address").getValue(String.class);
                    String member_number = memberSnapshot.child("Mobile").getValue(String.class);
                    String member_fee = memberSnapshot.child("Payment").getValue(String.class);
                    String Duration = memberSnapshot.child("Duration").getValue(String.class);

                    String join_date = memberSnapshot.child("Join Date").getValue(String.class);

                    Log.d("TAG", "Name: " + member_name + ", Mobile: " + member_number + ", Address: " + member_addrs + ", Payment: " + member_fee + ", Join Date: " + join_date + ", Duration: " + Duration);


// Calculate reminder status based on join date and duration
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
                        Date join_dat = null;
                        join_dat = dateFormat.parse( memberSnapshot.child("Join Date").getValue(String.class));


                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(join_dat);

                        int duration_in_months = 0;
                        if (Duration.equals("1 Month")) {
                            duration_in_months = 1;
                        }  else if (Duration.equals("3 Months")) {
                            duration_in_months = 3;
                        } else if (Duration.equals("6 Months")) {
                            duration_in_months = 6;
                        } else if (Duration.equals("1 Year")) {
                            duration_in_months = 12;
                        }

                        calendar.add(Calendar.MONTH, duration_in_months);

                        Date expiry_date = calendar.getTime();
                        Date current_date = new Date();

                        String reminder_status = "";

                        long diffInMillies = expiry_date.getTime() - current_date.getTime();
                        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        if (expiry_date.before(current_date)) {
                            reminder_status = "Expired";
                        } else if (diffInDays <= 5) {
                            reminder_status = "Expiring Soon";
                        } else {
                            reminder_status = "Active";
                        }


                        // Do something with the data
                        memberList.add(0, new Member( member_name, member_addrs, member_number, member_fee, Duration, join_date, reminder_status));
                        adapter.notifyItemInserted(0);


                    } catch (ParseException e) {
                        Toast.makeText(View_customer.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });



        searchname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name=searchname.getText().toString();


                List<Member> filterlist=new ArrayList<>();
                for(Member member:memberList){

                    if(member.getMember_name().toLowerCase().contains(charSequence.toString().toLowerCase().trim())||member.getReminder_status().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                        filterlist.add(member);
                    }

                }
                if(filterlist.isEmpty()) {
                    recyclerView.setAdapter(adapter);
                }
                else{
                    recyclerView.setAdapter(new MemberAdapter(filterlist, View_customer.this));


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




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