package com.example.sergio.sistz;

/**
 * Created by Sergio on 4/14/2016.
 */
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PJYAC on 22/03/2016.
 */
public class service_sms extends Service {
    private Timer timer= new Timer();
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    private APIUtility apiUtility;
    // Connection detector class
    ConnectionDetector cd;
    private static final long UPDATE_INTERVAL=60000;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String STATICS_ROOT_DB = STATICS_ROOT + File.separator + "sisdb.sqlite";
    ArrayList<String> list_g3 = new ArrayList<>();
    ArrayList<String> list_g2 = new ArrayList<>();
    ArrayList<String> list_num = new ArrayList<>();
    String phoneNo;

    @Override
    public void onCreate() {
        super.onCreate();

        smssend();


    }

    private void smssend(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                apiUtility = new APIUtility();
                Conexion cnGEO3 = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
                SQLiteDatabase dbGEO3 = cnGEO3.getWritableDatabase();
                Cursor cur_num = dbGEO3.rawQuery("SELECT mobil_server from login ", null);
                String col_num;
                cur_num.moveToFirst();
                if (cur_num.moveToFirst()) {
                    do {


                        col_num = cur_num.getString(0);
                        list_num.add(col_num);
                    } while (cur_num.moveToNext());
                }
                cur_num.close();
                if(list_num.isEmpty() || String.valueOf(list_num.get(0)).equals("null")){
                    phoneNo = "0";

                }
                else{
                    phoneNo = String.valueOf(list_num.get(0));
                    list_num.clear();
                }


                Cursor cur_g3 = dbGEO3.rawQuery("select sis_sql from sisupdate where flag=1", null);
                String col_g3, col_g2;
                cur_g3.moveToFirst();
                if (cur_g3.moveToFirst()) {
                    do {
                        col_g3 = cur_g3.getString(0);
                        list_g3.add(col_g3);
                    } while (cur_g3.moveToNext());
                }
                cur_g3.close();
                cnGEO3.close();
                dbGEO3.close();
                for (int a = 0; a < list_g3.size(); a++) {
                    cd = new ConnectionDetector(getApplicationContext());
                    isInternetPresent = cd.isConnectingToInternet() && ConnectionDetector.isInternetAvailable(true);
                    String msglog = list_g3.get(a);
                    String msglog2=msglog.replaceAll("'","`");
                    if (isInternetPresent) {
                        apiUtility.saveData(msglog2,getApplicationContext());
                    } else {
                        if(phoneNo.equals("0")){

                        }
                        else{
                            sendSMSMessage(msglog2);
                        }
                    }
                }
                list_g2.clear();
                list_g3.clear();

            }
        }, 0, UPDATE_INTERVAL);
    }


    //protected void sendSMSMessage(String v1, String v2) {
    protected void sendSMSMessage(String v1) {
        Log.i("Send SMS", "");


        final String message = v1;
        try {

            SmsManager smsManager = SmsManager.getDefault();
            //modificacion 01032016

            String SMS_SENT = "SMS_SENT";
            String SMS_DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            ArrayList<String> smsBodyParts = smsManager.divideMessage(message);
            ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
            ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

            for (int i = 0; i < smsBodyParts.size(); i++) {
                sentPendingIntents.add(sentPendingIntent);
                deliveredPendingIntents.add(deliveredPendingIntent);
            }
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Conexion cnGEO3 = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
                    SQLiteDatabase dbGEO3 = cnGEO3.getWritableDatabase();
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(context, "Data packet sent successfully", Toast.LENGTH_SHORT).show();
                            ContentValues sql = new ContentValues();
                            sql.put("flag","0");
                            dbGEO3.update("sisupdate", sql, "sis_sql='" + message + "'", null);
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_SENT));

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Conexion cnGEO3 = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
                    SQLiteDatabase dbGEO3 = cnGEO3.getWritableDatabase();

                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "Packet delivered", Toast.LENGTH_SHORT).show();
                            ContentValues sql = new ContentValues();
                            sql.put("flag","0");
                            String mslog2="";
                            if (message.contains("`") ){
                                mslog2 =message.replaceAll("`","''");
                            }
                            else{
                                mslog2=message;
                            }
                            dbGEO3.update("sisupdate", sql, "sis_sql='" + mslog2 + "'", null);
                            dbGEO3.close();
                            cnGEO3.close();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_DELIVERED));

            smsManager.sendMultipartTextMessage(phoneNo, null, smsBodyParts, sentPendingIntents, deliveredPendingIntents);


        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
