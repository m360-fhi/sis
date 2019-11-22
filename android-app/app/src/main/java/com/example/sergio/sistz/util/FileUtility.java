package com.example.sergio.sistz.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.example.sergio.sistz.mysql.DBUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static java.lang.System.in;

public class FileUtility {
    private static final String QUERY_FILE = "queries.txt";
    public static String getQueriesUpdates(Context context) {
        AssetManager assetManager = context.getAssets();
        BufferedReader br = null;
        String  line;
        StringBuilder json = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(assetManager.open(QUERY_FILE)));
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
        } catch(IOException e) {
            Log.e("tag", "Failed to copy asset file: " + QUERY_FILE, e);
        }
        return json.toString();
    }

    public static void returnOriginal(Context context) {
        File fdelete = new File(DBUtility.STATICS_ROOT + File.separator + DBUtility.DB_NAME);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + DBUtility.STATICS_ROOT + File.separator + DBUtility.DB_NAME);
            } else {
                System.out.println("file not Deleted :" + DBUtility.STATICS_ROOT + File.separator + DBUtility.DB_NAME);
            }
        }
        changeDataBaseFile("", "dbsis.sqlite.bak", DBUtility.DB_NAME, context);
    }
    public static void changeDataBaseFile(String hil, String source, String target,Context context) {
        DBUtility conn;
        String sourcepath = DBUtility.STATICS_ROOT + File.separator + source;
        String targetpath = DBUtility.STATICS_ROOT + File.separator + target;
        File sourceLocation= new File (sourcepath);
        File targetLocation= new File (targetpath);

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(sourceLocation);
            out =  new FileOutputStream(targetLocation);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!hil.equals("")) {
            //change emis code inDB
            conn = new DBUtility(context);
            conn.changeEMISCode(hil);
        }


    }


}
