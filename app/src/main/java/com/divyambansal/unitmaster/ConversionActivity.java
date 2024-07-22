package com.divyambansal.unitmaster;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.divyambansal.unitmaster.databinding.ActivityConversionBinding;

public class ConversionActivity extends AppCompatActivity {

    ActivityConversionBinding conversionBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversionBinding = ActivityConversionBinding.inflate(getLayoutInflater());
        setContentView(conversionBinding.getRoot());

        String unitId = getIntent().getStringExtra("unitId");
        conversionBinding.conversionTitle.setText("Quantity: "+unitId.toUpperCase());
        setupSpinners(unitId);
        setupButtonListeners();
    }

    private void setupSpinners(String unitId) {
        int unitsArrayId;
        switch (unitId) {
            case "length":
                unitsArrayId = R.array.length_units;
                break;
            case "weight":
                unitsArrayId = R.array.weight_units;
                break;
            case "temp":
                unitsArrayId = R.array.temp_units;
                break;
            case "volume":
                unitsArrayId = R.array.volume_units;
                break;
            case "area":
                unitsArrayId = R.array.area_units;
                break;
            case "pressure":
                unitsArrayId = R.array.pressure_units;
                break;
            default:
                throw new IllegalArgumentException("Invalid unit ID: " + unitId);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, unitsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionBinding.spinnerFromUnit.setAdapter(adapter);
        conversionBinding.spinnerToUnit.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        GridLayout gridLayoutButtons = findViewById(R.id.gridLayoutButtons);
        int childCount = gridLayoutButtons.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = gridLayoutButtons.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClicked((Button) v);
                    }
                });
            }
        }
    }

    private void onButtonClicked(Button button) {
        String buttonText = button.getText().toString();
        String currentText = conversionBinding.editTextValue.getText().toString();
        switch (buttonText) {
            case "C":
                conversionBinding.editTextValue.setText("");
                conversionBinding.textViewResult.setText("");
                break;
            case ".":
                if (!currentText.contains(".")) {
                    conversionBinding.editTextValue.append(buttonText);
                }
                break;
            case "CE":
                // Remove the last character if the input is not empty
                if (!currentText.isEmpty()) {
                    conversionBinding.editTextValue.setText(currentText.substring(0, currentText.length() - 1));
                    conversionBinding.textViewResult.setText("");
                }
                break;
            case "Show":
                // Perform conversion and show result
                performConversion();
                break;
            default:
                conversionBinding.editTextValue.append(buttonText);
                break;
        }
    }

    private void performConversion() {
        // Retrieve value from EditText
        String valueString = conversionBinding.editTextValue.getText().toString();
        if (valueString.isEmpty()) {
            conversionBinding.textViewResult.setText("Invalid Input");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            conversionBinding.textViewResult.setText("Invalid Number");
            return;
        }

        // Retrieve units from spinners
        String fromUnit = conversionBinding.spinnerFromUnit.getSelectedItem().toString();
        String toUnit = conversionBinding.spinnerToUnit.getSelectedItem().toString();

        // Perform the conversion
        double convertedValue = convert(value, fromUnit, toUnit);

        // Display the result
        conversionBinding.textViewResult.setText(String.format("%.2f", convertedValue));
    }

    private double convert(double value, String fromUnit, String toUnit) {
        // Conversion logic based on units
        // This example assumes conversions are linear and will need to be updated with actual conversion logic
        // Placeholder conversion example: Convert between length units (e.g., meters to kilometers)

        double conversionFactor = getConversionFactor(fromUnit, toUnit);
        return value * conversionFactor;
    }

    public double getConversionFactor(String fromUnit, String toUnit) {
        // Length conversion factors (meters as base unit)
        if (fromUnit.equals("Meter")) {
            switch (toUnit) {
                case "Kilometer": return 0.001;
                case "Centimeter": return 100;
                case "Millimeter": return 1000;
                case "Mile": return 0.000621371;
                case "Yard": return 1.09361;
                case "Foot": return 3.28084;
                case "Inch": return 39.3701;
                default: return 1;
            }
        } else if (fromUnit.equals("Kilometer")) {
            switch (toUnit) {
                case "Meter": return 1000;
                case "Centimeter": return 100000;
                case "Millimeter": return 1000000;
                case "Mile": return 0.621371;
                case "Yard": return 1094;
                case "Foot": return 3281;
                case "Inch": return 39370.1;
                default: return 1;
            }
        } else if (fromUnit.equals("Centimeter")) {
            switch (toUnit) {
                case "Meter": return 0.01;
                case "Kilometer": return 0.00001;
                case "Millimeter": return 10;
                case "Mile": return 0.00000621371;
                case "Yard": return 0.0109361;
                case "Foot": return 0.0328084;
                case "Inch": return 0.393701;
                default: return 1;
            }
        } else if (fromUnit.equals("Millimeter")) {
            switch (toUnit) {
                case "Meter": return 0.001;
                case "Kilometer": return 0.000001;
                case "Centimeter": return 0.1;
                case "Mile": return 0.000000621371;
                case "Yard": return 0.00109361;
                case "Foot": return 0.00328084;
                case "Inch": return 0.0393701;
                default: return 1;
            }
        } else if (fromUnit.equals("Mile")) {
            switch (toUnit) {
                case "Meter": return 1609.34;
                case "Kilometer": return 1.60934;
                case "Centimeter": return 160934;
                case "Millimeter": return 1609340;
                case "Yard": return 1760;
                case "Foot": return 5280;
                case "Inch": return 63360;
                default: return 1;
            }
        } else if (fromUnit.equals("Yard")) {
            switch (toUnit) {
                case "Meter": return 0.9144;
                case "Kilometer": return 0.0009144;
                case "Centimeter": return 91.44;
                case "Millimeter": return 914.4;
                case "Mile": return 0.000568182;
                case "Foot": return 3;
                case "Inch": return 36;
                default: return 1;
            }
        } else if (fromUnit.equals("Foot")) {
            switch (toUnit) {
                case "Meter": return 0.3048;
                case "Kilometer": return 0.0003048;
                case "Centimeter": return 30.48;
                case "Millimeter": return 304.8;
                case "Mile": return 0.000189394;
                case "Yard": return 0.333333;
                case "Inch": return 12;
                default: return 1;
            }
        } else if (fromUnit.equals("Inch")) {
            switch (toUnit) {
                case "Meter": return 0.0254;
                case "Kilometer": return 0.0000254;
                case "Centimeter": return 2.54;
                case "Millimeter": return 25.4;
                case "Mile": return 0.0000157828;
                case "Yard": return 0.0277778;
                case "Foot": return 0.0833333;
                default: return 1;
            }
        }

        // Weight conversion factors (kilograms as base unit)
        if (fromUnit.equals("Kilogram")) {
            switch (toUnit) {
                case "Gram": return 1000;
                case "Milligram": return 1000000;
                case "Pound": return 2.20462;
                case "Ounce": return 35.274;
                default: return 1;
            }
        } else if (fromUnit.equals("Gram")) {
            switch (toUnit) {
                case "Kilogram": return 0.001;
                case "Milligram": return 1000;
                case "Pound": return 0.00220462;
                case "Ounce": return 0.035274;
                default: return 1;
            }
        } else if (fromUnit.equals("Milligram")) {
            switch (toUnit) {
                case "Kilogram": return 0.000001;
                case "Gram": return 0.001;
                case "Pound": return 0.00000220462;
                case "Ounce": return 0.000035274;
                default: return 1;
            }
        } else if (fromUnit.equals("Pound")) {
            switch (toUnit) {
                case "Kilogram": return 0.453592;
                case "Gram": return 453.592;
                case "Milligram": return 453592;
                case "Ounce": return 16;
                default: return 1;
            }
        } else if (fromUnit.equals("Ounce")) {
            switch (toUnit) {
                case "Kilogram": return 0.0283495;
                case "Gram": return 28.3495;
                case "Milligram": return 28349.5;
                case "Pound": return 0.0625;
                default: return 1;
            }
        }

        // Temperature conversion factors (Celsius as base unit)
        if (fromUnit.equals("Celsius")) {
            switch (toUnit) {
                case "Fahrenheit": return 9.0 / 5.0;
                case "Kelvin": return 1;
                default: return 1;
            }
        } else if (fromUnit.equals("Fahrenheit")) {
            switch (toUnit) {
                case "Celsius": return (5.0 / 9.0);
                case "Kelvin": return (5.0 / 9.0);
                default: return 1;
            }
        } else if (fromUnit.equals("Kelvin")) {
            switch (toUnit) {
                case "Celsius": return 1;
                case "Fahrenheit": return 9.0 / 5.0;
                default: return 1;
            }
        }

        // Volume conversion factors (liters as base unit)
        if (fromUnit.equals("Liter")) {
            switch (toUnit) {
                case "Milliliter": return 1000;
                case "Gallon": return 0.264172;
                case "Quart": return 1.05669;
                case "Pint": return 2.11338;
                case "Cup": return 4.22675;
                default: return 1;
            }
        } else if (fromUnit.equals("Milliliter")) {
            switch (toUnit) {
                case "Liter": return 0.001;
                case "Gallon": return 0.000264172;
                case "Quart": return 0.00105669;
                case "Pint": return 0.00211338;
                case "Cup": return 0.00422675;
                default: return 1;
            }
        } else if (fromUnit.equals("Gallon")) {
            switch (toUnit) {
                case "Liter": return 3.78541;
                case "Milliliter": return 3785.41;
                case "Quart": return 4;
                case "Pint": return 8;
                case "Cup": return 16;
                default: return 1;
            }
        } else if (fromUnit.equals("Quart")) {
            switch (toUnit) {
                case "Liter": return 0.946353;
                case "Milliliter": return 946.353;
                case "Gallon": return 0.25;
                case "Pint": return 2;
                case "Cup": return 4;
                default: return 1;
            }
        } else if (fromUnit.equals("Pint")) {
            switch (toUnit) {
                case "Liter": return 0.473176;
                case "Milliliter": return 473.176;
                case "Gallon": return 0.125;
                case "Quart": return 0.5;
                case "Cup": return 2;
                default: return 1;
            }
        } else if (fromUnit.equals("Cup")) {
            switch (toUnit) {
                case "Liter": return 0.236588;
                case "Milliliter": return 236.588;
                case "Gallon": return 0.0625;
                case "Quart": return 0.25;
                case "Pint": return 0.5;
                default: return 1;
            }
        }

        // Area conversion factors (square meters as base unit)
        if (fromUnit.equals("Square Meter")) {
            switch (toUnit) {
                case "Square Kilometer": return 0.000001;
                case "Square Mile": return 3.861e-7;
                case "Acre": return 0.000247105;
                case "Hectare": return 0.0001;
                default: return 1;
            }
        } else if (fromUnit.equals("Square Kilometer")) {
            switch (toUnit) {
                case "Square Meter": return 1e6;
                case "Square Mile": return 0.386102;
                case "Acre": return 247.105;
                case "Hectare": return 100;
                default: return 1;
            }
        } else if (fromUnit.equals("Square Mile")) {
            switch (toUnit) {
                case "Square Meter": return 2.59e6;
                case "Square Kilometer": return 2.59;
                case "Acre": return 640;
                case "Hectare": return 259;
                default: return 1;
            }
        } else if (fromUnit.equals("Acre")) {
            switch (toUnit) {
                case "Square Meter": return 4046.86;
                case "Square Kilometer": return 0.00404686;
                case "Square Mile": return 0.0015625;
                case "Hectare": return 0.404686;
                default: return 1;
            }
        } else if (fromUnit.equals("Hectare")) {
            switch (toUnit) {
                case "Square Meter": return 10000;
                case "Square Kilometer": return 0.01;
                case "Square Mile": return 0.003861;
                case "Acre": return 2.47105;
                default: return 1;
            }
        }

        // Pressure conversion factors (Pascal as base unit)
        if (fromUnit.equals("Pascal")) {
            switch (toUnit) {
                case "Bar": return 0.00001;
                case "PSI": return 0.000145038;
                case "Atmosphere": return 0.00000986923;
                default: return 1;
            }
        } else if (fromUnit.equals("Bar")) {
            switch (toUnit) {
                case "Pascal": return 100000;
                case "PSI": return 14.5038;
                case "Atmosphere": return 0.986923;
                default: return 1;
            }
        } else if (fromUnit.equals("PSI")) {
            switch (toUnit) {
                case "Pascal": return 6894.76;
                case "Bar": return 0.0689476;
                case "Atmosphere": return 0.068046;
                default: return 1;
            }
        } else if (fromUnit.equals("Atmosphere")) {
            switch (toUnit) {
                case "Pascal": return 101325;
                case "Bar": return 1.01325;
                case "PSI": return 14.696;
                default: return 1;
            }
        }

        // If units are not found or not convertible, return 1
        return 1;
    }


}