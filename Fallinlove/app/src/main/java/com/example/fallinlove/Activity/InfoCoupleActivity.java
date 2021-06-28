package com.example.fallinlove.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fallinlove.Activity.ui.PersonDetailPageAdapter;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.DBUtil.UserDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class InfoCoupleActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;
    List<Person> persons;

    ImageButton btnBack;

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabItemMale, tabItemFemale;
    ImageView imgBgHome;

    PersonDetailPageAdapter personDetailPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_couple);

        getModel();
        getView();
        setView();
        setOnClick();

        //Event Listener tabLayout
        tabLayout.addOnTabSelectedListener(onTabSelectedListener());

        personDetailPageAdapter = new PersonDetailPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), persons);
        viewPager.setAdapter(personDetailPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public void getModel(){
        user = UserDB.getInstance(this).get(1);
        imageSetting = ImageSettingDB.getInstance(this).get(user);
        displaySetting = DisplaySettingDB.getInstance(this).get(user);
        persons = PersonDB.getInstance(this).gets(user);
        SharedPreferenceProvider.getInstance(this).set("user", user);
    }

    void getView(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPage);
        imgBgHome = findViewById(R.id.imgBgHome);
        btnBack = findViewById(R.id.btnBack);
    }

    void setView(){
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));
    }

    void setOnClick(){
        btnBack.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }

    public TabLayout.OnTabSelectedListener onTabSelectedListener(){
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1){
                    personDetailPageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

}