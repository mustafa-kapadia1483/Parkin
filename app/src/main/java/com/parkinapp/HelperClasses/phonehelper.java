package com.parkinapp.HelperClasses;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class phonehelper {

    int image;
    String title, price, charge;
    String address;
    GradientDrawable color;

    public phonehelper(GradientDrawable color, int image, String title, String price, String charge, String address) {
        this.image = image;
        this.address = address;
        this.title = title;
        this.price = price;
        this.charge = charge;
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getCharge() {
        return charge;
    }



    public Drawable getgradient() {
        return color;
    }


}
