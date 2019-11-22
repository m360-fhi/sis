package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sergio.sistz.data.EmisInformation;
import com.example.sergio.sistz.fragment.IGetEmisCode;
import com.example.sergio.sistz.fragment.SchoolDescriptionFromEmisDialog;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Sergio on 3/8/2016.
 */
public class SettingsMenu_0 extends Activity implements View.OnClickListener, IGetEmisCode{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1; // ************ FrameLayout ***************
    CheckBox _col1, _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9;
    EditText et_emis, et_school_name;
    String _IU="U", regExist;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String currentDateandTime = sdf.format(new Date());
    private EmisInformation emis;
    private DBUtility connDB;
    private SchoolDescriptionFromEmisDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu_0);

        // ********************** Global vars ******************
        et_emis = (EditText) findViewById(R.id.et_emis);
        et_school_name = (EditText) findViewById(R.id.et_school_name);
        _col1 = (CheckBox) findViewById(R.id._col1);
        _col2 = (CheckBox) findViewById(R.id._col2);
        _col3 = (CheckBox) findViewById(R.id._col3);
        _col4 = (CheckBox) findViewById(R.id._col4);
        _col5 = (CheckBox) findViewById(R.id._col5);
        _col6 = (CheckBox) findViewById(R.id._col6);
        _col7 = (CheckBox) findViewById(R.id._col7);
        _col8 = (CheckBox) findViewById(R.id._col8);
        _col9 = (CheckBox) findViewById(R.id._col9);
        et_school_name.setEnabled(false);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);


        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) findViewById(R.id.btn_save);
        //ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        loadRecord();

    }


    // **************** Load DATA *************************
    public void loadRecord() {
        //toolsfncs.dialogAlertConfirm(this,getResources(),13);
        dialogAlert(2);
        et_emis.setText("");
        et_school_name.setText("");
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, m_pp, m_p, m_s, a_pp, a_p, a_s, e_pp, e_p, e_s, emis, flag FROM ms_0 WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            if(cur_data.getInt(1)==1) {_col1.setChecked(true);}
            if(cur_data.getInt(2)==1) {_col2.setChecked(true);}
            if(cur_data.getInt(3)==1) {_col3.setChecked(true);}
            if(cur_data.getInt(4)==1) {_col4.setChecked(true);}
            if(cur_data.getInt(5)==1) {_col5.setChecked(true);}
            if(cur_data.getInt(6)==1) {_col6.setChecked(true);}
            if(cur_data.getInt(7)==1) {_col7.setChecked(true);}
            if(cur_data.getInt(8)==1) {_col8.setChecked(true);}
            if(cur_data.getInt(9)==1) {_col9.setChecked(true);}
            et_emis.setText(cur_data.getString(10));
            et_school_name.setText(cur_data.getString(11));
            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit=";", s1;
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        if (!et_emis.getText().toString().isEmpty() && Integer.parseInt(et_emis.getText().toString())!=0 ) {
            if ((!et_emis.getText().toString().isEmpty()) && (Integer.parseInt(et_emis.getText().toString())!=0) && ((!et_school_name.getText().toString().equals("") && (!et_school_name.getText().toString().isEmpty())) )) {
                if (_IU == "I" ) {reg.put("_id",1);}
                reg.put("flag", et_school_name.getText().toString());
                sql = sql  + "ms_0;" + et_emis.getText().toString() + delimit;
                reg.put("emis", et_emis.getText().toString());
                if (_col1.isChecked() == true) {reg.put("m_pp", 1); sql = sql + "1" + delimit;} else {reg.put("m_pp",0); sql = sql + "0" + delimit;}
                if (_col2.isChecked() == true) {reg.put("m_p", 1); sql = sql + "1" + delimit;} else {reg.put("m_p",0); sql = sql + "0" + delimit;}
                if (_col3.isChecked() == true) {reg.put("m_s", 1); sql = sql + "1" + delimit;} else {reg.put("m_s",0); sql = sql + "0" + delimit;}
                if (_col4.isChecked() == true) {reg.put("a_pp", 1); sql = sql + "1" + delimit;} else {reg.put("a_pp",0); sql = sql + "0" + delimit;}
                if (_col5.isChecked() == true) {reg.put("a_p", 1); sql = sql + "1" + delimit;} else {reg.put("a_p",0); sql = sql + "0" + delimit;}
                if (_col6.isChecked() == true) {reg.put("a_s", 1); sql = sql + "1" + delimit;} else {reg.put("a_s",0); sql = sql + "0" + delimit;}
                if (_col7.isChecked() == true) {reg.put("e_pp", 1); sql = sql + "1" + delimit;} else {reg.put("e_pp",0); sql = sql + "0" + delimit;}
                if (_col8.isChecked() == true) {reg.put("e_p", 1); sql = sql + "1" + delimit;} else {reg.put("e_p",0); sql = sql + "0" + delimit;}
                if (_col9.isChecked() == true) {reg.put("e_s", 1); sql = sql + "1" + delimit;} else {reg.put("e_s",0); sql = sql + "0" + delimit;}
                sql = sql + _IU;
                try {
                    // ****************** Fill Bitacora
                    dbSET.update("ms_0", reg, "_id=1", null);
                    String sqlupdate = "UPDATE a SET a1='"+ et_emis.getText().toString()+"', a2='"+ et_school_name.getText().toString().replace("'","''")+"'";
                    dbSET.execSQL(sqlupdate);
                    // Aqui empiezo a reemplazar el codigo emis de attendaces, behaviour y evaluation. Antes debe verificar que tenga datos la tabla
                    dbSET.execSQL("UPDATE attendance SET emis='"+ et_emis.getText().toString()+"'");
                    dbSET.execSQL("UPDATE evaluation SET emis='"+ et_emis.getText().toString()+"'");
                    dbSET.execSQL("UPDATE behaviour SET emis='"+ et_emis.getText().toString()+"'");

                    toolsfncs.dialogAlertConfirm(this,getResources(),9);
                }catch (Exception e) {
                    toolsfncs.dialogAlertConfirm(this,getResources(),12);
                }
            } else {
                toolsfncs.dialogAlertConfirm(this,getResources(),15);
            }
        } else {
            toolsfncs.dialogAlertConfirm(this,getResources(),14);
        }

        dbSET.close();
        cnSET.close();
    }

    public void clearRecord(){

    }


    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                String selectedEmis = et_emis.getText().toString();
                if (connDB == null) {
                    connDB = new DBUtility(this);
                }

                if (selectedEmis.substring(0,2).equals("**")) {
                    emis = connDB.getEmisInformation(selectedEmis.substring(2));
                    if (emis != null) {
                        dialog = new SchoolDescriptionFromEmisDialog(this, this, emis, R.style.WalletFragmentDefaultStyle);
                        dialog.show();
                    } else {
                        Toast.makeText(this,getString(R.string.error_emis), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this,getString(R.string.error_emis), Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    // *********** Control change page
    public void change_fl(Integer fl) {
        if (fl > 2) {fl_location=1;}
        if (fl < 1) {fl_location=2;}
        switch (fl_location){
            case 1:
                fl_part1.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

        if (v == 1) {
            dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
            dialogo1.setMessage(getResources().getString(R.string.str_g_change_emis_code_confirm));
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar();
                }
            });
            dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancelar();
                }
            });
            dialogo1.show();
        }
        if (v == 2) {
            dialogo1.setTitle(getResources().getString(R.string.str_g_warning));
            dialogo1.setMessage(getResources().getString(R.string.str_g_change_emis_code));
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar2();
                }
            });
            dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancelar2();
                }
            });
            dialogo1.show();
        }

    }
    public void aceptar() { updateRecord();  }
    public void cancelar() {finish(); }

    public void aceptar2() {  }
    public void cancelar2() {finish(); }
    // *********** END Control Alerts ************************

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
    }
    public String getEMIS_code_form_a(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}

        return getemiscode;
    }


    public void logFunctions(String log1, String log2, String log3) {
    Conexion cnSETlogFunction = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
    SQLiteDatabase dbSETlogFunction = cnSETlogFunction.getReadableDatabase();

        dbSETlogFunction.execSQL("INSERT INTO logfunctions(startlog, locationlog, finishlog) VALUES('" + log1 + "', '" + log2 + "', '" + log3 + "')");
        dbSETlogFunction.close();
        cnSETlogFunction.close();
    }

    @Override
    public void emisCodeCorrect(EmisInformation emis, boolean flag, String mensaje) {
        dialog.dismiss();
        if (emis == null || !flag) {
            if (!mensaje.equals("")) {
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
            }
            cancelar();
        } else {
            connDB.updateEmisCode(emis);
            et_school_name.setText(emis.getSchool());
            et_emis.setText(emis.getEmisCode());
            aceptar();
            finish();
        }
    }
}
