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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.sergio.sistz.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Sergio on 3/13/2016.
 */
public class SettingsMenuStudentBehaviour extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    ListView lv_absentList;
    Calendar calendar = Calendar.getInstance();
    public int school_year = calendar.get(Calendar.YEAR);


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }

        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_student_behaviour,
                container, false);

        // ********************** Global vars ******************
        lv_absentList = (ListView) mLinearLayout.findViewById(R.id.lv_absentList);



        loadRecord(SettingsMenuStudent.TS_code);



        return mLinearLayout;
    }


    // **************** Load DATA *************************
    public void loadRecord(String code) {
        if (code.equals("")) {}
        else {
            String[] studentBehaviour = getResources().getStringArray(R.array.behaviour);
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT  a.date, s.subject, a.reason FROM behaviour  a INNER JOIN subject s ON s.level=a.level AND s.id=a.subject WHERE a.s_id="+code+" AND strftime('%Y',a.date) ='"+ school_year +"' AND a.reason<>'0' ORDER BY a.date", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {
                ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map = new HashMap<String, String>();
                do {
                    map = new HashMap<String, String>();
                    map.put("date", cur_data.getString(0));
                    map.put("subject", cur_data.getString(1));
                    map.put("reason", studentBehaviour[cur_data.getInt(2)]);
                    String tmp = studentBehaviour[2];
                    mylist.add(map);
                }
                while (cur_data.moveToNext());

                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), mylist, R.layout.row_list_student_behaviour,
                        new String[]{"date", "subject", "reason"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3});

                lv_absentList.setAdapter(mSchedule);

                int tmp_hg = lv_absentList.getCount() * 110;
                ViewGroup.LayoutParams params = lv_absentList.getLayoutParams();
                params.height = tmp_hg;
                lv_absentList.setLayoutParams(params);

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
            case R.id.add_reg:
                //loadRecord(0);
                dialogAlert(1);
                break;
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

}
