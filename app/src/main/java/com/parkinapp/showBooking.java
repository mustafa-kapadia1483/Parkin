package com.parkinapp;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class showBooking extends AppCompatActivity {
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class showBooking extends AppCompatActivity {
    String phoneNumber;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Bookings> bookingsList=new ArrayList<>();
    TextView parkName,startTime,endTime,givenDate,noHours,vhRegNo, tranId, type, finalAmount, parkAddress, vhModel, chargeYes;
    Bookings bookings;
>>>>>>> dabe6fb5cb953ddebe1ef2e9d8f34d320fcfeed8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_booking);
<<<<<<< HEAD
    }
}
=======
        Intent intent = getIntent();
        String str = intent.getStringExtra("TranID");
        String str2 = intent.getStringExtra("ParkName");


        TextView tranID = findViewById(R.id.textView2);
        tranID.setText("TRANSACTION ID  -"+ str);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        phoneNumber = fAuth.getCurrentUser().getPhoneNumber().substring(3,13);

        firebaseDatabase= FirebaseDatabase.getInstance("https://parkin-e5c4e-default-rtdb.firebaseio.com/");
        databaseReference=firebaseDatabase.getReference(phoneNumber);
        databaseReference.child(str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,String> map=(Map<String, String>) snapshot.getValue();
                bookings = new Bookings();
                bookings.setParkName(map.get("parkName"));
                bookings.setParkAdd(map.get("parkAdd"));
                bookings.setVhRegNo(map.get("vhRegNo"));
                bookings.setStartTime(map.get("startTime"));
                bookings.setGivenDate(map.get("givenDate"));
                bookings.setNoHours(map.get("noHours"));
                bookings.setVhModel(map.get("vhModel"));
                bookings.setFinalAmount(map.get("finalAmount"));
                bookings.setStatus(map.get("status"));
                bookings.setTransId(map.get("transId"));
                bookings.setType(map.get("type"));
                bookings.setName(map.get("name"));
                bookingsList.add(bookings);

                parkName= findViewById(R.id.parkName);
                vhRegNo= findViewById(R.id.vhRegNo2);
                startTime= findViewById(R.id.startTime);
                givenDate= findViewById(R.id.date);
                parkAddress= findViewById(R.id.parkAddress);
                finalAmount = findViewById(R.id.amount);
                vhModel = findViewById(R.id.vhModel2);
                type = findViewById(R.id.vhType);
                chargeYes=findViewById(R.id.chargeYes);


                parkName.setText(bookings.getParkName());
                vhRegNo.setText(bookings.getVhRegNo());
                startTime.setText(bookings.getStartTime());
                givenDate.setText(bookings.getGivenDate());
                parkAddress.setText(bookings.getParkAdd());
                finalAmount.setText(bookings.getFinalAmount());
                vhModel.setText(bookings.getVhModel());
                type.setText(bookings.getType());
                chargeYes.setText(bookings.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}


>>>>>>> dabe6fb5cb953ddebe1ef2e9d8f34d320fcfeed8
