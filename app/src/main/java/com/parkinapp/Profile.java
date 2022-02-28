package com.parkinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profile extends AppCompatActivity {
    static int btnClickCount = 0;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String phoneNumber = fAuth.getCurrentUser().getPhoneNumber();
        TextView textView = findViewById(R.id.userPhone);
        textView.setText(phoneNumber);

        EditText userName = findViewById(R.id.username);
        AppCompatButton editUsernameBtn = findViewById(R.id.editProfileBtn);

        String phoneWithoutSuffix = phoneNumber.substring(3,13);

        setUsernameField(phoneWithoutSuffix, userName);

        editUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClickCount ++;
                if(btnClickCount == 1) {
                    userName.setFocusableInTouchMode(true);
                    userName.requestFocus();
                    editUsernameBtn.setText("Save");
                }
                else{
                    mDatabase.child("users").child(phoneWithoutSuffix).setValue(userName.getText().toString());
                    setUsernameField(phoneNumber, userName);
                    editUsernameBtn.setText("Edit");
                    userName.setFocusable(false);
                    btnClickCount = 0;
                }
            }
        });

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

    private void setUsernameField(String phoneNumber, EditText userName) {
        mDatabase.child("users").child(phoneNumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String user = task.getResult().getValue().toString();
                    userName.setText(user);
                }
            }
        });
    }


}