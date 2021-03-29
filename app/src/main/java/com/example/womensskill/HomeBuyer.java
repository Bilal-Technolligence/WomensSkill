package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeBuyer extends BaseClass {
    private Menu menu;
    private MenuItem login,logout,empty;
    Boolean session;
    String uid;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_buyer);
         parentLayout = findViewById(android.R.id.content);

//        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F45FC")));

        TabLayout tabLayout=(TabLayout) findViewById(R.id.summaryTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Services"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1dbf73"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#1dbf73"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager= (ViewPager) findViewById(R.id.summaryPager);
        PagerAdapterBuyerHome pageAdapter=new PagerAdapterBuyerHome(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
//        viewPager.setPageTransformer(true, new ZoomOutTranformer());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu. main , menu);
        this.menu = menu;
        SESSION();
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        switch (item.getItemId()) {

            case R.id. loginMain :
                updateLoginMenu();
                // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            case R.id. logoutMain :
                updateLogoutMenu();
                return true;
            default :
                return super .onOptionsItemSelected(item) ;
        }
    }

    public void SESSION() {
        //default value false
        session = Boolean.valueOf(SaveLogin.read(getApplicationContext(), "session", "false"));
        if (!session) {
            login = menu.findItem(R.id.loginMain);
            logout = menu.findItem(R.id.logoutMain);
            empty = menu.findItem(R.id.empty);
            login.setVisible(true);
            logout.setVisible(false);
            empty.setVisible(false);


        }
        else{
            login = menu.findItem(R.id.loginMain);
            logout = menu.findItem(R.id.logoutMain);
            empty = menu.findItem(R.id.empty);

            login.setVisible(false);
            logout.setVisible(true);
            empty.setVisible(true);

        }
    }
    private void updateLoginMenu() {
        login = menu.findItem(R.id.loginMain);
        logout = menu.findItem(R.id.logoutMain);
        empty = menu.findItem(R.id.empty);

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        login.setVisible(false);
        logout.setVisible(true);
        empty.setVisible(true);

    }
    private void updateLogoutMenu() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        login = menu.findItem(R.id.loginMain);
        logout = menu.findItem(R.id.logoutMain);
        //when user first or logout
        if (!uid.equals("")) {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(parentLayout, "Logout ok", Snackbar.LENGTH_LONG).show();
                    SaveLogin.save(getApplicationContext(), "session", "false");
                    login.setVisible(true);
                    logout.setVisible(false);
                    empty.setVisible(false);
                    //

                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    recreate();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } else {
            Snackbar.make(parentLayout, "You are not login", Snackbar.LENGTH_LONG).show();
        }
    }



    @Override
    int getContentViewId() {
        return R.layout.activity_home_buyer;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_homebuyer;
    }
}