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

public class ProductDetail extends AppCompatActivity {
    ImageView img1, profile;
    TextView frwd, back, name, title, price, quantity;
    Button contact, order;
    String id, uid , productId;
    String i1, i2, i3;
    int i = 0;
    String Name, UserName, Title, Price;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String img;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        id = getIntent().getStringExtra("id");
        img1 = (ImageView) findViewById(R.id.img1);
        profile = (ImageView) findViewById(R.id.imgProfile);
        name = (TextView) findViewById(R.id.txtName);
        title = (TextView) findViewById(R.id.txtTitle);
        price = (TextView) findViewById(R.id.txtPrice);
        frwd = (TextView) findViewById(R.id.btnFrwd);
        back = (TextView) findViewById(R.id.btnBack);
        quantity = (TextView) findViewById(R.id.txtQuantity);
        contact = (Button) findViewById(R.id.btnContact);
        order = (Button) findViewById(R.id.btnOrder);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
        databaseReference.child("Products").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    back.setVisibility(View.GONE);
                    i1 = dataSnapshot.child("image1").getValue().toString();
                    i2 = dataSnapshot.child("image2").getValue().toString();
                    i3 = dataSnapshot.child("image3").getValue().toString();
                    price.setText(dataSnapshot.child("price").getValue().toString());
                    Title = dataSnapshot.child("title").getValue().toString();
                    productId = dataSnapshot.child("id").getValue().toString();
                    Price = dataSnapshot.child("price").getValue().toString();
                    title.setText(dataSnapshot.child("title").getValue().toString());
                    quantity.setText(dataSnapshot.child("quantity").getValue().toString());
                    uid = dataSnapshot.child("userId").getValue().toString();
                    Picasso.get().load(i1).into(img1);
                    databaseReference.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                try {
                                    Name = snapshot.child("fullname").getValue().toString();
                                    name.setText(Name);
                                    img = snapshot.child("img").getValue().toString();
                                    Picasso.get().load(img).into(profile);
                                } catch (Exception e) {
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    try {
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if(uid.equals(userId)){
                            contact.setVisibility(View.GONE);
                            order.setVisibility(View.GONE);
                        }
                        databaseReference.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    try {
                                        UserName = snapshot.child("fullname").getValue().toString();
                                    } catch (Exception e) {
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } catch (Exception e) {
                        Intent o = new Intent(ProductDetail.this, LoginActivity.class);
                        startActivity(o);
                    }
                    frwd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            i++;
                            if (i == 0) {
                                Picasso.get().load(i1).into(img1);
                                back.setVisibility(View.GONE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                            if (i == 2) {
                                Picasso.get().load(i3).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.GONE);
                            }
                            if (i == 1) {
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
                            if (i == 0) {
                                Picasso.get().load(i1).into(img1);
                                back.setVisibility(View.GONE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                            if (i == 2) {
                                Picasso.get().load(i3).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.GONE);
                            }
                            if (i == 1) {
                                Picasso.get().load(i2).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent o = new Intent(ProductDetail.this, Chat.class);
                            o.putExtra("chaterId", uid);
                            startActivity(o);
                        }
                    });
                    try {
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(ProductDetail.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Are you sure you want to order product?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                databaseReference.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(dataSnapshot.exists()){
                                                            try{
                                                                String p = snapshot.child("balance").getValue().toString();
                                                                if(Integer.parseInt(p)>=Integer.parseInt(Price)){
                                                                    final String push = FirebaseDatabase.getInstance().getReference().child("Order").push().getKey();
                                                                    OrderAttr orderAttr = new OrderAttr();
                                                                    orderAttr.setId(push);
                                                                    orderAttr.setUserId(userId);
                                                                    orderAttr.setTitle(Title);
                                                                    orderAttr.setPrice(Price);
                                                                    orderAttr.setProviderId(uid);
                                                                    orderAttr.setImg(i1);
                                                                    orderAttr.setProviderImg(img);
                                                                    orderAttr.setProviderName(Name);
                                                                    orderAttr.setUserName(UserName);
                                                                    orderAttr.setStatus("Active");
                                                                    orderAttr.setDate(date);
                                                                    orderAttr.setProductId(productId);
                                                                    databaseReference.child("Order").child(push).setValue(orderAttr);
                                                                    Toast.makeText(getApplicationContext(), "Order Created", Toast.LENGTH_LONG).show();
                                                                    startActivity(new Intent(ProductDetail.this , SkillOrderActivity.class));
                                                                }
                                                                else{
                                                                    Toast.makeText(getApplicationContext() , "You have low balance!",Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                            catch (Exception e){}
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }

                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        });
                    }
                    catch (Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}