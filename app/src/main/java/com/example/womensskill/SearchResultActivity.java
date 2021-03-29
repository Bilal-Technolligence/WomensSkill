package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResultActivity extends BaseClass {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<ServiceAttr> serviceAttrs;
    ProgressDialog progressDialog;
    String id, cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_result);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        id = getIntent().getStringExtra("id");
        cat = getIntent().getStringExtra("cat");
        databaseReference.child(id).orderByChild("category").equalTo(cat).addValueEventListener(new ValueEventListener() {
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
                    if (id.equals("Products")) {
                        recyclerView.setAdapter(new ProductListAdapterBuyer(serviceAttrs, getApplicationContext()));
                        progressDialog.dismiss();
                    }else {
                        recyclerView.setAdapter(new ServiceListAdapterBuyer(serviceAttrs, getApplicationContext()));
                        progressDialog.dismiss();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Ads not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_search;
    }
}