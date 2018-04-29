package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.raza.total_logix_customer.DTO.customerHistory;
import com.example.raza.total_logix_customer.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class driverHistoryAdapter extends RecyclerView.Adapter<driverHistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<customerHistory> dHistory;
    private String mydate;
    private String mytime;
    private Date date;
    private float ridestars;
    private NumberFormat RsFormat = new DecimalFormat("'Rs.'#");
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
    public void onBindViewHolder(@NonNull driverHistoryAdapter.ViewHolder holder, int position) {

        date= dHistory.get(position).getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        mydate =DateFormat.getDateInstance().format(date);
        mytime = formatter.format(date);

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
        holder.mRideRating.setRating(dHistory.get(position).getRidestars());
        holder.mdate.setText(mydate);
        holder.mtime.setText(mytime);



    }

    @Override
    public int getItemCount() {
        return dHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView mName,mPickup,mDrop,mPhone, mstatus, mDiscription, mBoxes, mRideDistance,mWeight, mPaymentStatus,mPaidVia, mfare, mtime, mdate;
        public RatingBar mRideRating;

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
            mRideRating=(RatingBar)mView.findViewById(R.id.rideratingbar);
        }
    }
    }
