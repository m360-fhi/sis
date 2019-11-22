package com.example.sergio.sistz.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;

import java.io.File;

/**
 * Created by jlgarcia on 20/03/2017.
 */

public class toolsfncs extends Activity{
    //public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb.sqlite";
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";

    DBUtility conn;

    public static void logFunctions(Context contexto, String startLog, String locationLog, String finishLog) {
        Conexion cnSET = new Conexion(contexto,STATICS_ROOT, null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();

        ContentValues logfcns = new ContentValues();
        logfcns.put("startlog", startLog);
        logfcns.put("locationlog", locationLog);
        logfcns.put("finishlog", finishLog);
        dbSET.insert("logfunctions", null, logfcns);

    }


    // *********** Control Alerts ************************
    public static void dialogAlertConfirm(Context contexto, Resources resources, int v){
        //Toast.makeText(getContext(),String.valueOf(v) ,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(contexto);
        dialogo1.setTitle(resources.getString(R.string.str_bl_msj1)); // Importante
        if (v == 1){dialogo1.setMessage(resources.getString(R.string.str_g_msj1));}
        if (v == 2){dialogo1.setMessage(resources.getString(R.string.str_g_msj2));} // Your application is up to date.
        if (v == 3){dialogo1.setMessage(resources.getString(R.string.str_g_msj3));} // You have made a Local backup.
        if (v == 4){dialogo1.setMessage(resources.getString(R.string.str_g_msj4));} // You have made a Local restore.
        if (v == 5){dialogo1.setMessage(resources.getString(R.string.str_g_msj5));} // You have made a WEB backup.
        if (v == 6){dialogo1.setMessage(resources.getString(R.string.str_g_msj6));} // You have made a WEB restore.
        if (v == 7){dialogo1.setMessage(resources.getString(R.string.synchronizing));} // You have made a WEB restore.
        if (v == 8){dialogo1.setMessage(resources.getString(R.string.not_connected_internet));} // You have made a WEB restore.
        if (v == 9){dialogo1.setMessage(resources.getString(R.string.str_bl_msj5));} // The information has been update...
        if (v == 10){dialogo1.setMessage(resources.getString(R.string.str_g_therearenot_student));} // There are not student assigned...
        if (v == 11){dialogo1.setMessage(resources.getString(R.string.automatic_promotion));} // Automatic promotion...
        if (v == 12){dialogo1.setMessage(resources.getString(R.string.str_g_enter_information));} // Automatic promotion...
        if (v == 13){dialogo1.setMessage(resources.getString(R.string.str_g_change_emis_code));} // Automatic promotion...
        if (v == 14){dialogo1.setMessage(resources.getString(R.string.str_w_a));} // EMIS Code...
        if (v == 15){dialogo1.setMessage(resources.getString(R.string.str_w_b));} // School name..
        if (v == 16){dialogo1.setMessage(resources.getString(R.string.str_validate_reason_absence));} // School name..



        dialogo1.setCancelable(false);

        dialogo1.setPositiveButton(resources.getString(R.string.str_g_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                confirm();
            }
//        }).setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogo1, int id) {
//                aceptar();
//            }
        });
        if (v == 11) {
            dialogo1.setNegativeButton(resources.getString(R.string.str_g_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancel();
                }
            });
        }


        dialogo1.show();
    }

    private static void cancel() {
    }

    private static void confirm() {
    }
    private static void closeInformation() {
    }


    public static String getEMIS_code(Context context){
        String getemiscode="";
        Conexion cnSET = new Conexion(context, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}
        dbSET.close();
        cnSET.close();
        return getemiscode;
    }

    public static void updateLanguageTable() {

    }

}
