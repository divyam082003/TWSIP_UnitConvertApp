package com.divyambansal.unitmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.divyambansal.unitmaster.databinding.ActivityUnitchoiceBinding;


public class ActivityUnitChoice extends AppCompatActivity {

    ActivityUnitchoiceBinding unitChoiceBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unitChoiceBinding = ActivityUnitchoiceBinding.inflate(getLayoutInflater());
        setContentView(unitChoiceBinding.getRoot());

        unitChoiceBinding.buttonLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversionActivity("length");
            }
        });

        unitChoiceBinding.buttonWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversionActivity("weight");
            }
        });

        unitChoiceBinding.buttonTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversionActivity("temperature");
            }
        });

        unitChoiceBinding.buttonVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversionActivity("volume");
            }
        });

        unitChoiceBinding.buttonArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversionActivity("area");
            }
        });

        unitChoiceBinding.buttonPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversionActivity("pressure");
            }
        });


    }

    private void openConversionActivity(String unitId) {
        Intent intent = new Intent(ActivityUnitChoice.this, ConversionActivity.class);
        intent.putExtra("unitId", unitId);
        startActivity(intent);
    }
}