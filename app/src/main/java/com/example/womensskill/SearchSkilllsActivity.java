package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SearchSkilllsActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_skillls);


        TabLayout tabLayout=(TabLayout) findViewById(R.id.productTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));
        tabLayout.addTab(tabLayout.newTab().setText("Interests"));
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

    @Override
    int getContentViewId() {
        return R.layout.activity_search_skillls;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_search;
    }
}