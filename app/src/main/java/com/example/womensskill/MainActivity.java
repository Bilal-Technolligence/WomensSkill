package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends BaseClass {
    CardView performanceStore,skillUpload,performanceSkill,skillMart,productUpload,manageOrder;
    TextView userName;
    ImageView userImage;
String uid;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        userName = findViewById(R.id.txtUserName);
        userImage = findViewById(R.id.userProfile);

        performanceStore =(CardView) findViewById(R.id.performanceCard);
        skillUpload = (CardView) findViewById(R.id.skillCard);
        performanceSkill = findViewById(R.id.performanceSkillCard);
        skillMart = findViewById(R.id.skillmartCard);
        productUpload = findViewById(R.id.productUploadCard);
        manageOrder = findViewById(R.id.manageorderCard);
        performanceStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StorePerformanceActivity.class));

            }
        });

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child( "Users" ).child( uid ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String eName = dataSnapshot.child( "username" ).getValue().toString();
                    userName.setText(String.valueOf( eName ));
                    if(dataSnapshot.child( "imageUrl" ).getValue().toString().equals( " " )) {
//                        Picasso.get().load( dataSnapshot.child( "imageurl" ).getValue().toString() ).into( imageView );
                    }
                    else {
                        Picasso.get().load( dataSnapshot.child( "imageUrl" ).getValue().toString() ).into( userImage );

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}