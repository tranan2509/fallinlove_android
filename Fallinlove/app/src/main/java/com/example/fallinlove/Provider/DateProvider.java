package com.example.fallinlove.Provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateProvider {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    //Convert yyyy-MM-dd to dd/MM/yyyy
    public static String convertDateSqliteToPerson(String date){
        String[] units = date.split("-");
        return units[2] + "/" + units[1] + "/" + units[0];
    }
    //Convert dd/MM/yyyy to yyyy-MM-dd
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

    //Convert yyyy-MM-dd HH:mm to HH:mm dd/MM/yyyy
    public static String convertDateTimeSqliteToPerson(String fullTime){
        String[] times = fullTime.split(" ");
        return times[1] + " " + convertDateSqliteToPerson(times[0]);
    }

    //Convert HH:mm dd/MM/yyyy to yyyy-MM-dd HH:mm
    public static String convertDateTimePersonToSqlite(String date){
        String[] times = date.split(" ");
        return convertDatePersonToSqlite(times[1]) + " " + times[0];
    }

}
