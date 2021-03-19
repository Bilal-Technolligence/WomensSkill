package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MesgChatActivity extends BaseClass {
    RecyclerView recyclerView;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

    ArrayList<String> chaterId=new ArrayList<>();
    ArrayList<UserAttr> userAttrs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mesg_chat);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Inbox");
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dref.child("ChatList").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                        chaterId.clear();
                        userAttrs.clear();
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            if (ds.exists()) {
                                if (ds.child("receiverId").getValue().equals(userId))
                                {
                                    chaterId.add(ds.child("senderId").getValue().toString());
                                }
                                if(ds.child("senderId").getValue().equals(userId)) {
                                    chaterId.add(ds.child("receiverId").getValue().toString());
                                }
                            }
                        }
                        showChatList();
                    }catch (Exception e){

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            startActivity(new Intent(MesgChatActivity.this , LoginActivity.class));
        }
    }
    private void showChatList() {
        Set<String> set = new HashSet<>(chaterId);
        chaterId.clear();
        chaterId.addAll(set);

        for(int i=0;i<chaterId.size();i++) {
            dref.child("Users").child(chaterId.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        UserAttr p = dataSnapshot.getValue(UserAttr.class);
                        userAttrs.add(p);
                    }
                    recyclerView.setAdapter(new ChatListAdapter(userAttrs , getApplicationContext() , MesgChatActivity.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
    @Override
    int getContentViewId() {
        return R.layout.activity_mesg_chat;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_message;
    }
}