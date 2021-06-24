package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.AnniversaryRecyclerViewAdapter;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class AnniversaryActivity extends AppCompatActivity implements View.OnClickListener{

    ChipNavigationBar chipNavigationBar;
    Intent intentBottom, intentNext;
    RecyclerView recyclerViewAnniversary;

    AnniversaryRecyclerViewAdapter anniversaryRecyclerViewAdapter;
    public static List<String> anniversaries;
    //Bottom sheet dialog
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;

    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);

        getView();
        setView();
        setOnClick();
    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        recyclerViewAnniversary = findViewById(R.id.recyclerViewAnniversary);
        btnAdd = findViewById(R.id.btnAdd);
    }

    public void setView(){

        chipNavigationBar.setItemSelected(R.id.anniversary, true);
//        chipNavigationBar.showBadge(R.id.anniversary, 2);
//        chipNavigationBar.showBadge(R.id.restaurant);

        loadRecycleView();
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

    public void loadRecycleView(){
        anniversaries = new ArrayList<String>();
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");
        anniversaries.add("1");

        if (recyclerViewAnniversary != null) {
            anniversaryRecyclerViewAdapter = new AnniversaryRecyclerViewAdapter(anniversaries);
            recyclerViewAnniversary.setAdapter(anniversaryRecyclerViewAdapter);
            recyclerViewAnniversary.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewAnniversary.setItemAnimator(new SlideInUpAnimator());
        }

    }

}