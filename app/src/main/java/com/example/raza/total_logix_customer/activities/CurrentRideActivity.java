package com.example.raza.total_logix_customer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.raza.total_logix_customer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.raza.total_logix_customer.recylerViewAdapter.currentRideAdapter;
import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CurrentRideActivity extends AppCompatActivity {
    private FirebaseFirestore firestoreDB;
    private RecyclerView mDhistory;

    private List<acceptRequest> dHistory;
    private acceptRequest acceptRequest;
    private currentRideAdapter currentRideAdapter;
    private String driverID;
    private String TAG;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);
        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        driverID = mAuth.getCurrentUser().getUid();



        dHistory = new ArrayList<>();
        currentRideAdapter = new currentRideAdapter(this,dHistory);
        mDhistory = (RecyclerView)findViewById(R.id.currentRideRV);
        mDhistory.setHasFixedSize(true);
        mDhistory.setLayoutManager(new LinearLayoutManager(this));
        mDhistory.setAdapter(currentRideAdapter);

        firestoreDB.collection("acceptRequest").whereEqualTo("cid", driverID).addSnapshotListener(new EventListener<QuerySnapshot>() {

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

    }
}
