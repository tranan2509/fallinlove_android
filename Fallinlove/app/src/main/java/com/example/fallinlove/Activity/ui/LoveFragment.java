package com.example.fallinlove.Activity.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.Provider.ZodiacProvider;
import com.example.fallinlove.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoveFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View mView;
    private static Context mContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoveFragment newInstance(String param1, String param2) {
        LoveFragment fragment = new LoveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    //Model
    User user;
    List<Person> persons;
    Person male, female;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;

    CountDownTimer timer;
    TextView txtViewYear, txtViewMonth, txtViewWeek, txtViewDay, txtViewHour, txtViewMinute, txtViewSecond;
    TextView txtViewDays, txtViewAgeMale, txtViewAgeFemale, txtViewNameMale, txtViewNameFemale, txtViewZodiacMale, txtViewZodiacFemale;
    ImageView imgViewAvatarMale, imgViewAvatarFemale, imgViewZodiacMale, imgViewZodiacFemale;
    ImageView imgHeart;
    Date dateLoveDate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_love, container, false);
        mContext = mView.getContext();
        getModel(mView);
        getViewFragment(mView);
        setView(mView);

        setTime();
        setAge();

        startTime();

        return mView;
    }

    public void getModel(View view){
        user = (User) SharedPreferenceProvider.getInstance(view.getContext()).get("user");
        persons = PersonDB.getInstance(view.getContext()).gets();
        male = persons.get(0);
        female = persons.get(1);
        imageSetting = ImageSettingDB.getInstance(view.getContext()).get(user);
        displaySetting = DisplaySettingDB.getInstance(view.getContext()).get(user);
    }

    private void getViewFragment(View view){
//      Get date
        txtViewDays = view.findViewById(R.id.txtViewDays);
        txtViewYear = view.findViewById(R.id.txtViewYear);
        txtViewMonth = view.findViewById(R.id.txtViewMonth);
        txtViewWeek = view.findViewById(R.id.txtViewWeek);
        txtViewDay = view.findViewById(R.id.txtViewDay);
        txtViewHour = view.findViewById(R.id.txtViewHour);
        txtViewMinute = view.findViewById(R.id.txtViewMinute);
        txtViewSecond = view.findViewById(R.id.txtViewSecond);
        txtViewAgeMale = view.findViewById(R.id.txtViewAgeMale);
        txtViewAgeFemale = view.findViewById(R.id.txtViewAgeFemale);
        txtViewNameMale = view.findViewById(R.id.txtViewNameMale);
        txtViewNameFemale = view.findViewById(R.id.txtViewNameFemale);
        txtViewZodiacMale = view.findViewById(R.id.txtViewZodiacMale);
        txtViewZodiacFemale = view.findViewById(R.id.txtViewZodiacFemale);
        imgViewAvatarMale = view.findViewById(R.id.imgAvatarMale);
        imgViewAvatarFemale = view.findViewById(R.id.imgAvatarFemale);
        imgViewZodiacMale = view.findViewById(R.id.imgViewZodiacMale);
        imgViewZodiacFemale = view.findViewById(R.id.imgViewZodiacFemale);

        //Get background
        imgHeart = view.findViewById(R.id.imgHeart);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setView(View view){
        txtViewDays.setText(String.valueOf(datesLove()));

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

        //Set image setting
        imgHeart.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getHeart()));
    }

    void setOnClick(){

    }

    public void onClick(View view){

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