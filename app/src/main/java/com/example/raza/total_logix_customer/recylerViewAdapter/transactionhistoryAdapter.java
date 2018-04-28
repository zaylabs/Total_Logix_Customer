package com.example.raza.total_logix_customer.recylerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.raza.total_logix_customer.DTO.transactionhistory;
import com.example.raza.total_logix_customer.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;



    public class transactionhistoryAdapter extends RecyclerView.Adapter<transactionhistoryAdapter.ViewHolder> {

        private final Context context;
        private final List<transactionhistory> dHistory;

        public transactionhistoryAdapter(Context context, List<transactionhistory> dHistory){

            this.context = context;
            this.dHistory = dHistory;
        }

        @NonNull
        @Override
        public transactionhistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionhistory_single_item,parent,false);
            return new transactionhistoryAdapter.ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull transactionhistoryAdapter.ViewHolder holder, int position) {

            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
            NumberFormat numberFormat = new DecimalFormat("'Rs.'#.##");


            holder.source.setText(dHistory.get(position).getSource());
            holder.currentbalance.setText(numberFormat.format(dHistory.get(position).getCurrentbalance()));
            holder.oldbalance.setText(numberFormat.format(dHistory.get(position).getOldbalance()));
            holder.amount.setText(numberFormat.format(dHistory.get(position).getAmount()));
            holder.dateupdated.setText(formatter.format(dHistory.get(position).getDateupdated()));


        }

        @Override
        public int getItemCount() {
            return dHistory.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView amount;
            private TextView dateupdated;
            private TextView source;
            private TextView oldbalance;
            private TextView currentbalance;
            View mView;


            public ViewHolder(View itemView) {
                super(itemView);
                mView= itemView;
            amount=mView.findViewById(R.id.th_addedbalance);
            dateupdated=mView.findViewById(R.id.th_dateupdated);
            source=mView.findViewById(R.id.th_source);
            oldbalance=mView.findViewById(R.id.th_oldbalance);
            currentbalance=mView.findViewById(R.id.th_updatedbalance);
            }
        }
    }



