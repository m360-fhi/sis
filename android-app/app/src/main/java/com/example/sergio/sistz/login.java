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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;

/**
 * Created by Eduardo on 09/03/2016.
 */
public class login extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1; // ************ FrameLayout ***************
    TextView _col1, _col3; // ********* HABILITAR Password y URL, cambiar TextView a EditText aqui y en el XML
    EditText _col2;
    String _IU="U";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // ********************** Global vars ******************
        _col1 = (TextView) findViewById(R.id.et_col1);
        _col2 = (EditText) findViewById(R.id.et_col2);
        _col3 = (TextView) findViewById(R.id.et_col3);
        _col2.setFocusableInTouchMode(true); // **** habilita edición

        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) findViewById(R.id.btn_save);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        loadRecord();

    }


    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, password, mobil_server, server_IP FROM login WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            _col1.setText(cur_data.getString(1));
            _col2.setText(cur_data.getString(2));
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
            if (_IU == "I" ) {reg.put("_id",1);}

            if (!_col1.getText().toString().isEmpty()){reg.put("password", _col1.getText().toString()); sql = sql + _col1.getText().toString() + delimit;}
            if (!_col2.getText().toString().isEmpty()){reg.put("mobil_server", _col2.getText().toString()); sql = sql + _col2.getText().toString() + delimit;}
            if (!_col3.getText().toString().isEmpty()){reg.put("server_IP", _col3.getText().toString()); sql = sql + _col3.getText().toString() + delimit;}

            try {
                if (_IU=="I") {dbSET.insert("login", null, reg); _IU="U";}
                else {dbSET.update("login", reg, "_id=1", null);}
                toolsfncs.dialogAlertConfirm(this,getResources(),9);
            }catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Debe ingresar al menos un registro... !!! ",Toast.LENGTH_SHORT).show();
            }
        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_save:
                updateRecord();
                break;
        }

    }


    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        Toast.makeText(getApplicationContext(),String.valueOf(v) ,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Save and Exit !!!");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {   }
    public void cancelar() {finish(); }
    // *********** END Control Alerts ************************
}
