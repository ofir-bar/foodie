package com.foodis.app;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


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


        defaultFragment = new DishesFragment();
        if(!fragmentsInMemory.contains(defaultFragment)){
            fragmentsInMemory.add(defaultFragment);
        }

        FragmentTransaction defaultFragmentTransaction = fragmentManager.beginTransaction();
        defaultFragmentTransaction.add(R.id.main_activity_fragment, defaultFragment);
        defaultFragmentTransaction.commit();

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

    protected void onPause(){
        super.onPause();

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
