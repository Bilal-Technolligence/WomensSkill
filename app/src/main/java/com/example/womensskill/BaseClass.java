package com.example.womensskill;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseClass extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    protected BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());


        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);


    }
    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.nav_message) {
            Intent intent=new Intent(this, MesgChatActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_ordermanage) {
            Intent intent=new Intent(this, ManageSkillsOrProductActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_notification) {
            Intent intent=new Intent(this, NotificationsActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_profile) {
            Intent intent=new Intent(this, ProfileDetailsActivity.class);
            startActivity(intent);
            finish();
        }
        else if (itemId == R.id.nav_homebuyer) {
            Intent intent=new Intent(this, HomeBuyer.class);
            startActivity(intent);
            finish();
        }
        else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileDetailsActivity.class));
            finish();
        }
        else if (itemId == R.id.nav_ordermanage) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (itemId == R.id.nav_search) {
            startActivity(new Intent(this, SearchSkilllsActivity.class));
            finish();
        }
        else if (itemId == R.id.nav_notification) {
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }
    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }
    //    void selectBottomNavigationBarItem(int itemId) {
//        Menu menu = navigationView.getMenu();
//        for (int i = 0, size = menu.size(); i < size; i++) {
//            MenuItem item = menu.getItem(i);
//            boolean shouldBeChecked = item.getItemId() == itemId;
//            if (shouldBeChecked) {
//                item.setChecked(true);
//                break;
//            }
//        }
//    }
    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}

