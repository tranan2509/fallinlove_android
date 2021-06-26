package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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

    ImageView imgBgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        getModel();
        getView();
        setView();
        setOnClick();
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
    }

    public void setView(){
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
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.imgMessage: case R.id.imgResponsibility: case R.id.imgNone:
                selectMessage(view.getId());
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