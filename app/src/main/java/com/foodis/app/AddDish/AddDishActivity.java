package com.foodis.app.AddDish;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodis.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddDishActivity extends AppCompatActivity {

    ImageView imageViewPhoto;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
//        imageViewPhoto = findViewById(R.id.imageViewPhoto);
//        imageViewPhoto.setOnClickListener(v -> {
//            EasyImage.openChooserWithDocuments(this, "select photo", 0);
//        });
//        mStorageRef = FirebaseStorage.getInstance().getReference();

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.publishAirdropViewPager);

        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(pager, true);

        Button publishAirdropBtn = findViewById(R.id.publish_airdrop_button);

//        publishAirdropBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(SalesMainPageActivity.this, newAirdropForm.class);
//            startActivity(intent);
//        });

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
//            @Override
//            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//                //Some error handling
//            }
//
//            @Override
//            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
//                //Handle the images
//                Bitmap myBitmap = BitmapFactory.decodeFile(imagesFiles.get(0).getAbsolutePath());
//
//
//                StorageReference mountainsRef = mStorageRef.child("mountainss1.jpg");
//
//
//                imageViewPhoto.setImageBitmap(myBitmap);
//                imageViewPhoto.setDrawingCacheEnabled(true);
//                imageViewPhoto.buildDrawingCache();
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] data = baos.toByteArray();
//
//                UploadTask uploadTask = mountainsRef.putBytes(data);
//                uploadTask.addOnFailureListener(exception -> {
//                    // Handle unsuccessful uploads
//                    Toast.makeText(AddDishActivity.this, "upload failed: "+exception.getMessage(), Toast.LENGTH_SHORT).show();
//                }).addOnSuccessListener(taskSnapshot -> {
//
//
//                    Toast.makeText(AddDishActivity.this, "upload success", Toast.LENGTH_SHORT).show();
//
//                });
//
//            }
//        });
//    }
//

    //Tell a view pager about its pages using a fragment pager adapter,this is the inner class for this adapter.
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount(){
            return 4;
        }

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    return new AddPhotoFragment();
                case 1:
                    return new ByGramsOrByPiecesFragment();
                case 2:
                    return new FoodCategoryFragment();
                case 3:
                    return new MayContainFragment();

            }
            return null;
        }




    }

}












