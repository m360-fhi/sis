package com.example.sergio.sistz.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sergio on 2/22/2016.
 */
public class CopyAssetDBUtility {
   // public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
//    public static String getLang(Context context){
//        String getLang="";
//        Conexion cnSET = new Conexion(context, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
//        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
//        Cursor cur_data = dbSET.rawQuery("SELECT lang from lang", null);
//        cur_data.moveToFirst();
//        getLang = cur_data.getString(0);
//        return getLang;
//    }

    public static void copyDB (Context context,String dbName) {

        File dbFile = new File (Environment.getExternalStorageDirectory() + File.separator + "sisdb" + File.separator + dbName);
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "sisdb");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!dbFile.exists()) {
            InputStream is = null;
            try {
                is = context.getAssets().open(dbName);
                OutputStream os = new FileOutputStream(dbFile);

                byte[] buffer = new byte[1024];
                while (is.read(buffer) > 0) {
                    os.write(buffer);
                }

                os.flush();
                os.close();
                is.close();

//                String sqlCreate_table_logFunction = "CREATE TABLE IF NOT EXISTS\"logfunctions\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"startlog\"  TEXT, \"locationlog\" TEXT, \"finishlog\" TEXT, \"flag\" INTEGER DEFAULT 1)";
//                Conexion cnSETlogFunction = new Conexion(context, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
//                SQLiteDatabase dbSETlogFunction = cnSETlogFunction.getReadableDatabase();
//                dbSETlogFunction.execSQL(sqlCreate_table_logFunction);
//                dbSETlogFunction.close();
//                cnSETlogFunction.close();

            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }
}
