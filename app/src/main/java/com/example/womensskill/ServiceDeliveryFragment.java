package com.example.womensskill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class ServiceDeliveryFragment extends Fragment {
//    RecyclerView recyclerView;
//    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    DatabaseReference databaseReference = firebaseDatabase.getReference();
//    ArrayList<ServiceAttr> serviceAttrs;
//    ProgressDialog progressDialog;
//    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_servicedelivery, container, false);
//        recyclerView = v.findViewById(R.id.recyclerServices);
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading..... ");
//        progressDialog.show();
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        serviceAttrs = new ArrayList<ServiceAttr>();
//        databaseReference.child("Services").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    serviceAttrs.clear();
//                    //profiledata.clear();
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        ServiceAttr p = dataSnapshot1.getValue(ServiceAttr.class);
//                        serviceAttrs.add(p);
//                    }
//                    Collections.reverse(serviceAttrs);
//                    recyclerView.setAdapter(new ServiceListAdapter(serviceAttrs, getContext(),getActivity()));
//                    progressDialog.dismiss();
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(getContext(), "Services not Found!", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        return v;
    }
}