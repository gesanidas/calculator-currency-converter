package com.example.gesanidas.calculator;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gesanidas.calculator.models.Rate;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    TextView textView,textView2;
    Button button1,button2,buttonAdd,buttonEquals;
    FloatingActionButton fab;
    boolean val1;


    public static final String ADDITION="+";
    public static final String SUBTRACTION="-";
    public static final String MULTIPLICATION="*";
    public static final String DIVISION="/";

    public static String ACTION;






    String value1,value2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        fab=(FloatingActionButton)findViewById(R.id.sendButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CurrencyActivity.class);
                startActivity(intent);
            }
        });

        value1="0";
        value2="0";
        val1=true;





    }


    @Override
    protected void onStart()
    {
        super.onStart();
        deleteAll();

    }

    public void onResume()
    {
        super.onResume();
        deleteAll();

    }





    public void setNumber(View view)
    {
        if(val1)
        {
            value1=deleteFirstZero(value1+((Button)view).getText());
            textView.setText(value1);
        }
        else
        {
            value2=deleteFirstZero(value2+((Button)view).getText());
            textView.setText(value1+ACTION+value2);
        }


    }

    public void choose(View view)
    {
        ACTION=((Button)view).getText().toString();
        textView.setText(value1+ACTION);
        val1=false;
    }

    public void calc(View view)
    {
        double res=0;
        switch (ACTION)
        {
            case ADDITION: res= (double) Double.parseDouble(value1)+Double.parseDouble(value2);
                textView2.setText(String.valueOf(res));
                break;

            case SUBTRACTION:res= Double.parseDouble(value1)-Double.parseDouble(value2);
                textView2.setText(String.valueOf(res));
                break;

            case MULTIPLICATION:res= Double.parseDouble(value1)*Double.parseDouble(value2);
                textView2.setText(String.valueOf(res));
                break;

            case DIVISION:
                if(Integer.parseInt(value2)!=0)
                {
                    res=(double) Double.parseDouble(value1)/Double.parseDouble(value2);
                    textView2.setText(String.valueOf(res));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Division by zero",Toast.LENGTH_LONG).show();
                }
                break;
            default: Toast.makeText(MainActivity.this,"WTF",Toast.LENGTH_LONG).show();
                break;
        }
        value1=String.valueOf(res);
        value2="0";
    }

    public void reset(View view)
    {
        deleteAll();
        textView.setText(" ");
        textView2.setText(" ");
    }

    public void deleteAll()
    {
        //textView.setText(" ");
        //textView2.setText(" ");
        value1="0";
        value2="0";
        val1=true;
        ACTION=null;
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



}
