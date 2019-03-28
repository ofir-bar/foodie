package com.foodis.app.data_models;

import android.graphics.Bitmap;

import com.foodis.app.FirebaseStorage;

public class Dish {

    private String name;
    private String details;
    private String imageUrl;
    private double price;
    private int pieces;
    private int amount;
    private String[] mayContain; // Not sure, may change
    private String[] category;
    private Bitmap image;

    public Dish(){}

    public Dish(String name, String details, String imageUrl, double price, int pieces, int amount, String[] mayContain, String[] category, Bitmap image) {
        this.name = name;
        this.details = details;
        this.imageUrl = imageUrl;
        this.price = price;
        this.pieces = pieces;
        this.amount = amount;
        this.mayContain = mayContain;
        this.category = category;
        this.image = FirebaseStorage.loadImage(imageUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String[] getMayContain() {
        return mayContain;
    }

    public void setMayContain(String[] mayContain) {
        this.mayContain = mayContain;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
