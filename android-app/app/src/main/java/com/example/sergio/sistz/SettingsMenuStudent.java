package com.example.sergio.sistz;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.adapter.TeacherStudentListAdapter;
import com.example.sergio.sistz.data.TeacherStudentItem;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by Sergio on 3/13/2016.
 */
public class SettingsMenuStudent extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static String EMIS_code = "";
    public static String TS_code = "", code="";
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    String[] _shift = {"Morning","Afternoon","Evening"};
    String[] _level = {"Primary","Secondary","Pre-Primary"};
    private String[] _section = {"A","B","C","D","E","F","G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public Spinner sp_shift, sp_level, sp_grade, sp_section;  // ****** 2018/FEB/06 Se ha agregado los spinner de seleccion por grado y aula
    ArrayList<String> list_shift = new ArrayList<>();
    ArrayList<String> list_level = new ArrayList<>();
    ArrayList<String> list_grade = new ArrayList<>();
    String filter_shift="1", filter_level="1", filter_grade="1", filter_section="1";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    ListView lv_list, lv_unassign;
    FloatingActionButton add_reg, find_reg, erase_reg, sendEnrollment;
    EditText et_find_reg;
    ImageButton ib_find_reg;
    View cv1_enrollment, cv2_enrollment;
    TextView no_data_to_show;
    Space space;
    ProgressDialog progress;
    String delimit="%";
    private int totalProgressTime = 0;
    private Handler handler = new Handler();
    // *******************  School year definition *********************
    Calendar c = Calendar.getInstance();
    public int school_year = c.get(Calendar.YEAR);
    public int actual_year = c.get(Calendar.YEAR);
    String dropt_out_reason_student = " AND (s.reason_exit_1 IS NULL OR s.reason_exit_1=0) AND s.reason_exit_2=0 AND s.reason_exit_3=0 AND s.reason_exit_4=0 AND s.reason_exit_5=0 AND s.reason_do_1=0 " +
            "AND s.reason_do_2=0 AND s.reason_do_3=0 AND s.reason_do_4=0";
    List<TeacherStudentItem>   listUnassign = new ArrayList<>();
    TextView no_data_show;
    Button btn_close_unassign;
    Handler handlerData;
    ProgressDialog dialogCarga;


    @Override
    protected void onRestart() {
        super.onRestart();
        //loadListStudentUnassign();
        loadListTeacher("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListTeacher("");
        TS_code="";


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu_student);

        // ********************** Global vars ******************
        lv_list = (ListView) findViewById(R.id.lv_list);
        lv_unassign = (ListView) findViewById(R.id.lv_unassign);
        et_find_reg = (EditText) findViewById(R.id.et_find_reg);
        ib_find_reg = (ImageButton) findViewById(R.id.ib_find_reg);
        cv1_enrollment = (View) findViewById(R.id.cv1_enrollment);
        cv2_enrollment = (View) findViewById(R.id.cv2_enrollment);
        no_data_to_show = (TextView) findViewById(R.id.txt_no_data_to_show);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);

        //  ************************ 2018/FEB/06 seleccion de alumnos por grado. *********************
        sp_shift = (Spinner) findViewById(R.id.sp_shift);
        sp_level = (Spinner) findViewById(R.id.sp_level);
        sp_grade = (Spinner) findViewById(R.id.sp_grade);
        sp_section = (Spinner) findViewById(R.id.sp_section);

        //  ************************ Objects Buttoms *********************
        sendEnrollment = (FloatingActionButton) findViewById(R.id.sendEnrollment);
        add_reg = (FloatingActionButton) findViewById(R.id.add_reg);
        find_reg = (FloatingActionButton) findViewById(R.id.find_reg);
        btn_close_unassign = (Button) findViewById(R.id.btn_close_unassign);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.GONE);
        fl_part2.setVisibility(View.VISIBLE);
        no_data_show = (TextView) findViewById(R.id.txt_no_data_to_show);

        // **************** CLICK ON BUTTONS ********************
        sendEnrollment.setOnClickListener(this);
        add_reg.setOnClickListener(this);
        add_reg.setVisibility(View.VISIBLE);
        find_reg.setOnClickListener(this);
        ib_find_reg.setOnClickListener(this);
        btn_close_unassign.setOnClickListener(this);
        // ***************** LOCAD INFORMATION *************************

        actual_year = getMaxSchoolYear();

        //processToStudentDuplicate(); // Implemented 20190708

        start_array();
        LoadSpinner();

        loadListStudentUnassign();
        loadListTeacher("");

    }

    private void start_array() {
        _shift[0] = getResources().getString(R.string.str_g_morning);
        _shift[1] = getResources().getString(R.string.str_g_afternoon);
        _shift[2] = getResources().getString(R.string.str_g_evening);
        _level[0] = getResources().getString(R.string.p);
        _level[1] = getResources().getString(R.string.s);
        _level[2] = getResources().getString(R.string.pp);

        // ***************** Load Subject  if read DATABASE recordSet ****************************
        /*
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
        */
    }

    // ***************** Load Student LIST *************************


    public void loadListStudentUnassign() {
        dialogCarga = new ProgressDialog(this);
        dialogCarga.setMessage(getResources().getString(R.string.str_loadin_student_unassign));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                DBUtility db = new DBUtility(SettingsMenuStudent.this);
                listUnassign = db.getStudentList();
                handlerData.sendEmptyMessage(0);
            }
        }.start();

     handlerData = new Handler() {
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             dialogCarga.dismiss();
             if (listUnassign != null) {
                 no_data_show.setVisibility(View.GONE);
                 TeacherStudentListAdapter adap_list = new TeacherStudentListAdapter(SettingsMenuStudent.this, R.layout.row_teacher_student_list_, listUnassign);
                 lv_unassign.setAdapter(adap_list);
                 lv_unassign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                         ReportS.S_report_enable="";
                         TeacherStudentItem item = listUnassign.get(posicion);
                         TS_code = item.getId();
                         Intent intent1 = new Intent(SettingsMenuStudent.this, SettingsMenuStudent_menu.class);
                         startActivity(intent1);
                         //loadListStudentUnassign();
                     }
                 });
                 lv_unassign.refreshDrawableState();
             } else {
                 //no_data_show.setVisibility(View.VISIBLE);
                 lv_unassign.clearChoices();
                 lv_unassign.refreshDrawableState();
                 fl_part2.setVisibility(View.GONE);
                 fl_part1.setVisibility(View.VISIBLE);
             }
         }
         };

    }


    public void loadListTeacher(String parametros) {
        String sql_listener="";
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        if (parametros.isEmpty()){
            sql_listener = "SELECT DISTINCT(s._id), s.surname, s.givenname, s.family FROM student AS s " +
                    "INNER JOIN  _sa AS sa ON (s._id=sa.sc AND sa.shift='" + filter_shift +  "' AND sa.level='" + filter_level + "' AND sa.grade='" + filter_grade + "' AND sa.section='" + filter_section + "' AND sa.year_ta="+ actual_year+") " +
                    "ORDER BY s.surname";
            }
        else
        {
            sql_listener ="SELECT DISTINCT(_id), surname, givenname, family FROM student where _id like '%" + parametros +"%' or surname like '%"+parametros +"%' or family like '%"+parametros+"%' or givenname like'%"+parametros+"%' ORDER BY surname";
        }
        Cursor cur_data = dbSET.rawQuery(sql_listener, null);

        String col_id, col_g1="";
        cur_data.moveToFirst();
        list_1.clear();
        list_code.clear();
        String givenName = "",midleName="",famName = "" ;
        if (cur_data.moveToFirst()) {
            no_data_to_show.setVisibility(View.GONE);
            do {
                if (cur_data.getString(1) != null) {givenName = cur_data.getString(1);} else {givenName="";}
                if (cur_data.getString(2) != null) {midleName = cur_data.getString(2);} else {midleName="";}
                if (cur_data.getString(3) != null) {famName = cur_data.getString(3);} else {famName="";}
                //col_g1 = famName + ", " + givenName + " " + midleName;
                col_g1 = givenName + " " + midleName + " " +  famName;
                col_id = cur_data.getString(0);
                list_1.add(col_g1);
                list_code.add(col_id);
            } while (cur_data.moveToNext());

            lv_list.clearChoices();
            lv_list.refreshDrawableState();

            ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, list_1);
            lv_list.setAdapter(adap_list);

            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                   ReportS.S_report_enable="";
                    code = list_code.get(posicion);
                    TS_code = list_code.get(posicion);
                    Intent intent1 = new Intent(SettingsMenuStudent.this, SettingsMenuStudent_menu.class);
                    startActivity(intent1);
                }
            });
        } else{
            no_data_to_show.setVisibility(View.VISIBLE);
            list_1.clear();
            list_code.clear();
            lv_list.clearChoices();
            lv_list.refreshDrawableState();
            ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, list_1);
            lv_list.setAdapter(adap_list);
        }
        dbSET.close();
        cnSET.close();
    }


    // **************** NEWENTRANT BY SHIFT-LEVEL-GRADE-AGE-SEX ********************
    public void sendNewEntrant() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "select c.*, d.total as male, e.total as female  from \n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select sa.sc, sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s\n" +
                "inner join _sa as sa on s._id=sa.sc and s.pre_sch_att=1 and new_entrant=1 and grade =1 and year_ta=" + school_year + dropt_out_reason_student + ") as a\n" +
                "group by shift,level, grade,studentage) as c\n" +
                "left join\n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select sa.sc, sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s inner join _sa as sa on s._id=sa.sc and s.pre_sch_att=1 and new_entrant=1 and grade =1 and year_ta=" + school_year + dropt_out_reason_student + ") as a where sex = 1\n" +
                "group by shift,level, grade,studentage) as d on c.shift=d.shift and c.level=d.level and c.grade=d.grade and c.studentage=d.studentage\n" +
                "left join\n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select sa.sc, sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s inner join _sa as sa on s._id=sa.sc and s.pre_sch_att=1 and new_entrant=1 and grade =1 and year_ta=" + school_year + dropt_out_reason_student + ") as a where sex = 2\n" +
                "group by shift,level, grade,studentage) as e on c.shift=e.shift and c.level=e.level and c.grade=e.grade and c.studentage=e.studentage";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        String sql_tmep="",sql_tmep1="", recDelete = "0";
        if (cur_data.moveToFirst()) {
            do {
                sql_tmep = "newentrant" + delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + cur_data.getString(0) + delimit + cur_data.getString(1) + delimit + cur_data.getString(2) + delimit + cur_data.getString(3) + delimit + cur_data.getString(4) + delimit + cur_data.getString(5) + delimit + cur_data.getString(6) + delimit + "I";
                if (recDelete.equals("0")) {sql_tmep1 = "newentrant" + delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "D";}
                ContentValues Bitacora = new ContentValues();
                if (recDelete.equals("0")) {
                    Bitacora.put("sis_sql", sql_tmep1); dbSET.insert("sisupdate", null, Bitacora);
                    Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);
                    recDelete = "1";
                } else {Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);}
            } while (cur_data.moveToNext());
        }
        dbSET.close();
        cnSET.close();
    }

    /* ***************************** Implemented 20190707
        PROCES TO ERASE RECORD IN STUDENT DUCPLICATES
     */
    public void processToStudentDuplicate(){
        if (studentDuplicate()) {eraseDuplicateRecordInStudent();}
    }
    public boolean studentDuplicate(){
        boolean studentDuplicateYN=true;
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql ="SELECT * FROM student \n" +
                "WHERE _id IN (SELECT _id FROM \n" +
                "(SELECT _id, COUNT(*) AS reg FROM student GROUP BY _id)\n" +
                "WHERE reg>1)\n" +
                "ORDER BY _id ;\n";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        if (cur_data.getCount()<=0) {studentDuplicateYN=false;}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return studentDuplicateYN;
    }
    public void eraseDuplicateRecordInStudent() {
        Boolean TableExists=false;
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getWritableDatabase();
        String sql ="CREATE TABLE  student_" + school_year+c.get(Calendar.MONTH)+c.get(Calendar.DAY_OF_MONTH) + " AS SELECT * FROM student;";
        String sqlTableExist ="SELECT * student_" + school_year+c.get(Calendar.MONTH)+c.get(Calendar.DAY_OF_MONTH);
        try {
            Cursor cur_tableExist = dbSET.rawQuery(sqlTableExist, null);
            cur_tableExist.close();
            TableExists=true;
        } catch (SQLException xp) {
            TableExists=false;
        }
        if (!TableExists) {
            Cursor cur_data = dbSET.rawQuery(sql, null);
            cur_data.close();
        }
        dbSET.close();
        cnSET.close();
    }

    // **************** ENROLLMENT BY SHIFT-LEVEL-GRADE-AGE-SEX ********************
    public void sendEnrollment() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "select c.*,(CASE WHEN d.total IS NULL THEN 0 ELSE d.total END) as male, (CASE WHEN e.total IS NULL THEN 0 ELSE e.total END)as female  from (\n" +
                "select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select DISTINCT(s._id), sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s\n" +
                "inner join _sa as sa on s._id=sa.sc and year_ta=" + school_year + dropt_out_reason_student + ") as a \n" +
                "group by shift,level, grade,studentage) as c\n" +
                "left join\n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select DISTINCT(s._id), sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s\n" +
                "inner join _sa as sa on s._id=sa.sc and year_ta=" + school_year + dropt_out_reason_student + ") as a\n" +
                "where sex = 1\n" +
                "group by shift,level, grade,studentage) as d on c.shift=d.shift and c.level=d.level and c.grade=d.grade and c.studentage=d.studentage\n" +
                "left join\n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select DISTINCT(s._id), sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s\n" +
                "inner join _sa as sa on s._id=sa.sc and year_ta=" + school_year + dropt_out_reason_student + ") as a\n" +
                "where sex = 2\n" +
                "group by shift,level, grade,studentage) as e on c.shift=e.shift and c.level=e.level and c.grade=e.grade and c.studentage=e.studentage";

        sql = "SELECT a.shift, a.level, a.grade, studentage, SUM(M+F) AS total,  SUM(M) AS male, SUM(F) AS female FROM  (\n" +
                " SELECT  DISTINCT(s._id), sa.shift, sa.level, sa.grade, (Date() - Date(s.yearob)) as studentage,\n" +
                " (CASE WHEN s.sex = 1 THEN 1 ELSE 0 END) M, (CASE WHEN s.sex = 2 THEN 1 ELSE 0 END) F FROM student s  \n" +
                "  JOIN _sa sa ON (sa.sc=s._id AND sa.year_ta=" + school_year + " AND (sa.level=1))  \n" +
                " WHERE (s.reason_exit_1=0 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2=0 OR s.reason_exit_2 IS NULL)  AND (s.reason_exit_3=0 OR s.reason_exit_3 IS NULL)  \n" +
                " AND (s.reason_exit_4=0 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5=0 OR s.reason_exit_5 IS NULL)  AND (s.reason_do_1=0 OR s.reason_do_1 IS NULL) \n" +
                " AND (s.reason_do_2=0 OR s.reason_do_2 IS NULL) AND (s.reason_do_3=0 OR s.reason_do_3 IS NULL) AND (s.reason_do_4=0 OR s.reason_do_4 IS NULL)  GROUP BY  s._id) AS a  \n" +
                " GROUP BY a.shift, a.level, a.grade, studentage  \n" +
                " union\n" +
                "SELECT a.shift, a.level, a.grade, studentage, SUM(M+F) AS total,  SUM(M) AS male, SUM(F) AS female FROM  (\n" +
                " SELECT  DISTINCT(s._id), sa.shift, sa.level, sa.grade, (Date() - Date(s.yearob)) as studentage,\n" +
                " (CASE WHEN s.sex = 1 THEN 1 ELSE 0 END) M, (CASE WHEN s.sex = 2 THEN 1 ELSE 0 END) F FROM student s  \n" +
                "  JOIN _sa sa ON (sa.sc=s._id AND sa.year_ta=" + school_year + " AND (sa.level=3))  \n" +
                " WHERE (s.reason_exit_1=0 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2=0 OR s.reason_exit_2 IS NULL)  AND (s.reason_exit_3=0 OR s.reason_exit_3 IS NULL)  \n" +
                " AND (s.reason_exit_4=0 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5=0 OR s.reason_exit_5 IS NULL)  AND (s.reason_do_1=0 OR s.reason_do_1 IS NULL) \n" +
                " AND (s.reason_do_2=0 OR s.reason_do_2 IS NULL) AND (s.reason_do_3=0 OR s.reason_do_3 IS NULL) AND (s.reason_do_4=0 OR s.reason_do_4 IS NULL)  GROUP BY  s._id) AS a  \n" +
                " GROUP BY a.shift, a.level, a.grade, studentage  ORDER BY a.shift, a.level, a.grade, studentage; ";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        String sql_tmep="",sql_tmep1="", recDelete = "0";
        if (cur_data.moveToFirst()) {
            do {
                sql_tmep = "enrollment" + delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + cur_data.getString(0) + delimit + cur_data.getString(1) + delimit + cur_data.getString(2) + delimit + cur_data.getString(3) + delimit + cur_data.getString(4) + delimit + cur_data.getString(5) + delimit + cur_data.getString(6) + delimit + "I";
                if (recDelete.equals("0")) {sql_tmep1 = "enrollment" + delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "D";}
                ContentValues Bitacora = new ContentValues();
                if (recDelete.equals("0")) {
                    Bitacora.put("sis_sql", sql_tmep1); dbSET.insert("sisupdate", null, Bitacora);
                    Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);
                    recDelete = "1";
                } else {Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);}
            } while (cur_data.moveToNext());
        }
        dbSET.close();
        cnSET.close();
    }

    // **************** REPEATERS BY SHIFT-LEVEL-GRADE-AGE-SEX ********************
    public void sendRepeaters() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "select c.*,d.total as male, e.total as female  from \n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select sa.sc, sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s\n" +
                "inner join _sa as sa on s._id=sa.sc and repeater=1 and year_ta=" + school_year + dropt_out_reason_student + " ) as a\n" +
                "group by shift,level, grade,studentage) as c\n" +
                "left join\n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select sa.sc, sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s inner join _sa as sa on s._id=sa.sc and sa.repeater=1 and year_ta=" + school_year + dropt_out_reason_student + ") as a where sex = 1\n" +
                "group by shift,level, grade,studentage) as d on c.shift=d.shift and c.level=d.level and c.grade=d.grade and c.studentage=d.studentage \n" +
                "left join\n" +
                "(select a.shift, a.level,a.grade,  studentage,count(*)as total from\n" +
                "(select sa.sc, sa.shift, sa.level, sa.grade, (Date() - Date(yearob)) as studentage, sex from student as s inner join _sa as sa on s._id=sa.sc and sa.repeater=1 and year_ta=" + school_year + dropt_out_reason_student + ") as a where sex = 2\n" +
                "group by shift,level, grade,studentage) as e on c.shift=e.shift and c.level=e.level and c.grade=e.grade and c.studentage=e.studentage;";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        String sql_tmep="",sql_tmep1="", recDelete = "0";
        if (cur_data.moveToFirst()) {
            do {
                sql_tmep = "repeater" + delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + cur_data.getString(0) + delimit + cur_data.getString(1) + delimit + cur_data.getString(2) + delimit + cur_data.getString(3) + delimit + cur_data.getString(4) + delimit + cur_data.getString(5) + delimit + cur_data.getString(6) + delimit+  "I";
                if (recDelete.equals("0")) {sql_tmep1 = "repeater" + delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "D";}
                ContentValues Bitacora = new ContentValues();
                if (recDelete.equals("0")) {
                    Bitacora.put("sis_sql", sql_tmep1); dbSET.insert("sisupdate", null, Bitacora);
                    Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);
                    recDelete = "1";
                } else {Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);}
            } while (cur_data.moveToNext());
        }
        dbSET.close();
        cnSET.close();
    }

    // **************** DISABILITY BY SHIFT-LEVEL-GRADE-AGE-SEX ********************
    public void sendDisability() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "select c.*, d.total as vision , e.total as hearing , f.total as phisical , g.handicap  \n" +
                "  from (\n" +
                " select a.shift, a.level,a.grade,  a.sex, count(*)as total from (\n" +
                " select sa.sc, sa.shift, sa.level, sa.grade, sex from student as s\n" +
                " inner join _sa as sa on s._id=sa.sc and (s.disability in(1,2,3) or s.handicap<>'') and year_ta=" + school_year + dropt_out_reason_student + ") as a \n" +
                " group by shift,level, grade, sex ) as c\n" +
                "left join\n" +
                "   (select shift, level, grade,  sex,  count(*)as total from\n" +
                "   (select sa.sc, sa.shift, sa.level, sa.grade, sex, disability  from student as s inner join _sa as sa on s._id=sa.sc and s.disability=1 and year_ta=" + school_year + dropt_out_reason_student + ") as a \n" +
                "   group by shift,level, grade, sex, disability) as d on c.shift=d.shift and c.level=d.level and c.grade=d.grade and c.sex=d.sex\n" +
                " left join\n" +
                "    (select shift, level, grade,  sex,  count(*)as total from\n" +
                "     (select sa.sc, sa.shift, sa.level, sa.grade, sex, disability  from student as s inner join _sa as sa on s._id=sa.sc and s.disability=2 and year_ta=" + school_year + dropt_out_reason_student + ") as a \n" +
                "   group by shift,level, grade, sex, disability) as e on c.shift=e.shift and c.level=e.level and c.grade=e.grade and c.sex=e.sex\n" +
                " left join\n" +
                "   (select shift, level, grade,  sex,  count(*)as total from\n" +
                "   (select sa.sc, sa.shift, sa.level, sa.grade, sex, disability  from student as s inner join _sa as sa on s._id=sa.sc and s.disability=3 and year_ta=" + school_year + dropt_out_reason_student + ") as a \n" +
                "   group by shift,level, grade, sex, disability) as f on c.shift=f.shift and c.level=f.level and c.grade=f.grade and c.sex=f.sex\n" +
                " left join\n" +
                "   (select shift, level, grade,  sex, handicap from\n" +
                "   (select sa.sc, sa.shift, sa.level, sa.grade, sex, s.handicap  from student as s inner join _sa as sa on s._id=sa.sc and s.handicap<>'' and year_ta=" + school_year + dropt_out_reason_student + " ) as a \n" +
                "   group by shift,level, grade, sex, handicap) as g on c.shift=g.shift and c.level=g.level and c.grade=g.grade and c.sex=g.sex";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        String sql_tmep="",sql_tmep1="", recDelete = "0";
        if (cur_data.moveToFirst()) {
            do {
                sql_tmep = "disability"+ delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + cur_data.getString(0) + delimit + cur_data.getString(1) + delimit + cur_data.getString(2) + delimit + cur_data.getString(3) + delimit + cur_data.getString(4) + delimit + cur_data.getString(5) + delimit + cur_data.getString(6) + delimit + cur_data.getString(7)+ delimit + cur_data.getString(8) + delimit + "I";
                if (recDelete.equals("0")) {sql_tmep1 = "disability"+ delimit + String.valueOf(school_year) + delimit + getEMIS_code() + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0" + delimit + "0"+ delimit + "0"+ delimit + "0" + delimit + "D";}
                ContentValues Bitacora = new ContentValues();
                if (recDelete.equals("0")) {
                    Bitacora.put("sis_sql", sql_tmep1); dbSET.insert("sisupdate", null, Bitacora);
                    Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);
                    recDelete = "1";
                } else {Bitacora.put("sis_sql", sql_tmep); dbSET.insert("sisupdate", null, Bitacora);}
            } while (cur_data.moveToNext());
        }
        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                TS_code="";
                Intent intent1 = new Intent(SettingsMenuStudent.this, SettingsMenuStudent_menu.class);
                startActivity(intent1);
                loadListTeacher("");
                break;
            case R.id.find_reg:
                cv1_enrollment.setVisibility(View.GONE);
                cv2_enrollment.setVisibility(View.GONE);
                sendEnrollment.setVisibility(View.GONE);
                et_find_reg.setVisibility(View.VISIBLE);
                ib_find_reg.setVisibility(View.VISIBLE);
                find_reg.setVisibility(View.GONE);
                break;
            case R.id.ib_find_reg:
                et_find_reg.setVisibility(View.GONE);
                ib_find_reg.setVisibility(View.GONE);
                cv1_enrollment.setVisibility(View.VISIBLE);
                cv2_enrollment.setVisibility(View.VISIBLE);
                sendEnrollment.setVisibility(View.VISIBLE);
                loadListTeacher(et_find_reg.getText().toString());
                et_find_reg.setText("");
                find_reg.setVisibility(View.VISIBLE);
                break;
            case R.id.sendEnrollment:
                dialogAlert(6);
                break;
            case R.id.btn_close_unassign:
                fl_part2.setVisibility(View.GONE);
                fl_part1.setVisibility(View.VISIBLE);
                break;
        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Save and Exit !!!");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}
        if (v == 6){dialogo1.setMessage("Are you sure you want to send?");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {
        //sendEnrollment();
        progressBarEnrollment();
    }

    public void cancelar() {}


    public void progressBarEnrollment(){
        progress=new ProgressDialog(this);
        progress.setMessage("Sending enrollment ...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;

        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;
                sendNewEntrant();
                sendEnrollment();
                sendRepeaters();
                sendDisability();
                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 20;
                        progress.setProgress(jumpTime);
                    }
                    catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (jumpTime==100){
                        progress.dismiss();
                    }
                }
            }
        };
        t.start();

    }
    // *********** END Control Alerts ************************


    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        dbSET.close();
        cnSET.close();
        return getemiscode;
    }

    public int findRecord(String string){
        int getFindRecord=0;
        try {
            Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT * FROM student WHERE _id like '%" + string+"'% or surname like '%"+string +"%' or family like '%"+string+"%' or givenname like'%"+string+"%'", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getFindRecord = cur_data.getCount();} else {getFindRecord = 0;}
            dbSET.close();
            cnSET.close();
        } catch (Exception e) {}
        return getFindRecord;
    }


    // **************************** 2018/FEB/06 control de spinner ******************
    private void LoadSpinner(){
        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_shift;
        String sql = "SELECT  shift\n" +
                "FROM \n" +
                "(SELECT 1 AS id, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 1 AS id, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 1 AS id, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"'  ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS shift, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT  2 AS id, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 2 AS id, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 2 AS id, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT  3 AS id, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 3 AS id, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 3 AS id, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0)\n" +
                "WHERE shift <>'"+getResources().getString(R.string.str_g_selectone)+"'  \n" +
                "GROUP BY shift\n" +
                " ORDER BY id";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_shift = cur_data.getString(0);
                list_shift.add(col_shift);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_shift);
        sp_shift.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        cnSET.close();

        ArrayAdapter<String> adap_section = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, _section);
        sp_section.setAdapter(adap_section);

        this.sp_shift.setOnItemSelectedListener(this);
        this.sp_level.setOnItemSelectedListener(this);
        this.sp_grade.setOnItemSelectedListener(this);
        this.sp_section.setOnItemSelectedListener(this);

    }

    // *************************** FILL SPINERR SHIFT -  LEVEL  -   GRADE  -  SECTION  - SUBJECT - **************
    private void fill_sp_level(String shift) {
        list_level.clear();
        Conexion cnSET = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_level,
                sql = "SELECT id, Shift, level\n" +
                        "FROM \n" +
                        "(SELECT 13 AS id, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                        "SELECT 11 AS id, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                        "SELECT 12 AS id, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"'  ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS shift, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                        "SELECT  23 AS id, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                        "SELECT 21 AS id, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                        "SELECT 22 AS id, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                        "SELECT  33 AS id, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                        "SELECT 31 AS id, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                        "SELECT 32 AS id, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0)\n" +
                        "WHERE shift <>'"+getResources().getString(R.string.str_g_selectone)+"'  AND shift='"+shift+"'\n" +
                        "GROUP BY id, shift, level\n" +
                        " ORDER BY id";

        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_level = cur_data.getString(2);
                list_level.add(col_level);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_level);
        sp_level.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    private void fill_sp_grade(String level){
        list_grade.clear();
        Conexion cnSET = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_grade;
        String sql = "SELECT id, grade FROM grade WHERE level="+ getIndexArray(_level, level);
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_grade = cur_data.getString(1);
                list_grade.add(col_grade);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_grade);
        sp_grade.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    private void fill_sp_section(){
    }

    private String getGradeBDD(int level, int id){
        Conexion cnSET = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String result;
        String sql = "SELECT grade FROM grade WHERE level="+ level+" AND id="+id;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        result =  cur_data.getString(0);
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    public int getIndexArray(String[] myArray, String myString){
        int index = 0;
        for (int i=0;i<myArray.length;i++){
            if (myArray[i].equals(myString)){
                index = i+1;
            }
        }
        return index;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_shift:
                fill_sp_level(sp_shift.getSelectedItem().toString());
                fill_sp_grade(sp_level.getSelectedItem().toString());
                break;
            case R.id.sp_level:
                fill_sp_grade(sp_level.getSelectedItem().toString());
                break;
            case R.id.sp_grade:
                break;
        }
        filter_shift= String.valueOf(getIndexArray(_shift , sp_shift.getSelectedItem().toString()));
        filter_level=String.valueOf(getIndexArray(_level , sp_level.getSelectedItem().toString()));
        filter_grade=String.valueOf(sp_grade.getSelectedItemPosition()+1);
        filter_section=String.valueOf(getIndexArray(_section ,sp_section.getSelectedItem().toString()));

       loadListTeacher("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getSection(String _shift, String _level, String _grade){
        int getSectionbdd=0;
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT MAX(section)  FROM _sa  WHERE shift=" + _shift + " AND level=" + _level + " AND grade=" + _grade, null);
        cur_data.moveToFirst();
        if (cur_data.getInt(0) > 0) {
            getSectionbdd= cur_data.getInt(0);
        }
        dbSET.close();
        cnSET.close();
        return getSectionbdd;
    }

    public void fill_section(int cant){
    }

    public int getMaxSchoolYear(){
        int getMaxSchoolYearbdd=0;
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT MAX(year_ta) FROM _sa", null);
        cur_data.moveToFirst();
        if (cur_data.getInt(0) > 0) {
            getMaxSchoolYearbdd = cur_data.getInt(0);
        }
        dbSET.close();
        cnSET.close();
        return getMaxSchoolYearbdd;
    }
}
