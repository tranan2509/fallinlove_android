package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener{

    ChipNavigationBar chipNavigationBar;
    Intent intentBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        getView();
        setView();
        setOnClick();
    }

    @Override
    public void onBackPressed() {

    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
    }

    public void setView(){

        chipNavigationBar.setItemSelected(R.id.restaurant, true);
//        chipNavigationBar.showBadge(R.id.anniversary, 2);
//        chipNavigationBar.showBadge(R.id.restaurant);

    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
    }

    public void onClick(View view){

    }

    public void onChipNavigationBarSelected(int id){
        switch (id){
            case R.id.home:
                intentBottom = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.responsibility:
                intentBottom = new Intent(getApplicationContext(), ResponsibilityActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.anniversary:
                intentBottom = new Intent(getApplicationContext(), AnniversaryActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.restaurant:
                break;
            case R.id.setting:
                intentBottom = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
        }
    }
}