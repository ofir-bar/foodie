package com.foodis.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodis.app.data_models.Dish;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishList;

    public static class DishViewHolder extends RecyclerView.ViewHolder{

        private Dish dish;

        private ImageView ivDishImage;
        private TextView tvDishName;


        public DishViewHolder(@NonNull View itemView) {
            super(itemView);

            ivDishImage = itemView.findViewById(R.id.iv_dish_image);
            tvDishName = itemView.findViewById(R.id.tv_dish_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailedDishActivity.class);
                    intent.putExtra(DetailedDishActivity.EXTRA_NAME, dish.getName());
                    intent.putExtra(DetailedDishActivity.EXTRA_DETAILS, dish.getDetails());
                    intent.putExtra(DetailedDishActivity.EXTRA_PIECES, dish.getPieces());
                    intent.putExtra(DetailedDishActivity.EXTRA_PRICE, dish.getPrice());
                    intent.putExtra(DetailedDishActivity.EXTRA_WEIGHT, dish.getWeight());

                    //BitmapDrawable drawable = (BitmapDrawable) ivDishImage.getDrawable();
                    //Bitmap bitmap = drawable.getBitmap();
                    //ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                    //intent.putExtra(DetailedDishActivity.EXTRA_IMAGE, bs.toByteArray());


                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setDishData(Dish dish){
            this.dish = dish;

//            ivDishImage.setImageBitmap(dish.());
            tvDishName.setText(dish.getName());
        }

    }

    public DishAdapter(List<Dish> dishList) {
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dish_item, viewGroup, false);

        return new DishViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder dishViewHolder, int i) {
        dishViewHolder.setDishData(dishList.get(i));
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }
}
