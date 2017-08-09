package com.example.gesanidas.calculator;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gesanidas.calculator.models.Rate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyActivity extends AppCompatActivity
{
    String input;
    TextView textView,textView2;
    Spinner spinner1,spinner2;
    Rate rate;
    boolean calcDone;
    FloatingActionButton fab;


    String[] rateNames={
            "AUD",
            "BGN",
            "BRL",
            "CAD",
            "CHF",
            "CNY",
            "CZK",
            "DKK",
            "GBP",
            "HKD",
            "HRK",
            "HUF",
            "IDR",
            "ILS",
            "INR",
            "JPY",
            "KRW",
            "MXN",
            "MYR",
            "NOK",
            "NZD",
            "PHP",
            "PLN",
            "RON",
            "RUB",
            "SEK",
            "SGD",
            "THB",
            "TRY",
            "USD",
            "ZAR",
            "EUR"};



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        input="0";
        calcDone=false;
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CurrencyActivity.this, android.R.layout.simple_spinner_dropdown_item, rateNames);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        fab=(FloatingActionButton)findViewById(R.id.sendButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CurrencyActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("input",textView.getText().toString());
        outState.putString("res",textView2.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("input"));
        textView2.setText(savedInstanceState.getString("r"));

    }

    public void getResults(final String base) //work here
    {
        rate=null;
        String BASE_URL = "http://api.fixer.io/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        Fixer fixer=retrofit.create(Fixer.class);

        Call<Rate> call = fixer.getRate(base);




        call.enqueue(new Callback<Rate>()
        {
            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response)
            {
                int statusCode = response.code();
                rate = response.body();
                //Toast.makeText(MainActivity.this,rate.getRates().toString(),Toast.LENGTH_LONG).show();
                HashMap<String,Double> hash=rate.getRates();
                hash.put(base,1.0);


                input=textView.getText().toString();
                double r=rate.getRates().get(spinner2.getSelectedItem().toString());
                double res=Double.parseDouble(input)*r;
                textView2.setText(String.format("%.2f",res));










            }

            @Override
            public void onFailure(Call<Rate> call, Throwable t)
            {
                // Log error here since request failed
            }
        });

    }








    public void setNumber(View view)
    {

        if(calcDone)
        {
            input="0";
        }
        calcDone=false;

        input=deleteFirstZero(input+((Button)view).getText());
        textView.setText(input);



    }

    public void calc(View view)
    {
        String base = spinner1.getSelectedItem().toString();
        getResults(base);
        calcDone=true;


        //work here
    }


    public String deleteFirstZero(String input)
    {

        if(input!= null && input.trim().length() > 0)
        {
            if(input.startsWith("0"))
            {
                return input.substring(1);
            }
        }
        return input;
    }


    public void reset(View view)
    {
        input="0";
        textView.setText(input);
        textView2.setText(input);
    }

}
