package com.example.sergio.sistz.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;



import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jalfaro on 11/8/17.
 */

@SuppressLint("ValidFragment")
public class MyDateTimePicker extends DialogFragment implements OnDateSetListener{
    private int viewId;
    private Calendar cal;
private Locale locale;

    public MyDateTimePicker(int viewId) {
        super();
        this.viewId = viewId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

        return datepickerdialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        locale = new Locale("en","US");
        TextView textview = (TextView)getActivity().findViewById(viewId);
        textview.setHint(String.format("%04d", year) + "-" + String.format("%02d",(month + 1)) + "-" + String.format("%02d", day));
        textview.setText(DateUtility.formatLocale(String.format("%04d", year) + "-" + String.format("%02d",(month + 1)) + "-" + String.format("%02d", day), locale));

        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

    }

    public Calendar getCalendar( ){
        return cal;
    }
}
