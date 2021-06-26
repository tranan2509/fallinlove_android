package com.example.fallinlove.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fallinlove.DBUtil.AnniversaryDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.Model.Anniversary;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.ImageResizer;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.io.IOException;
import java.util.Calendar;

public class FunctionAnniversaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 111;
    private static final int PICK_IMAGE = 222;

    //Model
    User user;
    Anniversary anniversary;
    ImageSetting imageSetting;

    Intent intent;
    EditText txtName, txtDate, txtDescription;
    ImageView imgAnniversary;
    ImageButton btnAdd, btnBack, btnSelectDate;
    Button btnSave, btnEdit, btnDelete;
    ImageView imgBgHome;

    //Time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String mFullTime;
    private static String mDate, mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_anniversary);

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
        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        imgAnniversary = findViewById(R.id.imgAnniversary);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelectDate = findViewById(R.id.btnSelectDate);
    }

    public void setView() {
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));

        mDate = "";
        mTime = "";

        switch (intent.getStringExtra("function")){
            case "add":
                Calendar cal = Calendar.getInstance();
                String now = DateProvider.datetimeFormat.format(cal.getTime());
                mFullTime = DateProvider.convertDateTimeSqliteToPerson(now);
                txtDate.setText(mFullTime);
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                break;
            default:
                anniversary = (Anniversary)intent.getExtras().getSerializable("anniversary");
                txtName.setText(anniversary.getName());
                mFullTime = DateProvider.convertDateTimeSqliteToPerson(anniversary.getDate());
                txtDate.setText(mFullTime);
                txtDescription.setText(anniversary.getDescription());
                imgAnniversary.setImageBitmap(ImageConvert.ArrayByteToBitmap(anniversary.getImage()));
                btnSave.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                break;

        }
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSelectDate.setOnClickListener(this);
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
            case R.id.btnSelectDate:
                selectDate();
                break;
            case R.id.btnAdd:
                getImageGallery();
                break;
        }
    }

    private void delete() {
        AnniversaryDB.getInstance(this).delete(anniversary);
        intent = new Intent(this, AnniversaryActivity.class);
        startActivity(intent);
        finish();
    }

    private void save() {
        String name = txtName.getText().toString();
        String date = DateProvider.convertDateTimePersonToSqlite(txtDate.getText().toString());
        String description = txtDescription.getText().toString();
        byte[] image = ImageConvert.ImageViewToArrayByte(imgAnniversary);
        if (intent.getStringExtra("function").equals("add")){
            anniversary = new Anniversary(user.getId(), name, date, description, image, true);
            AnniversaryDB.getInstance(this).add(anniversary);
        }else{
            anniversary.setName(name);
            anniversary.setDate(date);
            anniversary.setDescription(description);
            anniversary.setImage(image);
            AnniversaryDB.getInstance(this).update(anniversary);
        }
        intent = new Intent(this, AnniversaryActivity.class);
        startActivity(intent);
        finish();
    }

    public void selectDate(){
//        final Calendar c = Calendar.getInstance();
        String[] fullTimes = txtDate.getText().toString().split(" ");
        String[] dates = fullTimes[1].split("/");
        mYear = Integer.parseInt(dates[2]);
        mMonth = Integer.parseInt(dates[1]);
        mDay = Integer.parseInt(dates[0]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mDate = DateProvider.standardization(dayOfMonth, 2) + "/" + DateProvider.standardization(monthOfYear + 1, 2) + "/" + year;
                        selectTime();
                    }
                }, mYear, mMonth - 1, mDay);
        datePickerDialog.show();
    }

    public void selectTime(){
        String[] fullTimes = txtDate.getText().toString().split(" ");
        String[] times = fullTimes[0].split(":");
        mHour = Integer.parseInt(times[0]);
        mMinute = Integer.parseInt(times[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
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

    private void getImageGallery(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Chọn ảnh");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                photo = ImageResizer.reduceBitmapSize(photo, ImageResizer.MAX_SIZE);
                imgAnniversary.setImageBitmap(photo);
            } catch (IOException e) {

            }
            return;
        }
    }

    private boolean checkPermissionReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;        } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;        }
        } else {
            return true;    }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //xử lý ở đây
        }
    }
}