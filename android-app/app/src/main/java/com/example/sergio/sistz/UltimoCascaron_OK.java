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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;

import java.io.File;

/**
 * Created by Sergio on 2/26/2016.
 */
public class UltimoCascaron_OK extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    EditText _col1, _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14;
    private final static String[] lista_datos = { "Perro", "Gato", "Caballo", "Canario", "Vaca", "Cerdo" };
    private final static String[] lista_datos_accion = { "ladra", "maulla", "relincha", "canta", "muje", "no se" };
    private final String[] lista_datos_accion_hace = new String[4];
    public Spinner sp_g1, sp_g2, sp_g3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ultimo_cascaron_ok);
        _col7 = (EditText) findViewById(R.id.et_col7);
        _col8 = (EditText) findViewById(R.id.et_col8);
        _col9 = (EditText) findViewById(R.id.et_col9);
        _col10 = (EditText) findViewById(R.id.et_col10);
        _col11 = (EditText) findViewById(R.id.et_col11);
        _col12 = (EditText) findViewById(R.id.et_col12);
        _col13 = (EditText) findViewById(R.id.et_col13);
        _col14 = (EditText) findViewById(R.id.et_col14);

        sp_g1 = (Spinner) findViewById(R.id.sp_g1);
        sp_g2 = (Spinner) findViewById(R.id.sp_g2);
        sp_g3 = (Spinner) findViewById(R.id.sp_g3);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        ImageButton btn_next = (ImageButton) findViewById(R.id.btn_next);
        ImageButton btn_save = (ImageButton) findViewById(R.id.btn_save);
        ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);
        fl_part2.setVisibility(View.GONE);

        // **************** CLICK ON BUTTONS ********************
        btn_next.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        // **************** llenando el spiner *****************
        loadSpinner();


        // ***************** LOCAD INFORMATION *************************
        loadRecord();



    }


    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, solid, semisolid, makeshift, partitioned, openair_utt, other, chairs, desks1, desks2, desk3, benches1, benches2, benches3, chalkboard\n" +
                "FROM set_school_pp_operation WHERE _id=1",null);
        cur_data.moveToFirst();
                _col7.setText(cur_data.getString(7));
                _col8.setText(cur_data.getString(8));
                _col9.setText(cur_data.getString(9));
                _col10.setText(cur_data.getString(10));
                _col11.setText(cur_data.getString(11));
                _col12.setText(cur_data.getString(12));
                _col13.setText(cur_data.getString(13));
                _col14.setText(cur_data.getString(14));
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
            if (!_col7.getText().toString().isEmpty()){reg.put("chairs", Integer.parseInt(_col7.getText().toString()));}
            if (!_col8.getText().toString().isEmpty()){reg.put("desks1", Integer.parseInt(_col8.getText().toString()));}
            if (!_col9.getText().toString().isEmpty()){reg.put("desks2", Integer.parseInt(_col9.getText().toString()));}
            if (!_col10.getText().toString().isEmpty()){reg.put("desks3", Integer.parseInt(_col10.getText().toString()));}
            if (!_col11.getText().toString().isEmpty()){reg.put("benches1", Integer.parseInt(_col11.getText().toString()));}
            if (!_col12.getText().toString().isEmpty()){reg.put("benches2", Integer.parseInt(_col12.getText().toString()));}
            if (!_col13.getText().toString().isEmpty()){reg.put("benches3", Integer.parseInt(_col13.getText().toString()));}
            if (!_col14.getText().toString().isEmpty()){reg.put("chalkboard", Integer.parseInt(_col14.getText().toString()));}
        try {
            dbSET.update("set_school_pp_operation", reg, "_id=1", null);
            Toast.makeText(getApplicationContext(),"The information has been updated!!!",Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Debe ingresar al menos un registro... !!! ",Toast.LENGTH_SHORT).show();
        }

        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    //@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                fl_location++;
                change_fl(fl_location);
                break;
            case R.id.btn_back:
                fl_location--;
                change_fl(fl_location);
                break;
            case R.id.btn_save:
                //dialogAlert(1);
                updateRecord();
                break;
            case R.id.btn_exit:
                dialogAlert(2);
                break;
        }

    }

    // *********** Control change page
    public void change_fl(Integer fl) {
        if (fl > 2) {fl_location=1;}
        if (fl < 1) {fl_location=2;}
        switch (fl_location){
            case 1:
                fl_part2.setVisibility(View.GONE);
                fl_part1.setVisibility(View.VISIBLE);
                break;
            case 2:
                fl_part1.setVisibility(View.GONE);
                fl_part2.setVisibility(View.VISIBLE);
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

    // ************** Load Spinner GEO ***********************
    public void loadSpinner(){
        ArrayAdapter animals = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,lista_datos);
        sp_g1.setAdapter(animals);

        this.sp_g1.setOnItemSelectedListener(this);
        this.sp_g2.setOnItemSelectedListener(this);
        this.sp_g3.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_g1:
                String spg1 = sp_g1.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),spg1,Toast.LENGTH_LONG).show();
                ArrayAdapter accion = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,lista_datos_accion);
                sp_g2.setAdapter(accion);
                break;
            case R.id.sp_g2:
                String spg2 = sp_g2.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),spg2,Toast.LENGTH_LONG).show();
                if (spg2=="canta") {
                    lista_datos_accion_hace[0] = "cero";
                    lista_datos_accion_hace[1] = "uno";
                    lista_datos_accion_hace[2] = "dos";
                    lista_datos_accion_hace[3] = "tres";
                    ArrayAdapter accion_hace = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,lista_datos_accion_hace);
                    sp_g3.setAdapter(accion_hace);
                    sp_g3.setSelection(getIndex(sp_g3,"tres"));
                }
                break;
            case R.id.sp_g3:
                if (!sp_g3.getSelectedItem().toString().isEmpty()) {
                    String spg3 = sp_g3.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),spg3,Toast.LENGTH_LONG).show();
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }


    // *********** END Control Alerts ************************
}
