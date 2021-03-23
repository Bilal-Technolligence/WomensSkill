package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class BuyersRequestActivity extends AppCompatActivity {
TextView txttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyers_request);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Buyer Request");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txttext =findViewById(R.id.txtbuyerRequest);
//        txttext.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if(expandable) {
//                    expandable = false;
//                    if (txttext.getLineCount() > 4) {
//                        btnSeeMore.setVisibility(View.VISIBLE);
//                        ObjectAnimator animation = ObjectAnimator.ofInt(txttext, "maxLines", 4);
//                        animation.setDuration(0).start();
//                    }
//                }
//            }
//        });
    }
}