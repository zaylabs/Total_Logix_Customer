package com.example.raza.total_logix_customer.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_customer.BuildConfig;
import com.example.raza.total_logix_customer.DTO.dropLocationDTO;
import com.example.raza.total_logix_customer.DTO.homeinfoPass;
import com.example.raza.total_logix_customer.DTO.settings;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.adapters.HttpDataHandler;
import com.example.raza.total_logix_customer.adapters.PlaceAutocompleteAdapter;

import com.example.raza.total_logix_customer.fragment.BlankFragment;
import com.example.raza.total_logix_customer.fragment.acceptRequestFragment;
import com.example.raza.total_logix_customer.fragment.currentrideitemFragment;
import com.example.raza.total_logix_customer.fragment.customerRequestFragment;
import com.example.raza.total_logix_customer.fragment.helpFragment;
import com.example.raza.total_logix_customer.fragment.historyFragment;
import com.example.raza.total_logix_customer.fragment.hovermapFragment;
import com.example.raza.total_logix_customer.fragment.pendingRequestFragment;
import com.example.raza.total_logix_customer.fragment.profileFragment;
import com.example.raza.total_logix_customer.fragment.transactionhistoryFragment;
import com.example.raza.total_logix_customer.fragment.walletFragment;
import com.example.raza.total_logix_customer.recylerViewAdapter.dropLocationAdapter;
import com.example.raza.total_logix_customer.support_classes.PermissionUtils;
import com.example.raza.total_logix_customer.support_classes.dialogbox;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,ActivityCompat.OnRequestPermissionsResultCallback {


    public String mdistancekm;

    public android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();




    private FirebaseFirestore db;
    private Place mPlacePickup, mPlaceDrop;
    public FrameLayout mHeader;
    public FrameLayout mFooter;
    private LinearLayout mRideNow;
    private LinearLayout mBookNow;
    private Button mAddDrop;
    private RecyclerView mDropRecylerView;
    private GoogleApiClient mGoogleApiClient;
    public List<dropLocationDTO> mDropLocation= new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView.Adapter mDropAdapter;
    public String LastDropPoint;
    public LatLng LastDropPointLatLng;
    public Float LastDropPointDistance;
    private int DropLocationSize;

    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    public AutoCompleteTextView mPickupText, mDropOffText;
    public String mPickupAddress, mDropoffAddress;
    private TextView mPickUpDetailsText;
    private TextView mPickUpAttribution;
    private TextView mDropOffDetailsText;
    private TextView mDropOffAttribution;
    public LatLng mPickUpLatLng, mDropLatLng;
    private PlaceDetectionClient mPlaceDetectionClient;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));


    //Maps
    private Marker mPickupMarker, mDropMarker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    public GoogleMap mMap;
    public SupportMapFragment sMapFragment;
    public String mDropName;


    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    /*Location Update*/
    private static final String TAGLoc = HomeActivity.class.getSimpleName();
    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;
    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;
    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;
    /**
     * Represents a geographical location.
     */
    private Location mCurrentLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;
    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;

    //*******************************Location Update End********************************

    public Float distance;

    private TextView mNameField, mFareEstimate;
    private ImageView  mMyLocation, mClear;
    private de.hdodenhof.circleimageview.CircleImageView mDisplayPic;
    private static final String TAG = "MyActivity";

    private Date date;
    public String droplocationUniqueID;
    public double lat, lng;
    public DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private homeinfoPass pass;
    private Spinner mNoOfBoxes_et, mWeight_et, mDescription_et;
    private co.ceryle.radiorealbutton.RadioRealButtonGroup mRadioGroup;
    private co.ceryle.radiorealbutton.RadioRealButton mCarType1;
    private co.ceryle.radiorealbutton.RadioRealButton mCarType2;
    private CheckBox mDriverLoading_et;
    public String mDriverLoading;
    public String vt;
    private int SuzukiRate;
    private int RikshaRate;
    private int SuzukiBase;
    private int RikshaBase;
    private int DriverLoadingRate;
    private int DropStopRates;
    public String FareEstimate;
    public String mPickupString;
    public String mDropString;
    private TextView mFareEstimateLabel;
    private ListenerRegistration settingslistner;
    private dropLocationDTO dropLocationDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHeader = findViewById(R.id.headerframe);
        mPickupText = findViewById(R.id.pickup_location);
        mDropOffText = findViewById(R.id.drop_location);
        mMyLocation = findViewById(R.id.current_location);
        mClear = findViewById(R.id.clear);
        mDropRecylerView=findViewById(R.id.drop_RecyclerView);
        mAddDrop=findViewById(R.id.adddrop);


        mDropRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mDropAdapter=new dropLocationAdapter(mDropLocation);

        mDropRecylerView.setLayoutManager(mLayoutManager);
        mDropRecylerView.setAdapter(mDropAdapter);
/*


        mAddDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

                if (mDropoffAddress!=null){
                    for(int i = 0; i < mDropLocation.size();i++){


                    }
                    dropLocationDTO =new dropLocationDTO(mDropoffAddress,userID,distance,distanceInKM());
                    mDropLocation.add(dropLocationDTO);


                    mDropOffText.setText("");
                    mFareEstimate.setText("");
                    mDropoffAddress=null;
                }

                for(int i = 0; i < mDropLocation.size();i++){
                     Log.d(TAG,"onCreate:"+mDropLocation.get(i));

                 }
                Collections.sort(mDropLocation,dropLocationDTO.BY_Distance);
                if (mDropLocation!=null&&!mDropLocation.isEmpty()){

                        item = (mDropLocation.get(mDropLocation.size()-1)).getAddress();

                }
                Toast.makeText(HomeActivity.this, item, Toast.LENGTH_SHORT).show();
                Log.d(TAG,item);
                mDropAdapter.notifyDataSetChanged();
            }
        });
*/


        mFooter = findViewById(R.id.footerframe);
        mRideNow = findViewById(R.id.linear_request);
        mBookNow = findViewById(R.id.linear_booking);
        mDriverLoading_et=(CheckBox)findViewById(R.id.driver_loading);
        mRadioGroup = findViewById(R.id.radiogroup_VType);
        mCarType1 = findViewById(R.id.radio_CarType1);
        mCarType2 = findViewById(R.id.radio_CarType2);
        mFareEstimate=findViewById(R.id.fareestimate_tv);
        mFareEstimateLabel=findViewById(R.id.fareest_lble);
        db = FirebaseFirestore.getInstance();
        settingslistner=db.collection("settings").document("rates").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                settings settings = documentSnapshot.toObject(settings.class);
                SuzukiRate = settings.getSuzukirate();
                RikshaRate = settings.getRiksharate();
                SuzukiBase = settings.getSuzukibase();
                RikshaBase = settings.getRikshabase();
                DropStopRates=settings.getDropstoprates();
                DriverLoadingRate = settings.getDriverloadingRate();

            }
        });




        if (mCarType1.isChecked()) {
            vt="Suzuki";
            if (distance!=null) {
                fareCarCalculator();
            }
        } else if (mCarType2.isChecked()){
            vt = "Riksha";

            if (distance!=null) {
                fareCarCalculator();
            }
        }

        mDriverLoading_et.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!(mDriverLoading_et.isChecked())){
                    mDriverLoading = "Not Needed";
                    if (mDropOffText!=null) {
                        if (distance!=null) {
                            fareCarCalculator();
                        }
                    }
                } else {
                    mDriverLoading="Needed";
                    if (mDropOffText!=null) {
                        if (distance!=null) {
                            fareCarCalculator();
                        }
                    }
                }
            }
        });



        mRadioGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
               // Toast.makeText(HomeActivity.this, "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                if (position!=1){
                    vt=("Suzuki");
                    if (distance!=null) {
                        fareCarCalculator();
                    }
                }else {
                    vt=("Riksha");
                    if (distance!=null) {
                        fareCarCalculator();
                    }
                }

            }
        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);


        mNameField = hView.findViewById(R.id.txt_name);
        mDisplayPic = hView.findViewById(R.id.profile);

        navigationView.getMenu().getItem(0).setCheckable(true);
        FragmentManager fm = getFragmentManager();
        final android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        if (!sMapFragment.isAdded()) {
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        } else {
            sFm.beginTransaction().show(sMapFragment).commit();
        }


        sMapFragment.getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(HomeActivity.this, "User is Signed In", Toast.LENGTH_SHORT).show();
                    getUserInfo();
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        //AutoComplete Google Start

        // Construct a GeoDataClient for the Google Places API for Android.
        mGeoDataClient = Places.getGeoDataClient(this);
        //Auto Place Google End


        mMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentLocation != null) {
                    mPickUpLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    lat = mPickUpLatLng.latitude;
                    lng = mPickUpLatLng.longitude;
                    new GetAddress().execute(String.format("%.4f,%.4f",lat,lng));

                    CameraUpdate mCameraCL = CameraUpdateFactory.newLatLngZoom(mPickUpLatLng, 18);


                    mMap.animateCamera(mCameraCL);
                    if (mPickupMarker != null) {
                        mPickupMarker.remove();
                    }

                    mPickupMarker = mMap.addMarker(new MarkerOptions().position(mPickUpLatLng)
                            .title(mPickUpLatLng.toString()).draggable(true));


                }
            }

        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropOffText.setText("");
                mFareEstimate.setText("");


            }

        });




        mPickupText.setOnItemClickListener(mPickupTextClickListener);
        mDropOffText.setOnItemClickListener(mDropOffTextClickListener);

        mPickUpDetailsText = findViewById(R.id.pickup_details);
        mPickUpAttribution = findViewById(R.id.pickup_attribution);

        mDropOffDetailsText = findViewById(R.id.dropoff_details);
        mDropOffAttribution = findViewById(R.id.dropoff_attribution);


        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("PK")
                .build();

        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, BOUNDS_GREATER_SYDNEY, typeFilter);
        mPickupText.setAdapter(mAdapter);
        mDropOffText.setAdapter(mAdapter);




        //Auto Complete Google End

        // *********************Location Update ************************************

     /*   // Locate the UI widgets.
        *//*mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);*//*
        *//*mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);*//*
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);

        // Set labels.
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);
*/
        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();


        //**************************************Location update End ****************************8

        mRideNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mPickUpLatLng != null && LastDropPointLatLng != null) {
                        if (vt != null) {
                            if (!(mDriverLoading_et.isChecked())) {
                                mDriverLoading = "Not Needed";
                            } else {
                                mDriverLoading = "Needed";
                            }
                            mHeader.setVisibility(GONE);
                            mFooter.setVisibility(GONE);
                            if (sMapFragment.isAdded()){
                                sFm.beginTransaction().hide(sMapFragment).commit();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.cm, new customerRequestFragment());
                            ft.commit();
                        }else {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.cm, new customerRequestFragment());
                            ft.commit();
                        }
                        } else
                            Toast.makeText(HomeActivity.this, R.string.Vahicle_Type_Error, Toast.LENGTH_LONG).show();
                        }
                        else {
                        Toast.makeText(HomeActivity.this, getString(R.string.invalid_location), Toast.LENGTH_LONG).show();
                    }
                }
                });




    }
/**//*

    private void openDialog() {
            dialogbox dialogbox = new dialogbox();
            dialogbox.show(getSupportFragmentManager(), "Additional Details");

        }

*/


    public void fareCarCalculator() {
        Double result = 0.0;
        Double b=0.0;
        Double c=3.0;
        int d=0;
        if (DropLocationSize>1){
            d=(DropLocationSize-1)*DropStopRates;
        }

        Double a = Double.parseDouble(LastDropPointDistance.toString());
        if (!(mCarType2.isChecked())) {
            if (a<c){
                b= Double.valueOf(SuzukiBase);
                if ((mDriverLoading_et.isChecked())) {
                    result = b + DriverLoadingRate+d;
                } else {
                    result = b+d;
                }
            }else {
                b = (a * SuzukiRate);
                if ((mDriverLoading_et.isChecked())) {
                    result = b + DriverLoadingRate+d;
                } else {
                    result = b+d;
                }
            }

        } else if (!(mCarType1.isChecked())) {
            if (a<c) {
                b = Double.valueOf(RikshaBase);
                if ((mDriverLoading_et.isChecked())) {
                    result = b + DriverLoadingRate+d;
                } else {
                    result = b+d;
                }
            }else{
                b=(a * RikshaRate);
                if ((mDriverLoading_et.isChecked())) {
                    result = b + DriverLoadingRate+d;
                } else {
                    result = b+d;
                }
                }
        }
        NumberFormat numberFormat = new DecimalFormat("'Rs.'#");
        mFareEstimateLabel.setVisibility(View.VISIBLE);
        FareEstimate = numberFormat.format(result);
        mFareEstimate.setText(FareEstimate);
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fm = getFragmentManager();
        int id = item.getItemId();



        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();
            FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.home:
                mAuth.addAuthStateListener(firebaseAuthListener);
                mHeader.setVisibility(View.VISIBLE);
                mFooter.setVisibility(View.VISIBLE);
                mDropLocation.clear();
                mDropAdapter.notifyDataSetChanged();
                if (!sMapFragment.isAdded()) {
                    sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
                }else{
                        sFm.beginTransaction().show(sMapFragment).commit();
                }

                break;

            case R.id.completed_rides:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                ft.replace(R.id.cm, new historyFragment());
                ft.commit();

                break;

            case R.id.pending_request:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                ft.replace(R.id.cm, new pendingRequestFragment());
                ft.commit();

                break;

            case R.id.current_ride:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                    ft.replace(R.id.cm, new BlankFragment());
                    ft.commit();


                break;


            case R.id.transactionhistory:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                ft.replace(R.id.cm, new transactionhistoryFragment());
                ft.commit();

                break;
            case R.id.wallet:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                ft.replace(R.id.cm, new walletFragment());
                ft.commit();
                break;
            case R.id.profile:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                ft.replace(R.id.cm, new profileFragment());
                ft.commit();
                break;
            case R.id.help:
                mHeader.setVisibility(GONE);
                mFooter.setVisibility(GONE);


                ft.replace(R.id.cm, new hovermapFragment() );
                ft.commit();

                break;

            case R.id.logout:
                settingslistner.remove();
                mAuth.signOut();

                break;

            default:

                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
        startLocationUpdates();



    }

    @Override
    protected void onDestroy() {
     //   mAuth.removeAuthStateListener(firebaseAuthListener);
        //stopLocationUpdates();
        super.onDestroy();
        stopLocationUpdates();
        settingslistner.remove();
        mAuth.removeAuthStateListener(firebaseAuthListener);




    }



    @Override
    protected void onStop() {
        mAuth.removeAuthStateListener(firebaseAuthListener);
        //stopLocationUpdates();
        super.onStop();

    }

    private void getUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            mNameField.setText(name);

            if (user.getPhotoUrl() != null) {
                String photodp = user.getPhotoUrl().toString();
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View hView = navigationView.getHeaderView(0);
                navigationView.setNavigationItemSelectedListener(this);
                Picasso.with(hView.getContext()).load(photodp).resize(150, 150).centerCrop().into(mDisplayPic);
            }
        }

    }




    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        enableMyLocation();
        setOnMyLocationButtonClick();
        setOnMyLocationClick();
        mHeader.setVisibility(View.VISIBLE);
        mFooter.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if (mCurrentLocation!=null){
                mPickUpLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                lat = mPickUpLatLng.latitude;
                lng = mPickUpLatLng.longitude;
                /*    new GetAddress().execute(String.format("%.4f,%.4f", lat, lng));*/
                CameraUpdate mCameraCL = CameraUpdateFactory.newLatLngZoom(mPickUpLatLng, 18);
                mMap.animateCamera(mCameraCL);
                if (mPickupMarker != null) {
                    mPickupMarker.remove();
                }

                mPickupMarker = mMap.addMarker(new MarkerOptions().position(mPickUpLatLng)
                        .title(mPickUpLatLng.toString()).draggable(true));
            }}
        }, 8000);



    }
    private void setOnMyLocationButtonClick() {
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(HomeActivity.this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                // Return false so that we don't consume the event and the default behavior still occurs
                // (the camera animates to the user's current position).
                return false;
            }

        });
    }


    private void setOnMyLocationClick() {
        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(HomeActivity.this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(HomeActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            startLocationUpdates();

        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }

    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    //Auto Complete Google Start
    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data Client
     * to retrieve more details about the place.
     *
     * @see GeoDataClient#getPlaceById(String...)
     */
    private AdapterView.OnItemClickListener mPickupTextClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            //     Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
            placeResult.addOnCompleteListener(mUpdatePickUpDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            // Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

        }
    };
    private AdapterView.OnItemClickListener mDropOffTextClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
            placeResult.addOnCompleteListener(mUpdateDropOffDetailsCallback);
            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
        }
    };

    // Callback for results from a Places Geo Data Client query that shows the first place result in
    // the details view on screen.
    private OnCompleteListener<PlaceBufferResponse> mUpdatePickUpDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onComplete(Task<PlaceBufferResponse> task) {
            try {
                PlaceBufferResponse places = task.getResult();
                // Get the Place object from the buffer.
                @SuppressLint("RestrictedApi") final Place place = places.get(0);
                // Format details of the place for display and show it in a TextView.
                mPickUpDetailsText.setText(formatPickUpDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));

                mPickUpLatLng = place.getLatLng();
                mPickupAddress= place.getAddress().toString();
                mPickupString=  mPickupText.getText().toString();

                CameraUpdate mCameraCL = CameraUpdateFactory.newLatLngZoom(mPickUpLatLng, 18);
                // mMap.moveCamera(mCameraCL);
                mMap.animateCamera(mCameraCL);
                if(mPickupMarker != null){
                    mPickupMarker.remove();
                }
                mPickupMarker = mMap.addMarker(new MarkerOptions().position(mPickUpLatLng)
                        .title(place.getName().toString()).draggable(true));
                // Display the third party attributions if set.
                final CharSequence thirdPartyAttribution = places.getAttributions();
                if (thirdPartyAttribution == null) {
                    mPickUpAttribution.setVisibility(GONE);
                } else {
                    mPickUpAttribution.setVisibility(View.VISIBLE);
                    mPickUpAttribution.setText(
                            Html.fromHtml(thirdPartyAttribution.toString()));
                }
                places.release();
            } catch (RuntimeRemoteException e) {
                // Request did not complete successfully
                //           Log.e(TAG, "Place query did not complete.", e);
                //          return;
            }
        }
    };

    private static Spanned formatPickUpDetails(Resources res, CharSequence name, String id,
                                               CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
    }

    // Dropoff
    private OnCompleteListener<PlaceBufferResponse> mUpdateDropOffDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onComplete(Task<PlaceBufferResponse> task) {
            try {
                PlaceBufferResponse places = task.getResult();
                // Get the Place object from the buffer.
                @SuppressLint("RestrictedApi") final Place drop = places.get(0);
                // Format details of the place for display and show it in a TextView.
                mDropOffDetailsText.setText(formatDropOffDetails(getResources(), drop.getName(),
                        drop.getId(), drop.getAddress(), drop.getPhoneNumber(),
                        drop.getWebsiteUri()));
                mDropLatLng = drop.getLatLng();
                mDropName= drop.getName().toString();
                mDropoffAddress=drop.getAddress().toString();
                if(mDropMarker != null){
                    mDropMarker.remove();
                }
                mDropMarker = mMap.addMarker(new MarkerOptions().position(mDropLatLng)
                        .title(drop.getName().toString()).draggable(true));
                mdistancekm=distanceInKM();

                Toast.makeText(HomeActivity.this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                date =  Calendar.getInstance().getTime();
                droplocationUniqueID=userID+date;
                if (mDropoffAddress!=null) {
                    dropLocationDTO = new dropLocationDTO(mDropoffAddress, userID, droplocationUniqueID,distance, distanceInKM(),mDropLatLng);
                    mDropLocation.add(dropLocationDTO);
                    mDropOffText.setText("");

                    mDropoffAddress = null;
                }

                Collections.sort(mDropLocation,dropLocationDTO.BY_Distance);
                if (mDropLocation!=null&&!mDropLocation.isEmpty()){
                    LastDropPoint = (mDropLocation.get(mDropLocation.size()-1)).getAddress();
                    LastDropPointLatLng=(mDropLocation.get(mDropLocation.size()-1)).getDroplocation();
                    LastDropPointDistance=(mDropLocation.get(mDropLocation.size()-1)).getDistance();
                    DropLocationSize= mDropLocation.size();
                    mDropAdapter.notifyDataSetChanged();
                    Log.d(TAG,"onCreate:"+DropLocationSize+"-"+LastDropPointLatLng+"-"+LastDropPointDistance);
                    Toast.makeText(HomeActivity.this,DropLocationSize +LastDropPointDistance+ LastDropPointLatLng.toString(), Toast.LENGTH_LONG).show();
                    fareCarCalculator();

                }



                // Display the third party attributions if set.
                final CharSequence thirdPartyAttribution = places.getAttributions();
                if (thirdPartyAttribution == null) {
                    mDropOffAttribution.setVisibility(GONE);
                } else {
                    mDropOffAttribution.setVisibility(View.VISIBLE);
                    mDropOffAttribution.setText(
                            Html.fromHtml(thirdPartyAttribution.toString()));
                }
                places.release();
            } catch (RuntimeRemoteException e) {

            }
        }
    };

    private static Spanned formatDropOffDetails(Resources res, CharSequence name, String id,
                                                CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    // ************************Location Update Code ********************************8

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();

            }
        };
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAGLoc, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAGLoc, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        updateUI();
                        break;
                }
                break;
        }
    }



    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAGLoc, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateUI();


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAGLoc, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAGLoc, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAGLoc, errorMessage);
                                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }


    /**
     * Updates all UI fields.
     */
    private void updateUI() {
        setButtonsEnabledState();
        updateLocationUI();
    }

    /**
     * Disables both buttons when functionality is disabled due to insuffucient location settings.
     * Otherwise ensures that only one button is enabled at any time. The Start Updates button is
     * enabled if the user is not requesting location updates. The Stop Updates button is enabled
     * if the user is requesting location updates.
     */
    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            /* mStartUpdatesButton.setEnabled(false);*/
            /* mStopUpdatesButton.setEnabled(true);*/
        } else {
            /*mStartUpdatesButton.setEnabled(true);*/
            /*mStopUpdatesButton.setEnabled(false);*/
        }
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            /*mLatitudeTextView.setText(String.format(Locale.ENGLISH, "%f",
                    mCurrentLocation.getLatitude()));
            *//*mLatitudeTextView.setText(String.format(Locale.ENGLISH, "%s: %f", mLatitudeLabel,
                    mCurrentLocation.getLatitude()));*//*
            *//*mLongitudeTextView.setText(String.format(Locale.ENGLISH, "%s: %f", mLongitudeLabel,
                    mCurrentLocation.getLongitude()));*//*
            mLongitudeTextView.setText(String.format(Locale.ENGLISH, "%f",
                    mCurrentLocation.getLongitude()));
            mLastUpdateTimeTextView.setText(String.format(Locale.ENGLISH, "%s: %s",
                    mLastUpdateTimeLabel, mLastUpdateTime));
*/
            /*
             */


            saveLocation();

        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAGLoc, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                        setButtonsEnabledState();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
        // location updates if the user has requested them.

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Remove location updates to save battery.


    }


    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAGLoc, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAGLoc, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAGLoc, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAGLoc, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mRequestingLocationUpdates) {
                    Log.i(TAGLoc, "Permission granted, updates requested, starting location updates");
                    startLocationUpdates();
                }
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private void saveLocation() {


    }

    private void setCurrentLocation() {


    }


    private String distanceInKM() {


        Location loc1 = new Location("");
        loc1.setLatitude(mPickUpLatLng.latitude);
        loc1.setLongitude(mPickUpLatLng.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(mDropLatLng.latitude);
        loc2.setLongitude(mDropLatLng.longitude);

        distance = loc1.distanceTo(loc2)/1000;

        NumberFormat numberFormat = new DecimalFormat("#.##'KM'");


        return numberFormat.format(distance);

    }
    public void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        }
        else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }


    private class GetAddress extends AsyncTask<String,Void,String> {


        ProgressDialog dialog = new ProgressDialog(HomeActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false",lat,lng);
                response = http.HttpData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                String address = ((JSONArray)jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                mPickupText.setText(address);
                mPickupAddress=address;
                mPickupString=mPickupText.getText().toString();



            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(dialog.isShowing())
                dialog.dismiss();
        }

    }
}





