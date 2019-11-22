package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;

/**
 * Created by Sergio on 3/15/2016.
 */
public class SettingsMenuInfra_8 extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    String  _col1, _col2,_col5, _col6;
    EditText  _col3, _col4, _col7, _col8;
    RadioButton a_rb_yes,a_rb_no,a_rb_once, a_rb_twice, a_rb_three, a_rb_four, a_rb_zero,b_rb_yes,b_rb_no,b_rb_once, b_rb_twice, b_rb_three, b_rb_four, b_rb_zero;
    int rg1_id ;
    int rg2_id;
    int rg3_id ;
    int rg4_id ;

    String _IU="U";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_8,
                container, false);

        // ********************** Global vars ******************
        a_rb_yes = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_yes);
        a_rb_no = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_no);
        a_rb_once = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_once);
        a_rb_twice = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_twice);
        a_rb_three = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_three);
        a_rb_four = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_four);
        a_rb_zero = (RadioButton) mLinearLayout.findViewById(R.id.a_rb_zero);
        _col3 = (EditText) mLinearLayout.findViewById(R.id.a_et_col3);
        _col4 = (EditText) mLinearLayout.findViewById(R.id.a_et_col4);

        b_rb_once = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_once);
        b_rb_twice = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_twice);
        b_rb_three = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_three);
        b_rb_four = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_four);
        b_rb_zero = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_zero);
        b_rb_yes = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_yes);
        b_rb_no = (RadioButton) mLinearLayout.findViewById(R.id.b_rb_no);

        _col7 = (EditText) mLinearLayout.findViewById(R.id.b_et_col3);
        _col8 = (EditText) mLinearLayout.findViewById(R.id.b_et_col4);


        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        loadRecord();

        return mLinearLayout;
    }


    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, s1a, s1b, s1c, s1d, s2a, s2b, s2c, s2d, flag FROM s WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {

            _col1 = cur_data.getString(1);
            if(_col1 != null) {switch (_col1) {case "1":a_rb_yes.setChecked(true);break;case "2":a_rb_no.setChecked(true);break;default:break;}}

            _col2 = cur_data.getString(2);
            if(_col2 != null) {switch (_col2){
                case "1":a_rb_once.setChecked(true);break;
                case "2":a_rb_twice.setChecked(true);break;
                case "3":a_rb_three.setChecked(true);break;
                case "4":a_rb_four.setChecked(true);break;
                case "5":a_rb_zero.setChecked(true);break;
                default:break;}}

            _col3.setText(cur_data.getString(3));
            _col4.setText(cur_data.getString(4));

            _col5 = cur_data.getString(5);
            if(_col5 != null) {switch (_col5) {case "1":b_rb_yes.setChecked(true);break;case "2":b_rb_no.setChecked(true);break;default:break;}}
            _col6 = cur_data.getString(6);

            if(_col6 != null) {switch (_col6){
                case "1":b_rb_once.setChecked(true);break;
                case "2":b_rb_twice.setChecked(true);break;
                case "3":b_rb_three.setChecked(true);break;
                case "4":b_rb_four.setChecked(true);break;
                case "5":b_rb_zero.setChecked(true);break;
                default:break;}}

            _col7.setText(cur_data.getString(7));
            _col8.setText(cur_data.getString(8));

            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", s1;

        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        if (_IU == "I" ) {reg.put("_id",1);}
        reg.put("flag", _IU); //sql = sql + _IU + ";s;" + SettingsMenuInfra_0.EMIS_code + delimit;
        sql = sql + "s" + delimit + SettingsMenuInfra_0.EMIS_code + delimit;

        if (a_rb_yes.isChecked() == true) {reg.put("s1a", 1); sql = sql + "1" + delimit;}
        else if (a_rb_no.isChecked() == true) {reg.put("s1a", 2); sql = sql + "2" + delimit;}
        else {sql = sql + "" + delimit;}


        if (a_rb_once.isChecked() == true) {reg.put("s1b", 1); sql = sql + "1" + delimit;}
        else if (a_rb_twice.isChecked() == true) {reg.put("s1b", 2); sql = sql + "2" + delimit;}
        else if (a_rb_three.isChecked() == true) {reg.put("s1b", 3); sql = sql + "3" + delimit;}
        else if (a_rb_four.isChecked() == true) {reg.put("s1b", 4); sql = sql + "4" + delimit;}
        else if (a_rb_zero.isChecked() == true) {reg.put("s1b", 5); sql = sql + "5" + delimit;}
        else {sql = sql + "" + delimit;}

        if (!_col3.getText().toString().isEmpty()){reg.put("s1c", _col3.getText().toString());} sql = sql + _col3.getText().toString() + delimit;
        if (!_col4.getText().toString().isEmpty()){reg.put("s1d", _col4.getText().toString());} sql = sql + _col4.getText().toString() + delimit;

        if (b_rb_yes.isChecked() == true) {reg.put("s2a", 1); sql = sql + "1" + delimit;}
        else if (b_rb_no.isChecked() == true) {reg.put("s2a", 2); sql = sql + "2" + delimit;}
        else {sql = sql + "" + delimit;}

        if (b_rb_once.isChecked() == true) {reg.put("s2b", 1); sql = sql + "1" + delimit;}
        else if (b_rb_twice.isChecked() == true) {reg.put("s2b", 2); sql = sql + "2" + delimit;}
        else if (b_rb_three.isChecked() == true) {reg.put("s2b", 3); sql = sql + "3" + delimit;}
        else if (b_rb_four.isChecked() == true) {reg.put("s2b", 4); sql = sql + "4" + delimit;}
        else if (b_rb_zero.isChecked() == true) {reg.put("s2b", 5); sql = sql + "5" + delimit;}
        else {sql = sql + "" + delimit;}


        if (!_col7.getText().toString().isEmpty()){reg.put("s2c", _col7.getText().toString());} sql = sql + _col7.getText().toString() + delimit;
        if (!_col8.getText().toString().isEmpty()){reg.put("s2d", _col8.getText().toString());} sql = sql + _col8.getText().toString() + delimit;

        sql = sql + _IU;
        try {
            // ****************** Fill Bitacora
            ContentValues Bitacora = new ContentValues();
            Bitacora.put("sis_sql",sql);
            dbSET.insert("sisupdate",null,Bitacora);
            // ********************* Fill TABLE d
            if (_IU=="I") {dbSET.insert("s", null, reg); _IU="U";}
            else {dbSET.update("s", reg, "_id=1", null);}

            toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
        } catch (Exception e) {
            Toast.makeText(getContext(), getResources().getString(R.string.str_bl2_msj1), Toast.LENGTH_SHORT).show();
        }

        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                dialogAlert(1);
                break;
        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj2));}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
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
    public void aceptar() {updateRecord();}
    public void cancelar() {} //getActivity().finish();}
    // *********** END Control Alerts ************************
}
