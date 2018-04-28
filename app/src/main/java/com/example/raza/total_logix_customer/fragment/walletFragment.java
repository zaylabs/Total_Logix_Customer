package com.example.raza.total_logix_customer.fragment;


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

import com.example.raza.total_logix_customer.DTO.cashVoucher;
import com.example.raza.total_logix_customer.DTO.cashVoucherApplied;
import com.example.raza.total_logix_customer.DTO.transactionhistory;
import com.example.raza.total_logix_customer.DTO.wallet;
import com.example.raza.total_logix_customer.activities.CreditCardActivity;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    private String vcode;
    private String codealreadyapplied;
    private String UniqueCashCodeID;
    private String Codeisthere;

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

        getBalance();




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

                vcode = CashVoucher.getText().toString().trim();
                UniqueCashCodeID = userID + vcode;

                if (vcode == null) {
                    Toast.makeText(getContext(), "Kindly Add Voucher Code", Toast.LENGTH_LONG).show();
                } else {
                    db.collection("cashVoucherApplied").document(UniqueCashCodeID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    Toast.makeText(getContext(), "Voucher Already Used", Toast.LENGTH_LONG).show();

                                    applyvouchercv.setVisibility(View.GONE);
                                    RowVoucher.setVisibility(View.GONE);
                                    addcreditcv.setVisibility(View.VISIBLE);


                                } else {
                                    Log.d(TAG, "No such document");

                                    db.collection("cashVoucher").document(vcode).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                    Codeisthere = "yes";
                                                    db.collection("cashVoucher").document(vcode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            cashVoucher cashVoucher = documentSnapshot.toObject(cashVoucher.class);
                                                            ExpireDate = cashVoucher.getExpiredate();
                                                            cash = cashVoucher.getCash();
                                                            if (ExpireDate.after(currentdate)) {

                                                                float newbalance = cash + currentBalance;

                                                                wallet wallet = new wallet(newbalance, currentdate);
                                                                db.collection("wallet").document(userID).set(wallet);
                                                                transactionhistory transactionhistory = new transactionhistory(cash, currentdate, userID, vcode, currentBalance, newbalance);
                                                                db.collection("transactionhistory").add(transactionhistory);
                                                                cashVoucherApplied cashVoucherApplied = new cashVoucherApplied(currentdate);
                                                                db.collection("cashVoucherApplied").document(UniqueCashCodeID).set(cashVoucherApplied);
                                                                Toast.makeText(getContext(), "Anount Added", Toast.LENGTH_LONG).show();
                                                                getBalance();
                                                                applyvouchercv.setVisibility(View.GONE);
                                                                RowVoucher.setVisibility(View.GONE);
                                                                addcreditcv.setVisibility(View.VISIBLE);
                                                            } else {
                                                                Toast.makeText(getContext(), "Voucher Expired", Toast.LENGTH_LONG).show();
                                                                applyvouchercv.setVisibility(View.GONE);
                                                                RowVoucher.setVisibility(View.GONE);
                                                                addcreditcv.setVisibility(View.VISIBLE);
                                                            }

                                                        }

                                                    });

                                                } else {
                                                    Log.d(TAG, "No such document");
                                                    Toast.makeText(getContext(), "Voucher Does Not Exist" + ExpireDate + vcode, Toast.LENGTH_LONG).show();
                                                    applyvouchercv.setVisibility(View.GONE);
                                                    RowVoucher.setVisibility(View.GONE);
                                                    addcreditcv.setVisibility(View.VISIBLE);

                                                }
                                            } else {
                                                Log.d(TAG, "get failed with ", task.getException());
                                            }
                                        }
                                    });


                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());


                            }
                        }

                    });
                }}
            });

        return view;
    }

    private void getBalance() {
        DocumentReference docRef = db.collection("wallet").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                wallet wallet = documentSnapshot.toObject(wallet.class);
                currentBalance = wallet.getAmount();
                String balance = numberFormat.format(currentBalance);
                Balance.setText(balance);


            }
        });


    }

   /* private void CodeInfo() {
        db.collection("cashVoucher").document(vcode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cashVoucher cashVoucher = documentSnapshot.toObject(cashVoucher.class);
                ExpireDate=cashVoucher.getExpire();
                cash=cashVoucher.getCash();
            }
        });


    }

    private void addCash() {

        float newbalance = cash + currentBalance;

        wallet wallet = new wallet(newbalance, currentdate);
        db.collection("wallet").document(userID).set(wallet);
        transactionhistory transactionhistory = new transactionhistory(cash, currentdate, userID, vcode, currentBalance, newbalance);
        db.collection("transactionhistory").add(transactionhistory);
        cashVoucherApplied cashVoucherApplied = new cashVoucherApplied(currentdate);
        db.collection("cashVoucherApplied").document(UniqueCashCodeID).set(cashVoucherApplied);
        Toast.makeText(getContext(), "Anount Added", Toast.LENGTH_LONG).show();
        applyvouchercv.setVisibility(View.GONE);
        RowVoucher.setVisibility(View.GONE);
        addcreditcv.setVisibility(View.VISIBLE);

    }

    private String voucherAlreadyApplied() {

        db.collection("cashVoucherApplied").document(UniqueCashCodeID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        codealreadyapplied="CodeApplied";
                    } else {
                        Log.d(TAG, "No such document");
                        codealreadyapplied=null;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

        });

        return codealreadyapplied;
    }

    private String voucherAvailable() {


        db.collection("cashVoucher").document(vcode).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Codeisthere="yes";
                    } else {
                        Log.d(TAG, "No such document");
                        Codeisthere=null;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return Codeisthere;



           }

*/

}


