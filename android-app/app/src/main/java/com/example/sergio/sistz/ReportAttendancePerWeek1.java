package com.example.sergio.sistz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.adapter.AssistanceAdapter;
import com.example.sergio.sistz.mysql.DBUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class ReportAttendancePerWeek1 extends Fragment {

    private int WEEK_NUMBER = 1;
    private int report;
    private String reportTitle;
    private String attendanceTextDate;
    private LinearLayout linearLayout;
    private String Month, Level, Grade, Shift, Stream, SpYear;
    private TextView txtDate, txtTitle, txtWeek;
    private Button buttonLeft, buttonRight;
    private DBUtility connDB;
    private Locale loc;
    private ListView lista;
    private AssistanceAdapter adapter;
    private ArrayList<Assistance> aLista1, aLista2, aLista3, aLista4, aLista5, aLista6;
    float initialX, initialY, finalX, finalY;
    Handler handlerData;
    ProgressDialog dialogCarga;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        WEEK_NUMBER = 1;//default week to start
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.report_attendance_per_week_1,
                container, false);
        linearLayout = mLinearLayout;
        connDB = new DBUtility(linearLayout.getContext());
        loc = connDB.getCurrentLocale();

        lista = (ListView) linearLayout.findViewById(R.id.LicenseListView);

        loadPreferences();

        txtTitle = (TextView) linearLayout.findViewById(R.id.attendanceTextTitle);
        txtTitle.setText(reportTitle);

        txtDate = (TextView) linearLayout.findViewById(R.id.attendanceTextDate);
        txtDate.setText(attendanceTextDate);


        buttonLeft = (Button) mLinearLayout.findViewById(R.id.bLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveLeft();
            }
        });

        buttonRight = (Button) mLinearLayout.findViewById(R.id.bRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveRight();
            }
        });

        init();
//        loadFromDataBaseData();
//        loadData(1);//default week on create view
        return mLinearLayout;
    }

    private void init() {
        dialogCarga = new ProgressDialog(getContext());
        dialogCarga.setMessage(getResources().getString(R.string.attendance_rep_msg));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                loadFromDataBaseData();
                handlerData.sendEmptyMessage(0);
//                loadData(1);//default week on create view
            }
        }.start();
        handlerData = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dialogCarga.dismiss();
                loadData(1);//default week on create view
            }
        };
    }

    private void moveLeft() {
        if (WEEK_NUMBER > 1) {
            WEEK_NUMBER--;
            //toastMSG("clicked left " + String.valueOf(WEEK_NUMBER));
            loadData(WEEK_NUMBER);
            txtWeek = (TextView) linearLayout.findViewById(R.id.textW1);
            txtWeek.setText(getResources().getString(R.string.week) + " " + String.valueOf(WEEK_NUMBER));
        }
    }

    private void moveRight() {
        if (WEEK_NUMBER < 6) {
            WEEK_NUMBER++;
            //toastMSG("clicked right " + String.valueOf(WEEK_NUMBER));
            loadData(WEEK_NUMBER);
            txtWeek = (TextView) linearLayout.findViewById(R.id.textW1);
            txtWeek.setText(getResources().getString(R.string.week) + " " + String.valueOf(WEEK_NUMBER));
        }

    }

    private void loadFromDataBaseData() {
        aLista1 = loadAssistance(1);
        aLista2 = loadAssistance(2);
        aLista3 = loadAssistance(3);
        aLista4 = loadAssistance(4);
        aLista5 = loadAssistance(5);
        aLista6 = loadAssistance(6);
    }

    private void loadData(int week) {
        switch (week) {
            case 1:
                adapter = new AssistanceAdapter(getContext(), R.layout.report_attendance_per_week_listview, aLista1);
                lista.setAdapter(adapter);
                break;
            case 2:
                adapter = new AssistanceAdapter(getContext(), R.layout.report_attendance_per_week_listview, aLista2);
                lista.setAdapter(adapter);
                break;
            case 3:
                adapter = new AssistanceAdapter(getContext(), R.layout.report_attendance_per_week_listview, aLista3);
                lista.setAdapter(adapter);
                break;
            case 4:
                adapter = new AssistanceAdapter(getContext(), R.layout.report_attendance_per_week_listview, aLista4);
                lista.setAdapter(adapter);
                break;
            case 5:
                adapter = new AssistanceAdapter(getContext(), R.layout.report_attendance_per_week_listview, aLista5);
                lista.setAdapter(adapter);
                break;
            case 6:
                adapter = new AssistanceAdapter(getContext(), R.layout.report_attendance_per_week_listview, aLista6);
                lista.setAdapter(adapter);
                break;
        }
    }

    private void loadPreferences() {
        //control configuration per report.
        //TEACHER REPORT 17
        //STUDENT REPORT 18

        SharedPreferences prefs = getContext().getSharedPreferences("ATTENDANCE", MODE_PRIVATE);
        attendanceTextDate = prefs.getString("ATTENDANCE_MONTH", null) + " , "
                + prefs.getString("ATTENDANCE_YEAR", null);
        reportTitle = prefs.getString("ATTENDANCE_TITLE", "Attendance List");

        SpYear = prefs.getString("ATTENDANCE_YEAR", null);
        Month = prefs.getString("ATTENDANCE_MONTH", null);
        Level = prefs.getString("ATTENDANCE_LEVEL", null);
        Grade = prefs.getString("ATTENDANCE_GRADE", null);
        Shift = prefs.getString("ATTENDANCE_SHIFT", null);
        Stream = prefs.getString("ATTENDANCE_STREAM", null);
        report = prefs.getInt("REPORT", 17);

        txtWeek = (TextView) linearLayout.findViewById(R.id.textW1);
        txtWeek.setText(getResources().getString(R.string.week) + " " + String.valueOf(WEEK_NUMBER));
    }


    private ArrayList<Assistance> loadAssistance(int weekNum) {
        int yyyy = Integer.valueOf(SpYear);//Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<Assistance> list = new ArrayList<>();
        Assistance A = new Assistance(this.getString(R.string.table_1_report1_name));
        list.add(A.weekDayNames(loc));
        list.add(A.GetWeekArray(yyyy, A.monthNameToNumber(Month), weekNum));


        if (report == 17)//teacher
        {
            ArrayList<Assistance> listT =
                    connDB.getTeacherAttendancePerWeek(report, weekNum, A.GetWeekArray(yyyy, A.monthNameToNumber(Month),
                            weekNum), A.monthNameToNumber(Month), yyyy);
            for (int i = 0; i < listT.size(); i++) {
                A = new Assistance(this.getString(R.string.table_1_report1_name));
                A = listT.get(i);
                list.add(A);
            }
        } else if (report == 18)//students
        {
            ArrayList<Assistance> listT =
                    connDB.getStudentAttendancePerWeek(report, weekNum, A.GetWeekArray(yyyy, A.monthNameToNumber(Month),
                            weekNum), A.monthNameToNumber(Month), yyyy, Shift, Level, Grade, Stream);
            for (int i = 0; i < listT.size(); i++) {
                A = new Assistance(this.getString(R.string.table_1_report1_name));
                A = listT.get(i);
                list.add(A);
            }
        }

        return list;
    }

    public void toastMSG(String msg) {
        Context context = getContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}
