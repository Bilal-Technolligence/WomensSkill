package com.example.womensskill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class ProductFragment extends Fragment {
    RecyclerView recyclerView;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<ServiceAttr> serviceAttrs;
    ProgressDialog progressDialog;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = v.findViewById(R.id.recyclerProducts);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();

        recyclerView.setLayoutManager(new GridLayoutManager( getContext(),2 ));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality( View.DRAWING_CACHE_QUALITY_HIGH);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        databaseReference.child("Products").orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
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
                    recyclerView.setAdapter(new ServiceListAdapter(serviceAttrs, getContext() , getActivity()));
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Services not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
}