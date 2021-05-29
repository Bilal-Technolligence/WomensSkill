package com.example.womensskill;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class BaseClass extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    protected BottomNavigationView navigationView;
    Boolean session;
    String userMode;

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
            finish();
        } else if (itemId == R.id.nav_message) {
            Intent intent=new Intent(this, MesgChatActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_ordermanage) {
            Intent intent=new Intent(this, SkillOrderActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_notification) {
            Intent intent=new Intent(this, NotificationsActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_profile) {
            session = Boolean.valueOf(SaveLogin.read(getApplicationContext(),"session","false"));
            if (!session){
                //when user first or logout
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class );
                startActivity( intent );
                finish();

            }
            else{
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            try {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                userMode = snapshot.child("UserMode").child(uid).child("Mode").getValue().toString();
                                if (userMode.equals("Seller")) {
                                    Intent intent = new Intent(getApplicationContext(), ProfileDetailsActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), ProfileOffSellerActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            } catch (Exception e) {
                                Intent intent = new Intent(getApplicationContext(), ProfileOffSellerActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }
        else if (itemId == R.id.nav_homebuyer) {
            Intent intent=new Intent(this, HomeBuyer.class);
            startActivity(intent);
            finish();
        }
        else if (itemId == R.id.nav_search) {
            startActivity(new Intent(this, SearchSkilllsActivity.class));
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

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}

