package com.example.sergio.sistz.mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.sergio.sistz.util.FileUtility;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sergio on 3/18/2016.
 */
public class DBSubjectsUtils {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    private static final String DB_NAME = STATICS_ROOT + File.separator + "sisdb.sqlite";
    private DBUtility conn;
    public static final String TABLE_NAME ="subject";

    public static final String CN_COL1 = "id";
    public static final String CN_COL2 = "subject";

    private DBSubjectHelper dbHelper;
    private Context context;

    public DBSubjectsUtils(Context context) {
        this.context = context;
        dbHelper = new DBSubjectHelper(context);
    }

    public ArrayList<String> getSubjects() {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase dbSubject = dbHelper.getWritableDatabase();

        Cursor cursor = dbSubject.rawQuery("SELECT id, subject FROM subject", null);

        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
                data.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return data;
    }

    private class DBSubjectHelper extends SQLiteOpenHelper {

        public DBSubjectHelper(Context context) {
            super(context, DB_NAME, null, DBUtility.DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String createTable = "create table " + TABLE_NAME + " ("
                    + CN_COL1 + " integer primary key autoincrement,"
                    + CN_COL2 + " text not null);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            conn = new DBUtility(context);
            String json = FileUtility.getQueriesUpdates(context);
            try {
                JSONArray jsonQueries = new JSONArray(json);
                conn.executeUpdates(jsonQueries, db);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
