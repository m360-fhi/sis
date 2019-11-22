package com.example.sergio.sistz;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jlgarcia on 27/04/2016.
 */
public class ReportStudent extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    private final static String DB_INDICATORS_NAME = "sisdb.sqlite";
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    LinearLayout ll_teacher, ll_staff;
    CheckBox _col2a, _col2b, _col2c, _col2d, _col2e, _col2f, _col2g, _col2h;
    FloatingActionButton btn_confirm;
    int indiceGroupList=0, selectTS=0, selectAcad_q=0;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_student);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);
        ll_teacher = (LinearLayout) findViewById(R.id.ll_Teacher);
        btn_confirm = (FloatingActionButton) findViewById(R.id.btn_confirm);
        _col2a = (CheckBox) findViewById(R.id._col2a);
        _col2b = (CheckBox) findViewById(R.id._col2b);
        _col2c = (CheckBox) findViewById(R.id._col2c);
        _col2d = (CheckBox) findViewById(R.id._col2d);
        _col2e = (CheckBox) findViewById(R.id._col2e);
        _col2f = (CheckBox) findViewById(R.id._col2f);
        _col2g = (CheckBox) findViewById(R.id._col2g);
        _col2h = (CheckBox) findViewById(R.id._col2h);



        fl_part2.setVisibility(View.GONE);
        fl_part1.setVisibility(View.VISIBLE);

        btn_confirm.setOnClickListener(this);
        _col2a.setOnClickListener(this);
        _col2b.setOnClickListener(this);




    }

    private void validaListData(){
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.clear();
        listDataChild.clear();
        indiceGroupList=0;

        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();

        // Adding child data

        if (_col2b.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT s._id, s.family, s.givenname, s.surname, CASE sa.section WHEN 1 THEN \"A\" WHEN 2 THEN \"B\" END  FROM student AS s\n" +
                        " INNER JOIN _sa  AS sa ON (sa.sc=s._id AND sa.shift=1 AND sa.level=1 AND sa.grade=4 )", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Morning - Primary - Grade 4 [Stream]");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(3) + " [" + cur_data.getString(4) + "]" );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++;
                }
            }catch (Exception e) {}
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT s._id, s.family, s.givenname, s.surname, CASE sa.section WHEN 1 THEN \"A\" WHEN 2 THEN \"B\" END  FROM student AS s\n" +
                        " INNER JOIN _sa  AS sa ON (sa.sc=s._id AND sa.shift=1 AND sa.level=1 AND sa.grade=5 )", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Morning - Primary - Grade 5 [Stream]");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(3) + " [" + cur_data.getString(4) + "]" );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++;
                }
            }catch (Exception e) {}
         }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                validaListData();
                break;
        }
    }
}
