package com.example.sergio.sistz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.data.SISConstants;
import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.BKDBWebUtility;
import com.example.sergio.sistz.util.BkDbUtility;
import com.example.sergio.sistz.util.CopyAssetDBUtility;
import com.example.sergio.sistz.util.toolsfncs;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String STATICS_ROOT_BK = Environment.getExternalStorageDirectory() + File.separator + "sisdbBK";
    private final static String DB_INDICATORS_NAME = "sisdb.sqlite";
    public String _EMISCODE="", _PhoneNumber="";
    FrameLayout fl2, fl3, fl4, fl5, fl6;
    LinearLayout ll_baseline, ll_dailyClassroom,ll_finance, ll_reports;
    ImageButton btn_base, btn_infra, btn_teacher, btn_student, btn_daily, btn_attendance, btn_evaluation, btn_behavior, btn_report, btn_setting,status, btn_number, btn_finance;
    ImageButton btn_ts, btn_st, btn_a, btn_f;
    ArrayList<String> list_num = new ArrayList<>();
    SampleResultReceiver resultReceiever;
    String defaultUrl = "http://developer.android.com/assets/images/dac_logo.png";
    ProgressBar pd;
    public Locale locale;
    public Configuration config= new Configuration();
    TextView tv_baseline, tv_dailyclassroom, tv_finance, tv_reports;
    public static Integer language=1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=124;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    private GoogleApiClient client;
    Calendar calendar = Calendar.getInstance();
    public int school_year = calendar.get(Calendar.YEAR);
    public int actual_year = calendar.get(Calendar.YEAR);

    ProgressDialog progress;
    private int totalProgressTime = 0;
    private Handler handler = new Handler();

    int data_transfer_ready_done; // Cambio 11.Enero.2018 para indicar si hay informacion pendiente de enviar
    ImageButton btn_data_transfer;
    private DBUtility connDB;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            status.setBackgroundResource(R.drawable.internet_signal);
        } else {
            status.setBackgroundResource(R.drawable.cell_signal);
        }
        server_number();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showLanguage();
        if (getEMIS_code() == "") {finish();}
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            status.setBackgroundResource(R.drawable.internet_signal);
        } else {
            status.setBackgroundResource(R.drawable.cell_signal);
        }
        server_number();
        _PhoneNumber = getPhone_number();
        onCheckDataTransferReady();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            status.setBackgroundResource(R.drawable.internet_signal);
        } else {
            status.setBackgroundResource(R.drawable.cell_signal);
        }
        server_number();
        onCheckDataTransferReady();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }



        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
        connDB = new DBUtility(this);
        String sqlCreate_table_logFunction = "CREATE TABLE IF NOT EXISTS\"logfunctions\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"startlog\"  TEXT, \"locationlog\" TEXT, \"finishlog\" TEXT, \"flag\" INTEGER DEFAULT 1)";
        Conexion cnSETlogFunction = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSETlogFunction = cnSETlogFunction.getReadableDatabase();
        dbSETlogFunction.execSQL(sqlCreate_table_logFunction);
        dbSETlogFunction.close();
        cnSETlogFunction.close();
        Locale locale = new Locale(getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
        getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_main);
        resultReceiever = new SampleResultReceiver(new Handler());
        pd = (ProgressBar) findViewById(R.id.downloadPD);
        status = (ImageButton)findViewById(R.id.btn_status);
        btn_number=(ImageButton) findViewById(R.id.btn_cell_number);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            status.setBackgroundResource(R.drawable.internet_signal);
        } else {
            status.setBackgroundResource(R.drawable.cell_signal);
        }

        server_number();
        // ********************** Global vars ******************
        fl2 = (FrameLayout) findViewById(R.id.fl2);
        fl3 = (FrameLayout) findViewById(R.id.fl3);
        fl4 = (FrameLayout) findViewById(R.id.fl4);
        fl5 = (FrameLayout) findViewById(R.id.fl5);
        fl6 = (FrameLayout) findViewById(R.id.fl6);
        ll_baseline = (LinearLayout) findViewById(R.id.ll_baseline);
        ll_dailyClassroom = (LinearLayout) findViewById(R.id.ll_dailyClassroom);
        ll_finance = (LinearLayout) findViewById(R.id.ll_finance);
        ll_reports = (LinearLayout) findViewById(R.id.ll_reports);

        //  ************************ Objects Buttoms *********************
        btn_base = (ImageButton) findViewById(R.id.btn_base);
        btn_infra = (ImageButton) findViewById(R.id.btn_infra);
        btn_teacher = (ImageButton) findViewById(R.id.btn_teacher);
        btn_student = (ImageButton) findViewById(R.id.btn_student);
        btn_daily = (ImageButton) findViewById(R.id.btn_daily);
        btn_attendance = (ImageButton) findViewById(R.id.btn_attendance);
        btn_evaluation = (ImageButton) findViewById(R.id.btn_evaluation);
        btn_behavior = (ImageButton) findViewById(R.id.btn_behavior);
        btn_finance = (ImageButton) findViewById(R.id.btn_finance);
        btn_report = (ImageButton) findViewById(R.id.btn_reports);
        btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_ts = (ImageButton) findViewById(R.id.btn_ts);
        btn_st = (ImageButton) findViewById(R.id.btn_st);
        btn_a = (ImageButton) findViewById(R.id.btn_a);
        btn_f = (ImageButton) findViewById(R.id.btn_f);
        tv_baseline = (TextView) findViewById(R.id.tv_baseline);
        tv_dailyclassroom = (TextView) findViewById(R.id.tv_dailyclassroom);
        tv_finance = (TextView) findViewById(R.id.tv_finance);
        tv_reports = (TextView) findViewById(R.id.tv_reports);
        btn_data_transfer = (ImageButton) findViewById(R.id.btn_data_ready);


        //************* Start FrameLayout **************************

        fl2.setVisibility(View.VISIBLE);
        fl3.setVisibility(View.GONE);
        fl4.setVisibility(View.GONE);
        fl5.setVisibility(View.GONE);
        fl6.setVisibility(View.GONE);

        _EMISCODE = getEMIS_code();
        _PhoneNumber = getPhone_number();
        start_menu();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                            "image download via IntentService is done",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    File file = new File("/sdcard/Sis_tz.apk");
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    startActivity(intent);
                    pd.setIndeterminate(false);
                    pd.setVisibility(View.INVISIBLE);

                    break;
            }
            super.onReceiveResult(resultCode, resultData);
        }

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    void doBindService(){
        bindService(new Intent(this, service_sms.class), mConnection, Context.BIND_AUTO_CREATE);
    }
    private void start_menu () {
       // ************** AUTOMATIC UPDATE... ***************
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            if (downloadText().equals("0")) {

            } else {
                if (downloadText().equals(String.valueOf(getVersionCode(this)))) {

                }
                else{
                    dialogAlert1();
                }
            }
        }


        if (getFlagbkTable().equals("0")) {
            backup();
        }

        new_set_geo_codes();
        new_subject();

        if (!_EMISCODE.toString().equals("")) {
            _buttons();
        } else {
            Intent intent0 = new Intent(MainActivity.this, Language.class);
            startActivity(intent0);
            _buttons();
        }

        onCheckDataTransferReady();
    }

    private void _buttons () {
        btn_setting.setOnClickListener(this);
        btn_base.setOnClickListener(this);
        btn_infra.setOnClickListener(this);
        btn_teacher.setOnClickListener(this);
        btn_student.setOnClickListener(this);
        btn_daily.setOnClickListener(this);
        btn_attendance.setOnClickListener(this);
        btn_evaluation.setOnClickListener(this);
        btn_behavior.setOnClickListener(this);
        btn_finance.setOnClickListener(this);
        btn_report.setOnClickListener(this);
        btn_ts.setOnClickListener(this);
        btn_st.setOnClickListener(this);
        btn_a.setOnClickListener(this);
        btn_f.setOnClickListener(this);

        ll_baseline.setOnClickListener(this);
        ll_dailyClassroom.setOnClickListener(this);
        ll_finance.setOnClickListener(this);
        ll_reports.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_baseline:
                fl2.setVisibility(View.VISIBLE);
                fl3.setVisibility(View.GONE);
                fl4.setVisibility(View.GONE);
                fl6.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#F47321"));
                tv_baseline.setTextColor(Color.parseColor("#ffffff"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                ll_finance.setBackgroundColor(Color.parseColor("#343434"));
                tv_finance.setTextColor(Color.parseColor("#F47321"));
                ll_reports.setBackgroundColor(Color.parseColor("#343434"));
                tv_reports.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4_));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                break;
            case R.id.ll_dailyClassroom:
                fl2.setVisibility(View.GONE);
                fl3.setVisibility(View.VISIBLE);
                fl4.setVisibility(View.GONE);
                fl6.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#F47321"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#ffffff"));
                ll_finance.setBackgroundColor(Color.parseColor("#343434"));
                tv_finance.setTextColor(Color.parseColor("#F47321"));
                ll_reports.setBackgroundColor(Color.parseColor("#343434"));
                tv_reports.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5_));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                break;
            case R.id.ll_finance:
                fl2.setVisibility(View.GONE);
                fl3.setVisibility(View.GONE);
                fl4.setVisibility(View.GONE);
                fl6.setVisibility(View.VISIBLE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                ll_finance.setBackgroundColor(Color.parseColor("#F47321"));
                tv_finance.setTextColor(Color.parseColor("#ffffff"));
                ll_reports.setBackgroundColor(Color.parseColor("#343434"));
                tv_reports.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf_));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                break;
            case R.id.ll_reports:
                fl2.setVisibility(View.GONE);
                fl3.setVisibility(View.GONE);
                fl4.setVisibility(View.VISIBLE);
                fl6.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                ll_finance.setBackgroundColor(Color.parseColor("#343434"));
                tv_finance.setTextColor(Color.parseColor("#F47321"));
                ll_reports.setBackgroundColor(Color.parseColor("#F47321"));
                tv_reports.setTextColor(Color.parseColor("#ffffff"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6_));
                break;
            case R.id.btn_base:
                fl2.setVisibility(View.VISIBLE);
                fl3.setVisibility(View.GONE);
                fl4.setVisibility(View.GONE);
                fl6.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#F47321"));
                tv_baseline.setTextColor(Color.parseColor("#ffffff"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                ll_finance.setBackgroundColor(Color.parseColor("#343434"));
                tv_finance.setTextColor(Color.parseColor("#F47321"));
                ll_reports.setBackgroundColor(Color.parseColor("#343434"));
                tv_reports.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4_));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                break;
            case R.id.btn_daily:
                fl2.setVisibility(View.GONE);
                fl3.setVisibility(View.VISIBLE);
                fl4.setVisibility(View.GONE);
                fl6.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#F47321"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#ffffff"));
                ll_finance.setBackgroundColor(Color.parseColor("#343434"));
                tv_finance.setTextColor(Color.parseColor("#F47321"));
                ll_reports.setBackgroundColor(Color.parseColor("#343434"));
                tv_reports.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5_));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                break;
            case R.id.btn_finance:
                fl2.setVisibility(View.GONE);
                fl3.setVisibility(View.GONE);
                fl4.setVisibility(View.GONE);
                fl6.setVisibility(View.VISIBLE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                ll_finance.setBackgroundColor(Color.parseColor("#F47321"));
                tv_finance.setTextColor(Color.parseColor("#ffffff"));
                ll_reports.setBackgroundColor(Color.parseColor("#343434"));
                tv_reports.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.icon6_));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                break;
            case R.id.btn_reports:
                fl2.setVisibility(View.GONE);
                fl3.setVisibility(View.GONE);
                fl4.setVisibility(View.VISIBLE);
                fl6.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                ll_finance.setBackgroundColor(Color.parseColor("#343434"));
                tv_finance.setTextColor(Color.parseColor("#F47321"));
                ll_reports.setBackgroundColor(Color.parseColor("#F47321"));
                tv_reports.setTextColor(Color.parseColor("#ffffff"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                btn_finance.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
                btn_report.setImageDrawable(getResources().getDrawable(R.drawable.icon6_));
                break;
            case R.id.btn_infra:
                Intent intent1 = new Intent(MainActivity.this, SettingsMenuInfra.class);
                startActivity(intent1);
                break;
            case R.id.btn_teacher:
                Intent intent2 = new Intent(MainActivity.this, SettingsMenuStaff.class);
                startActivity(intent2);
                break;
            case R.id.btn_student:
                Intent intent3 = new Intent(MainActivity.this, SettingsMenuStudent.class);
                startActivity(intent3);
                break;
            case R.id.btn_attendance:
                Intent intent4 = new Intent(MainActivity.this, DailyClassroomAttendance.class);
                startActivity(intent4);
                break;
            case R.id.btn_evaluation:
                Intent intent5 = new Intent(MainActivity.this, DailyClassroomEvaluation.class);
                startActivity(intent5);
                break;
            case R.id.btn_behavior:
                Intent intent6 = new Intent(MainActivity.this, DailyClassroomBehaviour.class);
                startActivity(intent6);
                break;
            case R.id.btn_ts:
                Intent intent7 = new Intent(MainActivity.this, ReportTS.class);
                startActivity(intent7);
                break;
            case R.id.btn_st:
                Intent intent8 = new Intent(MainActivity.this, ReportS.class);
                startActivity(intent8);
                break;
            case R.id.btn_a:
                Intent intent9 = new Intent(MainActivity.this, ReportA.class);
                startActivity(intent9);
                break;
            case R.id.btn_f:
                Intent intent10 = new Intent(MainActivity.this, FinanceForm.class);
                startActivity(intent10);
                break;
            case R.id.btn_setting:
                popupMenu();
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sergio.sistz/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sergio.sistz/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /** Popup Menu */
    private void popupMenu()
    {
        //Crea instancia a PopupMenu
        PopupMenu popup = new PopupMenu(this, btn_setting );
        popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
        //registra los eventos click para cada item del menu
        final Context contexto = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_settings1) {
                    Intent intent1 = new Intent(MainActivity.this, SettingsMenu_0.class);
                    startActivity(intent1);
                } else if (item.getItemId() == R.id.action_settings2) {
                    Intent intent1 = new Intent(MainActivity.this, login.class);
                    startActivity(intent1);
                } else if (item.getItemId() == R.id.action_settings4) {

                    if (isInternetPresent) {
                        if (downloadText().equals("0")) {
                            dialogAlert(8);
                        } else {
                            if (downloadText().equals(String.valueOf(getVersionCode(contexto)))) {
                                dialogAlert(2);
                            }
                            else{
                                dialogAlert1();
                            }
                        }
                    }
                    else{
                        dialogAlert(8);
                    }

                } else if (item.getItemId() == R.id.action_settings5) {
                    Intent intent1 = new Intent(MainActivity.this, About.class);
                    startActivity(intent1);
                } else if (item.getItemId() == R.id.action_settings6) {
                    dialogAlert2(1); // Backup Local and WEB
                } else if (item.getItemId() == R.id.action_settings8) {
                    dialogAlert2(2);
                } else if (item.getItemId() == R.id.action_settings9) {
                    showDialog();
                } else if (item.getItemId() == R.id.action_settings10) {
                    dialogAlert3(4);
                }

                return true;
            }
        });

        popup.show();
    }



    //modificacion PY



    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {}
        return 0;
    }

    private String downloadText() {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = openHttpConnection();
        } catch (IOException e1) {
            return "";
        }

        String str = "";
        if (in != null) {
            InputStreamReader isr = new InputStreamReader(in);
            int charRead;
            char[] inputBuffer = new char[BUFFER_SIZE];
            try {
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    // ---convert the chars to a String---
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    str += readString;
                    inputBuffer = new char[BUFFER_SIZE];
                }
                in.close();
            } catch (IOException e) {
                return "";
            }
        }
        if (str.length()<=4){
        }
        else{
                str = "0";
        }
        return str;
    }


    private InputStream openHttpConnection() throws IOException {
        InputStream in = null;
        int response = -1;
        URL url = new URL(SISConstants.PING_URL);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection)) {
            throw new IOException("Not an HTTP connection");
        }

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            throw new IOException("Error connecting");
        }
        return in;
    }

    //MOdificacion py fin

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}

        return getemiscode;
    }

    public String getPhone_number(){
        String getPhoneNumber="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT mobil_server from login", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getPhoneNumber = cur_data.getString(0);} else {getPhoneNumber = "";
            }
        } catch (Exception e) {}

        return getPhoneNumber;
    }

    public String getURL(){
        String getUrl="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT server_ip from login", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getUrl = cur_data.getString(0);} else {getUrl = "";
            }
        } catch (Exception e) {}

        return getUrl;
    }


    public void createTableLang() {
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.execSQL("CREATE TABLE IF NOT EXISTS \"lang\" (\"lang\" TEXT, \"flag\" TEXT DEFAULT 0)");
        dbSET.execSQL("INSERT INTO lang VALUES(\"en\",\"0\")");

    }

    public String getLang(){
        String getLang="en";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT lang from lang", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            getLang = cur_data.getString(0);
        } else {
            createTableLang();
        }
        return getLang;
    }

    public void  addLang(){
        try {
            Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            dbSET.execSQL("ALTER TABLE ms_0  ADD lang TEXT");
        } catch (Exception e) {}
    }

    private void server_number(){
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
        if(list_num.isEmpty()){
            btn_number.setVisibility(View.VISIBLE);
        }
        else{
            btn_number.setVisibility(View.INVISIBLE);
        }
    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1)); // Importante
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_g_msj1));}
        if (v == 2){dialogo1.setMessage(getResources().getString(R.string.str_g_msj2));} // Your application is up to date.
        if (v == 3){dialogo1.setMessage(getResources().getString(R.string.str_g_msj3));} // You have made a Local backup.
        if (v == 4){dialogo1.setMessage(getResources().getString(R.string.str_g_msj4));} // You have made a Local restore.
        if (v == 5){dialogo1.setMessage(getResources().getString(R.string.str_g_msj5));} // You have made a WEB backup.
        if (v == 6){dialogo1.setMessage(getResources().getString(R.string.str_g_msj6));} // You have made a WEB restore.
        if (v == 7){dialogo1.setMessage(getResources().getString(R.string.synchronizing));} // You have made a WEB restore.
        if (v == 8){dialogo1.setMessage(getResources().getString(R.string.not_connected_internet));} // You have made a WEB restore.

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_g_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void dialogAlert1(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1)); // Importante
        dialogo1.setMessage(getResources().getString(R.string.str_g_msj7)); // "There is a new version available. Update?"

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_g_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // OK
                Intent startIntent = new Intent(MainActivity.this,
                        DownloadService.class);
                startIntent.putExtra("receiver", resultReceiever);
                startIntent.putExtra("url", SISConstants.APK_LOCATION);
                startService(startIntent);

                pd.setVisibility(View.VISIBLE);
                pd.setIndeterminate(true);
            }
        });
        dialogo1.setNegativeButton(getResources().getString(R.string.str_g_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // Cancel
                cancelar();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {}
    public void cancelar() {} //finish();}

    public void dialogAlert2(int v){
        final int v_BR = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1)); // Importante
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_g_msj8));} // You are about to make a backup ...
        if (v == 2) {dialogo1.setMessage(getResources().getString(R.string.str_g_msj9));} // You are about to make a restore ...
        dialogo1.setCancelable(true);
        dialogo1.setNegativeButton(getResources().getString(R.string.str_g_local), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // Local
                if (v_BR == 1) {BkDbUtility.copyFile(STATICS_ROOT, DB_INDICATORS_NAME, STATICS_ROOT_BK); dialogAlert(3);}
                if (v_BR == 2) {
                    Intent intent1 = new Intent(MainActivity.this, FileListBKLocal.class);
                    startActivity(intent1); dialogAlert(4);
                }
            }
        });
        dialogo1.setNeutralButton(getResources().getString(R.string.str_g_web), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // WEB
                if (v_BR == 1) {BKDBWebUtility.doFileUpload(_EMISCODE); dialogAlert(5);}
                if (v_BR == 2) {
                    Intent intent1 = new Intent(MainActivity.this, FileListBKWEB.class);
                    intent1.putExtra("emis", _EMISCODE);
                    startActivity(intent1);
                }
            }
        });
        dialogo1.setPositiveButton(getResources().getString(R.string.str_g_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // Cancel
            }
        });
        dialogo1.show();
    }

    public void dialogAlert3(int v){
        final int v_BR = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1)); // Importante
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_g_msj8));} // You are about to make a backup ...
        if (v == 2) {dialogo1.setMessage(getResources().getString(R.string.str_g_msj9));} // You are about to make a restore ...
        if (v == 3) {dialogo1.setMessage(getResources().getString(R.string.str_g_msj11));} // Initialize database ...
        if (v == 4) {dialogo1.setMessage(getResources().getString(R.string.str_g_msj13));} // Automatic promotion ...
        dialogo1.setCancelable(true);
        dialogo1.setNegativeButton(getResources().getString(R.string.str_g_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // Local
                cancelar();
            }
        });
        dialogo1.setPositiveButton(getResources().getString(R.string.str_g_confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) { // Cancel
                if (v_BR == 3) {initializeDatabase();}
                if (v_BR == 4) {aut_promotion();}
            }
        });
        dialogo1.show();
    }
    // *********** END Control Alerts ************************

    /**
     * Muestra una ventana de dialogo para elegir el nuevo idioma de la aplicacion
     * Cuando se hace clic en uno de los idiomas, se cambia el idioma de la aplicacion
     * y se recarga la actividad para ver los cambios
     * */
    public void showDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        String[] types = getResources().getStringArray(R.array.languages);
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        locale = new Locale("en");
                        config.locale = locale;
                        saveLang("en");
                        break;
                    case 1:
                        locale = new Locale("sw");
                        config.locale = locale;
                        saveLang("sw");
                        break;
                    case 2:
                        locale = new Locale("es");
                        config.locale = locale;
                        saveLang("es");
                        break;
                }
                getResources().updateConfiguration(config, null);
                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
                finish();
            }

        });

        b.show();
    }

    public void showLanguage(){
        Locale locale = new Locale(getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


    private void saveLang(String lang){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.execSQL("UPDATE lang SET lang='" + lang + "'");
    }


    private void initializeDatabase(){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.execSQL("UPDATE lang SET flag='2'"); // se usa flag = 1 para inicializar datos en la capacitación y flag=2 para iniciar el proceso de recolección verdadero
        dbSET.execSQL("DELETE FROM a");
        dbSET.execSQL("DELETE FROM d");
        dbSET.execSQL("DELETE FROM f");
        dbSET.execSQL("DELETE FROM g");
        dbSET.execSQL("DELETE FROM i");
        dbSET.execSQL("DELETE FROM if1");
        dbSET.execSQL("DELETE FROM if2");
        dbSET.execSQL("DELETE FROM if3");
        dbSET.execSQL("DELETE FROM if4");
        dbSET.execSQL("DELETE FROM if5");
        dbSET.execSQL("DELETE FROM if6");
        dbSET.execSQL("DELETE FROM ii");
        dbSET.execSQL("DELETE FROM j");
        dbSET.execSQL("DELETE FROM q");
        dbSET.execSQL("DELETE FROM r");
        dbSET.execSQL("DELETE FROM s");
        dbSET.execSQL("DELETE FROM login");
        dbSET.execSQL("DELETE FROM ms_0");
        dbSET.execSQL("DELETE FROM sisupdate");
        dbSET.execSQL("DELETE FROM student");
        dbSET.execSQL("DELETE FROM _sa");
        dbSET.execSQL("DELETE FROM teacher");
        dbSET.execSQL("DELETE FROM _ta");
        dbSET.execSQL("DELETE FROM attendance");
        dbSET.execSQL("DELETE FROM behaviour");
        dbSET.execSQL("DELETE FROM evaluation");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public String getFlagDatabase(){
        String getflagbdd="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT flag FROM lang", null);
        cur_data.moveToFirst();
        getflagbdd = cur_data.getString(0);
        return getflagbdd;
    }

    public int dataTransferReady(){
        int getflag=0;
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        //Cursor cur_data = dbSET.rawQuery("SELECT COUNT(*) AS ready_done FROM sisupdate WHERE flag=1 AND sql_sis not like \'%''%\'", null);
        Cursor cur_data = dbSET.rawQuery("SELECT COUNT(*) AS ready_done FROM sisupdate WHERE flag=1 AND sis_sql NOT LIKE \"%'%\" ", null);
        cur_data.moveToFirst();
        getflag =  cur_data.getInt(0);
        return getflag;
    }

    public void onCheckDataTransferReady() {
        data_transfer_ready_done = dataTransferReady();
        if ( data_transfer_ready_done  > 0 ) {
            btn_data_transfer.setBackgroundResource(R.drawable.data_transfer_ready);
        } else {
            btn_data_transfer.setBackgroundResource(R.drawable.data_transfer_done);
        }
    }


    public void dialogAlert_update_geo(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Save and Exit !!!");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}
        if (v == 6){dialogo1.setMessage("Are you sure you want to send?");}
        if (v == 7){dialogo1.setMessage(getResources().getString(R.string.synchronizing));}

        dialogo1.setCancelable(false);
        dialogo1.setNegativeButton(getResources().getString(R.string.str_g_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar_update_geo();
            }
        });
        dialogo1.show();
    }
    public void aceptar_update_geo() {
        progressBar_update_geo();
    }

    public void progressBar_update_geo(){
        progress=new ProgressDialog(this);
        progress.setMessage("Synchronizing ...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;

        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;
                insert_new_geo_code();
                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 20;
                        progress.setProgress(jumpTime);
                    }
                    catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (jumpTime==100){
                        progress.dismiss();
                    }
                }
            }
        };
        t.start();

    }

    // ******* NEW subject ******************
    public void new_subject() {
        String cod2031="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT COUNT(*) AS prim FROM subject WHERE level=1", null);
        cur_data.moveToFirst();
        if (!cur_data.getString(0).toString().equals("19")) {
            if (getLang().equals("en")) {
                dbSET.execSQL("INSERT INTO subject (level, id, subject) VALUES(1,17,\"Social Studies\")");
                dbSET.execSQL("INSERT INTO subject (level, id, subject) VALUES(1,18,\"Civics and Moral\")");
                dbSET.execSQL("INSERT INTO subject (level, id, subject) VALUES(1,19,\"Science and Technology\")");
            } else {
                dbSET.execSQL("INSERT INTO subject (level, id, subject) VALUES(1,17,\"Maarifa ya Jamii\")");
                dbSET.execSQL("INSERT INTO subject (level, id, subject) VALUES(1,18,\"Jamii na Maadili\")");
                dbSET.execSQL("INSERT INTO subject (level, id, subject) VALUES(1,19,\"Sayansi na Teknolojia\")");
            }

        }
        cnSET.close();
        dbSET.close();
        cur_data.close();
    }

    public void new_set_geo_codes() {
        String cod2031="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT COUNT(*) FROM set_geo_codes", null);
        Cursor cur_data2031 = dbSET.rawQuery("select g1, g2, g3 from set_geo_codes where g1='1' and g2='7' and g3='2031'", null);
        if (cur_data2031.moveToFirst()) { cod2031 = cur_data2031.getString(2);}
        cur_data.moveToFirst();
        if (!cur_data.getString(0).equals("1136") | !cod2031.equals("2031")) {
            dialogAlert_update_geo(7);
        }
        dbSET.execSQL("UPDATE set_geo_codes SET g2=2 WHERE name2=\"KASULU DC\"");
        dbSET.execSQL("UPDATE set_geo_codes SET g2=5 WHERE name2=\"UVINZA\"");
        cnSET.close();
        dbSET.close();
        cur_data.close();
    }


    public  void insert_new_geo_code() {
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.execSQL("DELETE FROM set_geo_codes");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,11,'DODOMA','KONDOA DC','BUMBUTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,21,'DODOMA','KONDOA DC','PAHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,31,'DODOMA','KONDOA DC','BUSI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,41,'DODOMA','KONDOA DC','HAUBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,51,'DODOMA','KONDOA DC','KALAMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,61,'DODOMA','KONDOA DC','KWADELO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,71,'DODOMA','KONDOA DC','MASANGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,81,'DODOMA','KONDOA DC','KIKILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,91,'DODOMA','KONDOA DC','BEREKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,101,'DODOMA','KONDOA DC','KISESE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,111,'DODOMA','KONDOA DC','KIKORE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,171,'DODOMA','KONDOA DC','KINYASI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,181,'DODOMA','KONDOA DC','SALANKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,191,'DODOMA','KONDOA DC','ITOLOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,201,'DODOMA','KONDOA DC','ITASWI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,251,'DODOMA','KONDOA DC','CHANGAA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,261,'DODOMA','KONDOA DC','THAWI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,271,'DODOMA','KONDOA DC','MNENIA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,281,'DODOMA','KONDOA DC','SOERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,1,1000,'DODOMA','KONDOA DC','HONDOMAIRO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,11,'DODOMA','MPWAPWA','MAZAE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,23,'DODOMA','MPWAPWA','VING''HAWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,31,'DODOMA','MPWAPWA','MATOMONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,41,'DODOMA','MPWAPWA','KIMAGAI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,53,'DODOMA','MPWAPWA','KIBAKWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,61,'DODOMA','MPWAPWA','LUMUMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,71,'DODOMA','MPWAPWA','LUHUNDWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,81,'DODOMA','MPWAPWA','MASSA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,91,'DODOMA','MPWAPWA','IPERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,101,'DODOMA','MPWAPWA','RUDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,111,'DODOMA','MPWAPWA','MLUNDUZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,121,'DODOMA','MPWAPWA','WOTTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,131,'DODOMA','MPWAPWA','MIMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,141,'DODOMA','MPWAPWA','BEREGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,151,'DODOMA','MPWAPWA','CHUNYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,161,'DODOMA','MPWAPWA','MBUGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,171,'DODOMA','MPWAPWA','GODEGODE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,191,'DODOMA','MPWAPWA','LUPETA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,201,'DODOMA','MPWAPWA','GULWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,211,'DODOMA','MPWAPWA','NG''HAMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,221,'DODOMA','MPWAPWA','CHITEMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,231,'DODOMA','MPWAPWA','IWONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,241,'DODOMA','MPWAPWA','KINGITI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,251,'DODOMA','MPWAPWA','LUFU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,261,'DODOMA','MPWAPWA','PWAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,271,'DODOMA','MPWAPWA','GALIGALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,281,'DODOMA','MPWAPWA','MTERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,291,'DODOMA','MPWAPWA','CHIPOGORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,301,'DODOMA','MPWAPWA','MALOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,1001,'DODOMA','MPWAPWA','MANG''ALIZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,1002,'DODOMA','MPWAPWA','MLEMBULE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,1003,'DODOMA','MPWAPWA','MPWAPWA MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,1004,'DODOMA','MPWAPWA','WANGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,13,'DODOMA','KONGWA','KONGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,21,'DODOMA','KONGWA','SEJELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,31,'DODOMA','KONGWA','HOGORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,41,'DODOMA','KONGWA','ZOISSA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,51,'DODOMA','KONGWA','MKOKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,61,'DODOMA','KONGWA','NJOGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,71,'DODOMA','KONGWA','MTANANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,81,'DODOMA','KONGWA','PANDAMBILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,91,'DODOMA','KONGWA','MLALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,101,'DODOMA','KONGWA','IDUO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,111,'DODOMA','KONGWA','SAGARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,123,'DODOMA','KONGWA','KIBAIGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,133,'DODOMA','KONGWA','UGOGONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,141,'DODOMA','KONGWA','CHAMKOROMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,151,'DODOMA','KONGWA','MAKAWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,161,'DODOMA','KONGWA','CHITEGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,171,'DODOMA','KONGWA','MATONGORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,181,'DODOMA','KONGWA','NGOMAI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,191,'DODOMA','KONGWA','SONGAMBELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,201,'DODOMA','KONGWA','CHIWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,211,'DODOMA','KONGWA','LENJULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,221,'DODOMA','KONGWA','NGHUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,11,'DODOMA','CHAMWINO','HANETI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,21,'DODOMA','CHAMWINO','SEGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,31,'DODOMA','CHAMWINO','ITISO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,32,'DODOMA','CHAMWINO','CHAMWINO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,41,'DODOMA','CHAMWINO','DABALO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,51,'DODOMA','CHAMWINO','MEMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,61,'DODOMA','CHAMWINO','MSANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,71,'DODOMA','CHAMWINO','CHILONWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,83,'DODOMA','CHAMWINO','BUIGIRI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,91,'DODOMA','CHAMWINO','MAJELEKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,101,'DODOMA','CHAMWINO','MANCHALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,111,'DODOMA','CHAMWINO','IKOWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,121,'DODOMA','CHAMWINO','MSAMALO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,131,'DODOMA','CHAMWINO','IGANDU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,141,'DODOMA','CHAMWINO','MUUNGANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,151,'DODOMA','CHAMWINO','MVUMI MAKULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,161,'DODOMA','CHAMWINO','HANDALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,173,'DODOMA','CHAMWINO','MVUMI MISHENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,181,'DODOMA','CHAMWINO','IDIFU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,191,'DODOMA','CHAMWINO','MAKANG''WA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,201,'DODOMA','CHAMWINO','IRINGA MVUMI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,211,'DODOMA','CHAMWINO','MANZASE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,221,'DODOMA','CHAMWINO','FUFU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,231,'DODOMA','CHAMWINO','MLOWA BWAWANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,241,'DODOMA','CHAMWINO','MPWAYUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,251,'DODOMA','CHAMWINO','NGHAMBAKU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,261,'DODOMA','CHAMWINO','CHINUGULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,271,'DODOMA','CHAMWINO','MANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,281,'DODOMA','CHAMWINO','HUZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,291,'DODOMA','CHAMWINO','LOJE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,301,'DODOMA','CHAMWINO','CHIBOLI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,311,'DODOMA','CHAMWINO','NHINHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,321,'DODOMA','CHAMWINO','ZAJILWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,1005,'DODOMA','CHAMWINO','IKOMBOLINGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,1006,'DODOMA','CHAMWINO','MLOWA BARABARANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,1007,'DODOMA','CHAMWINO','NGHAHELEZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,22,'DODOMA','DODOMA URBAN','UHURU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,32,'DODOMA','DODOMA URBAN','CHAMWINO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,42,'DODOMA','DODOMA URBAN','KIWANJA CHA NDEGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,52,'DODOMA','DODOMA URBAN','MAKOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,62,'DODOMA','DODOMA URBAN','MIYUJI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,71,'DODOMA','DODOMA URBAN','MSALATO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,81,'DODOMA','DODOMA URBAN','MAKUTUPORA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,91,'DODOMA','DODOMA URBAN','CHIHANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,111,'DODOMA','DODOMA URBAN','IPALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,121,'DODOMA','DODOMA URBAN','NZUGUNI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,141,'DODOMA','DODOMA URBAN','MTUMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,151,'DODOMA','DODOMA URBAN','KIKOMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,171,'DODOMA','DODOMA URBAN','MPUNGUZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,182,'DODOMA','DODOMA URBAN','TAMBUKARELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,202,'DODOMA','DODOMA URBAN','KIKUYU KUSINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,212,'DODOMA','DODOMA URBAN','KIKUYU KASKAZINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,221,'DODOMA','DODOMA URBAN','MKONZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,231,'DODOMA','DODOMA URBAN','MBABALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,241,'DODOMA','DODOMA URBAN','ZUZU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,252,'DODOMA','DODOMA URBAN','HAZINA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,282,'DODOMA','DODOMA URBAN','KIZOTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,291,'DODOMA','DODOMA URBAN','NALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,301,'DODOMA','DODOMA URBAN','MBALAWALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,311,'DODOMA','DODOMA URBAN','NTYUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,332,'DODOMA','DODOMA URBAN','CHANG''OMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,362,'DODOMA','DODOMA URBAN','MNADANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,372,'DODOMA','DODOMA URBAN','IPAGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1008,'DODOMA','DODOMA URBAN','CHAHWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1009,'DODOMA','DODOMA URBAN','CHIGONGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1010,'DODOMA','DODOMA URBAN','DODOMA MAKULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1011,'DODOMA','DODOMA URBAN','HOMBOLO BWAWANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1012,'DODOMA','DODOMA URBAN','HOMBOLO MAKULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1013,'DODOMA','DODOMA URBAN','IHUMWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1014,'DODOMA','DODOMA URBAN','IYUMBU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1015,'DODOMA','DODOMA URBAN','MATUMBULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1016,'DODOMA','DODOMA URBAN','NG’ONNG’ONHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,1017,'DODOMA','DODOMA URBAN','NKUHUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,11,'DODOMA','BAHI','MAKANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,21,'DODOMA','BAHI','LAMAITI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,31,'DODOMA','BAHI','BABAYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,41,'DODOMA','BAHI','ZANKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,51,'DODOMA','BAHI','MSISI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,61,'DODOMA','BAHI','MUNDEMU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,71,'DODOMA','BAHI','BAHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,81,'DODOMA','BAHI','MPAMANTWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,91,'DODOMA','BAHI','IBIHWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,101,'DODOMA','BAHI','ILINDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,111,'DODOMA','BAHI','KIGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,121,'DODOMA','BAHI','CHIKOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,131,'DODOMA','BAHI','CHIPANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,141,'DODOMA','BAHI','CHALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,151,'DODOMA','BAHI','NONDWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,161,'DODOMA','BAHI','MPALANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,171,'DODOMA','BAHI','IBUGULE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,181,'DODOMA','BAHI','CHIBELELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,191,'DODOMA','BAHI','MWITIKIRA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,201,'DODOMA','BAHI','MTITAA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,1018,'DODOMA','BAHI','CHIFUTUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,1019,'DODOMA','BAHI','MPINGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,11,'DODOMA','CHEMBA','MAKORONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,21,'DODOMA','CHEMBA','OVADA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,31,'DODOMA','CHEMBA','BABAYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,41,'DODOMA','CHEMBA','KIMAHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,51,'DODOMA','CHEMBA','CHURUKU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,61,'DODOMA','CHEMBA','SONGOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,71,'DODOMA','CHEMBA','MONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,81,'DODOMA','CHEMBA','DALAI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,91,'DODOMA','CHEMBA','JANGALO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,103,'DODOMA','CHEMBA','MRIJO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,111,'DODOMA','CHEMBA','CHANDAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,121,'DODOMA','CHEMBA','GOIMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,131,'DODOMA','CHEMBA','CHEMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,141,'DODOMA','CHEMBA','PARANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,151,'DODOMA','CHEMBA','GWANDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,161,'DODOMA','CHEMBA','FARKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,171,'DODOMA','CHEMBA','MPENDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,181,'DODOMA','CHEMBA','SANZAWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,191,'DODOMA','CHEMBA','KWAMTORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,201,'DODOMA','CHEMBA','LALTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,1020,'DODOMA','CHEMBA','KIDOKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,1021,'DODOMA','CHEMBA','KINYAMSINDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,1022,'DODOMA','CHEMBA','LAHODA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,1023,'DODOMA','CHEMBA','SOYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,1024,'DODOMA','CHEMBA','TUMBAKOSE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,2031,'DODOMA','CHEMBA','MSAADA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,121,'DODOMA','KONDOA TC','SERYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,133,'DODOMA','KONDOA TC','KILIMANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,142,'DODOMA','KONDOA TC','CHEMCHEM')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,161,'DODOMA','KONDOA TC','BOLISA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,211,'DODOMA','KONDOA TC','SURUKE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,221,'DODOMA','KONDOA TC','KINGALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,232,'DODOMA','KONDOA TC','KONDOA MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,8,241,'DODOMA','KONDOA TC','KOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,11,'LINDI','KILWA','TINGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,21,'LINDI','KILWA','MITEJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,31,'LINDI','KILWA','MINGUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,41,'LINDI','KILWA','KINJUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,51,'LINDI','KILWA','CHUMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,61,'LINDI','KILWA','KIPATIMU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,71,'LINDI','KILWA','KANDAWALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,81,'LINDI','KILWA','NJINJO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,91,'LINDI','KILWA','MITOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,101,'LINDI','KILWA','MIGURUWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,111,'LINDI','KILWA','LIKAWAGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,121,'LINDI','KILWA','NANJIRINJI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,141,'LINDI','KILWA','MANDAWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,151,'LINDI','KILWA','LIHIMALYAO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,161,'LINDI','KILWA','PANDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,171,'LINDI','KILWA','KIKOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,183,'LINDI','KILWA','KIVINJE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,191,'LINDI','KILWA','SONGOSONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,202,'LINDI','KILWA','MASOKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,211,'LINDI','KILWA','KIBATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,1000,'LINDI','KILWA','KIRANJERANJE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,1001,'LINDI','KILWA','NAMAYUNI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,1002,'LINDI','KILWA','SOMANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,11,'LINDI','LINDI RURAL','MIPINGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,21,'LINDI','LINDI RURAL','KITOMANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,31,'LINDI','LINDI RURAL','MCHINGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,41,'LINDI','LINDI RURAL','KILOLAMBWANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,51,'LINDI','LINDI RURAL','KILANGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,63,'LINDI','LINDI RURAL','KIWALALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,71,'LINDI','LINDI RURAL','NAVANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,81,'LINDI','LINDI RURAL','MNOLELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,91,'LINDI','LINDI RURAL','SUDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,101,'LINDI','LINDI RURAL','NACHUNYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,113,'LINDI','LINDI RURAL','MTAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,123,'LINDI','LINDI RURAL','NYANGAO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,131,'LINDI','LINDI RURAL','NAMUPA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,141,'LINDI','LINDI RURAL','NYENGEDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,151,'LINDI','LINDI RURAL','MTUA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,161,'LINDI','LINDI RURAL','NAHUKAHUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,171,'LINDI','LINDI RURAL','NYANGAMARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,181,'LINDI','LINDI RURAL','MANDWANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,191,'LINDI','LINDI RURAL','MNARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,201,'LINDI','LINDI RURAL','CHIPONDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,221,'LINDI','LINDI RURAL','LONGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,231,'LINDI','LINDI RURAL','RUTAMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,241,'LINDI','LINDI RURAL','MILOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,251,'LINDI','LINDI RURAL','KIWAWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,261,'LINDI','LINDI RURAL','MTUMBYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,271,'LINDI','LINDI RURAL','MATIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,281,'LINDI','LINDI RURAL','NANGARU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,293,'LINDI','LINDI RURAL','MAJENGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,301,'LINDI','LINDI RURAL','NAMANGALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,1003,'LINDI','LINDI RURAL','MVULENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,12,'LINDI','NACHINGWEA','NAMBAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,23,'LINDI','NACHINGWEA','KILIMANIHEWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,31,'LINDI','NACHINGWEA','RUPONDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,51,'LINDI','NACHINGWEA','NAMAPWIA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,61,'LINDI','NACHINGWEA','KIPARA MNERO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,71,'LINDI','NACHINGWEA','LIONJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,81,'LINDI','NACHINGWEA','NAMIKANGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,91,'LINDI','NACHINGWEA','NDITI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,111,'LINDI','NACHINGWEA','MBONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,121,'LINDI','NACHINGWEA','KIEGEI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,131,'LINDI','NACHINGWEA','MKOKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,141,'LINDI','NACHINGWEA','CHIOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,151,'LINDI','NACHINGWEA','MPIRUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,161,'LINDI','NACHINGWEA','NANGOWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,171,'LINDI','NACHINGWEA','MKOTOKUYANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,183,'LINDI','NACHINGWEA','NAIPANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,191,'LINDI','NACHINGWEA','STESHENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,201,'LINDI','NACHINGWEA','NAIPINGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,231,'LINDI','NACHINGWEA','MATEKWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,241,'LINDI','NACHINGWEA','MARAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,251,'LINDI','NACHINGWEA','NAMATULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,261,'LINDI','NACHINGWEA','NDOMONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1004,'LINDI','NACHINGWEA','KILIMARONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1005,'LINDI','NACHINGWEA','KIPARA MTUA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1006,'LINDI','NACHINGWEA','MCHONDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1007,'LINDI','NACHINGWEA','MITUMBATI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1008,'LINDI','NACHINGWEA','MNERO MIEMBENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1009,'LINDI','NACHINGWEA','MNERO NGONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1010,'LINDI','NACHINGWEA','NACHINGWEA MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1011,'LINDI','NACHINGWEA','NANG''ONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1012,'LINDI','NACHINGWEA','NGUNICHILE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1013,'LINDI','NACHINGWEA','UGAWAJI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,1151,'LINDI','NACHINGWEA','MTUA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,13,'LINDI','LIWALE','LIWALE MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,21,'LINDI','LIWALE','MIHUMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,31,'LINDI','LIWALE','NGONGOWELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,41,'LINDI','LIWALE','MLEMBWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,51,'LINDI','LIWALE','MAKATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,61,'LINDI','LIWALE','BARIKIWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,71,'LINDI','LIWALE','MKUTANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,81,'LINDI','LIWALE','MBAYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,91,'LINDI','LIWALE','KIMAMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,101,'LINDI','LIWALE','KIANGARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,111,'LINDI','LIWALE','KIBUTUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,121,'LINDI','LIWALE','NANGANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,131,'LINDI','LIWALE','MPIGAMITI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,141,'LINDI','LIWALE','MIRUI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,151,'LINDI','LIWALE','LIWALE ''B''')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,161,'LINDI','LIWALE','MANGIRIKITI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,191,'LINDI','LIWALE','KICHONDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,201,'LINDI','LIWALE','LILOMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,1014,'LINDI','LIWALE','LIKONGOWELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,1015,'LINDI','LIWALE','NANGANDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,13,'LINDI','RUANGWA','RUANGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,23,'LINDI','RUANGWA','MBEKENYERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,33,'LINDI','RUANGWA','NKOWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,41,'LINDI','RUANGWA','MALOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,51,'LINDI','RUANGWA','LUCHELEGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,61,'LINDI','RUANGWA','CHIENJELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,71,'LINDI','RUANGWA','NAMICHIGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,81,'LINDI','RUANGWA','NARUNG''OMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,91,'LINDI','RUANGWA','MAKANJIRO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,101,'LINDI','RUANGWA','LIKUNJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,111,'LINDI','RUANGWA','MNACHO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,131,'LINDI','RUANGWA','NAMBILANJE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,141,'LINDI','RUANGWA','CHUNYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,151,'LINDI','RUANGWA','MANDARAWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,163,'LINDI','RUANGWA','NACHINGWEA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,171,'LINDI','RUANGWA','MATAMBARALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,181,'LINDI','RUANGWA','CHIBULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,191,'LINDI','RUANGWA','NANDAGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,201,'LINDI','RUANGWA','NANGANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,211,'LINDI','RUANGWA','CHINONGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,1016,'LINDI','RUANGWA','MBWEMKURU (MACHANG''ANJA)')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,1141,'LINDI','RUANGWA','MANDAWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,32,'LINDI','LINDI URBAN','MIKUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,52,'LINDI','LINDI URBAN','RAHALEO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,72,'LINDI','LINDI URBAN','MATOPENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,82,'LINDI','LINDI URBAN','WAILES')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,102,'LINDI','LINDI URBAN','RASBURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,112,'LINDI','LINDI URBAN','MTANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,122,'LINDI','LINDI URBAN','JAMHURI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,132,'LINDI','LINDI URBAN','MSINJAHILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,142,'LINDI','LINDI URBAN','MINGOYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,152,'LINDI','LINDI URBAN','NG''APA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,162,'LINDI','LINDI URBAN','TANDANGONGORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,163,'LINDI','LINDI URBAN','NACHINGWEA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,172,'LINDI','LINDI URBAN','CHIKONJI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,182,'LINDI','LINDI URBAN','MBANJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,1017,'LINDI','LINDI URBAN','KITUMBIKWELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,1018,'LINDI','LINDI URBAN','MNAZIMMOJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,13,'TABORA','IGUNGA','IGUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,21,'TABORA','IGUNGA','ITUMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,31,'TABORA','IGUNGA','BUKOKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,41,'TABORA','IGUNGA','ISAKAMALIWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,51,'TABORA','IGUNGA','NYANDEKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,61,'TABORA','IGUNGA','NANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,71,'TABORA','IGUNGA','NGUVUMOJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,81,'TABORA','IGUNGA','MBUTU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,91,'TABORA','IGUNGA','KINING''INILA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,103,'TABORA','IGUNGA','IGURUBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,111,'TABORA','IGUNGA','MWAMASHIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,121,'TABORA','IGUNGA','KINUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,131,'TABORA','IGUNGA','NTOBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,141,'TABORA','IGUNGA','ITUNDURU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,151,'TABORA','IGUNGA','MWAMASHIGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,163,'TABORA','IGUNGA','CHOMACHANKOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,181,'TABORA','IGUNGA','ZIBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,191,'TABORA','IGUNGA','NDEMBEZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,201,'TABORA','IGUNGA','NKINGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,211,'TABORA','IGUNGA','NGULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,221,'TABORA','IGUNGA','SIMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,241,'TABORA','IGUNGA','MWISI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,251,'TABORA','IGUNGA','CHABUTWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,261,'TABORA','IGUNGA','SUNGWIZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1000,'TABORA','IGUNGA','IBOROGELO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1001,'TABORA','IGUNGA','IGOWEKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1002,'TABORA','IGUNGA','KITANGILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1003,'TABORA','IGUNGA','LUGUBU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1004,'TABORA','IGUNGA','MTUNGURU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1005,'TABORA','IGUNGA','MWAMAKOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1006,'TABORA','IGUNGA','MWASHIKUMBILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1007,'TABORA','IGUNGA','TAMBALALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1008,'TABORA','IGUNGA','UGAKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1009,'TABORA','IGUNGA','USWAYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,1251,'TABORA','IGUNGA','MWAMALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,11,'TABORA','UYUI','LUTENDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,21,'TABORA','UYUI','KIZENGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,31,'TABORA','UYUI','GOWEKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,41,'TABORA','UYUI','IGALULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,63,'TABORA','UYUI','MABAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,71,'TABORA','UYUI','NDONO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,81,'TABORA','UYUI','UFULUMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,91,'TABORA','UYUI','USAGARI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,101,'TABORA','UYUI','IBIRI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,111,'TABORA','UYUI','BUKUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,121,'TABORA','UYUI','IKONGOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,131,'TABORA','UYUI','UPUGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,141,'TABORA','UYUI','MAGIRI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,151,'TABORA','UYUI','ISIKIZYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,161,'TABORA','UYUI','SHITAGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,171,'TABORA','UYUI','LOYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,181,'TABORA','UYUI','MISWAKI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,191,'TABORA','UYUI','TURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,201,'TABORA','UYUI','NSOLOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,211,'TABORA','UYUI','KIGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,221,'TABORA','UYUI','MIYENZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,231,'TABORA','UYUI','NSIMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,241,'TABORA','UYUI','IBELAMILUNDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1010,'TABORA','UYUI','IGULUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1011,'TABORA','UYUI','ILOLANGULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1012,'TABORA','UYUI','ISILA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1013,'TABORA','UYUI','KALOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1014,'TABORA','UYUI','MAKAZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1015,'TABORA','UYUI','MMALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,1016,'TABORA','UYUI','NZUBUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,11,'TABORA','URAMBO','KAPILULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,23,'TABORA','URAMBO','URAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,31,'TABORA','URAMBO','VUMILIA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,41,'TABORA','URAMBO','MUUNGANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,51,'TABORA','URAMBO','SONGAMBELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,61,'TABORA','URAMBO','UYOGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,71,'TABORA','URAMBO','KILOLENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,81,'TABORA','URAMBO','USSOKE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,91,'TABORA','URAMBO','UYUMBU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,101,'TABORA','URAMBO','UGALLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,111,'TABORA','URAMBO','USISYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,131,'TABORA','URAMBO','KASISI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,141,'TABORA','URAMBO','IMALAMAKOYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,151,'TABORA','URAMBO','NSENDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,161,'TABORA','URAMBO','UKONDAMOYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,1017,'TABORA','URAMBO','ITUNDU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,1018,'TABORA','URAMBO','KIYUNGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,1019,'TABORA','URAMBO','MCHIKICHINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,11,'TABORA','SIKONGE','TUTUO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,41,'TABORA','SIKONGE','KIPANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,53,'TABORA','SIKONGE','SIKONGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,61,'TABORA','SIKONGE','IGIGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,71,'TABORA','SIKONGE','KITUNDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,81,'TABORA','SIKONGE','KILOLI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,91,'TABORA','SIKONGE','KIPILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,101,'TABORA','SIKONGE','PANGALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,111,'TABORA','SIKONGE','IPOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,121,'TABORA','SIKONGE','NGOYWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,131,'TABORA','SIKONGE','KISANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,141,'TABORA','SIKONGE','MISHENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,151,'TABORA','SIKONGE','MOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,171,'TABORA','SIKONGE','USUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,251,'TABORA','SIKONGE','CHABUTWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,1020,'TABORA','SIKONGE','KILOLELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,1021,'TABORA','SIKONGE','KILUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,1022,'TABORA','SIKONGE','MKOLYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,1023,'TABORA','SIKONGE','MPOMBWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,1024,'TABORA','SIKONGE','NYAHUA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,12,'TABORA','TABORA URBAN','KANYENYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,22,'TABORA','TABORA URBAN','GONGONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,32,'TABORA','TABORA URBAN','MBUGANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,42,'TABORA','TABORA URBAN','CHEMCHEM')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,71,'TABORA','TABORA URBAN','KILOLENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,82,'TABORA','TABORA URBAN','ISEVYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,92,'TABORA','TABORA URBAN','IPULI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,102,'TABORA','TABORA URBAN','CHEYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,112,'TABORA','TABORA URBAN','KITETE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,132,'TABORA','TABORA URBAN','MALOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,141,'TABORA','TABORA URBAN','KAKOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,151,'TABORA','TABORA URBAN','UYUI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,161,'TABORA','TABORA URBAN','ITONJANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,171,'TABORA','TABORA URBAN','NDEVELWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,181,'TABORA','TABORA URBAN','ITETEMIA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,191,'TABORA','TABORA URBAN','TUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,201,'TABORA','TABORA URBAN','KALUNDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,211,'TABORA','TABORA URBAN','MISHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,221,'TABORA','TABORA URBAN','KABILA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,231,'TABORA','TABORA URBAN','IKOMWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,241,'TABORA','TABORA URBAN','IFUCHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,251,'TABORA','TABORA URBAN','NTALIKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1025,'TABORA','TABORA URBAN','KIDONGOCHEKUNDU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1026,'TABORA','TABORA URBAN','MAPAMBANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1027,'TABORA','TABORA URBAN','MPELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1028,'TABORA','TABORA URBAN','MTENEDENI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1029,'TABORA','TABORA URBAN','MWINYI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1030,'TABORA','TABORA URBAN','NG''AMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,6,1031,'TABORA','TABORA URBAN','TAMBUKA-RELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,11,'TABORA','KALIUA','UKUMBI SIGANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,21,'TABORA','KALIUA','ZUGIMLOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,31,'TABORA','KALIUA','USHOKOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,41,'TABORA','KALIUA','UGUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,53,'TABORA','KALIUA','KALIUA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,61,'TABORA','KALIUA','USINGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,71,'TABORA','KALIUA','IGAGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,81,'TABORA','KALIUA','KAMSEKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,91,'TABORA','KALIUA','KAZAROHO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,101,'TABORA','KALIUA','IGWISI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,111,'TABORA','KALIUA','UYOWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,121,'TABORA','KALIUA','SILAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,131,'TABORA','KALIUA','KASHISHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,141,'TABORA','KALIUA','SASU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,151,'TABORA','KALIUA','SELELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,161,'TABORA','KALIUA','ICHEMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,171,'TABORA','KALIUA','MWONGOZO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,181,'TABORA','KALIUA','KANOGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,191,'TABORA','KALIUA','KANINDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,201,'TABORA','KALIUA','MILAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,211,'TABORA','KALIUA','IGOMBEMKULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1032,'TABORA','KALIUA','ILEGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1033,'TABORA','KALIUA','KONA NNE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1034,'TABORA','KALIUA','MAKINGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1035,'TABORA','KALIUA','NHWANDE*')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1036,'TABORA','KALIUA','UFUKUTWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1037,'TABORA','KALIUA','USENYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,1038,'TABORA','KALIUA','USIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,11,'TABORA','NZEGA DC','PUGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,21,'TABORA','NZEGA DC','NKINIZIWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,31,'TABORA','NZEGA DC','BUDUSHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,41,'TABORA','NZEGA DC','MWAKASHANHALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,51,'TABORA','NZEGA DC','TONGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,61,'TABORA','NZEGA DC','MIZIBAZIBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,71,'TABORA','NZEGA DC','MILAMBO ITOBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,81,'TABORA','NZEGA DC','MAGENGATI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,91,'TABORA','NZEGA DC','NDALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,111,'TABORA','NZEGA DC','WELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,151,'TABORA','NZEGA DC','MUHUGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,161,'TABORA','NZEGA DC','UTWIGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,191,'TABORA','NZEGA DC','LUSU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,201,'TABORA','NZEGA DC','NATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,211,'TABORA','NZEGA DC','ISANZU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,221,'TABORA','NZEGA DC','ITOBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,231,'TABORA','NZEGA DC','MWANGOYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,241,'TABORA','NZEGA DC','SIGILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,251,'TABORA','NZEGA DC','MWAMALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,261,'TABORA','NZEGA DC','IGUSULE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,271,'TABORA','NZEGA DC','SHIGAMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,281,'TABORA','NZEGA DC','KASELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,291,'TABORA','NZEGA DC','KARITU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,303,'TABORA','NZEGA DC','BUKENE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,311,'TABORA','NZEGA DC','MOGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,321,'TABORA','NZEGA DC','MAMBALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,341,'TABORA','NZEGA DC','UDUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,351,'TABORA','NZEGA DC','SEMEMBELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,361,'TABORA','NZEGA DC','ISAGENHE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,371,'TABORA','NZEGA DC','IKINDWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,1039,'TABORA','NZEGA DC','KAMANHALANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,1040,'TABORA','NZEGA DC','MBAGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,1041,'TABORA','NZEGA DC','MWANTUNDU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,1042,'TABORA','NZEGA DC','MWASALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,1043,'TABORA','NZEGA DC','UGEMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,8,1081,'TABORA','NZEGA DC','MBUTU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,121,'TABORA','NZEGA TC','MBOGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,131,'TABORA','NZEGA TC','MIGUWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,141,'TABORA','NZEGA TC','ITILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,171,'TABORA','NZEGA TC','IJANIJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,1044,'TABORA','NZEGA TC','KITANGILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,1045,'TABORA','NZEGA TC','MWANZOLI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,1046,'TABORA','NZEGA TC','NZEGA MJINI MAGHARIBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,1047,'TABORA','NZEGA TC','NZEGA MJINI MASHARIKI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,1048,'TABORA','NZEGA TC','NZEGA NDOGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,9,1049,'TABORA','NZEGA TC','UCHAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,11,'KIGOMA','KIBONDO','MISEZERO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,21,'KIGOMA','KIBONDO','BITARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,41,'KIGOMA','KIBONDO','MURUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,51,'KIGOMA','KIBONDO','BUSAGARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,61,'KIGOMA','KIBONDO','RUGONGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,71,'KIGOMA','KIBONDO','BUSUNZU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,81,'KIGOMA','KIBONDO','KUMSENGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,91,'KIGOMA','KIBONDO','KIZAZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,103,'KIGOMA','KIBONDO','MABAMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,111,'KIGOMA','KIBONDO','BUNYAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,121,'KIGOMA','KIBONDO','ITABA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,131,'KIGOMA','KIBONDO','KITAHANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1025,'KIGOMA','KIBONDO','BITURANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1026,'KIGOMA','KIBONDO','KAGEZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1027,'KIGOMA','KIBONDO','KIBONDO MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1028,'KIGOMA','KIBONDO','KUMWAMBU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1029,'KIGOMA','KIBONDO','MUKABUYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1030,'KIGOMA','KIBONDO','NYARUYOBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,1031,'KIGOMA','KIBONDO','RUSOHOKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,11,'KIGOMA','KASULU','KITANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,51,'KIGOMA','KASULU','NYACHENDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,71,'KIGOMA','KASULU','NYAMNYUSI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,81,'KIGOMA','KASULU','NYAKITONTO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,101,'KIGOMA','KASULU','KURUGONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,111,'KIGOMA','KASULU','RUNGWE MPYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,121,'KIGOMA','KASULU','ASANTE NYERERE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,131,'KIGOMA','KASULU','TITYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,141,'KIGOMA','KASULU','SHUNGULIBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,151,'KIGOMA','KASULU','MUZYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,171,'KIGOMA','KASULU','KIGEMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1008,'KIGOMA','KASULU','BUGAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1009,'KIGOMA','KASULU','BUHORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1010,'KIGOMA','KASULU','HERU USHINGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1011,'KIGOMA','KASULU','KAGER NKANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1012,'KIGOMA','KASULU','KALELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1013,'KIGOMA','KASULU','KITAGATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1014,'KIGOMA','KASULU','KWAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1015,'KIGOMA','KASULU','MAKERE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1016,'KIGOMA','KASULU','NYAMIDAHO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,1017,'KIGOMA','KASULU','RUSESA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,11,'KIGOMA','KIGOMA RURAL','KAGUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,21,'KIGOMA','KIGOMA RURAL','MKIGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,31,'KIGOMA','KIGOMA RURAL','MWAMGONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,41,'KIGOMA','KIGOMA RURAL','KALINZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,51,'KIGOMA','KIGOMA RURAL','BITALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,61,'KIGOMA','KIGOMA RURAL','MKONGORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,71,'KIGOMA','KIGOMA RURAL','MAHEMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,81,'KIGOMA','KIGOMA RURAL','MATENDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,91,'KIGOMA','KIGOMA RURAL','MUNGONYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,101,'KIGOMA','KIGOMA RURAL','KAGONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,113,'KIGOMA','KIGOMA RURAL','MWANDIGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,1044,'KIGOMA','KIGOMA RURAL','KIDAHWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,1045,'KIGOMA','KIGOMA RURAL','NKUNGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,1046,'KIGOMA','KIGOMA RURAL','NYARUBANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,1047,'KIGOMA','KIGOMA RURAL','ZIWANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,1071,'KIGOMA','KIGOMA RURAL','SIMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,12,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','GUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,22,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIBIRIZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,32,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUHANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,42,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUSINDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,52,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MACHINJIONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,62,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KAGERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,72,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KASIMBU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,82,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','RUBUGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,102,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MAJENGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,112,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KITONGONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,122,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIPAMPA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,132,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','RUSIMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,142,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUZEBAZEBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,152,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MWANGA KUSINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,162,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIGOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,172,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BANGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,182,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MWANGA KASKAZINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,192,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KATUBUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1032,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BANGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1033,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUZEBAZEBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1034,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','GUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1035,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KAGERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1036,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KASIMBU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1037,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KATUBUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1038,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIBIRIZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1039,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIGOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1040,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIPAMPA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1041,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MACHINJIONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1042,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MWANGA KASKAZINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,1043,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MWANGA KUSINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,11,'KIGOMA','UVINZA','KALYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,21,'KIGOMA','UVINZA','BUHINGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,31,'KIGOMA','UVINZA','IGALULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,41,'KIGOMA','UVINZA','SIGUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,51,'KIGOMA','UVINZA','SUNUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,61,'KIGOMA','UVINZA','ILAGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,81,'KIGOMA','UVINZA','KANDAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,91,'KIGOMA','UVINZA','KAZURAMIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,103,'KIGOMA','UVINZA','UVINZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,111,'KIGOMA','UVINZA','MGANZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,121,'KIGOMA','UVINZA','MTEGOWANOTI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,133,'KIGOMA','UVINZA','NGURUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,141,'KIGOMA','UVINZA','ITEBULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,1048,'KIGOMA','UVINZA','BASANZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,1049,'KIGOMA','UVINZA','HEREMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,1050,'KIGOMA','UVINZA','MWAKIZEGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,11,'KIGOMA','BUHIGWE','NYAMUGALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,21,'KIGOMA','BUHIGWE','BIHARU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,31,'KIGOMA','BUHIGWE','MUYAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,41,'KIGOMA','BUHIGWE','KAJANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,51,'KIGOMA','BUHIGWE','MUGERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,61,'KIGOMA','BUHIGWE','KILELEMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,71,'KIGOMA','BUHIGWE','MUNYEGERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,81,'KIGOMA','BUHIGWE','BUHIGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,91,'KIGOMA','BUHIGWE','KIBANDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,101,'KIGOMA','BUHIGWE','JANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,111,'KIGOMA','BUHIGWE','MUNZEZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,121,'KIGOMA','BUHIGWE','RUSABA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,131,'KIGOMA','BUHIGWE','MUHINDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,141,'KIGOMA','BUHIGWE','MUNANILA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,151,'KIGOMA','BUHIGWE','MWAYAYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,1000,'KIGOMA','BUHIGWE','BUKUBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,1001,'KIGOMA','BUHIGWE','KIBWIGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,1002,'KIGOMA','BUHIGWE','KINAZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,1003,'KIGOMA','BUHIGWE','MKATANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,1004,'KIGOMA','BUHIGWE','MUBANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,11,'KIGOMA','KAKONKO','NYABIBUYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,21,'KIGOMA','KAKONKO','NYAMTUKUZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,31,'KIGOMA','KAKONKO','MUHANGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,53,'KIGOMA','KAKONKO','KAKONKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,61,'KIGOMA','KAKONKO','KIZIGUZIGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,71,'KIGOMA','KAKONKO','RUGENGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,81,'KIGOMA','KAKONKO','KASANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,91,'KIGOMA','KAKONKO','GWANUMPU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,101,'KIGOMA','KAKONKO','KATANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,111,'KIGOMA','KAKONKO','MUGUNZU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,1005,'KIGOMA','KAKONKO','GWARAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,1006,'KIGOMA','KAKONKO','KANYONZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,1007,'KIGOMA','KAKONKO','KASUGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,11,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MUGANZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,21,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KIGONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,31,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MSAMBARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,51,'KIGOMA','KASULU TOWNSHIP AUTHORITY','NYUMBIGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,61,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MURUFITI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,71,'KIGOMA','KASULU TOWNSHIP AUTHORITY','NYANSHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,81,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KUMSENGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,91,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MUHUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1018,'KIGOMA','KASULU TOWNSHIP AUTHORITY','HERU JUU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1019,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KABANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1020,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KIMOBWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1021,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KUMNYIKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1022,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MURUBONA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1023,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MURUSI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,1024,'KIGOMA','KASULU TOWNSHIP AUTHORITY','RUHITA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,11,'SHINYANGA','SHINYANGA URBAN','MWAMALILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,21,'SHINYANGA','SHINYANGA URBAN','KOLANDOTO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,32,'SHINYANGA','SHINYANGA URBAN','NGOKOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,43,'SHINYANGA','SHINYANGA URBAN','IBADAKULI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,62,'SHINYANGA','SHINYANGA URBAN','CHAMAGUHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,72,'SHINYANGA','SHINYANGA URBAN','IBINZAMATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,82,'SHINYANGA','SHINYANGA URBAN','KITANGILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,91,'SHINYANGA','SHINYANGA URBAN','KIZUMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,101,'SHINYANGA','SHINYANGA URBAN','MWAWAZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,112,'SHINYANGA','SHINYANGA URBAN','NDALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,122,'SHINYANGA','SHINYANGA URBAN','KAMBARAGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,133,'SHINYANGA','SHINYANGA URBAN','CHIBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,142,'SHINYANGA','SHINYANGA URBAN','LUBAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,152,'SHINYANGA','SHINYANGA URBAN','NDEMBEZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,162,'SHINYANGA','SHINYANGA URBAN','MASEKELO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,173,'SHINYANGA','SHINYANGA URBAN','OLD SHINYANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,1,1011,'SHINYANGA','SHINYANGA URBAN','MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,11,'SHINYANGA','KISHAPU','BUNAMBIYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,21,'SHINYANGA','KISHAPU','BUBIKI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,33,'SHINYANGA','KISHAPU','SONGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,41,'SHINYANGA','KISHAPU','SEKE BUGORO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,51,'SHINYANGA','KISHAPU','MONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,63,'SHINYANGA','KISHAPU','MWADUI LOHUMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,71,'SHINYANGA','KISHAPU','UCHUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,83,'SHINYANGA','KISHAPU','KISHAPU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,91,'SHINYANGA','KISHAPU','MWAKIPOYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,101,'SHINYANGA','KISHAPU','SHAGIHILU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,111,'SHINYANGA','KISHAPU','SOMAGEDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,121,'SHINYANGA','KISHAPU','MWAMALASA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,131,'SHINYANGA','KISHAPU','MASANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,141,'SHINYANGA','KISHAPU','LAGANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,151,'SHINYANGA','KISHAPU','MWAMASHELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,161,'SHINYANGA','KISHAPU','NGOFILA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,171,'SHINYANGA','KISHAPU','KILOLELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,181,'SHINYANGA','KISHAPU','UKENYENGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,191,'SHINYANGA','KISHAPU','TALAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,201,'SHINYANGA','KISHAPU','ITILIMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,1001,'SHINYANGA','KISHAPU','BUSANGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,1002,'SHINYANGA','KISHAPU','IDUKILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,1003,'SHINYANGA','KISHAPU','MAGANZO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,1004,'SHINYANGA','KISHAPU','MWATAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,1005,'SHINYANGA','KISHAPU','MWAWEJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,11,'SHINYANGA','SHINYANGA RURAL','IMESELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,21,'SHINYANGA','SHINYANGA RURAL','USULE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,41,'SHINYANGA','SHINYANGA RURAL','DIDIA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,51,'SHINYANGA','SHINYANGA RURAL','ITWANGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,63,'SHINYANGA','SHINYANGA RURAL','TINDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,81,'SHINYANGA','SHINYANGA RURAL','SALAWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,91,'SHINYANGA','SHINYANGA RURAL','SOLWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,101,'SHINYANGA','SHINYANGA RURAL','ISELAMAGAZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,111,'SHINYANGA','SHINYANGA RURAL','LYABUKANDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,121,'SHINYANGA','SHINYANGA RURAL','MWANTINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,131,'SHINYANGA','SHINYANGA RURAL','PANDAGICHIZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,141,'SHINYANGA','SHINYANGA RURAL','MWAMALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,151,'SHINYANGA','SHINYANGA RURAL','SAMUYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,161,'SHINYANGA','SHINYANGA RURAL','USANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,171,'SHINYANGA','SHINYANGA RURAL','PUNI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,181,'SHINYANGA','SHINYANGA RURAL','NYIDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,191,'SHINYANGA','SHINYANGA RURAL','NSALALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,201,'SHINYANGA','SHINYANGA RURAL','BUKENE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,211,'SHINYANGA','SHINYANGA RURAL','MASENGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,221,'SHINYANGA','SHINYANGA RURAL','MWENGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,231,'SHINYANGA','SHINYANGA RURAL','LYABUSALU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,241,'SHINYANGA','SHINYANGA RURAL','MWALUKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,251,'SHINYANGA','SHINYANGA RURAL','NYAMALOGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,261,'SHINYANGA','SHINYANGA RURAL','LYAMIDATI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,1009,'SHINYANGA','SHINYANGA RURAL','LLOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,1010,'SHINYANGA','SHINYANGA RURAL','MWAKITOLYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,11,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NGOGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,21,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','WENDELE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,31,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KINAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,41,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','ISAGEHE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,51,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,63,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KAGONGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,73,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYAHANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,83,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MALUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,93,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MHONGOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,111,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','ZONGOMERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,123,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MHUNGULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,132,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYIHOGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,143,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYASUBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,152,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KAHAMA MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,163,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MAJENGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,171,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','BUSOKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,181,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KILAGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,191,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','IYENZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,201,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYANDEKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,1000,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MWANDAKULIMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,23,'SHINYANGA','MSALALA','BUGARAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,31,'SHINYANGA','MSALALA','LUNGUYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,41,'SHINYANGA','MSALALA','SHILELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,53,'SHINYANGA','MSALALA','SEGESE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,61,'SHINYANGA','MSALALA','MEGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,71,'SHINYANGA','MSALALA','CHELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,81,'SHINYANGA','MSALALA','BUSANGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,91,'SHINYANGA','MSALALA','NTOBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,101,'SHINYANGA','MSALALA','NGAYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,111,'SHINYANGA','MSALALA','BULIGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,121,'SHINYANGA','MSALALA','KASHISHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,131,'SHINYANGA','MSALALA','MWANASE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,141,'SHINYANGA','MSALALA','MWALUGULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,151,'SHINYANGA','MSALALA','JANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,163,'SHINYANGA','MSALALA','ISAKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,1006,'SHINYANGA','MSALALA','BULYAN''HULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,1007,'SHINYANGA','MSALALA','IKINDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,6,1008,'SHINYANGA','MSALALA','MWAKATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,171,'SHINYANGA','USHETU','CHONA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,181,'SHINYANGA','USHETU','CHAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,191,'SHINYANGA','USHETU','KISUKE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,201,'SHINYANGA','USHETU','MAPAMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,211,'SHINYANGA','USHETU','BUKOMELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,221,'SHINYANGA','USHETU','UKUNE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,231,'SHINYANGA','USHETU','IGUNDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,241,'SHINYANGA','USHETU','KINAMAPULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,251,'SHINYANGA','USHETU','IGWAMANONI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,261,'SHINYANGA','USHETU','MPUNZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,271,'SHINYANGA','USHETU','SABASABINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,281,'SHINYANGA','USHETU','IDAHINA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,291,'SHINYANGA','USHETU','BULUNGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,301,'SHINYANGA','USHETU','NYANKENDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,311,'SHINYANGA','USHETU','ULEWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,321,'SHINYANGA','USHETU','USHETU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,331,'SHINYANGA','USHETU','UYOGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,343,'SHINYANGA','USHETU','ULOWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,1012,'SHINYANGA','USHETU','NYAMILANGANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,7,1013,'SHINYANGA','USHETU','UBAGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,11,'MARA','SERENGETI','KENYAMONTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,21,'MARA','SERENGETI','BUSAWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,31,'MARA','SERENGETI','KISAKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,71,'MARA','SERENGETI','MACHOCHWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,81,'MARA','SERENGETI','KISANGURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,92,'MARA','SERENGETI','MUGUMU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,101,'MARA','SERENGETI','IKOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,111,'MARA','SERENGETI','NATTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,123,'MARA','SERENGETI','ISSENYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,131,'MARA','SERENGETI','RIGICHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,141,'MARA','SERENGETI','NYAMBURETI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,151,'MARA','SERENGETI','NYAMOKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,161,'MARA','SERENGETI','MANCHIRA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,171,'MARA','SERENGETI','KYAMBAHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,181,'MARA','SERENGETI','NYAMATARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,191,'MARA','SERENGETI','MAJIMOTO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,201,'MARA','SERENGETI','MAGANGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,211,'MARA','SERENGETI','NYANSURURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,221,'MARA','SERENGETI','MOSONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,231,'MARA','SERENGETI','SEDECO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,241,'MARA','SERENGETI','MBALIBALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,252,'MARA','SERENGETI','STENDI KUU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,261,'MARA','SERENGETI','GEITASAMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,273,'MARA','SERENGETI','MOROTONGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,281,'MARA','SERENGETI','UWANJA WA NDEGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,1016,'MARA','SERENGETI','KEBANCHEBANCHE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,1017,'MARA','SERENGETI','MATARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,1018,'MARA','SERENGETI','NAGUSI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,1019,'MARA','SERENGETI','RING’WANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,1020,'MARA','SERENGETI','RUNG’ABURE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,22,'MARA','MUSOMA MUNICIPAL','MWIGOBERO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,32,'MARA','MUSOMA MUNICIPAL','IRINGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,42,'MARA','MUSOMA MUNICIPAL','KITAJI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,52,'MARA','MUSOMA MUNICIPAL','NYASHO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,62,'MARA','MUSOMA MUNICIPAL','BWERI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,72,'MARA','MUSOMA MUNICIPAL','NYAKATO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,82,'MARA','MUSOMA MUNICIPAL','KIGERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,92,'MARA','MUSOMA MUNICIPAL','KAMUNYONGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,112,'MARA','MUSOMA MUNICIPAL','MWISENGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,122,'MARA','MUSOMA MUNICIPAL','BUHARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,132,'MARA','MUSOMA MUNICIPAL','MAKOKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,181,'MARA','MUSOMA MUNICIPAL','NYAMATARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,1007,'MARA','MUSOMA MUNICIPAL','KWANGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,1008,'MARA','MUSOMA MUNICIPAL','MKENDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,1009,'MARA','MUSOMA MUNICIPAL','MSHIKAMANO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,5,1010,'MARA','MUSOMA MUNICIPAL','RWAMLIMI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,11,'MARA','RORYA','KIGUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,21,'MARA','RORYA','KIROGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,31,'MARA','RORYA','NYAMTINGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,41,'MARA','RORYA','NYAMAGARO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,51,'MARA','RORYA','NYAHONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,63,'MARA','RORYA','MKOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,71,'MARA','RORYA','TAI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,81,'MARA','RORYA','BUKURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,91,'MARA','RORYA','ROCHE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,101,'MARA','RORYA','IKOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,111,'MARA','RORYA','MIRARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,121,'MARA','RORYA','GORIBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,143,'MARA','RORYA','KORYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,151,'MARA','RORYA','BUKWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,161,'MARA','RORYA','NYATHOROGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,171,'MARA','RORYA','RABOUR')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,181,'MARA','RORYA','KISUMWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,191,'MARA','RORYA','KOMUGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,201,'MARA','RORYA','NYAMUNGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,211,'MARA','RORYA','KYANG''OMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,1011,'MARA','RORYA','BARAKI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,1012,'MARA','RORYA','KINYENCHE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,1013,'MARA','RORYA','KYANGASAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,1014,'MARA','RORYA','NYABURONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,1015,'MARA','RORYA','RARANYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,1101,'MARA','RORYA','KITEMBE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,11,'MARA','BUTIAMA','BWIREGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,21,'MARA','BUTIAMA','BUSWAHILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,31,'MARA','BUTIAMA','NYAMIMANGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,41,'MARA','BUTIAMA','SIRORISIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,51,'MARA','BUTIAMA','BUHEMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,61,'MARA','BUTIAMA','MIRWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,71,'MARA','BUTIAMA','MURIAZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,83,'MARA','BUTIAMA','BUTIAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,91,'MARA','BUTIAMA','MASABA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,101,'MARA','BUTIAMA','KYANYARI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,113,'MARA','BUTIAMA','KUKIRANGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,121,'MARA','BUTIAMA','BURUMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,161,'MARA','BUTIAMA','NYANKANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,171,'MARA','BUTIAMA','BISUMWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,181,'MARA','BUTIAMA','BUKABWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,191,'MARA','BUTIAMA','BUTUGURI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,201,'MARA','BUTIAMA','BUSEGWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,1001,'MARA','BUTIAMA','KAMUGEGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,13,'MARA','BUNDA DC','NYAMUSWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,21,'MARA','BUNDA DC','SALAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,31,'MARA','BUNDA DC','MIHINGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,41,'MARA','BUNDA DC','MUGETA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,51,'MARA','BUNDA DC','HUNYARI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,111,'MARA','BUNDA DC','BUTIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,121,'MARA','BUNDA DC','NERUMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,133,'MARA','BUNDA DC','KIBARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,141,'MARA','BUNDA DC','NANSIMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,151,'MARA','BUNDA DC','KISORYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,161,'MARA','BUNDA DC','IGUNDU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,171,'MARA','BUNDA DC','IRAMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,211,'MARA','BUNDA DC','NAMPINDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,221,'MARA','BUNDA DC','CHITENGULE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,231,'MARA','BUNDA DC','KASUGUTI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,271,'MARA','BUNDA DC','NYAMANG''UTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,281,'MARA','BUNDA DC','KETARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,8,1000,'MARA','BUNDA DC','NAMHULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,61,'MARA','BUNDA TC','MCHARO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,71,'MARA','BUNDA TC','SAZIRA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,81,'MARA','BUNDA TC','KUNZUGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,93,'MARA','BUNDA TC','BUNDA MJINI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,101,'MARA','BUNDA TC','GUTA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,191,'MARA','BUNDA TC','WARIKU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,201,'MARA','BUNDA TC','KABASA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,243,'MARA','BUNDA TC','BALILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,253,'MARA','BUNDA TC','BUNDA STOO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,9,263,'MARA','BUNDA TC','NYASURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,11,'MARA','MUSOMA DC','BUKUMI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,21,'MARA','MUSOMA DC','MAKOJO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,31,'MARA','MUSOMA DC','BWASI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,51,'MARA','MUSOMA DC','BUKIMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,61,'MARA','MUSOMA DC','MURANGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,71,'MARA','MUSOMA DC','BUGWEMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,81,'MARA','MUSOMA DC','NYAMRANDIRIRA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,91,'MARA','MUSOMA DC','NYAMBONO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,101,'MARA','MUSOMA DC','SUGUTI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,111,'MARA','MUSOMA DC','TEGERUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,121,'MARA','MUSOMA DC','KIRIBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,131,'MARA','MUSOMA DC','BUSAMBARA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,141,'MARA','MUSOMA DC','ETARO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,143,'MARA','MUSOMA DC','MUGANGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,151,'MARA','MUSOMA DC','NYEGINA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,1002,'MARA','MUSOMA DC','BUGOJI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,1003,'MARA','MUSOMA DC','BULINGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,1004,'MARA','MUSOMA DC','IFULIFU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,1005,'MARA','MUSOMA DC','MUSANJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,1006,'MARA','MUSOMA DC','RUSOLI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,10,1131,'MARA','MUSOMA DC','NYAKATENDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,11,'MARA','TARIME DC','SUSUNI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,21,'MARA','TARIME DC','MWEMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,33,'MARA','TARIME DC','SIRARI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,41,'MARA','TARIME DC','PEMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,51,'MARA','TARIME DC','NYAKONGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,61,'MARA','TARIME DC','NYARERO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,71,'MARA','TARIME DC','NYAMWAGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,81,'MARA','TARIME DC','MURIBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,91,'MARA','TARIME DC','NYANUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,101,'MARA','TARIME DC','GORONG''A')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,121,'MARA','TARIME DC','KEMAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,131,'MARA','TARIME DC','KIBASUKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,141,'MARA','TARIME DC','BINAGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,183,'MARA','TARIME DC','MATONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,191,'MARA','TARIME DC','MANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,201,'MARA','TARIME DC','BUMERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,211,'MARA','TARIME DC','KOMASWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,221,'MARA','TARIME DC','KIORE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,271,'MARA','TARIME DC','MBOGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,301,'MARA','TARIME DC','ITIRYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,1021,'MARA','TARIME DC','GANYANGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,1022,'MARA','TARIME DC','GWITIRYO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,1023,'MARA','TARIME DC','KWIHANCHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,1024,'MARA','TARIME DC','NYARUKOBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,1025,'MARA','TARIME DC','NYASINCHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,11,1026,'MARA','TARIME DC','REGICHERI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,153,'MARA','TARIME TC','TURWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,162,'MARA','TARIME TC','BOMANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,171,'MARA','TARIME TC','NYANDOTO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,232,'MARA','TARIME TC','SABASABA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,242,'MARA','TARIME TC','NYAMISANGURA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,281,'MARA','TARIME TC','KETARE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,1027,'MARA','TARIME TC','KENYAMANYORI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,12,1028,'MARA','TARIME TC','NKENDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,11,'SIMIYU','ITILIMA','BUMERA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,31,'SIMIYU','ITILIMA','MWAMTANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,41,'SIMIYU','ITILIMA','SAGATA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,51,'SIMIYU','ITILIMA','MWASWALE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,61,'SIMIYU','ITILIMA','NKUYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,71,'SIMIYU','ITILIMA','MHUNZE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,81,'SIMIYU','ITILIMA','MIGATO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,91,'SIMIYU','ITILIMA','CHINAMILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,101,'SIMIYU','ITILIMA','NDOLELEZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,111,'SIMIYU','ITILIMA','LAGANGABILILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,121,'SIMIYU','ITILIMA','BUDALABUJIGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,131,'SIMIYU','ITILIMA','NKOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,141,'SIMIYU','ITILIMA','MWALUSHU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,153,'SIMIYU','ITILIMA','MWAMAPALALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,161,'SIMIYU','ITILIMA','NYAMALAPA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,181,'SIMIYU','ITILIMA','NHOBORA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,191,'SIMIYU','ITILIMA','ZAGAYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,201,'SIMIYU','ITILIMA','KINANG''WELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,211,'SIMIYU','ITILIMA','MBITA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,221,'SIMIYU','ITILIMA','SAWIDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,1009,'SIMIYU','ITILIMA','IKINDILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,1010,'SIMIYU','ITILIMA','LUGURU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,13,'SIMIYU','MEATU','MWANHUZI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,31,'SIMIYU','MEATU','KIMALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,41,'SIMIYU','MEATU','MWAMISHALI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,51,'SIMIYU','MEATU','ITINJE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,61,'SIMIYU','MEATU','KISESA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,71,'SIMIYU','MEATU','MWANDOYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,81,'SIMIYU','MEATU','LINGEKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,91,'SIMIYU','MEATU','SAKASAKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,101,'SIMIYU','MEATU','IMALASEKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,111,'SIMIYU','MEATU','MWABUZO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,121,'SIMIYU','MEATU','MWAMALOLE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,131,'SIMIYU','MEATU','MWANJOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,141,'SIMIYU','MEATU','MWABUMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,151,'SIMIYU','MEATU','MWABUSALU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,161,'SIMIYU','MEATU','LUBIGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,171,'SIMIYU','MEATU','MWAMANONGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,181,'SIMIYU','MEATU','NG''HOBOKO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,191,'SIMIYU','MEATU','BUKUNDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,201,'SIMIYU','MEATU','MWASENGELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,211,'SIMIYU','MEATU','MWAMANIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,221,'SIMIYU','MEATU','TINDABULIGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,231,'SIMIYU','MEATU','MWAKISANDU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,251,'SIMIYU','MEATU','MWANYAHINA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,1022,'SIMIYU','MEATU','ISENGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,1023,'SIMIYU','MEATU','KABONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,1024,'SIMIYU','MEATU','MBUGAYABANGHYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,1025,'SIMIYU','MEATU','MBUSHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,1026,'SIMIYU','MEATU','MWANGUDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,1131,'SIMIYU','MEATU','NKOMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,11,'SIMIYU','MASWA','NG''WIGWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,21,'SIMIYU','MASWA','NGULIGULI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,31,'SIMIYU','MASWA','IPILILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,41,'SIMIYU','MASWA','SENANI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,51,'SIMIYU','MASWA','MWAMANENGE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,61,'SIMIYU','MASWA','SUKUMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,71,'SIMIYU','MASWA','MPINDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,81,'SIMIYU','MASWA','DAKAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,93,'SIMIYU','MASWA','LALAGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,101,'SIMIYU','MASWA','BUDEKWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,111,'SIMIYU','MASWA','BUSILILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,131,'SIMIYU','MASWA','MASELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,151,'SIMIYU','MASWA','ZANZUI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,161,'SIMIYU','MASWA','MWAMASHIMBA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,171,'SIMIYU','MASWA','BUCHAMBI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,181,'SIMIYU','MASWA','KADOTO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,191,'SIMIYU','MASWA','SHISHIYU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,201,'SIMIYU','MASWA','NYABUBINZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,211,'SIMIYU','MASWA','MWANG''HONOLI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,221,'SIMIYU','MASWA','ISANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,233,'SIMIYU','MASWA','MALAMPAKA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,241,'SIMIYU','MASWA','BADI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,253,'SIMIYU','MASWA','NYALIKUNGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,263,'SIMIYU','MASWA','BINZA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1011,'SIMIYU','MASWA','BUGARAMA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1012,'SIMIYU','MASWA','BUSANGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1013,'SIMIYU','MASWA','JIJA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1014,'SIMIYU','MASWA','MATABA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1015,'SIMIYU','MASWA','MBARAGANE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1016,'SIMIYU','MASWA','MWABARATULU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1017,'SIMIYU','MASWA','MWABAYANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1018,'SIMIYU','MASWA','SANGAMWALUGESHA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1019,'SIMIYU','MASWA','SENG''WA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1020,'SIMIYU','MASWA','SHANWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1021,'SIMIYU','MASWA','SOLA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,1221,'SIMIYU','MASWA','KULIMI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,11,'SIMIYU','BUSEGA','SHIGALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,21,'SIMIYU','BUSEGA','BADUGU')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,31,'SIMIYU','BUSEGA','NYALUHANDE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,41,'SIMIYU','BUSEGA','KILOLELI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,51,'SIMIYU','BUSEGA','MWAMANYILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,63,'SIMIYU','BUSEGA','KABITA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,73,'SIMIYU','BUSEGA','KALEMELA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,83,'SIMIYU','BUSEGA','LAMADI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,91,'SIMIYU','BUSEGA','LUTUBIGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,101,'SIMIYU','BUSEGA','MKULA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,111,'SIMIYU','BUSEGA','NGASAMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,121,'SIMIYU','BUSEGA','MALILI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,131,'SIMIYU','BUSEGA','IGALUKILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,1007,'SIMIYU','BUSEGA','IMALAMATE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,1008,'SIMIYU','BUSEGA','NYASHIMO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,11,'SIMIYU','BARIADI DC','NKINDWABIYE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,33,'SIMIYU','BARIADI DC','NKOLOLO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,41,'SIMIYU','BARIADI DC','MWAUMATONDO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,51,'SIMIYU','BARIADI DC','MWADOBANA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,91,'SIMIYU','BARIADI DC','SAKWE')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,103,'SIMIYU','BARIADI DC','NGULYATI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,121,'SIMIYU','BARIADI DC','KILALO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,131,'SIMIYU','BARIADI DC','KASOLI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,141,'SIMIYU','BARIADI DC','GAMBOSI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,161,'SIMIYU','BARIADI DC','IKUNGULYABASHASHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,183,'SIMIYU','BARIADI DC','DUTWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,191,'SIMIYU','BARIADI DC','SAPIWI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,201,'SIMIYU','BARIADI DC','MATONGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,211,'SIMIYU','BARIADI DC','GILYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1000,'SIMIYU','BARIADI DC','BENEMHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1001,'SIMIYU','BARIADI DC','GIBISHI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1002,'SIMIYU','BARIADI DC','IHUSI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1003,'SIMIYU','BARIADI DC','ITUBUKILO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1004,'SIMIYU','BARIADI DC','MASEWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1005,'SIMIYU','BARIADI DC','MWASUBUYA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,6,1006,'SIMIYU','BARIADI DC','MWAUBINGI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,61,'SIMIYU','BARIADI TC','NYANGOKOLWA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,73,'SIMIYU','BARIADI TC','SOMANDA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,81,'SIMIYU','BARIADI TC','BUNAMHALA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,111,'SIMIYU','BARIADI TC','MHANGO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,151,'SIMIYU','BARIADI TC','GUDUWI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,173,'SIMIYU','BARIADI TC','NYAKABINDI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,221,'SIMIYU','BARIADI TC','ISANGA')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,233,'SIMIYU','BARIADI TC','BARIADI')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,243,'SIMIYU','BARIADI TC','MALAMBO')");
        dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,7,253,'SIMIYU','BARIADI TC','SIMA')");

        cnSET.close();
        dbSET.close();
    }

    private void bkTable(){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            dbSET.execSQL("CREATE TABLE IF NOT EXISTS backup ( _date DATE, flag INTEGER)");
        }catch (Exception e) {}
    }

    public String getFlagbkTable(){
        bkTable();
        String getflagbktable="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT flag FROM backup", null);
        if (cur_data.moveToFirst()){
            getflagbktable = cur_data.getString(0);
        }
        else{
            getflagbktable = "0";
        }
        return getflagbktable;
    }

    private void updatebkTable(){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        ContentValues Bitacora = new ContentValues();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        Bitacora.put("_date", formattedDate);
        Bitacora.put("flag", "1");
        dbSET.insert("backup", null, Bitacora);
    }

    private void backup(){
        BkDbUtility.copyFile(STATICS_ROOT, DB_INDICATORS_NAME, STATICS_ROOT_BK);
        updatebkTable();
    }

    private void aut_promotion() {
        Conexion cnSETpromotion = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSETpromotion = cnSETpromotion.getReadableDatabase();
        int promotion_year=0;
        try {
            school_year = getMaxSchoolYear();
            promotion_year = getMaxSchoolYear() + 1;
            if (school_year > 0 && promotion_year <= actual_year) {
                    dbSETpromotion.execSQL("INSERT INTO _sa (emis, sc, shift, level, grade, section, subject_assigned, year_ta)\n" +
                            "SELECT emis, sc, shift, level, grade+1, section, subject_assigned, year_ta+1 FROM _sa\n" +
                            "WHERE grade<7  AND year_ta = "+ school_year +" AND sc IN (SELECT sc FROM _sa WHERE year_ta ="+ school_year +" )");
                    dbSETpromotion.execSQL("update _sa set level = 1 and grade = 1 where level=3 and grade = 3 and year_ta ="+ promotion_year +")");
                toolsfncs.dialogAlertConfirm(this,getResources(),11);
            }
        }catch (Exception e) {}
        dbSETpromotion.close();
        cnSETpromotion.close();
    }

    public int getMaxSchoolYear(){
        int getMaxSchoolYearbdd=0;
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT MAX(year_ta) FROM _sa", null);
        cur_data.moveToFirst();
        if (cur_data.getInt(0) > 0) {
            getMaxSchoolYearbdd = cur_data.getInt(0);
        }
        return getMaxSchoolYearbdd;
    }
}
