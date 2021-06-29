package com.example.fallinlove.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.ImageResizer;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final int CAMERA_REQUEST = 111;
    private static final int PICK_IMAGE = 222;

    //Model
    User user;
    Person person;
    ImageSetting imageSetting;
    DisplaySetting displaySetting;

    //View
    Intent intent, intentNext;
    EditText txtName, txtDob;
    ImageView imgAvatar;
    ImageButton btnAdd, btnBack, btnSelectDate;
    Button btnSave;
    ImageView imgBgHome;

    //Date
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getModel();
        getView();
        setView();
        setOnClick();
    }

    public void getModel(){
        user = (User) SharedPreferenceProvider.getInstance(this).get("user");
        intent = getIntent();
        int personId = intent.getIntExtra("personId", 1);
        person = PersonDB.getInstance(this).get(personId);
        imageSetting = ImageSettingDB.getInstance(this).get(user);
        displaySetting = DisplaySettingDB.getInstance(this).get(user);
    }

    public void getView(){
        txtName = findViewById(R.id.txtName);
        txtDob = findViewById(R.id.txtDob);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        imgBgHome = findViewById(R.id.imgBgHome);
    }

    public void setView() {
        txtName.setText(person.getName());
        txtDob.setText(DateProvider.convertDateSqliteToPerson(person.getDob()));
        imgAvatar.setImageBitmap(ImageConvert.ArrayByteToBitmap(person.getAvatar()));
        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSelectDate.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.btnAdd:
                getImageGallery();
                break;
            case R.id.btnSave:
                save();
                intentNext = displaySetting.getMain() == 1 ? new Intent(getApplicationContext(), MainActivity.class) : new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentNext);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.btnSelectDate:
                selectDate();
                break;
        }
    }

    public void save(){
        String name = txtName.getText().toString();
        String dob = txtDob.getText().toString();
        byte[] avatar = ImageConvert.ImageViewToArrayByte(imgAvatar);
        person.setName(name);
        person.setDob(DateProvider.convertDatePersonToSqlite(dob));
        person.setAvatar(avatar);
        PersonDB.getInstance(this).update(person);
    }

    public void selectDate(){
//        final Calendar c = Calendar.getInstance();
        String[] units = txtDob.getText().toString().split("/");
        mYear = Integer.parseInt(units[2]);
        mMonth = Integer.parseInt(units[1]);
        mDay = Integer.parseInt(units[0]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txtDob.setText(DateProvider.standardization(dayOfMonth, 2) + "/" + DateProvider.standardization(monthOfYear + 1, 2) + "/" + year);

                    }
                }, mYear, mMonth - 1, mDay);
        datePickerDialog.show();
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
                imgAvatar.setImageBitmap(photo);
            } catch (IOException e) {

            }
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //xử lý ở đây
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
            return true;
        }
    }

}