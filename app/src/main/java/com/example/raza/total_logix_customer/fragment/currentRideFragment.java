package com.example.raza.total_logix_customer.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.adapters.ExampleDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class currentRideFragment extends Fragment implements ExampleDialog.ExampleDialogListner {

    private TextView mNoOfBoxes, mWeight, mDescription, mDriverLoading,mVt,mEstimatedfare,mPickup,mDrop,mDistanceInKM;
    private Button mDialog;



    public currentRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_ride, container, false);

        mNoOfBoxes=(TextView) view.findViewById(R.id.number_of_boxes);
        mWeight=(TextView) view.findViewById(R.id.weight);
        mDescription=(TextView) view.findViewById(R.id.discription);
        mDriverLoading=(TextView) view.findViewById(R.id.driverloading);
        mVt=(TextView) view.findViewById(R.id.vt);
        mEstimatedfare=(TextView) view.findViewById(R.id.estimated_fare);
        mPickup=(TextView) view.findViewById(R.id.pickuplocationsubmit);
        mDrop=(TextView) view.findViewById(R.id.droplocationsubmit);
        mDistanceInKM=(TextView) view.findViewById(R.id.distanceinkm);
        mDialog=(Button) view.findViewById(R.id.enter_info);

        mDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // openDialog();
            }
        });





        return view;
    }


    @Override
    public void applyTexts(String noofboxes, String weight, String discription, String driverloading, String vt, String estimatedfare,String distanceinkms,String pickuptextaddress,String dropoffTextAddress) {

        mNoOfBoxes.setText(noofboxes);
        mWeight.setText(weight);
        mDescription.setText(discription);
        mDriverLoading.setText(driverloading);
        mVt.setText(vt);
        mEstimatedfare.setText(estimatedfare);
        mDistanceInKM.setText(distanceinkms);
        mPickup.setText(pickuptextaddress);
        mDrop.setText(dropoffTextAddress);

   }

/*
    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Cargo Discription");
    }*/

}
