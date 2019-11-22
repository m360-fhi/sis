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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 2/26/2016.
 */
public class UP_SettingsMenuInfra_2_matriz extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_parta, fl_part2; // ************ FrameLayout ***************
    EditText _col1, _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14;
    //*******************  TableLayout *********************
    private List<EditText> editTextList = new ArrayList<EditText>();
    private String[] grade = {"Textbooks", "Std. I", "Std. II", "Std. III", "Std. IV", "Std. V", "Std. VI", "Std. VII"};
    private String[] subjects = {"Mathematics", "English", "Kiswahili", "French", "Science", "Geography", "Civics", "History", "Vocational Skills", "Personality and Sports", "ICT", "Other"};
    private int a= 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu_infra_2);
        int count = 104;
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        ImageButton btn_next = (ImageButton) findViewById(R.id.btn_next);
        ImageButton btn_save = (ImageButton) findViewById(R.id.btn_save);
        ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);
        fl_part1.setVisibility(View.VISIBLE);
        fl_part2.setVisibility(View.GONE);
        btn_next.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(submitListener);
        btn_exit.setOnClickListener(this);
        loadRecord();
        fl_part2.addView(tableLayout(count));
   }


    public void loadRecord() {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, solid, semisolid, makeshift, partitioned, openair_utt, other, chairs, desks1, desks2, desk3, benches1, benches2, benches3, chalkboard\n" +
                "FROM set_school_pp_operation WHERE _id=1",null);
        cur_data.moveToFirst();
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        ContentValues reg = new ContentValues();
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
    @Override
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
    // *********** END Control Alerts ************************


    // ************************** Begin TableLayout **************
    private TableLayout tableLayout(int count) {

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        int noOfRows = count / 8;
        for (int i = 0; i < noOfRows; i++) {
            int rowId = 8 * i;
            if (i == 0 ){
                tableLayout.addView(createFirstFullRow(rowId));
            }
            else {
                tableLayout.addView(createOneFullRow(rowId));
            }

        }
        int individualCells = count % 8;
        tableLayout.addView(createLeftOverCells(individualCells, count));
        return tableLayout;

    }

    private TableRow createLeftOverCells(int individualCells, int count) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
        int rowId = count - individualCells;
        for (int i = 1; i <= individualCells; i++) {

            tableRow.addView(editText(String.valueOf(rowId + i)));
        }
        return tableRow;
    }

    private TableRow createOneFullRow(int rowId) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);

        for (int i = 1; i <= 8; i++) {
            if (i==1 ){

                tableRow.addView(textView(subjects[a]));
                a++;
            }
            else {
                tableRow.addView(editText(String.valueOf(rowId + i)));
            }
        }
        return tableRow;
    }

    private TableRow createFirstFullRow(int rowId) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
        for (int i = 0; i <= 7; i++) {

            tableRow.addView(textView(grade[rowId+i]));
        }
        return tableRow;
    }

    private EditText editText(String hint) {
        EditText editText = new EditText(this);
        editText.setId(Integer.valueOf(hint));
        if (Integer.valueOf(hint) == 60){
            editText.setText("60");
        }
        editTextList.add(editText);
        return editText;

    }

    private TextView textView(String txt) {
        TextView textView = new TextView(this);
        textView.setText(txt);
        return textView;
    }
    //*************************** End TableLayout **************

    private View.OnClickListener submitListener = new View.OnClickListener() {
        public void onClick(View view) {
            StringBuilder stringBuilder = new StringBuilder();
            for (EditText editText : editTextList) {
                stringBuilder.append(editText.getText().toString());
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                editText.getText().toString(), Toast.LENGTH_SHORT);

                toast1.show();
            }
        }
    };
}
