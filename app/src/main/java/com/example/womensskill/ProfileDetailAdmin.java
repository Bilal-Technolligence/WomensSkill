package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileDetailAdmin extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView la , sp , des , name , location , memberSince;
    Button delete , skills , products;
    ImageView img;
    String uid;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_admin);
        uid = getIntent().getStringExtra("id");

        img = (ImageView) findViewById(R.id.txtImg);
        des = (TextView)  findViewById(R.id.txtDes);
        la = (TextView) findViewById(R.id.txtLanguage);
        sp =  (TextView)  findViewById(R.id.txtSpeed);
        name = (TextView) findViewById(R.id.txtName);
        location = (TextView)  findViewById(R.id.txtLocation);
        memberSince =  (TextView)  findViewById(R.id.txtMemberSince);
        delete = (Button)  findViewById(R.id.btnDelete);
        skills = findViewById(R.id.btnSkill);
        products = findViewById(R.id.btnProduct);

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

                        }

                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileDetailAdmin.this , ProductSkillAdmin.class);
                i.putExtra("id" , uid);
                i.putExtra("cat" , "Products");
                startActivity(i);
            }
        });
        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileDetailAdmin.this , ProductSkillAdmin.class);
                i.putExtra("id" , uid);
                i.putExtra("cat" , "Services");
                startActivity(i);
            }
        });

    }
}