package com.parkinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.logincolor));
        setContentView(R.layout.activity_send_otpactivity);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        final EditText inputMobile = findViewById(R.id.inputMobile);
        Button sendOtpBtn = findViewById(R.id.sendOtpBtn);

        final ProgressBar progressBar = findViewById(R.id.progressBar);


        sendOtpBtn.setOnClickListener(view -> {
            if(inputMobile.getText().toString().trim().isEmpty()) {
                Toast.makeText(SendOTPActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            sendOtpBtn.setVisibility(View.INVISIBLE);
            String inputMobileString = inputMobile.getText().toString();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + inputMobileString,
                    60,
                    TimeUnit.SECONDS,
                    SendOTPActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            progressBar.setVisibility(View.GONE);
                            sendOtpBtn.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            progressBar.setVisibility(View.GONE);
                            sendOtpBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            progressBar.setVisibility(View.GONE);
                            sendOtpBtn.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                            intent.putExtra("mobile", inputMobileString);
                            intent.putExtra("verificationId", verificationId);
                            startActivity(intent);

                        }
                    }
            );
        });
    }
}