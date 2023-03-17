package com.technolite.adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Button addButton;
    private EditText titleEditText;
    private ImageView imageView;

    private SharedPreferencesHelper sharedPreferencesHelper;
    private String lastCardTitle = "";


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference().child("Product Details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this,new ArrayList<>(),this);
        recyclerView.setAdapter(recyclerViewAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference("Product Details");

        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String title = snapshot.child("Title").getValue(String.class);
                String imageUrl = snapshot.child("Logo").getValue(String.class);

                CardViewItem cardViewItem = new CardViewItem(title, imageUrl);
                recyclerViewAdapter.addCardViewItem(cardViewItem);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle changes to existing data
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle removal of data
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle changes to the order of the data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });




        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardViewDialog();
            }
        });


    }
    private void showAddCardViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Card View");

        View view = getLayoutInflater().inflate(R.layout.add_card_view_dialog, null);
        builder.setView(view);

        titleEditText = view.findViewById(R.id.title_edit_text);
        imageView = view.findViewById(R.id.image_view);
        Button selectImageButton = view.findViewById(R.id.select_image_button);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = titleEditText.getText().toString();

                // Upload the image to Firebase Storage
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference imageRef = storageRef.child("images/" + UUID.randomUUID().toString() + ".jpg");
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = imageRef.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                // Store the title and image URL in the Firebase Realtime Database
                                DatabaseReference titleRef = rootRef.child(title);
                                titleRef.child("Title").setValue(title);
                                titleRef.child("Logo").setValue(imageUrl);

                                CardViewItem cardViewItem = new CardViewItem(title, imageUrl);
                                recyclerViewAdapter.addCardViewItem(cardViewItem);
// reload the activity
                                recreate();

                            }
                        });
                    }
                });

            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void saveCardViewDataToFirebase(CardViewItem cardViewItem) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(cardViewItem.getTitle());
        Map<String, Object> cardViewItemValues = new HashMap<>();
        cardViewItemValues.put("Title", cardViewItem.getTitle());
        cardViewItemValues.put("Logo", cardViewItem.getImage());
        databaseReference.setValue(cardViewItemValues);
    }
}