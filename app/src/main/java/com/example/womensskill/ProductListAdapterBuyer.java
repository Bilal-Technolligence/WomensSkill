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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapterBuyer extends RecyclerView.Adapter<ProductListAdapterBuyer.ViewHolder> {
    ArrayList<ServiceAttr> serviceAttrs;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ProductListAdapterBuyer(ArrayList<ServiceAttr> serviceAttrs, Context context) {
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , ProductDetail.class);
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
        ImageView img;
        TextView title , balance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            balance = (TextView) itemView.findViewById(R.id.balance);


        }
    }
}
