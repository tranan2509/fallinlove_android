package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fallinlove.Activity.ui.ResponsibilityAdapter;
import com.example.fallinlove.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ResponsibilityActivity extends AppCompatActivity implements View.OnClickListener{


    ChipNavigationBar chipNavigationBar;
    Intent intentBottom;

    TabLayout tabResponsibility;
    TabItem tabItemDaily, tabItemResponsibility, tabItemAdd;
    ViewPager viewPageResponsibility;

    ResponsibilityAdapter responsibilityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsibility);

        getView();
        setView();
        setOnClick();
    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);

        tabResponsibility = findViewById(R.id.tabResponsibility);
        viewPageResponsibility = findViewById(R.id.viewPageResponsibility);
        tabItemDaily = findViewById(R.id.tabItemDaily);
        tabItemResponsibility = findViewById(R.id.tabItemResponsibility);
        tabItemAdd = findViewById(R.id.tabItemAdd);
    }

    public void setView(){

        chipNavigationBar.setItemSelected(R.id.responsibility, true);
//        chipNavigationBar.showBadge(R.id.anniversary, 2);
//        chipNavigationBar.showBadge(R.id.restaurant);

        responsibilityAdapter = new ResponsibilityAdapter(getSupportFragmentManager(), tabResponsibility.getTabCount());
        viewPageResponsibility.setAdapter(responsibilityAdapter);
        viewPageResponsibility.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabResponsibility));
    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
        tabResponsibility.addOnTabSelectedListener(onTabSelectedListener());
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
                break;
            case R.id.anniversary:
                intentBottom = new Intent(getApplicationContext(), AnniversaryActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
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

    public TabLayout.OnTabSelectedListener onTabSelectedListener(){
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPageResponsibility.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2){
                    responsibilityAdapter.notifyDataSetChanged();
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