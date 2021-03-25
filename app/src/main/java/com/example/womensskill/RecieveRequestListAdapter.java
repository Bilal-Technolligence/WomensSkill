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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecieveRequestListAdapter extends RecyclerView.Adapter<RecieveRequestListAdapter.ViewHolder> {
    ArrayList<OrderPostAttr> postAttrs;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Activity activity;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    int i = 0;

    public RecieveRequestListAdapter(ArrayList<OrderPostAttr> postAttrs, Context context , Activity activity) {
        this.context = context;
        this.postAttrs = postAttrs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recieverequest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(postAttrs.get(position).getProviderImg()).into(holder.img);
        holder.title.setText(postAttrs.get(position).getTitle());
        holder.price.setText(postAttrs.get(position).getPrice());
        holder.name.setText(postAttrs.get(position).getProviderName());
        holder.date.setText(postAttrs.get(position).getDate());
        holder.time.setText(postAttrs.get(position).getTime());
        holder.category.setText(postAttrs.get(position).getCategory());
        holder.criteria.setText(postAttrs.get(position).getCriteria());



        String Time = (postAttrs.get(position).getTime());
        String UserImg = (postAttrs.get(position).getUserImg());
        String category = (postAttrs.get(position).getCategory());
        String criteria = (postAttrs.get(position).getCriteria());

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
        String id = postAttrs.get(position).getId();
        String img = postAttrs.get(position).getImg();
        String Price = (postAttrs.get(position).getPrice());
        String ProductId = (postAttrs.get(position).getProductId());
        String ProviderId = (postAttrs.get(position).getProductId());
        String ProviderImg = (postAttrs.get(position).getProductId());
        String providerName = postAttrs.get(position).getProviderName();
        String Title = (postAttrs.get(position).getTitle());
        String userId = postAttrs.get(position).getUserId();
        String UserName = postAttrs.get(position).getUserName();

        if(uid.equals(userId)){
            holder.send.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }
        databaseReference.child("Rating").child(ProductId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long count = dataSnapshot.getChildrenCount();
                    Double total = 0.0;
                    for (DataSnapshot adminsnapshot : dataSnapshot.getChildren()) {
                        Rating_Attr rating_attr = adminsnapshot.getValue(Rating_Attr.class);
                        total += rating_attr.getRating();
                    }
                    String star = String.valueOf(new DecimalFormat("#.#").format(total / count));
                    String str = String.valueOf(total / count);
//                    ratingBar.setRating(Float.parseFloat(str));
//                    //ratingBar.setNumStars((int) (total/count));
//                    txtRating.setText(star);
//                    txtNum.setText(String.valueOf(count));
                    holder.rating.setText(star + " (" + count + ")");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to confirm order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OrderAttr orderAttr = new OrderAttr();
                                orderAttr.setId(id);
                                orderAttr.setUserId(userId);
                                orderAttr.setTitle(Title);
                                orderAttr.setPrice(Price);
                                orderAttr.setProviderId(ProviderId);
                                orderAttr.setImg(img);
                                orderAttr.setProviderImg(ProviderImg);
                                orderAttr.setProviderName(providerName);
                                orderAttr.setUserName(UserName);
                                orderAttr.setStatus("Active");
                                orderAttr.setDate(date);
                                orderAttr.setProductId(ProductId);
                                databaseReference.child("Order").child(id).setValue(orderAttr);
                                databaseReference.child("PostOrder").child(id).setValue(null);
                                Toast.makeText(context, "Order Created", Toast.LENGTH_LONG).show();


                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to confirm order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("PostOrder").child(id).setValue(null);
                                Toast.makeText(context, "Offer Rejected!", Toast.LENGTH_LONG).show();

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
        TextView title, price, name , date , time , category ,criteria , rating;
        Button send , reject;

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
            rating = (TextView) itemView.findViewById(R.id.rating);
            send = (Button) itemView.findViewById(R.id.btnConfirmOffer);
            reject = (Button) itemView.findViewById(R.id.btnDeleteOffer);
        }
    }
}
