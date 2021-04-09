package com.example.myapplication.Ex_Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.R;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;
import java.util.List;

public class ExampleClassActivity extends AppCompatActivity {
    RangeSlider houseSizeSlider,areaSizeSLider,priceSlider;
    AutoCompleteTextView autocomplete_property;
    String[] types = {"House/Villa", "Residential Land",
            "Flat/Apartment", "Agricultural Land",
            "Farm House Land", "Office Space",
            "Shop/Showroom", "Commercial Land",
            "Ware House/Godown", "Resort/Hotel"};
    TextInputLayout property_layout;
    String string_Propertytype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_class);
        houseSizeSlider=findViewById(R.id.example_Size_Slider_id);
        areaSizeSLider=findViewById(R.id.example_landArea_Slider_id);
        priceSlider=findViewById(R.id.example_Price_Slider_id);
        property_layout=findViewById(R.id.example_TextinputLayout);
        autocomplete_property=findViewById(R.id.example_location__autocomplete_id);

        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        autocomplete_property.setAdapter(adap);
        autocomplete_property.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                string_Propertytype = autocomplete_property.getText().toString();
                if (string_Propertytype.equals("House/Villa") | string_Propertytype.equals("Residential Land") |
                        string_Propertytype.equals("Flat") | string_Propertytype.equals("Agricultural Land") |
                        string_Propertytype.equals("Farm House") | string_Propertytype.equals("Office Space") |
                        string_Propertytype.equals("Shop/Showroom") | string_Propertytype.equals("Commercial Land") |
                        string_Propertytype.equals("Ware House/Godown") | string_Propertytype.equals("Resort")) {
                    property_layout.setError(null);
                    property_layout.setErrorEnabled(false);
                        rangeSliders();
                } else {
                    property_layout.setError("Select From The List");
                }
            }
        });
    }

    private void rangeSliders() {
        Class_Example class_example;
        if (string_Propertytype.equals("House/Villa")){
            houseSizeSlider.setValueFrom(0);
            houseSizeSlider.setValueTo(100000);
            houseSizeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    houseSizeSlider.
                    if (value)
                        
                  /*  List<Float> values = slider.getValues();
                    float min= Collections.min(values);
                    int sizeWithoutZeroMIN=(int) min;
                    String end */
                }
            });
        }
    }
}