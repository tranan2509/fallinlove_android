package com.example.fallinlove.Adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Activity.ui.DailyFragment;
import com.example.fallinlove.Activity.ui.ResponsibilityFragment;
import com.example.fallinlove.DBUtil.ResponsibilityDB;
import com.example.fallinlove.Model.Responsibility;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResponsibilityRecyclerViewAdapter extends RecyclerView.Adapter<ResponsibilityRecyclerViewAdapter.ViewHolder>{

    //Model
    User user;

    List<Responsibility> responsibilities;

    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    String[] typeResponsibility, level;
    Spinner spnTypeResponsibilities, spnLevels;

    String[] backgroundColors = {"#a0e5e1", "#dfe4ba", "#d4a7aa", "#afbec3"};

    public ResponsibilityRecyclerViewAdapter(List<Responsibility> responsibilities){

        this.responsibilities = responsibilities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewName, txtViewDate;
        CheckBox ckbState;
        CardView cardViewResponsibility;
        ImageView imgBackground;

        public ViewHolder(View itemView) {
            super(itemView);

            getModel(itemView);
            getView(itemView);
        }

        public void getModel(View itemView){
            user = (User) SharedPreferenceProvider.getInstance(itemView.getContext()).get("user");
        }

        public void getView(View itemView){
            txtViewName = itemView.findViewById(R.id.txtViewName);
            txtViewDate = itemView.findViewById(R.id.txtViewDate);
            ckbState = itemView.findViewById(R.id.ckbState);
            cardViewResponsibility = itemView.findViewById(R.id.cardViewResponsibility);
            imgBackground = itemView.findViewById(R.id.imgBackground);
        }

        public void setView(ViewHolder holder, Responsibility responsibility){
            holder.txtViewName.setText(responsibility.getName());
            String time = DateProvider.convertDateTimeSqliteToPerson(responsibility.getDate());
            time = responsibility.getType() == 1 ? time.split(" ")[0] : time;
            holder.txtViewDate.setText(time);
            holder.ckbState.setChecked(responsibility.isState());

            try {
                Date date = DateProvider.datetimeFormat.parse(responsibility.getDate());
                Calendar cal = Calendar.getInstance();
                int position = responsibility.getLevel() - 1;
                if (date.getTime() < cal.getTime().getTime()){
                    position = backgroundColors.length - 1;
                }
                holder.imgBackground.setBackgroundColor(Color.parseColor(backgroundColors[position]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.responsibility_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Responsibility responsibility = responsibilities.get(position);
        holder.setView(holder, responsibility);

        holder.ckbState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Responsibility oldResponsibility = responsibility;
                responsibility.setState(holder.ckbState.isChecked());
                if (responsibility.getType() == Responsibility.TYPE_DAILY){
                    DailyFragment.move(oldResponsibility, responsibility);
                }else{
                    ResponsibilityFragment.move(oldResponsibility, responsibility);
                }
//                ResponsibilityDB.getInstance(holder.itemView.getContext()).update(responsibility);
//                DailyFragment.loadRecycleView(ResponsibilityDB.getInstance(holder.itemView.getContext()).getsSorted(user, 1));
//                ResponsibilityFragment.loadRecycleView(ResponsibilityDB.getInstance(holder.itemView.getContext()).getsSorted(user, 2));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, responsibility, "edit");
            }
        });
    }

    public void showDialog(View view, Responsibility responsibility, String type){
        typeResponsibility = new String[]{"Hàng ngày", "Nhiệm vụ"};
        level = new String[]{"Bình thường", "Quan trọng", "Rất quan trọng"};

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.bottom_sheet_responsibility, (LinearLayout)view.findViewById(R.id.btnSheetContainer));
        spnTypeResponsibilities = bottomSheetView.findViewById(R.id.spnType);
        spnLevels = bottomSheetView.findViewById(R.id.spnLevel);
        setSpinner(bottomSheetView.getRootView(), typeResponsibility, level);

        EditText txtName = bottomSheetView.findViewById(R.id.txtName);
        EditText txtDate = bottomSheetView.findViewById(R.id.txtDate);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        Button btnEdit = bottomSheetView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(bottomSheetView, responsibility);
                bottomSheetDialog.hide();
            }
        });
        bottomSheetView.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(bottomSheetView, responsibility);
                bottomSheetDialog.hide();
            }
        });
        bottomSheetView.findViewById(R.id.btnSelectDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(bottomSheetView);
            }
        });
        switch (type){
            case "add":
                bottomSheetDialog.findViewById(R.id.btnSave).setVisibility(View.VISIBLE);
                bottomSheetDialog.findViewById(R.id.btnEdit).setVisibility(View.GONE);
                bottomSheetDialog.findViewById(R.id.btnDelete).setVisibility(View.GONE);
                break;
            default:
                txtName.setText(responsibility.getName());
                txtDate.setText(DateProvider.convertDateTimeSqliteToPerson(responsibility.getDate()));
                spnLevels.setSelection(responsibility.getLevel() - 1);
                spnTypeResponsibilities.setSelection(responsibility.getType() - 1);
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

    public void edit(View view, Responsibility responsibility){
        Responsibility oldResponsibility = new Responsibility(responsibility.getId(), responsibility.getUserId(), responsibility.getName(), responsibility.getDate(),
                responsibility.getType(), responsibility.getLevel(), responsibility.isState());
        EditText txtName = view.findViewById(R.id.txtName);
        EditText txtDate = view.findViewById(R.id.txtDate);
        String name = txtName.getText().toString();
        String date = txtDate.getText().toString();
        int type = (int)spnTypeResponsibilities.getSelectedItemPosition() + 1;
        int level = (int)spnLevels.getSelectedItemPosition() + 1;
        responsibility.setName(name);
        responsibility.setDate(DateProvider.convertDateTimePersonToSqlite(date));
        responsibility.setType(type);
        responsibility.setLevel(level);
        if (responsibility.getType() == Responsibility.TYPE_DAILY && oldResponsibility.getType() == Responsibility.TYPE_DAILY){
            DailyFragment.move(oldResponsibility, responsibility);
            DailyFragment.update(responsibility);
        }else if (responsibility.getType() == Responsibility.TYPE_RESPONSIBILITY && oldResponsibility.getType() == Responsibility.TYPE_RESPONSIBILITY){
            ResponsibilityFragment.move(oldResponsibility, responsibility);
            ResponsibilityFragment.update(responsibility);
        }else if (responsibility.getType() == Responsibility.TYPE_DAILY){
            ResponsibilityDB.getInstance(view.getContext()).update(responsibility);
            ResponsibilityDB.getInstance(view.getContext()).updateDaily(responsibility);
            removeItem(responsibility);
            DailyFragment.loadRecycleView(ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, Responsibility.TYPE_DAILY));
            DailyFragment.responsibilities = ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, Responsibility.TYPE_DAILY);
        }else{
            ResponsibilityDB.getInstance(view.getContext()).update(responsibility);
            removeItem(responsibility);
            ResponsibilityFragment.loadRecycleView(ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, Responsibility.TYPE_RESPONSIBILITY));
            ResponsibilityFragment.responsibilities = ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, Responsibility.TYPE_RESPONSIBILITY);
        }
//        ResponsibilityDB.getInstance(view.getContext()).update(responsibility);
//        if (type == 1){
//            ResponsibilityDB.getInstance(view.getContext()).updateDaily(responsibility);
//        }
//        DailyFragment.loadRecycleView(ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, 1));
//        ResponsibilityFragment.loadRecycleView(ResponsibilityDB.getInstance(view.getContext()).getsSorted(user, 2));
    }

    public void delete(View view, Responsibility responsibility){
        removeItem(responsibility);
        ResponsibilityDB.getInstance(view.getContext()).delete(responsibility);
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

    @Override
    public int getItemCount() {
        return responsibilities.size();
    }

    private void removeItem(int position) {
        responsibilities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, responsibilities.size());
    }

    private void removeItem(Responsibility responsibility) {
        int position = responsibilities.indexOf(responsibility);
        responsibilities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, responsibilities.size());
    }
}
