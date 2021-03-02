package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class ManageSkillsOrProductActivity extends AppCompatActivity {
    LinearLayout manageOrder,manageLayout;
    Button btnSOrderManage,btnPOrderManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_skills_or_product);

        manageOrder =(LinearLayout) findViewById(R.id.manageorderLayout);
        manageLayout =(LinearLayout) findViewById(R.id.mainLayout);
        btnSOrderManage =findViewById(R.id.btnSkillOrder);
        btnPOrderManage =findViewById(R.id.btnProductOrder);
        btnSOrderManage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),SkillOrderActivity.class));
    }
});

        btnPOrderManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageOrder.setVisibility(View.GONE);
                manageLayout.setVisibility(View.VISIBLE);
            }
        });
        TabLayout tabLayout=(TabLayout) findViewById(R.id.productTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));
        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1dbf73"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#1dbf73"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPagers= (ViewPager) findViewById(R.id.productSummaryPager);
        SkillPagerAdapter skillPagerAdapter =new SkillPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPagers.setAdapter(skillPagerAdapter);
        viewPagers.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagers.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}