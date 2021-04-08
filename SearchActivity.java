package com.example.myapplication.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.myapplication.NetSoftActivity;
import com.example.myapplication.server_Get_Locations.ApiInterface_Location;
import com.example.myapplication.server_Get_Locations.Retrofit_Client_Location;
import com.example.myapplication.R;
import com.example.myapplication.server_Get_Locations.AutoSuggest_LOcation_Adapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    TextInputLayout propertyLocation_layout, min_Price_Layout, max_Price_Layout;
    ArrayList<String> ch = new ArrayList<>();
    String string_Propertytype;
    RadioGroup s_r_b_radiogroup;
    ConstraintLayout constraintLayout;
    String[] chipTexts;
    Button searchButton;
    CircularImageView backsearchButton;
    ImageView radioErrorS_R_B;
    AutoCompleteTextView multiAut;
    String salerentradio;
    Chip chips;
    ChipGroup chipgroup;
    LinearLayout linearLayout;
    AutoCompleteTextView autocomplete_property, autocomplete_price_from, autocomplete_price_to;
    AutoSuggest_LOcation_Adapter autoSuggestAdapter;
    String salerent_radio_value_checked;
    String loc;
    List<String> listOfLOcations = new ArrayList<>();
    RadioButton saleB, rentB;
    TextView iWantTo_error_Text;
    TextView textviewSale_HouseVilla_Price_from, textviewSale_HouseVilla_price_to, textviewSale_HouseVilla_size_from, textviewSale_HouseVilla_size_to, textviewSale_HouseVilla_landArea_from, textviewSale_HouseVilla_landArea_to,
            textviewRent_HouseVilla_price_from, textviewRent_HouseVilla_price_to, textviewRent_HouseVilla_size_from, textviewRent_HouseVilla_size_to, textviewRent_HouseVilla_landArea_from, textviewRent_HouseVilla_landArea_to,
            textviewSale_LAND_size_from, textviewSale_LAND_size_to, textviewSale_LAND_price_from, textviewSale_LAND_price_to,
            textviewRent_LAND_size_from, textviewRent_LAND_size_to, textviewRent_LAND_price_from, textviewRent_LAND_price_to,
            textviewSale_FLAT_price_from, textviewSale_FLAT_price_to, textviewSale_FLAT_size_from, textviewSale_FLAT_size_to,
            textviewRent_FLAT_price_from, textviewRent_FLAT_price_to, textviewRent_FLAT_size_from, textviewRent_FLAT_size_to,
            textviewSale_AgriculturalLand_price_from, textviewSale_AgriculturalLand_price_to, textviewSale_AgriculturalLand_size_from, textviewSale_AgriculturalLand_size_to,
            textviewRENT_AgriculturalLand_price_from, textviewRENT_AgriculturalLand_price_to, textviewRENT_AgriculturalLand_size_from, textviewRENT_AgriculturalLand_Size_to;
    List<String> sec = new ArrayList<>();
    String e;
    RangeSlider sale_price_HouseOrvilla_RangeSlider, sale_landArea_HouseOrvilla_RangeSlider, sale_size_HouseOrvilla_RangeSlider,
            rent_price_HouseOrvilla_RangeSlider, rent_landArea_HouseOrvilla_RangeSlider, rent_size_HouseOrvilla_RangeSlider,
            sale_land_size_rangeSlider, sale_land_pricePer_cent_RangeSlider,
            rent_land_priceper_cent_RangeSlider, rent_land_size_rangeSlider_RangeSlider,
            sale_flat_size_RangeSlider, sale_flat_price_RangeSlider,
            rent_flat_size_RangeSlider, rent_flat_price_RangeSlider,
            sale_Agricultural_Land_SIZE_RangeSlider, rent_Agricultural_Land_SIZE_RangeSlider,
            sale_Agricultural_Land_PRICE_RangeSlider,rent_Agricultural_Land_PRICE_RangeSlider;
    LinearLayout sale_Housevilla_layout, rent_HouseVilla_layout,
            sale_Land_layout, rent_Land_layout, sale_Flat_layout, rent_Flat_layout,sale_agriculturalLand_layout,rent_AgriculturalLand_layout;

    String[] types = {"House/Villa", "Residential Land",
            "Flat/Apartment", "Agricultural Land",
            "Farm House Land", "Office Space",
            "Shop/Showroom", "Commercial Land",
            "Ware House/Godown", "Resort/Hotel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        min_Price_Layout = findViewById(R.id.budget_Start_inputLayout_id);

        textviewSale_HouseVilla_Price_from = findViewById(R.id.sale_housevilla_price_from_from);
        textviewSale_HouseVilla_price_to = findViewById(R.id.sale_housevilla_price_to);
        textviewSale_HouseVilla_size_from = findViewById(R.id.sale_size_from_text_id);
        textviewSale_HouseVilla_size_to = findViewById(R.id.sale_size_to_text_id);
        textviewSale_HouseVilla_landArea_from = findViewById(R.id.sale_landarea_from_text_id);
        textviewSale_HouseVilla_landArea_to = findViewById(R.id.sale_landarea_to_text_id);
        textviewRent_HouseVilla_price_from = findViewById(R.id.rent_housevillaprice_from_from);
        textviewRent_HouseVilla_price_to = findViewById(R.id.rent_housevillaprice_from_to);
        textviewRent_HouseVilla_size_from = findViewById(R.id.rent_housevillasize_from_text_id);
        textviewRent_HouseVilla_size_to = findViewById(R.id.rent_housevillasize_to_text_id);
        textviewRent_HouseVilla_landArea_from = findViewById(R.id.rent_housevillalandarea_from_text_id);
        textviewRent_HouseVilla_landArea_to = findViewById(R.id.rent_housevillalandarea_to_text_id);
        textviewSale_LAND_size_from = findViewById(R.id.sale_landareasize_from_text_id);
        textviewSale_LAND_size_to = findViewById(R.id.sale_landareasize_to_text_id);
        textviewSale_LAND_price_from = findViewById(R.id.sale_landarea_price_from_from);
        textviewSale_LAND_price_to = findViewById(R.id.sale_landarea_price_from_to);
        textviewRent_LAND_size_from = findViewById(R.id.rent_landarea_size_landarea_from_text_id);
        textviewRent_LAND_size_to = findViewById(R.id.rent_landarea_size_landarea_to_text_id);
        textviewRent_LAND_price_from = findViewById(R.id.rent_landarea_price_from_from);
        textviewRent_LAND_price_to = findViewById(R.id.rent_landarea_price_from_to);
        textviewSale_FLAT_price_from = findViewById(R.id.sale_flatprice_from_from);
        textviewSale_FLAT_price_to = findViewById(R.id.sale_flatprice_from_to);
        textviewRent_FLAT_price_from = findViewById(R.id.rent_flatprice_from_from);
        textviewRent_FLAT_price_to = findViewById(R.id.rent_flatprice_from_to);
        textviewSale_FLAT_size_from = findViewById(R.id.sale_flat_size_from_text_id);
        textviewSale_FLAT_size_to = findViewById(R.id.sale_flat_size_to_text_id);
        textviewRent_FLAT_size_from = findViewById(R.id.rent_flat_size_from_text_id);
        textviewRent_FLAT_size_to = findViewById(R.id.rent_flatsize_to_text_id);
        textviewSale_AgriculturalLand_price_from=findViewById(R.id.sale_agricultural_landarea_price_from_from);
        textviewSale_AgriculturalLand_price_to=findViewById(R.id.sale_agricultural_landarea_price_from_to);
        textviewSale_AgriculturalLand_size_from=findViewById(R.id.sale_agricultural_landareasize_from_text_id);
        textviewSale_AgriculturalLand_size_to=findViewById(R.id.sale_agricultural_landareasize_to_text_id);
        textviewRENT_AgriculturalLand_price_from=findViewById(R.id.rent_agricultural_landarea_price_from_from);
        textviewRENT_AgriculturalLand_price_to=findViewById(R.id.rent_agricultural_landarea_price_from_to);
        textviewRENT_AgriculturalLand_size_from=findViewById(R.id.rent_agricultural_landarea_size_landarea_from_text_id);
        textviewRENT_AgriculturalLand_Size_to=findViewById(R.id.rent_agricultural_landarea_size_landarea_to_text_id);


        max_Price_Layout = findViewById(R.id.budget_END_inputLayout_id);
        saleB = findViewById(R.id.sale_searchid);
        iWantTo_error_Text = findViewById(R.id.i_want_to_error_radio__search_id);
        rentB = findViewById(R.id.rent_searchid);
        multiAut = findViewById(R.id.editType);
        chipgroup = findViewById(R.id.chips);
        linearLayout = findViewById(R.id.autoChip_id);
        propertyLocation_layout = findViewById(R.id.sp_inputLayout_id);
        backsearchButton = findViewById(R.id.back_idh_search);
        searchButton = findViewById(R.id.search_butt);
        autocomplete_property = findViewById(R.id.location__autocomplete_id);
        s_r_b_radiogroup = findViewById(R.id.radio_group_id_search);
        constraintLayout = findViewById(R.id.const_radio_id);
        autocomplete_price_from = findViewById(R.id.budget_Start_autocomplete_id);
        autocomplete_price_to = findViewById(R.id.budget_END_autocomplete_id);

        sale_Housevilla_layout = findViewById(R.id.sale_housevilla_rangeSlider_Layout_id);
        rent_HouseVilla_layout = findViewById(R.id.rent_housevilla_rangeSlider_Layout_id);
        sale_Land_layout = findViewById(R.id.sale_land_rangeSlider_Layout_id);
        rent_Land_layout = findViewById(R.id.rent_land_rangeSlider_Layout_id);
        sale_Flat_layout = findViewById(R.id.sale_flat_rangeSlider_Layout_id);
        rent_Flat_layout = findViewById(R.id.rent_flat_rangeSlider_Layout_id);
        sale_agriculturalLand_layout=findViewById(R.id.sale_agricultural_land_rangeSlider_Layout_id);
        rent_AgriculturalLand_layout=findViewById(R.id.rent_agricultural_land_rangeSlider_Layout_id);

        sale_price_HouseOrvilla_RangeSlider = findViewById(R.id.sale_housevilla_price_rangeslider);
        sale_landArea_HouseOrvilla_RangeSlider = findViewById(R.id.sale_housevilla_landArea_rangeslider);
        sale_size_HouseOrvilla_RangeSlider = findViewById(R.id.sale_housevilla_size_rangeslider);
        rent_price_HouseOrvilla_RangeSlider = findViewById(R.id.rent_housevillaprice_rangeslider);
        rent_landArea_HouseOrvilla_RangeSlider = findViewById(R.id.rent_housevillalandArea_rangeslider);
        rent_size_HouseOrvilla_RangeSlider = findViewById(R.id.rent_housevillasize_rangeslider);
        sale_land_size_rangeSlider = findViewById(R.id.sale_landarea_size_rangeslider);
        sale_land_pricePer_cent_RangeSlider = findViewById(R.id.sale_landarea_price_rangeslider);
        rent_land_priceper_cent_RangeSlider = findViewById(R.id.rent_landarea_price_rangeslider);
        rent_land_size_rangeSlider_RangeSlider = findViewById(R.id.rent_landarealandArea_size__rangeslider);
        sale_flat_size_RangeSlider = findViewById(R.id.sale_flat_size_rangeslider);
        sale_flat_price_RangeSlider = findViewById(R.id.sale_flatprice_rangeslider);
        rent_flat_size_RangeSlider = findViewById(R.id.rent_flat_size_rangeslider);
        rent_flat_price_RangeSlider = findViewById(R.id.rent_flatprice_rangeslider);
        sale_Agricultural_Land_SIZE_RangeSlider=findViewById(R.id.sale_agricultural_landarea_size_rangeslider);
        rent_Agricultural_Land_SIZE_RangeSlider=findViewById(R.id.rent_agricultural_landarealandArea_size__rangeslider);
        sale_Agricultural_Land_PRICE_RangeSlider=findViewById(R.id.sale_agricultural_landarea_price_rangeslider);
        rent_Agricultural_Land_PRICE_RangeSlider=findViewById(R.id.rent_agricultural_landarea_price_rangeslider);
        //  radioErrorS_R_B=findViewById(R.id.slae_rent_buy_error_search_id);
        //

        String[] string_price_From = {"5 Lac", "10 Lac",
                "20 Lac", "30 Lac", "30 Lac",
                "40 Lac", "50 Lac", "60 Lac",
                "70 Lac", "80 Lac"};
        String[] string_price_to = {"5 Lac", "10 Lac",
                "20 Lac", "30 Lac", "30 Lac",
                "40 Lac", "50 Lac", "60 Lac",
                "70 Lac", "80 Lac"};
        sec.add("5 Lac");
        sec.add("10 Lac");
        sec.add("20 Lac");
        sec.add("30 Lac");
        sec.add("40 Lac");
        sec.add("50 Lac");
        sec.add("60 Lac");
        sec.add("70 Lac");
        sec.add("80 Lac");

        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        autocomplete_property.setAdapter(adap);
        ArrayAdapter<String> priceAdapterMIn = new ArrayAdapter<>(this, R.layout.spinner_dropdown_rupees_icon, string_price_From);
        autocomplete_price_from.setAdapter(priceAdapterMIn);
        ArrayAdapter<String> priceAdapterTO = new ArrayAdapter<>(this, R.layout.spinner_dropdown_rupees_icon, sec);
        autocomplete_price_to.setAdapter(priceAdapterTO);
        autoSuggestAdapter = new AutoSuggest_LOcation_Adapter(this, android.R.layout.simple_dropdown_item_1line);
        multiAut.setAdapter(autoSuggestAdapter);

        textWatcher_Auto();
        multiChip();
        toSearchButton();
        validPropertyType_Typing();
        checkedRadioS_R_B();
        autocompleteMin_Typing();
        autocomplete_Max_Typing();
        backsearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, NetSoftActivity.class);
                startActivity(intent);
            }
        });

        sale_price_HouseOrvilla_RangeSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                currencyFormat.setCurrency(Currency.getInstance("INR"));
                return currencyFormat.format(value)+"qw";
            }
        });
        rent_price_HouseOrvilla_RangeSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currencyFormat2 = NumberFormat.getCurrencyInstance();
                currencyFormat2.setCurrency(Currency.getInstance("INR"));

                return currencyFormat2.format(value);
            }
        });

    }


    private void autocompleteMin_Typing() {

        autocomplete_price_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string_Min_Value = autocomplete_price_from.getText().toString();
                if (string_Min_Value.isEmpty()) {
                    min_Price_Layout.setError("Select From The List");
                } else {
                    min_Price_Layout.setError(null);
                    min_Price_Layout.setErrorEnabled(false);
                }

            }
        });
    }


    private void autocomplete_Max_Typing() {
        autocomplete_price_to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string_Min_Value = autocomplete_price_to.getText().toString();
                if (string_Min_Value.isEmpty()) {
                    max_Price_Layout.setError("Select From The List");
                } else {
                    max_Price_Layout.setError(null);
                    max_Price_Layout.setErrorEnabled(false);
                }

            }
        });
    }

    private Boolean min_Price_Typing() {
        String string_minValue = autocomplete_price_from.getText().toString();
        if (string_minValue.isEmpty()) {
            min_Price_Layout.setError("Select From The List");

            return false;
        } else {
            min_Price_Layout.setError(null);
            min_Price_Layout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean max_Price_Typing() {
        String string_minValue = autocomplete_price_to.getText().toString();
        if (string_minValue.isEmpty()) {
            max_Price_Layout.setError(" ");

            return false;
        } else {
            max_Price_Layout.setError(null);
            max_Price_Layout.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validPropertyLocation_Typing() {
        ch = new ArrayList<>();
        if (chipgroup.getChildCount() == 0 && ch.size() == 0) {
            multiAut.setError("Enter Location");
            return false;
        } else {
            for (int i = 0; i < chipgroup.getChildCount(); i++) {
                ch.add(((Chip) chipgroup.getChildAt(i)).getText().toString());
            }
            Log.e("chips String", String.valueOf(ch));
            return true;
        }
    }

    private void validPropertyType_Typing() {
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
                    propertyLocation_layout.setError(null);
                    propertyLocation_layout.setErrorEnabled(false);
                    if (string_Propertytype != null & salerent_radio_value_checked != null) {
                        rangeSliders();
                    }
                } else {
                    propertyLocation_layout.setError("Select From The List");
                }

            }
        });
    }

    public Boolean validProType() {
        string_Propertytype = autocomplete_property.getText().toString();
        if (string_Propertytype.equals("House/Villa") | string_Propertytype.equals("Residential Land") |
                string_Propertytype.equals("Flat") | string_Propertytype.equals("Agricultural Land") |
                string_Propertytype.equals("Farm House") | string_Propertytype.equals("Office Space") |
                string_Propertytype.equals("Shop/Showroom") | string_Propertytype.equals("Commercial Land") |
                string_Propertytype.equals("Ware House/Godown") | string_Propertytype.equals("Resort")) {
            propertyLocation_layout.setError(null);
            propertyLocation_layout.setErrorEnabled(false);
            return true;
        } else {
            propertyLocation_layout.setError("Select From The List");
            return false;
        }
    }

    private void customToastNetsoft() {
        StyleableToast.makeText(getApplicationContext(),
                "Please Select The Required Items", Toast.LENGTH_LONG, R.style.toaststyle).show();
    }

    private void checkedRadioS_R_B() {
        s_r_b_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int s_ag_checked = s_r_b_radiogroup.getCheckedRadioButtonId();

                RadioButton sele_rent_radiobutton_checked = (RadioButton) findViewById(s_ag_checked);
                salerent_radio_value_checked = sele_rent_radiobutton_checked.getText().toString();

                if (s_ag_checked != -1) {
                    iWantTo_error_Text.setVisibility(View.GONE);
                    if (string_Propertytype != null & salerent_radio_value_checked != null) {
                        rangeSliders();
                    }
                }
                // rangeSliders();

            }
        });

    }

    private void rangeSliders() {
        if (string_Propertytype.equals("House/Villa") & salerent_radio_value_checked.equals("Sale")) {
            sale_Housevilla_layout.setVisibility(View.VISIBLE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_Land_layout.setVisibility(View.GONE);
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            HouseOrvilla_RangeSlider_M();
        } else if (string_Propertytype.equals("House/Villa") & salerent_radio_value_checked.equals("Rent")) {
            rent_HouseVilla_layout.setVisibility(View.VISIBLE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_Land_layout.setVisibility(View.GONE);
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            HouseOrvilla_RangeSlider_M();
        } else if ((string_Propertytype.equals("Residential Land") |
                string_Propertytype.equals("Farm House") | string_Propertytype.equals("Commercial Land")) &
                salerent_radio_value_checked.equals("Sale")) {
            sale_Land_layout.setVisibility(View.VISIBLE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            rent_Land_layout.setVisibility(View.GONE);
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            land_rangeSlider_M();
        } else if ((string_Propertytype.equals("Residential Land")  |
                string_Propertytype.equals("Farm House") | string_Propertytype.equals("Commercial Land")) &
                salerent_radio_value_checked.equals("Rent")) {
            rent_Land_layout.setVisibility(View.VISIBLE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            land_rangeSlider_M();
        }else if (string_Propertytype.equals("Agricultural Land") & salerent_radio_value_checked.equals("Sale")){
            rent_Land_layout.setVisibility(View.GONE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.VISIBLE);
            agricltural_Land_Slider();
        }
        else if (string_Propertytype.equals("Agricultural Land") & salerent_radio_value_checked.equals("Rent")){
            rent_Land_layout.setVisibility(View.GONE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.VISIBLE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            agricltural_Land_Slider();
        }
        else if ((string_Propertytype.equals("Flat") |  string_Propertytype.equals("Office Space") |
                string_Propertytype.equals("Shop/Showroom")  |
                string_Propertytype.equals("Ware House/Godown") | string_Propertytype.equals("Resort")) &
                salerent_radio_value_checked.equals("Sale")){
            sale_Flat_layout.setVisibility(View.VISIBLE);
            rent_Land_layout.setVisibility(View.GONE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.GONE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            flat__M();
        }else if ((string_Propertytype.equals("Flat") |  string_Propertytype.equals("Office Space") |
                string_Propertytype.equals("Shop/Showroom")  |
                string_Propertytype.equals("Ware House/Godown") | string_Propertytype.equals("Resort")) &
                salerent_radio_value_checked.equals("Rent")){
            sale_Flat_layout.setVisibility(View.GONE);
            rent_Land_layout.setVisibility(View.GONE);
            sale_Land_layout.setVisibility(View.GONE);
            rent_HouseVilla_layout.setVisibility(View.GONE);
            sale_Housevilla_layout.setVisibility(View.GONE);
            rent_Flat_layout.setVisibility(View.VISIBLE);
            rent_AgriculturalLand_layout.setVisibility(View.GONE);
            sale_agriculturalLand_layout.setVisibility(View.GONE);
            flat__M();
        }

    }


    private void HouseOrvilla_RangeSlider_M() {
        if (sale_Housevilla_layout.getVisibility() == View.VISIBLE) {
            sale_size_HouseOrvilla_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int sizeWithoutZeroMIN=(int) min;

                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 6) {
                        String first1= stringMin.substring(0,1);
                        textviewSale_HouseVilla_size_from.setText(first1 + " Lakh SQFT");
                    } else if (check_Min == 5) {
                        String first2=stringMin.substring(0,2);
                        textviewSale_HouseVilla_size_from.setText(first2 + "K SQFT");
                    } else if (check_Min <= 4) {
                        textviewSale_HouseVilla_size_from.setText(sizeWithoutZeroMIN + " SQFT");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    int sizeWithoutZeroMAX=(int) max;

                        if (check_Max == 6) {
                            String first1= stringMax.substring(0,1);
                            textviewSale_HouseVilla_size_to.setText(first1 + " Lakh SQFT");
                        } else if (check_Max == 5) {
                            String first2=stringMax.substring(0,2);
                            textviewSale_HouseVilla_size_to.setText(first2 + "K SQFT");
                        } else if (check_Max <= 4) {
                            textviewSale_HouseVilla_size_to.setText(sizeWithoutZeroMAX + " SQFT");
                        }
                    }
                });

            sale_landArea_HouseOrvilla_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 3) {
                        String first1Lakh = stringMin.substring(0, 1);
                        String secondDigit=stringMin.substring(1,2);
                        textviewSale_HouseVilla_landArea_from.setText(first1Lakh +"."+secondDigit+ " Acre");
                    } else if (check_Min == 2) {
                        String first2Thousand = stringMin.substring(0, 2);
                        textviewSale_HouseVilla_landArea_from.setText(first2Thousand + " Cent");
                    } else if (check_Min <= 1) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewSale_HouseVilla_landArea_from.setText(sizeWithoutZeroMIN + " Cent");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 3) {
                        String first1Lakh = stringMax.substring(0, 1);
                        String secondDigit=stringMax.substring(1,2);
                        textviewSale_HouseVilla_landArea_to.setText(first1Lakh+"."+secondDigit+ " Acre");
                    } else if (check_Max == 2) {
                        String first2Thousand = stringMax.substring(0, 2);
                        textviewSale_HouseVilla_landArea_to.setText(first2Thousand + " Cent");
                    } else if (check_Max <= 1) {
                        int sizeWithoutZeroMAX=(int) max;
                        textviewSale_HouseVilla_landArea_to.setText(sizeWithoutZeroMAX + " Cent");
                    }
                }
            });

            sale_price_HouseOrvilla_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {

                    List<Float> values = slider.getValues();
                    double min = Collections.min(values);
                    String stringMin = String.valueOf((int) min);
                    //           String stringlastmin=stringMin.replace("[^0-9]","");

                    //   int check_Min= Integer.parseInt(String.valueOf(stringMin.length()-2));
                    int lengthMin = (int) (Math.log10(min) + 1);

                    if (lengthMin == 9) {
                        String first2Crore = stringMin.substring(0, 2);

                        textviewSale_HouseVilla_Price_from.setText(first2Crore + " Cr.");
                    } else if (lengthMin == 8) {
                        String first1Cr = stringMin.substring(0, 1);
                        String secondDigit=stringMin.substring(1,2);
                        textviewSale_HouseVilla_Price_from.setText(first1Cr+"."+secondDigit+ " Cr.");
                    } else if (lengthMin == 7) {
                        String first2Lakh = stringMin.substring(0, 2);
                        textviewSale_HouseVilla_Price_from.setText(first2Lakh + " Lakh");
                    } else if (lengthMin == 6) {
                        String first1Lakh = stringMin.substring(0, 1);
                        textviewSale_HouseVilla_Price_from.setText(first1Lakh + " Lakh");
                    } else if (lengthMin < 6) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewSale_HouseVilla_Price_from.setText(sizeWithoutZeroMIN + " Rs.");
                    }


                    float max = Collections.max(values);
                    String stringMax = String.valueOf((int) max);

                    int lengthMax = (int) (Math.log10(max) + 1);
                    if (lengthMax == 9) {
                        String first2Crore = stringMax.substring(0, 2);
                        textviewSale_HouseVilla_price_to.setText(first2Crore + " Cr.");
                    } else if (lengthMax == 8) {
                        String first1Cr = stringMax.substring(0, 1);
                        String secondDigit=stringMax.substring(1,2);
                        textviewSale_HouseVilla_price_to.setText(first1Cr+"."+secondDigit+ " Cr.");
                    } else if (lengthMax == 7) {
                        String first2Lakh = stringMax.substring(0, 2);
                        textviewSale_HouseVilla_price_to.setText(first2Lakh + " Lakh");
                    } else if (lengthMax == 6) {
                        String first1Lakh = stringMax.substring(0, 1);
                        textviewSale_HouseVilla_price_to.setText(first1Lakh + " Lakh");
                    } else if (lengthMax < 6) {
                        int sizeWithoutZeroMAX=(int) max;
                        textviewSale_HouseVilla_price_to.setText(sizeWithoutZeroMAX + " Rs.");
                    }
                }
            });
        } else if (rent_HouseVilla_layout.getVisibility() == View.VISIBLE) {
            rent_size_HouseOrvilla_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);

                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 6) {
                        String first1Lakh = stringMin.substring(0, 1);
                        textviewRent_HouseVilla_size_from.setText(first1Lakh + " Lakh SQFT");
                    } else if (check_Min == 5) {
                        String first2Thousand = stringMin.substring(0, 2);
                        textviewRent_HouseVilla_size_from.setText(first2Thousand + " K SQFT");
                    } else if (check_Min <= 4) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewRent_HouseVilla_size_from.setText(sizeWithoutZeroMIN + " SQFT");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 6) {
                        String first1Lakh = stringMax.substring(0, 1);
                        textviewRent_HouseVilla_size_to.setText(first1Lakh + " Lakh SQFT");
                    } else if (check_Max == 5) {
                        String first2Thousand = stringMax.substring(0, 2);
                        textviewRent_HouseVilla_size_to.setText(first2Thousand + " K SQFT");
                    } else if (check_Max <= 4) {
                        int sizeWithoutZeroMax=(int) max;
                        textviewRent_HouseVilla_size_to.setText(sizeWithoutZeroMax + " SQFT");

                    }
                }
            });


            rent_landArea_HouseOrvilla_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 3) {
                        String first1Lakh = stringMin.substring(0, 1);
                        String secDigit=stringMin.substring(1,2);
                        textviewRent_HouseVilla_landArea_from.setText(first1Lakh+"."+secDigit+ " Acre");
                    } else if (check_Min == 2) {
                        String first2Thousand = stringMin.substring(0, 2);
                        textviewRent_HouseVilla_landArea_from.setText(first2Thousand + " Cent");
                    } else if (check_Min <= 1) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewRent_HouseVilla_landArea_from.setText(sizeWithoutZeroMIN + " Cent");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 3) {
                        String first1Lakh = stringMax.substring(0, 1);
                        String secDigit=stringMax.substring(1,2);
                        textviewRent_HouseVilla_landArea_to.setText(first1Lakh+"."+secDigit+ " Acre");
                    } else if (check_Max == 2) {
                        String first2Thousand = stringMax.substring(0, 2);
                        textviewRent_HouseVilla_landArea_to.setText(first2Thousand + " Cent");
                    } else if (check_Max <= 1) {
                        int sizeWithoutZeroMax=(int) max;
                        textviewRent_HouseVilla_landArea_to.setText(sizeWithoutZeroMax + " Cent");

                    }
                }
            });

            rent_price_HouseOrvilla_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {

                    List<Float> values = slider.getValues();
                    double min = Collections.min(values);
                    String stringMin = String.valueOf((int) min);
                    //           String stringlastmin=stringMin.replace("[^0-9]","");

                    //   int check_Min= Integer.parseInt(String.valueOf(stringMin.length()-2));
                    int lengthMin = (int) (Math.log10(min) + 1);

                    if (lengthMin == 7) {
                        String first2Lakh = stringMin.substring(0, 2);
                        textviewRent_HouseVilla_price_from.setText(first2Lakh + " Lakh");
                    } else if (lengthMin == 6) {
                        String first1Lakh = stringMin.substring(0, 1);
                        textviewRent_HouseVilla_price_from.setText(first1Lakh + " Lakh");
                    } else if (lengthMin < 6) {
                        int sizeWithoutZeroMin=(int) min;
                        textviewRent_HouseVilla_price_from.setText(sizeWithoutZeroMin + " Rs.");
                    }


                    float max = Collections.max(values);
                    //  BigInteger bigInteger=BigInteger.valueOf(Long.parseLong(String.valueOf(max)));
                    String stringMax = String.valueOf((int) max);
                    int lengthMax = (int) (Math.log10(max) + 1);
                    if (lengthMax == 7) {
                        String first2Lakh = stringMax.substring(0, 2);
                        textviewRent_HouseVilla_price_to.setText(first2Lakh + " Lakh");
                    } else if (lengthMax == 6) {
                        String first1Lakh = stringMax.substring(0, 1);
                        textviewRent_HouseVilla_price_to.setText(first1Lakh + " Lakh");
                    } else if (lengthMax < 6) {
                        int sizeWithoutZeroMax=(int) max;
                        textviewRent_HouseVilla_price_to.setText(sizeWithoutZeroMax + " Rs.");
                    }
                }
            });
        }
    }

    private void land_rangeSlider_M() {
        if (sale_Land_layout.getVisibility() == View.VISIBLE) {
            sale_land_size_rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 3) {
                        String first1 = stringMin.substring(0, 1);
                        String secDigit=stringMin.substring(1,2);
                        textviewSale_LAND_size_from.setText(first1+"."+secDigit + " Acre");
                    } else if (check_Min == 2) {
                        String first2 = stringMin.substring(0, 2);
                        textviewSale_LAND_size_from.setText(first2 + " Cent");
                    } else if (check_Min == 1) {
                        int withoutZeroMin= (int)min;
                        textviewSale_LAND_size_from.setText(withoutZeroMin + " Cent");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 3) {
                        String first1Lakh = stringMax.substring(0, 1);
                        String secDigit=stringMax.substring(1,2);
                        textviewSale_LAND_size_to.setText(first1Lakh+"."+secDigit + " Acre");
                    } else if (check_Max == 2) {
                        String first2Thousand = stringMax.substring(0, 2);
                        textviewSale_LAND_size_to.setText(first2Thousand + " Cent");
                    } else if (check_Max <= 1) {
                        int withoutZeroMax= (int)max;
                        textviewSale_LAND_size_to.setText(withoutZeroMax + " Cent");
                    }
                }
            });

            sale_land_pricePer_cent_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 7) {
                        String first2Lakh = stringMin.substring(0, 2);
                        textviewSale_LAND_price_from.setText(first2Lakh + " Lakhs");
                    } else if (check_Min == 6) {
                        String first1lak = stringMin.substring(0, 1);
                        textviewSale_LAND_price_from.setText(first1lak + " Lakhs");
                    } else if (check_Min <= 5) {
                        int withoutZeroMin= (int)min;
                        textviewSale_LAND_price_from.setText(withoutZeroMin + " Rs.");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 7) {
                        String first2Lakh = stringMax.substring(0, 2);
                        textviewSale_LAND_price_to.setText(first2Lakh + " Lakhs");
                    } else if (check_Max == 6) {
                        String first1lak = stringMax.substring(0, 1);
                        textviewSale_LAND_price_to.setText(first1lak + " Lakhs");
                    } else if (check_Max <= 5) {

                        textviewSale_LAND_price_to.setText(stringMax + " Rs.");
                    }
                }
            });
        } else if (rent_Land_layout.getVisibility() == View.VISIBLE) {
            rent_land_size_rangeSlider_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 3) {
                        String first1 = stringMin.substring(0, 1);
                        String secDigit=stringMin.substring(1,2);
                        textviewRent_LAND_size_from.setText(first1+"."+secDigit + " Acre");
                    } else if (check_Min == 2) {
                        String first2 = stringMin.substring(0, 2);
                        textviewRent_LAND_size_from.setText(first2 + " Cent");
                    } else if (check_Min == 1) {
                        int withoutZeroMin=(int) min;
                        textviewRent_LAND_size_from.setText(withoutZeroMin + " Cent");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 3) {
                        String first1 = stringMax.substring(0, 1);
                        String secDigit=stringMax.substring(1,2);
                        textviewRent_LAND_size_to.setText(first1+"."+secDigit + " Acre");
                    } else if (check_Max == 2) {
                        String first2 = stringMax.substring(0, 2);
                        textviewRent_LAND_size_to.setText(first2 + " Cent");
                    } else if (check_Max <= 1) {
                        textviewRent_LAND_size_to.setText(stringMax + " Cent");
                    }
                }
            });

            rent_land_priceper_cent_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 6) {
                        String first1lak = stringMin.substring(0, 1);
                        textviewRent_LAND_price_from.setText(first1lak + " Lakhs");
                    } else if (check_Min <= 5) {
                        int withourtZeroMin=(int)min;
                        textviewRent_LAND_price_from.setText(withourtZeroMin + " Rs.");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 6) {
                        String first1lak = stringMax.substring(0, 1);
                        textviewRent_LAND_price_to.setText(first1lak + " Lakhs");
                    } else if (check_Max <= 5) {

                        textviewRent_LAND_price_to.setText(stringMax + " Rs.");
                    }
                }
            });
        }
    }

    private void agricltural_Land_Slider() {
        if (sale_agriculturalLand_layout.getVisibility()==View.VISIBLE) {
            sale_Agricultural_Land_SIZE_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 3) {
                        String first1 = stringMin.substring(0, 1);
                        String secDigit=stringMin.substring(1,2);
                        textviewSale_AgriculturalLand_size_from.setText(first1+"."+secDigit + " Acre");
                    } else if (check_Min == 2) {
                        String first2 = stringMin.substring(0, 2);
                        textviewSale_AgriculturalLand_size_from.setText(first2 + " Cent");
                    } else if (check_Min == 1) {
                        int withoutZeroMin= (int)min;
                        textviewSale_AgriculturalLand_size_from.setText(withoutZeroMin + " Cent");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 3) {
                        String first1Lakh = stringMax.substring(0, 1);
                        String secDigit=stringMax.substring(1,2);
                        textviewSale_AgriculturalLand_size_to.setText(first1Lakh+"."+secDigit + " Acre");
                    } else if (check_Max == 2) {
                        String first2Thousand = stringMax.substring(0, 2);
                        textviewSale_AgriculturalLand_size_to.setText(first2Thousand + " Cent");
                    } else if (check_Max <= 1) {
                        int withoutZeroMax= (int)max;
                        textviewSale_AgriculturalLand_size_to.setText(withoutZeroMax + " Cent");
                    }
                }

            });
            sale_Agricultural_Land_PRICE_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {

                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 7) {
                        String first2Lakh = stringMin.substring(0, 2);
                        textviewSale_AgriculturalLand_price_from.setText(first2Lakh + " Lakh");
                    } else if (check_Min == 6) {
                        String first1lak = stringMin.substring(0, 1);
                        textviewSale_AgriculturalLand_price_from.setText(first1lak + " Lakh");
                    } else if (check_Min <= 5) {
                        int withoutZeroMin= (int)min;
                        textviewSale_AgriculturalLand_price_from.setText(withoutZeroMin + " Rs.");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 7) {
                        String first2Lakh = stringMax.substring(0, 2);
                        textviewSale_AgriculturalLand_price_to.setText(first2Lakh + " Lakh");
                    } else if (check_Max == 6) {
                        String first1lak = stringMax.substring(0, 1);
                        textviewSale_AgriculturalLand_price_to.setText(first1lak + " Lakh");
                    } else if (check_Max <= 5) {

                        textviewSale_AgriculturalLand_price_to.setText(stringMax + " Rs.");
                    }
                }
            });
        }else if (rent_AgriculturalLand_layout.getVisibility()==View.VISIBLE){
            rent_Agricultural_Land_SIZE_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 5) {
                        String first1 = stringMin.substring(0, 3);
                        textviewRENT_AgriculturalLand_size_from.setText(first1 + " Acre");
                    } else if (check_Min == 4) {
                        String first2 = stringMin.substring(0, 2);
                        String secDigit=stringMin.substring(2,3);
                        textviewRENT_AgriculturalLand_size_from.setText(first2+"."+secDigit+ " Acre");
                    } else if (check_Min == 3) {
                        String first1 = stringMin.substring(0, 1);
                        textviewRENT_AgriculturalLand_size_from.setText(first1 + " Acre");
                    }else if (check_Min <= 2) {
                        int withoutZeroMin= (int)min;
                        textviewRENT_AgriculturalLand_size_from.setText(withoutZeroMin + " Cent");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 5) {
                        String first1Lakh = stringMax.substring(0, 3);
                        textviewRENT_AgriculturalLand_Size_to.setText(first1Lakh + " Acre");
                    } else if (check_Max == 4) {
                        String first2Thousand = stringMax.substring(0, 2);
                        String secDigit=stringMax.substring(2,3);
                        textviewRENT_AgriculturalLand_Size_to.setText(first2Thousand+"."+secDigit + " Acre");
                    } else if (check_Max <= 3) {
                        String first1 = stringMin.substring(0, 1);
                        textviewRENT_AgriculturalLand_Size_to.setText(first1 + " Acre");
                    }else if (check_Max <= 2) {
                        int withoutZeroMax= (int)max;
                        textviewRENT_AgriculturalLand_Size_to.setText(withoutZeroMax + " Cent");
                    }
                }
            });

            rent_Agricultural_Land_PRICE_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {

                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);
                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 6) {
                        String first1Lakh = stringMin.substring(0, 1);
                        textviewRENT_AgriculturalLand_price_from.setText(first1Lakh + " Lakh");
                    } else if (check_Min == 5) {
                        String first2lak = stringMin.substring(0, 2);
                        textviewRENT_AgriculturalLand_price_from.setText(first2lak + "K");
                    } else if (check_Min <= 4) {
                        int withoutZeroMin= (int)min;
                        textviewRENT_AgriculturalLand_price_from.setText(withoutZeroMin + " Rs.");
                    }
                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));
                    if (check_Max == 6) {
                        String first1Lakh = stringMax.substring(0, 1);
                        textviewRENT_AgriculturalLand_price_to.setText(first1Lakh + " Lakh");
                    } else if (check_Max == 5) {
                        String first2lak = stringMax.substring(0, 2);
                        textviewRENT_AgriculturalLand_price_to.setText(first2lak + " K");
                    } else if (check_Max <= 4) {
                        int withoutZeroMax= (int)max;
                        textviewRENT_AgriculturalLand_price_to.setText(withoutZeroMax + " Rs.");
                    }
                }
            });
        }
    }

    private void flat__M() {
        if (sale_Flat_layout.getVisibility() == View.VISIBLE) {
            sale_flat_size_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);

                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 6) {
                        String first1= stringMin.substring(0,1);
                        textviewSale_FLAT_size_from.setText(first1 + " Lakh SQFT");
                    } else if (check_Min == 5) {
                        String first2=stringMin.substring(0,2);
                        textviewSale_FLAT_size_from.setText(first2 + "K SQFT");
                    } else if (check_Min <= 4) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewSale_FLAT_size_from.setText(sizeWithoutZeroMIN + " SQFT");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));

                    if (check_Max == 6) {
                        String first1= stringMax.substring(0,1);
                        textviewSale_FLAT_size_to.setText(first1 + " Lakh SQFT");
                    } else if (check_Max == 5) {
                        String first2=stringMax.substring(0,2);
                        textviewSale_FLAT_size_to.setText(first2 + "K SQFT");
                    } else if (check_Max <= 4) {
                        int sizeWithoutZeroMAX=(int) max;
                        textviewSale_FLAT_size_to.setText(sizeWithoutZeroMAX + " SQFT");
                    }
                }
            });

            sale_flat_price_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    double min = Collections.min(values);
                    String stringMin = String.valueOf((int) min);
                    //           String stringlastmin=stringMin.replace("[^0-9]","");

                    //   int check_Min= Integer.parseInt(String.valueOf(stringMin.length()-2));
                    int lengthMin = (int) (Math.log10(min) + 1);

                    if (lengthMin == 9) {
                        String first2Crore = stringMin.substring(0, 2);
                        textviewSale_FLAT_price_from.setText(first2Crore + " Cr.");
                    } else if (lengthMin == 8) {
                        String first1Cr = stringMin.substring(0, 1);
                        String secondDigit=stringMin.substring(1,2);
                        textviewSale_FLAT_price_from.setText(first1Cr+"."+secondDigit+ " Cr.");
                    } else if (lengthMin == 7) {
                        String first2Lakh = stringMin.substring(0, 2);
                        textviewSale_FLAT_price_from.setText(first2Lakh + " Lakh");
                    } else if (lengthMin == 6) {
                        String first1Lakh = stringMin.substring(0, 1);
                        textviewSale_FLAT_price_from.setText(first1Lakh + " Lakh");
                    } else if (lengthMin < 6) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewSale_FLAT_price_from.setText(sizeWithoutZeroMIN + " Rs.");
                    }


                    float max = Collections.max(values);
                    String stringMax = String.valueOf((int) max);

                    int lengthMax = (int) (Math.log10(max) + 1);
                    if (lengthMax == 9) {
                        String first2Crore = stringMax.substring(0, 2);
                        textviewSale_FLAT_price_to.setText(first2Crore + " Cr.");
                    } else if (lengthMax == 8) {
                        String first1Cr = stringMax.substring(0, 1);
                        String secondDigit=stringMax.substring(1,2);
                        textviewSale_FLAT_price_to.setText(first1Cr+"."+secondDigit+ " Cr.");
                    } else if (lengthMax == 7) {
                        String first2Lakh = stringMax.substring(0, 2);
                        textviewSale_FLAT_price_to.setText(first2Lakh + " Lakh");
                    } else if (lengthMax == 6) {
                        String first1Lakh = stringMax.substring(0, 1);
                        textviewSale_FLAT_price_to.setText(first1Lakh + " Lakh");
                    } else if (lengthMax < 6) {
                        int sizeWithoutZeroMAX=(int) max;
                        textviewSale_FLAT_price_to.setText(sizeWithoutZeroMAX + " Rs.");
                    }
                }
            });
        } else if (rent_Flat_layout.getVisibility() == View.VISIBLE) {
            rent_flat_size_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    float min = Collections.min(values);
                    String stringMin = String.valueOf(min);

                    int check_Min = Integer.parseInt(String.valueOf(String.valueOf(Collections.min(values)).length() - 2));
                    if (check_Min == 6) {
                        String first1= stringMin.substring(0,1);
                        textviewRent_FLAT_size_from.setText(first1 + " Lakh SQFT");
                    } else if (check_Min == 5) {
                        String first2=stringMin.substring(0,2);
                        textviewRent_FLAT_size_from.setText(first2 + "K SQFT");
                    } else if (check_Min <= 4) {
                        int sizeWithoutZeroMIN=(int) min;
                        textviewRent_FLAT_size_from.setText(sizeWithoutZeroMIN + " SQFT");
                    }

                    float max = Collections.max(values);
                    String stringMax = String.valueOf(max);
                    int check_Max = Integer.parseInt(String.valueOf(String.valueOf(Collections.max(values)).length() - 2));

                    if (check_Max == 6) {
                        String first1= stringMax.substring(0,1);
                        textviewRent_FLAT_size_to.setText(first1 + " Lakh SQFT");
                    } else if (check_Max == 5) {
                        String first2=stringMax.substring(0,2);
                        textviewRent_FLAT_size_to.setText(first2 + " K SQFT");
                    } else if (check_Max <= 4) {
                        int sizeWithoutZeroMAX=(int) max;
                        textviewRent_FLAT_size_to.setText(sizeWithoutZeroMAX + " SQFT");
                    }
                }
            });

            rent_flat_price_RangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    List<Float> values = slider.getValues();
                    double min = Collections.min(values);
                    String stringMin = String.valueOf((int) min);
                    //           String stringlastmin=stringMin.replace("[^0-9]","");

                    //   int check_Min= Integer.parseInt(String.valueOf(stringMin.length()-2));
                    int lengthMin = (int) (Math.log10(min) + 1);

                    if (lengthMin == 7) {
                        String first2Lakh = stringMin.substring(0, 2);
                        textviewRent_FLAT_price_from.setText(first2Lakh + " Lakh");
                    } else if (lengthMin == 6) {
                        String first1Lakh = stringMin.substring(0, 1);
                        String secDigit=stringMin.substring(1,2);
                        textviewRent_FLAT_price_from.setText(first1Lakh+"."+secDigit + " Lakh");
                    } else if (lengthMin < 6) {
                        int sizeWithoutZeroMin=(int) min;
                        textviewRent_FLAT_price_from.setText(sizeWithoutZeroMin + " Rs.");
                    }


                    float max = Collections.max(values);
                    //  BigInteger bigInteger=BigInteger.valueOf(Long.parseLong(String.valueOf(max)));
                    String stringMax = String.valueOf((int) max);
                    int lengthMax = (int) (Math.log10(max) + 1);
                    if (lengthMax == 7) {
                        String first2Lakh = stringMax.substring(0, 2);
                        textviewRent_FLAT_price_to.setText(first2Lakh + " Lakh");
                    } else if (lengthMax == 6) {
                        String first1Lakh = stringMax.substring(0, 1);
                        String secDigit=stringMax.substring(1,2);
                        textviewRent_FLAT_price_to.setText(first1Lakh+"."+secDigit + " Lakh");
                    } else if (lengthMax < 6) {
                        int sizeWithoutZeroMax=(int) max;
                        textviewRent_FLAT_price_to.setText(sizeWithoutZeroMax + " Rs.");
                    }
                }
            });
        }
    }


    private void toSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioSRB = s_r_b_radiogroup.getCheckedRadioButtonId();


                if (!validProType() | !validPropertyLocation_Typing() |
                        radioSRB == -1 | !min_Price_Typing() | !max_Price_Typing()) {
                    if (radioSRB == -1) {
                        iWantTo_error_Text.setVisibility(View.VISIBLE);
                    }

                    customToastNetsoft();
                    return;
                }

                RadioButton sele_rent_radiobutton = (RadioButton) findViewById(radioSRB);
                String salerent_radio_value = sele_rent_radiobutton.getText().toString();
                salerentradio = salerent_radio_value;
                String minValue = autocomplete_price_from.getText().toString();
                String maxValue = autocomplete_price_to.getText().toString();
                Toast.makeText(SearchActivity.this, String.valueOf(ch), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < ch.size(); i++) {
                    loc = ch.get(i);
                }
                SharedPreferences sharedPreferences = getSharedPreferences("myKk", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("propertyType", string_Propertytype);
                editor.putString("propertylocation", loc);
                editor.putString("salerentbuyValue", salerent_radio_value);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), SearchActivity_results.class);
                //  intent.putExtra("propertyType",string_Propertytype);
                //  intent.putExtra("propertylocation",loc);
                //  intent.putExtra("salerentbuyValue",salerent_radio_value);
                startActivity(intent);

            }
        });
    }

    private void textWatcher_Auto() {
        multiAut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(multiAut.getText())) {
                    makeAPICALL(multiAut.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        handler=new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                if (msg.what==Trigger_AUTO_COMPLETE){
//
//                }
//                return false;
//            }
//        });
    }

    private void multiChip() {

        multiAut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = multiAut.getText().toString();

                chips = new Chip(SearchActivity.this);
                ChipDrawable drawable = ChipDrawable.createFromAttributes(SearchActivity.this, null,
                        0, R.style.Widget_MaterialComponents_Chip_Entry);
                chips.setCheckable(false);
                chips.setClickable(false);
                chips.setText(str);
                ChipDrawable chipDrawable = (ChipDrawable) chips.getChipDrawable();
                chipDrawable.setChipBackgroundColorResource(R.color.black);
                chips.setCloseIconVisible(true);
                chips.setCloseIconTint(ColorStateList.valueOf(Color.WHITE));
                chips.setTextColor(getColor(android.R.color.white));
                chips.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Chip chip = (Chip) v;
                        chipgroup.removeView(chip);
                        ch.remove(chip.getText().toString());
                        chipCheck_Count();
                    }
                });
                chipgroup.addView(chips);
                multiAut.setText("");
                chipCheck_Count();

                Log.e("chips String", String.valueOf(ch));

            }
        });
    }

    public void chipCheck_Count() {
        int counts = chipgroup.getChildCount();
        if (counts < 5) {
            multiAut.setVisibility(View.VISIBLE);
        } else {
            multiAut.setVisibility(View.GONE);
        }
    }

    private void makeAPICALL(String texts) {
        ApiInterface_Location apiNames = Retrofit_Client_Location.getRetrofit().create(ApiInterface_Location.class);
        Call<List<String>> request = apiNames.getNames(texts);
        request.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body() != null) {
                    listOfLOcations = response.body();
                    Log.d("onResponse: ", response.body().toString());
                    autoSuggestAdapter.setData(listOfLOcations);
                    autoSuggestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });

      /*  ApiCall.make(this, texts, response -> {
            List<String> stringList=new ArrayList<>();

            try {
                JSONArray ja=new JSONArray(response);
                Log.e( "Cannot process JSON", String.valueOf(ja));
                stringList.clear();

                if(response!=null) {
                    for (int i = 0; i < ja.length(); i++) {
                        String namePlace = ja.getString(i);
                        //only for checking single valur as string
                        Log.e("single json value" , "placeName ::" + namePlace);
                        stringList.add(namePlace);
                    }
                    Log.e("places", stringList.toString()); //same thing
                    //time to check array list is empty of not
                    if (stringList.isEmpty()) {
                        Log.e("places", "List is empty"); }
                }
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        */
    }

    public void po() {

    }
}