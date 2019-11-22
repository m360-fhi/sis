package com.example.sergio.sistz.util;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.ClassCalendarInformation;
import com.example.sergio.sistz.mysql.DBUtility;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassCalendarDialog extends Dialog {

    @BindView(R.id.tv_daysMontly_1) TextView tvdm_1;
    @BindView(R.id.tv_daysMontly_2) TextView tvdm_2;
    @BindView(R.id.tv_daysMontly_3) TextView tvdm_3;
    @BindView(R.id.tv_daysMontly_4) TextView tvdm_4;
    @BindView(R.id.tv_daysMontly_5) TextView tvdm_5;
    @BindView(R.id.tv_daysMontly_6) TextView tvdm_6;
    @BindView(R.id.tv_daysMontly_7) TextView tvdm_7;
    @BindView(R.id.tv_daysMontly_8) TextView tvdm_8;
    @BindView(R.id.tv_daysMontly_9) TextView tvdm_9;
    @BindView(R.id.tv_daysMontly_10) TextView tvdm_10;
    @BindView(R.id.tv_daysMontly_11) TextView tvdm_11;
    @BindView(R.id.tv_daysMontly_12) TextView tvdm_12;
    @BindView(R.id.bn_confirm_calendar) Button confirm;
    @BindView(R.id.lyt_dialog)
    LinearLayout layout;
    @BindView(R.id.title_calendar) TextView title_c;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);


    private Context context;
    private String title_dialog="";
    private ArrayList<ClassCalendarInformation> list;

    DBUtility conn;

    public ClassCalendarDialog(@NonNull Context context, String title) {
        super(context);
        this.context = context;
        title_dialog = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.class_calendar);
        conn = new DBUtility(getContext());
        list = conn.ClassCalendarInformation(currentYear);
        ButterKnife.bind(this);
        title_c.setText(title_dialog);
        //layout.setLayoutParams(new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvdm_1.setText(list.get(0).getCcdays() + " " + getContext().getString(R.string.str_days));
        tvdm_2.setText(list.get(1).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_3.setText(list.get(2).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_4.setText(list.get(3).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_5.setText(list.get(4).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_6.setText(list.get(5).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_7.setText(list.get(6).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_8.setText(list.get(7).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_9.setText(list.get(8).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_10.setText(list.get(9).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_11.setText(list.get(10).getCcdays()+ " " + getContext().getString(R.string.str_days));
        tvdm_12.setText(list.get(11).getCcdays()+ " " + getContext().getString(R.string.str_days));
    }

    @OnClick(R.id.bn_confirm_calendar)
    public void clickYes(View v) {
        dismiss();
    }

}
