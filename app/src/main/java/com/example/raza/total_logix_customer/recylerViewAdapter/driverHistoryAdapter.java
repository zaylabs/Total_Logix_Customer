package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.raza.total_logix_customer.DTO.customerHistory;
import com.example.raza.total_logix_customer.DTO.driverRating;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.example.raza.total_logix_customer.fragment.historyFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class driverHistoryAdapter extends RecyclerView.Adapter<driverHistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<customerHistory> dHistory;
    private String mydate;
    private String mytime;
    private Date date;
    private float ridestars;
    private NumberFormat RsFormat = new DecimalFormat("'Rs.'#");
    private Dialog myDialog;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration driverratinglistner;
    private String userId;
    private FirebaseAuth mAuth;

    private float oldrating;
    private float totalride;
    private float newrating;
    private String driverId;
    private float updatedrating;



    public driverHistoryAdapter(Context context, List<customerHistory> dHistory){




        this.context = context;
        this.dHistory = dHistory;
    }

    @NonNull
    @Override
    public driverHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driverhistory_single_item,parent,false);
        return new driverHistoryAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull driverHistoryAdapter.ViewHolder holder, final int position) {

        date= dHistory.get(position).getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        mydate =DateFormat.getDateInstance().format(date);
        mytime = formatter.format(date);

        firestoreDB = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();


        holder.mName.setText(dHistory.get(position).getDrivername());
        holder.mPickup.setText(dHistory.get(position).getPickupaddress());
        holder.mDrop.setText(dHistory.get(position).getDropaddress());
        holder.mPhone.setText(dHistory.get(position).getDriverphone());
        holder.mstatus.setText(dHistory.get(position).getStatus());
        holder.mRideDistance.setText(String.valueOf(dHistory.get(position).getRidedistance()));
        holder.mDiscription.setText(dHistory.get(position).getDescription());
        holder.mBoxes.setText(dHistory.get(position).getBoxes());
        holder.mWeight.setText(dHistory.get(position).getWeight());
        
        holder.mPaidVia.setText(dHistory.get(position).getPaidvia());
        holder.mfare.setText(RsFormat.format(dHistory.get(position).getRidefare()));
        holder.mCustomerRideRating.setRating(dHistory.get(position).getCustomerridestars());
        holder.mDriverRideRating.setRating(dHistory.get(position).getDriverridestars());
        holder.mdate.setText(mydate);
        holder.mtime.setText(mytime);

        holder.mRateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dHistory.get(position).getDriverridestars()!=0){
                    Toast.makeText(context, "You have already rated", Toast.LENGTH_SHORT).show();
                }else{
                    myDialog = new Dialog(context);
                    myDialog.setContentView(R.layout.driver_rating_dialog);
                    myDialog.show();
                    myDialog.setCancelable(true);
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

                                    Map<String, Object> rideupdate = new HashMap<>();
                                    rideupdate.put("driverridestars",newrating);

                                    firestoreDB.collection("DriverHistory").document(dHistory.get(position).getUniqueID())
                                            .update(rideupdate);


                                    myDialog.dismiss();

                                    notifyDataSetChanged();

                                }
                            });


                        }
                    });


                }


            }

        });

    }

    @Override
    public int getItemCount() {
        return dHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView mName,mPickup,mDrop,mPhone, mstatus, mDiscription, mBoxes, mRideDistance,mWeight, mPaymentStatus,mPaidVia, mfare, mtime, mdate;
        public RatingBar mCustomerRideRating,mDriverRideRating;
        public LinearLayout mRateDriver;

        public ViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
            mName=(TextView)mView.findViewById(R.id.dh_customername);
            mPickup=(TextView)mView.findViewById(R.id.dh_from_add);
            mDrop=(TextView)mView.findViewById(R.id.dh_to_add);
            mPhone=(TextView)mView.findViewById(R.id.dh_phone);
            mstatus=(TextView)mView.findViewById(R.id.dh__status);
            mRideDistance=(TextView)mView.findViewById(R.id.dh_totaldistance);
            mDiscription=(TextView)mView.findViewById(R.id.dh_description);

            mPaidVia=(TextView)mView.findViewById(R.id.dh_paidvia);
            mWeight=(TextView)mView.findViewById(R.id.dh_weight);
            mBoxes=(TextView)mView.findViewById(R.id.dh_boxes);
            mfare=(TextView)mView.findViewById(R.id.dh_fare);
            mtime=(TextView)mView.findViewById(R.id.dh_time);
            mdate=(TextView)mView.findViewById(R.id.dh_date);
            mCustomerRideRating=(RatingBar)mView.findViewById(R.id.customerrideratingbar);
            mDriverRideRating=(RatingBar)mView.findViewById(R.id.driverrideratingbar);
            mRateDriver=(LinearLayout)mView.findViewById(R.id.updateratinglinear);
        }
    }
    }
