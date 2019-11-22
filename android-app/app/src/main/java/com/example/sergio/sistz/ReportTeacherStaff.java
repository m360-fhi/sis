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
public class ReportTeacherStaff extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    private final static String DB_INDICATORS_NAME = "sisdb.sqlite";
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    LinearLayout ll_teacher, ll_staff;
    RadioButton _col1a, _col1b, _col1c;
    CheckBox _col2a, _col2b, _col2c, _col2d, _col2e, _col2f, _col2g, _col2h, _col3a, _col3b, _col3c, _col3d;
    FloatingActionButton btn_confirm;
    int indiceGroupList=0, selectTS=0, selectAcad_q=0;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_teacher_staff);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);
        ll_teacher = (LinearLayout) findViewById(R.id.ll_Teacher);
        ll_staff = (LinearLayout) findViewById(R.id.ll_Staff);
        btn_confirm = (FloatingActionButton) findViewById(R.id.btn_confirm);
        _col1a = (RadioButton) findViewById(R.id._col1a);
        _col1b = (RadioButton) findViewById(R.id._col1b);
        _col1c = (RadioButton) findViewById(R.id._col1c);
        _col2a = (CheckBox) findViewById(R.id._col2a);
        _col2b = (CheckBox) findViewById(R.id._col2b);
        _col2c = (CheckBox) findViewById(R.id._col2c);
        _col2d = (CheckBox) findViewById(R.id._col2d);
        _col2e = (CheckBox) findViewById(R.id._col2e);
        _col2f = (CheckBox) findViewById(R.id._col2f);
        _col2g = (CheckBox) findViewById(R.id._col2g);
        _col2h = (CheckBox) findViewById(R.id._col2h);
        _col3a = (CheckBox) findViewById(R.id._col3a);
        _col3b = (CheckBox) findViewById(R.id._col3b);
        _col3c = (CheckBox) findViewById(R.id._col3c);
        _col3d = (CheckBox) findViewById(R.id._col3d);


        fl_part2.setVisibility(View.GONE);
        fl_part1.setVisibility(View.VISIBLE);
        ll_teacher.setVisibility(View.GONE);
        ll_staff.setVisibility(View.GONE);
        btn_confirm.setOnClickListener(this);
        _col1a.setOnClickListener(this);
        _col1b.setOnClickListener(this);
        _col1c.setOnClickListener(this);
        _col2a.setOnClickListener(this);
        _col2b.setOnClickListener(this);




    }

    private void validaListData(){
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

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
        if (_col2a.isChecked()==true) {
            try {
                //Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t INNER JOIN _ta  AS ta ON (ta.tc=_id AND ta.shift=1 AND ta.level=1 AND ta.grade=2)", null);
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=1 AND ta.level=3 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Morning Pre-primary");
                    List<String> top250 = new ArrayList<String>();
                    do {
                        top250.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), top250); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}

         }
        if (_col2b.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=1 AND ta.level=1 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Morning Primary");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
         }
        if (_col2c.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=1 AND ta.level=2 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Morning Secondary");
                    List<String> comingSoon = new ArrayList<String>();
                    do {
                        comingSoon.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), comingSoon); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
         }
        if (_col2d.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=2 AND ta.level=3 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Afternoon Pre-primary");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col2e.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=2 AND ta.level=1 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Afternoon Primary");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col2f.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=2 AND ta.level=2 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Afternoon Secondary");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col2g.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=3 AND ta.level=1 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Evening Primary");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col2h.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "INNER JOIN _ta  AS ta ON (ta.tc=t._id AND ta.shift=3 AND ta.level=1 )\n" +
                        "WHERE t.t_s=" + selectTS + "\n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Evening Secondary");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }

        if (_col1c.isChecked()==true && _col3a.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "WHERE t.t_s=" + selectTS + " AND t.acad_q=1 \n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Didn't complete secondary school");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col1c.isChecked()==true && _col3b.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "WHERE t.t_s=" + selectTS + " AND t.acad_q=2 \n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Completed secondary school");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col1c.isChecked()==true && _col3c.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "WHERE t.t_s=" + selectTS + " AND t.acad_q=3 \n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Any Bachelor's degree");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
                }
            }catch (Exception e) {}
        }
        if (_col1c.isChecked()==true && _col3d.isChecked()==true) {
            try {
                Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.givenname, t.surname  FROM teacher   AS t \n" +
                        "WHERE t.t_s=" + selectTS + " AND t.acad_q=4 \n" +
                        "GROUP BY t._id, t.givenname, t.surname ", null);
                cur_data.moveToFirst();
                if (cur_data.getCount() > 0) {
                    listDataHeader.add("Any Masters'degree or above");
                    List<String> nowShowing = new ArrayList<String>();
                    do {
                        nowShowing.add(cur_data.getString(0) + " - " + cur_data.getString(1) + ", " + cur_data.getString(2) );
                    } while (cur_data.moveToNext());
                    listDataChild.put(listDataHeader.get(indiceGroupList), nowShowing); indiceGroupList++; // Header, Child data
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
            case R.id._col1a:
                ll_staff.setVisibility(View.GONE);
                ll_teacher.setVisibility(View.VISIBLE);
                _col3a.setChecked(false);
                _col3b.setChecked(false);
                _col3c.setChecked(false);
                _col3d.setChecked(false);
                selectTS=1;
                break;
            case R.id._col1b:
                ll_staff.setVisibility(View.GONE);
                ll_teacher.setVisibility(View.VISIBLE);
                _col3a.setChecked(false);
                _col3b.setChecked(false);
                _col3c.setChecked(false);
                _col3d.setChecked(false);
                selectTS=2;
                break;
            case R.id._col1c:
                ll_teacher.setVisibility(View.GONE);
                ll_staff.setVisibility(View.VISIBLE);
                _col2a.setChecked(false);
                _col2b.setChecked(false);
                _col2c.setChecked(false);
                _col2d.setChecked(false);
                _col2e.setChecked(false);
                _col2f.setChecked(false);
                _col2g.setChecked(false);
                _col2h.setChecked(false);
                selectTS=3;
                break;
            case R.id._col2a:
                // sumar una variable si es check y restar si quita check
                break;
            case R.id._col2b:
                // sumar la misma variable si es check y restar si quita check
                break;
            case R.id._col3a: selectAcad_q = 1; break;
            case R.id._col3b: selectAcad_q = 2; break;
            case R.id._col3c: selectAcad_q = 3; break;
            case R.id._col3d: selectAcad_q = 4; break;
        }
    }


}
