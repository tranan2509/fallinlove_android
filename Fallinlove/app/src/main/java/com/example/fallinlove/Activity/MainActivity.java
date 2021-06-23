package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fallinlove.Activity.ui.ResponsibilityAdapter;
import com.example.fallinlove.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ChipNavigationBar chipNavigationBar;
    Intent intentBottom;
    CountDownTimer timer;
    TextView txtViewYear, txtViewMonth, txtViewWeek, txtViewDay, txtViewHour, txtViewMinute, txtViewSecond;
    TextView txtViewDays, txtViewAgeMale, txtViewAgeFemale, txtViewNameMale, txtViewNameFemale, txtViewZodiacMale, txtViewZodiacFemale;
    ImageView imgViewAvatarMale, imgViewAvatarFemale, imgViewZodiacMale, imgViewZodiacFemale;

    TabLayout tabResponsibility;
    TabItem tabItemDaily, tabItemResponsibility, tabItemAdd;
    ViewPager viewPageResponsibility;

    ResponsibilityAdapter responsibilityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        getView();
        setView();
        setOnclick();
        setTime();
        setAge();

        startTime();
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

        tabResponsibility = findViewById(R.id.tabResponsibility);
        viewPageResponsibility = findViewById(R.id.viewPageResponsibility);
        tabItemDaily = findViewById(R.id.tabItemDaily);
        tabItemResponsibility = findViewById(R.id.tabItemResponsibility);
        tabItemAdd = findViewById(R.id.tabItemAdd);
    }

    public void setView(){
        chipNavigationBar.setItemSelected(R.id.home, true);
        chipNavigationBar.showBadge(R.id.anniversary, 2);
        chipNavigationBar.showBadge(R.id.restaurant);
        txtViewDays.setText(String.valueOf(datesLove()));

        responsibilityAdapter = new ResponsibilityAdapter(getSupportFragmentManager(), tabResponsibility.getTabCount());
        viewPageResponsibility.setAdapter(responsibilityAdapter);
        viewPageResponsibility.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabResponsibility));
    }

    public void onClick(View view){

    }

    public void setOnclick(){
        chipNavigationBar.setOnItemSelectedListener(id -> {
            onChipNavigationBarSelected(id);
        });
        tabResponsibility.addOnTabSelectedListener(onTabSelectedListener());
    }

    public void onChipNavigationBarSelected(int id){
        switch (id){
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

    String dateLove = "09/01/2021";
    String birthdayMale = "25/09/2000";
    String birthdayFemale = "17/07/2001";
    Date dateLoveDate;


    long getDistance(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            dateLoveDate = dateFormat.parse(dateLove);
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date dob = new Date();
        try {
            dob = dateFormat.parse(date);
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
        txtViewAgeMale.setText(String.valueOf(getAge(birthdayMale)));
        txtViewAgeFemale.setText(String.valueOf(getAge(birthdayFemale)));
    }

}