package com.parkinapp;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Home extends AppCompatActivity {
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.logincolor));
        setContentView(R.layout.activity_home);


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), SendOTPActivity.class);
            startActivity(intent);
            finish();
        }

        ImageButton profileBtn = (ImageButton)findViewById(R.id.profileHomeBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(Home.this,Profile.class);
                startActivity(p);
            }
        });

        ImageButton notifyHome = (ImageButton)findViewById(R.id.notifyHome);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(Home.this,Profile.class);
                startActivity(p);
            }
        });

        Button explore = (Button) findViewById(R.id.exploreMore);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(Home.this,Explore.class);
                startActivity(p);
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        MenuItem item = navigation.getMenu().findItem(R.id.nav_home);
        item.setCheckable(true);
        item.setChecked(true);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_book:
                        Intent a = new Intent(Home.this,Explore.class);
                        startActivity(a);
                        break;
                    case R.id.nav_history:
                        Intent b = new Intent(Home.this,History.class);
                        startActivity(b);
                        break;
                    case R.id.nav_profile:
                        Intent c = new Intent(Home.this,Profile.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

    }

}