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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.adapter.TeacherStudentListAdapter;
import com.example.sergio.sistz.data.TeacherStudentItem;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 3/13/2016.
 */
public class SettingsMenuStaff extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static String EMIS_code = "";
    public static String TS_code = "", code="";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    ListView lv_list;
    FloatingActionButton add_reg, find_reg, erase_reg;
    EditText et_find_reg;
    ImageButton ib_find_reg;
    CheckBox cbDropOut;
    TextView no_data_show;
    List<TeacherStudentItem> list = new ArrayList<>();


    @Override
    protected void onRestart() {
        super.onRestart();
        loadListTeacher();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListTeacher();
        TS_code="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu_staff);

        // ********************** Global vars ******************
        lv_list = (ListView) findViewById(R.id.lv_list);
        et_find_reg = (EditText) findViewById(R.id.et_find_reg);
        ib_find_reg = (ImageButton) findViewById(R.id.ib_find_reg);

        cbDropOut = (CheckBox) findViewById(R.id.cb_drop_out);

        no_data_show = (TextView) findViewById(R.id.txt_no_data_to_show);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);

        //  ************************ Objects Buttoms *********************

        add_reg = (FloatingActionButton) findViewById(R.id.add_reg);
        find_reg = (FloatingActionButton) findViewById(R.id.find_reg);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);


        // **************** CLICK ON BUTTONS ********************
        add_reg.setOnClickListener(this);
        add_reg.setVisibility(View.VISIBLE);
        find_reg.setOnClickListener(this);
        ib_find_reg.setOnClickListener(this);
        cbDropOut.setOnClickListener(this);
        // ***************** LOCAD INFORMATION *************************
        loadListTeacher();
        //loadRecord();


    }

    // ***************** Load Teacher/Staff LIST *************************
    public void loadListTeacher() {

        DBUtility db = new DBUtility(this);
        list = db.getTeacherList(cbDropOut.isChecked());
        if (list != null) {
            no_data_show.setVisibility(View.GONE);
            TeacherStudentListAdapter adap_list = new TeacherStudentListAdapter(this, R.layout.row_teacher_student_list_, list);
            lv_list.setAdapter(adap_list);
            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                    ReportTS.TS_report_enable="";
                    TeacherStudentItem item = list.get(posicion);
                    code = item.getId();
                    TS_code = item.getId();
                    Intent intent1 = new Intent(SettingsMenuStaff.this, SettingsMenuStaff_menu.class);
                    startActivity(intent1);
                    loadListTeacher();
                }
            });
            lv_list.refreshDrawableState();
        } else {
            no_data_show.setVisibility(View.VISIBLE);
            lv_list.clearChoices();
            lv_list.refreshDrawableState();
        }
    }


    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                TS_code="";
                Intent intent1 = new Intent(SettingsMenuStaff.this, SettingsMenuStaff_menu.class);
                startActivity(intent1);
                loadListTeacher();
                break;
            case R.id.find_reg:
                et_find_reg.setVisibility(View.VISIBLE);
                ib_find_reg.setVisibility(View.VISIBLE);
                cbDropOut.setVisibility(View.GONE);
                break;
            case R.id.ib_find_reg:
                et_find_reg.setVisibility(View.GONE);
                ib_find_reg.setVisibility(View.GONE);
                cbDropOut.setVisibility(View.VISIBLE);
                if (findRecord(et_find_reg.getText().toString()).equals("1")){
                    TS_code = et_find_reg.getText().toString();

                    Intent intent2 = new Intent(SettingsMenuStaff.this, SettingsMenuStaff_menu.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(getApplicationContext(), "Not exist Teacher/Staff... !!! ", Toast.LENGTH_SHORT).show();
                }
                et_find_reg.setText("");
                break;
            case R.id.cb_drop_out:
                loadListTeacher();
//                if (cbDropOut.isChecked()) {Toast.makeText(getApplicationContext(), "muestra los maestros retirados", Toast.LENGTH_SHORT).show();}
//                else {Toast.makeText(getApplicationContext(), "oculta los maestros retirados", Toast.LENGTH_SHORT).show();}
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
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.delete("_t","_id=" + code,null);
        dbSET.close();
        cnSET.close();
        loadListTeacher();
    }

    public void cancelar() {
    }

    // *********** END Control Alerts ************************

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
    }

    public String findRecord(String string){
        String getFindRecord="0";
        try {
            Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT * FROM teacher WHERE _id=" + string, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getFindRecord = "1";} else {getFindRecord = "0";}
        } catch (Exception e) {}
        return getFindRecord;
    }
}
