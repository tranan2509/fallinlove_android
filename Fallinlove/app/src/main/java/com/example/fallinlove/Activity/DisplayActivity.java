package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    DisplaySetting displaySetting;
    ImageSetting imageSetting;

    Intent intentNext;

    ImageButton btnBack;
    ImageView imgMessage, imgResponsibility, imgNone, imgBorderMessage, imgBorderResponsibility, imgBorderNone;
    ImageView imgDisplay01, imgDisplay02, imgBorderDisplay01, imgBorderDisplay02;

    ConstraintLayout layoutMessage;

    ImageView imgBgHome;

    public static boolean isChangeDisplay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        getModel();
        getView();
        setView();
        setOnClick();
    }

    @Override
    public void onBackPressed() {
        if (isChangeDisplay){
            intentNext = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intentNext);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void getModel(){
        user = (User) SharedPreferenceProvider.getInstance(this).get("user");
        displaySetting = DisplaySettingDB.getInstance(this).get(user);
        imageSetting = ImageSettingDB.getInstance(this).get(user);
    }

    public void getView(){
        btnBack = findViewById(R.id.btnBack);
        imgMessage = findViewById(R.id.imgMessage);
        imgResponsibility = findViewById(R.id.imgResponsibility);
        imgNone = findViewById(R.id.imgNone);
        imgBorderMessage = findViewById(R.id.imgBorderMessage);
        imgBorderResponsibility = findViewById(R.id.imgBorderResponsibility);
        imgBorderNone = findViewById(R.id.imgBorderNone);
        imgBgHome = findViewById(R.id.imgBgHome);
        imgDisplay01 = findViewById(R.id.imgDisplay01);
        imgDisplay02 = findViewById(R.id.imgDisplay02);
        layoutMessage = findViewById(R.id.layoutMessage);
        imgBorderDisplay01 = findViewById(R.id.imgBorderDisplay01);
        imgBorderDisplay02 = findViewById(R.id.imgBorderDisplay02);
    }

    public void setView(){
        isChangeDisplay = false;
        if (displaySetting.getMain() == 1){
            selectDisplay(R.id.imgDisplay01);
        }else{
            selectDisplay(R.id.imgDisplay02);
        }

        if (displaySetting.getHome() == 1){
            selectMessage(R.id.imgMessage);
        }else if (displaySetting.getHome() == 2){
            selectMessage(R.id.imgResponsibility);
        }else{
            selectMessage(R.id.imgNone);
        }
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        imgMessage.setOnClickListener(this);
        imgResponsibility.setOnClickListener(this);
        imgNone.setOnClickListener(this);
        imgDisplay01.setOnClickListener(this);
        imgDisplay02.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                if (isChangeDisplay){
                    intentNext = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intentNext);
                }
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.imgMessage: case R.id.imgResponsibility: case R.id.imgNone:
                selectMessage(view.getId());
                break;
            case R.id.imgDisplay01: case R.id.imgDisplay02:
                isChangeDisplay = true;
                selectDisplay(view.getId());
                break;
        }
    }

    public void selectMessage(int id){
        switch (id){
            case R.id.imgMessage:
                imgBorderMessage.setVisibility(View.VISIBLE);
                imgBorderResponsibility.setVisibility(View.GONE);
                imgBorderNone.setVisibility(View.GONE);
                break;
            case R.id.imgResponsibility:
                imgBorderMessage.setVisibility(View.GONE);
                imgBorderResponsibility.setVisibility(View.VISIBLE);
                imgBorderNone.setVisibility(View.GONE);
                break;
            case R.id.imgNone:
                imgBorderMessage.setVisibility(View.GONE);
                imgBorderResponsibility.setVisibility(View.GONE);
                imgBorderNone.setVisibility(View.VISIBLE);
                break;
        }
        save(id);
    }

    public void selectDisplay(int id){
        switch (id){
            case R.id.imgDisplay01:
                imgBorderDisplay01.setVisibility(View.VISIBLE);
                imgBorderDisplay02.setVisibility(View.GONE);
                layoutMessage.setVisibility(View.VISIBLE);
                break;
            case R.id.imgDisplay02:
                imgBorderDisplay01.setVisibility(View.GONE);
                imgBorderDisplay02.setVisibility(View.VISIBLE);
                layoutMessage.setVisibility(View.GONE);
                break;
        }
        saveDisplay(id);
    }

    private void saveDisplay(int id) {
        switch (id){
            case R.id.imgDisplay01:
                displaySetting.setMain(1);
                break;
            case R.id.imgDisplay02:
                displaySetting.setMain(2);
                break;
        }
        DisplaySettingDB.getInstance(this).update(displaySetting);
    }

    public void save(int id){
        switch (id){
            case R.id.imgMessage:
                displaySetting.setHome(1);
                break;
            case R.id.imgResponsibility:
                displaySetting.setHome(2);
                break;
            case R.id.imgNone:
                displaySetting.setHome(3);
                break;
        }
        DisplaySettingDB.getInstance(this).update(displaySetting);
    }
}