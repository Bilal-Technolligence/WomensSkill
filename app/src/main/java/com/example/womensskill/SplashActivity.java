package com.example.womensskill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    Boolean session;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
     String userMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById( R.id.imagelogo );
        Animation animation = AnimationUtils.loadAnimation( getApplicationContext(),R.anim.fade );
        imageView.startAnimation( animation );

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    try {
                        userMode = snapshot.child("UserMode").getValue().toString();
                    }
                    catch (Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Thread timer = new Thread(  ) {
            @Override
            public void run() {
                try {
                    sleep( 5000 );
                    SESSION();

//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class );
//                    startActivity( intent );
//                    finish();
                    super.run();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    public void SESSION(){
        //default value false
        session = Boolean.valueOf(SaveLogin.read(getApplicationContext(),"session","false"));
        if (!session){

            //when user first or logout
            Intent intent = new Intent(getApplicationContext(), HomeBuyer.class );
            startActivity( intent );
            finish();


        }
        else{
            //when user loged in
            //here value true
            //how the value can change true
            if(userMode.equals("Seller")){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else if(userMode.equals("Buyer")){
                Intent intent = new Intent(getApplicationContext(), HomeBuyer.class );
                startActivity( intent );
                finish();

            }


        }
    }
}