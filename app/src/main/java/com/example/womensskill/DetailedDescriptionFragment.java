package com.example.womensskill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class DetailedDescriptionFragment extends Fragment {
    EditText title, price ,quantity ,video, description;
    ImageView img1, img2, img3;
    Spinner category, subcat;
    String cat = "";
    String sub = "";
    private Uri imagePath1, imagePath2, imagePath3;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    CardView save;
    String tit, pri ,qua ,vid ,des;
    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Products");
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detaileddescription, container, false);
       // ((AppCompatActivity)this).getSupportActionBar().setTitle("Product Upload");
       // ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        title = v.findViewById(R.id.txtTitle);
//        quantity = v.findViewById(R.id.txtQuantity);
//        video =v.findViewById(R.id.txtVideo);
//        description = v.findViewById(R.id.txtDes);
//        price = v.findViewById(R.id.txtPrice);
//        img1 = v.findViewById(R.id.img1);
//        img2 = v.findViewById(R.id.img2);
//        img3 =v.findViewById(R.id.img3);
//        category = v.findViewById(R.id.spinner_category);
//        subcat = v.findViewById(R.id.spinner_subcategory);
//        save = v.findViewById(R.id.btn_save);
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Uploading please wait..... ");
//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                cat = (String) parent.getItemAtPosition(position);
//                if (cat.equals("Handicraft")) {
//                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.product_handicraft, android.R.layout.simple_spinner_item);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    subcat.setAdapter(adapter);
//                }
//                if (cat.equals("Cloth Sewing")) {
//                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.product_cloth, android.R.layout.simple_spinner_item);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    subcat.setAdapter(adapter);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                cat = "Handicraft";
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.product_handicraft, android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                subcat.setAdapter(adapter);
//            }
//        });
//        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sub = (String) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                if (cat.equals("Handicraft")) {
//                    sub = "Embroidery";
//                }
//                if (cat.equals("Cloth Sewing")) {
//                    sub = "T-Shirts";
//                }
//            }
//        });
//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 2);
//            }
//        });
//        img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 3);
//            }
//        });
//        img3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 4);
//            }
//        });
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tit = title.getText().toString();
//                pri = price.getText().toString();
//                qua = quantity.getText().toString();
//                vid = video.getText().toString();
//                des = description.getText().toString();
//                if (tit.equals("")) {
//                    title.setError("Enter Valid Title");
//                    title.setFocusable(true);
//                } else if (pri.equals("")) {
//                    price.setError("Enter Valid Price");
//                    price.setFocusable(true);
//                }
//                else if (des.equals("")) {
//                    description.setError("Enter Valid Description");
//                    description.setFocusable(true);
//                }else if (qua.equals("")) {
//                    quantity.setError("Enter Valid Quantity");
//                    quantity.setFocusable(true);
//                }else if (vid.equals("")) {
//                    video.setError("Enter Video URL");
//                    video.setFocusable(true);
//                }else if (count1 == 0 || count2 ==0 ||count3 ==0) {
//                    Snackbar.make(view, "Please Select All Image", Snackbar.LENGTH_LONG).show();
//                } else {
//                    progressDialog.show();
//                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
//                    storageReference.putFile(imagePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                            while (!uriTask.isSuccessful()) ;
//                            Uri downloadUri = uriTask.getResult();
//                            String img1 = downloadUri.toString();
//                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
//                            storageReference.putFile(imagePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                                    while (!uriTask.isSuccessful()) ;
//                                    Uri downloadUri = uriTask.getResult();
//                                    String img2 = downloadUri.toString();
//                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
//                                    storageReference.putFile(imagePath3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                                            while (!uriTask.isSuccessful()) ;
//                                            Uri downloadUri = uriTask.getResult();
//                                            String img3 = downloadUri.toString();
//                                            final String push = FirebaseDatabase.getInstance().getReference().child("Services").push().getKey();
//                                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                            ProductAttr productAttr = new ProductAttr();
//                                            productAttr.setId(push);
//                                            productAttr.setUserId(uid);
//                                            productAttr.setTitle("i will provide "+tit);
//                                            productAttr.setPrice(pri);
//                                            productAttr.setCategory(cat.toUpperCase());
//                                            productAttr.setSubcategory(sub.toUpperCase());
//                                            productAttr.setImage1(img1);
//                                            productAttr.setImage2(img2);
//                                            productAttr.setImage3(img3);
//                                            productAttr.setDescription(des);
//                                            productAttr.setVideo(vid);
//                                            productAttr.setQuantity(qua);
//                                            productAttr.setStatus("not");
//                                            reference.child(push).setValue(productAttr);
//
//                                            Toast.makeText(getContext(), "Product Added", Toast.LENGTH_SHORT).show();
//                                            progressDialog.dismiss();
//                                           // finish();
//
//                                        }
//                                    });
//
//                                }
//                            });
//
//
//                        }
//                    });
//
//                }
//            }
//        });
        return v;
    }

//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == requestCode && resultCode == resultCode
//                && data != null && data.getData() != null) {
//            if (requestCode == 2) {
//                imagePath1 = data.getData();
//                try {
//
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath1);
//                    img1.setImageBitmap(bitmap);
//                    count1 = 1;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (requestCode == 3) {
//                imagePath2 = data.getData();
//                try {
//
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath2);
//                    img2.setImageBitmap(bitmap);
//                    count2 = 1;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (requestCode == 4) {
//                imagePath3 = data.getData();
//                try {
//
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath3);
//                    img3.setImageBitmap(bitmap);
//                    count3 = 1;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}