package com.foodis.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.foodis.app.data_models.Seller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.foodis.app.AddDish.AddDishActivity;
import com.foodis.app.fragments.DishesFragment;
import com.foodis.app.fragments.FavoritesFragment;
import com.foodis.app.fragments.MyOrdersFragment;
import com.foodis.app.fragments.SearchFragment;
import com.foodis.app.fragments.SellerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.foodis.app.fragments.SellerFragment.RC_SIGN_IN;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentsInMemory = new ArrayList<>();
    private Fragment defaultFragment;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedBottomNavFragment = null;

                        switch (item.getItemId()) {

                            case R.id.bottom_nav_seller:
                                selectedBottomNavFragment = new SellerFragment();
                                break;

                            case R.id.bottom_nav_dishes:
                                selectedBottomNavFragment = new DishesFragment();
                                break;

                            case R.id.bottom_nav_search:
                                selectedBottomNavFragment = new SearchFragment();
                                break;

                            case R.id.bottom_nav_my_orders:
                                selectedBottomNavFragment = new MyOrdersFragment();
                                break;

                            case R.id.bottom_nav_favorites:
                                selectedBottomNavFragment = new FavoritesFragment();
                                break;
                        }

                        // We store all active Fragments in a list, to avoid starting the same Fragment twice
                        if (!fragmentsInMemory.contains(selectedBottomNavFragment)) {
                            fragmentsInMemory.add(selectedBottomNavFragment);
                        }

                        if (selectedBottomNavFragment != null) {
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.main_activity_fragment, selectedBottomNavFragment);
                            ft.commit();
                        }

                        return true;
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        removeAllFragmentsFromMemory();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (true/*requestCode == RC_SIGN_IN*/) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();

                // Add user to Firebase Database
                FirebaseDB.setSellerProfile(new Seller(user.getDisplayName(), "", "", new HashMap<>()));

                for (Fragment fragment: fragmentsInMemory) {
                    if (fragment instanceof SellerFragment) {
                        ((SellerFragment)fragment).loadUserData(user);
                    }
                }

            } else {

                if (response == null) {
                } else {
                    try {
                        Toast.makeText(this, response.getError().getErrorCode(), Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void removeAllFragmentsFromMemory() {
        //removing all fragments in memory
        if (!fragmentsInMemory.isEmpty()) {
            Log.d(TAG, "Removing all Active Fragments");
            FragmentTransaction removeAllFragments = fragmentManager.beginTransaction();
            for (Fragment fragment : fragmentsInMemory) {
                removeAllFragments.remove(fragment);
            }
            removeAllFragments.commit();
        }
    }


}
