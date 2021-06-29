package com.example.fallinlove.Activity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fallinlove.DBUtil.AccountDB;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.UserDB;
import com.example.fallinlove.Model.Account;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private int INTENT_AUTHENTICATE = 123;

    private static int SPLASH_TIME_OUT = 250;
    User user;
    DisplaySetting displaySetting;
    Account account;
    Intent intent ;
    ImageButton btnLock;
    CardView cardViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user = UserDB.getInstance(this).get(1);
        if (user != null) {
            displaySetting = DisplaySettingDB.getInstance(this).get(user);
            account = AccountDB.getInstance(this).get(user);
        }

        getView();
        setView();
        setOnClick();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (account == null || !account.isState()){
                    if (user != null && displaySetting.getMain() == 2){
                        intent = new Intent(SplashActivity.this, HomeActivity.class);
                    }
                    else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                   show();
                }


            }
        }, SPLASH_TIME_OUT);
    }

    public void getView(){
        btnLock = findViewById(R.id.btnLock);
        cardViewLogo = findViewById(R.id.cardViewLogo);
    }

    public void setView(){
        if (account == null || !account.isState()){
            cardViewLogo.setVisibility(View.GONE);
        }
        else {
            cardViewLogo.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClick(){
        btnLock.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLock:
                show();
                break;
        }
    }


    public void show(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent("Xác nhận mật khẩu", "Mật khẩu điện thoại của bạn");
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
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
        }
    }
}