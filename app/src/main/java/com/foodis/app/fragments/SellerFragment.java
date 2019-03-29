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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.foodis.app.AddDish.AddDishActivity;
import com.foodis.app.DishAdapter;
import com.foodis.app.FirebaseDB;
import com.foodis.app.MainActivity;
import com.foodis.app.data_models.Dish;
import com.foodis.app.data_models.Seller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.foodis.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellerFragment extends Fragment {
    private static final String TAG = "SellerFragment";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView logout;
    private TextView sellerUsernameValue;
    private TextView sellerCityValue;
    private TextView sellerAddressValue;
    private FloatingActionButton addDish;
    public static final int RC_SIGN_IN = 1;
    String sellerName;
    String sellerUid;


    private List<Dish> dishList;
    private DishAdapter dishAdapter;
    private RecyclerView rc;

    public SellerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupAuthentication(inflater.getContext().getApplicationContext());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seller, container, false);

        sellerAddressValue = v.findViewById(R.id.seller_address_value);
        sellerCityValue = v.findViewById(R.id.seller_city_value);
        sellerUsernameValue = v.findViewById(R.id.seller_username_value);

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


        /* RecyclerView adaptation starts */
        RecyclerView rc = (RecyclerView) v.findViewById(R.id.rc_dishes);
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));

        dishList = new ArrayList<>();

        dishAdapter = new DishAdapter(dishList);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rc.getContext(),
                new LinearLayoutManager(getActivity()).getOrientation());
        rc.addItemDecoration(dividerItemDecoration);
        rc.setAdapter(dishAdapter);

        /* RecyclerView adaptation ends */

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

                    loadUserData(user);
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

    public void loadUserData(FirebaseUser user){
        if(user == null || TextUtils.isEmpty(user.getDisplayName())) return;

        FirebaseDB.getSellersRef().child(user.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Seller seller = dataSnapshot.getValue(Seller.class);
                sellerName = user.getDisplayName();
                sellerUid = user.getUid();
                if(seller == null) return;

                sellerAddressValue.setText(seller.address);
                sellerCityValue.setText(seller.city);
                sellerUsernameValue.setText(seller.name);

                loadDishes();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadDishes() {
        FirebaseDB.getSellersRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Seller> sellersList = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    sellersList.add(data.getValue(Seller.class));
                }

                // Add only the dishes of this seller to the list to show the seller
                for(Seller seller: sellersList){
                    if(seller.dishList == null || seller.dishList.isEmpty() || seller.name == null || !seller.name.equals(sellerName)) continue;

                    dishList.addAll(seller.dishList.values());
                }

                dishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




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
        Intent intent = new Intent(getActivity(), AddDishActivity.class);
        intent.putExtra("numOfNextDish", 4);
        intent.putExtra("userUid", sellerUid);
        intent.putExtra("userName", sellerName);
        //////
        startActivity(intent);
    }

//    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////    }


}
