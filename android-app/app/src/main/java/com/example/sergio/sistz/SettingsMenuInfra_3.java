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
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;

/**
 * Created by Sergio on 3/7/2016.
 */
public class SettingsMenuInfra_3 extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    EditText _col1, _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14, tables_count;
    String _IU="U";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_3,
                container, false);

        // ********************** Global vars ******************
        _col1 = (EditText) mLinearLayout.findViewById(R.id.et_col1);
        _col2 = (EditText) mLinearLayout.findViewById(R.id.et_col2);
        _col3 = (EditText) mLinearLayout.findViewById(R.id.et_col3);
        _col4 = (EditText) mLinearLayout.findViewById(R.id.et_col4);
        _col5 = (EditText) mLinearLayout.findViewById(R.id.et_col5);
        _col6 = (EditText) mLinearLayout.findViewById(R.id.et_col6);
        _col7 = (EditText) mLinearLayout.findViewById(R.id.et_col7);
        _col8 = (EditText) mLinearLayout.findViewById(R.id.et_col8);
        _col9 = (EditText) mLinearLayout.findViewById(R.id.et_col9);
        _col10 = (EditText) mLinearLayout.findViewById(R.id.et_col10);
        _col11 = (EditText) mLinearLayout.findViewById(R.id.et_col11);
        _col12 = (EditText) mLinearLayout.findViewById(R.id.et_col12);
        _col13 = (EditText) mLinearLayout.findViewById(R.id.et_col13);
        _col14 = (EditText) mLinearLayout.findViewById(R.id.et_col14);
        tables_count = (EditText) mLinearLayout.findViewById(R.id.et_tables);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part2);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_back = (ImageButton) mLinearLayout.findViewById(R.id.btn_back);
        ImageButton btn_next = (ImageButton) mLinearLayout.findViewById(R.id.btn_next);
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);
        ImageButton btn_exit = (ImageButton) mLinearLayout.findViewById(R.id.btn_exit);

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
        Cursor cur_data = dbSET.rawQuery("SELECT _id, g1a, g1b, g1c, g1d, g1e, g1f, g2a, g2b, g2c, g2d, g2e, g2f, g2g,  g24, flag, tables_count FROM g WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            _col1.setText(cur_data.getString(1));
            _col2.setText(cur_data.getString(2));
            _col3.setText(cur_data.getString(3));
            _col4.setText(cur_data.getString(4));
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
            tables_count.setText(cur_data.getString(16));
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
            reg.put("flag", _IU); // sql = sql + _IU + ";g;" + SettingsMenuInfra_0.EMIS_code + delimit;
            sql = sql + "g" + delimit + SettingsMenuInfra_0.EMIS_code + delimit;
            if (!_col1.getText().toString().isEmpty()){reg.put("g1a", Integer.parseInt(_col1.getText().toString()));} sql = sql + _col1.getText().toString() + delimit;
            if (!_col2.getText().toString().isEmpty()){reg.put("g1b", Integer.parseInt(_col2.getText().toString()));} sql = sql + _col2.getText().toString() + delimit;
            if (!_col3.getText().toString().isEmpty()){reg.put("g1c", Integer.parseInt(_col3.getText().toString()));} sql = sql + _col3.getText().toString() + delimit;
            if (!_col4.getText().toString().isEmpty()){reg.put("g1d", Integer.parseInt(_col4.getText().toString()));} sql = sql + _col4.getText().toString() + delimit;
            if (!_col5.getText().toString().isEmpty()){reg.put("g1e", Integer.parseInt(_col5.getText().toString()));} sql = sql + _col5.getText().toString() + delimit;
            if (!_col6.getText().toString().isEmpty()){reg.put("g1f", Integer.parseInt(_col6.getText().toString()));} sql = sql + _col6.getText().toString() + delimit;
            if (!_col7.getText().toString().isEmpty()){reg.put("g2a", Integer.parseInt(_col7.getText().toString()));} sql = sql + _col7.getText().toString() + delimit;
            if (!_col8.getText().toString().isEmpty()){reg.put("g2b", Integer.parseInt(_col8.getText().toString()));} sql = sql + _col8.getText().toString() + delimit;
            if (!_col9.getText().toString().isEmpty()){reg.put("g2c", Integer.parseInt(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
            if (!_col10.getText().toString().isEmpty()){reg.put("g2d", Integer.parseInt(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
            if (!_col11.getText().toString().isEmpty()){reg.put("g2e", Integer.parseInt(_col11.getText().toString()));} sql = sql + _col11.getText().toString() + delimit;
            if (!_col12.getText().toString().isEmpty()){reg.put("g2f", Integer.parseInt(_col12.getText().toString()));} sql = sql + _col12.getText().toString() + delimit;
            if (!_col13.getText().toString().isEmpty()){reg.put("g2g", Integer.parseInt(_col13.getText().toString()));} sql = sql + _col13.getText().toString() + delimit;
            if (!_col14.getText().toString().isEmpty()){reg.put("g24", Integer.parseInt(_col14.getText().toString()));} sql = sql + _col14.getText().toString() + delimit;
            if (!tables_count.getText().toString().isEmpty()){reg.put("tables_count", Integer.parseInt(tables_count.getText().toString()));} sql = sql + tables_count.getText().toString() + delimit;
            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("g", null, reg); _IU="U";}
                else {dbSET.update("g", reg, "_id=1", null);}

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
