package com.example.raza.total_logix_customer.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.example.raza.total_logix_customer.DTO.driverAvailable;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class currentrideitemFragment extends Fragment implements OnMapReadyCallback {

    private FirebaseFirestore firestoreDB;
    private ListenerRegistration driveravailablelistner;
    View mView;
    GoogleMap mGoogleMap;
    MapView mMapView;
    TextView mdrivername;
    TextView mPickupLocation;
    TextView mDropLoaction;
    TextView mPhoneNumber;
    String mdriverID;
    de.hdodenhof.circleimageview.CircleImageView mdriverdp;
    acceptRequest acceptRequest;
    GeoPoint driverlocation;
    LatLng driverlatlng;

    public currentrideitemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        acceptRequest=bundle.getParcelable("currentride");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_currentrideitem, container, false);

        mdriverID=acceptRequest.getDriverid();

        firestoreDB = FirebaseFirestore.getInstance();
        mdrivername=(TextView)mView.findViewById(R.id.footer_drivername);
        mPhoneNumber=(TextView)mView.findViewById(R.id.footercr_phone);
        mPickupLocation=(TextView)mView.findViewById(R.id.footercr_from_add);
        mDropLoaction=(TextView)mView.findViewById(R.id.footercr_to_add);
        mdriverdp=(de.hdodenhof.circleimageview.CircleImageView)mView.findViewById(R.id.footerdriverdp);


        mdrivername.setText(acceptRequest.getDrivername());
        mPhoneNumber.setText(acceptRequest.getPhone());
        mPickupLocation.setText(acceptRequest.getPickupaddress());
        mDropLoaction.setText(acceptRequest.getDropaddress());
        Picasso.with(getContext()).load(Uri.parse(acceptRequest.getDriverdp())).resize(150,150).centerCrop().into(mdriverdp);



        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView=(MapView)mView.findViewById(R.id.drivermapview);
        if (mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(Objects.requireNonNull(getContext()));
        mGoogleMap=googleMap;

        driveravailablelistner=firestoreDB.collection("driveravailable").document(mdriverID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (documentSnapshot!=null){
                    driverAvailable driverAvailable = documentSnapshot.toObject(com.example.raza.total_logix_customer.DTO.driverAvailable.class);
                    assert driverAvailable != null;
                    driverlocation=driverAvailable.getDriverLocation();
                    driverlatlng= new LatLng(driverlocation.getLatitude(),driverlocation.getLongitude());
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mGoogleMap.addMarker(new MarkerOptions().position(driverlatlng).
                            title("Title").snippet("TitleName"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(driverlatlng).zoom(18).build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                            (cameraPosition ));

                }
            }
        });






    }
}
