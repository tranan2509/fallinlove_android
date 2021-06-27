package com.example.fallinlove.Activity.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fallinlove.Adapter.StringSpinnerAdapter;
import com.example.fallinlove.DBUtil.ResponsibilityDB;
import com.example.fallinlove.Model.Responsibility;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddResponsibilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddResponsibilityFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddResponsibilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddResponsibilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddResponsibilityFragment newInstance(String param1, String param2) {
        AddResponsibilityFragment fragment = new AddResponsibilityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Model
    User user;

    Button btnSave;
    EditText txtName, txtDate;
    Spinner spnTypeResponsibilities, spnLevels;
    ImageView btnSelectDate;

    String[] typeResponsibility, level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_responsibility, container, false);

        getModel(mView);
        getViewFragment(mView);
        setView(mView);
        setOnClick();

        return mView;
    }

    public void getModel(View view){
        user = (User) SharedPreferenceProvider.getInstance(view.getContext()).get("user");
    }

    public void getViewFragment(View view){
        btnSave = view.findViewById(R.id.btnSave);
        txtName = view.findViewById(R.id.txtName);
        txtDate = view.findViewById(R.id.txtDate);
        spnTypeResponsibilities = view.findViewById(R.id.spnType);
        spnLevels = view.findViewById(R.id.spnLevel);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
    }

    public void setView(View view){
        typeResponsibility = new String[]{"Hàng ngày", "Nhiệm vụ"};

        level = new String[]{"Bình thường", "Quan trọng", "Rất quan trọng"};

        setSpinner(view, typeResponsibility, level);

        final Calendar c = Calendar.getInstance();
        String date = DateProvider.datetimeFormat.format(c.getTime());
        txtDate.setText(DateProvider.convertDateTimeSqliteToPerson(date));
    }

    public void setSpinner(View view, String[] typeResponsibilities, String[] levels){
        StringSpinnerAdapter stringSpinnerAdapterType = new StringSpinnerAdapter(getContext(), typeResponsibilities);
        spnTypeResponsibilities.setAdapter(stringSpinnerAdapterType);
        spnTypeResponsibilities.setSelection(0);

        StringSpinnerAdapter stringSpinnerAdapterLevel = new StringSpinnerAdapter(getContext(), levels);
        spnLevels.setAdapter(stringSpinnerAdapterLevel);
        spnLevels.setSelection(0);
    }

    public void setOnClick(){
        btnSelectDate.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSelectDate:
                getDate(view);
                break;
            case R.id.btnSave:
                save(view);
                txtName.setText("");
                Toast.makeText(view.getContext(), "Lưu thành công", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void save(View view) {
        String name = txtName.getText().toString();
        String date = DateProvider.convertDateTimePersonToSqlite(txtDate.getText().toString());
        int type = (int)spnTypeResponsibilities.getSelectedItemPosition() + 1;
        int level = (int)spnLevels.getSelectedItemPosition() + 1;
        if (type == 1){
            Calendar cal = Calendar.getInstance();
            String now = DateProvider.datetimeFormat.format(cal.getTime());
            date = now.split(" ")[0] + " " + date.split(" ")[1];
        }

        Responsibility responsibility = new Responsibility(user.getId(), name, date, type, level, false);
        ResponsibilityDB.getInstance(view.getContext()).add(responsibility);
        DailyFragment.loadRecycleView(ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, 1));
        ResponsibilityFragment.loadRecycleView(ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, 2));
    }

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String mDate, mTime, mFullTime;

    public void getDate(View view){
        selectDate(view);
    }

    public void selectDate(View view){
//        final Calendar c = Calendar.getInstance();
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