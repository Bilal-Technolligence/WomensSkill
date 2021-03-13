package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OrderDetail extends AppCompatActivity {
    String id;
    ImageView orderImg, providerImg;
    TextView title, price, providerName, date, status;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Button end, contact , cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        id = getIntent().getStringExtra("id");
        orderImg = (ImageView) findViewById(R.id.orderImg);
        providerImg = (ImageView) findViewById(R.id.providerImg);
        title = (TextView) findViewById(R.id.txtTitle);
        price = (TextView) findViewById(R.id.txtPrice);
        providerName = (TextView) findViewById(R.id.txtProviderName);
        date = (TextView) findViewById(R.id.txtDate);
        status = (TextView) findViewById(R.id.txtStatus);
        end = findViewById(R.id.btnFinish);
        contact = findViewById(R.id.btnContact);
        cancel = findViewById(R.id.btnCancel);

        databaseReference.child("Order").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Picasso.get().load(dataSnapshot.child("img").getValue().toString()).into(orderImg);
                    Picasso.get().load(dataSnapshot.child("providerImg").getValue().toString()).into(providerImg);
                    title.setText(dataSnapshot.child("title").getValue().toString());
                    price.setText(dataSnapshot.child("price").getValue().toString()+"$");
                    providerName.setText(dataSnapshot.child("providerName").getValue().toString());
                    date.setText(dataSnapshot.child("date").getValue().toString()+"$");
                    status.setText(dataSnapshot.child("status").getValue().toString());
                    String status = dataSnapshot.child("status").getValue().toString();
                    String id = dataSnapshot.child("id").getValue().toString();
                    if(!status.equals("Active")){
                        end.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }
                    String providerId = dataSnapshot.child("providerId").getValue().toString();
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if(providerId.equals(userId)){
                        end.setVisibility(View.GONE);
                        contact.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }
                    end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            databaseReference.child("Order").child(id).child("status").setValue("Completed");
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseReference.child("Order").child(id).child("status").setValue("Cancelled");
                        }
                    });
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent o = new Intent(OrderDetail.this, Chat.class);
                            o.putExtra("chaterId", providerId);
                            startActivity(o);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}