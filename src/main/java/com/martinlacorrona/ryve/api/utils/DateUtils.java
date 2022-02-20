package com.martinlacorrona.ryve.api.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date addRestDaysToDate(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, - (days));
        return c.getTime();
    }
}
