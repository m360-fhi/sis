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
 * Created by Sergio on 2/26/2016.
 */
public class SettingsMenuInfra_4 extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2, fl_part3; // ************ FrameLayout ***************
    EditText _col2, _col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14, _col15, _col16, _col17, _col18, _col19, _col20, _col21, _col22, _col23, _col24, _col25, _col26, _col27, _col28, _col29, _col30, _col31, _col32, _col33, _col34, _col35, _col36;
    RadioButton _col1a, _col1b, _col3a, _col3b, _col4a, _col4b;
    String _IU="U";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_4,
                container, false);

        // ********************** Global vars ******************
        _col1a = (RadioButton) mLinearLayout.findViewById(R.id._col1a);
        _col1b = (RadioButton) mLinearLayout.findViewById(R.id._col1b);
        _col2 = (EditText) mLinearLayout.findViewById(R.id._col2);
        _col3a = (RadioButton) mLinearLayout.findViewById(R.id._col3a);
        _col3b= (RadioButton) mLinearLayout.findViewById(R.id._col3b);
        _col4a = (RadioButton) mLinearLayout.findViewById(R.id._col4a);
        _col4b = (RadioButton) mLinearLayout.findViewById(R.id._col4b);
        _col5 = (EditText) mLinearLayout.findViewById(R.id._col5);
        _col6 = (EditText) mLinearLayout.findViewById(R.id._col6);
        _col7 = (EditText) mLinearLayout.findViewById(R.id._col7);
        _col8 = (EditText) mLinearLayout.findViewById(R.id._col8);
        _col9 = (EditText) mLinearLayout.findViewById(R.id._col9);
        _col10 = (EditText) mLinearLayout.findViewById(R.id._col10);
        _col11 = (EditText) mLinearLayout.findViewById(R.id._col11);
        _col12 = (EditText) mLinearLayout.findViewById(R.id._col12);
        _col13 = (EditText) mLinearLayout.findViewById(R.id._col13);
        _col14 = (EditText) mLinearLayout.findViewById(R.id._col14);
        _col15 = (EditText) mLinearLayout.findViewById(R.id._col15);
        _col16 = (EditText) mLinearLayout.findViewById(R.id._col16);
        _col17 = (EditText) mLinearLayout.findViewById(R.id._col17);
        _col18 = (EditText) mLinearLayout.findViewById(R.id._col18);
        _col19 = (EditText) mLinearLayout.findViewById(R.id._col19);
        _col20 = (EditText) mLinearLayout.findViewById(R.id._col20);
        _col21 = (EditText) mLinearLayout.findViewById(R.id._col21);
        _col22 = (EditText) mLinearLayout.findViewById(R.id._col22);
        _col23 = (EditText) mLinearLayout.findViewById(R.id._col23);
        _col24 = (EditText) mLinearLayout.findViewById(R.id._col24);
        _col25 = (EditText) mLinearLayout.findViewById(R.id._col25);
        _col26 = (EditText) mLinearLayout.findViewById(R.id._col26);
        _col27 = (EditText) mLinearLayout.findViewById(R.id._col27);
        _col28 = (EditText) mLinearLayout.findViewById(R.id._col28);
        _col29 = (EditText) mLinearLayout.findViewById(R.id._col29);
        _col30 = (EditText) mLinearLayout.findViewById(R.id._col30);
        _col31 = (EditText) mLinearLayout.findViewById(R.id._col31);
        _col32 = (EditText) mLinearLayout.findViewById(R.id._col32);
        _col33 = (EditText) mLinearLayout.findViewById(R.id._col33);
        _col34 = (EditText) mLinearLayout.findViewById(R.id._col34);
        _col35 = (EditText) mLinearLayout.findViewById(R.id._col35);
        _col36 = (EditText) mLinearLayout.findViewById(R.id._col36);



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
        Cursor cur_data = dbSET.rawQuery("SELECT _id, i1a, i1b, i2a, i3, i41, i42, i43, i44, i45, i46, i47, i48, i49, i410, i411, i412, i413, i414, i415, i416, i417, i418, i419, i420, i421, i422, i423, i424, i425, i426, i427, i428, i429, i430, i431, i432, flag FROM i WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            // ********************* Part 1
            if (cur_data.getInt(1)==1) {_col1a.setChecked(true);}
            if (cur_data.getInt(1)==2) {_col1b.setChecked(true);}
            _col2.setText(cur_data.getString(2));
            if (cur_data.getInt(3)==1) {_col3a.setChecked(true);}
            if (cur_data.getInt(3)==2) {_col3b.setChecked(true);}
            if (cur_data.getInt(4)==1) {_col4a.setChecked(true);}
            if (cur_data.getInt(4)==2) {_col4b.setChecked(true);}
            _col5.setText(cur_data.getString(5));
            _col6.setText(cur_data.getString(6));
            _col7.setText(cur_data.getString(7));
            _col8.setText(cur_data.getString(8));
            _col9.setText(cur_data.getString(9));
            _col10.setText(cur_data.getString(10));
            _col11.setText(cur_data.getString(11));
            _col12.setText(cur_data.getString(12));
            _col13.setText(cur_data.getString(13));
            _col14.setText(cur_data.getString(14));
            _col15.setText(cur_data.getString(15));
            _col16.setText(cur_data.getString(16));
            _col17.setText(cur_data.getString(17));
            _col18.setText(cur_data.getString(18));
            _col19.setText(cur_data.getString(19));
            _col20.setText(cur_data.getString(20));
            _col21.setText(cur_data.getString(21));
            _col22.setText(cur_data.getString(22));
            _col23.setText(cur_data.getString(23));
            _col24.setText(cur_data.getString(24));
            _col25.setText(cur_data.getString(25));
            _col26.setText(cur_data.getString(26));
            _col27.setText(cur_data.getString(27));
            _col28.setText(cur_data.getString(28));
            _col29.setText(cur_data.getString(29));
            _col30.setText(cur_data.getString(30));
            _col31.setText(cur_data.getString(31));
            _col32.setText(cur_data.getString(32));
            _col33.setText(cur_data.getString(33));
            _col34.setText(cur_data.getString(34));
            _col35.setText(cur_data.getString(35));
            _col36.setText(cur_data.getString(36));


            // ********************* Part 3


            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        _col1a.setFocusable(true);
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", s1;
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
            if (_IU == "I" ) {reg.put("_id",1);}
            reg.put("flag", _IU); //sql = sql + _IU + ";i;" + SettingsMenuInfra_0.EMIS_code + delimit;
            sql = sql + "i" + delimit + SettingsMenuInfra_0.EMIS_code + delimit;
            // **************** Frame 1  ******************
            if (_col1a.isChecked() == true) {reg.put("i1a", 1); sql = sql + "1" + delimit;}
            else if (_col1b.isChecked() == true) {reg.put("i1a", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}
            if (!_col2.getText().toString().isEmpty()){reg.put("i1b", Integer.parseInt(_col2.getText().toString()));} sql = sql + _col2.getText().toString() + delimit;
            // **************** Frame 2  ******************
            if (_col3a.isChecked() == true) {reg.put("i2a", 1); sql = sql + "1" + delimit;}
            else if (_col3b.isChecked() == true) {reg.put("i2a", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "2" + delimit;}
            if (_col4a.isChecked() == true) {reg.put("i3", 1); sql = sql + "1" + delimit;}
            else if (_col4b.isChecked() == true) {reg.put("i3", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}
            if (!_col5.getText().toString().isEmpty()){reg.put("i41", Integer.parseInt(_col5.getText().toString()));} sql = sql + _col5.getText().toString() + delimit;
            if (!_col6.getText().toString().isEmpty()){reg.put("i42", Integer.parseInt(_col6.getText().toString()));} sql = sql + _col6.getText().toString() + delimit;
            if (!_col7.getText().toString().isEmpty()){reg.put("i43", Integer.parseInt(_col7.getText().toString()));} sql = sql + _col7.getText().toString() + delimit;
            if (!_col8.getText().toString().isEmpty()){reg.put("i44", Integer.parseInt(_col8.getText().toString()));} sql = sql + _col8.getText().toString() + delimit;
            if (!_col9.getText().toString().isEmpty()){reg.put("i45", Integer.parseInt(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
            if (!_col10.getText().toString().isEmpty()){reg.put("i46", Integer.parseInt(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
            if (!_col11.getText().toString().isEmpty()){reg.put("i47", Integer.parseInt(_col11.getText().toString()));} sql = sql + _col11.getText().toString() + delimit;
            if (!_col12.getText().toString().isEmpty()){reg.put("i48", Integer.parseInt(_col12.getText().toString()));} sql = sql + _col12.getText().toString() + delimit;
            if (!_col13.getText().toString().isEmpty()){reg.put("i49", Integer.parseInt(_col13.getText().toString()));} sql = sql + _col13.getText().toString() + delimit;
            if (!_col14.getText().toString().isEmpty()){reg.put("i410", Integer.parseInt(_col14.getText().toString()));} sql = sql + _col14.getText().toString() + delimit;
            if (!_col15.getText().toString().isEmpty()){reg.put("i411", Integer.parseInt(_col15.getText().toString()));} sql = sql + _col15.getText().toString() + delimit;
            if (!_col16.getText().toString().isEmpty()){reg.put("i412", Integer.parseInt(_col16.getText().toString()));} sql = sql + _col16.getText().toString() + delimit;
            if (!_col17.getText().toString().isEmpty()){reg.put("i413", Integer.parseInt(_col17.getText().toString()));} sql = sql + _col17.getText().toString() + delimit;
            if (!_col18.getText().toString().isEmpty()){reg.put("i414", Integer.parseInt(_col18.getText().toString()));} sql = sql + _col18.getText().toString() + delimit;
            if (!_col19.getText().toString().isEmpty()){reg.put("i415", Integer.parseInt(_col19.getText().toString()));} sql = sql + _col19.getText().toString() + delimit;
            if (!_col20.getText().toString().isEmpty()){reg.put("i416", Integer.parseInt(_col20.getText().toString()));} sql = sql + _col20.getText().toString() + delimit;
            if (!_col21.getText().toString().isEmpty()){reg.put("i417", Integer.parseInt(_col21.getText().toString()));} sql = sql + _col21.getText().toString() + delimit;
            if (!_col22.getText().toString().isEmpty()){reg.put("i418", Integer.parseInt(_col22.getText().toString()));} sql = sql + _col22.getText().toString() + delimit;
            if (!_col23.getText().toString().isEmpty()){reg.put("i419", Integer.parseInt(_col23.getText().toString()));} sql = sql + _col23.getText().toString() + delimit;
            if (!_col24.getText().toString().isEmpty()){reg.put("i420", Integer.parseInt(_col24.getText().toString()));} sql = sql + _col24.getText().toString() + delimit;
            if (!_col25.getText().toString().isEmpty()){reg.put("i421", Integer.parseInt(_col25.getText().toString()));} sql = sql + _col25.getText().toString() + delimit;
            if (!_col26.getText().toString().isEmpty()){reg.put("i422", Integer.parseInt(_col26.getText().toString()));} sql = sql + _col26.getText().toString() + delimit;
            if (!_col27.getText().toString().isEmpty()){reg.put("i423", Integer.parseInt(_col27.getText().toString()));} sql = sql + _col27.getText().toString() + delimit;
            if (!_col28.getText().toString().isEmpty()){reg.put("i424", Integer.parseInt(_col28.getText().toString()));} sql = sql + _col28.getText().toString() + delimit;
            if (!_col29.getText().toString().isEmpty()){reg.put("i425", Integer.parseInt(_col29.getText().toString()));} sql = sql + _col29.getText().toString() + delimit;
            if (!_col30.getText().toString().isEmpty()){reg.put("i426", Integer.parseInt(_col30.getText().toString()));} sql = sql + _col30.getText().toString() + delimit;
            if (!_col31.getText().toString().isEmpty()){reg.put("i427", Integer.parseInt(_col31.getText().toString()));} sql = sql + _col31.getText().toString() + delimit;
            if (!_col32.getText().toString().isEmpty()){reg.put("i428", Integer.parseInt(_col32.getText().toString()));} sql = sql + _col32.getText().toString() + delimit;
            if (!_col33.getText().toString().isEmpty()){reg.put("i429", Integer.parseInt(_col33.getText().toString()));} sql = sql + _col33.getText().toString() + delimit;
            if (!_col34.getText().toString().isEmpty()){reg.put("i430", Integer.parseInt(_col34.getText().toString()));} sql = sql + _col34.getText().toString() + delimit;
            if (!_col35.getText().toString().isEmpty()){reg.put("i431", Integer.parseInt(_col35.getText().toString()));} sql = sql + _col35.getText().toString() + delimit;
            if (!_col36.getText().toString().isEmpty()){reg.put("i432", Integer.parseInt(_col36.getText().toString()));} sql = sql + _col36.getText().toString() + delimit;

            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("i", null, reg); _IU="U";}
                else {dbSET.update("i", reg, "_id=1", null);}

                toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
                //Toast.makeText(getContext(), getResources().getString(R.string.str_bl_msj5), Toast.LENGTH_SHORT).show();
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
