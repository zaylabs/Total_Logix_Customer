package com.example.raza.total_logix_customer.fragment;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.example.raza.total_logix_customer.DTO.driverRating;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.CurrentRideActivity;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.example.raza.total_logix_customer.recylerViewAdapter.currentRideAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class acceptRequestFragment extends android.app.Fragment {
    private FirebaseFirestore firestoreDB;
    private RecyclerView mDhistory;
    private ProgressBar mProgressbar;
    private List<com.example.raza.total_logix_customer.DTO.acceptRequest> dHistory;
    private acceptRequest acceptRequest;
    private com.example.raza.total_logix_customer.recylerViewAdapter.currentRideAdapter currentRideAdapter;
    private String userId;
    private String TAG;
    private FirebaseAuth mAuth;
    private FrameLayout mProgressLayout;
    private Dialog myDialog;
    private float oldrating;
    private float totalride;
    private float newrating;
    private String driverId;
    private float updatedrating;
    private ListenerRegistration acceptrequestlistner;
    private ListenerRegistration driverratinglistner;

    public acceptRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_request, container, false);

        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mProgressLayout=(FrameLayout)view.findViewById(R.id.ProgressLayout);
        //mProgressbar=(ProgressBar)view.findViewById(R.id.progressBar_customer);
        dHistory = new ArrayList<>();
        currentRideAdapter = new currentRideAdapter(getContext(),dHistory);
        mDhistory = (RecyclerView)view.findViewById(R.id.currentRideRV);
        mDhistory.setHasFixedSize(true);
        mDhistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mDhistory.setAdapter(currentRideAdapter);





        acceptrequestlistner=firestoreDB.collection("acceptRequest").whereEqualTo("cid", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
                            dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            acceptRequest = doc.getDocument().toObject(acceptRequest.class);
                            dHistory.add(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
          //                  mProgressLayout.setVisibility(View.GONE);
                            break;
                        case MODIFIED:
                            dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            acceptRequest = doc.getDocument().toObject(acceptRequest.class);
                            dHistory.add(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();



                            break;
                    }

                }
            }
        });




        return view;
    }
 /*   public void currentredeitemFragment()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.cm, new currentrideitemFragment());
        ft.commit();

    }*/
}
