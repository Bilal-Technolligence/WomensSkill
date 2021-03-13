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

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    ArrayList<ServiceAttr> serviceAttrs;
    private Context context;
    Activity activity;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ServiceListAdapter(ArrayList<ServiceAttr> serviceAttrs, Context context , Activity activity) {
        this.context = context;
        this.serviceAttrs = serviceAttrs;
        this.activity = activity;
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
        String id = serviceAttrs.get(position).getId();
        holder.balance.setText("Starting from "+b);
        holder.delete.setImageResource(R.drawable.delete);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("Services").child(id).setValue(null);
                                databaseReference.child("Products").child(id).setValue(null);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img , delete;
        TextView title , balance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            balance = (TextView) itemView.findViewById(R.id.balance);
            delete = (ImageView) itemView.findViewById(R.id.like);


        }
    }
}
