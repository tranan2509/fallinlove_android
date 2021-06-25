package com.example.fallinlove.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.DBUtil.UserDB;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;

    //Element of view
    ChipNavigationBar chipNavigationBar;
    Intent intentBottom, intentNext;
    Button btnDisplay, btnImage, btnEdit, btnSelectDate;

    //Dialog message
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;

    //Date
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        user = (User)SharedPreferenceProvider.getInstance(this).get("user");

        getView();
        setOnClick();
        setView();
    }

    @Override
    public void onBackPressed() {

    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        btnDisplay = findViewById(R.id.btnDisplay);
        btnImage = findViewById(R.id.btnImage);
        btnEdit = findViewById(R.id.btnEdit);
        btnSelectDate = findViewById(R.id.btnSelectDate);
    }

    public void setView(){
        chipNavigationBar.setItemSelected(R.id.setting, true);
    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });

        btnDisplay.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSelectDate.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSelectDate:
                showDialog(view);
                break;
            case R.id.btnEdit:
                intentNext = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentNext);
                break;
            case R.id.btnImage:
                intentNext = new Intent(getApplicationContext(), BackgroundActivity.class);
                startActivity(intentNext);
                break;
            case R.id.btnDisplay:
                intentNext = new Intent(getApplicationContext(), DisplayActivity.class);
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
                break;

        }
    }

    public void showDialog(View view){

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.bottom_sheet_loved_date, (LinearLayout)view.findViewById(R.id.btnSheetContainer));
        EditText txtDate = bottomSheetView.findViewById(R.id.txtDate);
        txtDate.setText(DateProvider.convertDateSqliteToPerson(user.getLovedDate()));
        bottomSheetView.findViewById(R.id.btnSelectDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save message
                selectDate(bottomSheetView);
                bottomSheetDialog.hide();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void saveLovedDate(String lovedDate){
        user.setLovedDate(lovedDate);
        UserDB.getInstance(this).update(user);
        SharedPreferenceProvider.getInstance(this).set("user", user);
    }

    public void selectDate(View view){
//        final Calendar c = Calendar.getInstance();
        EditText txtDate = view.findViewById(R.id.txtDate);
        String[] units = txtDate.getText().toString().split("/");
        mYear = Integer.parseInt(units[2]);
        mMonth = Integer.parseInt(units[1]);
        mDay = Integer.parseInt(units[0]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txtDate.setText(DateProvider.standardization(dayOfMonth, 2) + "/" + DateProvider.standardization(monthOfYear + 1, 2) + "/" + year);
                        saveLovedDate(DateProvider.convertDatePersonToSqlite(txtDate.getText().toString()));
                    }
                }, mYear, mMonth - 1, mDay);
        datePickerDialog.show();
    }

}