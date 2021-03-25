package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

public class OrderDetail extends AppCompatActivity {
    String id , productId , pp;
    ImageView orderImg, providerImg;
    TextView title, price, providerName, date, status;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Button end, contact, cancel;
    Dialog rankDialog;
    RatingBar ratingBar;


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

        databaseReference.child("Order").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try{
                    Picasso.get().load(dataSnapshot.child("img").getValue().toString()).into(orderImg);
                    Picasso.get().load(dataSnapshot.child("providerImg").getValue().toString()).into(providerImg);
                    title.setText(dataSnapshot.child("title").getValue().toString());
                    price.setText(dataSnapshot.child("price").getValue().toString() + "$");
                    pp = dataSnapshot.child("price").getValue().toString();
                    providerName.setText(dataSnapshot.child("providerName").getValue().toString());
                    date.setText(dataSnapshot.child("date").getValue().toString());
                    status.setText(dataSnapshot.child("status").getValue().toString());
                    String status = dataSnapshot.child("status").getValue().toString();
                    productId = dataSnapshot.child("productId").getValue().toString();
                    String id = dataSnapshot.child("id").getValue().toString();
                    try{
                    if (!status.equals("Active")) {
                        end.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }}
                    catch (Exception e){}
                    String providerId = dataSnapshot.child("providerId").getValue().toString();
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    try{
                    if (providerId.equals(userId)) {
                        end.setVisibility(View.GONE);
                        contact.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }}
                    catch (Exception e){}
                    end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar cldr = Calendar.getInstance();
                            int date = cldr.get(Calendar.DATE);
                            int month = cldr.get(Calendar.MONTH);
                            int year = cldr.get(Calendar.YEAR);

                            rankDialog = new Dialog(OrderDetail.this, R.style.FullHeightDialog);
                            rankDialog.setContentView(R.layout.rank_dialog);
                            rankDialog.setCancelable(true);
                            ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
                            ratingBar.setRating(1);
                            // ratingBar.setRating(userRankValue);

                            //  TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                            //  text.setText(name);
                            rankDialog.show();
                            Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                            EditText comment = (EditText) rankDialog.findViewById(R.id.edtcoment);
                            updateButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    end.setVisibility(View.GONE);
                                    cancel.setVisibility(View.GONE);
                                    databaseReference.child("Order").child(id).child("status").setValue("Completed");
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    databaseReference.child("Rating").child(productId).child(userId).child("rating").setValue(ratingBar.getRating());
                                    databaseReference.child("Rating").child(productId).child(userId).child("comment").setValue(comment.getText().toString());
                                    final String push = FirebaseDatabase.getInstance().getReference().child("Payment").push().getKey();
                                    databaseReference.child("Payment").child(push).child("id").setValue(push);
                                    databaseReference.child("Payment").child(push).child("userId").setValue(providerId);
                                    databaseReference.child("Payment").child(push).child("price").setValue(pp);
                                    databaseReference.child("Payment").child(push).child("year").setValue(String.valueOf(year));
                                    databaseReference.child("Payment").child(push).child("month").setValue(String.valueOf(month+1));

                                    databaseReference.child("Users").child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(dataSnapshot.exists()){
                                                try{
                                                    String p = snapshot.child("balance").getValue().toString();
                                                    int total = Integer.parseInt(pp)+Integer.parseInt(p);
                                                    databaseReference.child("Users").child(providerId).child("balance").setValue(String.valueOf(total));
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
                                            if(dataSnapshot.exists()){
                                                try{
                                                    String p = snapshot.child("balance").getValue().toString();
                                                    int total = Integer.parseInt(p)-Integer.parseInt(pp);
                                                    databaseReference.child("Users").child(userId).child("balance").setValue(String.valueOf(total));
                                                }
                                                catch (Exception e){}
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    rankDialog.dismiss();
                                 }
                            });
                            Button cancel = (Button) rankDialog.findViewById(R.id.rank_dialog_buttoncancel);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    end.setVisibility(View.GONE);
                                    cancel.setVisibility(View.GONE);
                                    databaseReference.child("Order").child(id).child("status").setValue("Completed");
                                    final String push = FirebaseDatabase.getInstance().getReference().child("Payment").push().getKey();
                                    databaseReference.child("Payment").child(providerId).child(String.valueOf(year)).child(String.valueOf(month+1)).child(push).child("id").setValue(push);
                                    databaseReference.child("Payment").child(providerId).child(String.valueOf(year)).child(String.valueOf(month+1)).child(push).child("price").setValue(pp);
                                    databaseReference.child("Users").child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(dataSnapshot.exists()){
                                                try{
                                                    String p = snapshot.child("balance").getValue().toString();
                                                    int total = Integer.parseInt(p)+Integer.parseInt(pp);
                                                    databaseReference.child("Users").child(providerId).child("balance").setValue(String.valueOf(total));
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
                                            if(dataSnapshot.exists()){
                                                try{
                                                    String p = snapshot.child("balance").getValue().toString();
                                                    int total = Integer.parseInt(p)-Integer.parseInt(pp);
                                                    databaseReference.child("Users").child(userId).child("balance").setValue(String.valueOf(total));
                                                }
                                                catch (Exception e){}
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    rankDialog.dismiss();
                                }
                            });
                            Button close = (Button) rankDialog.findViewById(R.id.rank_dialog_buttonclose);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rankDialog.dismiss();
                                }
                            });

                            //ShowDialog();

                            // databaseReference.child("Order").child(id).child("status").setValue("Completed");
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetail.this);
                            alertDialogBuilder.setMessage("Are you sure to cancel the order?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    databaseReference.child("Order").child(id).child("status").setValue("Cancelled");
                                    end.setVisibility(View.GONE);
                                    cancel.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

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
                    catch (Exception e){}
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}