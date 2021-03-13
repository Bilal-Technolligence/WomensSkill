package com.example.womensskill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class OrderCompletedFragment extends Fragment {
    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<OrderAttr> orderAttrs;
    ProgressDialog progressDialog;
    TextView txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ordercompleted, container, false);
        try {
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            recyclerView = v.findViewById(R.id.recyclerOrders);
            txt = v.findViewById(R.id.txt);
            txt.setVisibility(View.GONE);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading..... ");
            progressDialog.show();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            orderAttrs = new ArrayList<OrderAttr>();
            databaseReference.child("Order").orderByChild("status").equalTo("Completed").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        orderAttrs.clear();
                        //profiledata.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String id = dataSnapshot1.child("providerId").getValue().toString();
                            String id2 = dataSnapshot1.child("userId").getValue().toString();
                            if (id.equals(uid) || id2.equals(uid)) {
                                OrderAttr p = dataSnapshot1.getValue(OrderAttr.class);
                                orderAttrs.add(p);
                            }
                        }
                        Collections.reverse(orderAttrs);
                        recyclerView.setAdapter(new OrderListAdapter(orderAttrs, getContext()));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            startActivity(new Intent(getContext() , LoginActivity.class));
        }

        return v;
    }
}