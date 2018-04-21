package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.example.raza.total_logix_customer.R;

import java.util.List;

public class currentRideAdapter extends RecyclerView.Adapter<currentRideAdapter.ViewHolder> {

private final Context context;
private final List<acceptRequest> dHistory;

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
public void onBindViewHolder(@NonNull currentRideAdapter.ViewHolder holder, int position) {

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
        holder.mfare.setText(dHistory.get(position).getRidefare());
        holder.mEstRideDistance.setText(String.valueOf(dHistory.get(position).getEstDistance()));
        holder.mDatetime.setText(String.valueOf(dHistory.get(position).getDate()));





}

@Override
public int getItemCount() {
        return dHistory.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{

    View mView;
    public TextView mName,mPickup,mDrop,mPhone, mstatus, mDiscription, mBoxes, mRideDistance,mWeight, mPaymentStatus,mPaidVia, mfare,mDatetime,mEstRideDistance;
    public Button mCancel;

    public ViewHolder(View itemView) {
        super(itemView);
        mView= itemView;
        mName=(TextView)mView.findViewById(R.id.cr_customername);
        mPickup=(TextView)mView.findViewById(R.id.cr_from_add);
        mDrop=(TextView)mView.findViewById(R.id.cr_to_add);
        mPhone=(TextView)mView.findViewById(R.id.cr_phone);
        mstatus=(TextView)mView.findViewById(R.id.cr__status);
        mRideDistance=(TextView)mView.findViewById(R.id.cr__totaldistance);
        mDiscription=(TextView)mView.findViewById(R.id.cr_description);
        mDatetime= mView.findViewById(R.id.cr_datetime);
        mPaidVia= mView.findViewById(R.id.cr_paidvia);
        mWeight= mView.findViewById(R.id.cr_weight);
        mBoxes= mView.findViewById(R.id.cr_boxes);
        mfare= mView.findViewById(R.id.cr_fare);
        mEstRideDistance=mView.findViewById(R.id.cr_estdistance);


    }
}
    }


