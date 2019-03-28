package com.foodis.app.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.foodis.app.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

import com.foodis.app.R;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellerFragment extends Fragment {
    private static final String TAG = "SellerFragment";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView logout;
    private TextView sellerUsernameValue;
    private FloatingActionButton addDish;
    public static final int RC_SIGN_IN = 1;

    public SellerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupAuthentication(inflater.getContext().getApplicationContext());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seller, container, false);

        logout = v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthUI.getInstance()
                        .signOut(v.getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        addDish = v.findViewById(R.id.seller_add_dish);
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDish();
            }
        });


        return v;
    }


    private void setupAuthentication(Context context){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // user is already signed in
                    Log.d(TAG,"User is signed in");
                    Toast.makeText(context, "Seller already signed in", Toast.LENGTH_SHORT).show();
                }
                else {
                    //user is signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setLogo(R.drawable.icon)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build())
                                    )
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }


    public void onResume(){
        Log.d(TAG, "onResume");
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void onPause(){
        Log.d(TAG, "onPause");
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    private void addNewDish(){
        Toast.makeText(this.getContext(), "Added new dish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
