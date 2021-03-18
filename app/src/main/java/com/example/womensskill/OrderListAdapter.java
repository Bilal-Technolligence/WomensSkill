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

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    ArrayList<OrderAttr> orderAttrs;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    public OrderListAdapter(ArrayList<OrderAttr> orderAttrs, Context context) {
        this.context = context;
        this.orderAttrs = orderAttrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(orderAttrs.get(position).getImg()).into(holder.orderImg);
        Picasso.get().load(orderAttrs.get(position).getProviderImg()).into(holder.providerImg);
        holder.title.setText(orderAttrs.get(position).getTitle());
        holder.price.setText(orderAttrs.get(position).getPrice()+"$");
        holder.providerName.setText(orderAttrs.get(position).getProviderName());
        holder.date.setText(orderAttrs.get(position).getDate());
        holder.status.setText(orderAttrs.get(position).getStatus());
        String id = orderAttrs.get(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , OrderDetail.class);
                i.putExtra("id",id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImg , providerImg;
        TextView title , price , providerName ,date , status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImg = (ImageView) itemView.findViewById(R.id.orderImg);
            providerImg = (ImageView) itemView.findViewById(R.id.providerImg);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            price = (TextView) itemView.findViewById(R.id.txtPrice);
            providerName = (TextView) itemView.findViewById(R.id.txtProviderName);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            status = (TextView) itemView.findViewById(R.id.txtStatus);


        }
    }
}
