package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMain extends AppCompatActivity {
    RecyclerView  memberList;
    ArrayList<UserAttr> userAttrs;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        userAttrs = new ArrayList<UserAttr>();
        memberList = findViewById(R.id.recycler);
        memberList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userAttrs.clear();
                //profiledata.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UserAttr p = dataSnapshot1.getValue(UserAttr.class);
                    userAttrs.add(p);
                }
                memberList.setAdapter(new MemberAdapter(userAttrs, AdminMain.this));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}