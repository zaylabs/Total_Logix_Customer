package com.example.raza.total_logix_customer.fragment;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_customer.DTO.acceptRequest;
import com.example.raza.total_logix_customer.DTO.cashVoucher;
import com.example.raza.total_logix_customer.DTO.cashVoucherApplied;
import com.example.raza.total_logix_customer.DTO.transactionhistory;
import com.example.raza.total_logix_customer.DTO.voucherapplied;
import com.example.raza.total_logix_customer.DTO.wallet;
import com.example.raza.total_logix_customer.activities.CreditCardActivity;
import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.activities.CurrentRideActivity;
import com.example.raza.total_logix_customer.activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class walletFragment extends android.app.Fragment {

    private FirebaseFirestore db;
    private String userID;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Date currentdate;
    private AppCompatButton cashvoucher;
    private AppCompatButton applyvoucher;
    private AppCompatButton payviacc;
    private AppCompatButton addCredit;
    private TextView Balance;
    private EditText Amount;
    private EditText CashVoucher;
    private TableRow RowVoucher;
    private TableRow RowAmount;
    private CardView cashvouchercv;
    private CardView applyvouchercv;
    private CardView payviacccv;
    private CardView addcreditcv;
    private NumberFormat numberFormat;
    private List <cashVoucherApplied>voucherappliedList;
    private Date ExpireDate;
    private float currentBalance;
    private float cash;
    private ArrayList<cashVoucherApplied> cashVoucherApplieds;

    public walletFragment() {
        // Required empty public constructor


    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);


        cashvoucher = view.findViewById(R.id.addviacashvoucher);
        applyvoucher = view.findViewById(R.id.applyCashVoucher);
        payviacc = view.findViewById(R.id.addviacc);
        addCredit = view.findViewById(R.id.addBalace);
        Balance = view.findViewById(R.id.currentBalaceTV);
        Amount = view.findViewById(R.id.AmountET);
        CashVoucher = view.findViewById(R.id.cashvoucherET);
        RowVoucher = view.findViewById(R.id.cashvrow);
        RowAmount = view.findViewById(R.id.amountrow);
        cashvouchercv = view.findViewById(R.id.addviacashvoucherCV);
        applyvouchercv = view.findViewById(R.id.applyCashVoucherCV);
        payviacccv = view.findViewById(R.id.addviaccCV);
        addcreditcv = view.findViewById(R.id.addBalaceCV);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        currentdate = Calendar.getInstance().getTime();
        numberFormat = new DecimalFormat("'Rs.'#.##");


        db.collection("wallet")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {


                                wallet wallet = dc.getDocument().toObject(com.example.raza.total_logix_customer.DTO.wallet.class);
                                currentBalance = wallet.getAmount();
                                String balance = numberFormat.format(currentBalance);
                                Balance.setText(balance);
                            }
                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                wallet wallet = dc.getDocument().toObject(com.example.raza.total_logix_customer.DTO.wallet.class);
                                currentBalance = wallet.getAmount();
                                String balance = numberFormat.format(currentBalance);
                                Balance.setText(balance);
                            }
                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                String balance = numberFormat.format(0.0);
                                Balance.setText(balance);
                            }
                        }

                    }
                });


        addCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RowAmount.setVisibility(View.VISIBLE);
                payviacccv.setVisibility(View.VISIBLE);
                cashvouchercv.setVisibility(View.VISIBLE);
                addcreditcv.setVisibility(View.GONE);
            }
        });

        cashvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*db.collection("cashVoucherApplied").whereEqualTo("userid", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cashVoucherApplied cashVoucherApplied = document.toObject(cashVoucherApplied.class);
                                cashVoucherApplieds.add(cashVoucherApplied);
                            }
                        }
                    }
                });*/
                RowAmount.setVisibility(View.GONE);
                payviacccv.setVisibility(View.GONE);
                applyvouchercv.setVisibility(View.VISIBLE);
                RowVoucher.setVisibility(View.VISIBLE);
                cashvouchercv.setVisibility(View.GONE);
            }
        });

        payviacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreditCardActivity.class);
                startActivity(i);
            }
        });


        applyvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String vcode = CashVoucher.getText().toString();
                final int[] CodeApplied = {0};
                final int[] DoesNotExist = {0};
                Query mQuery = db.collection("cashVoucherApplied")
                        .whereEqualTo("userid", userID);
                Query mcodeQuery = mQuery.whereEqualTo("cashvouchercode", vcode);


                mcodeQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        for (DocumentSnapshot ds : documentSnapshots) {
                            if (ds != null) {
                                Log.d(TAG, "checkingIfCashVoucher: FOUND A MATCH: " + vcode);
                                CodeApplied[0] = 1;
                            }
                        }
                    }
                });

                db.collection("cashVoucher").whereEqualTo("cashvouchercode", vcode).
                        addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                for (DocumentSnapshot ds : documentSnapshots) {
                                    if (ds != null) {

                                        cashVoucher cashVoucher = ds.toObject(com.example.raza.total_logix_customer.DTO.cashVoucher.class);
                                        ExpireDate = cashVoucher.getExpire();
                                        cash = cashVoucher.getCash();
                                        DoesNotExist[0] = 1;
                                    }
                                }
                            }
                        });

                if (DoesNotExist[0] == 1) {

                    if (!(CodeApplied[0] == 1)) {

                        if (ExpireDate.after(currentdate)) {
                            float newbalance = cash + currentBalance;
                            wallet wallet = new wallet(newbalance, currentdate);
                            db.collection("wallet").document(userID).set(wallet);

                            transactionhistory transactionhistory = new transactionhistory(cash, currentdate, userID, vcode, currentBalance, newbalance);
                            db.collection("transactionhistory").add(transactionhistory);
                            cashVoucherApplied cashVoucherApplied = new cashVoucherApplied(vcode, userID);
                            db.collection("cashVoucherApplied").add(cashVoucherApplied);
                            Toast.makeText(getContext(), "Anount Added", Toast.LENGTH_LONG).show();
                            applyvouchercv.setVisibility(View.GONE);
                            RowVoucher.setVisibility(View.GONE);
                            addcreditcv.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(getContext(), "Voucher Expired", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Toast.makeText(getContext(), "Voucher Already Used", Toast.LENGTH_LONG).show();

                        applyvouchercv.setVisibility(View.GONE);
                        RowVoucher.setVisibility(View.GONE);
                        addcreditcv.setVisibility(View.VISIBLE);
                    }
                }else {

                    Toast.makeText(getContext(), "Voucher Does Not Exist", Toast.LENGTH_LONG).show();
                }


            }

});

       /* }
                })


                mcodeQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        for (DocumentSnapshot ds : documentSnapshots) {
                            if (ds != null) {


*/
        return view;
    }



}


