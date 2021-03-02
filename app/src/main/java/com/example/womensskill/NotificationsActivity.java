package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NotificationsActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notifications);
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