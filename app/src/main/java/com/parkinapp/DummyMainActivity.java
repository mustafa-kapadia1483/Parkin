package com.parkinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class DummyMainActivity extends AppCompatActivity implements PaymentResultListener {
    int minteger = 1;
    TextView parkName, parkAddress, edDate, edTime, noHours, finalAmount, selectPay, vhRegNo, vhModel, type;
    TextView amount;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private DatePickerDialog datePickerDialog;
    RelativeLayout dateLayout;
    static int bookingCount= 0;
    TextView dategiven;
    String tamount, paidAmount, phoneNumber, name, transId;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //Getting Parking Name from cards on Explore ***********************************

        parkName = findViewById(R.id.parkName);
        parkAddress = findViewById(R.id.parkAddress);
        vhModel = findViewById(R.id.vhModel);
        type = findViewById(R.id.chargeType);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        phoneNumber = fAuth.getCurrentUser().getPhoneNumber().substring(3,13);

        TextView discountAmount = findViewById(R.id.discountAmount);

        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str = intent.getStringExtra("title");
        String str2 = intent.getStringExtra("address");
        String str3 = intent.getStringExtra("price");
        String str4 = intent.getStringExtra("textview");



        // display the string into textView
        parkName.setText(str);
        parkAddress.setText(str2);




        //************************************************************
        TextView getCost = findViewById(R.id.getCost);
        getCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(DummyMainActivity.this);
                dialog.setContentView(R.layout.extimatedialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Dialog box");

                String[] evChargingTypes = {"type1", "ChAdeMO", "type2", "CCS1/2"};


                int[] evChargingCostPerHr = {7,11,15,22};

                Spinner evChargingTypeSelect = dialog.findViewById(R.id.evChargingTypeSelect);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DummyMainActivity.this, android.R.layout.simple_spinner_dropdown_item, evChargingTypes);
                evChargingTypeSelect.setAdapter(adapter);

                TextView evChargingTypeResult = findViewById(R.id.evChargingTypeResult);

                Button check = dialog.findViewById(R.id.chekc);



                evChargingTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String typer = evChargingTypes[i];
                        Integer typeCost = evChargingCostPerHr[i];
                        check.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        evChargingTypeResult.setText("Charging Type Selected is: " + typer + ", \nThe estimated cost of charging will: Rs " + typeCost  * 5 + "/hr");
                                    }
                                }
                        );

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                dialog.show();
            }
        });



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
        type = (TextView) findViewById(R.id.chargeType);
        name = "Charge While parking";

        //TIMEPICKER******************************************
        edTime = findViewById(R.id.time);

        RelativeLayout popTimePicker = (RelativeLayout) findViewById(R.id.timeLayout);
        popTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        hour = selectedHour;
                        minute = selectedMinute;
                        edTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));
                    }
                };

                int style = AlertDialog.THEME_HOLO_LIGHT;

                TimePickerDialog timePickerDialog = new TimePickerDialog(DummyMainActivity.this, style, onTimeSetListener, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        //TIMEPICKER******************************************

        //PROMO CLICK******************

        RelativeLayout promo = (RelativeLayout) findViewById(R.id.Promo);
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountAmount.setText("50");
                paidAmount = String.valueOf(Integer.parseInt(amount.getText().toString()) - Integer.parseInt(discountAmount.getText().toString()));
                finalAmount =findViewById(R.id.finalAmount);
                finalAmount.setText(paidAmount);
            }
        });


        //PAYMENT CLICK************************
        selectPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });




        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBackk);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                Toast.makeText(DummyMainActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
            }
        });



        amount = findViewById(R.id.amount);
        amount.setText(str3);
        parkName.setText(str);
        parkAddress.setText(str2);

        noHours = findViewById(R.id.noHours);
        noHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String finalnoh= noHours.getText().toString();
                tamount=  String.valueOf(Integer.parseInt(finalnoh) * Integer.parseInt(str3));

                amount.setText(tamount);
                paidAmount = String.valueOf(Integer.parseInt(tamount) - Integer.parseInt(discountAmount.getText().toString()));
                finalAmount =findViewById(R.id.finalAmount);
                finalAmount.setText(paidAmount);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        paidAmount = String.valueOf(Integer.parseInt(amount.getText().toString()) - Integer.parseInt(discountAmount.getText().toString()));
        finalAmount =findViewById(R.id.finalAmount);
        finalAmount.setText(paidAmount);

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


    //RAZOR PAY
    public void startPayment() {

        String Payable = String.valueOf(Integer.parseInt(finalAmount.getText().toString()) * 100);
        String contact = phoneNumber;

        Checkout checkout = new Checkout();

        checkout.setImage(R.mipmap.ic_launcher);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", R.string.app_name);
            options.put("description", "Payment for Anything");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", false);

            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", Payable);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", contact);

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {

        Bookings bookings=new Bookings();
        bookings.setParkName(parkName.getText().toString());
        bookings.setVhRegNo(vhRegNo.getText().toString());
        bookings.setStartTime(edTime.getText().toString());
        bookings.setNoHours(noHours.getText().toString());
        bookings.setGivenDate(edDate.getText().toString());
        bookings.setFinalAmount(finalAmount.getText().toString());
        bookings.setType(type.getText().toString());
        bookings.setVhModel(vhModel.getText().toString());
        bookings.setParkAdd(parkAddress.getText().toString());
        bookings.setTransId(s);
        bookings.setName("Charge While Parking");
        bookings.setStatus("Booked");



        databaseReference=firebaseDatabase.getReference("TestBookings").child(phoneNumber);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingCount = (int) snapshot.getChildrenCount() + 2;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference=firebaseDatabase.getReference(phoneNumber).child(s);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(bookings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent a = new Intent(this, History.class);
        startActivity(a);
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Payment Failed" +s, Toast.LENGTH_SHORT).show();
    }
//RAZOR PAY

}