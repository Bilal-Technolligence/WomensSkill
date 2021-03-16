package com.example.womensskill;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.ArrayList;

public class ServiceListAdapterBuyer extends RecyclerView.Adapter<ServiceListAdapterBuyer.ViewHolder> {
    ArrayList<ServiceAttr> serviceAttrs;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    //final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ServiceListAdapterBuyer(ArrayList<ServiceAttr> serviceAttrs, Context context) {
        this.context = context;
        this.serviceAttrs = serviceAttrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(serviceAttrs.get(position).getImage1()).into(holder.img);
        holder.title.setText(serviceAttrs.get(position).getTitle());
        String b = serviceAttrs.get(position).getPrice();
        holder.balance.setText("Starting from "+b);
        String id = serviceAttrs.get(position).getId();
        holder.like.setImageResource(R.drawable.emptyheart);
        try{
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("Like").child(uid).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                databaseReference.child("Like").child(uid).child(id).setValue(null);
                                holder.like.setImageResource(R.drawable.emptyheart);
                            }
                            else
                            {
                                databaseReference.child("Like").child(uid).child(id).child("id").setValue(id);
                                holder.like.setImageResource(R.drawable.fillheart);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
            databaseReference.child("Like").child(uid).child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        holder.like.setImageResource(R.drawable.fillheart);
                    }
                    else
                        holder.like.setImageResource(R.drawable.emptyheart);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            databaseReference.child("Rating").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
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
        }
        catch (Exception e){
            holder.like.setVisibility(View.GONE);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , ServiceDetail.class);
                i.putExtra("id",id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, like;
        TextView title , balance , rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            balance = (TextView) itemView.findViewById(R.id.balance);
            rating = (TextView) itemView.findViewById(R.id.rating);
            like = (ImageView) itemView.findViewById(R.id.like);


        }
    }
}
