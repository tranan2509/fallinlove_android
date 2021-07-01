package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.fallinlove.Activity.ui.ResponsibilityAdapter;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.DBUtil.UserDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DataInitialization;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.Provider.ZodiacProvider;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;
    List<Person> persons;
    Person male, female;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;

    //Chip navigation bar
    ChipNavigationBar chipNavigationBar;
    Intent intentBottom, intentNext;

    //Count down time for days
    CountDownTimer timer;

    //Attribute in main
    TextView txtViewYear, txtViewMonth, txtViewWeek, txtViewDay, txtViewHour, txtViewMinute, txtViewSecond;
    TextView txtViewDays, txtViewAgeMale, txtViewAgeFemale, txtViewNameMale, txtViewNameFemale, txtViewZodiacMale, txtViewZodiacFemale;
    TextView txtViewMessage;
    ImageView imgViewAvatarMale, imgViewAvatarFemale, imgViewZodiacMale, imgViewZodiacFemale;
    ConstraintLayout layoutMale, layoutFemale;
    ImageView imgBgHome, imgBgDays, imgHeart;

    //Tab responsibility
    TabLayout tabResponsibility;
    TabItem tabItemDaily, tabItemResponsibility, tabItemAdd;
    ViewPager viewPageResponsibility;

    //Responsibility list
    ResponsibilityAdapter responsibilityAdapter;

    //Dialog message
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;

    //Count date
    Date dateLoveDate;

    //Layout
    LinearLayout layoutResponsibility, layoutMessage;

    //Exit app
    boolean doubleBackToExitPressedOnce = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initApp();
        getModel();
        getView();
        setView();
        setOnclick();
        setTime();
        setAge();

        startTime();
    }

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

    public void initApp(){
        List<User> users = UserDB.getInstance(this).gets();
        if (users == null || users.isEmpty()){
            Calendar cal = Calendar.getInstance();
            String now = DateProvider.dateFormat.format(cal.getTime());
            user = new User("Gửi người yêu,\nGửi lời yêu thương đến người mình yêu để có thêm những cảm xúc bên nhau và gẫn gũi với nhau hơn giữa hai người <3\nIn love, kết nối yêu thương",
                    now, now, true);
            user.setId(1);
            DataInitialization.getInstance(this).insertAllData(user);
        }else{
            user = users.get(0);
        }
        SharedPreferenceProvider.getInstance(this).set("user", user);
    }

    public void getModel(){
//        user.setLovedDate("2021-01-09");
//        UserDB.getInstance(this).update(user);
        persons = PersonDB.getInstance(this).gets();
        male = persons.get(0);
        female = persons.get(1);
        imageSetting = ImageSettingDB.getInstance(this).get(user);
        displaySetting = DisplaySettingDB.getInstance(this).get(user);
    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        txtViewDays = findViewById(R.id.txtViewDays);
        txtViewYear = findViewById(R.id.txtViewYear);
        txtViewMonth = findViewById(R.id.txtViewMonth);
        txtViewWeek = findViewById(R.id.txtViewWeek);
        txtViewDay = findViewById(R.id.txtViewDay);
        txtViewHour = findViewById(R.id.txtViewHour);
        txtViewMinute = findViewById(R.id.txtViewMinute);
        txtViewSecond = findViewById(R.id.txtViewSecond);
        txtViewAgeMale = findViewById(R.id.txtViewAgeMale);
        txtViewAgeFemale = findViewById(R.id.txtViewAgeFemale);
        txtViewNameMale = findViewById(R.id.txtViewNameMale);
        txtViewNameFemale = findViewById(R.id.txtViewNameFemale);
        txtViewZodiacMale = findViewById(R.id.txtViewZodiacMale);
        txtViewZodiacFemale = findViewById(R.id.txtViewZodiacFemale);
        imgViewAvatarMale = findViewById(R.id.imgAvatarMale);
        imgViewAvatarFemale = findViewById(R.id.imgAvatarFemale);
        imgViewZodiacMale = findViewById(R.id.imgViewZodiacMale);
        imgViewZodiacFemale = findViewById(R.id.imgViewZodiacFemale);

        txtViewMessage = findViewById(R.id.txtViewMessage);

        //Get layout message
        layoutResponsibility = findViewById(R.id.layoutResponsibility);
        layoutMessage = findViewById(R.id.layoutMessage);

        //Get background
        imgBgHome = findViewById(R.id.imgBgHome);
        imgBgDays = findViewById(R.id.imgBgDays);
        imgHeart = findViewById(R.id.imgHeart);

        layoutMale = findViewById(R.id.layoutMale);
        layoutFemale = findViewById(R.id.layoutFemale);

        tabResponsibility = findViewById(R.id.tabResponsibility);
        viewPageResponsibility = findViewById(R.id.viewPageResponsibility);
        tabItemDaily = findViewById(R.id.tabItemDaily);
        tabItemResponsibility = findViewById(R.id.tabItemResponsibility);
//        tabItemAdd = findViewById(R.id.tabItemAdd);

    }

    public void setView(){
        chipNavigationBar.setItemSelected(R.id.home, true);
        txtViewDays.setText(String.valueOf(datesLove()));

        //Load information to tab responsibility
        responsibilityAdapter = new ResponsibilityAdapter(getSupportFragmentManager(), tabResponsibility.getTabCount());
        viewPageResponsibility.setAdapter(responsibilityAdapter);
        viewPageResponsibility.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabResponsibility));

        //Set information male
        txtViewNameMale.setText(male.getName());
        imgViewAvatarMale.setImageBitmap(ImageConvert.ArrayByteToBitmap(male.getAvatar()));
        int zodiacMaleId = ZodiacProvider.getResourceId(male.getDob(), true);
        imgViewZodiacMale.setImageResource(zodiacMaleId);
        txtViewZodiacMale.setText(ZodiacProvider.getZodiacName(zodiacMaleId, true));
        //Set information female
        txtViewNameFemale.setText(female.getName());
        imgViewAvatarFemale.setImageBitmap(ImageConvert.ArrayByteToBitmap(female.getAvatar()));
        int zodiacFemaleId = ZodiacProvider.getResourceId(female.getDob(), false);
        imgViewZodiacFemale.setImageResource(zodiacFemaleId);
        txtViewZodiacFemale.setText(ZodiacProvider.getZodiacName(zodiacFemaleId, false));

        //Set message
        txtViewMessage.setText(user.getMessage());

        //Set image setting
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));
        imgBgDays.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getDays()));
        imgHeart.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getHeart()));

        //Setting message
        if (displaySetting.getHome() == 1){
            layoutMessage.setVisibility(View.VISIBLE);
            layoutResponsibility.setVisibility(View.GONE);
        }else if (displaySetting.getHome() == 2){
            layoutMessage.setVisibility(View.GONE);
            layoutResponsibility.setVisibility(View.VISIBLE);
        }else{
            layoutMessage.setVisibility(View.GONE);
            layoutResponsibility.setVisibility(View.GONE);
        }

        int heightMale = imgViewAvatarMale.getHeight();
        int heightFemale = imgViewAvatarFemale.getHeight();
        int height = heightMale > heightFemale ? heightFemale : heightMale;
        imgViewAvatarMale.setMaxHeight(height);
        imgViewAvatarFemale.setMaxHeight(height);
    }

    public void setOnclick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
        tabResponsibility.addOnTabSelectedListener(onTabSelectedListener());
        txtViewMessage.setOnClickListener(this);
        layoutMale.setOnClickListener(this);
        layoutFemale.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.txtViewMessage:
                showDialog(view);
                break;
            case R.id.layoutMale:
                intentNext = new Intent(getApplicationContext(), EditProfileActivity.class);
                intentNext.putExtra("personId", male.getId());
                startActivity(intentNext);
                break;
            case R.id.layoutFemale:
                intentNext = new Intent(getApplicationContext(), EditProfileActivity.class);
                intentNext.putExtra("personId", female.getId());
                startActivity(intentNext);
                break;
        }
    }

    public void onChipNavigationBarSelected(int id){
        switch (id){
            case R.id.home:
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

    public void showDialog(View view){

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.bottom_sheet_message, (LinearLayout)view.findViewById(R.id.btnSheetContainer));
        EditText txtMessage = bottomSheetView.findViewById(R.id.txtMessage);
        txtMessage.setText(user.getMessage());
        bottomSheetView.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save message
                saveMessage(txtMessage.getText().toString());
                bottomSheetDialog.hide();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void saveMessage(String message){
        user.setMessage(message);
        UserDB.getInstance(this).update(user);
        SharedPreferenceProvider.getInstance(this).set("user", user);
        txtViewMessage.setText(message);
    }

    long getDistance(){
        Calendar cal = Calendar.getInstance();
        try {
            dateLoveDate = DateProvider.dateFormat.parse(user.getLovedDate());
            return cal.getTime().getTime() - dateLoveDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    int datesLove() {
        double dateLoves = (double)getDistance()/86400000;
        return dateLoves == (int)dateLoves ? (int)dateLoves : (int)dateLoves + 1;
    }

    void setTime(){
        long timesLove = getDistance()/1000;
        int[] years = getDistanceYear(timesLove / 86400);
        txtViewYear.setText(String.valueOf(years[0]));
        timesLove = years[1] != 0 ? timesLove % (86400 * years[1]) : timesLove;
        int[] months = getDistanceMonth(timesLove / 86400);
        txtViewMonth.setText(String.valueOf(months[0]));
        timesLove = months[1] != 0 ? timesLove % (86400 * months[1]) : timesLove;
        int week = (int)timesLove / (86400 * 7);
        txtViewWeek.setText(String.valueOf(week));
        timesLove %= (86400 * 7);
        int date = (int)timesLove / 86400;
        txtViewDay.setText(String.valueOf(date));
        timesLove %= 86400;
        int hour = (int)timesLove / 3600;
        txtViewHour.setText(String.valueOf(hour));
        timesLove %= 3600;
        int minute = (int)timesLove / 60;
        txtViewMinute.setText(String.valueOf(minute));
        timesLove = timesLove % (60);
        int second = (int)timesLove;
        txtViewSecond.setText(String.valueOf(second));
    }


    int getDaysOfYear(int year) {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) ? 366 : 365;
    }

    int[] getDistanceYear(long days){
        int count = 0;
        int sumDays = 0;
        Calendar cal = Calendar.getInstance();
        for (int i = dateLoveDate.getYear(); i < cal.getTime().getYear(); i++)
        {
            days -= getDaysOfYear(i);
            if (days >= 0)
            {
                sumDays += getDaysOfYear(i);
                count++;
            }
            else
                break;
        }
        return new int[]{count, sumDays};
    }

    int[] getDistanceMonth(long days)
    {
        int count = 0;
        int sumDays = 0;
        Calendar cal = Calendar.getInstance();
        for (int i = dateLoveDate.getMonth() + 1; i <= 12; i++){
            days = days - getMaxDay(i, cal.getTime().getYear());
            if (days >= 0)
            {
                sumDays += getMaxDay(i, cal.getTime().getYear());
                count++;
            }
            else
                break;
        }
        return new int[]{count, sumDays};
    }

    int getMaxDay(int month, int year)
    {
        switch (month){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: return 31;
            case 4: case 6: case 9: case 11: return 30;
            case 2:
            {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
                    return 29;
                return 28;
            }
        }
        return -1;
    }

    void startTime(){
        timer = new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                loadTime();
            }

            @Override
            public void onFinish() {
                startTime();
            }
        }.start();
    }

    void loadTime()
    {
        int year = Integer.parseInt(txtViewYear.getText().toString());
        int month = Integer.parseInt(txtViewMonth.getText().toString());
        int week = Integer.parseInt(txtViewWeek.getText().toString());
        int date = Integer.parseInt(txtViewDay.getText().toString());
        int hour = Integer.parseInt(txtViewHour.getText().toString());
        int minute = Integer.parseInt(txtViewMinute.getText().toString());
        int second = Integer.parseInt(txtViewSecond.getText().toString());

        if (second < 59)
            second += 1;
        else{
            second = 1;
            if (minute < 59)
                minute += 1;
            else{
                minute = 1;
                if (hour < 23)
                    hour += 1;
                else{
                    hour = 0;
                    if (date < getMaxDay(month, year)){
                        date += 1;
                    }else
                    {
                        date = 1;
                        if (week < 4)
                            week += 1;
                        else{
                            week = 1;
                            if (month < 12)
                                month += 1;
                            else{
                                month = 1;
                                year += 1;
                            }
                        }
                    }
                }
            }
        }

        txtViewYear.setText(String.valueOf(year));
        txtViewMonth.setText(String.valueOf(month));
        txtViewWeek.setText(String.valueOf(week));
        txtViewDay.setText(String.valueOf(date));
        txtViewHour.setText(String.valueOf(hour));
        txtViewMinute.setText(String.valueOf(minute));
        txtViewSecond.setText(String.valueOf(second));
    }

    int getAge(String date)
    {
        Calendar cal = Calendar.getInstance();
        Date dob = new Date();
        try {
            dob = DateProvider.dateFormat.parse(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        if (cal.getTime().getMonth() < dob.getMonth()) {
            return cal.getTime().getYear() - dob.getYear();
        }else if (cal.getTime().getMonth() == dob.getMonth()){
            if (cal.getTime().getDate() >= dob.getDate()){
                return cal.getTime().getYear() - dob.getYear() + 1;
            }else{
                return cal.getTime().getYear() - dob.getYear();
            }
        }else{
            return cal.getTime().getYear() - dob.getYear() + 1;
        }
    }

    void setAge()
    {
        txtViewAgeMale.setText(String.valueOf(getAge(persons.get(0).getDob())));
        txtViewAgeFemale.setText(String.valueOf(getAge(persons.get(1).getDob())));
    }

}