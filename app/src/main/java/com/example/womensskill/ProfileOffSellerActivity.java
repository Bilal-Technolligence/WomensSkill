package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileOffSellerActivity extends BaseClass {
    Switch btnSellerMode;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile_off_seller);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Seller Mode");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSellerMode = (Switch) findViewById(R.id.btnswitchSeller);
        btnSellerMode.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!btnSellerMode.isChecked()){
                    dref.child("UserMode").setValue( "Buyer" );
                    startActivity(new Intent(getApplicationContext(),ProfileDetailsActivity.class));
                }

            }
        } );
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_profile_off_seller;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}