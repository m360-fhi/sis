package com.example.sergio.sistz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.adapter.TeacherAttendanceAdapter;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.ClassCalendarDialog;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.OnClick;


public class DailyClassroomTeacherAttendance extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    Spinner sp_reason;
    ListView lv_attendance;
    ScrollView lista;
    FloatingActionButton save_reg;
    CheckBox cb_absence;
    String _IU="U",dateAttendance="";
    DatePicker dp_attandance;
    int present, absence, ts_present=0, ts_absence=0;
    TextView date_record;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy  h:mm a");
    SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-MM-dd");
    LinearLayout ll_set_date_attendance, ll_attendance_list;
    Button btn_set_date_attendance;
    ClassCalendarDialog classCalendarDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_classroom_teacher_attendance);

        classCalendarDay = new ClassCalendarDialog(this, getString(R.string.calendar_title));
        classCalendarDay.show();
        lista = (ScrollView) findViewById(R.id.sv_1);
        lv_attendance = (ListView) findViewById(R.id.lv_attendance);
        save_reg = (FloatingActionButton) findViewById(R.id.save_reg);
        date_record = (TextView) findViewById(R.id.date_record);
        dp_attandance = (DatePicker) findViewById(R.id.dp_atteandance);
        btn_set_date_attendance = (Button) findViewById(R.id.btn_set_date_attendance);
        ll_attendance_list = (LinearLayout) findViewById(R.id.ll_attendance_list);
        ll_set_date_attendance = (LinearLayout) findViewById(R.id.ll_set_date_attendance);

        init();
    }

    private void init() {
        ll_attendance_list.setVisibility(View.GONE);
        save_reg.setVisibility(View.GONE);
        save_reg.setOnClickListener(this);
        dp_attandance.setMinDate(DateUtility.setAttendanceDateDefaul());
        dp_attandance.setMaxDate(System.currentTimeMillis());
        btn_set_date_attendance.setOnClickListener(this);
        dp_attandance.setEnabled(true);
    }

    private void setDateAttendance() {
        int day = dp_attandance.getDayOfMonth();
        int month = dp_attandance.getMonth();
        int year = dp_attandance.getYear();
        calendar.set(year, month, day);
        String strDate = format.format(calendar.getTime());
        String strDate2 = formatDatabase.format(calendar.getTime());

        date_record.setText(strDate);
        dateAttendance = strDate2;
    }

    private void loadTeacher() {
        setDateAttendance();
        Cursor cur;
        Conexion cnSET_Attendance = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET_Attendance = cnSET_Attendance.getReadableDatabase();
        String sql = "SELECT _id AS code, (surname || ', ' || givenname) AS fullname  FROM teacher WHERE (exit IS NULL OR exit='' OR exit=0) ORDER BY surname;";
        Cursor cur_data_attendance = dbSET_Attendance.rawQuery(sql, null);
        cur_data_attendance.moveToFirst();

        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        if (cur_data_attendance.getCount() > 0) {
            do {
                map = new HashMap<String, String>();
                map.put("code", cur_data_attendance.getString(0));
                map.put("fullname", cur_data_attendance.getString(1));
                sql = "SELECT reason, absence FROM attendance WHERE t_id = " +map.get("code") + " AND date = '" +dateAttendance + "' AND shift is null ";
                cur = dbSET_Attendance.rawQuery(sql, null);
                map.put("absence", "1");
                map.put("reason", "0");
                if (cur != null && cur.getCount() > 0) {
                    cur.moveToFirst();
                    map.put("absence", cur.getString(cur.getColumnIndex("absence")));
                    map.put("reason", cur.getString(cur.getColumnIndex("reason")));
                }
                cur.close();
                mylist.add(map);
            }
            while (cur_data_attendance.moveToNext());

            TeacherAttendanceAdapter mSchedule = new TeacherAttendanceAdapter(this, mylist);
            lv_attendance.setAdapter(mSchedule);

            int tmp_hg = (lv_attendance.getCount() * 75 ) + 150;
            ViewGroup.LayoutParams params = lv_attendance.getLayoutParams();
            params.height = tmp_hg;
            lv_attendance.setLayoutParams(params);
        }
        else {
            mylist.clear();
            SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.row_list_assing,
                    new String[]{"code", "fullname"}, new int[]{R.id.txt1, R.id.txt2});
            lv_attendance.setAdapter(mSchedule);
            save_reg.setVisibility(View.GONE);
        }
        cur_data_attendance.close();
        dbSET_Attendance.close();
        cnSET_Attendance.close();
    }

    private boolean validAttendances () {
        boolean validate=true;
        for (int i=0; i < lv_attendance.getAdapter().getCount(); i++) {
            if (!((CheckBox)lv_attendance.getChildAt(i).findViewById(R.id.cb_absence)).isChecked() &&
                    //((Spinner)lv_attendance.getChildAt(i).findViewById(R.id.sp_reason)).equals("0") ) {
                    ((Spinner)lv_attendance.getChildAt(i).findViewById(R.id.sp_reason)).getSelectedItemPosition() == 0 ) {
                validate=false;}
        }
        return validate;
    }
    private void saveTeacherAttendance() {
        setDateAttendance();
        int t_present = 0, charge=0;
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", sql_teacher="", delimit="%", tableName="attendance";
        dbSET.delete("attendance", "shift IS null AND level IS null AND grade IS null AND date=\"" + dateAttendance +"\"",null);

        sql = sql + tableName + delimit + dateAttendance + delimit + getEMIS_code() + delimit;
        ContentValues reg = new ContentValues();
        for (int i=0; i < lv_attendance.getAdapter().getCount(); i++) {
            HashMap<String,String> item = (HashMap<String, String>) lv_attendance.getItemAtPosition(i);
            String absence="", reason="";
            reg.put("emis",getEMIS_code());
            reg.put("t_id",  item.get("code"));

            String reason_val = String.valueOf(((TextView)((Spinner)lv_attendance.getChildAt(i).findViewById(R.id.sp_reason)).getSelectedView()).getText().toString());
            if (reason_val.toString().equals(getResources().getString(R.string.str_g_sick)) || reason_val.toString().equals(getResources().getString(R.string.str_g_excused)) || reason_val.toString().equals(getResources().getString(R.string.str_g_unexcused)) || reason_val.toString().equals("Total absence") ) {
                absence="";
                reason="0";
                // ******************* CONTROL DE RAZON DE AUSENCIA *****************************
                reason_val = String.valueOf(((TextView)((Spinner)lv_attendance.getChildAt(i).findViewById(R.id.sp_reason)).getSelectedView()).getText().toString());
                ts_absence++;
                reason="0";
                switch (reason_val){
                    case "Sick": reason="1"; break;
                    case "Excused": reason="2"; break;
                    case "Unexcused": reason="3"; break;
                    case "Kuumwa": reason="1"; break;
                    case "Ruhusiwa": reason="2"; break;
                    case "Kutoruhusiwa": reason="3"; break;
                    //default: reason="0"; break;
                    //case "Total absence": reason="4"; break;
                }
            } else {
                absence="1";
                reason="0";
                ts_present++;
            }

            // 2018-04-30 AGREGA INASISTENCIA "0", NO IMPORTA SI TIENE RAZON DE FALTA O NO.
            if (((CheckBox)lv_attendance.getChildAt(i).findViewById(R.id.cb_absence)).isChecked()) {absence="1"; }
            else {absence="0";}

            reg.put("s_id", item.get("code"));  //sql = sql + item.get("code") + delimit;
            reg.put("absence", absence);        //sql = sql + absence + delimit;
            reg.put("reason", reason);          //sql = sql + reason + delimit;
            reg.put("date", dateAttendance);

            dbSET.insert("attendance", null, reg);
            if (reason != "0") {sql_teacher= item.get("code") + delimit + absence + delimit + reason + delimit;}
            else {sql_teacher="";}
            //sql = sql + item.get("code") + delimit + absence + delimit + reason + delimit;
            sql = sql + sql_teacher;
        }
        toolsfncs.dialogAlertConfirm(this,getResources(),9);
        sql = sql  + _IU;
        _IU = "U";
        try {
            // ****************** Fill Bitacora
            ContentValues Bitacora = new ContentValues();
            Bitacora.put("sis_sql", sql);
            dbSET.insert("sisupdate", null, Bitacora);  sql = "";
        } catch (Exception e) {
        }
        ts_present=0; ts_absence=0;
    }

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_date_attendance:
                loadTeacher();
                save_reg.setVisibility(View.VISIBLE);
                ll_attendance_list.setVisibility(view.VISIBLE);
                btn_set_date_attendance.setVisibility(View.GONE);
                dp_attandance.setEnabled(false);
                break;
            case R.id.save_reg:
                if (validAttendances()) { // Aqui se valida si se graba o no la asistencia.
                    saveTeacherAttendance();
                    save_reg.setVisibility(View.GONE);
                    ll_attendance_list.setVisibility(View.GONE);
                    btn_set_date_attendance.setVisibility(View.VISIBLE);
                    dp_attandance.setEnabled(true);
                } else {
                    toolsfncs.dialogAlertConfirm(this,getResources(),16);
                }

                break;
        }

    }
}
