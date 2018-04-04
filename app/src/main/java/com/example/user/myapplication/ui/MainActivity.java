package com.example.user.myapplication.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myapplication.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stockButton;
    private Button cryptoButton;
    Intent i ;
    Intent x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stockButton = (Button)findViewById(R.id.button);
        cryptoButton = (Button) findViewById(R.id.button2);
        stockButton.setOnClickListener(this);
        cryptoButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
            if(v.getId()==R.id.button) {
                i = new Intent(this, TickerActivity.class);
            }
            else if(v.getId() == R.id.button2)
            {
                i = new Intent(this,CryptoTickerActivity.class);
        }
        startActivity(i);
    }
}
