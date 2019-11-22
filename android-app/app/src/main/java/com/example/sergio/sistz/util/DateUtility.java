package com.example.sergio.sistz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DateUtility {

    private static Calendar convertDate(String date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(8)));
        cal.set(MONTH, Integer.parseInt(date.substring(5,7)) - 1);
        cal.set(YEAR, Integer.parseInt(date.substring(0,4)));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  convertCalToString(cal);
    }

    public static String convertCalToString(Calendar cal) {
        return  cal.get(YEAR)+ "-" + String.format ("%02d",cal.get(MONTH) +1) + "-" + String.format ("%02d",cal.get(Calendar.DAY_OF_MONTH));
    }



    public static Calendar firstDayOf (int period, int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(MONTH, 0);
        if (period == MONTH) {
            cal.set(MONTH, month);
        }
        cal.set(YEAR, year);
        return cal;
    }

    public static Calendar lastDayOf (int period, int year, int month){
        Calendar cal = Calendar.getInstance();
        Calendar temp;
        cal.set(Calendar.DAY_OF_MONTH, 28);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(MONTH, 11);
        cal.set(YEAR, year);
        if (period == MONTH) {
            cal.set(MONTH, month);
        }
        temp = Calendar.getInstance();
        temp.set(Calendar.DAY_OF_MONTH, 28);
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);
        temp.set(MONTH, cal.get(MONTH));
        temp.set(YEAR, year);
        temp.add(Calendar.DAY_OF_MONTH, 1);
        while (temp.get(MONTH)== cal.get(MONTH)) {
            cal.add(Calendar.DAY_OF_MONTH,1);
            temp.add(Calendar.DAY_OF_MONTH, 1);
        }
        return cal;
    }

    public static String calendarToString(Calendar cal) {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }
    public static String formatLocale(String date, Locale locale) {
        Date currentDate = convertDate(date).getTime();
        DateFormat full = DateFormat.getDateInstance(DateFormat.LONG, locale);
        return full.format(currentDate);
    }

    public static long setDateMinMax (int nYears) {
        Calendar dateMin = Calendar.getInstance();
        int yearMin = dateMin.get(Calendar.YEAR);
        int monthMin = dateMin.get(Calendar.MONTH);
        int dayMin = dateMin.get(Calendar.DAY_OF_MONTH);
        dateMin.set(yearMin -  nYears, monthMin,dayMin);
        return  dateMin.getTimeInMillis();
    }

    public static long setDateDefaul () {
        Calendar dateMin = Calendar.getInstance();
        int currentYear = dateMin.get(Calendar.YEAR);
        dateMin.set(currentYear , 0,1);
        return  dateMin.getTimeInMillis();
    }

    public static long setAttendanceDateDefaul () {
        Calendar dateMin = Calendar.getInstance();
        int currentYear = dateMin.get(Calendar.YEAR);
        int currentMonth = dateMin.get(Calendar.MONTH);
        dateMin.set(currentYear , currentMonth,1);
        return  dateMin.getTimeInMillis();
    }
}
