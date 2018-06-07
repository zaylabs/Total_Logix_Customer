package com.example.raza.total_logix_customer.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.example.raza.total_logix_customer.DTO.customerRequest;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.recylerViewAdapter.currentRideAdapter;
import com.example.raza.total_logix_customer.recylerViewAdapter.customerRequestAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class pendingRequestFragment extends android.app.Fragment {
    private FirebaseFirestore firestoreDB;
    private RecyclerView mDhistory;
    private ProgressBar mProgressbar;
    private List<com.example.raza.total_logix_customer.DTO.customerRequest> dHistory;
    private customerRequest acceptRequest;
    private com.example.raza.total_logix_customer.recylerViewAdapter.customerRequestAdapter currentRideAdapter;
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


    public pendingRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view=inflater.inflate(R.layout.fragment_pending_request, container, false);

        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mProgressLayout=(FrameLayout)view.findViewById(R.id.ProgressLayout);
        //mProgressbar=(ProgressBar)view.findViewById(R.id.progressBar_customer);
        dHistory = new ArrayList<>();
        currentRideAdapter = new customerRequestAdapter(getContext(),dHistory);
        mDhistory = (RecyclerView)view.findViewById(R.id.pendingRideRV);
        mDhistory.setHasFixedSize(true);
        mDhistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mDhistory.setAdapter(currentRideAdapter);

        acceptrequestlistner=firestoreDB.collection("customerRequest").whereEqualTo("cid", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
                            /*dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            */
                            acceptRequest = doc.getDocument().toObject(customerRequest.class);
                            dHistory.add(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            /*dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();*/
                            acceptRequest = doc.getDocument().toObject(customerRequest.class);
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

}
