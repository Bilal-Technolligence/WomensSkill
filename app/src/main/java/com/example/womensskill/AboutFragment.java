package com.example.womensskill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class AboutFragment extends Fragment {

    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_about, container, false);

//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading..... ");
//        progressDialog.show();

        return v;
    }
}