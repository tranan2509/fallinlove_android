package com.example.fallinlove.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.RestaurantDB;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Restaurant;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

public class FunctionRestaurantActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;
    Restaurant restaurant;
    ImageSetting imageSetting;

    Intent intent;
    EditText txtName, txtAddress, txtTimeStart, txtTimeEnd;
    ImageButton btnBack, btnSelectTimeStart, btnSelectTimeEnd;
    Button btnSave, btnEdit, btnDelete;
    ImageView imgBgHome;

    //Time
    private int mHourStart, mMinuteStart, mHourEnd, mMinuteEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_restaurant);

        intent = getIntent();

        getModel();
        getView();
        setView();
        setOnClick();
    }

    public void getModel(){
        user = (User) SharedPreferenceProvider.getInstance(this).get("user");
        imageSetting = ImageSettingDB.getInstance(this).get(user);
    }

    public void getView(){
        //image view
        imgBgHome = findViewById(R.id.imgBgHome);

        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtTimeStart = findViewById(R.id.txtTimeStart);
        txtTimeEnd = findViewById(R.id.txtTimeEnd);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelectTimeStart = findViewById(R.id.btnSelectTimeStart);
        btnSelectTimeEnd = findViewById(R.id.btnSelectTimeEnd);
    }

    public void setView() {
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));

        switch (intent.getStringExtra("function")){
            case "add":
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                break;
            default:
                restaurant = (Restaurant) intent.getExtras().getSerializable("restaurant");
                txtName.setText(restaurant.getName());
                txtAddress.setText(restaurant.getAddress());
                txtTimeStart.setText(restaurant.getTimeStart());
                txtTimeEnd.setText(restaurant.getTimeEnd());
                btnSave.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                break;

        }
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSelectTimeStart.setOnClickListener(this);
        btnSelectTimeEnd.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSave: case R.id.btnEdit:
                save();
                break;
            case R.id.btnDelete:
                delete();
                break;
            case R.id.btnSelectTimeStart:
                selectTimeStart();
                break;
            case R.id.btnSelectTimeEnd:
                selectTimeEnd();
                break;
        }
    }

    private void delete() {
        RestaurantDB.getInstance(this).delete(restaurant);
        intent = new Intent(this, RestaurantActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void save() {
        String name = txtName.getText().toString();
        String address = txtAddress.getText().toString();
        String timeStart = txtTimeStart.getText().toString();
        String timeEnd = txtTimeEnd.getText().toString();
        if (intent.getStringExtra("function").equals("add")){
            restaurant = new Restaurant(user.getId(), name, address, timeStart, timeEnd, 0, true);
            RestaurantDB.getInstance(this).add(restaurant);
        }else{
            restaurant.setName(name);
            restaurant.setAddress(address);
            restaurant.setTimeStart(timeStart);
            restaurant.setTimeEnd(timeEnd);
            RestaurantDB.getInstance(this).update(restaurant);
        }
        intent = new Intent(this, RestaurantActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void selectTimeStart(){
        String[] times = txtTimeStart.getText().toString().split(":");
        mHourStart = Integer.parseInt(times[0]);
        mMinuteStart = Integer.parseInt(times[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        txtTimeStart.setText(DateProvider.standardization(hourOfDay, 2) + ":" + DateProvider.standardization(minute, 2));
                    }
                }, mHourStart, mMinuteStart, false);
        timePickerDialog.show();
    }

    public void selectTimeEnd(){
        String[] times = txtTimeEnd.getText().toString().split(":");
        mHourEnd = Integer.parseInt(times[0]);
        mMinuteEnd = Integer.parseInt(times[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        txtTimeEnd.setText(DateProvider.standardization(hourOfDay, 2) + ":" + DateProvider.standardization(minute, 2));
                    }
                }, mHourEnd, mMinuteEnd, false);
        timePickerDialog.show();
    }

}