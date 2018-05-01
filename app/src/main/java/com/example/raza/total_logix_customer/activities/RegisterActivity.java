package com.example.raza.total_logix_customer.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.raza.total_logix_customer.DTO.transactionhistory;
import com.example.raza.total_logix_customer.DTO.userProfile;
import com.example.raza.total_logix_customer.DTO.wallet;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends BaseActivity {
    private static final String TAG ="";
    private TextInputEditText mPassword;
    private TextInputEditText mName;
    private TextInputEditText mEmail;
    private TextInputEditText mPhone;
    private RadioGroup mRadioGroup;
    private RadioButton mVT1;
    private RadioButton mVT2;
    private FirebaseAuth mAuth;
    private AppCompatButton mRegister;
    private RelativeLayout mBack;
    private int mVTInt;
    private RadioButton mVTRadio;
    private String mVT;
    private String userID;
    private TextInputEditText mCnic;
    private TextInputEditText mRegNo;
    private FirebaseFirestore db;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //mDBRef = mDatabase.child("driver").child(userID);
        db = FirebaseFirestore.getInstance();


        mName = findViewById(R.id.input_name);
        mPhone= findViewById(R.id.et_phone);
        mBack=(RelativeLayout) findViewById(R.id.relative_signin);
        mRegister = (AppCompatButton)findViewById(R.id.register_button);
        mPassword =findViewById(R.id.password);
        mEmail=findViewById(R.id.email);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });
    }


    public void signUp(View view) {
        Log.d(TAG, "signUp");
        if (!validateSignUpForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        //Set Display name
        userdisplayname();
        //setphonenumber
        saveMap();
        // Go to MainActivity
        backToLogin();
    }


    private boolean validateSignUpForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmail.getText().toString())) {
            mEmail.setError("Required");
            result = false;
        } else {
            mEmail.setError(null);
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError("Required");
            result = false;
        } else {
            mPassword.setError(null);
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            mName.setError("Required");
            result = false;
        } else {
            mName.setError(null);
        }
        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            mPhone.setError("Required");
            result = false;
        } else {
            mPhone.setError(null);
        }

        return result;
    }

    private void userdisplayname() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName.getText().toString())
                .build();

        Objects.requireNonNull(user).updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void saveMap() {

        Date currentDate = Calendar.getInstance().getTime();
        String source= "New Account";

        userID = mAuth.getCurrentUser().getUid();
        final String phone = mPhone.getText().toString();
        final String name = mName.getText().toString();


        Integer stars= 0;
        Integer totalrides=0;
        userProfile profile = new userProfile(name, mEmail.getText().toString(), null, phone, currentDate, null,totalrides,stars);
        db.collection("customers").document(userID).set(profile);
        wallet wallet = new wallet(0,currentDate);
        db.collection("wallet").document(userID).set(wallet);
        String uniqueTransactionID=userID+(currentDate.toString());
        transactionhistory transactionhistory=new transactionhistory(0,currentDate,userID,source,0,0);
        db.collection("transactionhistory").document(uniqueTransactionID).set(transactionhistory);


    }

    public void backToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        backToLogin();
    }
}

