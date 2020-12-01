package com.lexsoft.project.beer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String convertTime(long time){
        Date date = new Date(time);
        return SIMPLE_DATE_FORMAT.format(date);
    }
}
