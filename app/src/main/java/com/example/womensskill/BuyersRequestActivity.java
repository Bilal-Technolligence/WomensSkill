package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BuyersRequestActivity extends BaseClass {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<PostAttr> postAttrs;
    ProgressDialog progressDialog;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_buyers_request);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Buyer Request");
        recyclerView = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAttrs = new ArrayList<PostAttr>();

        databaseReference.child("Services").orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        String sid = dataSnapshot1.getKey();
                        databaseReference.child("Services").child(sid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String cat = snapshot.child("category").getValue().toString();
                                databaseReference.child("Posts").orderByChild("category").equalTo(cat).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            postAttrs.clear();
                                            //profiledata.clear();
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                PostAttr p = dataSnapshot1.getValue(PostAttr.class);
                                                postAttrs.add(p);
                                            }
                                            Collections.reverse(postAttrs);
                                            recyclerView.setAdapter(new PostListAdapter(postAttrs, getApplicationContext(),BuyersRequestActivity.this));
                                            progressDialog.dismiss();
                                        } else {
                                            progressDialog.dismiss();
                                          //  Toast.makeText(getApplicationContext(), "Posts not Found!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_buyers_request;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}