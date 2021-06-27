package com.example.fallinlove.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fallinlove.Activity.ui.DailyFragment;
import com.example.fallinlove.Activity.ui.ResponsibilityAdapter;
import com.example.fallinlove.Activity.ui.ResponsibilityFragment;
import com.example.fallinlove.Adapter.StringSpinnerAdapter;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.ResponsibilityDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Responsibility;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Calendar;

public class ResponsibilityActivity extends AppCompatActivity implements View.OnClickListener{

    //Model
    User user;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;

    //Navigation bar
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
    ImageView imgBgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsibility);

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

        ResponsibilityDB.getInstance(this).updateAllDaily(user);
    }

    public void getView(){
        chipNavigationBar = findViewById(R.id.chipNavigationBar);

        tabResponsibility = findViewById(R.id.tabResponsibility);
        viewPageResponsibility = findViewById(R.id.viewPageResponsibility);
        tabItemDaily = findViewById(R.id.tabItemDaily);
        tabItemResponsibility = findViewById(R.id.tabItemResponsibility);
        tabItemAdd = findViewById(R.id.tabItemAdd);

        btnAdd = findViewById(R.id.btnAdd);

        imgBgHome = findViewById(R.id.imgBgHome);
    }

    public void setView(){

        chipNavigationBar.setItemSelected(R.id.responsibility, true);

        responsibilityAdapter = new ResponsibilityAdapter(getSupportFragmentManager(), tabResponsibility.getTabCount());
        viewPageResponsibility.setAdapter(responsibilityAdapter);
        viewPageResponsibility.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabResponsibility));

        //Hide tab
        tabResponsibility.removeTabAt(2);

        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));
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
                intentBottom = displaySetting.getMain() == 1 ? new Intent(getApplicationContext(), MainActivity.class) : new Intent(getApplicationContext(), HomeActivity.class);
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
        typeResponsibility = new String[]{"Hàng ngày", "Nhiệm vụ"};
        level = new String[]{"Bình thường", "Quan trọng", "Rất quan trọng"};

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.bottom_sheet_responsibility, (LinearLayout)view.findViewById(R.id.btnSheetContainer));

        EditText txtName = bottomSheetView.findViewById(R.id.txtName);
        EditText txtDate = bottomSheetView.findViewById(R.id.txtDate);
        Spinner spnType = bottomSheetView.findViewById(R.id.spnType);
        Spinner spnLevel = bottomSheetView.findViewById(R.id.spnLevel);

        if (type == "add"){
            final Calendar c = Calendar.getInstance();
            String date = DateProvider.datetimeFormat.format(c.getTime());
            txtDate.setText(DateProvider.convertDateTimeSqliteToPerson(date));
        }

        bottomSheetView.findViewById(R.id.btnSelectDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(bottomSheetView);
            }
        });
        bottomSheetView.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txtName.getText().toString();
                String date = DateProvider.convertDateTimePersonToSqlite(txtDate.getText().toString());
                int type = (int)spnType.getSelectedItemPosition() + 1;
                int level = (int)spnLevel.getSelectedItemPosition() + 1;
                if (type == 1){
                    Calendar cal = Calendar.getInstance();
                    String now = DateProvider.datetimeFormat.format(cal.getTime());
                    date = now.split(" ")[0] + " " + date.split(" ")[1];
                }

                Responsibility responsibility = new Responsibility(user.getId(), name, date, type, level, false);
                ResponsibilityDB.getInstance(v.getContext()).add(responsibility);

                DailyFragment.loadRecycleView(ResponsibilityDB.getInstance(v.getContext()).getsSorted(user, 1));
                ResponsibilityFragment.loadRecycleView(ResponsibilityDB.getInstance(v.getContext()).getsSorted(user, 2));
                bottomSheetDialog.hide();
            }
        });
        spnTypeResponsibilities = bottomSheetView.findViewById(R.id.spnType);
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

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String mDate, mTime, mFullTime;

    public void getDate(View view){
        selectDate(view);
    }

    public void selectDate(View view){
//        final Calendar c = Calendar.getInstance();
        EditText txtDate = view.findViewById(R.id.txtDate);
        String[] fullTimes = txtDate.getText().toString().split(" ");
        String[] dates = fullTimes[1].split("/");
        mYear = Integer.parseInt(dates[2]);
        mMonth = Integer.parseInt(dates[1]);
        mDay = Integer.parseInt(dates[0]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker mView, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mDate = DateProvider.standardization(dayOfMonth, 2) + "/" + DateProvider.standardization(monthOfYear + 1, 2) + "/" + year;
                        selectTime(view, fullTimes[0]);
                    }
                }, mYear, mMonth - 1, mDay);
        datePickerDialog.show();
    }

    public void selectTime(View view, String time){
        EditText txtDate = view.findViewById(R.id.txtDate);
        String[] times = time.split(":");
        mHour = Integer.parseInt(times[0]);
        mMinute = Integer.parseInt(times[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mTime = DateProvider.standardization(hourOfDay, 2) + ":" + DateProvider.standardization(minute, 2);
                        if (!mDate.equals("") && !mTime.equals("")){
                            mFullTime = mTime + " " + mDate;
                            txtDate.setText(mFullTime);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

}