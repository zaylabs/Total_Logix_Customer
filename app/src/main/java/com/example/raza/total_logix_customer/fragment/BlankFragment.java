package com.example.raza.total_logix_customer.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raza.total_logix_customer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends android.app.Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.dm, new acceptRequestFragment());
        ft.commit();


        return view;
    }

}
