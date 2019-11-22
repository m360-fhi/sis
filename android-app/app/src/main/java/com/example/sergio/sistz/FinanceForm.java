package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sergio on 3/15/2016.
 */
public class FinanceForm extends Activity implements View.OnClickListener, TextWatcher{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1; // ************ FrameLayout ***************
    EditText  _col_sn, _col_emis, _col1, _col2, _col3, _col4,_col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14, _col15, _col16, _col17, _col18, _col19, _col20, _col21, _col22, _col23, _col24, _col25, _col26;
    DatePicker _date;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat format = new DecimalFormat("###,###,###.##");
    DecimalFormat precision = new DecimalFormat("###,###,###.##");
    String strDate, col1, col2, col3, col4, col5="", col6="", col7="", col8="", col9="", col10="", col11="", col12="", col13="", col14="";
    int rg1_id ;
    int rg2_id;
    int rg3_id ;
    int rg4_id ;

    String _IU="U";
    String tmp_update="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_form);

        financeTable();

        // ********************** Global vars ******************
        _col_sn = (EditText) findViewById(R.id._col_sn);
        _col_emis = (EditText) findViewById(R.id._col_emis);
        _col1 = (EditText) findViewById(R.id._col1);
        _col2 = (EditText) findViewById(R.id._col2);
        _col3 = (EditText) findViewById(R.id._col3);
        _col4 = (EditText) findViewById(R.id._col4);
        _col5 = (EditText) findViewById(R.id._col5);
        _col6 = (EditText) findViewById(R.id._col6);
        _col7 = (EditText) findViewById(R.id._col7);
        _col8 = (EditText) findViewById(R.id._col8);
        _col9 = (EditText) findViewById(R.id._col9);
        _col10 = (EditText) findViewById(R.id._col10);
        _col11 = (EditText) findViewById(R.id._col11);
        _col12 = (EditText) findViewById(R.id._col12);
        _col13 = (EditText) findViewById(R.id._col13);
        _col14 = (EditText) findViewById(R.id._col14);
        _col15 = (EditText) findViewById(R.id._col15);
        _col16 = (EditText) findViewById(R.id._col16);
        _col17 = (EditText) findViewById(R.id._col17);
        _col18 = (EditText) findViewById(R.id._col18);
        _col19 = (EditText) findViewById(R.id._col19);
        _col20 = (EditText) findViewById(R.id._col20);
        _col21 = (EditText) findViewById(R.id._col21);
        _col22 = (EditText) findViewById(R.id._col22);
        _col23 = (EditText) findViewById(R.id._col23);
        _col24 = (EditText) findViewById(R.id._col24);
        _date = (DatePicker) findViewById(R.id._date);


        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);
        _col_sn.setEnabled(false);
        _col_emis.setEnabled(false);

        _col3.setEnabled(false);
        _col4.setEnabled(false);
        _col15.setEnabled(false);
        _col16.setEnabled(false);
        _col17.setEnabled(false);
        _col18.setEnabled(false);
        _col19.setEnabled(false);
        _col20.setEnabled(false);
        _col21.setEnabled(false);
        _col22.setEnabled(false);
        _col23.setEnabled(false);
        _col24.setEnabled(false);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        loadRecord();

        _col1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
        _col2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
        _col6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
        _col8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
        _col10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
        _col12.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
        _col14.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                totalBalance();
            }
        });
    }

    private void totalBalance() {
        col1=_col1.getText().toString();
        col2=_col2.getText().toString();
        col5=_col5.getText().toString();
        col6=_col6.getText().toString();
        col7=_col7.getText().toString();
        col8=_col8.getText().toString();
        col9=_col9.getText().toString();
        col10=_col10.getText().toString();
        col11=_col11.getText().toString();
        col12=_col12.getText().toString();
        col13=_col13.getText().toString();
        col14=_col14.getText().toString();

        if (col1.isEmpty()) {col1="0";}
        if (col2.isEmpty()) {col2="0";}
        if (col5.isEmpty()) {col5="0";}
        if (col6.isEmpty()) {col6="0";}
        if (col7.isEmpty()) {col7="0";}
        if (col8.isEmpty()) {col8="0";}
        if (col9.isEmpty()) {col9="0";}
        if (col10.isEmpty()) {col10="0";}
        if (col11.isEmpty()) {col11="0";}
        if (col12.isEmpty()) {col12="0";}
        if (col13.isEmpty()) {col13="0";}
        if (col14.isEmpty()) {col14="0";}

        _col3.setText(String.valueOf(Double.valueOf(col1) + Double.valueOf(col2)));

        // 20180213 --- se han agregado los if, para condicionar que no se graben numeros negativos.
        _col15.setText(String.valueOf(Double.valueOf(col5) * Double.valueOf(col6)));
        _col16.setText(String.valueOf(Double.valueOf(_col3.getText().toString()) - Double.valueOf(_col15.getText().toString()) ));
        _col17.setText(String.valueOf(Double.valueOf(col7) * Double.valueOf(col8)));
        _col18.setText(String.valueOf(Double.valueOf(_col16.getText().toString()) - Double.valueOf(_col17.getText().toString())));
        _col19.setText(String.valueOf(Double.valueOf(col9) * Double.valueOf(col10)));
        _col20.setText(String.valueOf(Double.valueOf(_col18.getText().toString()) - Double.valueOf(_col19.getText().toString())));
        _col21.setText(String.valueOf(Double.valueOf(col11) * Double.valueOf(col12)));
        _col22.setText(String.valueOf(Double.valueOf(_col20.getText().toString()) - Double.valueOf(_col21.getText().toString())));
        _col23.setText(String.valueOf(Double.valueOf(col13) * Double.valueOf(col14)));
        _col24.setText(String.valueOf(Double.valueOf(_col22.getText().toString()) - Double.valueOf(_col23.getText().toString())));
        if ((Double.valueOf(_col24.getText().toString()) < 0.00 )){
            dialogAlert(3);
            tmp_update="false";
        } else {
            _col4.setText(_col24.getText().toString());
            tmp_update="true";
        }

    }


    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, _date, emis, flag FROM finance WHERE _id=1",null);
        cur_data.moveToFirst();
        _col_sn.setText(getSchool_name());
        _col_emis.setText(getEMIS_code());
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
            _date.updateDate(Integer.parseInt(cur_data.getString(15).substring(0, 4)), Integer.parseInt(cur_data.getString(15).substring(5, 7)) - 1, Integer.parseInt(cur_data.getString(15).substring(8, 10)));
            totalBalance();
            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", s1;
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        if (_IU == "I" ) {reg.put("_id",1);}
        reg.put("flag", _IU); //sql = sql + _IU + ";s;" + SettingsMenuInfra_0.EMIS_code + delimit;

        int day = _date.getDayOfMonth();
        int month = _date.getMonth();
        int year = _date.getYear();
        calendar.set(year, month, day);
        strDate = formatDatabase.format(calendar.getTime());

        sql = sql + "finance" + delimit + strDate + delimit + _col_emis.getText().toString() + delimit;
        reg.put("_date", strDate);
        reg.put("emis", Integer.valueOf(_col_emis.getText().toString()));
        if (!_col1.getText().toString().isEmpty()){reg.put("f1", Double.valueOf(_col1.getText().toString()));} sql = sql + _col1.getText().toString() + delimit;
        if (!_col2.getText().toString().isEmpty()){reg.put("f2", Double.valueOf(_col2.getText().toString()));} sql = sql + _col2.getText().toString() + delimit;
        if (!_col3.getText().toString().isEmpty()){reg.put("f3", Double.valueOf(_col3.getText().toString()));} sql = sql + _col3.getText().toString() + delimit;
        if (!_col4.getText().toString().isEmpty()){reg.put("f4", Double.valueOf(_col4.getText().toString()));} sql = sql + _col4.getText().toString() + delimit;
        if (!_col5.getText().toString().isEmpty()){reg.put("f5", Double.valueOf(_col5.getText().toString()));} sql = sql + _col5.getText().toString() + delimit;
        if (!_col6.getText().toString().isEmpty()){reg.put("f6", Double.valueOf(_col6.getText().toString()));} sql = sql + _col6.getText().toString() + delimit;
        if (!_col7.getText().toString().isEmpty()){reg.put("f7", Double.valueOf(_col7.getText().toString()));} sql = sql + _col7.getText().toString() + delimit;
        if (!_col8.getText().toString().isEmpty()){reg.put("f8", Double.valueOf(_col8.getText().toString()));} sql = sql + _col8.getText().toString() + delimit;
        if (!_col9.getText().toString().isEmpty()){reg.put("f9", Double.valueOf(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
        if (!_col10.getText().toString().isEmpty()){reg.put("f10", Double.valueOf(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
        if (!_col11.getText().toString().isEmpty()){reg.put("f11", Double.valueOf(_col11.getText().toString()));} sql = sql + _col11.getText().toString() + delimit;
        if (!_col12.getText().toString().isEmpty()){reg.put("f12", Double.valueOf(_col12.getText().toString()));} sql = sql + _col12.getText().toString() + delimit;
        if (!_col13.getText().toString().isEmpty()){reg.put("f13", Double.valueOf(_col13.getText().toString()));} sql = sql + _col13.getText().toString() + delimit;
        if (!_col14.getText().toString().isEmpty()){reg.put("f14", Double.valueOf(_col14.getText().toString()));} sql = sql + _col14.getText().toString() + delimit;
        sql = sql + _IU;
        try {
            if (tmp_update.equals("true")) { // 20180213 verifica que el gasto sea menor al monto disponible
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("finance", null, reg); _IU="U";}
                else {dbSET.update("finance", reg, "_id=1", null);}

                toolsfncs.dialogAlertConfirm(this,getResources(),9);
            }

        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.str_bl2_msj1), Toast.LENGTH_SHORT).show();
        }

        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                if (tmp_update.equals("false")){
                    dialogAlert(3);
                } else {
                    dialogAlert(1);
                }
                break;
        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        final int tmp_v=v;
        //Toast.makeText(getContext(),String.valueOf(v) ,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj2));}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage(getResources().getString(R.string.str_balance));}
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                if (tmp_v==1) {
                    aceptar();
                }

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

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}

        return getemiscode;
    }

    public String getSchool_name(){
        String getSchoolName="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT flag FROM ms_0", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getSchoolName = cur_data.getString(0);} else {getSchoolName = "";}
        }catch (Exception e) {}

        return getSchoolName;
    }

    private void financeTable(){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            dbSET.execSQL("CREATE TABLE IF NOT EXISTS finance (_id INTEGER, _date DATE, emis INTEGER,f1 NUMERIC,f2 NUMERIC,f3 NUMERIC,f4 NUMERIC,f5 NUMERIC,f6 NUMERIC,f7 NUMERIC,f8 NUMERIC,f9 NUMERIC, f10 NUMERIC,f11 NUMERIC,f12 NUMERIC,f13 NUMERIC,f14 NUMERIC,flag TEXT)");
        }catch (Exception e) {}
    }

    public static String formatAmount(int num)
    {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
        DecimalFormatSymbols decimalFormateSymbol = new DecimalFormatSymbols();
        decimalFormateSymbol.setGroupingSeparator(',');
        decimalFormat.setDecimalFormatSymbols(decimalFormateSymbol);
        return decimalFormat.format(num);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
