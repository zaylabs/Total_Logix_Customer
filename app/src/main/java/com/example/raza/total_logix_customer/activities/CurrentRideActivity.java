package com.example.raza.total_logix_customer.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.example.raza.total_logix_customer.DTO.driverRating;
import com.example.raza.total_logix_customer.DTO.ratingUpdate;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.raza.total_logix_customer.recylerViewAdapter.currentRideAdapter;
import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class CurrentRideActivity extends AppCompatActivity {
    private FirebaseFirestore firestoreDB;
    private RecyclerView mDhistory;
    private ProgressBar mProgressbar;
    private List<acceptRequest> dHistory;
    private acceptRequest acceptRequest;
    private currentRideAdapter currentRideAdapter;
    private String driverID;
    private String TAG;
    private FirebaseAuth mAuth;
    private FrameLayout mProgressLayout;
    private Dialog myDialog;
    private float oldrating;
    private float totalride;
    private float newrating;
    private String currentdriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);
        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        driverID = mAuth.getCurrentUser().getUid();

        mProgressLayout=(FrameLayout)findViewById(R.id.ProgressLayout);
        mProgressbar=(ProgressBar)findViewById(R.id.progressBar_customer);
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
                            mProgressLayout.setVisibility(View.GONE);
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
                            myDialog = new Dialog(CurrentRideActivity.this);
                            myDialog.setContentView(R.layout.driver_rating_dialog);
                            myDialog.show();
                            final RatingBar mRatingbar=(RatingBar)myDialog.findViewById(R.id.payDriverRating);
                            final AppCompatButton mSubmit=(AppCompatButton)myDialog.findViewById(R.id.btn_ratingsubmit);

                            mSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    firestoreDB.collection("driverRating").document(driverID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                        driverRating driverRating = documentSnapshot.toObject(com.example.raza.total_logix_customer.DTO.driverRating.class);
                                        oldrating=driverRating.getOldrating();
                                        totalride=driverRating.getTotalride();
                                        currentdriver=driverRating.getDriverID();

                                        }
                                    });
                                    newrating = mRatingbar.getRating();

                                    float updatedrating=(newrating+oldrating)/totalride;
                                    ratingUpdate ratingUpdate = new ratingUpdate(totalride,updatedrating);

                                    Map<String, Object> driverUpdates = new HashMap<>();
                                    driverUpdates.put("totalrides",totalride);
                                    driverUpdates.put("stars",updatedrating);



                                    firestoreDB.collection("drivers").document(currentdriver)
                                            .update(driverUpdates);



                                    /*firestoreDB.collection("drivers").document(currentdriver).update("totalrides", totalride)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });

                                    firestoreDB.collection("drivers").document(currentdriver).update("stars", updatedrating)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                    firestoreDB.collection("driverRating").document(driverID).delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                                    Intent intent = new Intent(CurrentRideActivity.this, HomeActivity.class);
                                                                    CurrentRideActivity.this.startActivity(intent);
                                                                }

                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w(TAG, "Error deleting document", e);
                                                                }
                                                            });

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error updating document", e);
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

    }
}
