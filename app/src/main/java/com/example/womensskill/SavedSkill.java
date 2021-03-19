package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SavedSkill extends BaseClass {
    RecyclerView recyclerView;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<ServiceAttr> serviceAttrs;
    ProgressDialog progressDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_saved_skill);
        recyclerView = findViewById(R.id.recyclerServices);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        databaseReference.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //profiledata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        serviceAttrs.clear();
                        id = dataSnapshot1.getKey();
                        databaseReference.child("Like").child(uid).child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    ServiceAttr p = dataSnapshot1.getValue(ServiceAttr.class);
                                    serviceAttrs.add(p);
                                }
                                Collections.reverse(serviceAttrs);
                                recyclerView.setAdapter(new ServiceListAdapterBuyer(serviceAttrs, SavedSkill.this));
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Services not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_saved_skill;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}