package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.adapter.SpinnerItemAdapter;
import com.example.sergio.sistz.data.SpinnerItem;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBSubjectsUtils;
import com.example.sergio.sistz.mysql.DBUtility;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**DBUtility
 * Created by Sergio on 3/17/2016.
 */

// View.OnClickListener, AdapterView.OnItemSelectedListener
public class ReportS extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static String EMIS_code = "";
    public static String TS_code = "", code="", S_report_enable="";
    String[] _shift = {"Morning","Afternoon","Evening"};
    String[] _level = {"Primary","Secondary","Pre-Primary"};
    private String[] _section = {"A","B","C","D","E","F","G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public Spinner sp_shift, sp_level, sp_grade, sp_section, sp_subject,spnYear;
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    ArrayList<String> list_shift = new ArrayList<>();
    ArrayList<String> list_level = new ArrayList<>();
    ArrayList<String> list_grade = new ArrayList<>();
    ArrayList<String> list_subject = new ArrayList<>();
    FrameLayout fl_part2; // ************ FrameLayout ***************
    RadioButton _col3a, _col3b;
    ListView lv_subject, lv_list;
    FloatingActionButton add_reg, save_reg, erase_reg;
    private DBSubjectsUtils conn;
    TextView txt1, txt2, txt3,txtYear;
    CharSequence texto;
    Calendar calendar = Calendar.getInstance();
    public int school_year = calendar.get(Calendar.YEAR);
    ImageButton btn_information;
    TextView no_data_to_show;
    private List<SpinnerItem> spYears;
    private DBUtility connDB;



    private void enableYearDisplay() {
        spnYear.setVisibility(View.VISIBLE);//yyyy
        txtYear.setVisibility(View.VISIBLE);//yyyy
        spYears = connDB.getYears();//yyyy
        spnYear.setAdapter(new SpinnerItemAdapter(this, spYears));//yyyy
        spnYear.setOnItemSelectedListener(this);//yyyy
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TS_code="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_s);

        // ********************** Global vars ******************
        connDB = new DBUtility(this);
        lv_list = (ListView) findViewById(R.id.lv_list);
        sp_shift = (Spinner) findViewById(R.id.sp_shift);
        sp_level = (Spinner) findViewById(R.id.sp_level);
        sp_grade = (Spinner) findViewById(R.id.sp_grade);
        sp_section = (Spinner) findViewById(R.id.sp_section);
        sp_subject = (Spinner) findViewById(R.id.sp_subject);
        no_data_to_show = (TextView) findViewById(R.id.txt_no_data_to_show);
        spnYear = (Spinner) findViewById(R.id.spnYearSelectedS);
        txtYear = (TextView)findViewById(R.id.spnYearSelectedTextS);
        //  ************************ Objects assing *********************
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);
        enableYearDisplay();
        //  ************************ Objects Buttoms *********************

        add_reg = (FloatingActionButton) findViewById(R.id.add_reg);
        save_reg = (FloatingActionButton) findViewById(R.id.save_reg);
        erase_reg = (FloatingActionButton) findViewById(R.id.erase_reg);
        btn_information = (ImageButton) findViewById(R.id.btn_information);

        //************* Start FrameLayout **************************
        fl_part2.setVisibility(View.VISIBLE);

        // **************** CLICK ON BUTTONS ********************
        add_reg.setOnClickListener(this);
        save_reg.setOnClickListener(this);
        erase_reg.setOnClickListener(this);
        btn_information.setOnClickListener(this);

        save_reg.setVisibility(View.GONE);
        erase_reg.setVisibility(View.GONE);
        add_reg.setVisibility(View.GONE);

        // ***************** LOAD INFORMATION *************************
        start_array();
        loadInformation();

    }

    private void loadInformation() {
        //System.out.println("loading information");
        school_year = (((SpinnerItem) spnYear.getSelectedItem()).getId());
        LoadSpinner();
    }

    private void start_array() {
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
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_science) + "' WHERE level=1 and id=9");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_history) + "' WHERE level=1 and id=10");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_geography) + "' WHERE level=1 and id=11");

        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_all) + "' WHERE level=3 and id=1");

        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_kiswahili) + "' WHERE level=1 and id=12");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_civics) + "' WHERE level=1 and id=13");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_vocational_skills) + "' WHERE level=1 and id=14");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_ict)+"' WHERE level=1 and id=15");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_personality) + "' WHERE level=1 and id=16");
    }

    private void LoadSpinner(){
        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        dbSET.close();

        ArrayAdapter<String> adap_section = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, _section);
        sp_section.setAdapter(adap_section);

        // ****************** if NEED ONLY read database to do DINAMIC SPINNER ***********
        this.sp_shift.setOnItemSelectedListener(this);
        this.sp_level.setOnItemSelectedListener(this);
        this.sp_grade.setOnItemSelectedListener(this);
        this.sp_section.setOnItemSelectedListener(this);

    }


// ***************** Load Teacher/Staff LIST *************************
    public void loadListTeacher(String sql) {
        school_year = (((SpinnerItem) spnYear.getSelectedItem()).getId());
        System.out.println(school_year);
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String query = "SELECT DISTINCT(s._id), s.surname, s.givenname, s.family FROM student  AS s " +
                "INNER JOIN _sa  AS sa ON sa.sc=s._id " + sql + " AND year_ta=" + school_year + " " +
                "GROUP BY s._id, s.surname, s.givenname, s.family ORDER BY s.family, s.surname ";
        System.out.println(query);
        Cursor cur_data = dbSET.rawQuery(query, null);
        String col_id, col_g1="";
        cur_data.moveToFirst();
        list_1.clear();
        list_code.clear();
        String surName="", givenName="", famName="";
        if (cur_data.moveToFirst()) {
            no_data_to_show.setVisibility(View.GONE);
            do {
                if (cur_data.getString(1) != null) {surName = cur_data.getString(1);} else {surName="";}
                if (cur_data.getString(3) != null) {famName = cur_data.getString(3);} else {famName="";}
                if (cur_data.getString(2) != null) {givenName = cur_data.getString(2);} else {givenName="";}
                col_g1 = famName + ", " + surName + " " + givenName;
                col_id = cur_data.getString(0);
                list_1.add(col_g1);
                list_code.add(col_id);
            } while (cur_data.moveToNext());

            ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, list_1);
            lv_list.setAdapter(adap_list);

            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                    S_report_enable="1";
                    code = list_code.get(posicion);
                    SettingsMenuStudent.TS_code = list_code.get(posicion);
                    Intent intent1 = new Intent(ReportS.this, SettingsMenuStudent_menu.class);
                    startActivity(intent1);
                }
            });
        } else {
            no_data_to_show.setVisibility(View.VISIBLE);
            ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, list_1);
            lv_list.setAdapter(adap_list);
        }
        lv_list.refreshDrawableState();
    }



    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                break;
            case R.id.btn_information:
                dialogAlert(1);
                break;
        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.report_information));
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.report_0_information));}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}

        dialogo1.setCancelable(false);
        dialogo1.setNegativeButton(getResources().getString(R.string.str_g_close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {
    }
    public void cancelar() {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_shift:
                fill_sp_level(sp_shift.getSelectedItem().toString());
                break;
            case R.id.sp_level:
                fill_sp_grade(sp_level.getSelectedItem().toString());
                break;
            case R.id.sp_grade:
                loadListTeacher(" AND shift=" + getIndexArray(_shift,sp_shift.getSelectedItem().toString()) +
                                " AND  level=" + getIndexArray(_level, sp_level.getSelectedItem().toString()) +
                                " AND grade=" + String.valueOf(sp_grade.getSelectedItemId() + 1) +
                                " AND section=" + String.valueOf(sp_section.getSelectedItemId() + 1)
                                );
                break;
            case R.id.sp_section:
                // ************* 20180525 validar que se pueda ejecutar al llenar los datos de grado, DA ERROR si no hay datos
                loadListTeacher(" AND shift=" + getIndexArray(_shift,sp_shift.getSelectedItem().toString()) +
                                " AND  level=" + getIndexArray(_level, sp_level.getSelectedItem().toString()) +
                                " AND grade=" + String.valueOf(sp_grade.getSelectedItemId() + 1) +
                                " AND section=" + String.valueOf(sp_section.getSelectedItemId() + 1)
                );
                break;
            case R.id.spnYearSelectedS:
                loadInformation();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // *********** END Control Alerts ************************
    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
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

    // *************************** FILL SPINERR SHIFT -  LEVEL  -   GRADE  -  SECTION  - SUBJECT - **************
    private void fill_sp_level(String shift) {
        list_level.clear();
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        dbSET.close();
    }

        private void fill_sp_grade(String level){
        list_grade.clear();
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        dbSET.close();
    }

    private void fill_sp_subject(String level, String grade){
        list_subject.clear();
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_subject;
        String complementSQL = "";
        if (grade.equals(getResources().getString(R.string.str_g_std1)) || grade.equals(getResources().getString(R.string.str_g_std2)) ) {complementSQL=" AND id IN (1,2,3,4,5,6)";}
        else if (grade.equals(getResources().getString(R.string.str_g_std3)) || grade.equals(getResources().getString(R.string.str_g_std4)) || grade.equals(getResources().getString(R.string.str_g_std5)) || grade.equals(getResources().getString(R.string.str_g_std6)) || grade.equals(getResources().getString(R.string.str_g_std7)) ) {complementSQL=" AND id IN (6,7,8,9,10,11,12,13,14,15,16)";}
        else {complementSQL="";}
        String sql = "SELECT id, subject FROM subject WHERE level="+ getIndexArray(_level, level ) + complementSQL;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_subject = cur_data.getString(1);
                list_subject.add(col_subject);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_subject);
        sp_subject.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();
    }


    private String getGradeBDD(int level, int id){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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

    private String getSubjectBDD(int level, int id){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String result;
        String sql = "SELECT subject FROM subject WHERE level="+ level+" AND id="+id;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        if (cur_data.getCount()>0 ) {
            cur_data.moveToFirst();
            result =  cur_data.getString(0);
        } else {result =  "PP";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    private int findGradeBDD(int level, String grade){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        int result;
        String sql = "SELECT id FROM grade WHERE level="+ level+" AND grade='"+grade+"'";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        result =  cur_data.getInt(0);
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    private int findSubjectBDD(int level, String subject){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        int result;
        String sql = "SELECT id FROM subject WHERE level="+ level+" AND subject='"+subject+"'";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        if (cur_data.getCount()>0 ) {
            cur_data.moveToFirst();
            result =  cur_data.getInt(0);
        } else {result = 0;}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }
}
