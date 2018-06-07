package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.example.raza.total_logix_customer.DTO.driverRating;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.fragment.acceptRequestFragment;
import com.example.raza.total_logix_customer.fragment.currentrideitemFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class currentRideAdapter extends RecyclerView.Adapter<currentRideAdapter.ViewHolder> {

    private final Context context;
private final List<acceptRequest> dHistory;
    private Dialog myDialog;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration driverratinglistner;
    private NumberFormat RsFormat = new DecimalFormat("'Rs.'#");
    private String userId;
    private FirebaseAuth mAuth;

    private float oldrating;
    private float totalride;
    private float newrating;
    private String driverId;
    private float updatedrating;



    public currentRideAdapter(Context context, List<acceptRequest> dHistory){


    this.context = context;
        this.dHistory = dHistory;
        }

@NonNull
@Override
public currentRideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_ride_single_item,parent,false);
        return new currentRideAdapter.ViewHolder(view);


        }

@Override
public void onBindViewHolder(@NonNull final currentRideAdapter.ViewHolder holder, final int position) {


    firestoreDB = FirebaseFirestore.getInstance();
    mAuth = FirebaseAuth.getInstance();
    userId = mAuth.getCurrentUser().getUid();

    final NumberFormat numberFormat = new DecimalFormat("#.##'KM'");
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
        NumberFormat watingFormat = new DecimalFormat("#'Mins'");
        String stringwaiting=watingFormat.format(dHistory.get(position).getWaitingtime());


    if (dHistory.get(position).getDriverdp()!=null) {
        Picasso.with(context).load(Uri.parse(dHistory.get(position).getDriverdp())).resize(150, 150).centerCrop().into(holder.driverdp);
    }
    holder.mName.setText(dHistory.get(position).getDrivername());
        holder.mPickup.setText(dHistory.get(position).getPickupaddress());
        holder.mDrop.setText(dHistory.get(position).getDropaddress());
        holder.mPhone.setText(dHistory.get(position).getDriverphone());
        holder.mstatus.setText(dHistory.get(position).getStatus());

    if (holder.mstatus.getText()=="Waiting for Payment"){

    }

holder.mTrack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        acceptRequest passacceptRequest= new acceptRequest((dHistory.get(position).getName()),dHistory.get(position).getOriginalpickup(), dHistory.get(position).getOriginaldrop(), dHistory.get(position).getActualpickup(), dHistory.get(position).getActualdrop(), dHistory.get(position).getPhone(), dHistory.get(position).getDate(), dHistory.get(position).getCID(), dHistory.get(position).getVT(), dHistory.get(position).getWeight(), dHistory.get(position).getBoxes(), dHistory.get(position).getDescription(), dHistory.get(position).getDriverloading(), dHistory.get(position).getRidedistance(), dHistory.get(position).getPickupaddress(), dHistory.get(position).getDropaddress(), dHistory.get(position).getEstFare(), dHistory.get(position).getDrivername(), dHistory.get(position).getDriverdp(), dHistory.get(position).getDrivernic(), dHistory.get(position).getDriverphone(), dHistory.get(position).getDriverlocation(), dHistory.get(position).getCarregno(), dHistory.get(position).getDriverid(), dHistory.get(position).getStatus(), dHistory.get(position).getRidefare(), dHistory.get(position).getPaidvia(), dHistory.get(position).getPaymentstatus(), dHistory.get(position).getDate(), dHistory.get(position).getWaitingtime(), dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate(),dHistory.get(position).getDroplocationUniqueID());

        android.support.v4.app.Fragment myFragment = new currentrideitemFragment();



        Bundle bundle=new Bundle();


        bundle.putParcelable("currentride", passacceptRequest);
      /*  bundle.putString("pickupLatLng",dHistory.get(position).getPickupaddress());
        bundle.putString("dropLatLng",dHistory.get(position).getDropaddress());
        bundle.putString("drivername",dHistory.get(position).getDrivername());
        bundle.putString("dirverphone",dHistory.get(position).getDriverphone());
        bundle.putString("driverid",dHistory.get(position).getDriverid());
        bundle.putString("status",dHistory.get(position).getStatus());
*/
        myFragment.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) context;

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.dm, myFragment).addToBackStack(null).commit();



    }

});
}

@Override
public int getItemCount() {
        return dHistory.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;
    public TextView mName,mPickup,mDrop,mPhone, mstatus, mDiscription, mBoxes, mRideDistance,mWeight, mPaymentStatus,mPaidVia, mfare,mDatetime,mEstRideDistance,mWaiting;
    public Button mCancel,mTrack;
    public de.hdodenhof.circleimageview.CircleImageView driverdp;

    public CardView mCardview;


    public ViewHolder(View itemView) {
        super(itemView);
        mView= itemView;
        mName=(TextView)mView.findViewById(R.id.cr_customername);
        mPickup=(TextView)mView.findViewById(R.id.cr_from_add);
        mDrop=(TextView)mView.findViewById(R.id.cr_to_add);
        mPhone=(TextView)mView.findViewById(R.id.cr_phone);
        mCardview=mView.findViewById(R.id.cardView);
        mstatus=mView.findViewById(R.id.cr__status);
        mTrack=mView.findViewById(R.id.trackbtn);
        driverdp=mView.findViewById(R.id.driverdp);
    }




}


    }


