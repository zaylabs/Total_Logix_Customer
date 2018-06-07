package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.raza.total_logix_customer.DTO.customerRequest;
import com.example.raza.total_logix_customer.DTO.dropLocationDTO;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class customerRequestAdapter extends RecyclerView.Adapter<customerRequestAdapter.ViewHolder> {


    //Firebase Start
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;
    //Firebase End
    private String TAG = "";
    public List<customerRequest> cRequests;

    public Context context;

    private GeoPoint driverLocation;
    private String drivername;
    private String driverdp;
    private String drivernic;
    private String driverphone;
    private String carregno;
    private Date date = Calendar.getInstance().getTime();
    private String uniqueID;
    private float starts;
    private float ridestars;
    private GeoPoint mPick;
    private float distance;
    private GeoPoint mCurrentLocation;
    private String mydate, mytime, gatepass;
    private ListenerRegistration driveravailablegetreqliststner;
    private ArrayList removelocation;
    public customerRequestAdapter(Context context, List<customerRequest> cRequests) {
        this.cRequests = cRequests;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedningrequest_single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


        /*docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        CurrentLocatiofcn= Objects.requireNonNull(document.toObject(driverAvailable.class)).getDriverLocation();
                    }
                }
            }


        });
*/

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());

        mydate = DateFormat.getDateInstance().format(cRequests.get(position).getDate());
        mytime = formatter.format(cRequests.get(position).getDate());
        holder.mName.setText(cRequests.get(position).getName());
        holder.mPickup.setText(cRequests.get(position).getPickupaddress());
        holder.mDrop.setText(cRequests.get(position).getDropaddress());
        holder.mPhone.setText(cRequests.get(position).getPhone());
        holder.mRideDistance.setText(String.valueOf(cRequests.get(position).getRidedistance()));
        holder.mDate.setText(mydate);
        holder.mTime.setText(mytime);
        holder.mWeight.setText(cRequests.get(position).getWeight());
        holder.mDiscription.setText(cRequests.get(position).getDescription());
        holder.mBoxes.setText(cRequests.get(position).getBoxes());
        holder.mRatingBar.setRating(cRequests.get(position).getStars());
        uniqueID = cRequests.get(position).getUniqueID();


        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("customerRequest").document(uniqueID).collection("customerRequestdroplocations").whereEqualTo("cid", userID).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        String DropLocationID= document.getId();
                                        db.collection("customerRequest").document(uniqueID).collection("customerRequestdroplocations").document(DropLocationID).delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");

                                                    }

                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error deleting document", e);
                                                    }
                                                });

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                db.collection("customerRequest").document(uniqueID).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

            }
        });




    }

    @Override
    public int getItemCount() {
        return cRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView mName, mPickup, mDrop, mPhone, mDistance, mRideDistance, mDate, mTime, mDiscription, mWeight, mBoxes;
        public Button  mCancel;
        public RatingBar mRatingBar;
        public RatingBar mRideRating;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = (TextView) mView.findViewById(R.id.txt_customername);
            mPickup = (TextView) mView.findViewById(R.id.txt_from_add);
            mDrop = (TextView) mView.findViewById(R.id.txt_to_add);
            mPhone = (TextView) mView.findViewById(R.id.txt_phone);
            mCancel=(Button)mView.findViewById(R.id.cancelbtn);
            mDistance = (TextView) mView.findViewById(R.id.distancefrom);
            mRideDistance = (TextView) mView.findViewById(R.id.txt_est_distance);
            mDate = (TextView) mView.findViewById(R.id.date);
            mTime = (TextView) mView.findViewById(R.id.time);
            mDiscription = (TextView) mView.findViewById(R.id.description);
            mBoxes = (TextView) mView.findViewById(R.id.Boxes);
            mWeight = (TextView) mView.findViewById(R.id.weight);
            mRatingBar = (RatingBar) mView.findViewById(R.id.c_rating);



        }
    }


}
