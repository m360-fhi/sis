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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;

/**
 * Created by Sergio on 2/26/2016.
 */
public class SettingsMenuInfra_4_forms extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2, fl_part3; // ************ FrameLayout ***************
    EditText _col1, _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14, _col15, _col16, _col17, _col18, _col19, _col20, _col21, _col22, _col23, _col24, _col25, _col26, _col27, _col28, _col29, _col30, _col31, _col32, _col33, _col34, _col35, _col36, _col37, _col38, _col39, _col40, _col41, _col42, _col43, _col44, _col45, _col46, _col47, _col48, _col49, _col50, _col51, _col52, _col53, _col54, _col55, _col56, _col57, _col58, _col59, _col60, _col61, _col62, _col63, _col64;
    TextView tv_formselected;
    RadioButton form1, form2, form3, form4, form5, form6;
    String _IU="U", form = "f1";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_4_forms,
                container, false);

        // ********************** Global vars ******************
        tv_formselected = (TextView) mLinearLayout.findViewById(R.id.tv_formselected);
        _col1 = (EditText) mLinearLayout.findViewById(R.id._col1);
        _col2 = (EditText) mLinearLayout.findViewById(R.id._col2);
        _col3 = (EditText) mLinearLayout.findViewById(R.id._col3);
        _col4 = (EditText) mLinearLayout.findViewById(R.id._col4);
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
        _col37 = (EditText) mLinearLayout.findViewById(R.id._col37);
        _col38 = (EditText) mLinearLayout.findViewById(R.id._col38);
        _col39 = (EditText) mLinearLayout.findViewById(R.id._col39);
        _col40 = (EditText) mLinearLayout.findViewById(R.id._col40);
        _col41 = (EditText) mLinearLayout.findViewById(R.id._col41);
        _col42 = (EditText) mLinearLayout.findViewById(R.id._col42);
        _col43 = (EditText) mLinearLayout.findViewById(R.id._col43);
        _col44 = (EditText) mLinearLayout.findViewById(R.id._col44);
        _col45 = (EditText) mLinearLayout.findViewById(R.id._col45);
        _col46 = (EditText) mLinearLayout.findViewById(R.id._col46);
        _col47 = (EditText) mLinearLayout.findViewById(R.id._col47);
        _col48 = (EditText) mLinearLayout.findViewById(R.id._col48);
        _col49 = (EditText) mLinearLayout.findViewById(R.id._col49);
        _col50 = (EditText) mLinearLayout.findViewById(R.id._col50);
        _col51 = (EditText) mLinearLayout.findViewById(R.id._col51);
        _col52 = (EditText) mLinearLayout.findViewById(R.id._col52);
        _col53 = (EditText) mLinearLayout.findViewById(R.id._col53);
        _col54 = (EditText) mLinearLayout.findViewById(R.id._col54);
        _col55 = (EditText) mLinearLayout.findViewById(R.id._col55);
        _col56 = (EditText) mLinearLayout.findViewById(R.id._col56);
        _col57 = (EditText) mLinearLayout.findViewById(R.id._col57);
        _col58 = (EditText) mLinearLayout.findViewById(R.id._col58);
        _col59 = (EditText) mLinearLayout.findViewById(R.id._col59);
        _col60 = (EditText) mLinearLayout.findViewById(R.id._col60);
        _col61 = (EditText) mLinearLayout.findViewById(R.id._col61);
        _col62 = (EditText) mLinearLayout.findViewById(R.id._col62);
        _col63 = (EditText) mLinearLayout.findViewById(R.id._col63);
        _col64 = (EditText) mLinearLayout.findViewById(R.id._col64);


        form1  = (RadioButton) mLinearLayout.findViewById(R.id.form1);
        form2  = (RadioButton) mLinearLayout.findViewById(R.id.form2);
        form3  = (RadioButton) mLinearLayout.findViewById(R.id.form3);
        form4  = (RadioButton) mLinearLayout.findViewById(R.id.form4);
        form5  = (RadioButton) mLinearLayout.findViewById(R.id.form5);
        form6  = (RadioButton) mLinearLayout.findViewById(R.id.form6);


        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);
        form1.setOnClickListener(this);
        form2.setOnClickListener(this);
        form3.setOnClickListener(this);
        form4.setOnClickListener(this);
        form5.setOnClickListener(this);
        form6.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        form1.setChecked(true);
        loadRecord(form);
        
        return mLinearLayout;
    }

    // **************** Load DATA *************************
    public void loadRecord(String form) {
        clearRecord();
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sqlForms ="";
        sqlForms = "SELECT _id, _1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _23, _24, _25, _26, _27, _28, _29, _30, _31, _32, _33, _34, _35, _36, _37, _38, _39, _40, _41, _42, _43, _44, _45, _46, _47, _48, _49, _50, _51, _52, _53, _54, _55, _56, _57, _58, _59, _60, _61, _62, _63, _64, flag FROM i" +form.toString() + "  WHERE _id=1";
        Cursor cur_data = dbSET.rawQuery(sqlForms,null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            _col1.setText(cur_data.getString(1));
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
            _col37.setText(cur_data.getString(37));
            _col38.setText(cur_data.getString(38));
            _col39.setText(cur_data.getString(39));
            _col40.setText(cur_data.getString(40));
            _col41.setText(cur_data.getString(41));
            _col42.setText(cur_data.getString(42));
            _col43.setText(cur_data.getString(43));
            _col44.setText(cur_data.getString(44));
            _col45.setText(cur_data.getString(45));
            _col46.setText(cur_data.getString(46));
            _col47.setText(cur_data.getString(47));
            _col48.setText(cur_data.getString(48));
            _col49.setText(cur_data.getString(49));
            _col50.setText(cur_data.getString(50));
            _col51.setText(cur_data.getString(51));
            _col52.setText(cur_data.getString(52));
            _col53.setText(cur_data.getString(53));
            _col54.setText(cur_data.getString(54));
            _col55.setText(cur_data.getString(55));
            _col56.setText(cur_data.getString(56));
            _col57.setText(cur_data.getString(57));
            _col58.setText(cur_data.getString(58));
            _col59.setText(cur_data.getString(59));
            _col60.setText(cur_data.getString(60));
            _col61.setText(cur_data.getString(61));
            _col62.setText(cur_data.getString(62));
            _col63.setText(cur_data.getString(63));
            _col64.setText(cur_data.getString(64));

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
            reg.put("flag", _IU); //sql = sql + _IU + ";i" + form.toString() +";" + SettingsMenuInfra_0.EMIS_code + delimit;
            sql = sql + "i" + form.toString() + delimit + SettingsMenuInfra_0.EMIS_code + delimit;
            if (!_col1.getText().toString().isEmpty()){reg.put("_1", Integer.parseInt(_col1.getText().toString()));} sql = sql + _col1.getText().toString() + delimit;
            if (!_col2.getText().toString().isEmpty()){reg.put("_2", Integer.parseInt(_col2.getText().toString()));} sql = sql + _col2.getText().toString() + delimit;
            if (!_col3.getText().toString().isEmpty()){reg.put("_3", Integer.parseInt(_col3.getText().toString()));} sql = sql + _col3.getText().toString() + delimit;
            if (!_col4.getText().toString().isEmpty()){reg.put("_4", Integer.parseInt(_col4.getText().toString()));} sql = sql + _col4.getText().toString() + delimit;
            if (!_col5.getText().toString().isEmpty()){reg.put("_5", Integer.parseInt(_col5.getText().toString()));} sql = sql + _col5.getText().toString() + delimit;
            if (!_col6.getText().toString().isEmpty()){reg.put("_6", Integer.parseInt(_col6.getText().toString()));} sql = sql + _col6.getText().toString() + delimit;
            if (!_col7.getText().toString().isEmpty()){reg.put("_7", Integer.parseInt(_col7.getText().toString()));} sql = sql + _col7.getText().toString() + delimit;
            if (!_col8.getText().toString().isEmpty()){reg.put("_8", Integer.parseInt(_col8.getText().toString()));} sql = sql + _col8.getText().toString() + delimit;
            if (!_col9.getText().toString().isEmpty()){reg.put("_9", Integer.parseInt(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
            if (!_col10.getText().toString().isEmpty()){reg.put("_10", Integer.parseInt(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
            if (!_col11.getText().toString().isEmpty()){reg.put("_11", Integer.parseInt(_col11.getText().toString()));} sql = sql + _col11.getText().toString() + delimit;
            if (!_col12.getText().toString().isEmpty()){reg.put("_12", Integer.parseInt(_col12.getText().toString()));} sql = sql + _col12.getText().toString() + delimit;
            if (!_col13.getText().toString().isEmpty()){reg.put("_13", Integer.parseInt(_col13.getText().toString()));} sql = sql + _col13.getText().toString() + delimit;
            if (!_col14.getText().toString().isEmpty()){reg.put("_14", Integer.parseInt(_col14.getText().toString()));} sql = sql + _col14.getText().toString() + delimit;
            if (!_col15.getText().toString().isEmpty()){reg.put("_15", Integer.parseInt(_col15.getText().toString()));} sql = sql + _col15.getText().toString() + delimit;
            if (!_col16.getText().toString().isEmpty()){reg.put("_16", Integer.parseInt(_col16.getText().toString()));} sql = sql + _col16.getText().toString() + delimit;
            if (!_col17.getText().toString().isEmpty()){reg.put("_17", Integer.parseInt(_col17.getText().toString()));} sql = sql + _col17.getText().toString() + delimit;
            if (!_col18.getText().toString().isEmpty()){reg.put("_18", Integer.parseInt(_col18.getText().toString()));} sql = sql + _col18.getText().toString() + delimit;
            if (!_col19.getText().toString().isEmpty()){reg.put("_19", Integer.parseInt(_col19.getText().toString()));} sql = sql + _col19.getText().toString() + delimit;
            if (!_col20.getText().toString().isEmpty()){reg.put("_20", Integer.parseInt(_col20.getText().toString()));} sql = sql + _col20.getText().toString() + delimit;
            if (!_col21.getText().toString().isEmpty()){reg.put("_21", Integer.parseInt(_col21.getText().toString()));} sql = sql + _col21.getText().toString() + delimit;
            if (!_col22.getText().toString().isEmpty()){reg.put("_22", Integer.parseInt(_col22.getText().toString()));} sql = sql + _col22.getText().toString() + delimit;
            if (!_col23.getText().toString().isEmpty()){reg.put("_23", Integer.parseInt(_col23.getText().toString()));} sql = sql + _col23.getText().toString() + delimit;
            if (!_col24.getText().toString().isEmpty()){reg.put("_24", Integer.parseInt(_col24.getText().toString()));} sql = sql + _col24.getText().toString() + delimit;
            if (!_col25.getText().toString().isEmpty()){reg.put("_25", Integer.parseInt(_col25.getText().toString()));} sql = sql + _col25.getText().toString() + delimit;
            if (!_col26.getText().toString().isEmpty()){reg.put("_26", Integer.parseInt(_col26.getText().toString()));} sql = sql + _col26.getText().toString() + delimit;
            if (!_col27.getText().toString().isEmpty()){reg.put("_27", Integer.parseInt(_col27.getText().toString()));} sql = sql + _col27.getText().toString() + delimit;
            if (!_col28.getText().toString().isEmpty()){reg.put("_28", Integer.parseInt(_col28.getText().toString()));} sql = sql + _col28.getText().toString() + delimit;
            if (!_col29.getText().toString().isEmpty()){reg.put("_29", Integer.parseInt(_col29.getText().toString()));} sql = sql + _col29.getText().toString() + delimit;
            if (!_col30.getText().toString().isEmpty()){reg.put("_30", Integer.parseInt(_col30.getText().toString()));} sql = sql + _col30.getText().toString() + delimit;
            if (!_col31.getText().toString().isEmpty()){reg.put("_31", Integer.parseInt(_col31.getText().toString()));} sql = sql + _col31.getText().toString() + delimit;
            if (!_col32.getText().toString().isEmpty()){reg.put("_32", Integer.parseInt(_col32.getText().toString()));} sql = sql + _col32.getText().toString() + delimit;
            if (!_col33.getText().toString().isEmpty()){reg.put("_33", Integer.parseInt(_col33.getText().toString()));} sql = sql + _col33.getText().toString() + delimit;
            if (!_col34.getText().toString().isEmpty()){reg.put("_34", Integer.parseInt(_col34.getText().toString()));} sql = sql + _col34.getText().toString() + delimit;
            if (!_col35.getText().toString().isEmpty()){reg.put("_35", Integer.parseInt(_col35.getText().toString()));} sql = sql + _col35.getText().toString() + delimit;
            if (!_col36.getText().toString().isEmpty()){reg.put("_36", Integer.parseInt(_col36.getText().toString()));} sql = sql + _col36.getText().toString() + delimit;
            if (!_col37.getText().toString().isEmpty()){reg.put("_37", Integer.parseInt(_col37.getText().toString()));} sql = sql + _col37.getText().toString() + delimit;
            if (!_col38.getText().toString().isEmpty()){reg.put("_38", Integer.parseInt(_col38.getText().toString()));} sql = sql + _col38.getText().toString() + delimit;
            if (!_col39.getText().toString().isEmpty()){reg.put("_39", Integer.parseInt(_col39.getText().toString()));} sql = sql + _col39.getText().toString() + delimit;
            if (!_col40.getText().toString().isEmpty()){reg.put("_40", Integer.parseInt(_col40.getText().toString()));} sql = sql + _col40.getText().toString() + delimit;
            if (!_col41.getText().toString().isEmpty()){reg.put("_41", Integer.parseInt(_col41.getText().toString()));} sql = sql + _col41.getText().toString() + delimit;
            if (!_col42.getText().toString().isEmpty()){reg.put("_42", Integer.parseInt(_col42.getText().toString()));} sql = sql + _col42.getText().toString() + delimit;
            if (!_col43.getText().toString().isEmpty()){reg.put("_43", Integer.parseInt(_col43.getText().toString()));} sql = sql + _col43.getText().toString() + delimit;
            if (!_col44.getText().toString().isEmpty()){reg.put("_44", Integer.parseInt(_col44.getText().toString()));} sql = sql + _col44.getText().toString() + delimit;
            if (!_col45.getText().toString().isEmpty()){reg.put("_45", Integer.parseInt(_col45.getText().toString()));} sql = sql + _col45.getText().toString() + delimit;
            if (!_col46.getText().toString().isEmpty()){reg.put("_46", Integer.parseInt(_col46.getText().toString()));} sql = sql + _col46.getText().toString() + delimit;
            if (!_col47.getText().toString().isEmpty()){reg.put("_47", Integer.parseInt(_col47.getText().toString()));} sql = sql + _col47.getText().toString() + delimit;
            if (!_col48.getText().toString().isEmpty()){reg.put("_48", Integer.parseInt(_col48.getText().toString()));} sql = sql + _col48.getText().toString() + delimit;
            if (!_col49.getText().toString().isEmpty()){reg.put("_49", Integer.parseInt(_col49.getText().toString()));} sql = sql + _col49.getText().toString() + delimit;
            if (!_col50.getText().toString().isEmpty()){reg.put("_50", Integer.parseInt(_col50.getText().toString()));} sql = sql + _col50.getText().toString() + delimit;
            if (!_col51.getText().toString().isEmpty()){reg.put("_51", Integer.parseInt(_col51.getText().toString()));} sql = sql + _col51.getText().toString() + delimit;
            if (!_col52.getText().toString().isEmpty()){reg.put("_52", Integer.parseInt(_col52.getText().toString()));} sql = sql + _col52.getText().toString() + delimit;
            if (!_col53.getText().toString().isEmpty()){reg.put("_53", Integer.parseInt(_col53.getText().toString()));} sql = sql + _col53.getText().toString() + delimit;
            if (!_col54.getText().toString().isEmpty()){reg.put("_54", Integer.parseInt(_col54.getText().toString()));} sql = sql + _col54.getText().toString() + delimit;
            if (!_col55.getText().toString().isEmpty()){reg.put("_55", Integer.parseInt(_col55.getText().toString()));} sql = sql + _col55.getText().toString() + delimit;
            if (!_col56.getText().toString().isEmpty()){reg.put("_56", Integer.parseInt(_col56.getText().toString()));} sql = sql + _col56.getText().toString() + delimit;
            if (!_col57.getText().toString().isEmpty()){reg.put("_57", Integer.parseInt(_col57.getText().toString()));} sql = sql + _col57.getText().toString() + delimit;
            if (!_col58.getText().toString().isEmpty()){reg.put("_58", Integer.parseInt(_col58.getText().toString()));} sql = sql + _col58.getText().toString() + delimit;
            if (!_col59.getText().toString().isEmpty()){reg.put("_59", Integer.parseInt(_col59.getText().toString()));} sql = sql + _col59.getText().toString() + delimit;
            if (!_col60.getText().toString().isEmpty()){reg.put("_60", Integer.parseInt(_col60.getText().toString()));} sql = sql + _col60.getText().toString() + delimit;
            if (!_col61.getText().toString().isEmpty()){reg.put("_61", Integer.parseInt(_col61.getText().toString()));} sql = sql + _col61.getText().toString() + delimit;
            if (!_col62.getText().toString().isEmpty()){reg.put("_62", Integer.parseInt(_col62.getText().toString()));} sql = sql + _col62.getText().toString() + delimit;
            if (!_col63.getText().toString().isEmpty()){reg.put("_63", Integer.parseInt(_col63.getText().toString()));} sql = sql + _col63.getText().toString() + delimit;
            if (!_col64.getText().toString().isEmpty()){reg.put("_64", Integer.parseInt(_col64.getText().toString()));} sql = sql + _col64.getText().toString() + delimit;

            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("i" + form.toString(), null, reg); _IU="U";}
                else {dbSET.update("i" + form.toString(), reg, "_id=1", null);}

                toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
            } catch (Exception e) {
                Toast.makeText(getContext(), getResources().getString(R.string.str_bl2_msj1), Toast.LENGTH_SHORT).show();
            }
        dbSET.close();
        cnSET.close();
    }


    private void clearRecord(){
        _col1.setText("");
        _col1.setText("");
        _col2.setText("");
        _col3.setText("");
        _col4.setText("");
        _col5.setText("");
        _col6.setText("");
        _col7.setText("");
        _col8.setText("");
        _col9.setText("");
        _col10.setText("");
        _col11.setText("");
        _col12.setText("");
        _col13.setText("");
        _col14.setText("");
        _col15.setText("");
        _col16.setText("");
        _col17.setText("");
        _col18.setText("");
        _col19.setText("");
        _col20.setText("");
        _col21.setText("");
        _col22.setText("");
        _col23.setText("");
        _col24.setText("");
        _col25.setText("");
        _col26.setText("");
        _col27.setText("");
        _col28.setText("");
        _col29.setText("");
        _col30.setText("");
        _col31.setText("");
        _col32.setText("");
        _col33.setText("");
        _col34.setText("");
        _col35.setText("");
        _col36.setText("");
        _col37.setText("");
        _col38.setText("");
        _col39.setText("");
        _col40.setText("");
        _col41.setText("");
        _col42.setText("");
        _col43.setText("");
        _col44.setText("");
        _col45.setText("");
        _col46.setText("");
        _col47.setText("");
        _col48.setText("");
        _col49.setText("");
        _col50.setText("");
        _col51.setText("");
        _col52.setText("");
        _col53.setText("");
        _col54.setText("");
        _col55.setText("");
        _col56.setText("");
        _col57.setText("");
        _col58.setText("");
        _col59.setText("");
        _col60.setText("");
        _col61.setText("");
        _col62.setText("");
        _col63.setText("");
        _col64.setText("");
        _col7.setText("");
    }
    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                dialogAlert(1);
                //updateRecord();
                break;
            case R.id.form1:
                form = "f1"; tv_formselected.setText(getResources().getString(R.string.str_g_f1));
                loadRecord(form);
                break;
            case R.id.form2:
                form = "f2"; tv_formselected.setText(getResources().getString(R.string.str_g_f2));
                loadRecord(form);
                break;
            case R.id.form3:
                form = "f3"; tv_formselected.setText(getResources().getString(R.string.str_g_f3));
                loadRecord(form);
                break;
            case R.id.form4:
                form = "f4"; tv_formselected.setText(getResources().getString(R.string.str_g_f4));
                loadRecord(form);
                break;
            case R.id.form5:
                form = "f5"; tv_formselected.setText(getResources().getString(R.string.str_g_f5));
                loadRecord(form);
                break;
            case R.id.form6:
                form = "f6"; tv_formselected.setText(getResources().getString(R.string.str_g_f6));
                loadRecord(form);
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
    public void aceptar() {
        updateRecord();
        loadRecord(form);
    }
    public void cancelar() {} //getActivity().finish();}
    // *********** END Control Alerts ************************


}
