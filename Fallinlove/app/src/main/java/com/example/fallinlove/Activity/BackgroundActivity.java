package com.example.fallinlove.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.BackgroundRecyclerViewAdapter;
import com.example.fallinlove.Adapter.DaysRecyclerViewAdapter;
import com.example.fallinlove.Adapter.HeartRecyclerViewAdapter;
import com.example.fallinlove.DBUtil.BackgroundDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.Model.Background;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.ImageResizer;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.io.IOException;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class BackgroundActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CAMERA_REQUEST = 111;
    private static final int PICK_IMAGE = 222;

    private static String type = "heart";
    public static boolean isChangeBackground = false;

    //Model
    User user;
    public static List<Background> hearts;
    public static List<Background> backgrounds;
    public static List<Background> days;
    ImageSetting imageSetting;

    //Elements
    Intent intentNext;
    public static ImageView imgBgHome;
    public static RecyclerView recyclerViewHeart, recyclerViewBackground, recyclerViewDays;
    ImageButton btnBack, btnAddHeart, btnAddBackground, btnAddDays;

    //Adapter
    public static HeartRecyclerViewAdapter heartRecyclerViewAdapter;
    public static BackgroundRecyclerViewAdapter backgroundRecyclerViewAdapter;
    public static DaysRecyclerViewAdapter daysRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        loadModel();
        getView();
        setView();
        setOnClick();
    }

    @Override
    public void onBackPressed() {
        if (isChangeBackground){
            intentNext = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intentNext);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }


    public void loadModel(){
        user = (User) SharedPreferenceProvider.getInstance(this).get("user");
        imageSetting = ImageSettingDB.getInstance(this).get(user);
        hearts = BackgroundDB.getInstance(this).gets(user, "heart");
        backgrounds = BackgroundDB.getInstance(this).gets(user,"background");
        days = BackgroundDB.getInstance(this).gets(user, "days");
    }

    public void getView(){
        //image view
        imgBgHome = findViewById(R.id.imgBgHome);

        //button
        btnBack = findViewById(R.id.btnBack);
        btnAddHeart = findViewById(R.id.btnAddHeart);
        btnAddBackground = findViewById(R.id.btnAddBackground);
        btnAddDays = findViewById(R.id.btnAddDays);

        //recycler view
        recyclerViewHeart = findViewById(R.id.recyclerViewHeart);
        recyclerViewBackground = findViewById(R.id.recyclerViewBackground);
        recyclerViewDays = findViewById(R.id.recyclerViewDays);
    }

    public void setView(){
        isChangeBackground = false;

        imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(imageSetting.getBackground()));

        loadHearts(hearts);
        loadBackground(backgrounds);
        loadDays(days);
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        btnAddHeart.setOnClickListener(this);
        btnAddBackground.setOnClickListener(this);
        btnAddDays.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                if (isChangeBackground){
                    intentNext = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intentNext);
                }
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.btnAddHeart:
                type = "heart";
                getImageGallery();
                break;
            case R.id.btnAddBackground:
                type = "background";
                getImageGallery();
                break;
            case R.id.btnAddDays:
                type = "days";
                getImageGallery();
                break;
        }
    }

    public static void loadHearts(List<Background> hearts){
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerViewHeart.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHeart.setLayoutManager(layoutManager);
        heartRecyclerViewAdapter = new HeartRecyclerViewAdapter(hearts);
        recyclerViewHeart.setAdapter(heartRecyclerViewAdapter);
        recyclerViewHeart.setItemAnimator(new SlideInUpAnimator());
    }

    public static void loadBackground(List<Background> backgrounds){
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerViewBackground.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBackground.setLayoutManager(layoutManager);
        backgroundRecyclerViewAdapter = new BackgroundRecyclerViewAdapter(backgrounds);
        recyclerViewBackground.setAdapter(backgroundRecyclerViewAdapter);
        recyclerViewBackground.setItemAnimator(new SlideInUpAnimator());
    }

    public static void loadDays(List<Background> backgrounds){
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerViewDays.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDays.setLayoutManager(layoutManager);
        daysRecyclerViewAdapter = new DaysRecyclerViewAdapter(backgrounds);
        recyclerViewDays.setAdapter(daysRecyclerViewAdapter);
        recyclerViewDays.setItemAnimator(new SlideInUpAnimator());
    }

    private void getImageGallery(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Chọn hình ảnh");
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
                photo = ImageResizer.reduceBitmapSize(photo, (int)ImageResizer.MAX_SIZE);
                Background img = new Background(user.getId(), ImageConvert.BitmapToArrayByte(photo), type, false);
                BackgroundDB.getInstance(this).add(img);
//                imgAvatar.setImageBitmap(photo);
                switch (type){
                    case "heart":
                        hearts.add(img);
                        heartRecyclerViewAdapter.notifyItemInserted(hearts.size() - 1);
                        recyclerViewHeart.scrollToPosition(heartRecyclerViewAdapter.getItemCount() - 1);
                        loadHearts(hearts);
                        break;
                    case "background":
                        backgrounds.add(img);
                        backgroundRecyclerViewAdapter.notifyItemInserted(backgrounds.size() - 1);
                        recyclerViewBackground.scrollToPosition(backgroundRecyclerViewAdapter.getItemCount() - 1);
                        loadBackground(backgrounds);
                        break;
                    case "days":
                        days.add(img);
                        daysRecyclerViewAdapter.notifyItemInserted(days.size() - 1);
                        recyclerViewDays.scrollToPosition(daysRecyclerViewAdapter.getItemCount() - 1);
                        loadDays(days);
                        break;
                }
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