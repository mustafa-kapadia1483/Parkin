package com.parkinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookNext extends AppCompatActivity {

    int minteger = 1;
    TextView parkName, parkAddress, edDate, edTime, noHours, finalAmount, selectPay, vhRegNo;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private DatePickerDialog datePickerDialog;
    RelativeLayout dateLayout;
    TextView dategiven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booknext);

        initDatePicker();
        dateLayout = (RelativeLayout) findViewById(R.id.dateLayout);
        dategiven= (TextView) findViewById(R.id.date);
        dategiven.setText(getTodaysDate());


 //Firebase connection and data type casting********************************************
        firebaseDatabase= FirebaseDatabase.getInstance("https://parkin-e5c4e-default-rtdb.firebaseio.com/");

        parkName = (TextView) findViewById(R.id.parkName);
        parkAddress = (TextView) findViewById(R.id.parkAddress);
        edDate = (TextView) findViewById(R.id.date);
        edTime = (TextView) findViewById(R.id.time);
        noHours = (TextView) findViewById(R.id.noHours);
        finalAmount = (TextView) findViewById(R.id.finalAmount);
        selectPay = (TextView) findViewById(R.id.selectPay);
        vhRegNo = (TextView) findViewById(R.id.vhRegNo);


        selectPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookings bookings=new Bookings();
                bookings.setParkName(parkName.getText().toString());
                bookings.setVhRegNo(vhRegNo.getText().toString());
                bookings.setStartTime(edTime.getText().toString());
                bookings.setEndTime(edTime.getText().toString());
                bookings.setNoHours(noHours.getText().toString());
                bookings.setGivenDate(edDate.getText().toString());

                databaseReference=firebaseDatabase.getReference("Bookings").child(vhRegNo.getText().toString());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(bookings);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


//Getting Parking Name from cards on Explore ***********************************

        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str = intent.getStringExtra("message_key");

        // display the string into textView
        parkName.setText(str);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBackk);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RelativeLayout timeLayout = (RelativeLayout) findViewById(R.id.timeLayout);
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookNext.this, "Set Time", Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout dateLayout = (RelativeLayout) findViewById(R.id.dateLayout);
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        LinearLayout addvh = (LinearLayout) findViewById(R.id.addVh);
        addvh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookNext.this, ""+ minteger, Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Date>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month);
    }



    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month);
                edDate.setText(date);
            }
        };


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        long now = System.currentTimeMillis() - 1000;
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.getDatePicker().setMaxDate(now+(1000*60*60*24*7));
    }

    private String makeDateString(int day, int month) {
        return getMonthFormat(month) + " " + day + " ";
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    //Date>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void increaseInteger(View view) {
        if(minteger<6){
        minteger = minteger + 1;
        display(minteger);}

    }public void decreaseInteger(View view) {
        if(minteger>=2){
        minteger = minteger - 1;
        display(minteger);}
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.noHours);
        displayInteger.setText("" + number);
    }

}