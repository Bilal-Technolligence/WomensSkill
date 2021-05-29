package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostRequest extends BaseClass {
    Spinner category;
    String cat = "";
    EditText title , price , time , criteria;
    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Posts");
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Button save;
    String tit , cri ,t , p;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_post_request);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Post Request");
        title = findViewById(R.id.edtTitle);
        price = findViewById(R.id.edtPrice);
        time = findViewById(R.id.edtDays);
        criteria = findViewById(R.id.edtCriteria);
        category = findViewById(R.id.spinner_category);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading please wait..... ");
        save = findViewById(R.id.btnSave);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat = "Embroidery";
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tit = title.getText().toString();
                cri = criteria.getText().toString();
                t = time.getText().toString();
                p = price.getText().toString();
                if (tit.equals("")) {
                    title.setError("Enter Valid Title");
                    title.setFocusable(true);
                } else if (cri.equals("")) {
                    criteria.setError("Enter Valid Criteria");
                    criteria.setFocusable(true);
                }else if (t.equals("")) {
                    time.setError("Enter Valid Duration");
                    time.setFocusable(true);
                }else if (p.equals("")) {
                    price.setError("Enter Valid Price");
                    price.setFocusable(true);
                } else {
                    progressDialog.show();

                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    database.getReference("Users").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String name = snapshot.child("username").getValue().toString();
                                String img = snapshot.child("img").getValue().toString();
                                String push = FirebaseDatabase.getInstance().getReference().child("Posts").push().getKey();
                                PostAttr postAttr = new PostAttr();
                                postAttr.setId(push);
                                postAttr.setUserId(uid);
                                postAttr.setTitle(tit);
                                postAttr.setCriteria(cri);
                                postAttr.setCategory(cat.toUpperCase());
                                postAttr.setTime(t);
                                calendar = Calendar.getInstance();
                                dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                date = dateFormat.format(calendar.getTime());
                                postAttr.setDate(date);
                                postAttr.setUserImg(img);
                                postAttr.setUserName(name);
                                postAttr.setPrice(p);
                                postAttr.setStatus("Active");
                                reference.child(push).setValue(postAttr);

                                Toast.makeText(getApplicationContext(), "Post Created", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(PostRequest.this , PostRequest.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_post_request;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}