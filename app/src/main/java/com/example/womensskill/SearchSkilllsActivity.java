package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchSkilllsActivity extends BaseClass {
    EditText find;
    Spinner category;
    String cat = "";
    Spinner category2;
    String cat2 = "";
    String sp = "";
    String f;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<ServiceAttr> serviceAttrs;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_skillls);
        category = findViewById(R.id.spinner_category);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = (String) parent.getItemAtPosition(position);
               // if (cat.contains("Title"))
                   // databaseReference.child("Search").child(uid).child("1").setValue("Title");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat = "Title";
            }
        });
        category2 = findViewById(R.id.spinner_category2);
        category2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cat2 = (String) parent.getItemAtPosition(position);
                        //databaseReference.child("Search").child(uid).child("2").setValue(cat2.toUpperCase());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        cat = "Website";
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat = "Title";
            }
        });
//        databaseReference.child("Search").child(uid).child("1").setValue("Title");
//        databaseReference.child("Search").child(uid).child("2").setValue("WEBSITE");
        find = findViewById(R.id.find);
        recyclerView = findViewById(R.id.searchList);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        initTextListener();


    }
    private void initTextListener() {
        serviceAttrs.clear();
        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = find.getText().toString().toUpperCase();
                searchForMatch(text);
                if(text.equals("")){
                    databaseReference.child("Services").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                serviceAttrs.clear();
                                //profiledata.clear();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    ServiceAttr p = dataSnapshot1.getValue(ServiceAttr.class);
                                    serviceAttrs.add(p);
                                }
                                Collections.reverse(serviceAttrs);
                                recyclerView.setAdapter(new ServiceListAdapterBuyer(serviceAttrs, getApplicationContext() ));
                                progressDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Ads not Found!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void searchForMatch(String keyword ) {
        serviceAttrs.clear();
        updatePostList();

        if(keyword.length() ==0)
        {
            return;
        }

        else
        {
            databaseReference.child("Search").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
//                        String cat = snapshot.child(uid).child("1").getValue().toString();
//                        String cat2 = snapshot.child(uid).child("2").getValue().toString();
                        if(cat.equals("Title")){
                            Query query = FirebaseDatabase.getInstance().getReference("Services")
                                    .orderByChild("title")
                                    .startAt(keyword)
                                    .endAt(keyword+"\uf8ff");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    serviceAttrs.clear();
                                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                                    {
                                        String sid = singleSnapshot.getKey();
                                        databaseReference.child("Services").child(sid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String ca = snapshot.child("category").getValue().toString();
                                                if(ca.equals(cat2)){
                                                    if(!serviceAttrs.contains(singleSnapshot.getValue(ServiceAttr.class)))
                                                    {
                                                        serviceAttrs.add(singleSnapshot.getValue(ServiceAttr.class));
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                    try{
                                        updatePostList();
                                    }
                                    catch (Exception ex)
                                    {

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            Query query2 = FirebaseDatabase.getInstance().getReference("Products")
                                    .orderByChild("title")
                                    .startAt(keyword)
                                    .endAt(keyword+"\uf8ff");
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    serviceAttrs.clear();
                                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                                    {
                                        String sid = singleSnapshot.getKey();
                                        databaseReference.child("Products").child(sid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String ca = snapshot.child("subcategory").getValue().toString();
                                                if(ca.equals(cat2)){
                                                    if(!serviceAttrs.contains(singleSnapshot.getValue(ServiceAttr.class)))
                                                    {
                                                        serviceAttrs.add(singleSnapshot.getValue(ServiceAttr.class));
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                    try{
                                        updatePostList2();
                                    }
                                    catch (Exception ex)
                                    {

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

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
    }

    private void updatePostList() {

        recyclerView.setAdapter(new ServiceListAdapterBuyer(serviceAttrs, getApplicationContext() ));

    }
    private void updatePostList2() {

        recyclerView.setAdapter(new ProductListAdapterBuyer(serviceAttrs, getApplicationContext() ));

    }
    @Override
    int getContentViewId() {
        return R.layout.activity_search_skillls;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_search;
    }
}