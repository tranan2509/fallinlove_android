package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fallinlove.Activity.ui.DailyFragment;
import com.example.fallinlove.Activity.ui.ResponsibilityAdapter;
import com.example.fallinlove.Activity.ui.ResponsibilityFragment;
import com.example.fallinlove.Adapter.StringSpinnerAdapter;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    FloatingActionButton btnAdd;
    String[] typeResponsibility, level;
    Spinner spnTypeResponsibilities, spnLevels;

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

        btnAdd = findViewById(R.id.btnAdd);
    }

    public void setView(){

        chipNavigationBar.setItemSelected(R.id.responsibility, true);
//        chipNavigationBar.showBadge(R.id.anniversary, 2);
//        chipNavigationBar.showBadge(R.id.restaurant);

        responsibilityAdapter = new ResponsibilityAdapter(getSupportFragmentManager(), tabResponsibility.getTabCount());
        viewPageResponsibility.setAdapter(responsibilityAdapter);
        viewPageResponsibility.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabResponsibility));

        //Hide tab
        tabResponsibility.removeTabAt(2);
    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
        tabResponsibility.addOnTabSelectedListener(onTabSelectedListener());
        btnAdd.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAdd:
                showDialog(view, "add");
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

    public void showDialog(View view, String type){
        typeResponsibility = new String[]{"Hàng ngày", "Trách nhiệm"};
        level = new String[]{"Bình thường", "Quan trọng", "Rất quan trọng"};

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.bottom_sheet_responsibility, (LinearLayout)view.findViewById(R.id.btnSheetContainer));
        bottomSheetView.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyFragment.loadRecycleView();
                ResponsibilityFragment.loadRecycleView();
                bottomSheetDialog.hide();
            }
        });
        spnTypeResponsibilities = bottomSheetView.findViewById(R.id.spnTypeResponsibility);
        spnLevels = bottomSheetView.findViewById(R.id.spnLevel);
        setSpinner(bottomSheetView.getRootView(), typeResponsibility, level);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        switch (type){
            case "add":
                bottomSheetDialog.findViewById(R.id.btnSave).setVisibility(View.VISIBLE);
                bottomSheetDialog.findViewById(R.id.btnEdit).setVisibility(View.GONE);
                bottomSheetDialog.findViewById(R.id.btnDelete).setVisibility(View.GONE);
                break;
            default:
                bottomSheetDialog.findViewById(R.id.btnSave).setVisibility(View.GONE);
                bottomSheetDialog.findViewById(R.id.btnEdit).setVisibility(View.VISIBLE);
                bottomSheetDialog.findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
                break;
        }
    }


    public void setSpinner(View view, String[] typeResponsibilities, String[] levels){
        StringSpinnerAdapter stringSpinnerAdapterType = new StringSpinnerAdapter(view.getContext(), typeResponsibilities);
        spnTypeResponsibilities.setAdapter(stringSpinnerAdapterType);
        spnTypeResponsibilities.setSelection(0);

        StringSpinnerAdapter stringSpinnerAdapterLevel = new StringSpinnerAdapter(view.getContext(), levels);
        spnLevels.setAdapter(stringSpinnerAdapterLevel);
        spnLevels.setSelection(0);
    }

}