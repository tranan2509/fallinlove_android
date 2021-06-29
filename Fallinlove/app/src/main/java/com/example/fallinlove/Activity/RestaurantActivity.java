package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.fallinlove.Adapter.RestaurantRecyclerViewAdapter;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.RestaurantDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Restaurant;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;
    public static List<Restaurant> restaurants;
    Restaurant restaurantRandom;
    List<Restaurant> restaurantsFilter;

    RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;

    RecyclerView recyclerViewRestaurant;
    ChipNavigationBar chipNavigationBar;
    Intent intentBottom, intentNext;

    FloatingActionButton btnAdd;
    ImageView imgBgHome;

    Button btnStart;
    EditText txtName;
    CheckBox ckbNew, ckbInTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        getModel();
        getView();
        setView();
        setOnClick();
    }

    @Override
    public void onBackPressed() {

    }

    public void getModel(){
        user = (User) SharedPreferenceProvider.getInstance(this).get("user");
        imageSetting = ImageSettingDB.getInstance(this).get(user);
        displaySetting = DisplaySettingDB.getInstance(this).get(user);
        restaurants = RestaurantDB.getInstance(this).gets(user);
    }


    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        recyclerViewRestaurant = findViewById(R.id.recyclerViewRestaurant);
        imgBgHome = findViewById(R.id.imgBgHome);
        btnAdd = findViewById(R.id.btnAdd);
        txtName = findViewById(R.id.txtName);
        btnStart = findViewById(R.id.btnStart);
        ckbInTime = findViewById(R.id.ckbInTime);
        ckbNew = findViewById(R.id.ckbNew);
    }

    public void setView(){
        RestaurantRecyclerViewAdapter.restaurantRandom = null;
        chipNavigationBar.setItemSelected(R.id.restaurant, true);
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));
        loadRecycleView(restaurants);
    }

    public void setOnClick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
        btnAdd.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAdd:
                intentNext = new Intent(getApplicationContext(), FunctionRestaurantActivity.class);
                intentNext.putExtra("function", "add");
                startActivity(intentNext);
                break;
            case R.id.btnStart:
                start();
                break;
        }
    }

    private void start() {

        if (restaurants == null || restaurants.size() == 0)
            return;
        restaurantsFilter = restaurants;
        restaurantRandom = new Restaurant();
        boolean isNew = ckbNew.isChecked();
        boolean isInTime = ckbInTime.isChecked();
        if (isInTime){
            restaurantsFilter = RestaurantDB.getInstance(RestaurantActivity.this).getsInTime(user);
        }
        if (isNew) {
            for (Restaurant restaurant : restaurantsFilter) {
                if (restaurant.getCount() > 0)
                    restaurantsFilter.remove(restaurant);
            }
        }
        Random random = new Random();
        if (restaurantsFilter.size() > 0) {
            new CountDownTimer(6000, 100) {
                public void onTick(long millisUntilFinished) {

                    int position = random.nextInt(restaurantsFilter.size());
                    restaurantRandom = restaurantsFilter.get(position);
                    txtName.setText(restaurantRandom.getName());
                    RestaurantRecyclerViewAdapter.restaurantRandom = restaurantRandom;
//                loadRecycleView(restaurants);
                    updateStart(restaurantRandom);
                }

                public void onFinish() {

                }

            }.start();
        }else{
            Toast.makeText(getApplicationContext(), "Không có cửa hàng nào thỏa điều kiện", Toast.LENGTH_LONG).show();
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
                intentBottom = new Intent(getApplicationContext(), AnniversaryActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
            case R.id.restaurant:
                break;
            case R.id.setting:
                intentBottom = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intentBottom);
                overridePendingTransition(0,0);
                break;
        }
    }

    public void loadRecycleView(List<Restaurant> restaurants){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewRestaurant.setLayoutManager(staggeredGridLayoutManager);
        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(restaurants);
        recyclerViewRestaurant.setAdapter(restaurantRecyclerViewAdapter);
        recyclerViewRestaurant.setItemAnimator(new SlideInUpAnimator());
    }

    public void updateStart(Restaurant restaurant){
        int indexRandom = -1;
        int indexColor = -1;
        for (int i = 0; i < restaurants.size(); i++){
            if (restaurants.get(i).isState() == true){
                indexColor = i;
                restaurants.get(indexColor).setState(false);
            }
            if (restaurants.get(i).getId() == restaurant.getId()){
                indexRandom = i;
            }
            if (indexColor > -1 && indexRandom > -1){
                break;
            }
        }
        restaurant.setState(true);
        if (indexRandom > -1) {
            restaurants.set(indexRandom, restaurant);
            restaurantRecyclerViewAdapter.notifyItemChanged(indexRandom);
        }
        if (indexColor > -1) {
            restaurants.set(indexColor, restaurants.get(indexColor));
            restaurantRecyclerViewAdapter.notifyItemChanged(indexColor);
        }
    }
}