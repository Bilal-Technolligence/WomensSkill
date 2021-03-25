package com.example.womensskill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    ArrayList<PostAttr> postAttrs;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Activity activity;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    int i = 0;

    public PostListAdapter(ArrayList<PostAttr> postAttrs, Context context , Activity activity) {
        this.context = context;
        this.postAttrs = postAttrs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(postAttrs.get(position).getUserImg()).into(holder.img);
        holder.title.setText(postAttrs.get(position).getTitle());
        String Title = (postAttrs.get(position).getTitle());
        holder.price.setText(postAttrs.get(position).getPrice());
        String Price = (postAttrs.get(position).getPrice());
        holder.name.setText(postAttrs.get(position).getUserName());
        holder.date.setText(postAttrs.get(position).getDate());
        holder.time.setText(postAttrs.get(position).getTime());
        holder.category.setText(postAttrs.get(position).getCategory());
        holder.criteria.setText(postAttrs.get(position).getCriteria());

        String id = postAttrs.get(position).getId();
        String userId = postAttrs.get(position).getUserId();
        String UserName = postAttrs.get(position).getUserName();
        String Time = (postAttrs.get(position).getTime());
        String UserImg = (postAttrs.get(position).getUserImg());
        String category = (postAttrs.get(position).getCategory());
        String criteria = (postAttrs.get(position).getCriteria());

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());

        if(uid.equals(userId)){
            holder.send.setVisibility(View.GONE);
        }
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to send request?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name = snapshot.child("username").getValue().toString();
                                        String img = snapshot.child("img").getValue().toString();
                                        //databaseReference.child()
                                        databaseReference.child("Services").orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                                        String sid = dataSnapshot1.getKey();
                                                        databaseReference.child("Services").child(sid).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                String cat = snapshot.child("category").getValue().toString();
                                                                String imgS = snapshot.child("image1").getValue().toString();
                                                                if(cat.equals(category)){
                                                                    if(i==0){
                                                                        final String push = FirebaseDatabase.getInstance().getReference().child("PostOrder").push().getKey();
                                                                        OrderPostAttr orderAttr = new OrderPostAttr();
                                                                        orderAttr.setId(push);
                                                                        orderAttr.setPostId(id);
                                                                        orderAttr.setUserId(userId);
                                                                        orderAttr.setTitle(Title);
                                                                        orderAttr.setPrice(Price);
                                                                        orderAttr.setProviderId(uid);
                                                                        orderAttr.setProviderName(name);
                                                                        orderAttr.setUserName(UserName);
                                                                        orderAttr.setImg(imgS);
                                                                        orderAttr.setProviderImg(img);
                                                                        orderAttr.setStatus("Pending");
                                                                        orderAttr.setDate(date);
                                                                        orderAttr.setTime(Time);
                                                                        orderAttr.setCategory(category);
                                                                        orderAttr.setCriteria(criteria);
                                                                        orderAttr.setUserImg(UserImg);
                                                                        orderAttr.setProductId(sid);
                                                                        databaseReference.child("PostOrder").child(push).setValue(orderAttr);
                                                                        Toast.makeText(context, "Request Created", Toast.LENGTH_LONG).show();
                                                                        //startActivity(new Intent(ServiceDetail.this , SkillOrderActivity.class));
                                                                        i++;
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
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

    @Override
    public int getItemCount() {
        return postAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, price, name , date , time , category ,criteria;
        Button send;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgProfile);
            title = (TextView) itemView.findViewById(R.id.txtbuyerRequest);
            price = (TextView) itemView.findViewById(R.id.txtPrice);
            name = (TextView) itemView.findViewById(R.id.txtName);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            time = (TextView) itemView.findViewById(R.id.txtTime);
            category = (TextView) itemView.findViewById(R.id.txtCategory);
            criteria = (TextView) itemView.findViewById(R.id.txtCriteria);
            send = (Button) itemView.findViewById(R.id.btnSendOffer);


        }
    }
}
