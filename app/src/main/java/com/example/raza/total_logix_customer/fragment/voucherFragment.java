package com.example.raza.total_logix_customer.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raza.total_logix_customer.DTO.transactionhistory;
import com.example.raza.total_logix_customer.DTO.voucher;
import com.example.raza.total_logix_customer.DTO.voucherapplied;
import com.example.raza.total_logix_customer.DTO.wallet;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class voucherFragment extends android.app.Fragment {

    private EditText mVoucehrCode;
    private FirebaseFirestore db;
    private String userID;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String mCode;
    private Date currentdate;
    private AppCompatButton mVerify;
    private com.example.raza.total_logix_customer.DTO.voucher voucher;
    private float balance;

    private String vouchercode;
    private Date ExpireDate;
    private float Cash;
    private float Percentage;


    public voucherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);


        mVerify=view.findViewById(R.id.btn_verify_fragment);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        currentdate = Calendar.getInstance().getTime();

        mVoucehrCode=view.findViewById(R.id.voucehr_TV);

        mCode=mVoucehrCode.getText().toString();

        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*db.collection("voucher").whereEqualTo("voucher code",mCode).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {

                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                voucher = document.toObject(com.example.raza.total_logix_customer.DTO.voucher.class);
                                vouchercode = voucher.getVouchercode();
                                ExpireDate = voucher.getExpire();
                                Cash = voucher.getCash();
                                Percentage = voucher.getPercentage();

                                db.collection("voucherapplied").whereEqualTo("userid", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                                    {

                                        if (task.isSuccessful())
                                        {
                                            for (QueryDocumentSnapshot document : task.getResult())
                                            {
                                                voucherapplied voucherapplied = document.toObject(voucherapplied.class);
                                                String voucherappliedcode = voucherapplied.getVouchercode();
                                                if (!(voucherappliedcode.equals(mCode)))
                                                {
                                                    if (ExpireDate.after(currentdate))
                                                    {
                                                        if (Cash != 0)
                                                        {
                                                            db.collection("wallet").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                                    balance = (float) documentSnapshot.get("amount");
                                                                }
                                                            });
                                                            float newbalance = Cash + balance;
                                                            wallet wallet = new wallet(newbalance, currentdate);
                                                            db.collection("wallet").document(userID).set(wallet);
                                                            transactionhistory transactionhistory = new transactionhistory(Cash, currentdate, userID, mCode, balance, newbalance);
                                                            db.collection("transactionhistory").add(transactionhistory);
                                                            voucherapplied = new voucherapplied(mCode, ExpireDate, Cash, 0, currentdate, userID);
                                                            db.collection("voucherapplied").add(voucherapplied);
                                                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                            ft.replace(R.id.cm, new walletFragment());
                                                            ft.commit();
                                                        }
                                                        else if (Percentage != 0)
                                                        {
                                                            voucherapplied = new voucherapplied(mCode, ExpireDate, 0, Percentage, currentdate, userID);
                                                            db.collection("voucherapplied").add(voucherapplied);
                                                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                            ft.replace(R.id.cm, new walletFragment());
                                                            ft.commit();
                                                        }
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getActivity(), "Voucher Expired", Toast.LENGTH_LONG).show();

                                                    }
                                                }
                                                else

                                                    Toast.makeText(getActivity(), "Voucher Already Used", Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        else
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        Toast.makeText(getActivity(), "Voucher Does Not Exist", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                });*/
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.cm, new walletFragment());
                ft.commit();

            }
        });


        return view;
    }
}
