package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{


    ChipNavigationBar chipNavigationBar;
    Intent intentBottom, intentNext;
    Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getView();
        setOnClick();
        setView();
    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        btnDisplay = findViewById(R.id.btnDisplay);
    }

    public void setView(){
        chipNavigationBar.setItemSelected(R.id.setting, true);
    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });

        btnDisplay.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnDisplay:
                intentNext = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentNext);
                break;
        }
    }

    public void onChipNavigationBarSelected(int id){
        switch (id){
            case R.id.home:
                intentBottom = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;

        }
    }

}