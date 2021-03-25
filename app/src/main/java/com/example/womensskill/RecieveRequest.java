package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class RecieveRequest extends BaseClass {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<OrderPostAttr> postAttrs;
    ProgressDialog progressDialog;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recieve_request);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Recieved Requests");
        recyclerView = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAttrs = new ArrayList<OrderPostAttr>();

        databaseReference.child("PostOrder").orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    postAttrs.clear();
                    //profiledata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        OrderPostAttr p = dataSnapshot1.getValue(OrderPostAttr.class);
                        postAttrs.add(p);
                    }
                    Collections.reverse(postAttrs);
                    recyclerView.setAdapter(new RecieveRequestListAdapter(postAttrs, getApplicationContext(),RecieveRequest.this));
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Posts not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_recieve_request;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}