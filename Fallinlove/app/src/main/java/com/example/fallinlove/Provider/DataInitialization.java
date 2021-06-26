package com.example.fallinlove.Provider;

import android.content.Context;

import com.example.fallinlove.DBUtil.BackgroundDB;
import com.example.fallinlove.DBUtil.DisplaySettingDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.DBUtil.UserDB;
import com.example.fallinlove.Model.Background;
import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.R;

import java.util.Calendar;

public class DataInitialization {

    private static final Object lock = new Object();
    private static DataInitialization instance;
    private Context context;

    public static DataInitialization getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new DataInitialization(context);
            }
            return instance;
        }
    }

    private DataInitialization(Context context){
        this.context = context;
    }

    public void insertAllData(User user){
        insertUser(user);
        insertPerson(user);
        insertImageSetting(user);
        insertDisplaySetting(user);
        insertBackground(user);
    }

    public void insertUser(User user){
        UserDB.getInstance(context).add(user);
    }

    public void insertPerson(User user){
        byte[] avatarMale = ImageConvert.ResourceToArrayByte(context, R.drawable.avatar_male_default);
        byte[] avatarFemale = ImageConvert.ResourceToArrayByte(context, R.drawable.avatar_female_default);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3652);
        String dobMale = DateProvider.dateFormat.format(cal.getTime());
        String dobFemale = DateProvider.dateFormat.format(cal.getTime());
        Person male = new Person(user.getId(), "Tên bạn nam", avatarMale, true, dobMale, true);
        Person female = new Person(user.getId(), "Tên bạn nữ", avatarFemale, false, dobFemale, true);
        PersonDB.getInstance(context).add(male);
        PersonDB.getInstance(context).add(female);
    }

    public void insertImageSetting(User user){
        byte[] background = ImageConvert.ResourceToArrayByte(context, R.drawable.background_default);
        byte[] heart = ImageConvert.ResourceToArrayByte(context, R.drawable.icons8_heart_48);
        byte[] days = ImageConvert.ResourceToArrayByte(context, R.drawable.bg_days_home);
        ImageSetting imageSetting = new ImageSetting(user.getId(), background, heart, days, true);
        ImageSettingDB.getInstance(context).add(imageSetting);
    }

    public void insertDisplaySetting(User user){
        DisplaySettingDB.getInstance(context).add(new DisplaySetting(user.getId(), 1, 1, 1, true));
    }

    public void insertBackground(User user){
        byte[] background01 = ImageConvert.ResourceToArrayByte(context, R.drawable.background_default);
        byte[] background001 = ImageConvert.ResourceToArrayByte(context, R.drawable.bg_in_love);
        byte[] background02 = ImageConvert.ResourceToArrayByte(context, R.drawable.bg_in_love_02);
        byte[] background03 = ImageConvert.ResourceToArrayByte(context, R.drawable.bg_in_love_03);
        byte[] background04 = ImageConvert.ResourceToArrayByte(context, R.drawable.bg_in_love_04);
        byte[] heart01 = ImageConvert.ResourceToArrayByte(context, R.drawable.icons8_heart_48);
        byte[] heart02 = ImageConvert.ResourceToArrayByte(context, R.drawable.heart_02);
        byte[] heart03 = ImageConvert.ResourceToArrayByte(context, R.drawable.heart_03);
        byte[] heart04 = ImageConvert.ResourceToArrayByte(context, R.drawable.heart_04);
        byte[] days01 = ImageConvert.ResourceToArrayByte(context, R.drawable.bg_days_home);

        Background background_01 = new Background(user.getId(), background01, "background", true);
        Background background_001 = new Background(user.getId(), background001, "background", false);
        Background background_02 = new Background(user.getId(), background02, "background", false);
        Background background_03 = new Background(user.getId(), background03, "background", false);
        Background background_04 = new Background(user.getId(), background04, "background", false);
        Background background_05 = new Background(user.getId(), heart01, "heart", true);
        Background background_06 = new Background(user.getId(), heart02, "heart", false);
        Background background_07 = new Background(user.getId(), heart03, "heart", false);
        Background background_08 = new Background(user.getId(), heart04, "heart", false);
        Background background_09 = new Background(user.getId(), days01, "days", true);

        BackgroundDB.getInstance(context).add(background_01);
        BackgroundDB.getInstance(context).add(background_001);
        BackgroundDB.getInstance(context).add(background_02);
        BackgroundDB.getInstance(context).add(background_03);
        BackgroundDB.getInstance(context).add(background_04);
        BackgroundDB.getInstance(context).add(background_05);
        BackgroundDB.getInstance(context).add(background_06);
        BackgroundDB.getInstance(context).add(background_07);
        BackgroundDB.getInstance(context).add(background_08);
        BackgroundDB.getInstance(context).add(background_09);
    }

    public void insertZodiac(){
        int[] zodiacMale = {R.drawable.aries_male, R.drawable.taurus_male, R.drawable.gemini_male, R.drawable.cancer_male, R.drawable.leo_male, R.drawable.virgo_male,
                R.drawable.libra_male, R.drawable.scorpio_male, R.drawable.sagittarius_male, R.drawable.capricorn_male, R.drawable.aquarius_male, R.drawable.pisces_male};
        int[] zodiacFemale = {R.drawable.aries_female, R.drawable.taurus_female, R.drawable.gemini_female, R.drawable.cancer_female, R.drawable.leo_female, R.drawable.virgo_female,
                R.drawable.libra_female, R.drawable.scorpio_female, R.drawable.sagittarius_female, R.drawable.capricorn_female, R.drawable.aquarius_female, R.drawable.pisces_female};
    }

}
