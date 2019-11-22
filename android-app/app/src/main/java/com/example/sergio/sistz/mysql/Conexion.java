package com.example.sergio.sistz.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sergio.sistz.util.FileUtility;
import com.example.sergio.sistz.util.toolsfncs;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Sergio on 2/22/2016.
 */
public class Conexion extends SQLiteOpenHelper {
    private Context context;
    private DBUtility conn;
    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, DBUtility.DB_VER);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
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

