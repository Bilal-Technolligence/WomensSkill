package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MesgChatActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mesg_chat);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Inbox");

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