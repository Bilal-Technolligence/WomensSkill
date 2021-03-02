package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ProfileOffSellerActivity extends AppCompatActivity {
    Switch btnSellerMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_off_seller);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Seller Mode");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        btnSellerMode.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(!btnSellerMode.isChecked()){
//                    startActivity(new Intent(getApplicationContext(),ProfileDetailsActivity.class));
//                }
//
//            }
//        } );
    }
}