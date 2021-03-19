package com.example.womensskill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    ArrayList<UserAttr> userAttrs;
    private Context context;

    public MemberAdapter(ArrayList<UserAttr> userAttrs, Context context) {
        this.context = context;
        this.userAttrs = userAttrs;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(userAttrs.get(position).getFullname());
        try {
            Picasso.get().load(userAttrs.get(position).getImg()).into(holder.profile);
        }
        catch (Exception e ){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , ProfileDetailAdmin.class);
                i.putExtra("id" , userAttrs.get(position).getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.imgProfile);
            name = (TextView) itemView.findViewById(R.id.txtName);


        }
    }
}
