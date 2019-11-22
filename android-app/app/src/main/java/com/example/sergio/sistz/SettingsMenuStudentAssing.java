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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBSubjectsUtils;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Sergio on 3/17/2016.
 */

// View.OnClickListener, AdapterView.OnItemSelectedListener
public class SettingsMenuStudentAssing extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    String[] _shift = {"Morning","Afternoon","Evening"};
    String[] _level = {"Primary","Secondary","Pre-Primary"};
    private String[] _grade = {"G1","G2","G3","G4","G5","G6","G7","G8"};
    private String[] _section = {"A","B","C","D","E","F","G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public Spinner sp_shift, sp_level, sp_grade, sp_section, sp_subject, sp_year; // se ha agregado año en asignación 07/Febrero/2018
    ArrayList<String> list_year = new ArrayList<>();
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    ArrayList<String> list_subject = new ArrayList<>();
    ArrayList<String> list_shift = new ArrayList<>();
    ArrayList<String> list_level = new ArrayList<>();
    ArrayList<String> list_grade = new ArrayList<>();
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    EditText _col0, _col1, _col2, _col4, _col5, _col6, _col7;
    TextView form_leyend; // 20180525
    CheckBox cb_new_entrant_pre_pri;
    RadioButton cb_repeater, cb_non_repeater ,cb_new_entrant, cb_stage_1, cb_stage_2, cb_stage_3;
    RadioGroup rg_enrollment_option;
    RadioButton _col3a, _col3b;
    ListView lv_subject_student, lv_subject_teacher;
    String _IU="U";
    FloatingActionButton add_reg, save_reg, erase_reg;
    private DBSubjectsUtils conn;
    TextView txt1, txt2, txt3, txt4, txt5;
    private  int t1, t2, t3, t4, t5;
    CharSequence texto;
    Calendar calendar = Calendar.getInstance();
    int school_year = calendar.get(Calendar.YEAR);
    LinearLayout ll_assign_student;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_student_assing,
                container, false);

        conn = new DBSubjectsUtils(getContext());
        ArrayList<String> Alist_subject = conn.getSubjects();

        form_leyend = (TextView) mLinearLayout.findViewById(R.id.form_leyend);
        // ********************** Global vars ******************
        ll_assign_student = (LinearLayout) mLinearLayout.findViewById(R.id.ll_assign_student);
        _col1 = (EditText) mLinearLayout.findViewById(R.id._col1);
        _col2 = (EditText) mLinearLayout.findViewById(R.id._col2);
        _col3a = (RadioButton) mLinearLayout.findViewById(R.id._col3a);
        _col3b = (RadioButton) mLinearLayout.findViewById(R.id._col3b);
        rg_enrollment_option = (RadioGroup) mLinearLayout.findViewById(R.id.rg_enrollment_option);
        _col5 = (EditText) mLinearLayout.findViewById(R.id._col5);
        _col6 = (EditText) mLinearLayout.findViewById(R.id._col6);
        _col7 = (EditText) mLinearLayout.findViewById(R.id._col7);
        cb_repeater = (RadioButton) mLinearLayout.findViewById(R.id.cb_repeater);
        cb_non_repeater = (RadioButton) mLinearLayout.findViewById(R.id.cb_non_repeater);
        cb_new_entrant = (RadioButton) mLinearLayout.findViewById(R.id.cb_new_entrant);
        cb_stage_1 = (RadioButton) mLinearLayout.findViewById(R.id.cb_stage_1);
        cb_stage_2 = (RadioButton) mLinearLayout.findViewById(R.id.cb_stage_2);
        cb_stage_3 = (RadioButton) mLinearLayout.findViewById(R.id.cb_stage_3);
        cb_new_entrant_pre_pri = (CheckBox) mLinearLayout.findViewById(R.id.cb_new_entrant_pre_pri);

        txt1 = (TextView) mLinearLayout.findViewById(R.id.txt1);
        txt2 = (TextView) mLinearLayout.findViewById(R.id.txt2);
        form_leyend = (TextView) mLinearLayout.findViewById(R.id.form_leyend);
        lv_subject_student = (ListView) mLinearLayout.findViewById(R.id.lv_subject_student);
        lv_subject_teacher = (ListView) mLinearLayout.findViewById(R.id.lv_subject_teacher);

        sp_year = (Spinner) mLinearLayout.findViewById(R.id.sp_year); // se ha agregado año en asignación 07/Febrero/2018
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
        rg_enrollment_option.setOnClickListener(this);

        erase_reg.setVisibility(View.GONE);
        add_reg.setVisibility(View.GONE);
        save_reg.setVisibility(View.VISIBLE);

        // ***************** LOCAD INFORMATION *************************
        start_array();
        LoadSpinner();
        load_lv_subject_assing(SettingsMenuStudent.TS_code);

        if (ReportS.S_report_enable == "1") {
            form_leyend.setVisibility(View.GONE);
            save_reg.setVisibility(View.GONE);
            lv_subject_student.setEnabled(false);
            sp_shift.setEnabled(false);
            sp_level.setEnabled(false);
            sp_grade.setEnabled(false);
            sp_section.setEnabled(false);
            cb_repeater.setEnabled(false);
            cb_non_repeater.setEnabled(false);
            cb_new_entrant.setEnabled(false);
            cb_stage_1.setEnabled(false);
            cb_stage_2.setEnabled(false);
            cb_stage_3.setEnabled(false);
            cb_new_entrant_pre_pri.setEnabled(false);
            ll_assign_student.setVisibility(View.GONE);
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
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_yearii)+"' WHERE level=3 and id=2");

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

        LoadYearSpinner();
    }

    private void LoadYearSpinner(){ // Se ha agreagdo este proceso el 7/Febrero/2018
        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_year;
        String sql = "SELECT distinct(year_ta) FROM _sa ORDER BY year_ta DESC";

        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_year = cur_data.getString(0);
                list_year.add(col_year);
            } while (cur_data.moveToNext());
        } else {list_year.add(String.valueOf(school_year));}
        ArrayAdapter<String> adap_year = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_year);
        sp_year.setAdapter(adap_year);
        cur_data.close();
        dbSET.close();
        dbSET.close();

        this.sp_year.setOnItemSelectedListener(this);
    }
    private void LoadSpinner(){
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

        this.sp_shift.setOnItemSelectedListener(this);
        this.sp_level.setOnItemSelectedListener(this);
        this.sp_grade.setOnItemSelectedListener(this);
        this.sp_section.setOnItemSelectedListener(this);

    }

    private void load_lv_subject_assing(String code){
        if (code != "") {
            final String deleteCode = code;
            int tmp1=0, tmp2=0, tmp3=0, tmp4=0;
            ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map = new HashMap<String, String>();
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            final Cursor cur_data = dbSET.rawQuery("SELECT emis, sc, shift, level, grade, section, year_ta, repeater, new_entrant, new_entrant_pp FROM _sa WHERE sc=" + code + " ORDER BY year_ta DESC", null);
            if (code == "") {code = SettingsMenuStudent.TS_code;}
            if (cur_data.moveToFirst()&& code != "") {
                do {
                    map = new HashMap<String, String>();
                    map.put("year", String.valueOf(cur_data.getInt(6)));
                    map.put("shift", String.valueOf(_shift[cur_data.getInt(2)-1]));  //tmp1 = cur_data.getInt(2)-1;
                    map.put("level", String.valueOf(_level[cur_data.getInt(3) - 1]));  //tmp2 = cur_data.getInt(3)-1;
                    map.put("grade", getGradeBDD(cur_data.getInt(3), cur_data.getInt(4)));
                    map.put("section", String.valueOf(_section[cur_data.getInt(5) - 1]));  //tmp4 = cur_data.getInt(5)-1;
                    mylist.add(map);
                    if (cur_data.getInt(7) == 1) {cb_repeater.setChecked(true);}
                    else if (cur_data.getInt(7) == 2) {cb_non_repeater.setChecked(true);}
                    else if (cur_data.getInt(7) == 3) {cb_new_entrant.setChecked(true);}
                    else if (cur_data.getInt(7) == 4) {cb_stage_1.setChecked(true);}
                    else if (cur_data.getInt(7) == 5) {cb_stage_2.setChecked(true);}
                    else if (cur_data.getInt(7) == 6) {cb_stage_3.setChecked(true);}
                }
                while (cur_data.moveToNext());
                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), mylist, R.layout.row_list_assing2,
                        new String[]{"year", "shift", "level", "grade", "section"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4, R.id.txt5});
                lv_subject_student.setAdapter(mSchedule);

                lv_subject_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // ******* get values in LineSelect ********************
                        TextView text1 = (TextView) view.findViewById(R.id.txt1);
                        TextView text2 = (TextView) view.findViewById(R.id.txt2);
                        TextView text3 = (TextView) view.findViewById(R.id.txt3);
                        TextView text4 = (TextView) view.findViewById(R.id.txt4);
                        TextView text5 = (TextView) view.findViewById(R.id.txt5);

                        // ****************** Codition for delete record *********************
                        texto = " sc=" + String.valueOf(deleteCode) + " AND year_ta=" +
                                text1.getText().toString() + " AND shift=" +
                                getIndexArray(_shift, text2.getText().toString()) + " AND  level=" +
                                getIndexArray(_level, text3.getText().toString()) + " AND  grade=" +
                                findGradeBDD(getIndexArray(_level, text3.getText().toString()), text4.getText().toString()) + " AND  section=" +
                                getStream(text5.getText().toString());
                        dialogAlert(3);
                        Toast.makeText(getContext(),texto, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                mylist.clear();
                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), mylist, R.layout.row_list_assing,
                        new String[]{"year", "shift", "level", "grade", "section"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4, R.id.txt5});
                lv_subject_student.setAdapter(mSchedule);
            }
        }
    }

    private void update_lv_subject_teacher(String code){
        if (!code.isEmpty()) {
            String sql = "", delimit="|", sqlcondition;
            Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            t1 = getIndexArray(_shift ,sp_shift.getSelectedItem().toString());
            t2 = getIndexArray(_level, sp_level.getSelectedItem().toString());
            t3 = sp_grade.getSelectedItemPosition()+1;
            t4 = sp_section.getSelectedItemPosition()+1;
            Calendar c = Calendar.getInstance();
            t5 = Integer.parseInt(sp_year.getSelectedItem().toString());
            sqlcondition = " sc=" + code + " AND year_ta=" + t5;
            Cursor cur_data = dbSET.rawQuery("SELECT emis, sc, shift, level, grade, section, year_ta FROM _sa WHERE " + sqlcondition, null);
            cur_data.moveToFirst();
            ContentValues reg = new ContentValues();
            if (cur_data.getCount() == 1) {_IU = "U";} else {_IU = "I";}
            sql = sql + "_sa" + delimit;
            reg.put("emis",getEMIS_code());         sql = sql + getEMIS_code() + delimit;
            reg.put("sc", code);                    sql = sql + code + delimit;
            reg.put("shift",t1);                    sql = sql + t1 + delimit;
            reg.put("level",t2);                    sql = sql + t2 + delimit;
            reg.put("grade",t3);                    sql = sql + t3 + delimit;
            reg.put("section",t4);                  sql = sql + t4 + delimit;
            if (cb_repeater.isChecked()==true) {reg.put("repeater",1); sql = sql + "1" + delimit;}
            else if (cb_non_repeater.isChecked()==true) {reg.put("repeater",2); sql = sql + "2" + delimit;}
            else if (cb_new_entrant.isChecked()==true) {reg.put("repeater",3); sql = sql + "3" + delimit;}
            else if (cb_stage_1.isChecked()==true) {reg.put("repeater",4); sql = sql + "4" + delimit;}
            else if (cb_stage_2.isChecked()==true) {reg.put("repeater",5); sql = sql + "5" + delimit;}
            else if (cb_stage_3.isChecked()==true) {reg.put("repeater",6); sql = sql + "6" + delimit;}
            else {reg.put("repeater",0); sql = sql + "0" + delimit;}
            if (cb_new_entrant.isChecked()==true) {reg.put("new_entrant",1); sql = sql + "1" + delimit;}  else   {reg.put("new_entrant",0); sql = sql + "0" + delimit;}
            if (cb_new_entrant_pre_pri.isChecked()==true) {reg.put("new_entrant_pp",1); sql = sql + "1" + delimit;}  else   {reg.put("new_entrant_pp",0); sql = sql + "0" + delimit;}
            reg.put("year_ta",t5);                  sql = sql + t5 + delimit;
            reg.put("subject_assigned",t1+""+t2+""+t3+""+t4);

            sql = sql + _IU;

            try {
                if (_IU == "I" && code != "") {
                    dbSET.insert("_sa", null, reg);  _IU = "U";
                } else {dbSET.update("_sa", reg, sqlcondition, null);}
                toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
            } catch (Exception e) {
            }
        }
    }



    // **************** Load DATA *************************

    public void deleteRecord(String string){
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.delete("_sa",string, null);
        dbSET.close();
        cnSET.close();
    }


    public void reloadspinner(int shift, int level, int grade, int section ) {
        Toast.makeText(getContext(), "" +
                sp_shift.getSelectedItem() + "-" +
                sp_level.getSelectedItem() + "-" +
                sp_grade.getSelectedItem() + "-" +
                sp_section.getSelectedItem() + "-"
                , Toast.LENGTH_SHORT).show();
    }

    public void updateRecord () {

    }

    public void initialize_upgrade() {

    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_reg:
                dialogAlert(1);
                break;
        }
    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        final int tmp_v = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Are you sure want to assign student?");}
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
                switch (tmp_v) {
                    case 1: aceptar(); break;
                    case 3: borrarRegistro();
                            load_lv_subject_assing(SettingsMenuStudent.TS_code);
                        break;
                }
            }
        });
        dialogo1.show();
    }

    public void aceptar() {
        update_lv_subject_teacher(SettingsMenuStudent.TS_code);
        load_lv_subject_assing(SettingsMenuStudent.TS_code);
    }
    public void cancelar() {
    }

    public void borrarRegistro(){
       deleteRecord(texto.toString());
    };
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
                if (sp_grade.getSelectedItemId()==0){
                    cb_new_entrant.setEnabled(true);
                }
                else {
                    cb_new_entrant.setEnabled(false);
                }
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
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
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

    public int getIndexSpinner(Spinner spinner , String myString){
        int index = 0;
        for (int i = 0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
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

    private int getStream(String str) {
        int str_return = 0;
        switch (str){
            case "A": str_return=1; break;
            case "B": str_return=2; break;
            case "C": str_return=3; break;
            case "D": str_return=4; break;
            case "E": str_return=5; break;
            case "F": str_return=6; break;
            case "G": str_return=8; break;
            case "H": str_return=9; break;
            case "I": str_return=10; break;
            case "J": str_return=11; break;
            case "K": str_return=12; break;
            case "L": str_return=13; break;
            case "M": str_return=14; break;
            case "N": str_return=15; break;
            case "O": str_return=16; break;
            case "P": str_return=17; break;
            case "Q": str_return=18; break;
            case "R": str_return=19; break;
            case "S": str_return=20; break;
            case "T": str_return=21; break;
            case "U": str_return=22; break;
            case "V": str_return=23; break;
            case "W": str_return=24; break;
            case "X": str_return=25; break;
            case "Y": str_return=26; break;
            case "Z": str_return=27; break;
        }
        return str_return;
    }

}
