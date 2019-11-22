package com.example.sergio.sistz;

/**
 * Created by Sergio on 4/14/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Environment;
import android.util.Log;

import com.example.sergio.sistz.data.SISConstants;
import com.example.sergio.sistz.mysql.Conexion;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class APIUtility {
    //Datos Miembro
    private String data;
    @SuppressWarnings("unused")
    private boolean error;
    private HttpContext httpContext;
    private DefaultHttpClient httpClient;
    private HttpPost post;
    private HttpGet get;
    private boolean isPost;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";

    SQLiteDatabase dbSET;
    Conexion cnSET;

    public void saveData(String datos, Context context) {
        cnSET = new Conexion(context,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        dbSET = cnSET.getReadableDatabase();
        ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("str", datos));
        sendData(SISConstants.API_URL, data, false, datos); // *** PORALG ****
        dbSET.close();
        cnSET.close();

    }



    private void sendData(String url,ArrayList<NameValuePair> params,boolean isPOST, String sms) {
        this.isPost = isPOST;
        httpContext  = new BasicHttpContext();
        httpClient = new DefaultHttpClient();
        if (isPost) {
            post = new HttpPost(url);
            try {
                post.setEntity(new UrlEncodedFormEntity(params));

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            String query = URLEncodedUtils.format(params, "utf-8");
            if (params.size()>0) {
                get = new HttpGet(url+"?"+query);
            } else {
                get = new HttpGet(url);
            }
        }
        data=null;
        BasicHttpResponse response;
        InputStream is;
        try {
            if (isPost) {
                response = (BasicHttpResponse) httpClient.execute(post,httpContext);
            } else {
                response =  (BasicHttpResponse)httpClient.execute(get, httpContext);
            }
            is = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                respuesta.append(linea);
            }
            Log.i("API", respuesta.toString());
            br.close();
            is.close();
            if (linea!="") {

                data= respuesta.toString();
                Log.d("JSONDATA", data);

                ContentValues sql = new ContentValues();
                sql.put("flag","0");
                boolean dbUpdated = false;
                int attempts = 0;
                while(!dbUpdated) {
                    try {
                        String msglog2="";
                        if (sms.contains("`") ){
                            msglog2=sms.replaceAll("`","''");
                        }
                        else if (sms.contains("'") ){
                            msglog2=sms.replaceAll("'","''");
                        }
                        else{
                            msglog2=sms;
                        }
                        dbSET.update("sisupdate", sql, "sis_sql='" + msglog2+"'", null);
                        dbUpdated = true;
                        Log.e("API", "Update database " + attempts);
                    } catch(SQLiteDatabaseLockedException exception) {
                        Log.e("API", "Error in update database " + attempts);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        attempts++;
                    }
                }



            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error = true;

        }
    }

    public String getData() {

        return data;
    }
}
