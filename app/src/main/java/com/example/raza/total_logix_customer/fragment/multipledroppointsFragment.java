package com.example.raza.total_logix_customer.fragment;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.example.raza.total_logix_customer.adapters.PlaceAutocompleteAdapter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class multipledroppointsFragment extends Fragment {

    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    public AutoCompleteTextView mDropOffText;
    public String mDropoffAddress;
    private TextView mDropOffDetailsText;
    private TextView mDropOffAttribution;
    public LatLng mDropLatLng;
    private PlaceDetectionClient mPlaceDetectionClient;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private RecyclerView mLocationsRV;
    private ImageView mClear;

    private LatLng mPickupLocation;

    public String mDropName;
    private Marker mDropMarker;
    public Float distance;
    public String mdistancekm;
    private ListView mListView;

    public multipledroppointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multipledroppoints, container, false);

        mDropOffText = view.findViewById(R.id.multi_drop_location);
        mClear = view.findViewById(R.id.multi_clear);
        mDropOffText.setOnItemClickListener(mDropOffTextClickListener);



        mDropOffDetailsText = view.findViewById(R.id.multi_dropoff_details);
        mDropOffAttribution = view.findViewById(R.id.multi_dropoff_attribution);

        mPickupLocation=((HomeActivity)getActivity()).mPickUpLatLng;

        mLocationsRV=view.findViewById(R.id.RV_multi);

        return view;
    }

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
            Toast.makeText(getContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
        }
    };

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
                mdistancekm=distanceInKM();

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

    private String distanceInKM() {


        Location loc1 = new Location("");
        loc1.setLatitude(mPickupLocation.latitude);
        loc1.setLongitude(mPickupLocation.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(mDropLatLng.latitude);
        loc2.setLongitude(mDropLatLng.longitude);

        distance = loc1.distanceTo(loc2)/1000;

        NumberFormat numberFormat = new DecimalFormat("#.##'KM'");


        return numberFormat.format(distance);

    }

}
