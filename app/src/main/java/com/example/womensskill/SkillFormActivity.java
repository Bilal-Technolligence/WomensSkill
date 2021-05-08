package com.example.womensskill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SkillFormActivity extends AppCompatActivity {
    EditText title, description ,tag ,price;
    ImageView img1, img2, img3;
    Spinner category;
    String cat = "";
    private Uri imagePath1, imagePath2, imagePath3;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    CardView save ;
    String tit , des ,t , p;
    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Services");
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_form);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Skill Upload");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.txtTitle);
        price = findViewById(R.id.txtPrice);
        description = findViewById(R.id.txtDes);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        tag = findViewById(R.id.txtTag);
        category = findViewById(R.id.spinner_category);
        save = findViewById(R.id.btn_save);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading please wait..... ");
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat = "Website";
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 4);
            }
        });
        description.setMovementMethod(new ScrollingMovementMethod());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tit = title.getText().toString();
                des = description.getText().toString();
                t = tag.getText().toString();
                p = price.getText().toString();
                if (tit.equals("")) {
                    title.setError("Enter Valid Title");
                    title.setFocusable(true);
                } else if (des.equals("")) {
                    description.setError("Enter Valid Description");
                    description.setFocusable(true);
                }else if (t.equals("")) {
                    tag.setError("Enter Valid Tags");
                    tag.setFocusable(true);
                }else if (p.equals("")) {
                    price.setError("Enter Valid Price");
                    price.setFocusable(true);
                }  else if (count1 == 0 || count2 ==0 ||count3 ==0) {
                    Snackbar.make(view, "Please Select All Image", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                    storageReference.putFile(imagePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();
                            String img1 = downloadUri.toString();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                            storageReference.putFile(imagePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    Uri downloadUri = uriTask.getResult();
                                    String img2 = downloadUri.toString();
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                                    storageReference.putFile(imagePath3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isSuccessful()) ;
                                            Uri downloadUri = uriTask.getResult();
                                            String img3 = downloadUri.toString();
                                            final String push = FirebaseDatabase.getInstance().getReference().child("Services").push().getKey();
                                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            ServiceAttr serviceAttr = new ServiceAttr();
                                            serviceAttr.setId(push);
                                            serviceAttr.setUserId(uid);
                                            serviceAttr.setTitle("I WILL CREATE "+tit.toUpperCase());
                                            serviceAttr.setDecription(des);
                                            serviceAttr.setCategory(cat.toUpperCase());
                                            serviceAttr.setImage1(img1);
                                            serviceAttr.setImage2(img2);
                                            serviceAttr.setImage3(img3);
                                            serviceAttr.setTag(t);
                                            serviceAttr.setPrice(p);
                                            serviceAttr.setStatus("not");
                                            reference.child(push).setValue(serviceAttr);

                                            Toast.makeText(getApplicationContext(), "Service Added", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();

                                        }
                                    });

                                }
                            });


                        }
                    });

                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {
            if (requestCode == 2) {
                imagePath1 = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath1);
                    img1.setImageBitmap(bitmap);
                    count1 = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == 3) {
                imagePath2 = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath2);
                    img2.setImageBitmap(bitmap);
                    count2 = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == 4) {
                imagePath3 = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath3);
                    img3.setImageBitmap(bitmap);
                    count3 = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}