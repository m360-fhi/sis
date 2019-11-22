package com.example.sergio.sistz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

;
/**
 * Created by Sergio on 3/21/2016.
 */
public class DailyClassroomEvaluation extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    String[] _shift = {"Morning","Afternoon","Evening"};
    String[] _level = {"Primary","Secondary","Pre-Primary"};
    private String[] _grade = {"G1","G2","G3","G4","G5","G6","G7","G8"};
    private String[] _section = {"A","B","C","D","E","F","G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String[] _subject_p = {"Mathematics","English","Kiswahili","French","Science","Geography","Civics","History","Vocational skills","Personality and Sports","ICT","Other"};
    String[] _evaluationType = {"Select one","January Test", "February Test","March test","April test","May test","June test","July test","August test","September test","Octuber test","November test","Mid-term1","Mid_term2","Terminal exam","End of year exam"};
    ArrayList<String> list_teacher = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    ArrayList<String> list_evaluationType = new ArrayList<>();
    Spinner sp_teacher, sp_reason, sp_evaluationType;
    ListView lv_attendance, lv_subject_attendance;
    ScrollView lista;
    public static String EMIS_code = "";
    public static String code="";
    CharSequence texto;
    FloatingActionButton save_reg,btn_confirm;
    CheckBox cb_absence;
    RadioButton _col1a, _col1b, _col1c, _col1d, _col1e;
    TextView date_record, tv_subject, tv_note;
    String _IU="U", teacher_selected="", shift_selected="", level_selected="", grade_selected="", section_selected="", subject_selected="", sqlcondition="", sqlconditionLoad, dateAttendance="", deleteCode="";
    FrameLayout fl_part1, fl_part2;
    LinearLayout ll_subject, ll_in_charge, ll_head_studentList, ll_question_3;
    DatePicker dp_attandance;
    int present, absence, ts_present=0, ts_absence=0;
    TextView text1, text2, text3, text4, text5;

    long date = System.currentTimeMillis();  // sistem date
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy  h:mm a");
    SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-MM-dd");
    public int school_year = calendar.get(Calendar.YEAR);





//    Date today;
    //String currentDateandTime = new SimpleDateFormat("MM-dd-yyyy").format();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_classroom_evaluation);

        lista = (ScrollView) findViewById(R.id.sv_1);
        this.sp_teacher = (Spinner) findViewById(R.id.sp_teacher);
        this.sp_evaluationType = (Spinner) findViewById(R.id.sp_evaluationType);
        this.sp_reason = (Spinner) findViewById(R.id.sp_reason);
        lv_subject_attendance = (ListView) findViewById(R.id.lv_subject_attendance);
        lv_attendance = (ListView) findViewById(R.id.lv_attendance);
        save_reg = (FloatingActionButton) findViewById(R.id.save_reg);
        date_record = (TextView) findViewById(R.id.date_record);
        btn_confirm = (FloatingActionButton) findViewById(R.id.btn_confirm);
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);
        ll_subject = (LinearLayout) findViewById(R.id.ll_subject);
        ll_head_studentList = (LinearLayout) findViewById(R.id.ll_head_studentList);
        ll_question_3 = (LinearLayout) findViewById(R.id.ll_question_3);
        tv_subject = (TextView) findViewById(R.id.tv_subject);
        tv_note = (TextView) findViewById(R.id.tv_note);
        _col1a = (RadioButton) findViewById(R.id._col1a);
        _col1b = (RadioButton) findViewById(R.id._col1b);
        _col1c = (RadioButton) findViewById(R.id._col1c);
        _col1d = (RadioButton) findViewById(R.id._col1d);
        _col1e = (RadioButton) findViewById(R.id._col1e);
        dp_attandance = (DatePicker) findViewById(R.id.dp_atteandance);

        fl_part1.setVisibility(View.GONE);
        fl_part2.setVisibility(View.VISIBLE);
        save_reg.setVisibility(View.GONE);
        btn_confirm.setVisibility(View.GONE);
        ll_head_studentList.setVisibility(View.GONE);
        ll_question_3.setVisibility(View.GONE);
        lista.setVisibility(View.GONE);

        btn_confirm.setOnClickListener(this);
        save_reg.setOnClickListener(this);
        tv_subject.setOnClickListener(this);
        _col1a.setOnClickListener(this);
        _col1b.setOnClickListener(this);
        _col1c.setOnClickListener(this);
        _col1d.setOnClickListener(this);
        _col1e.setOnClickListener(this);
        dp_attandance.setOnClickListener(this);

        start_array();
        loadSpinner_evaluationType();
        loadSpinner_teacher();
    }

    private void start_array() {
        dp_attandance.setMinDate(DateUtility.setDateDefaul());
        dp_attandance.setMaxDate(System.currentTimeMillis());
        _shift[0] = getResources().getString(R.string.str_g_morning);
        _shift[1] = getResources().getString(R.string.str_g_afternoon);
        _shift[2] = getResources().getString(R.string.str_g_evening);
        _level[0] = getResources().getString(R.string.p);
        _level[1] = getResources().getString(R.string.s);
        _level[2] = getResources().getString(R.string.pp);

        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std1)+"' WHERE level=1 and id=1");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std2)+"' WHERE level=1 and id=2");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std3)+"' WHERE level=1 and id=3");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std4)+"' WHERE level=1 and id=4");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std5)+"' WHERE level=1 and id=5");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std6)+"' WHERE level=1 and id=6");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std7)+"' WHERE level=1 and id=7");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_yeari)+"' WHERE level=3 and id=1");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_yearii)+"' WHERE level=3 and id=3");

        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_reading)+"' WHERE level=1 and id=1");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_writing)+"' WHERE level=1 and id=2");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_arithmetic)+"' WHERE level=1 and id=3");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_healt)+"' WHERE level=1 and id=4");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_games)+"' WHERE level=1 and id=5");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_religion)+"' WHERE level=1 and id=6");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_mathematics)+"' WHERE level=1 and id=7");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_english)+"' WHERE level=1 and id=8");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_science)+"' WHERE level=1 and id=9");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_history)+"' WHERE level=1 and id=10");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_geography)+"' WHERE level=1 and id=11");

        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_all)+"' WHERE level=3 and id=1");

        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_kiswahili)+"' WHERE level=1 and id=12");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_civics)+"' WHERE level=1 and id=13");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_vocational_skills)+"' WHERE level=1 and id=14");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_ict)+"' WHERE level=1 and id=15");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_personality)+"' WHERE level=1 and id=16");

        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_social_studies)+"' WHERE level=1 and id=17");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_civics_and_moral)+"' WHERE level=1 and id=18");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_cience_and_technology)+"' WHERE level=1 and id=19");
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

    private void loadSpinner_evaluationType () {
        _evaluationType = getResources().getStringArray(R.array.test);
        ArrayAdapter adap_et = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, _evaluationType);
        sp_evaluationType.setAdapter(adap_et);
        sp_evaluationType.setOnItemSelectedListener(this);
        sp_evaluationType.setSelection(0);
    }

    private void loadSpinner_teacher(){
        list_code.clear();
        list_teacher.clear();
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT DISTINCT(a.tc), t.surname, t.givenname FROM _ta a  INNER JOIN teacher t ON t._id=a.tc AND (t.exit IS NULL OR t.exit='' OR t.exit='0') GROUP BY a.tc, t.surname, t.surname ORDER BY t.surname", null);
        cur_data.moveToFirst();
        String col_id="0", col_g1=getResources().getString(R.string.str_g_selectone);
        list_teacher.add(col_g1);
        list_code.add(col_id);
        if (cur_data.moveToFirst()) {
            do {
                col_g1 = cur_data.getString(2) + ", " + cur_data.getString(1);
                col_id = cur_data.getString(0);
                list_teacher.add(col_g1);
                list_code.add(col_id);
            } while (cur_data.moveToNext());
        }

        ArrayAdapter adap_list = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_teacher);
        sp_teacher.setAdapter(adap_list);

        sp_teacher.setOnItemSelectedListener(this);
        sp_teacher.setSelected(false);
        lv_subject_attendance.setVisibility(View.VISIBLE);
        ll_question_3.setVisibility(View.GONE);
        ll_head_studentList.setVisibility(View.GONE);
    }


    public void loadSubjectAssigned(String code){
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.clear();
        if (code != "") {
            deleteCode = code;

            Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT DISTINCT(a.tc), a.shift, a.level, a.grade, a.section, a.subject, t.t_s FROM _ta a  INNER JOIN teacher t ON t._id=a.tc WHERE a.tc=" + code, null);
            if (cur_data.moveToFirst()&& code != "") {
                do {
                    map = new HashMap<String, String>();
                    map.put("shift", String.valueOf(_shift[cur_data.getInt(1) - 1]));
                    map.put("level", String.valueOf(_level[cur_data.getInt(2) - 1]));
                    map.put("grade", getGrade(cur_data.getString(2), cur_data.getString(3)));
                    map.put("section", String.valueOf(_section[cur_data.getInt(4) - 1]));
                    map.put("subject", getSubject(cur_data.getString(2), cur_data.getString(5)));
                    mylist.add(map);
                }
                while (cur_data.moveToNext());

                SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.row_list_assing,
                        new String[]{"shift", "level", "grade", "section", "subject"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4, R.id.txt5});
                lv_subject_attendance.setAdapter(mSchedule);

                lv_subject_attendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        text1 = (TextView) view.findViewById(R.id.txt1);
                        text2 = (TextView) view.findViewById(R.id.txt2);
                        text3 = (TextView) view.findViewById(R.id.txt3);
                        text4 = (TextView) view.findViewById(R.id.txt4);
                        text5 = (TextView) view.findViewById(R.id.txt5);

                        texto = " tc=" + String.valueOf(deleteCode) + " AND shift=" +
                                getIndexArray(_shift, text1.getText().toString()) + " AND level=" +
                                getIndexArray(_level, text2.getText().toString()) + " AND  grade=" +
                                getIndexArray(_grade, text3.getText().toString()) + " AND  section=" +
                                getIndexArray(_section, text4.getText().toString()) + " AND subject=" +
                                getIndexArray(_subject_p, text5.getText().toString());


                        teacher_selected = deleteCode;
                        tv_subject.setText(text1.getText().toString() + " - " + text2.getText().toString() + " - " + text3.getText().toString() + " - " + text4.getText().toString() + " - " + text5.getText().toString());

                        ll_subject.setVisibility(View.GONE);
                        sp_evaluationType.setSelection(0);
                        ll_question_3.setVisibility(View.VISIBLE);
                        dp_attandance.setEnabled(false);

                    }
                });
                lv_attendance.setVisibility(View.VISIBLE);
            }
        }
    }


    @SuppressLint("WrongViewCast")
     private void loadStudentAssigned(String code, String shift, String level, String grade, String section, String subject){
        sqlcondition = " emis="+getEMIS_code()+" AND t_id="+code+"  AND shift="+shift+"  AND level="+level+"  AND grade="+grade+"  AND section="+section+"  AND subject="+subject+ "  AND note='"+String.valueOf(sp_evaluationType.getSelectedItemId())+"' AND strftime('%Y',date)='" + school_year + "'"; //+"' AND Date(date)='"+dateAttendance+"'";
        ll_subject.setVisibility(View.GONE);
        tv_subject.setVisibility(View.VISIBLE);
        lista.setVisibility(View.VISIBLE);
        dp_attandance.setEnabled(false);
        shift_selected = shift;
        level_selected = level;
        grade_selected = grade;
        section_selected = section;
        subject_selected = subject;
        Conexion cnSET_Attendance = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET_Attendance = cnSET_Attendance.getReadableDatabase();
        //String sql_studentList = "SELECT DISTINCT(e.s_id),  (ifnull(st.family,\"\")  || \", \" || st.surname) AS fullname, e.points   FROM evaluation   e  " +
        String sql_studentList = "SELECT DISTINCT(CAST((e.s_id) AS INTEGER)) AS _id,  (st.surname || ' ' || st.givenname || ' ' || st.family) AS fullname, e.points   FROM evaluation   e  " +
                "INNER JOIN student st ON (st._id=e.s_id  " +
                " AND (st.reason_exit_1<>1 OR st.reason_exit_1 IS NULL) AND (st.reason_exit_2<>1 OR st.reason_exit_2 IS NULL) AND (st.reason_exit_3<>1 OR st.reason_exit_3 IS NULL) " +
                " AND (st.reason_exit_4<>1 OR st.reason_exit_4 IS NULL) AND (st.reason_exit_5<>1 OR st.reason_exit_5 IS NULL) AND (st.reason_do_1<>1 OR st.reason_do_1 IS NULL) " +
                " AND (st.reason_do_2<>1 OR st.reason_do_2 IS NULL) AND (st.reason_do_3<>1 OR st.reason_do_3 IS NULL) AND (st.reason_do_1<>4 OR st.reason_do_4 IS NULL)) " +
        " WHERE e.emis=" + getEMIS_code() + " AND e.t_id=" + code + "  AND e.shift=" + shift + "  AND e.level=" + level + "  AND e.grade=" + grade + "  AND e.section=" + section + "  AND e.subject=" + subject +
                "  AND e.note='" + String.valueOf(sp_evaluationType.getSelectedItemId()) +"' AND strftime('%Y',date)='" + school_year + "' ORDER BY st.surname"; // + "' AND Date(e.date)='" + dateAttendance + "'";
        Cursor cur_data_attendance = dbSET_Attendance.rawQuery(sql_studentList, null);
        if (cur_data_attendance.getCount()>0) {_IU = "U";
        } else {
            ts_present = cur_data_attendance.getCount(); _IU = "I";
            //sql_studentList = "SELECT  DISTINCT(s.sc), (ifnull(st.family,\"\")  || \", \" || st.surname) AS fullname, '' AS note, s2.subject FROM _sa s \n" +
            sql_studentList = "SELECT  DISTINCT(CAST((s.sc) AS INTEGER)) AS sc, (st.surname || ' ' || st.givenname || ' ' || st.family) AS fullname, '' AS note, s2.subject FROM _sa s \n" +
                    "  INNER JOIN student st ON (st._id=s.sc  " +
                    " AND (st.reason_exit_1<>1 OR st.reason_exit_1 IS NULL) AND (st.reason_exit_2<>1 OR st.reason_exit_2 IS NULL) AND (st.reason_exit_3<>1 OR st.reason_exit_3 IS NULL) " +
                    " AND (st.reason_exit_4<>1 OR st.reason_exit_4 IS NULL) AND (st.reason_exit_5<>1 OR st.reason_exit_5 IS NULL) AND (st.reason_do_1<>1 OR st.reason_do_1 IS NULL) " +
                    " AND (st.reason_do_2<>1 OR st.reason_do_2 IS NULL) AND (st.reason_do_3<>1 OR st.reason_do_3 IS NULL) AND (st.reason_do_1<>4 OR st.reason_do_4 IS NULL)) " +
                    "  INNER JOIN  _ta t ON t.shift=s.shift AND t.level=s.level AND t.grade=s.grade and t.section=s.section \n" +
                    "  INNER JOIN subject s2 ON  s2.level=t.level AND s2.id=t.subject \n" +
                    "  WHERE t.tc=" + code + "  AND s.year_ta=" + school_year + "\n" +
                    " AND t.shift="+shift+" AND t.level="+level+"  AND t.grade="+grade+"   AND t.section="+section+" AND t.subject="+subject+" ORDER BY st.surname";
        }
        if (code != "") {
            ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map = new HashMap<String, String>();
            Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery(sql_studentList, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {
                do {
                    map = new HashMap<String, String>();
                    map.put("code", String.valueOf(cur_data.getInt(0)));
                    map.put("fullname", cur_data.getString(1));
                    map.put("note", cur_data.getString(2));
                    mylist.add(map);
                }
                while (cur_data.moveToNext());


                SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.row_list_assing_evaluation,
                        new String[]{"code", "fullname", "note"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3});
                lv_attendance.setAdapter(mSchedule);

                int tmp_hg = (lv_attendance.getCount() * 90 ) + 150;
                ViewGroup.LayoutParams params = lv_attendance.getLayoutParams();
                params.height = tmp_hg;
                lv_attendance.setLayoutParams(params);
                lv_attendance.setVisibility(View.VISIBLE);
                ll_head_studentList.setVisibility(View.VISIBLE);
            }
            else {
                mylist.clear();
                SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.row_list_assing,
                        new String[]{"code", "fullname", "note"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3});
                lv_attendance.setAdapter(mSchedule);
                toolsfncs.dialogAlertConfirm(this,getResources(),10);
                save_reg.setVisibility(View.GONE);
            }
        }

    }


    public void saveAttendance () {
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        int n=0, nota=0, totall=0;
        String sql = "", delimit="%", tableName="evaluation";
        sql = sql + tableName + delimit;
        sql = sql + dateAttendance + delimit + getEMIS_code() + delimit;
        sql = sql + teacher_selected + delimit;
        sql = sql + shift_selected + delimit;
        sql = sql + level_selected + delimit;
        sql = sql + grade_selected + delimit;
        sql = sql + section_selected + delimit;
        sql = sql + subject_selected + delimit;

        ContentValues reg = new ContentValues();
        for (int i=0; i < lv_attendance.getAdapter().getCount(); i++) {
            String absence="", reason="", testNum="";
            reg.put("emis",getEMIS_code());
            reg.put("t_id", teacher_selected);
            reg.put("shift", shift_selected);
            reg.put("level", level_selected);
            reg.put("grade", grade_selected);
            reg.put("section", section_selected);
            reg.put("subject", subject_selected);
            HashMap<String,String> item = (HashMap<String, String>) lv_attendance.getItemAtPosition(i);
            if (((CheckBox)lv_attendance.getChildAt(i).findViewById(R.id.cb_absence)).isChecked()) {
                absence="1";
                ts_present++;
            }else {
                absence="";
                // ******************* CONTROL DE RAZON DE AUSENCIA *****************************
                ts_absence++;
            }
            reg.put("s_id", item.get("code"));  //sql = sql + item.get("code") + delimit;

            //******* guarda el nombre del test
            reg.put("note", String.valueOf(sp_evaluationType.getSelectedItemId()));          //sql = sql + reason + delimit;
            String str =  ((EditText) lv_attendance.getChildAt(i).findViewById(R.id.txt3)).getText().toString();
            if (!str.isEmpty()) {
                n++;
                nota = nota + Integer.valueOf(str);
            }
            reg.put("points", str);          //sql = sql + reason + delimit;
            reg.put("date", dateAttendance);
            dbSET.insert("evaluation", null, reg);  ;
        }
        toolsfncs.dialogAlertConfirm(this,getResources(),9);
        if (n > 0 & nota >0) {totall = nota / n;}
        sql = sql + String.valueOf(sp_evaluationType.getSelectedItemId()) + delimit + n + delimit + totall + delimit + _IU;
        _IU = "U";
        try {
            // ****************** Fill Bitacora
            ContentValues Bitacora = new ContentValues();
            Bitacora.put("sis_sql", sql);
            if (n > 0 & nota >0) {dbSET.insert("sisupdate", null, Bitacora);  sql = "";}
        } catch (Exception e) {
        }
        n = 0; nota = 0;
        ts_present=0; ts_absence=0;
    }


    public void deleteRecord(String string){
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.delete("evaluation", string, null);
        dbSET.close();
        cnSET.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_teacher:
                if (sp_teacher.getSelectedItem().toString().equals("Select one")) {} else {
                    loadSubjectAssigned(list_code.get(position));
                    ll_subject.setVisibility(View.VISIBLE);
                }
                tv_subject.setVisibility(View.GONE);
                lista.setVisibility(View.GONE);
                save_reg.setVisibility(View.GONE);
                break;
            case R.id.sp_evaluationType:
                if (sp_evaluationType.getSelectedItemPosition() > 0) {
                    setDateAttendance();
                    loadStudentAssigned(deleteCode,
                                String.valueOf(getIndexArray(_shift, text1.getText().toString())),
                                String.valueOf(getIndexArray(_level, text2.getText().toString())),
                            getGradeId(String.valueOf(getIndexArray(_level, text2.getText().toString())), text3.getText().toString()),
                                String.valueOf(getIndexArray(_section, text4.getText().toString())),
                                getSubjectId(String.valueOf(getIndexArray(_level, text2.getText().toString())), text5.getText().toString()));

                    tv_note.setText(sp_evaluationType.getSelectedItem().toString());
                    ll_head_studentList.setVisibility(View.VISIBLE);
                    lv_attendance.setVisibility(View.VISIBLE);
                    lista.setVisibility(View.VISIBLE);
                    save_reg.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getIndexArray(String[] myArray , String myString){
        int index = 0;
        for (int i=0;i<myArray.length;i++){
            if (myArray[i].equals(myString)){
                index = i+1;
            }
        }
        return index;
    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1)); // Importante
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj2));}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_g_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.setNegativeButton(getResources().getString(R.string.str_g_confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {
        deleteRecord(sqlcondition);
        saveAttendance();
        dp_attandance.setEnabled(true);
        lv_attendance.setVisibility(View.GONE);
        ll_head_studentList.setVisibility(View.GONE);
        ll_question_3.setVisibility(View.GONE);
    }
    public void cancelar() {
    }
    // *********** END Control Alerts ************************

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_reg:
                dialogAlert(1);
//                dp_attandance.setEnabled(true);
//                lv_attendance.setVisibility(View.GONE);
//                ll_head_studentList.setVisibility(View.GONE);
//                ll_question_3.setVisibility(View.GONE);
                break;
            case R.id.btn_confirm:
                setDateAttendance();
                fl_part1.setVisibility(View.GONE);
                fl_part2.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.GONE);
                break;
            case R.id.tv_subject:
                ll_subject.setVisibility(View.VISIBLE);
                tv_subject.setVisibility(View.GONE);
                lista.setVisibility(View.GONE);
                ll_head_studentList.setVisibility(View.GONE);
                ll_question_3.setVisibility(View.GONE);
                dp_attandance.setEnabled(false);
                //save_reg.setVisibility(View.GONE);
                break;
            case R.id._col1a:
                break;
            case R.id._col1b:
                break;
            case R.id._col1c:
                break;
            case R.id._col1d:
                break;
            case R.id._col1e:
                break;
        }
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

    public String getGrade(String level, String id){
        String getSubject="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT level, id, grade FROM grade WHERE level='"+level+"' AND id='"+id+"' ORDER BY level, id", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getSubject = cur_data.getString(2);} else {getSubject = "";}
        return getSubject;
    }

    public String getGradeId(String level, String grade){
        String getSubject="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT level, id, grade FROM grade WHERE level='"+level+"' AND grade='"+grade+"' ORDER BY level, id", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getSubject = cur_data.getString(1);} else {getSubject = "";}
        return getSubject;
    }

    public String getSubject(String level, String subject){
        String getSubject="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT level, id, subject FROM subject WHERE level='"+level+"' AND id='"+subject+"' ORDER BY level, id", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getSubject = cur_data.getString(2);} else {getSubject = "";}
        return getSubject;
    }

    public String getSubjectId(String level, String subject){
        String getSubject="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT level, id, subject FROM subject WHERE level='"+level+"' AND subject='"+subject+"' ORDER BY level, id", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getSubject = cur_data.getString(1);} else {getSubject = "";}
        return getSubject;
    }


        public String getLang(){
            String getLang="";
            Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT lang from lang", null);
            cur_data.moveToFirst();
            getLang = cur_data.getString(0);
            return getLang;
        }
}
