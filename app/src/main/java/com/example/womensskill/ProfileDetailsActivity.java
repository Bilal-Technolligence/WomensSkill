package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ProfileDetailsActivity extends BaseClass {
    CardView earning, buyerRequest, shareSkill, shareProduct, products,
            skills, profile, onlineStatus, payment, inviteFriends, support;

    Switch btnSellerMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile_details);
        earning = findViewById(R.id.btnEarning);
        buyerRequest = findViewById(R.id.btnBuyerRequest);
        shareSkill = findViewById(R.id.btnShareSkills);
        shareProduct = findViewById(R.id.btnShareProducts);
        products = findViewById(R.id.btnProducts);
        skills = findViewById(R.id.btnSkills);
        profile = findViewById(R.id.btnprofile);
        onlineStatus = findViewById(R.id.btnOnlineStatus);
        payment = findViewById(R.id.btnPayment);
        inviteFriends = findViewById(R.id.btnInviteFriends);
        support = findViewById(R.id.btnSupport);
        btnSellerMode = findViewById(R.id.btnswitchSeller);
        btnSellerMode.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btnSellerMode.isChecked()){
               startActivity(new Intent(getApplicationContext(),ProfileOffSellerActivity.class));
                }

            }
        } );

        earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EarningActivity.class));
            }
        });
        buyerRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BuyersRequestActivity.class));
            }
        });
        shareProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShareMyProductActivity.class));
            }
        });
        shareSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShareMyProductActivity.class));
            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MySkillOrProducts.class));
            }
        });
        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MySkillOrProducts.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyProfileActivity.class));
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_profile_details;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }

}