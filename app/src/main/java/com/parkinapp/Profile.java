package com.parkinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        MenuItem item = navigation.getMenu().findItem(R.id.nav_profile);
        item.setCheckable(true);
        item.setChecked(true);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(Profile.this,Home.class);
                        startActivity(a);
                        break;
                    case R.id.nav_book:
                        Intent b = new Intent(Profile.this,Explore.class);
                        startActivity(b);
                        break;
                    case R.id.nav_history:
                        Intent c = new Intent(Profile.this,History.class);
                        startActivity(c);
                        break;
                    case R.id.nav_profile:
                        break;
                }
                return false;
            }
        });
    }
}