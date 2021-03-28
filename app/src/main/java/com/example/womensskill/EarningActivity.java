package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EarningActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Earnings");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_earning;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}