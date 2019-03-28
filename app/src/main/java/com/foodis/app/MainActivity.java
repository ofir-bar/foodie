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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.foodis.app.SellerFragment.RC_SIGN_IN;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentsInMemory = new ArrayList<>();
    private Fragment defaultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDishesFragmentInFrontScreen();
        // TODO: Avoid making the same fragment twice
//
//        defaultFragment = new DishesFragment();
//        if(!fragmentsInMemory.contains(defaultFragment)){
//            fragmentsInMemory.add(defaultFragment);
//        }


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
                        if(!fragmentsInMemory.contains(selectedBottomNavFragment)){
                            fragmentsInMemory.add(selectedBottomNavFragment);
                        }

                        if(selectedBottomNavFragment != null){
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
        setDishesFragmentInFrontScreen();
    }

    protected void onPause(){
        super.onPause();
        removeAllFragmentsFromMemory();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();

            } else {

                if (response == null){
                    setDishesFragmentInFrontScreen();
                }
                else {
                    try{
                        Toast.makeText(this, response.getError().getErrorCode(), Toast.LENGTH_SHORT).show();
                    }catch (NullPointerException e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private void setDishesFragmentInFrontScreen(){
        if(fragmentsInMemory.isEmpty()){
            Fragment defaultFragment = new DishesFragment();
            FragmentTransaction defaultFragmentTransaction = fragmentManager.beginTransaction();
            defaultFragmentTransaction.add(R.id.main_activity_fragment, defaultFragment);
            defaultFragmentTransaction.commit();
        }
    }

    private void removeAllFragmentsFromMemory(){
        //removing all fragments in memory
        if(!fragmentsInMemory.isEmpty()){
            Log.d(TAG,"Removing all Active Fragments");
            FragmentTransaction removeAllFragments = fragmentManager.beginTransaction();
            for(Fragment fragment:fragmentsInMemory){
                removeAllFragments.remove(fragment);
            }
            removeAllFragments.commit();
        }
    }


}
