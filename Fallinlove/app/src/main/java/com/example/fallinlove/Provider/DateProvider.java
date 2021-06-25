package com.example.fallinlove.Provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateProvider {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String convertDateSqliteToPerson(String date){
        String[] units = date.split("-");
        return units[2] + "/" + units[1] + "/" + units[0];
    }

    public static String convertDatePersonToSqlite(String date){
        String[] units = date.split("/");
        return units[2] + "-" + units[1] + "-" + units[0];
    }

    public static String standardization(int value, int length){
        String str = String.valueOf(value);
        int lenStr = str.length();
        for (int i = 0; i < length - lenStr; i++){
            str = "0" + str;
        }
        return str;
    }
}
