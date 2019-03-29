package com.foodis.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedDishActivity extends AppCompatActivity {
    public static final String EXTRA_NAME="EXTRA_NAME";
    public static final String EXTRA_DETAILS ="EXTRA_DETAILS";
    public static final String EXTRA_PIECES ="EXTRA_PIECES";
    public static final String EXTRA_PRICE ="EXTRA_PRICE";
    public static final String EXTRA_WEIGHT ="EXTRA_WEIGHT";
    public static final String EXTRA_IMAGE ="EXTRA_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dish);

        //get values from intent
        String DishNameData =(String)getIntent().getExtras().get(EXTRA_NAME);
        String DishDetailsData =(String)getIntent().getExtras().get(EXTRA_DETAILS);
        String DishPiecesData =(String)getIntent().getExtras().get(EXTRA_PIECES);
       // String DishPriceData =(String)getIntent().getExtras().get(EXTRA_PRICE);
        String DishWeightData =(String)getIntent().getExtras().get(EXTRA_WEIGHT);

//        Bitmap DishImageData = BitmapFactory.decodeByteArray(
//                getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);


        // Populate the views with data
        TextView tv_DishName = findViewById(R.id.dish_name);
        tv_DishName.setText(DishNameData);

//        ImageView iv_dishImage= new ImageView(this);
//        iv_dishImage.setImageBitmap(DishImageData);

        TextView tv_DishDetails = findViewById(R.id.dish_details);
        tv_DishDetails.setText(DishDetailsData);

//        TextView tv_DishPriceData = findViewById(R.id.dish_price_value);
//        tv_DishPriceData.setText(DishPriceData);

        TextView tv_DishPiecesOrWeight = findViewById(R.id.dish_weight_or_pieces_value);
        if(false){
         //   tv_DishPiecesOrWeight.setText(DishPriceData);
        }

        Button checkout = findViewById(R.id.checkout_dish);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailedDishActivity.this, "Checked out successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
