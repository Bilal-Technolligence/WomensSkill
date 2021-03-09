package com.example.womensskill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class AboutFragment extends Fragment {
    LinearLayout l1 , l2;
    ProgressDialog progressDialog;
    EditText lan , speed , description;
    TextView la , sp , des , name , location , memberSince;
    Button update;
    ImageView img;
    String uid;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_about, container, false);
        l1 = (LinearLayout) v.findViewById(R.id.l1);
        l2 = (LinearLayout) v.findViewById(R.id.l2);
        img = (ImageView) v.findViewById(R.id.txtImg);
        lan = (EditText) v.findViewById(R.id.edtLanguage);
        speed = (EditText) v.findViewById(R.id.edtSpeed);
        description = (EditText) v.findViewById(R.id.edtDes);
        des = (TextView) v.findViewById(R.id.txtDes);
        la = (TextView) v.findViewById(R.id.txtLanguage);
        sp =  (TextView) v.findViewById(R.id.txtSpeed);
        name = (TextView) v.findViewById(R.id.txtName);
        location = (TextView) v.findViewById(R.id.txtLocation);
        memberSince =  (TextView) v.findViewById(R.id.txtMemberSince);
        update = (Button) v.findViewById(R.id.btnUpdate);
        l2.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
        update.setVisibility(View.GONE);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        String eName = dataSnapshot.child("username").getValue().toString();
                        String loc = dataSnapshot.child("address").getValue().toString();
                        String mem = dataSnapshot.child("memberFrom").getValue().toString();
                        name.setText(String.valueOf(eName));
                        location.setText(loc);
                        memberSince.setText(mem);
                        if (dataSnapshot.child("img").getValue().toString().equals(" ")) {
                            String firstLetter = eName.substring(0, 1);
                            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                            int color = generator.getRandomColor();

                            TextDrawable drawable = TextDrawable.builder()
                                    .buildRound(firstLetter, color); // radius in px
                            img.setImageDrawable(drawable);
                        } else {
                            Picasso.get().load(dataSnapshot.child("img").getValue().toString()).into(img);

                        }
                        try{
                        String l = dataSnapshot.child("language").getValue().toString();
                        String s = dataSnapshot.child("speed").getValue().toString();
                        String d = dataSnapshot.child("summary").getValue().toString();
                            des.setText(d);
                            la.setText(l);
                            sp.setText(s);
                        }
                        catch (Exception e){
                            l1.setVisibility(View.GONE);
                            l2.setVisibility(View.VISIBLE);
                            update.setVisibility(View.VISIBLE);
                            description.setVisibility(View.VISIBLE);
                            des.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Language = lan.getText().toString();
                String Speed = speed.getText().toString();
                String Description = description.getText().toString();
                if (Language.isEmpty())
                    lan.setError("Field can not be Empty");
                else if (Speed.isEmpty())
                    speed.setError("Field can not be Empty");
                else if (Description.isEmpty())
                    description.setError("Field can not be Empty");
                else
                {
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                    update.setVisibility(View.GONE);
                    description.setVisibility(View.GONE);
                    des.setVisibility(View.VISIBLE);
                    databaseReference.child("Users").child(uid).child("language").setValue(Language);
                    databaseReference.child("Users").child(uid).child("speed").setValue(Speed);
                    databaseReference.child("Users").child(uid).child("summary").setValue(Description);

                }
            }
        });

        return v;
    }
}