package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Sergio on 3/13/2016.
 */
public class SettingsMenuStudentEvaluation extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    ListView lv_listSubject;
    Spinner sp_subjectEvaluation;
    ArrayList<String> list_spSubject = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    public int school_year = calendar.get(Calendar.YEAR);

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }

        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_student_evaluation,
                container, false);

        // ********************** Global vars ******************
        sp_subjectEvaluation = (Spinner) mLinearLayout.findViewById(R.id.sp_subjectEvaluation);
        lv_listSubject = (ListView) mLinearLayout.findViewById(R.id.lv_listSubject);



        loadSubject(SettingsMenuStudent.TS_code);
        sp_subjectEvaluation.setOnItemSelectedListener(this);
        return mLinearLayout;
    }

    // **************** Load Notes *************************
    public void loadSubject(String code) {
        if (code.equals("")) {}
        else {
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            String sql = " SELECT s.subject FROM  evaluation AS e \n" +
                    " INNER JOIN subject as s ON (s.level=e.level AND s.id=e.subject)\n" +
                    " WHERE e.s_id=" + code + " AND strftime('%Y',e.date) ='"+ school_year +"' GROUP BY s.subject";  // AND lvl=1 AND sub=1 GROUP BY subject";
            Cursor cur_data = dbSET.rawQuery(sql, null);
            cur_data.moveToFirst();
            String col_subject;
            if (cur_data.getCount() > 0) {
                do {
                    col_subject = cur_data.getString(0);
                    list_spSubject.add(col_subject);
                } while (cur_data.moveToNext());
                ArrayAdapter adap_spSubject = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, list_spSubject);
                sp_subjectEvaluation.setAdapter(adap_spSubject);
            }
            cur_data.close();
            dbSET.close();
            cnSET.close();
        }
    }

    // **************** Load Subject *************************
    public void loadNotes(String code, String subject) {
        if (code.equals("")) {}
        else {
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            String sql = "SELECT e.date, s.subject, (CASE e.note WHEN '1' THEN '" + getResources().getString(R.string.str_gtest_1) +
                        "' WHEN '2' THEN '"+ getResources().getString(R.string.str_gtest_2) +
                        "' WHEN '3' THEN '"+ getResources().getString(R.string.str_gtest_3) +
                        "' WHEN '4' THEN '"+ getResources().getString(R.string.str_gtest_4) +
                        "' WHEN '5' THEN '"+ getResources().getString(R.string.str_gtest_5) +
                        "' WHEN '6' THEN '"+ getResources().getString(R.string.str_gtest_6) +
                        "' WHEN '7' THEN '"+ getResources().getString(R.string.str_gtest_7) +
                        "' WHEN '8' THEN '"+ getResources().getString(R.string.str_gtest_8) +
                        "' WHEN '9' THEN '"+ getResources().getString(R.string.str_gtest_9) +
                        "' WHEN '10' THEN '"+ getResources().getString(R.string.str_gtest_10) +
                        "' WHEN '11' THEN '"+ getResources().getString(R.string.str_gtest_11) +
                        "' WHEN '12' THEN '"+ getResources().getString(R.string.str_gtest_12) +"' END) AS note, e.points FROM  evaluation AS e \n" +
                    " INNER JOIN subject as s ON (s.level=e.level AND s.id=e.subject)\n" +
                    " WHERE e.s_id=" + code + " and s.subject ='" + subject + "'";  // AND lvl=1 AND sub=1 GROUP BY subject";
            Cursor cur_data = dbSET.rawQuery(sql, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {
                ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map = new HashMap<String, String>();
                do {
                    map = new HashMap<String, String>();
                    map.put("date", cur_data.getString(0));
                    map.put("note", cur_data.getString(2));
                    map.put("points", cur_data.getString(3));
                    mylist.add(map);
                }
                while (cur_data.moveToNext());

                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), mylist, R.layout.row_list_student_grade,
                        new String[]{"date", "note", "points"}, new int[]{R.id.txt0, R.id.txt1, R.id.txt2});

                lv_listSubject.setAdapter(mSchedule);

                int tmp_hg = lv_listSubject.getCount() * 80;
                ViewGroup.LayoutParams params = lv_listSubject.getLayoutParams();
                params.height = tmp_hg;
                lv_listSubject.setLayoutParams(params);

            } else {
            }
            cur_data.close();
            dbSET.close();
            cnSET.close();
        }
    }



    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        final int val = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Are sure you want to save?");}
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
                aceptar(val);
            }
        });
        dialogo1.show();
    }
    public void dialogAlert2(int v){
        final int val = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 4){dialogo1.setMessage("Teacher has assigned subjects ");}
        if (v == 5){dialogo1.setMessage("Empty");}
        if (v == 6){dialogo1.setMessage("Empty");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    public void aceptar(int action) {}
    public void cancelar() {}

    // *********** END Control Alerts ************************

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return getemiscode;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.sp_subjectEvaluation:
                loadNotes(SettingsMenuStudent.TS_code,sp_subjectEvaluation.getSelectedItem().toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
