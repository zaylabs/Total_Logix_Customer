package com.example.raza.total_logix_customer.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.google.android.gms.maps.model.LatLng;


public class ExampleDialog extends AppCompatDialogFragment {
    private CheckBox mDriverLoading_et;
    private ExampleDialogListner listner;
    private Spinner mNoOfBoxes_et, mWeight_et, mDescription_et;
    private RadioGroup mRadioGroup;
    private RadioButton mCarType1;
    private RadioButton mCarType2;
    private String mdriver_loading;
    private String VT;
    private TextView mDistance, mEstimatedfare;
    private float distanceinkm;
    private LatLng mDPickUpLatLng, mDDropLatLng;
    private String mDistanceinkmm, mEstimatedFare;
    private String mPickupLocationText, mDropLocationText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());




        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.cargo_info_dialog,null);


        mNoOfBoxes_et=(Spinner) view.findViewById(R.id.number_Boxes);
        mWeight_et=(Spinner)view.findViewById(R.id.weight);
        mDescription_et=(Spinner)view.findViewById(R.id.discription);
        mDriverLoading_et=(CheckBox) view.findViewById(R.id.driver_loading);
        mRadioGroup = (RadioGroup)view.findViewById(R.id.radiogroup_VType);
        mCarType1 = (RadioButton)view.findViewById(R.id.radio_CarType1);
        mCarType2 = (RadioButton)view.findViewById(R.id.radio_CarType2);
        mDPickUpLatLng= ((HomeActivity) getActivity()).mPickUpLatLng;
        mPickupLocationText=((HomeActivity) getActivity()).mPickupText.toString();
        mDropLocationText=((HomeActivity) getActivity()).mDropOffText.toString();


        mDDropLatLng=((HomeActivity) getActivity()).mDropLatLng;


        Location loc1 = new Location("");
        loc1.setLatitude(mDPickUpLatLng.latitude);
        loc1.setLongitude(mDPickUpLatLng.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(mDDropLatLng.latitude);
        loc2.setLongitude(mDDropLatLng.longitude);

        distanceinkm = loc1.distanceTo(loc2)/1000;
        mDistanceinkmm=String.valueOf(distanceinkm);
        Double result = 0.0;
        Double b;
        Double a = Double.parseDouble(mDistanceinkmm.trim());
        int SuzukiRate = 200;
        int SuzukiMin=600;
        int RikshaRate=90;
        int RiskshaMin=270;
        int DriverLoadingRate=150;
        if (!(mCarType2.isChecked())) {
            b = (a * SuzukiRate ) + SuzukiMin;
            if ((mDriverLoading_et.isChecked())) {
                result = b + DriverLoadingRate;
            } else {
                result = b;
            }}
        else if (!(mCarType1.isChecked())) {
            b = (a * RikshaRate) + RiskshaMin;
            if ((mDriverLoading_et.isChecked())) {
                result = b + DriverLoadingRate;
            } else {
                result = b;
            }
        }
        mEstimatedFare = result.toString();



// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> noofboxesadapter = ArrayAdapter.createFromResource(getContext(), R.array.numberofboxes, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        noofboxesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mNoOfBoxes_et.setAdapter(noofboxesadapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> weightadapter = ArrayAdapter.createFromResource(getContext(), R.array.weightinkg, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        weightadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mWeight_et.setAdapter(weightadapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> cargoadapter = ArrayAdapter.createFromResource(getContext(), R.array.cargotype, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        cargoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mDescription_et.setAdapter(cargoadapter);



        if (mDriverLoading_et.isChecked()){
            mdriver_loading ="Diver Loading Needed";
        }else  {
            mdriver_loading="DriverLoading Not Needed";
        }


        if (!(mCarType2.isChecked())){
            VT="Suzuki";
        }else
        {
            VT="Riksha";
        }



        builder.setView(view)
                .setTitle("Cargo Discrtiption")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String noofboxes = mNoOfBoxes_et.getSelectedItem().toString();
                        String weight = mWeight_et.getSelectedItem().toString();
                        String discription=mDescription_et.getSelectedItem().toString();
                        String driverloading=mdriver_loading;
                        String vt=VT;
                        String estimatedfare=mEstimatedFare;
                        String distanceinkms=mDistanceinkmm;
                        String pickuptextaddress=mPickupLocationText;
                        String dropoffTextAddress=mDropLocationText;
                        listner.applyTexts(noofboxes,weight,discription,driverloading,vt,estimatedfare,distanceinkms,pickuptextaddress,dropoffTextAddress);
                    }
                });

        return builder.create();




    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (ExampleDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
            "must implement ExampleDialogListner");

        }
    }

    public interface ExampleDialogListner{
        void applyTexts(String noofboxes, String weight, String discription, String driverloading, String vt, String estimatedfare,String distanceinkms,String pickuptextaddress,String dropoffTextAddress);
    }
}
