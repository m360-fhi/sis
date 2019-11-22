package com.example.sergio.sistz.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sergio on 3/21/2016.
 */
public class TeacherDBHelper extends SQLiteOpenHelper{

    public TeacherDBHelper(Context context) {
        super(context, TeacherAssing.DB_NAME , null, TeacherAssing.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                //"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT,", // emis
                                "%s TEXT,", // tc
                                "%s TEXT,", // shift
                                "%s TEXT,", // level
                                "%s TEXT,", // grade
                                "%s TEXT,", // section
                                "%s TEXT)", // subject
                        TeacherAssing.TABLE,
                        TeacherAssing.Columns.emis,
                        TeacherAssing.Columns.tc,
                        TeacherAssing.Columns.shift,
                        TeacherAssing.Columns.grade,
                        TeacherAssing.Columns.section,
                        TeacherAssing.Columns.subject);

        Log.d("TeacherDBHelper", "Query to form table: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+TeacherAssing.TABLE);
        onCreate(sqlDB);

    }

}
