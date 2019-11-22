package com.example.sergio.sistz;

import android.content.Context;
import android.text.style.AbsoluteSizeSpan;

import com.example.sergio.sistz.mysql.DBUtility;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class Assistance {
    private String ID,Name;
    private String Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday;
    private DBUtility connDB;
    private Context context;
    private String titleName;

    public Assistance()
    {
    }

    public Assistance(String titleName)
    {
        this.titleName = titleName;
    }

    public Assistance(String id,String name, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday) {
        Name = name;
        ID = id;
        Sunday = sunday;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSunday() {
        return Sunday;
    }

    public void setSunday(String sunday) {
        Sunday = sunday;
    }

    public String getMonday() {
        return Monday;
    }

    public void setMonday(String monday) {
        Monday = monday;
    }

    public String getTuesday() {
        return Tuesday;
    }

    public void setTuesday(String tuesday) {
        Tuesday = tuesday;
    }

    public String getWednesday() {
        return Wednesday;
    }

    public void setWednesday(String wednesday) {
        Wednesday = wednesday;
    }

    public String getThursday() {
        return Thursday;
    }

    public void setThursday(String thursday) {
        Thursday = thursday;
    }

    public String getFriday() {
        return Friday;
    }

    public void setFriday(String friday) {
        Friday = friday;
    }

    public String getSaturday() {
        return Saturday;
    }

    public void setSaturday(String saturday) {
        Saturday = saturday;
    }


    public  int getWeekDayNum(String weekDay)
    {
        int day = 0;
        if(weekDay.equals("Sunday")||weekDay.equals("Jumapili"))
            day = 0;
        else if(weekDay.equals("Monday")||weekDay.equals("Jumatatu"))
            day = 1;
        else if(weekDay.equals("Tuesday")||weekDay.equals("Jumanne"))
            day = 2;
        else if(weekDay.equals("Wednesday")||weekDay.equals("Jumatano"))
            day = 3;
        else if(weekDay.equals("Thursday")||weekDay.equals("Alhamisi"))
            day = 4;
        else if(weekDay.equals("Friday")||weekDay.equals("Ijumaa"))
            day = 5;
        else if(weekDay.equals("Saturday")||weekDay.equals("Jumamosi"))
            day = 6;

        return day;
    }

    public void printWeek(String[] week)
    {
        for(int i = 0; i < week.length; i++)
            System.out.println(week[i]);
    }


    public int getMaxNumWeeksPerMonth(int anio,int mes)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, anio);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.MONTH, mes);
        int maxWeeknumber = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
        return maxWeeknumber;
    }


    public int getCurrentNumWeekPerMonth(int anio,int mes, int dia)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, anio);
        cal.set(Calendar.DAY_OF_MONTH, dia);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.MONTH, mes);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }


    public Assistance GetWeekArray(int anio, int mes,int semana)
    {

        semana = semana - 1;//ajustamos para empezar semana 1,2,3, en vez de 0,1,2
        mes = mes - 1;//esto para usar la fecha estandar y no estar sumando
        String[] pagina = new String[7];
        //inicializamos
        for(int i = 0; i < 7; i++)
            pagina[i] = " ";

        // Create a calendar with year and day of year.
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        int maxDia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weekday;

        int recordWeek = getCurrentNumWeekPerMonth(anio,mes, 1) + semana;
        for(int i =1; i <= maxDia; i++)
        {
            cal.set(Calendar.YEAR, anio);
            cal.set(Calendar.MONTH, mes);
            cal.setFirstDayOfWeek(Calendar.SUNDAY);
            cal.set(Calendar.DAY_OF_MONTH, i);
            weekday = cal.get(Calendar.DAY_OF_WEEK);
            DateFormatSymbols dfs = new DateFormatSymbols();
            if(recordWeek == getCurrentNumWeekPerMonth(anio,mes, i))
            {
                pagina[getWeekDayNum(dfs.getWeekdays()[weekday])] = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) ;
            }
        }
        Assistance A = new Assistance("",titleName,pagina[0],pagina[1],pagina[2],pagina[3],pagina[4],pagina[5],pagina[6]);
        return A;
    }


    public Assistance weekDayNames(Locale loc)
    {
        Locale l = new Locale("sw", "TZ");
        Assistance A ;
        if (loc.equals(l))
            A = new Assistance("","","J","J","J","J","A","I","J");
        else
            A = new Assistance("","","S","M","T","W","T","F","S");
        return A;
    }


    public int monthNameToNumber(String monthName)
    {
        Date date = null;
        int monthNumber = 0;
        try {
            date = new SimpleDateFormat("MMMM").parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.SUNDAY);
            cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            cal.setTime(date);
            monthNumber = cal.get(Calendar.MONTH) + 1;
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return monthNumber;
    }
}
