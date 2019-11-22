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
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;

import static android.view.View.GONE;

/**
 * Created by Sergio on 3/7/2016.
 */
public class SettingsMenuInfra_7 extends Fragment implements View.OnClickListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2, fl_part3; // ************ FrameLayout ***************
    LinearLayout ll_CG, ll_LGAs, ll_PGI, ll_RG, ll_Comunity, ll_Domestic, ll_NGO, ll_Books, ll_Furniture, ll_Food, ll_Supplies, ll_Training;
    String _col1, _col3, _col5, _col7, _col9, _col11, _col13,_col15, _col16, _col17 ;
    EditText  _col2,_col4,_col6,_col8,_col10,_col12,_col14,_col27,_col31,other1,other2,other3,other4,other5;
    RadioButton rb_yes1,rb_no1, rb_yes2,rb_no2, rb_yes3,rb_no3, rb_yes4,rb_no4, rb_yes5,rb_no5, rb_yes6,rb_no6, rb_yes7,rb_no7,rb_yes8,rb_no8,rb_g8,rb_o8, rb_b8, rb_yes9,rb_no9,rb_g9,rb_o9, rb_b9,rb_yes10,rb_no10,rb_g10,rb_o10, rb_b10, rb_yes11,rb_no11,rb_g11,rb_o11, rb_b11, rb_yes12,rb_no12,rb_g12,rb_o12, rb_b12;
    CheckBox cb_pp1, cb_p1,cb_s1,cb_pp2, cb_p2,cb_s2,cb_pp3, cb_p3,cb_s3,cb_pp4, cb_p4,cb_s4,cb_pp5, cb_p5,cb_s5,cb_pp6, cb_p6,cb_s6;
    String _IU="U";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_7,
                container, false);

        // ********************** Global vars ******************
        rb_yes1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes1);
        rb_no1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no1);
        _col2 = (EditText) mLinearLayout.findViewById(R.id.et_m1);
        rb_yes2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes2);
        rb_no2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no2);
        _col4 = (EditText) mLinearLayout.findViewById(R.id.et_m2);
        rb_yes3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes3);
        rb_no3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no3);
        _col6 = (EditText) mLinearLayout.findViewById(R.id.et_m3);
        rb_yes4 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes4);
        rb_no4 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no4);
        _col8 = (EditText) mLinearLayout.findViewById(R.id.et_m4);
        rb_yes5 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes5);
        rb_no5 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no5);
        _col10 = (EditText) mLinearLayout.findViewById(R.id.et_m5);
        rb_yes6 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes6);
        rb_no6 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no6);
        _col12 = (EditText) mLinearLayout.findViewById(R.id.et_m6);
        rb_yes7 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes7);
        rb_no7 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no7);
        _col14 = (EditText) mLinearLayout.findViewById(R.id.et_m7);

        cb_pp1 = (CheckBox) mLinearLayout.findViewById(R.id.cb_prepri1);
        cb_p1 = (CheckBox) mLinearLayout.findViewById(R.id.cb_pri1);
        cb_s1 = (CheckBox) mLinearLayout.findViewById(R.id.cb_sec1);

        cb_pp2 = (CheckBox) mLinearLayout.findViewById(R.id.rb_prepri2);
        cb_p2 = (CheckBox) mLinearLayout.findViewById(R.id.rb_pri2);
        cb_s2 = (CheckBox) mLinearLayout.findViewById(R.id.rb_sec2);

        cb_pp3 = (CheckBox) mLinearLayout.findViewById(R.id.rb_prepri3);
        cb_p3 = (CheckBox) mLinearLayout.findViewById(R.id.rb_pri3);
        cb_s3 = (CheckBox) mLinearLayout.findViewById(R.id.rb_sec3);

        cb_pp4 = (CheckBox) mLinearLayout.findViewById(R.id.rb_prepri4);
        cb_p4 = (CheckBox) mLinearLayout.findViewById(R.id.rb_pri4);
        cb_s4 = (CheckBox) mLinearLayout.findViewById(R.id.rb_sec4);

        _col27 = (EditText) mLinearLayout.findViewById(R.id.et_Other1);
        cb_pp5 = (CheckBox) mLinearLayout.findViewById(R.id.rb_prepri5);
        cb_p5 = (CheckBox) mLinearLayout.findViewById(R.id.rb_pri5);
        cb_s5 = (CheckBox) mLinearLayout.findViewById(R.id.rb_sec5);

        _col31 = (EditText) mLinearLayout.findViewById(R.id.et_Other2);
        cb_pp6 = (CheckBox) mLinearLayout.findViewById(R.id.rb_prepri6);
        cb_p6 = (CheckBox) mLinearLayout.findViewById(R.id.rb_pri6);
        cb_s6 = (CheckBox) mLinearLayout.findViewById(R.id.rb_sec6);

        rb_yes8 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes8);
        rb_no8 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no8);
        rb_g8 = (RadioButton) mLinearLayout.findViewById(R.id.rb_g8);
        rb_o8 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o8);
        rb_b8 = (RadioButton) mLinearLayout.findViewById(R.id.rb_b8);
        other1 = (EditText) mLinearLayout.findViewById(R.id.other1);

        rb_yes9 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes9);
        rb_no9 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no9);
        rb_g9 = (RadioButton) mLinearLayout.findViewById(R.id.rb_g9);
        rb_o9 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o9);
        rb_b9 = (RadioButton) mLinearLayout.findViewById(R.id.rb_b9);
        other2 = (EditText) mLinearLayout.findViewById(R.id.other2);


        rb_yes10 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes10);
        rb_no10 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no10);
        rb_g10 = (RadioButton) mLinearLayout.findViewById(R.id.rb_g10);
        rb_o10 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o10);
        rb_b10 = (RadioButton) mLinearLayout.findViewById(R.id.rb_b10);
        other3 = (EditText) mLinearLayout.findViewById(R.id.other3);

        rb_yes11 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes11);
        rb_no11 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no11);
        rb_g11 = (RadioButton) mLinearLayout.findViewById(R.id.rb_g11);
        rb_o11 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o11);
        rb_b11 = (RadioButton) mLinearLayout.findViewById(R.id.rb_b11);
        other4 = (EditText) mLinearLayout.findViewById(R.id.other4);

        rb_yes12 = (RadioButton) mLinearLayout.findViewById(R.id.rb_yes12);
        rb_no12 = (RadioButton) mLinearLayout.findViewById(R.id.rb_no12);
        rb_g12 = (RadioButton) mLinearLayout.findViewById(R.id.rb_g12);
        rb_o12 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o12);
        rb_b12 = (RadioButton) mLinearLayout.findViewById(R.id.rb_b12);
        other5 = (EditText) mLinearLayout.findViewById(R.id.other5);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);
        //ll_CG, ll_LGAs, ll_PGI, ll_RG, ll_Comunity, ll_Domestic, ll_NGO;
        ll_CG = (LinearLayout) mLinearLayout.findViewById(R.id.ll_CG);
        ll_LGAs = (LinearLayout) mLinearLayout.findViewById(R.id.ll_LGAs);
        ll_PGI = (LinearLayout) mLinearLayout.findViewById(R.id.ll_PGI);
        ll_RG = (LinearLayout) mLinearLayout.findViewById(R.id.ll_RG);
        ll_Comunity = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Comunity);
        ll_Domestic = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Domestic);
        ll_NGO = (LinearLayout) mLinearLayout.findViewById(R.id.ll_NGO);
        ll_Books = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Books);
        ll_Furniture = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Furniture);
        ll_Food = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Food);
        ll_Supplies = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Supplies);
        ll_Training = (LinearLayout) mLinearLayout.findViewById(R.id.ll_Training);


        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);

        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);
        rb_yes1.setOnClickListener(this);
        rb_no1.setOnClickListener(this);
        rb_yes2.setOnClickListener(this);
        rb_no2.setOnClickListener(this);
        rb_yes3.setOnClickListener(this);
        rb_no3.setOnClickListener(this);
        rb_yes4.setOnClickListener(this);
        rb_no4.setOnClickListener(this);
        rb_yes5.setOnClickListener(this);
        rb_no5.setOnClickListener(this);
        rb_yes6.setOnClickListener(this);
        rb_no6.setOnClickListener(this);
        rb_yes7.setOnClickListener(this);
        rb_no7.setOnClickListener(this);
        rb_yes8.setOnClickListener(this);
        rb_no8.setOnClickListener(this);
        rb_yes9.setOnClickListener(this);
        rb_no9.setOnClickListener(this);
        rb_yes10.setOnClickListener(this);
        rb_no10.setOnClickListener(this);
        rb_yes11.setOnClickListener(this);
        rb_no11.setOnClickListener(this);
        rb_yes12.setOnClickListener(this);
        rb_no12.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        loadRecord();
        return mLinearLayout;
    }

    //ll_CG, ll_LGAs, ll_PGI, ll_RG, ll_Comunity, ll_Domestic, ll_NGO;
    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, r1a1, r1a2, r1b1, r1b2, r1c1, r1c2, r1d1, r1d2, r1e1, r1e2, r1f1, r1f2, r1g1, r1g2, r2a1,r2a2,r2a3, r2b1,r2b2,r2b3,r2c1,r2c2,r2c3,r2d1,r2d2,r2d3,r2e1,r2e2,r2e3,r2e4,r2f1,r2f2,r2f3,r2f4,r3a1,r3a2,r3a3,r3b1,r3b2,r3b3,r3c1,r3c2,r3c3,r3d1,r3d2,r3d3,r3e1,r3e2,r3e3, flag FROM r WHERE _id=1",null);        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            // **************** Frame 1  ******************
            _col1 = cur_data.getString(1);
            if(_col1 != null) {switch (_col1) {case "1":rb_yes1.setChecked(true);break;case "2":rb_no1.setChecked(true);ll_CG.setVisibility(View.GONE);break;default:break;}}
            _col2.setText(cur_data.getString(2));
            _col3 = cur_data.getString(3);
            if(_col3 != null) {switch (_col3) {case "1":rb_yes2.setChecked(true);break;case "2":rb_no2.setChecked(true);ll_LGAs.setVisibility(View.GONE);break;default:break;}}
            _col4.setText(cur_data.getString(4));
            _col5 = cur_data.getString(5);
            if(_col5 != null) {switch (_col5) {case "1":rb_yes3.setChecked(true);break;case "2":rb_no3.setChecked(true);ll_PGI.setVisibility(View.GONE);break;default:break;}}
            _col6.setText(cur_data.getString(6));
            _col7 = cur_data.getString(7);
            if(_col7 != null) {switch (_col7) {case "1":rb_yes4.setChecked(true);break;case "2":rb_no4.setChecked(true);ll_RG.setVisibility(View.GONE);break;default:break;}}
            _col8.setText(cur_data.getString(8));
            _col9 = cur_data.getString(9);
            if(_col9 != null) {switch (_col9) {case "1":rb_yes5.setChecked(true);break;case "2":rb_no5.setChecked(true);ll_Comunity.setVisibility(View.GONE);break;default:break;}}
            _col10.setText(cur_data.getString(10));
            _col11 = cur_data.getString(11);
            if(_col11 != null) {switch (_col11) {case "1":rb_yes6.setChecked(true);break;case "2":rb_no6.setChecked(true);ll_Domestic.setVisibility(View.GONE);break;default:break;}}
            _col12.setText(cur_data.getString(12));
            _col13 = cur_data.getString(13);
            if(_col13 != null) {switch (_col13) {case "1":rb_yes7.setChecked(true);break;case "2":rb_no7.setChecked(true);ll_NGO.setVisibility(View.GONE);break;default:break;}}
            _col14.setText(cur_data.getString(14));

            // **************** Frame 2  ******************
            if(cur_data.getInt(15)==1) {cb_pp1.setChecked(true);}
            if(cur_data.getInt(16)==1) {cb_p1.setChecked(true);}
            if(cur_data.getInt(17)==1) {cb_s1.setChecked(true);}

            if(cur_data.getInt(18)==1) {cb_pp2.setChecked(true);}  //r2a3
            if(cur_data.getInt(19)==1) {cb_p2.setChecked(true);}
            if(cur_data.getInt(20)==1) {cb_s2.setChecked(true);}

            if(cur_data.getInt(21)==1) {cb_pp3.setChecked(true);}
            if(cur_data.getInt(22)==1) {cb_p3.setChecked(true);}
            if(cur_data.getInt(23)==1) {cb_s3.setChecked(true);}

            if(cur_data.getInt(24)==1) {cb_pp4.setChecked(true);}
            if(cur_data.getInt(25)==1) {cb_p4.setChecked(true);}
            if(cur_data.getInt(26)==1) {cb_s4.setChecked(true);}

            _col27.setText(cur_data.getString(27));
            if(cur_data.getInt(28)==1) {cb_pp5.setChecked(true);}
            if(cur_data.getInt(29)==1) {cb_p5.setChecked(true);}
            if(cur_data.getInt(30)==1) {cb_s5.setChecked(true);}

            _col31.setText(cur_data.getString(31));
            if(cur_data.getInt(32)==1) {cb_pp6.setChecked(true);}
            if(cur_data.getInt(33)==1) {cb_p6.setChecked(true);}
            if(cur_data.getInt(34)==1) {cb_s6.setChecked(true);}

            // **************** Frame 3  ******************


            if(cur_data.getInt(35)==1) {rb_yes8.setChecked(true);}
            if(cur_data.getInt(35)==2) {rb_no8.setChecked(true); ll_Books.setVisibility(View.GONE);}
            if(cur_data.getInt(36)==1) {rb_g8.setChecked(true);}
            if(cur_data.getInt(36)==2) {rb_o8.setChecked(true);}
            if(cur_data.getInt(36)==3) {rb_b8.setChecked(true);}
            other1.setText(cur_data.getString(37));

            if(cur_data.getInt(38)==1) {rb_yes9.setChecked(true);}
            if(cur_data.getInt(38)==2) {rb_no9.setChecked(true); ll_Furniture.setVisibility(View.GONE);}
            if(cur_data.getInt(39)==1) {rb_g9.setChecked(true);}
            if(cur_data.getInt(39)==2) {rb_o9.setChecked(true);}
            if(cur_data.getInt(39)==3) {rb_b9.setChecked(true);}
            other2.setText(cur_data.getString(40));

            if(cur_data.getInt(41)==1) {rb_yes10.setChecked(true);}
            if(cur_data.getInt(41)==2) {rb_no10.setChecked(true); ll_Food.setVisibility(View.GONE);}
            if(cur_data.getInt(42)==1) {rb_g10.setChecked(true);}
            if(cur_data.getInt(42)==2) {rb_o10.setChecked(true);}
            if(cur_data.getInt(42)==3) {rb_b10.setChecked(true);}
            other3.setText(cur_data.getString(43));

            if(cur_data.getInt(44)==1) {rb_yes11.setChecked(true);}
            if(cur_data.getInt(44)==2) {rb_no11.setChecked(true); ll_Supplies.setVisibility(View.GONE);}
            if(cur_data.getInt(45)==1) {rb_g11.setChecked(true);}
            if(cur_data.getInt(45)==2) {rb_o11.setChecked(true);}
            if(cur_data.getInt(45)==3) {rb_b11.setChecked(true);}
            other4.setText(cur_data.getString(46));

            if(cur_data.getInt(47)==1) {rb_yes12.setChecked(true);}
            if(cur_data.getInt(47)==2) {rb_no12.setChecked(true); ll_Training.setVisibility(View.GONE);}
            if(cur_data.getInt(48)==1) {rb_g12.setChecked(true);}
            if(cur_data.getInt(48)==2) {rb_o12.setChecked(true);}
            if(cur_data.getInt(48)==3) {rb_b12.setChecked(true);}
            other5.setText(cur_data.getString(49));

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
            reg.put("flag", _IU); //sql = sql + _IU + ";r;" + SettingsMenuInfra_0.EMIS_code + delimit;
            sql = sql +  "r" + delimit + SettingsMenuInfra_0.EMIS_code + delimit;
            // **************** Frame 1  ******************
            if (rb_yes1.isChecked() == true) {reg.put("r1a1", 1); sql = sql + "1" + delimit;}
            else if (rb_no1.isChecked() == true) {reg.put("r1a1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col2.getText().toString().isEmpty()){reg.put("r1a2", Integer.parseInt(_col2.getText().toString()));} else {reg.put("r1a2","");} sql = sql + _col2.getText().toString() + delimit;

            if (rb_yes2.isChecked() == true) {reg.put("r1b1", 1); sql = sql + "1" + delimit;}
            else if (rb_no2.isChecked() == true) {reg.put("r1b1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col4.getText().toString().isEmpty()){reg.put("r1b2", Integer.parseInt(_col4.getText().toString()));} else {reg.put("r1b2","");} sql = sql + _col4.getText().toString() + delimit;

            if (rb_yes3.isChecked() == true) {reg.put("r1c1", 1); sql = sql + "1" + delimit;}
            else if (rb_no3.isChecked() == true) {reg.put("r1c1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col6.getText().toString().isEmpty()){reg.put("r1c2", Integer.parseInt(_col6.getText().toString()));} else {reg.put("r1c2","");} sql = sql + _col6.getText().toString() + delimit;

            if (rb_yes4.isChecked() == true) {reg.put("r1d1", 1); sql = sql + "1" + delimit;}
            else if (rb_no4.isChecked() == true) {reg.put("r1d1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col8.getText().toString().isEmpty()){reg.put("r1d2", Integer.parseInt(_col8.getText().toString()));} else {reg.put("r1d2","");} sql = sql + _col8.getText().toString() + delimit;

            if (rb_yes5.isChecked() == true) {reg.put("r1e1", 1); sql = sql + "1" + delimit;}
            else if (rb_no5.isChecked() == true) {reg.put("r1e1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col10.getText().toString().isEmpty()){reg.put("r1e2", Integer.parseInt(_col10.getText().toString()));} else {reg.put("r1e2","");} sql = sql + _col10.getText().toString() + delimit;

            if (rb_yes6.isChecked() == true) {reg.put("r1f1", 1); sql = sql + "1" + delimit;}
            else if (rb_no6.isChecked() == true) {reg.put("r1f1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col12.getText().toString().isEmpty()){reg.put("r1f2", Integer.parseInt(_col12.getText().toString()));} else {reg.put("r1f2","");} sql = sql + _col12.getText().toString() + delimit;

            if (rb_yes7.isChecked() == true) {reg.put("r1g1", 1); sql = sql + "1" + delimit;}
            else if (rb_no7.isChecked() == true) {reg.put("r1g1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col14.getText().toString().isEmpty()){reg.put("r1g2", Integer.parseInt(_col14.getText().toString()));} else {reg.put("r1g2","");} sql = sql + _col14.getText().toString() + delimit;

            // **************** Frame 2  ******************
            if (cb_pp1.isChecked() == true) {reg.put("r2a1", 1); sql = sql + "1" + delimit;} else {reg.put("r2a1", "0"); sql = sql + "0" + delimit;}

            if (cb_p1.isChecked() == true) {reg.put("r2a2", 1); sql = sql + "1" + delimit;} else {reg.put("r2a2", "0"); sql = sql + "0" + delimit;}
            if (cb_s1.isChecked() == true) {reg.put("r2a3", 1); sql = sql + "1" + delimit;} else {reg.put("r2a3", "0"); sql = sql + "0" + delimit;}

            if (cb_pp2.isChecked() == true) {reg.put("r2b1", 1); sql = sql + "1" + delimit;} else {reg.put("r2b1", "0"); sql = sql + "0" + delimit;}
            if (cb_p2.isChecked() == true) {reg.put("r2b2", 1); sql = sql + "1" + delimit;} else {reg.put("r2b2", "0"); sql = sql + "0" + delimit;}
            if (cb_s2.isChecked() == true) {reg.put("r2b3", 1); sql = sql + "1" + delimit;} else {reg.put("r2b3", "0"); sql = sql + "0" + delimit;}

            if (cb_pp3.isChecked() == true) {reg.put("r2c1", 1); sql = sql + "1" + delimit;} else {reg.put("r2c1", "0"); sql = sql + "0" + delimit;}
            if (cb_p3.isChecked() == true) {reg.put("r2c2", 1); sql = sql + "1" + delimit;} else {reg.put("r2c2", "0"); sql = sql + "0" + delimit;}
            if (cb_s3.isChecked() == true) {reg.put("r2c3", 1); sql = sql + "1" + delimit;} else {reg.put("r2c3", "0"); sql = sql + "0" + delimit;}

            if (cb_pp4.isChecked() == true) {reg.put("r2d1", 1); sql = sql + "1" + delimit;} else {reg.put("r2d1", "0"); sql = sql + "0" + delimit;}
            if (cb_p4.isChecked() == true) {reg.put("r2d2", 1); sql = sql + "1" + delimit;} else {reg.put("r2d2", "0"); sql = sql + "0" + delimit;}
            if (cb_s4.isChecked() == true) {reg.put("r2d3", 1); sql = sql + "1" + delimit;} else {reg.put("r2d3", "0"); sql = sql + "0" + delimit;}

            if (!_col27.getText().toString().isEmpty()){reg.put("r2e1", _col27.getText().toString());} sql = sql + _col27.getText().toString() + delimit;
            if (cb_pp5.isChecked() == true) {reg.put("r2e2", 1); sql = sql + "1" + delimit;} else {reg.put("r2e2", "0"); sql = sql + "0" + delimit;}
            if (cb_p5.isChecked() == true) {reg.put("r2e3", 1); sql = sql + "1" + delimit;} else {reg.put("r2e3", "0"); sql = sql + "0" + delimit;}
            if (cb_s5.isChecked() == true) {reg.put("r2e4", 1); sql = sql + "1" + delimit;} else {reg.put("r2e4", "0"); sql = sql + "0" + delimit;}

            if (!_col31.getText().toString().isEmpty()){reg.put("r2f1", _col31.getText().toString());} sql = sql + _col31.getText().toString() + delimit;
            if (cb_pp6.isChecked() == true) {reg.put("r2f2", 1); sql = sql + "1" + delimit;} else {reg.put("r2f2", "0"); sql = sql + "0" + delimit;}
            if (cb_p6.isChecked() == true) {reg.put("r2f3", 1); sql = sql + "1" + delimit;} else {reg.put("r2f3", "0"); sql = sql + "0" + delimit;}
            if (cb_s6.isChecked() == true) {reg.put("r2f4", 1); sql = sql + "1" + delimit;} else {reg.put("r2f4", "0"); sql = sql + "0" + delimit;}



            // **************** Frame 3  ******************
            if (rb_yes8.isChecked() == true) {reg.put("r3a1", 1); sql = sql + "1" + delimit;}
            else if (rb_no8.isChecked() == true) {reg.put("r3a1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_g8.isChecked() == true) {reg.put("r3a2", 1); sql = sql + "1" + delimit;}
            else if (rb_o8.isChecked() == true) {reg.put("r3a2", 2); sql = sql + "2" + delimit;}
            else if (rb_b8.isChecked() == true) {reg.put("r3a2", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!other1.getText().toString().isEmpty()){reg.put("r3a3", other1.getText().toString());} else {reg.put("r3a2","");reg.put("r3a3","");} sql = sql + other1.getText().toString() + delimit;

            if (rb_yes9.isChecked() == true) {reg.put("r3b1", 1); sql = sql + "1" + delimit;}
            else if (rb_no9.isChecked() == true) {reg.put("r3b1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_g9.isChecked() == true) {reg.put("r3b2", 1); sql = sql + "1" + delimit;}
            else if (rb_o9.isChecked() == true) {reg.put("r3b2", 2); sql = sql + "2" + delimit;}
            else if (rb_b9.isChecked() == true) {reg.put("r3b2", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!other2.getText().toString().isEmpty()){reg.put("r3b3", other2.getText().toString());} else {reg.put("r3b2",""); reg.put("r3b3","");} sql = sql + other2.getText().toString() + delimit;

            if (rb_yes10.isChecked() == true) {reg.put("r3c1", 1); sql = sql + "1" + delimit;}
            else if (rb_no10.isChecked() == true) {reg.put("r3c1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_g10.isChecked() == true) {reg.put("r3c2", 1); sql = sql + "1" + delimit;}
            else if (rb_o10.isChecked() == true) {reg.put("r3c2", 2); sql = sql + "2" + delimit;}
            else if (rb_b10.isChecked() == true) {reg.put("r3c2", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!other3.getText().toString().isEmpty()){reg.put("r3c3", other3.getText().toString());} else {reg.put("r3c2","");reg.put("r3c3","");} sql = sql + other3.getText().toString() + delimit;

            if (rb_yes11.isChecked() == true) {reg.put("r3d1", 1); sql = sql + "1" + delimit;}
            else if (rb_no11.isChecked() == true) {reg.put("r3d1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_g11.isChecked() == true) {reg.put("r3d2", 1); sql = sql + "1" + delimit;}
            else if (rb_o11.isChecked() == true) {reg.put("r3d2", 2); sql = sql + "2" + delimit;}
            else if (rb_b11.isChecked() == true) {reg.put("r3d2", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!other4.getText().toString().isEmpty()){reg.put("r3d3", other4.getText().toString());} else {reg.put("r3d2","");reg.put("r3d3","");} sql = sql + other4.getText().toString() + delimit;

            if (rb_yes12.isChecked() == true) {reg.put("r3e1", 1); sql = sql + "1" + delimit;}
            else if (rb_no12.isChecked() == true) {reg.put("r3e1", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_g12.isChecked() == true) {reg.put("r3e2", 1); sql = sql + "1" + delimit;}
            else if (rb_o12.isChecked() == true) {reg.put("r3e2", 2); sql = sql + "2" + delimit;}
            else if (rb_b12.isChecked() == true) {reg.put("r3e2", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!other5.getText().toString().isEmpty()){reg.put("r3e3", other5.getText().toString());} else {reg.put("r3e2","");reg.put("r3e3","");} sql = sql + other5.getText().toString() + delimit;

            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("r", null, reg); _IU="U";}
                else {dbSET.update("r", reg, "_id=1", null);}

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
                //updateRecord();
                break;
            //ll_CG, ll_LGAs, ll_PGI, ll_RG, ll_Comunity, ll_Domestic, ll_NGO;
            case R.id.rb_yes1: ll_CG.setVisibility(View.VISIBLE); break;
            case R.id.rb_no1: ll_CG.setVisibility(GONE); _col2.setText(""); break;
            case R.id.rb_yes2: ll_LGAs.setVisibility(View.VISIBLE); break;
            case R.id.rb_no2: ll_LGAs.setVisibility(GONE); _col4.setText(""); break;
            case R.id.rb_yes3: ll_PGI.setVisibility(View.VISIBLE); break;
            case R.id.rb_no3: ll_PGI.setVisibility(GONE); _col6.setText("");break;
            case R.id.rb_yes4: ll_RG.setVisibility(View.VISIBLE); break;
            case R.id.rb_no4: ll_RG.setVisibility(GONE); _col8.setText("");break;
            case R.id.rb_yes5: ll_Comunity.setVisibility(View.VISIBLE); break;
            case R.id.rb_no5: ll_Comunity.setVisibility(GONE); _col10.setText("");break;
            case R.id.rb_yes6: ll_Domestic.setVisibility(View.VISIBLE); break;
            case R.id.rb_no6: ll_Domestic.setVisibility(GONE); _col12.setText("");break;
            case R.id.rb_yes7: ll_NGO.setVisibility(View.VISIBLE); break;
            case R.id.rb_no7: ll_NGO.setVisibility(GONE); _col14.setText("");break;
            case R.id.rb_yes8: ll_Books.setVisibility(View.VISIBLE); break;
            case R.id.rb_no8: ll_Books.setVisibility(GONE); other1.setText("");break;
            case R.id.rb_yes9: ll_Furniture.setVisibility(View.VISIBLE); break;
            case R.id.rb_no9: ll_Furniture.setVisibility(GONE); other2.setText("");break;
            case R.id.rb_yes10: ll_Food.setVisibility(View.VISIBLE); break;
            case R.id.rb_no10: ll_Food.setVisibility(GONE); other3.setText("");break;
            case R.id.rb_yes11: ll_Supplies.setVisibility(View.VISIBLE); break;
            case R.id.rb_no11: ll_Supplies.setVisibility(GONE); other4.setText("");break;
            case R.id.rb_yes12: ll_Training.setVisibility(View.VISIBLE); break;
            case R.id.rb_no12: ll_Training.setVisibility(GONE); other5.setText("");break;

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
