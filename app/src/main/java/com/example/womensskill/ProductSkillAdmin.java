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

public class ProductSkillAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    String uid;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<ServiceAttr> serviceAttrs;
    ProgressDialog progressDialog;
    String cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_skill_admin);
        recyclerView = findViewById(R.id.recyclerServices);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();
        uid = getIntent().getStringExtra("id");
        cat = getIntent().getStringExtra("cat");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        databaseReference.child(cat).orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    serviceAttrs.clear();
                    //profiledata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ServiceAttr p = dataSnapshot1.getValue(ServiceAttr.class);
                        serviceAttrs.add(p);
                    }
                    Collections.reverse(serviceAttrs);
                    recyclerView.setAdapter(new ServiceListAdapter(serviceAttrs, getApplicationContext() , ProductSkillAdmin.this));
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}