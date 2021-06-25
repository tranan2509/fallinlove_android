package com.example.fallinlove.Provider;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fallinlove.R;

import java.util.Date;
import java.util.stream.IntStream;

public class ZodiacProvider {

    public static int[] zodiacMale = {R.drawable.aries_male, R.drawable.taurus_male, R.drawable.gemini_male, R.drawable.cancer_male, R.drawable.leo_male, R.drawable.virgo_male,
            R.drawable.libra_male, R.drawable.scorpio_male, R.drawable.sagittarius_male, R.drawable.capricorn_male, R.drawable.aquarius_male, R.drawable.pisces_male};
    public static int[] zodiacFemale = {R.drawable.aries_female, R.drawable.taurus_female, R.drawable.gemini_female, R.drawable.cancer_female, R.drawable.leo_female, R.drawable.virgo_female,
            R.drawable.libra_female, R.drawable.scorpio_female, R.drawable.sagittarius_female, R.drawable.capricorn_female, R.drawable.aquarius_female, R.drawable.pisces_female};
    public static String[] zodiacName= {"Bạch Dương", "Kim Ngưu", "Song Tử", "Cự Giải", "Sư Tử", "Xử Nữ",
            "Thiên Bình", "Thiên Yết", "Nhân Mã", "Ma Kết", "Bảo Bình", "Song Ngư"};


    public static int getResourceId(String strDate, boolean isMale){
        try{
            Date date = DateProvider.dateFormat.parse(strDate);
            String year = String.valueOf(date.getYear() + 1900);
            String[] dateZodiac = {year + "-03-21", year + "-04-20", year + "-05-21", year + "-06-21", year + "-07-23", year + "-08-23",
                    year + "-09-23", year + "-10-23", year + "-11-23", year + "-12-22", year + "-01-20", year + "-02-19"};
            for (int i = 0; i < 12; i++){
                if (i < 11 && date.compareTo(DateProvider.dateFormat.parse(dateZodiac[i])) >=0 && date.compareTo(DateProvider.dateFormat.parse(dateZodiac[i + 1])) < 0){
                    return isMale ? zodiacMale[i] : zodiacFemale[i];
                }else if (i == 11 && date.compareTo(DateProvider.dateFormat.parse(dateZodiac[i])) >= 0 && date.compareTo(DateProvider.dateFormat.parse(dateZodiac[0])) < 0){
                    return isMale ? zodiacMale[i] : zodiacFemale[i];
                }
            }
            return isMale ? zodiacMale[9] : zodiacFemale[9];
        }catch (Exception ex){
            return -1;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getZodiacName(int id, boolean isMale){
        int position = isMale ? findIndex(zodiacMale, id) : findIndex(zodiacFemale, id);
        return zodiacName[position];
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int findIndex(int arr[], int t)
    {
        int len = arr.length;
        return IntStream.range(0, len)
                .filter(i -> t == arr[i])
                .findFirst() // first occurrence
                .orElse(-1); // No element found
    }

}
