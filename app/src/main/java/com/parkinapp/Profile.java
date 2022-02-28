package com.parkinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        String phoneNumber = fAuth.getCurrentUser().getPhoneNumber();
        TextView textView = findViewById(R.id.userPhone);
        textView.setText(phoneNumber);


        final LinearLayout logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                Intent intent = new Intent(Profile.this, SendOTPActivity.class);
                startActivity(intent);
                finish();
            }
        });

//BOTTOM NAVBAR**********************************************************************************************************

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