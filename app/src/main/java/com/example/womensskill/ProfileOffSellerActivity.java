package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ProfileOffSellerActivity extends BaseClass {
    Switch btnSellerMode;
    String uid;
    TextView userName, balance;
    ImageView userImage;
    int count = 0;
    private Uri imagePath;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    CardView manageOrder,btnPostRequest;
    TextView savedSkill , savesProduct;

    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile_off_seller);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Seller Mode");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSellerMode = (Switch) findViewById(R.id.btnswitchSeller);
        manageOrder = findViewById(R.id.btnManageOrder);
        btnPostRequest = findViewById(R.id.btnPostRequest);

        manageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileOffSellerActivity.this , SkillOrderActivity.class));

            }
        });

        btnPostRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileOffSellerActivity.this , PostRequest.class));

            }
        });
        savedSkill = findViewById(R.id.layoutSavedSkill);
        savesProduct = findViewById(R.id.layoutSavedProduct);
        savedSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileOffSellerActivity.this , SavedSkill.class));

            }
        });
        savesProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileOffSellerActivity.this , SavedProduct.class));

            }
        });
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = findViewById(R.id.txtName);
        balance = findViewById(R.id.txtPrice);
        userImage = findViewById(R.id.txtImg);

        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        String eName = dataSnapshot.child("username").getValue().toString();
                        String bal = dataSnapshot.child("balance").getValue().toString();
                        String cur = dataSnapshot.child("currency").getValue().toString();
                        userName.setText(String.valueOf(eName));
                        balance.setText("Your balance is " + bal + cur);
                        if (dataSnapshot.child("img").getValue().toString().equals(" ")) {
                            String firstLetter = eName.substring(0, 1);
                            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                            int color = generator.getRandomColor();

                            TextDrawable drawable = TextDrawable.builder()
                                    .buildRound(firstLetter, color); // radius in px
                            userImage.setImageDrawable(drawable);
                        } else {
                            Picasso.get().load(dataSnapshot.child("img").getValue().toString()).into(userImage);

                        }
                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        btnSellerMode.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!btnSellerMode.isChecked()){
                    dref.child("UserMode").child(uid).child("Mode").setValue( "Seller" );
                    startActivity(new Intent(getApplicationContext(),ProfileDetailsActivity.class));
                    finish();
                }

            }
        } );
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath);
                userImage.setImageBitmap(bitmap);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        String img1 = downloadUri.toString();
                        databaseReference.child("Users").child(uid).child("img").setValue(img1);

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    int getContentViewId() {
        return R.layout.activity_profile_off_seller;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}