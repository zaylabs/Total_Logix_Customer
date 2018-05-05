package com.example.raza.total_logix_customer.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.raza.total_logix_customer.DTO.userProfile;
import com.example.raza.total_logix_customer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class profileFragment extends android.app.Fragment implements View.OnClickListener {

    private FirebaseFirestore db;
    private static final int REQUEST_IMAGE_CAPTURE = 111;


    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private RatingBar mRatingBar;
    private Button mSave;
    private Button mChangePassword;

    private de.hdodenhof.circleimageview.CircleImageView mProfilePic;

    private String email;
    private Date updatedate;

    private StorageReference mImageRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String userID;
    private Date createdate;
    private Uri mDP;
    private String photodp;
    private String mImageLink;
    private float stars;
    private float totalrides;
    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mName = (EditText)view.findViewById(R.id.input_name);
        mPhone = view.findViewById(R.id.input_mobile);
        mEmail=view.findViewById(R.id.input_email);
        mSave = view.findViewById(R.id.btn_update);
        mChangePassword=view.findViewById(R.id.btn_change_pass);
        mProfilePic=view.findViewById(R.id.profile_pic);
        mRatingBar=view.findViewById(R.id.ratingbar);
        mProfilePic.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);

        mImageRef= mStorageRef.child(userID);
        getInfo();



        return view;
    }

    private void chooseImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);*/
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProfilePic.setImageBitmap(imageBitmap);
            saveimage();
            //encodeBitmapAndSaveToFirebase(imageBitmap);
        }

    }

    public void saveimage(){
        // Get the data from an ImageView as bytes

        mProfilePic.setDrawingCacheEnabled(true);
        mProfilePic.buildDrawingCache();
        Bitmap bitmap = mProfilePic.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mImageRef.child("DP").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mDP = taskSnapshot.getDownloadUrl();
                mImageLink=mDP.toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri((Uri.parse(mImageLink)))
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }
                        });

                saveMap();
            }
        });

    }
    private void getInfo() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
            mName.setText(name);
            if (user.getPhotoUrl()!=null){
                mDP = user.getPhotoUrl();
                Picasso.with(getActivity()).load(mDP).resize(150,150).centerCrop().into(mProfilePic);
            }}


        DocumentReference docRef = db.collection("customers").document(userID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userProfile profile = documentSnapshot.toObject(userProfile.class);
                String phone=profile.getPhone();
                mPhone.setText(phone);
                String dpURL=profile.getDpURL();
                mImageLink=dpURL;
                email =profile.getEmail();
                mEmail.setText(email);
                createdate=profile.getCreateDate();
                stars=profile.getStars();
                mRatingBar.setRating(stars);
                totalrides=profile.getTotalrides();
            }
        });

    }


    public void saveMap(){
        String dpURL = mImageLink;
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        updatedate=Calendar.getInstance().getTime();
        userProfile profile = new userProfile(name,email,dpURL,phone,createdate,updatedate,totalrides,stars);
        db.collection("customers").document(userID).set(profile);

    }
    private void saveInfo() {
        saveMap();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName.getText().toString())
                .setPhotoUri(mDP)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.profile_pic) {
            chooseImage();
        } else if (i == R.id.btn_update) {
            saveInfo();
        }else if (i==R.id.btn_change_pass){
            changePassword();
        }
    }

    private void changePassword() {
        String email = mEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Re-set Email Sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Email Sending Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        mAuth.signOut();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}