package com.example.sergio.sistz;

/**
 * Created by jlgarcia on 03/06/2016.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.os.ResultReceiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadServiceBK extends IntentService {

    public static final int DOWNLOAD_ERROR = 10;
    public static final int DOWNLOAD_SUCCESS = 11;

    public DownloadServiceBK() {
        super(DownloadServiceBK.class.getName());
// TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
// TODO Auto-generated method stub
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String url = intent.getStringExtra("url");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();

        File downloadFile = new File("/sdcard/sisdbweb/sisdb.sqlite");
        if (downloadFile.exists())
            downloadFile.delete();
        try {
            File dir = new File ("/sdcard/sisdbweb");
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            downloadFile.createNewFile();
            URL downloadURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) downloadURL
                    .openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200)
                throw new Exception("Error in connection");
            InputStream is = conn.getInputStream();
            FileOutputStream os = new FileOutputStream(downloadFile);
            byte buffer[] = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                os.write(buffer, 0, byteCount);
            }
            os.close();
            is.close();

            String filePath = downloadFile.getPath();
            bundle.putString("filePath", filePath);
            receiver.send(DOWNLOAD_SUCCESS, bundle);

        } catch (Exception e) {
// TODO: handle exception
            receiver.send(DOWNLOAD_ERROR, Bundle.EMPTY);
            e.printStackTrace();
        }
    }

}
