package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raza.total_logix_customer.DTO.dropLocationDTO;
import com.example.raza.total_logix_customer.R;

import java.util.ArrayList;
import java.util.List;


public class dropLocationAdapter extends RecyclerView.Adapter<dropLocationAdapter.ViewHolder>{

    private List<dropLocationDTO> mDropLocation;
    View mView;

    public dropLocationAdapter(List<dropLocationDTO> mDropLocation) {


        this.mDropLocation = mDropLocation;
    }

    @NonNull
    @Override
    public dropLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.droplocation_single_item,parent,false);



        return new dropLocationAdapter.ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull dropLocationAdapter.ViewHolder holder, int position) {


        holder.mDropAddress.setText(mDropLocation.get(position).getAddress());
        holder.mDropAddressNumber.setText("Drop Point"+"-"+mDropLocation.get(position).getStringdistance());

    }

    @Override
    public int getItemCount() {
        return mDropLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mDropAddress,mDropAddressNumber, mdistance;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        mDropAddress=(TextView)mView.findViewById(R.id.drop_multiaddressTV);
        mDropAddressNumber=(TextView)mView.findViewById(R.id.AddressHeaderTexView);
        }
    }
}
