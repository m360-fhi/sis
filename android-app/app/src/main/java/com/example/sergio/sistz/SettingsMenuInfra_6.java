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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;
import com.google.android.gms.vision.Frame;

import java.io.File;

/**
 * Created by Sergio on 2/26/2016.
 */
public class SettingsMenuInfra_6 extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2;
    LinearLayout ll_q3,fl_part3; // ************ FrameLayout ***************
    CheckBox _col1, _col2, _col3, _col4, _col5,_col21,_col22,_col23,_col24,_col25,_col26,_col27,_col28,_col29,_col30,_col31,_col32,_col33,_col34,_col35,_col36,_col37,_col38,_col39,_col40,_col41,_col42;
    EditText  _col8, _col9, _col10, _col15, _col16, _col17, _col18, _col19, _col20, _col43, _col44, _col45, _col46;
    // 20180915 add new column _col11c,_col11d,
    RadioGroup water;
    RadioButton _col6a, _col6b, _col7a, _col7b, _col11a, _col11b, _col11c, _col11d, _col12a, _col12b, _col12c, _col13a, _col13b, _col13c, _col13d, _col13e, _col13f, _col14a, _col14b, _col14c;
    RadioButton _col13a_31,_col13b_31,_col13c_31; // 20180915 Add new column for drinking water
    CheckBox _col13a_33, _col13b_33, _col13c_33, _col13d_33, _col13e_33;
    String _IU="U";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_6,
                container, false);

        // ********************** Global vars ******************
        _col1 = (CheckBox) mLinearLayout.findViewById(R.id._col1);
        _col2 = (CheckBox) mLinearLayout.findViewById(R.id._col2);
        _col3 = (CheckBox) mLinearLayout.findViewById(R.id._col3);
        _col4 = (CheckBox) mLinearLayout.findViewById(R.id._col4);
        _col5 = (CheckBox) mLinearLayout.findViewById(R.id._col5);
        _col6a = (RadioButton) mLinearLayout.findViewById(R.id._col6a);
        _col6b = (RadioButton) mLinearLayout.findViewById(R.id._col6b);
        _col7a = (RadioButton) mLinearLayout.findViewById(R.id._col7a);
        _col7b = (RadioButton) mLinearLayout.findViewById(R.id._col7b);
        _col8 = (EditText) mLinearLayout.findViewById(R.id._col8);
        _col9 = (EditText) mLinearLayout.findViewById(R.id._col9);
        _col10 = (EditText) mLinearLayout.findViewById(R.id._col10);
        _col11a = (RadioButton) mLinearLayout.findViewById(R.id._col11a);
        _col11b = (RadioButton) mLinearLayout.findViewById(R.id._col11b);
        _col11c = (RadioButton) mLinearLayout.findViewById(R.id._col11c);
        _col11d = (RadioButton) mLinearLayout.findViewById(R.id._col11d);
        water = (RadioGroup) mLinearLayout.findViewById(R.id.rg_water);
        _col12a = (RadioButton) mLinearLayout.findViewById(R.id._col12a);
        _col12b = (RadioButton) mLinearLayout.findViewById(R.id._col12b);
        _col12c = (RadioButton) mLinearLayout.findViewById(R.id._col12c);

        _col13a_31 = (RadioButton) mLinearLayout.findViewById(R.id._col13a_31);
        _col13b_31 = (RadioButton) mLinearLayout.findViewById(R.id._col13b_31);
        _col13c_31 = (RadioButton) mLinearLayout.findViewById(R.id._col13c_31);

        _col13a = (RadioButton) mLinearLayout.findViewById(R.id._col13a);
        _col13b = (RadioButton) mLinearLayout.findViewById(R.id._col13b);
        _col13c = (RadioButton) mLinearLayout.findViewById(R.id._col13c);
        //_col13d = (RadioButton) mLinearLayout.findViewById(R.id._col13d);
        _col13d = (RadioButton) mLinearLayout.findViewById(R.id._col13d);
        _col13e = (RadioButton) mLinearLayout.findViewById(R.id._col13e);

        _col13a_33 = (CheckBox) mLinearLayout.findViewById(R.id._col13a_33);
        _col13b_33 = (CheckBox) mLinearLayout.findViewById(R.id._col13b_33);
        _col13c_33 = (CheckBox) mLinearLayout.findViewById(R.id._col13c_33);
        _col13d_33 = (CheckBox) mLinearLayout.findViewById(R.id._col13d_33);
        _col13e_33 = (CheckBox) mLinearLayout.findViewById(R.id._col13e_33);

        _col14a = (RadioButton) mLinearLayout.findViewById(R.id._col14a);
        _col14b = (RadioButton) mLinearLayout.findViewById(R.id._col14b);
        _col14c = (RadioButton) mLinearLayout.findViewById(R.id._col14c);

        _col15 = (EditText) mLinearLayout.findViewById(R.id._col15);
        _col16 = (EditText) mLinearLayout.findViewById(R.id._col16);
        _col17 = (EditText) mLinearLayout.findViewById(R.id._col17);
        _col18 = (EditText) mLinearLayout.findViewById(R.id._col18);
        _col19 = (EditText) mLinearLayout.findViewById(R.id._col19);
        _col20 = (EditText) mLinearLayout.findViewById(R.id._col20);

        _col21 = (CheckBox) mLinearLayout.findViewById(R.id._col21);
        _col22 = (CheckBox) mLinearLayout.findViewById(R.id._col22);
        _col23 = (CheckBox) mLinearLayout.findViewById(R.id._col23);
        _col24 = (CheckBox) mLinearLayout.findViewById(R.id._col24);
        _col25 = (CheckBox) mLinearLayout.findViewById(R.id._col25);
        _col26 = (CheckBox) mLinearLayout.findViewById(R.id._col26);
        _col27 = (CheckBox) mLinearLayout.findViewById(R.id._col27);
        _col28 = (CheckBox) mLinearLayout.findViewById(R.id._col28);
        _col29 = (CheckBox) mLinearLayout.findViewById(R.id._col29);

        _col30 = (CheckBox) mLinearLayout.findViewById(R.id._col30);
        _col31 = (CheckBox) mLinearLayout.findViewById(R.id._col31);
        _col32 = (CheckBox) mLinearLayout.findViewById(R.id._col32);
        _col33 = (CheckBox) mLinearLayout.findViewById(R.id._col33);
        _col34 = (CheckBox) mLinearLayout.findViewById(R.id._col34);
        _col35 = (CheckBox) mLinearLayout.findViewById(R.id._col35);
        _col36 = (CheckBox) mLinearLayout.findViewById(R.id._col36);
        _col37 = (CheckBox) mLinearLayout.findViewById(R.id._col37);
        _col38 = (CheckBox) mLinearLayout.findViewById(R.id._col38);
        _col39 = (CheckBox) mLinearLayout.findViewById(R.id._col39);

        _col40 = (CheckBox) mLinearLayout.findViewById(R.id._col40);
        _col41 = (CheckBox) mLinearLayout.findViewById(R.id._col41);
        _col42 = (CheckBox) mLinearLayout.findViewById(R.id._col42);

        _col43 = (EditText) mLinearLayout.findViewById(R.id._col43);
        _col44 = (EditText) mLinearLayout.findViewById(R.id._col44);
        _col45 = (EditText) mLinearLayout.findViewById(R.id._col45);
        _col46 = (EditText) mLinearLayout.findViewById(R.id._col46);



        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);
        ll_q3 = (LinearLayout) mLinearLayout.findViewById(R.id.ll_q3);
        fl_part3 = (LinearLayout) mLinearLayout.findViewById(R.id.lyt_chk_water2);
        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);
        ll_q3.setVisibility(View.GONE);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);
        _col6a.setOnClickListener(this);
        _col6b.setOnClickListener(this);
        _col13a.setOnClickListener(this);
        _col13b.setOnClickListener(this);
        _col13c.setOnClickListener(this);
        _col13d.setOnClickListener(this);
        _col13e.setOnClickListener(this);
        water.setOnClickListener(this);
        // ***************** LOCAD INFORMATION *************************
        loadRecord();
        if (_col6a.isChecked()==true) {ll_q3.setVisibility(View.VISIBLE);}

        return mLinearLayout;
    }
    

    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, q1a, q1b, q1c, q1d, q1e, q2a, q2b, q2c, q2d, q2e, q3a, q3b, q3c, q3d, q3e1, q3e2, q3e3, q3e4, q3e5, q3e6, q3f1, q3f2, q3f3, q3f4, q3f5, q3f6, q3f7, q3f8, q3f9,  q3f10, q3f11, q3f12, q3f13, q3f14, q3f15, q3f16, q3f17, q3f18, q3f19, q3f20, q3f21, q3f22, q3g1, q3g2, q3g3, q3g4, flag, sdw, ssdw1, ssdw2, ssdw3, ssdw4, ssdw5  FROM q WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            // **************** Part 1 *****************************
            if(cur_data.getInt(1)==1) {_col1.setChecked(true);}
            if(cur_data.getInt(2)==1) {_col2.setChecked(true);}
            if(cur_data.getInt(3)==1) {_col3.setChecked(true);}
            if(cur_data.getInt(4)==1) {_col4.setChecked(true);}
            if(cur_data.getInt(5)==1) {_col5.setChecked(true);}
            if(cur_data.getInt(6)==1) {_col6a.setChecked(true);}
            if(cur_data.getInt(6)==0) {_col6b.setChecked(true);}
            if(cur_data.getInt(7)==1) {_col7a.setChecked(true);}
            if(cur_data.getInt(7)==0) {_col7b.setChecked(true);}
            _col8.setText(cur_data.getString(8));
            _col9.setText(cur_data.getString(9));
            _col10.setText(cur_data.getString(10));

            // **************** Part 2 *****************************
            if(cur_data.getInt(11)==1) {_col11a.setChecked(true);}
            if(cur_data.getInt(11)==2) {_col11b.setChecked(true);}
            if(cur_data.getInt(11)==3) {_col11c.setChecked(true);}
            if(cur_data.getInt(11)==0) {_col11d.setChecked(true);}
            if(cur_data.getInt(12)==1) {_col12a.setChecked(true);}
            if(cur_data.getInt(12)==2) {_col12b.setChecked(true);}
            if(cur_data.getInt(12)==0) {_col12c.setChecked(true);}

            if(cur_data.getInt(48)==1) {_col13a_31.setChecked(true);}
            if(cur_data.getInt(48)==2) {_col13b_31.setChecked(true);}
            if(cur_data.getInt(48)==0) {_col13c_31.setChecked(true);}

            if(cur_data.getInt(13)==1) {_col13a.setChecked(true);}
            if(cur_data.getInt(13)==2) {_col13b.setChecked(true);}
            if(cur_data.getInt(13)==3) {_col13c.setChecked(true);}
            //if(cur_data.getInt(13)==4) {_col13d.setChecked(true);}
            if(cur_data.getInt(13)==4) {_col13d.setChecked(true);}
            if(cur_data.getInt(13)==0) {_col13e.setChecked(true);}

            if(cur_data.getInt(49)==1) {_col13a_33.setChecked(true);}
            if(cur_data.getInt(50)==1) {_col13b_33.setChecked(true);}
            if(cur_data.getInt(51)==1) {_col13c_33.setChecked(true);}
            if(cur_data.getInt(52)==1) {_col13d_33.setChecked(true);}
            if(cur_data.getInt(53)==1) {_col13e_33.setChecked(true);}

            if(cur_data.getInt(14)==1) {_col14a.setChecked(true);}
            if(cur_data.getInt(14)==2) {_col14b.setChecked(true);}
            if(cur_data.getInt(14)==0) {_col14c.setChecked(true);}

            _col15.setText(cur_data.getString(15));
            _col16.setText(cur_data.getString(16));
            _col17.setText(cur_data.getString(17));
            _col18.setText(cur_data.getString(18));
            _col19.setText(cur_data.getString(19));
            _col20.setText(cur_data.getString(20));

            if(cur_data.getInt(21)==1) {_col21.setChecked(true);}
            if(cur_data.getInt(22)==1) {_col22.setChecked(true);}
            if(cur_data.getInt(23)==1) {_col23.setChecked(true);}
            if(cur_data.getInt(24)==1) {_col24.setChecked(true);}
            if(cur_data.getInt(25)==1) {_col25.setChecked(true);}
            if(cur_data.getInt(26)==1) {_col26.setChecked(true);}
            if(cur_data.getInt(27)==1) {_col27.setChecked(true);}
            if(cur_data.getInt(28)==1) {_col28.setChecked(true);}
            if(cur_data.getInt(29)==1) {_col29.setChecked(true);}
            if(cur_data.getInt(30)==1) {_col30.setChecked(true);}
            if(cur_data.getInt(31)==1) {_col31.setChecked(true);}
            if(cur_data.getInt(32)==1) {_col32.setChecked(true);}
            if(cur_data.getInt(33)==1) {_col33.setChecked(true);}
            if(cur_data.getInt(34)==1) {_col34.setChecked(true);}
            if(cur_data.getInt(35)==1) {_col35.setChecked(true);}
            if(cur_data.getInt(36)==1) {_col36.setChecked(true);}
            if(cur_data.getInt(37)==1) {_col37.setChecked(true);}
            if(cur_data.getInt(38)==1) {_col38.setChecked(true);}
            if(cur_data.getInt(39)==1) {_col39.setChecked(true);}
            if(cur_data.getInt(40)==1) {_col40.setChecked(true);}
            if(cur_data.getInt(41)==1) {_col41.setChecked(true);}
            if(cur_data.getInt(42)==1) {_col42.setChecked(true);}

            _col43.setText(cur_data.getString(43));
            _col44.setText(cur_data.getString(44));
            _col45.setText(cur_data.getString(45));
            _col46.setText(cur_data.getString(46));

            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", tableName="q";
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        // **************** Part 1 *****************************
            if (_IU == "I" ) {reg.put("_id",1);}
            reg.put("flag", _IU); //sql = sql + _IU + ";" + tableName + ";" + SettingsMenuInfra_0.EMIS_code + delimit;
            sql = sql +  "" + tableName + delimit + SettingsMenuInfra_0.EMIS_code + delimit;
            if(_col1.isChecked()==true) {reg.put("q1a",1); sql = sql + "1" + delimit;} else {reg.put("q1a",0); sql = sql + "" + delimit;}
            if(_col2.isChecked()==true) {reg.put("q1b",1); sql = sql + "1" + delimit;} else {reg.put("q1b",0); sql = sql + "" + delimit;}
            if(_col3.isChecked()==true) {reg.put("q1c",1); sql = sql + "1" + delimit;} else {reg.put("q1c",0); sql = sql + "" + delimit;}
            if(_col4.isChecked()==true) {reg.put("q1d",1); sql = sql + "1" + delimit;} else {reg.put("q1d",0); sql = sql + "" + delimit;}
            if(_col5.isChecked()==true) {reg.put("q1e",1); sql = sql + "1" + delimit;} else {reg.put("q1e",0); sql = sql + "" + delimit;}
            if(_col6a.isChecked()==true) {
                reg.put("q2a",1); sql = sql + "1" + delimit;
                if(_col7a.isChecked()==true) {reg.put("q2b",1); sql = sql + "1" + delimit;}
                else if(_col7b.isChecked()==true) {reg.put("q2b",0); sql = sql + "0" + delimit;}
                else {sql = sql + "" + delimit;}
                if (!_col8.getText().toString().isEmpty()){reg.put("q2c",Integer.parseInt(_col8.getText().toString()));} sql = sql + _col8.getText().toString() + delimit;
                if (!_col9.getText().toString().isEmpty()){reg.put("q2d",Integer.parseInt(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
                if (!_col10.getText().toString().isEmpty()){reg.put("q2e",Integer.parseInt(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
            }
            if(_col6b.isChecked()==true) {
                reg.put("q2a",0); sql = sql + "0" + delimit;
                reg.put("q2b",2); sql = sql + "2" + delimit;
                reg.put("q2c",""); sql = sql + "" + delimit;
                reg.put("q2d",""); sql = sql + "" + delimit;
                reg.put("q2e",""); sql = sql + "" + delimit;
            }


        // **************** Part 2 *****************************
            if(_col11a.isChecked()==true) {reg.put("q3a",1); sql = sql + "1" + delimit;}
            else if(_col11b.isChecked()==true) {reg.put("q3a",2); sql = sql + "2" + delimit;}
            else if(_col11c.isChecked()==true) {reg.put("q3a",3); sql = sql + "3" + delimit;}
            else if(_col11d.isChecked()==true) {reg.put("q3a",0); sql = sql + "0" + delimit;}
            else {sql = sql + "" + delimit;}

            if(_col12a.isChecked()==true) {reg.put("q3b",1); sql = sql + "1" + delimit;}
            else if(_col12b.isChecked()==true) {reg.put("q3b",2); sql = sql + "2" + delimit;}
            else if(_col12c.isChecked()==true) {reg.put("q3b",0); sql = sql + "0" + delimit;}
            else {sql = sql + "" + delimit;}

            if(_col13a.isChecked()==true) {reg.put("q3c",1); sql = sql + "1" + delimit;}
            else if(_col13b.isChecked()==true) {reg.put("q3c",2); sql = sql + "2" + delimit;}
            else if(_col13c.isChecked()==true) {reg.put("q3c",3); sql = sql + "3" + delimit;}
            //else if(_col13d.isChecked()==true) {reg.put("q3c",4); sql = sql + "4" + delimit;}
            else if(_col13d.isChecked()==true) {reg.put("q3c",4); sql = sql + "4" + delimit;}
            else if(_col13e.isChecked()==true) {reg.put("q3c",0); sql = sql + "0" + delimit;}
            else {sql = sql + "" + delimit;}

            if(_col14a.isChecked()==true) {reg.put("q3d",1); sql = sql + "1" + delimit;}
            else if(_col14b.isChecked()==true) {reg.put("q3d",2); sql = sql + "2" + delimit;}
            else if(_col14c.isChecked()==true) {reg.put("q3d",0); sql = sql + "0" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col15.getText().toString().isEmpty()){reg.put("q3e1", Integer.parseInt(_col15.getText().toString()));} sql = sql + _col15.getText().toString() + delimit;
            if (!_col16.getText().toString().isEmpty()){reg.put("q3e2", Integer.parseInt(_col16.getText().toString()));} sql = sql + _col16.getText().toString() + delimit;
            if (!_col17.getText().toString().isEmpty()){reg.put("q3e3", Integer.parseInt(_col17.getText().toString()));} sql = sql + _col17.getText().toString() + delimit;
            if (!_col18.getText().toString().isEmpty()){reg.put("q3e4", Integer.parseInt(_col18.getText().toString()));} sql = sql + _col18.getText().toString() + delimit;
            if (!_col19.getText().toString().isEmpty()){reg.put("q3e5", Integer.parseInt(_col19.getText().toString()));} sql = sql + _col19.getText().toString() + delimit;
            if (!_col20.getText().toString().isEmpty()){reg.put("q3e6", Integer.parseInt(_col20.getText().toString()));} sql = sql + _col20.getText().toString() + delimit;

            if(_col21.isChecked()==true) {reg.put("q3f1",1); sql = sql + "1" + delimit;} else {reg.put("q3f1",0); sql = sql + "0" + delimit;}
            if(_col22.isChecked()==true) {reg.put("q3f2",1); sql = sql + "1" + delimit;} else {reg.put("q3f2",0); sql = sql + "0" + delimit;}
            if(_col23.isChecked()==true) {reg.put("q3f3",1); sql = sql + "1" + delimit;} else {reg.put("q3f3",0); sql = sql + "0" + delimit;}
            if(_col24.isChecked()==true) {reg.put("q3f4",1); sql = sql + "1" + delimit;} else {reg.put("q3f4",0); sql = sql + "0" + delimit;}
            if(_col25.isChecked()==true) {reg.put("q3f5",1); sql = sql + "1" + delimit;} else {reg.put("q3f5",0); sql = sql + "0" + delimit;}
            if(_col26.isChecked()==true) {reg.put("q3f6",1); sql = sql + "1" + delimit;} else {reg.put("q3f6",0); sql = sql + "0" + delimit;}
            if(_col27.isChecked()==true) {reg.put("q3f7",1); sql = sql + "1" + delimit;} else {reg.put("q3f7",0); sql = sql + "0" + delimit;}
            if(_col28.isChecked()==true) {reg.put("q3f8",1); sql = sql + "1" + delimit;} else {reg.put("q3f8",0); sql = sql + "0" + delimit;}
            if(_col29.isChecked()==true) {reg.put("q3f9",1); sql = sql + "1" + delimit;} else {reg.put("q3f9",0); sql = sql + "0" + delimit;}
            if(_col30.isChecked()==true) {reg.put("q3f10",1); sql = sql + "1" + delimit;} else {reg.put("q3f10",0); sql = sql + "0" + delimit;}
            if(_col31.isChecked()==true) {reg.put("q3f11",1); sql = sql + "1" + delimit;} else {reg.put("q3f11",0); sql = sql + "0" + delimit;}
            if(_col32.isChecked()==true) {reg.put("q3f12",1); sql = sql + "1" + delimit;} else {reg.put("q3f12",0); sql = sql + "0" + delimit;}
            if(_col33.isChecked()==true) {reg.put("q3f13",1); sql = sql + "1" + delimit;} else {reg.put("q3f13",0); sql = sql + "0" + delimit;}
            if(_col34.isChecked()==true) {reg.put("q3f14",1); sql = sql + "1" + delimit;} else {reg.put("q3f14",0); sql = sql + "0" + delimit;}
            if(_col35.isChecked()==true) {reg.put("q3f15",1); sql = sql + "1" + delimit;} else {reg.put("q3f15",0); sql = sql + "0" + delimit;}
            if(_col36.isChecked()==true) {reg.put("q3f16",1); sql = sql + "1" + delimit;} else {reg.put("q3f16",0); sql = sql + "0" + delimit;}
            if(_col37.isChecked()==true) {reg.put("q3f17",1); sql = sql + "1" + delimit;} else {reg.put("q3f17",0); sql = sql + "0" + delimit;}
            if(_col38.isChecked()==true) {reg.put("q3f18",1); sql = sql + "1" + delimit;} else {reg.put("q3f18",0); sql = sql + "0" + delimit;}
            if(_col39.isChecked()==true) {reg.put("q3f19",1); sql = sql + "1" + delimit;} else {reg.put("q3f19",0); sql = sql + "0" + delimit;}
            if(_col40.isChecked()==true) {reg.put("q3f20",1); sql = sql + "1" + delimit;} else {reg.put("q3f20",0); sql = sql + "0" + delimit;}
            if(_col41.isChecked()==true) {reg.put("q3f21",1); sql = sql + "1" + delimit;} else {reg.put("q3f21",0); sql = sql + "0" + delimit;}
            if(_col42.isChecked()==true) {reg.put("q3f22",1); sql = sql + "1" + delimit;} else {reg.put("q3f22",0); sql = sql + "0" + delimit;}

            if (!_col43.getText().toString().isEmpty()){reg.put("q3g1", Integer.parseInt(_col43.getText().toString()));} sql = sql + _col43.getText().toString() + delimit;
            if (!_col44.getText().toString().isEmpty()){reg.put("q3g2", Integer.parseInt(_col44.getText().toString()));} sql = sql + _col44.getText().toString() + delimit;
            if (!_col45.getText().toString().isEmpty()){reg.put("q3g3", Integer.parseInt(_col45.getText().toString()));} sql = sql + _col45.getText().toString() + delimit;
            if (!_col46.getText().toString().isEmpty()){reg.put("q3g4", Integer.parseInt(_col46.getText().toString()));} sql = sql + _col46.getText().toString() + delimit;

            // 20180915 Add new colum for Source Drinking Water
            if(_col13a_31.isChecked()==true) {reg.put("sdw",1); sql = sql + "1" + delimit;}
            else if(_col13b_31.isChecked()==true) {reg.put("sdw",2); sql = sql + "2" + delimit;}
            else if(_col13c_31.isChecked()==true) {reg.put("sdw",0); sql = sql + "0" + delimit;}
        // 20180915 Add new colum for Source Drinking Water
            if(_col13a_33.isChecked()==true) {reg.put("ssdw1",1); sql = sql + "1" + delimit;} else {reg.put("ssdw1",0); sql = sql + "0" + delimit;}
            if(_col13b_33.isChecked()==true) {reg.put("ssdw2",1); sql = sql + "1" + delimit;} else {reg.put("ssdw2",0); sql = sql + "0" + delimit;}
            if(_col13c_33.isChecked()==true) {reg.put("ssdw3",1); sql = sql + "1" + delimit;} else {reg.put("ssdw3",0); sql = sql + "0" + delimit;}
            if(_col13d_33.isChecked()==true) {reg.put("ssdw4",1); sql = sql + "1" + delimit;} else {reg.put("ssdw4",0); sql = sql + "0" + delimit;}
            if(_col13e_33.isChecked()==true) {reg.put("ssdw5",1); sql = sql + "1" + delimit;} else {reg.put("ssdw5",0); sql = sql + "0" + delimit;}

            sql = sql + _IU;

            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE q
                if (_IU=="I") {dbSET.insert("q", null, reg); _IU="U";}
                else {dbSET.update("q", reg, "_id=1", null);}

                toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
            }catch (Exception e) {
                Toast.makeText(getContext(),"Debe ingresar al menos un registro... !!! ",Toast.LENGTH_SHORT).show();
            }
        dbSET.close();
        cnSET.close();
    }

    public void bitacora(){

    }


    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_save:
                dialogAlert(1);
                break;
            case R.id._col6a:
                ll_q3.setVisibility(View.VISIBLE);
                break;
            case R.id._col6b:
                ll_q3.setVisibility(View.GONE);
                break;
            case R.id._col13e:
                fl_part3.setVisibility(View.GONE);
                    break;
            case R.id._col13a:
                fl_part3.setVisibility(View.VISIBLE);
                break;
            case R.id._col13b:
                fl_part3.setVisibility(View.VISIBLE);
                break;
            case R.id._col13c:
                fl_part3.setVisibility(View.VISIBLE);
                break;
            case R.id._col13d:
                fl_part3.setVisibility(View.VISIBLE);
                break;
        }

    }


    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Are sure you want to save?");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

