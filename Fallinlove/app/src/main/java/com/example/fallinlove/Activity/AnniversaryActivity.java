package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.AnniversaryRecyclerViewAdapter;
import com.example.fallinlove.DBUtil.AnniversaryDB;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.Model.Anniversary;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class AnniversaryActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;

    ChipNavigationBar chipNavigationBar;
    Intent intentBottom, intentNext;
    RecyclerView recyclerViewAnniversary;

    AnniversaryRecyclerViewAdapter anniversaryRecyclerViewAdapter;
    public static List<Anniversary> anniversaries;
    //Bottom sheet dialog
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    ImageView imgBgHome;

    FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);

        getModel();
        getView();
        setView();
        setOnClick();
    }

    //Exit app
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Nhấn trở về thêm một lần nữa để đóng ứng dụng", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            finishAffinity();
            return;
        }
    }


    public void getModel(){
        user = (User) SharedPreferenceProvider.getInstance(this).get("user");
        imageSetting = ImageSettingDB.getInstance(this).get(user);
        displaySetting = DisplaySettingDB.getInstance(this).get(user);
        anniversaries = AnniversaryDB.getInstance(this).gets(user);
    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        recyclerViewAnniversary = findViewById(R.id.recyclerViewAnniversary);
        btnAdd = findViewById(R.id.btnAdd);
        imgBgHome = findViewById(R.id.imgBgHome);
    }

    public void setView(){

        chipNavigationBar.setItemSelected(R.id.anniversary, true);
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));

        loadRecycleView(anniversaries);
    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
        btnAdd.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAdd:
                intentNext = new Intent(getApplicationContext(), FunctionAnniversaryActivity.class);
                intentNext.putExtra("function", "add");
                startActivity(intentNext);
                break;
        }
    }

    public void onChipNavigationBarSelected(int id){
        switch (id){
            case R.id.home:
                intentBottom = displaySetting.getMain() == 1 ? new Intent(getApplicationContext(), MainActivity.class) : new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.responsibility:
                intentBottom = new Intent(getApplicationContext(), ResponsibilityActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.anniversary:
                break;
            case R.id.restaurant:
                intentBottom = new Intent(getApplicationContext(), RestaurantActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.setting:
                intentBottom = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
        }
    }

    public void loadRecycleView(List<Anniversary> anniversaries){
        if (recyclerViewAnniversary != null) {
            anniversaryRecyclerViewAdapter = new AnniversaryRecyclerViewAdapter(anniversaries);
            recyclerViewAnniversary.setAdapter(anniversaryRecyclerViewAdapter);
            recyclerViewAnniversary.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewAnniversary.setItemAnimator(new SlideInUpAnimator());
        }

    }

}