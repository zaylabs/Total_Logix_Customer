package com.example.raza.total_logix_customer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.adapters.ExampleDialog;

public class CurrentRideActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListner{

    private TextView mNoOfBoxes, mWeight, mDescription, mDriverLoading,mVt,mEstimatedfare,mPickup,mDrop,mDistanceInKM;
    private Button mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);

        mNoOfBoxes=(TextView) findViewById(R.id.number_of_boxes);
        mWeight=(TextView) findViewById(R.id.weight);
        mDescription=(TextView) findViewById(R.id.discription);
        mDriverLoading=(TextView) findViewById(R.id.driverloading);
        mVt=(TextView) findViewById(R.id.vt);
        mEstimatedfare=(TextView) findViewById(R.id.estimated_fare);
        mPickup=(TextView) findViewById(R.id.pickuplocationsubmit);
        mDrop=(TextView) findViewById(R.id.droplocationsubmit);
        mDistanceInKM=(TextView) findViewById(R.id.distanceinkm);
        mDialog=(Button) findViewById(R.id.enter_info);

        mDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

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


    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Cargo Discription");
    }

}
