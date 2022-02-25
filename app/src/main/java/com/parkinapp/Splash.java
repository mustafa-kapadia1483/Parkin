package com.parkinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {

    SharedPreferences onBoarding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.Splash));
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoarding = getSharedPreferences("onBoarding",MODE_PRIVATE);
                boolean isFirstTime = onBoarding.getBoolean("firstTime", true);

                if(isFirstTime) {

                    SharedPreferences.Editor editor = onBoarding.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent i = new Intent(Splash.this, OnBoarding.class);
                    startActivity(i);
                    finish();

                }else{
                    Intent i = new Intent(Splash.this,Home.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);


    }
}