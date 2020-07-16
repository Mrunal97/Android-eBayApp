package com.example.ebaycatalogsearch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Float.parseFloat;

public class MainActivity extends AppCompatActivity {
    //TextInputLayout textInputKeyword;
    EditText keywordEdit, maxPriceField, minPriceField;
    Button search, clear;
    TextView keyAlert;
    TextView priceAlert;
    CheckBox conditionNew, conditionUsed, conditionUnspecified;
    Spinner spinner1;
    //final String hardcodedurl="http://10.0.2.2:8080/ebay?keyword=iphone&priceFrom=500&priceTo=2000";
    Toolbar toolbar;
    Boolean flag1, flag2;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.myAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ebay Catalog Search");

        //textInputKeyword = findViewById(R.id.text_input_keyword);
        keywordEdit=findViewById(R.id.keywordField);
        maxPriceField=findViewById(R.id.maxPriceField);
        minPriceField=findViewById(R.id.minPriceField);
        search = findViewById(R.id.search);
        clear = findViewById(R.id.clear);
        keyAlert = findViewById(R.id.keyAlert);
        priceAlert=findViewById(R.id.priceAlert);
        conditionNew=findViewById(R.id.conditionNew);
        conditionUsed=findViewById(R.id.conditionUsed);
        conditionUnspecified=findViewById(R.id.conditionUnspecified);
        spinner1=findViewById(R.id.spinner1);
        search.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View view) {
                flag1 = validateKeyword();
                //flag2 = validatePrice();
                if(flag1)
                {displayResults();}
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywordEdit.setText("");
                maxPriceField.setText("");
                minPriceField.setText("");
                conditionNew.setChecked(false);
                conditionUsed.setChecked(false);
                conditionUnspecified.setChecked(false);
                spinner1.setSelection(0);
                keyAlert.setVisibility(View.INVISIBLE);
                priceAlert.setVisibility(View.INVISIBLE);
            }
        });

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void displayResults() {
        // Request a string response from the provided URL.
        Log.d("check", String.valueOf(check));
        String key = keywordEdit.getText().toString();
        Log.d("key",key);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .encodedAuthority("webtechassign9.uk.r.appspot.com")
                .appendEncodedPath("ebay")
                .appendQueryParameter("keyword", key);

//        Float priceMin=null;
//        Float priceMax=null;

        if(check==1)
        {
            Float priceMin = parseFloat(minPriceField.getText().toString());
            Float priceMax = parseFloat(maxPriceField.getText().toString());
            builder.appendQueryParameter("priceFrom", String.valueOf(priceMin));
            builder.appendQueryParameter("priceTo", String.valueOf(priceMax));
        }
        if(check==2)
        {
            Float priceMin = parseFloat(minPriceField.getText().toString());
            builder.appendQueryParameter("priceFrom", String.valueOf(priceMin));
        }
        if(check==3)
        {
            Float priceMax = parseFloat(maxPriceField.getText().toString());
            builder.appendQueryParameter("priceTo", String.valueOf(priceMax));
        }
//        try {
//            priceMin = parseFloat(minPriceField.getText().toString());
//            priceMax = parseFloat(maxPriceField.getText().toString());
//        }
//        catch(NumberFormatException e) {
//
//        }


//
//        if( minPriceField != null ) {
//            Float p
//            priceMin =  parseFloat(minPriceField.getText().toString());
//          //  params.put("priceFrom", priceMin.toString());
//            builder.appendQueryParameter("priceFrom", String.valueOf(priceMin));
//            Log.d("insidemin",priceMin.toString());
//        }
//        if(maxPriceField != null){
//            Float priceMax =  parseFloat(maxPriceField.getText().toString());
//            builder.appendQueryParameter("priceTo", String.valueOf(priceMax));
//            Log.d("insidemax",priceMax.toString());
//        }

        if (conditionNew.isChecked()){
            builder.appendQueryParameter("condition","1000");
        }
        if (conditionUsed.isChecked()){
            builder.appendQueryParameter("condition","3000");
        }
        String spinnerText=spinner1.getSelectedItem().toString();
        if(spinnerText.equals("Best Match")){
            spinnerText="BestMatch";
        }
        else if(spinnerText.equals("Price: highest first"))
        {
            spinnerText="CurrentPriceHighest";
        }
        else if(spinnerText.equals("Price + Shipping:Highest First")){
            spinnerText="PricePlusShippingHighest";
        }
        else
        {
            spinnerText="PricePlusShippingLowest";
        }
        builder.appendQueryParameter("sortBy",spinnerText);

        String dynamicUrl = builder.build().toString();
        Log.d("url",dynamicUrl);
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        intent.putExtra("dynamicUrl",dynamicUrl);
        intent.putExtra("keyword",key);
        startActivity(intent);

    }

    @Nullable
    private boolean validateKeyword()
    {
        //String key = textInputKeyword.getEditText().getText().toString();
        Float priceMin=null;
        Float priceMax=null;
        String key = keywordEdit.getText().toString();
        try {
            priceMin = parseFloat(minPriceField.getText().toString());
            priceMax = parseFloat(maxPriceField.getText().toString());
        }
        catch(NumberFormatException e) {

        }
        //Log.d("validatePrice", String.valueOf(priceMin));


        if(key.isEmpty()){
            //keywordedit.setError("Please enter mandatory field");
            keyAlert.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Please fix all fields with errors",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else{

            keywordEdit.setError(null);
            if(priceMax!=null && priceMin!=null) {

                if (priceMin > priceMax) {
                    priceAlert.setVisibility(View.VISIBLE);
                    Toast.makeText(this,"Please fix all fields with errors",
                            Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    check=1;
                    maxPriceField.setError(null);
                    minPriceField.setError(null);

                    return true;
                }
            }
            else if(priceMin!=null && priceMax==null)
            {
                minPriceField.setError(null);
                check=2;
                // builder.appendQueryParameter("priceFrom", String.valueOf(priceMin));
                return true;
            }
            else if(priceMin==null && priceMax!=null) {
                check=3;
                maxPriceField.setError(null);
                return true;
            }
            else
            {
                check=4;
                maxPriceField.setError(null);
                minPriceField.setError(null);
                return true;
            }

        }
    }

//    private boolean validatePrice(){
//        Float priceMin =  parseFloat(minPriceField.getText().toString());
//        Log.d("validatePrice", String.valueOf(priceMin));
//        Float priceMax = parseFloat(maxPriceField.getText().toString());
//
//
//        if (priceMin > priceMax) {
//            priceAlert.setVisibility(View.VISIBLE);
//            Toast.makeText(this,"Please fix all fields with errors",
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            maxPriceField.setError(null);
//            minPriceField.setError(null);
//            return true;
//        }
//
//
//    }

}