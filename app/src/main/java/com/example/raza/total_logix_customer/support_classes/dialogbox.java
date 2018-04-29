package com.example.raza.total_logix_customer.support_classes;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raza.total_logix_customer.DTO.customerHistory;
import com.example.raza.total_logix_customer.DTO.customerRequest;
import com.example.raza.total_logix_customer.DTO.userProfile;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.CurrentRideActivity;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;


public class dialogbox extends AppCompatDialogFragment {


    private Spinner mDescription_et;

    private com.travijuu.numberpicker.library.NumberPicker mNoBoxes, mWeight_picker;
    private Button mGatepass;

    private int boxes_val;
    private int weight_val;
    private LatLng pickupLatLng,dropLatLng;
    private AlertDialog alert;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;
    private Button mCancel, mConfirm;
    private String mName;
    private String mPhone;
    private GeoPoint pickup;
    private GeoPoint drop;
    private String name;
    private String phone;
    private Date date;
    private String CID;
    private String VT;
    private String weight;

    private String boxes;
    private String description;
    private String driverloading;
    private float ridedistance;
    private String dropaddress;
    private String pickupaddress;
    public String uniqueID;
    private String estFare;
    private FirebaseUser user;
    private float stars;
    private float ridestar;
    private String gatepass;
    private FrameLayout mHeader;
    private FrameLayout mFooter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());




        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.additional_detail_dialog,null);


        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mWeight_picker=(com.travijuu.numberpicker.library.NumberPicker)view.findViewById(R.id.weight_picker);
        mNoBoxes=(com.travijuu.numberpicker.library.NumberPicker)view.findViewById(R.id.number_Boxes_picker);
        mDescription_et=(Spinner)view.findViewById(R.id.discription);
        mGatepass=(Button)view.findViewById(R.id.gatepass);
        mHeader=((HomeActivity)getActivity()).mHeader;
        mFooter=((HomeActivity)getActivity()).mFooter;
        pickupLatLng=((HomeActivity)getActivity()).mPickUpLatLng;
        dropLatLng= ((HomeActivity)getActivity()).mDropLatLng;
        pickup = new GeoPoint(pickupLatLng.latitude,pickupLatLng.longitude);
        drop=new GeoPoint(dropLatLng.latitude,dropLatLng.longitude);

        ridedistance=((HomeActivity) getActivity()).distance;


        boxes_val=mNoBoxes.getValue();

        weight_val=mWeight_picker.getValue();


        date =  Calendar.getInstance().getTime();
        CID= userID;
        uniqueID= userID+date;
        estFare = ((HomeActivity) getActivity()).FareEstimate;
        driverloading=((HomeActivity) getActivity()).mDriverLoading;
        VT=((HomeActivity)getActivity()).vt;
        ridestar = 0;

        pickupaddress= ((HomeActivity)getActivity()).mPickupAddress;
        dropaddress=((HomeActivity)getActivity()).mDropoffAddress;


        mNoBoxes.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                boxes_val=mNoBoxes.getValue();

            }
        });

        mNoBoxes.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                weight_val=mWeight_picker.getValue();

            }
        });


        addItemOnCargo();
        getCustomerInfo();

        builder.setTitle("Additional Details");

        builder.setView(view)



                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHeader.setVisibility(View.VISIBLE);
                        mFooter.setVisibility(View.VISIBLE);

                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gatepass="";

                        weight= String.valueOf(mNoBoxes.getValue())+"KG";
                        boxes= String.valueOf(mWeight_picker.getValue());

                        if (weight!=null && boxes != null) {
                        if (description!=null) {


                            user = FirebaseAuth.getInstance().getCurrentUser();




                            customerRequest customerRequest = new customerRequest(name, pickup, drop, phone, date, CID, VT, weight, boxes, description, driverloading, ridedistance, pickupaddress, dropaddress, estFare, uniqueID,stars,gatepass);
                            customerHistory customerHistory = new customerHistory(name, pickup, drop, null, null, phone, date, CID, VT, weight, boxes, description, driverloading, ridedistance, pickupaddress, dropaddress, estFare, null, null, null, null, null, null, null, "Pending", null, null, null, 0, uniqueID,null,ridestar,ridedistance,gatepass);

                            db.collection("customerRequest").document(uniqueID).set(customerRequest);
                            db.collection("CustomerHistory").document(uniqueID).set(customerHistory);
                            currentRide();
                        }else {
                            Toast.makeText(getContext(), "Kindly Select Cargo Type", Toast.LENGTH_LONG).show();
                        }
                        }else {
                        Toast.makeText(getContext(), "Kindly Select weight & No.of Boxes", Toast.LENGTH_LONG).show();
                        }


                }
                });

        return builder.create();




    }




    private void addItemOnCargo() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(), R.array.cargotype, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mDescription_et.setAdapter(adapter3);
        description= mDescription_et.getSelectedItem().toString();

    }

    private void currentRide() {

        Intent intent = new Intent(getActivity(), CurrentRideActivity.class);
        startActivity(intent);

    }

    private void getCustomerInfo(){
        DocumentReference docRef = db.collection("customers").document(userID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userProfile profile = documentSnapshot.toObject(userProfile.class);
                name =profile.getName();
                phone=profile.getPhone();
                stars=profile.getStars();


            }
        });

    }

}


