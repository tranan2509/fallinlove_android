package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.R;

public class BackgroundActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intentNext;

    Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        getView();
        setView();
    }

    public void getView(){
        btnDisplay = findViewById(R.id.btnDisplay);
    }

    public void setView(){

    }

    public void setOnClick(){
        btnDisplay.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){

        }
    }
}