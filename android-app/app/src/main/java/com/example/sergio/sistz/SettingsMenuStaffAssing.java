package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBSubjectsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Sergio on 3/17/2016.
 */

// View.OnClickListener, AdapterView.OnItemSelectedListener
public class SettingsMenuStaffAssing extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    String[] _shift = {"Morning","Afternoon","Evening"};
    String[] _level = {"Primary","Secondary","Pre-Primary"};
    private String[] _grade = {"G1","G2","G3","G4","G5","G6","G7","G8"};
    private String[] _section = {"A","B","C","D","E","F","G"};
    private String[] _subject_p = {"Mathematics","English","Kiswahili","French","Science","Geography","Civics","History","Vocational skills","Personality and Sports","ICT","Other"};
    public Spinner sp_shift, sp_level, sp_grade, sp_section, sp_subject;
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    ArrayList<String> list_shift = new ArrayList<>();
    ArrayList<String> list_level = new ArrayList<>();
    ArrayList<String> list_grade = new ArrayList<>();
    ArrayList<String> list_grade_cod = new ArrayList<>();
    ArrayList<String> list_section = new ArrayList<>();
    ArrayList<String> list_subject = new ArrayList<>();
    ArrayList<String> list_subject_cod = new ArrayList<>();
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    EditText _col0, _col1, _col2, _col4, _col5, _col6, _col7;
    RadioButton _col3a, _col3b;
    ListView lv_subject, lv_subject_teacher;
    String _IU="U";
    FloatingActionButton add_reg, save_reg, erase_reg;
    private DBSubjectsUtils conn;
    TextView txt1, txt2, txt3, txt4, txt5;
    private  int t1, t2, t3, t4, t5;
    CharSequence texto;
    Calendar calendar = Calendar.getInstance();
    public int school_year = calendar.get(Calendar.YEAR);


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_staff_assing,
                container, false);

        conn = new DBSubjectsUtils(getContext());
        ArrayList<String> Alist_subject = conn.getSubjects();

        _col1 = (EditText) mLinearLayout.findViewById(R.id._col1);
        _col2 = (EditText) mLinearLayout.findViewById(R.id._col2);
        _col3a = (RadioButton) mLinearLayout.findViewById(R.id._col3a);
        _col3b = (RadioButton) mLinearLayout.findViewById(R.id._col3b);
        _col5 = (EditText) mLinearLayout.findViewById(R.id._col5);
        _col6 = (EditText) mLinearLayout.findViewById(R.id._col6);
        _col7 = (EditText) mLinearLayout.findViewById(R.id._col7);

        txt1 = (TextView) mLinearLayout.findViewById(R.id.txt1);
        txt2 = (TextView) mLinearLayout.findViewById(R.id.txt2);

        lv_subject_teacher = (ListView) mLinearLayout.findViewById(R.id.lv_subject_teacher);

        sp_shift = (Spinner) mLinearLayout.findViewById(R.id.sp_shift);
        sp_level = (Spinner) mLinearLayout.findViewById(R.id.sp_level);
        sp_grade = (Spinner) mLinearLayout.findViewById(R.id.sp_grade);
        sp_section = (Spinner) mLinearLayout.findViewById(R.id.sp_section);
        sp_subject = (Spinner) mLinearLayout.findViewById(R.id.sp_subject);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part2);

        //  ************************ Objects Buttoms *********************

        add_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.add_reg);
        save_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.save_reg);
        erase_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.erase_reg);

        //************* Start FrameLayout **************************
        fl_part2.setVisibility(View.VISIBLE);
        fl_part1.setVisibility(View.GONE);



        // **************** CLICK ON BUTTONS ********************
        add_reg.setOnClickListener(this);
        save_reg.setOnClickListener(this);
        erase_reg.setOnClickListener(this);

        save_reg.setVisibility(View.GONE);
        erase_reg.setVisibility(View.GONE);
        add_reg.setVisibility(View.VISIBLE);

        // ***************** LOCAD INFORMATION *************************
        start_array();
        LoadSpinner(SettingsMenuStaff.TS_code);

        if (ReportTS.TS_report_enable == "1") {
            add_reg.setVisibility(View.GONE);
            lv_subject_teacher.setEnabled(false);
            sp_shift.setEnabled(false);
            sp_level.setEnabled(false);
            sp_grade.setEnabled(false);
            sp_section.setEnabled(false);
            sp_subject.setEnabled(false);
        }

        return mLinearLayout;
    }


    private void start_array() {
        _shift[0] = getResources().getString(R.string.str_g_morning);
        _shift[1] = getResources().getString(R.string.str_g_afternoon);
        _shift[2] = getResources().getString(R.string.str_g_evening);
        _level[0] = getResources().getString(R.string.p);
        _level[1] = getResources().getString(R.string.s);
        _level[2] = getResources().getString(R.string.pp);

        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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

    private void LoadSpinner(String code){
        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_shift);
        sp_shift.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();

        ArrayAdapter<String> adap_section = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, _section);
        sp_section.setAdapter(adap_section);

        // ****************** if NEED ONLY read database to do DINAMIC SPINNER ***********
        this.sp_shift.setOnItemSelectedListener(this);
        this.sp_level.setOnItemSelectedListener(this);
        this.sp_grade.setOnItemSelectedListener(this);
        this.sp_section.setOnItemSelectedListener(this);

        load_lv_subject_assing(code);
    }


    private void load_lv_subject_assing(String code){
        if (code != "") {
            final String deleteCode = code;
            ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map = new HashMap<String, String>();
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT ta.emis, ta.tc, ta.shift, ta.level, ta.grade, ta.section, ta.subject FROM _ta AS ta\n" +
                    "INNER JOIN subject AS S ON (s.level=ta.level AND s.id=ta.subject)\n" +
                    "WHERE ta.tc=" + code  + " ORDER BY ta.rowid DESC, ta.emis, ta.tc, ta.shift, ta.level, ta.grade, ta.section, ta.subject", null);
            if (code == "") {code = SettingsMenuStaff.TS_code;}
            if (cur_data.moveToFirst()&& code != "") {
                do {
                    map = new HashMap<String, String>();

                    map.put("shift", String.valueOf(_shift[cur_data.getInt(2) - 1]));
                    map.put("level", String.valueOf(_level[cur_data.getInt(3) - 1]));
                    map.put("grade", getGradeBDD(cur_data.getInt(3), cur_data.getInt(4)));
                    map.put("section", String.valueOf(_section[cur_data.getInt(5) - 1]));
                    map.put("subject", getSubjectBDD(cur_data.getInt(3), cur_data.getInt(6)));
                    mylist.add(map);
                }
                while (cur_data.moveToNext());

                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), mylist, R.layout.row_list_assing,
                        new String[]{"shift", "level", "grade", "section", "subject"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4, R.id.txt5});
                lv_subject_teacher.setAdapter(mSchedule);

                lv_subject_teacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView text1 = (TextView) view.findViewById(R.id.txt1);
                        TextView text2 = (TextView) view.findViewById(R.id.txt2);
                        TextView text3 = (TextView) view.findViewById(R.id.txt3);
                        TextView text4 = (TextView) view.findViewById(R.id.txt4);
                        TextView text5 = (TextView) view.findViewById(R.id.txt5);

                        texto = " tc=" + String.valueOf(deleteCode) + " AND shift=" +
                                getIndexArray(_shift, text1.getText().toString()) + " AND level=" +
                                getIndexArray(_level, text2.getText().toString()) + " AND  grade=" +
                                findGradeBDD(getIndexArray(_level, text2.getText().toString()), text3.getText().toString()) + " AND  section=" +
                                getIndexArray(_section, text4.getText().toString()) + " AND subject=" +
                                findSubjectBDD(getIndexArray(_level, text2.getText().toString()), text5.getText().toString());
                        dialogAlert(3);

                    }
                });
            }
            else {
                mylist.clear();
                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), mylist, R.layout.row_list_assing,
                        new String[]{"shift", "level", "grade", "section", "subject"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4, R.id.txt5});
                lv_subject_teacher.setAdapter(mSchedule);
            }
        }
    }

    private void update_lv_subject_teacher(String code){
        String sql = "", delimit="%", sqlcondition;
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        t1 = getIndexArray(_shift ,sp_shift.getSelectedItem().toString());
        t2 = getIndexArray(_level, sp_level.getSelectedItem().toString());
        t3 = sp_grade.getSelectedItemPosition()+1;
        t4 = sp_section.getSelectedItemPosition()+1;
        if ((t3==3 || t3==4) && (sp_subject.getSelectedItemPosition()==0 || sp_subject.getSelectedItemPosition()==1) ) {t5 = sp_subject.getSelectedItemPosition()+7;}
        else if ((t3==3 || t3==4) && sp_subject.getSelectedItemPosition()==2  ) {t5 = sp_subject.getSelectedItemPosition()+10;}
        else if ((t3==3 || t3==4) && sp_subject.getSelectedItemPosition()>2  ) {t5 = sp_subject.getSelectedItemPosition()+14;}
        else if (t3==5 || t3==6 || t3==7  ) {t5 = sp_subject.getSelectedItemPosition()+7;}
        else {t5 = sp_subject.getSelectedItemPosition()+1;}
        sqlcondition = " tc=" + code + " AND shift=" + t1 + " AND level=" + t2 + " AND grade=" + t3 + " AND section=" + t4 + " AND subject=" + t5;

        Cursor cur_data = dbSET.rawQuery("SELECT emis, tc, shift, level, grade, section, subject FROM _ta WHERE " + sqlcondition, null);
        cur_data.moveToFirst();
        ContentValues reg = new ContentValues();
        if (cur_data.getCount() > 0) {_IU = "U";} else {_IU = "I";}
        sql = sql + "_ta" + delimit;
        reg.put("year_ta",String.valueOf(year));
        reg.put("emis",getEMIS_code());         sql = sql + getEMIS_code() + delimit;
        reg.put("tc", code);    sql = sql + code + delimit;
        reg.put("shift",t1);                    sql = sql + t1 + delimit;
        reg.put("level",t2);                    sql = sql + t2 + delimit;
        reg.put("grade",t3);                    sql = sql + t3 + delimit;
        reg.put("section",t4);                  sql = sql + t4 + delimit;
        reg.put("subject",t5);                  sql = sql + t5 + delimit;
        reg.put("subject_assigned",t1+""+t2+""+t3+""+t4+""+t5);
        sql = sql + _IU;

        try {
            if (_IU == "I" && code != "") {
                dbSET.insert("_ta", null, reg);  _IU = "U";
            }
        } catch (Exception e) {
        }

    }

    // **************** Load DATA *************************
    public void deleteRecord(String string){
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.delete("_ta",string, null);
        dbSET.close();
        cnSET.close();

    }
    public void loadRecord(int code) {

    }

    public void updateRecord () {

    }

    public void initialize_upgrade() {

    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                update_lv_subject_teacher(SettingsMenuStaff.TS_code);
                load_lv_subject_assing(SettingsMenuStaff.TS_code);

                break;

        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Save and Exit !!!");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
                load_lv_subject_assing(SettingsMenuStaff.TS_code);
            }
        });
        dialogo1.show();
    }
    public void aceptar() {
        deleteRecord(texto.toString());
    }
    public void cancelar() {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_shift:
                try {
                    fill_sp_level(sp_shift.getSelectedItem().toString());
                } catch (Exception e) {}
                break;
            case R.id.sp_level:
                fill_sp_grade(sp_level.getSelectedItem().toString());
                fill_sp_subject(sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
                break;
            case R.id.sp_grade:
                fill_sp_subject(sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // *********** END Control Alerts ************************
    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        //Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
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
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_level);
        sp_level.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();
    }

        private void fill_sp_grade(String level){
        list_grade.clear();
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_grade);
        sp_grade.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();
    }

    private void fill_sp_subject(String level, String grade){
        list_subject.clear();
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_subject;
        String complementSQL = "";
        if (grade.equals(getResources().getString(R.string.str_g_std1)) || grade.equals(getResources().getString(R.string.str_g_std2)) ) {complementSQL=" AND id IN (1,2,3,4,5)";}
        else if (grade.equals(getResources().getString(R.string.str_g_std3)) || grade.equals(getResources().getString(R.string.str_g_std4))  ) {complementSQL=" AND id IN (7,8,12,17,18,19)";}
        else if (grade.equals(getResources().getString(R.string.str_g_std5)) || grade.equals(getResources().getString(R.string.str_g_std6)) || grade.equals(getResources().getString(R.string.str_g_std7)) ) {complementSQL=" AND id IN (7,8,9,10,11,12,13,14,15,16)";}
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
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_subject);
        sp_subject.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();
    }


    private String getGradeBDD(int level, int id){
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String result;
        String sql = "SELECT subject FROM subject WHERE level="+ level+" AND id="+id;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        if (cur_data.getCount()>0 ) {
            cur_data.moveToFirst();
            result =  cur_data.getString(0);
        } else {result =  "Not found subject";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    private int findGradeBDD(int level, String grade){
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
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

    public String getLang(){
        String getLang="";
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT lang from lang", null);
        cur_data.moveToFirst();
        getLang = cur_data.getString(0);
        return getLang;
    }

    public void disable(){
        add_reg.setVisibility(View.GONE);
        lv_subject_teacher.setEnabled(false);
        sp_shift.setEnabled(false);
        sp_level.setEnabled(false);
        sp_grade.setEnabled(false);
        sp_section.setEnabled(false);
        sp_subject.setEnabled(false);
    }
    public void enable(){
        add_reg.setVisibility(View.VISIBLE);
        lv_subject_teacher.setEnabled(true);
        sp_shift.setEnabled(true);
        sp_level.setEnabled(true);
        sp_grade.setEnabled(true);
        sp_section.setEnabled(true);
        sp_subject.setEnabled(true);
    }

}
