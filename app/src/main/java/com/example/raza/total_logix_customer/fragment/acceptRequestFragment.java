package com.example.raza.total_logix_customer.fragment;


import android.app.Dialog;
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
                            myDialog = new Dialog(getContext());
                            myDialog.setContentView(R.layout.driver_rating_dialog);
                            myDialog.show();
                            myDialog.setCancelable(false);
                            myDialog.setCanceledOnTouchOutside(false);
                            final RatingBar mRatingbar=(RatingBar)myDialog.findViewById(R.id.payDriverRating);
                            final AppCompatButton mSubmit=(AppCompatButton)myDialog.findViewById(R.id.btn_ratingsubmit);

                            mSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                    driverratinglistner= firestoreDB.collection("driverRating").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            driverRating driverRating = documentSnapshot.toObject(com.example.raza.total_logix_customer.DTO.driverRating.class);
                                            if (driverRating != null) {
                                                oldrating = driverRating.getOldrating();
                                            } else{
                                                oldrating = 0;
                                            }

                                            totalride = driverRating.getTotalride();

                                            driverId = driverRating.getDriverID();
                                            newrating = mRatingbar.getRating();
                                            if (totalride > 0) {
                                                updatedrating = (newrating + oldrating) / totalride;
                                            } else {
                                                updatedrating = newrating;
                                                totalride=1;
                                            }

                                            Map<String, Object> driverUpdates = new HashMap<>();
                                            driverUpdates.put("totalrides",totalride);
                                            driverUpdates.put("stars",updatedrating);

                                            String Temp = driverId;
//HGofXOnckgRHwl2g21e5CQ30Wly2
                                            firestoreDB.collection("drivers").document(driverId)
                                                    .update(driverUpdates);

                                        }
                                    });

                               /*     firestoreDB.collection("driverRating").document(userId).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                               */                   //  Intent intent = new Intent(getContext(), HomeActivity.class);
                                  //  startActivity(intent);
                                 /*                   finish();
                                                }

                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error deleting document", e);
                                                }
                                            });
*/



                                }
                            });




                            break;
                    }

                }
            }
        });



        return view;
    }

}
