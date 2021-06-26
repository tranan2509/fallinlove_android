package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.UserDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 250;
    User user;
    DisplaySetting displaySetting;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user = UserDB.getInstance(this).get(1);
        if (user != null)
            displaySetting = DisplaySettingDB.getInstance(this).get(user);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null && displaySetting.getMain() == 2){
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, SPLASH_TIME_OUT);
    }
}