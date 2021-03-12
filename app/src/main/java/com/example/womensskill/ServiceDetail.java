package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServiceDetail extends AppCompatActivity {
    ImageView img1, profile;
    TextView frwd, back, name, title, description;
    Button contact , order;
    String id , uid;
    String i1, i2, i3;
    int i=0;
    String Name , UserName , Title , Price;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        id = getIntent().getStringExtra("id");
        img1 = (ImageView) findViewById(R.id.img1);
        profile = (ImageView) findViewById(R.id.imgProfile);
        name = (TextView) findViewById(R.id.txtName);
        title = (TextView) findViewById(R.id.txtTitle);
        description = (TextView) findViewById(R.id.txtDescription);
        frwd = (TextView) findViewById(R.id.btnFrwd);
        back = (TextView) findViewById(R.id.btnBack);
        contact = (Button) findViewById(R.id.btnContact);
        order = (Button) findViewById(R.id.btnOrder);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
        databaseReference.child("Services").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    back.setVisibility(View.GONE);
                    i1 = dataSnapshot.child("image1").getValue().toString();
                    i2 = dataSnapshot.child("image2").getValue().toString();
                    i3 = dataSnapshot.child("image3").getValue().toString();
                    description.setText(dataSnapshot.child("decription").getValue().toString());
                    title.setText(dataSnapshot.child("title").getValue().toString());
                    Title = dataSnapshot.child("title").getValue().toString();
                    Price = dataSnapshot.child("price").getValue().toString();
                    uid = dataSnapshot.child("userId").getValue().toString();
                    Picasso.get().load(i1).into(img1);
                    databaseReference.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                try {
                                    Name = snapshot.child("fullname").getValue().toString();
                                    name.setText(Name);
                                    String img = snapshot.child("img").getValue().toString();
                                    Picasso.get().load(img).into(profile);
                                }
                                catch (Exception e){}
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    databaseReference.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                try {
                                    UserName = snapshot.child("fullname").getValue().toString();
                                }
                                catch (Exception e){}
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    frwd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            i++;
                            if(i==0){
                                Picasso.get().load(i1).into(img1);
                                back.setVisibility(View.GONE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                            if(i==2){
                                Picasso.get().load(i3).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.GONE);
                            }
                            if (i==1){
                                Picasso.get().load(i2).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            i--;
                            if(i==0){
                                Picasso.get().load(i1).into(img1);
                                back.setVisibility(View.GONE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                            if(i==2){
                                Picasso.get().load(i3).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.GONE);
                            }
                            if (i==1){
                                Picasso.get().load(i2).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent o = new Intent(ServiceDetail.this , Chat.class);
                            o.putExtra("chaterId", uid);
                            startActivity(o);
                        }
                    });
                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog.Builder(ServiceDetail.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setMessage("Are you sure you want to order service?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final String push = FirebaseDatabase.getInstance().getReference().child("Order").push().getKey();
                                            OrderAttr orderAttr = new OrderAttr();
                                            orderAttr.setId(push);
                                            orderAttr.setUserId(userId);
                                            orderAttr.setTitle(Title);
                                            orderAttr.setPrice(Price);
                                            orderAttr.setProviderId(uid);
                                            orderAttr.setProviderName(Name);
                                            orderAttr.setUserName(UserName);
                                            orderAttr.setStatus("Active");
                                            orderAttr.setDate(date);
                                            databaseReference.child("Order").child(push).setValue(orderAttr);
                                            Toast.makeText(getApplicationContext() , "Order Created" , Toast.LENGTH_LONG).show();
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}