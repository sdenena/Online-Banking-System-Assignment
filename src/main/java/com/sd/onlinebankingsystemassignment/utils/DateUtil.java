package com.sd.onlinebankingsystemassignment.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String convertDateToStr(Date dt, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(dt);
        } catch (Exception ex) {
            // System.out.println(ex);
        }
        return null;
    }
}
