package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationsActivity extends BaseClass {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<notificationAttr> notificationAttrs;
    NotificationAdapter adapter;
    RecyclerView recyclerView;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notifications);
        navigationView = findViewById(R.id.navigationView);
        recyclerView=findViewById(R.id.recyclerView);
        notificationAttrs = new ArrayList<notificationAttr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(getApplicationContext() , "Noti" , Toast.LENGTH_LONG).show();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("UserMode").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String mode = snapshot.child("Mode").getValue().toString();
                    if(mode.equals("Buyer")){
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.offnavigation);
                        navigationView.getMenu().getItem(3).setChecked(true);
                    }
                    else {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.navigation);
                        navigationView.getMenu().getItem(3).setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("Notification").orderByChild("receiverid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    notificationAttrs.clear();
                    //profiledata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        notificationAttr p = dataSnapshot1.getValue(notificationAttr.class);
                        notificationAttrs.add(p);
                    }
                    Collections.reverse(notificationAttrs);
                    recyclerView.setAdapter(new NotificationAdapter(notificationAttrs,NotificationsActivity.this));
                }
                else
                    Toast.makeText(getApplicationContext() , "No notification found!" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Notification").orderByChild("receiverid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int a = 0;
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            databaseReference.child("Notification").child(dataSnapshot1.getKey()).child("status").setValue("read");
                        }

                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_notifications;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_notification;
    }
}