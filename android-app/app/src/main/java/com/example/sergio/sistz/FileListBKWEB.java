package com.example.sergio.sistz;

/**
 * Created by jlgarcia on 03/06/2016.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sergio.sistz.data.SISConstants;
import com.example.sergio.sistz.util.BkDbUtility;
import com.example.sergio.sistz.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PJYAC on 27/05/2016.
 */
public class FileListBKWEB extends Activity {
    private static String url;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String STATICS_ROOT_BK_WEB = Environment.getExternalStorageDirectory() + File.separator + "sisdbweb";
    private List<String> fileList = new ArrayList<String>();
    ListView lv_list;
    private Bundle bundle;
    SampleResultReceiver resultReceiever;

    ProgressBar pd;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=getIntent().getExtras();
        setContentView(R.layout.bk_web);
        String emis = bundle.getString("emis");
        resultReceiever = new SampleResultReceiver(new Handler());
        pd = (ProgressBar) findViewById(R.id.downloadPD);
        lv_list = (ListView) findViewById(R.id.lv_list_bk_web);
        json(emis);
    }

    @SuppressLint("ParcelCreator")
    private class SampleResultReceiver extends ResultReceiver {

        public SampleResultReceiver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // TODO Auto-generated method stub
            switch (resultCode) {
                case DownloadService.DOWNLOAD_ERROR:
                    Toast.makeText(getApplicationContext(), "error in download",
                            Toast.LENGTH_SHORT).show();
                    pd.setVisibility(View.INVISIBLE);
                    break;

                case DownloadService.DOWNLOAD_SUCCESS:



                    Toast.makeText(getApplicationContext(),
                            "BK download via IntentService is done",
                            Toast.LENGTH_SHORT).show();
                    BkDbUtility.restoreFile(STATICS_ROOT_BK_WEB, "sisdb.sqlite", STATICS_ROOT);
                    pd.setIndeterminate(false);
                    pd.setVisibility(View.INVISIBLE);

                    break;
            }
            super.onReceiveResult(resultCode, resultData);
        }

    }
    void json(String emis){
        url = SISConstants.PHP_TO_UPLOAD_FILE+emis;
        JSONParser jParser = new JSONParser();

        // getting JSON string from URL
        JSONArray json = jParser.getJSONFromUrl(url);
        if (json != null) {
            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject c = json.getJSONObject(i);
                    String vtype = c.getString("data");
                    fileList.add(vtype);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
            }
        }
        ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, fileList);
        lv_list.setAdapter(adap_list);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                dialogAlert1(fileList.get(posicion));
            }
        });

    }

    public void dialogAlert1(final String database){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Important");
        dialogo1.setMessage("Are you sure to restore this data base");

        dialogo1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent startIntent = new Intent(FileListBKWEB.this,
                        DownloadServiceBK.class);
                startIntent.putExtra("receiver", resultReceiever);
                startIntent.putExtra("url", SISConstants.URL_BACKUP_DB + bundle.getString("emis") + "/" + database);
                startService(startIntent);
                pd.setVisibility(View.VISIBLE);
                pd.setIndeterminate(true);

            }
        });
        dialogo1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {}
}
