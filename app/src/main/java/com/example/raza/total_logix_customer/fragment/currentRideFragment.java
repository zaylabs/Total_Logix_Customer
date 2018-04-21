package com.example.raza.total_logix_customer.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.raza.total_logix_customer.DTO.customerHistory;
import com.example.raza.total_logix_customer.DTO.customerRequest;
import com.example.raza.total_logix_customer.DTO.userProfile;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.CurrentRideActivity;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class currentRideFragment extends android.app.Fragment{

private TextView mPickup, mDrop, mDistance, mVT, mfareestimate, mdriverloading;
private Spinner mNoOfBoxes_et, mWeight_et, mDescription_et;
/*
private MapView mapView;
private GoogleMap myMap;
*/
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
private Float ridedistance;
private String dropaddress;
private String pickupaddress;
private String uniqueID;
private String estFare;
private FirebaseUser user;
private float stars;
public currentRideFragment() {

        // Required empty public constructor
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_ride, container, false);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();


        mCancel=(Button)view.findViewById(R.id.btn_cancel);
        mConfirm=(Button)view.findViewById(R.id.btn_confirm);
        mPickup=(TextView)view.findViewById(R.id.txt_pickup);
        mDrop=(TextView)view.findViewById(R.id.txt_drop);
        mNoOfBoxes_et=(Spinner)view.findViewById(R.id.number_Boxes);
        mWeight_et=(Spinner)view.findViewById(R.id.weight);
        mDescription_et=(Spinner)view.findViewById(R.id.discription);
        mDistance=(TextView)view.findViewById(R.id.txt_distanceinKM);
        mVT=(TextView)view.findViewById(R.id.txt_vt);
        mfareestimate=(TextView)view.findViewById(R.id.txt_fare);
        mdriverloading=(TextView)view.findViewById(R.id.txt_driverloading);
//        mapView = (MapView) view.findViewById(R.id.mapview);


        pickupLatLng=((HomeActivity)getActivity()).mPickUpLatLng;
        dropLatLng= ((HomeActivity)getActivity()).mDropLatLng;
        mPickup.setText(((HomeActivity)getActivity()).mPickupString);
        mDrop.setText(((HomeActivity)getActivity()).mDropOffText.getText());

        mDistance.setText(((HomeActivity) getActivity()).mdistancekm);

        mVT.setText(((HomeActivity)getActivity()).vt);
        mdriverloading.setText(((HomeActivity)getActivity()).mDriverLoading);
        mfareestimate.setText(((HomeActivity)getActivity()).FareEstimate);
        pickup = new GeoPoint(pickupLatLng.latitude,pickupLatLng.longitude);
        drop=new GeoPoint(dropLatLng.latitude,dropLatLng.longitude);
        name = mName;
        phone = mPhone;
        date =  Calendar.getInstance().getTime();
        CID= userID;
        VT =mVT.getText().toString();

        driverloading = mdriverloading.getText().toString();
        ridedistance =((HomeActivity) getActivity()).distance ;
        dropaddress = mDrop.getText().toString();
        pickupaddress = mPickup.getText().toString();
        uniqueID= userID+date;
        estFare=mfareestimate.getText().toString();
            /*weight="3kg";
            boxes="Less than 5";
            description="Dry Items";
*/
     addItemOnWeight();
     addItemOnBoxes();
     addItemOnCargo();
     getCustomerInfo();


  //      mapView.getMapAsync(this);


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeActivity) getActivity()).mHeader.setVisibility(View.VISIBLE);
                ((HomeActivity) getActivity()).mFooter.setVisibility(View.VISIBLE);
                //((HomeActivity) getActivity()).setDrawerState(true);
                if (!((HomeActivity) getActivity()).sMapFragment.isAdded())
                    ((HomeActivity) getActivity()).sFm.beginTransaction().add(R.id.map, ((HomeActivity) getActivity()).sMapFragment).commit();
                else
                    ((HomeActivity) getActivity()).sFm.beginTransaction().show(((HomeActivity) getActivity()).sMapFragment).commit();

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              weight= mWeight_et.getSelectedItem().toString();
              boxes= mNoOfBoxes_et.getSelectedItem().toString();
              description= mDescription_et.getSelectedItem().toString();
                if (weight!=null && boxes != null) {

                    if (description!=null) {


                        user = FirebaseAuth.getInstance().getCurrentUser();

                        mCancel.setVisibility(View.INVISIBLE);


                        customerRequest customerRequest = new customerRequest(name, pickup, drop, phone, date, CID, VT, weight, boxes, description, driverloading, ridedistance, pickupaddress, dropaddress, estFare, uniqueID,stars);
                        customerHistory customerHistory = new customerHistory(name, pickup, drop, null, null, phone, date, CID, VT, weight, boxes, description, driverloading, ridedistance, pickupaddress, dropaddress, estFare, null, null, null, null, null, null, null, "Pending", null, null, null, null, uniqueID,null);

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



        return view;
    }

    private void currentRide() {

        Intent intent = new Intent(getActivity(), CurrentRideActivity.class);
        startActivity(intent);

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

    private void addItemOnBoxes() {
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.numberofboxes, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mNoOfBoxes_et.setAdapter(adapter2);


    }

    private void addItemOnWeight() {
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.weightinkg, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mWeight_et.setAdapter(adapter1);



    }


    /*@Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (getActivity() != null) {
            if (direction.isOK()) {
                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                myMap.addPolyline(DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED));
            } else {
                distanceAlert(direction.getErrorMessage());
            }

            myMap.addMarker(new MarkerOptions().position(new LatLng(pickupLatLng.latitude, pickupLatLng.longitude)).title("Pickup Location").snippet(mPickup.getText().toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            myMap.addMarker(new MarkerOptions().position(new LatLng(dropLatLng.latitude, dropLatLng.longitude)).title("Drop Location").snippet(mDrop.getText().toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupLatLng, 10));

        }
    }
*/
  /*  @Override
    public void onDirectionFailure(Throwable t) {
            distanceAlert(t.getMessage() + "\n" + t.getLocalizedMessage() + "\n");
        }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        requestDirection();
    }
    public void requestDirection() {

        // Snackbar.make(view, "calculating fare", Snackbar.LENGTH_INDEFINITE).show();
        GoogleDirection.withServerKey(getString(R.string.google_api_key))
                .from(pickupLatLng)
                .to(pickupLatLng)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    public void distanceAlert(String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getString(R.string.INVALID_DISTANCE));
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_warning_white_24dp);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.RED);
        alertDialog.setIcon(drawable);


        alertDialog.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.cancel();
            }
        });
        alert = alertDialog.create();
        alert.show();
    }
*/
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
