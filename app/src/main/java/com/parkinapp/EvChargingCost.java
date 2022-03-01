package com.parkinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EvChargingCost extends AppCompatActivity {
    String[] evChargingTypes = {"type1", "ChAdeMO", "type2", "CCS1/2"};
    int[] evChargingCostPerHr = {7,11,15,22};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ev_charging_cost);

        Spinner evChargingTypeSelect = findViewById(R.id.evChargingTypeSelect);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, evChargingTypes);
        evChargingTypeSelect.setAdapter(adapter);

        TextView evChargingTypeResult = findViewById(R.id.evChargingTypeResult);

        evChargingTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                evChargingTypeResult.setText("Charging Type Selected is: " + evChargingTypes[i] + ", \nThe estimated cost of charging will: Rs " + evChargingCostPerHr[i] * 5 + "/hr");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}