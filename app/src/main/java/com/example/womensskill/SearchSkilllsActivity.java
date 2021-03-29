package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

public class SearchSkilllsActivity extends BaseClass {
    LinearLayout website , mobile ,Writing , Art , dataentry , sales , bussiness , localjobs , handicraft , cloth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_skillls);
        website = findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "WEBSITE");
                startActivity(i);
            }
        });
        mobile = findViewById(R.id.mobile);
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "MOBILE");
                startActivity(i);
            }
        });
        Writing = findViewById(R.id.writing);
        Writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "WRITING");
                startActivity(i);
            }
        });
        Art = findViewById(R.id.art);
        Art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "ART AND DESIGN");
                startActivity(i);
            }
        });
        dataentry = findViewById(R.id.dataentry);
        dataentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "DATAENTRY");
                startActivity(i);
            }
        });
        sales = findViewById(R.id.sales);
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "SALES");
                startActivity(i);
            }
        });
        bussiness = findViewById(R.id.bussiness);
        bussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "BUSSINESS");
                startActivity(i);
            }
        });
        localjobs = findViewById(R.id.localjob);
        localjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Services");
                i.putExtra("cat" , "LOCAL JOBS");
                startActivity(i);
            }
        });
        handicraft = findViewById(R.id.Handicraft);
        handicraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Products");
                i.putExtra("cat" , "HANDICRAFT");
                startActivity(i);
            }
        });
        cloth = findViewById(R.id.clothSewing);
        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchSkilllsActivity.this , SearchResultActivity.class);
                i.putExtra("id" , "Products");
                i.putExtra("cat" , "CLOTH SEWING");
                startActivity(i);
            }
        });


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