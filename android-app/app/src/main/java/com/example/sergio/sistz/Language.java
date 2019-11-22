package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;

import java.io.File;
import java.util.Locale;

/**
 * Created by Sergio on 2/26/2016.
 */
public class Language extends Activity implements View.OnClickListener {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    LinearLayout ll_wizard;
    FrameLayout fl_part0; // ************ FrameLayout ***************
    TextView btn_back, btn_next, btn_save, tv_english, tv_kiswahili;
    String _IU="U", languageToLoadWizard="";
    private Locale locale;
    private Configuration config = new Configuration();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);

        tv_english = (TextView) findViewById(R.id.tv_english);
        tv_kiswahili = (TextView) findViewById(R.id.tv_kiswahili);



        //  ************************ Objects assing *********************
        ll_wizard = (LinearLayout) findViewById(R.id.ll_wizard);
        fl_part0 = (FrameLayout) findViewById(R.id.fl_part0);

        //  ************************ Objects Buttoms *********************

        //************* Start FrameLayout **************************
        fl_part0.setVisibility(View.VISIBLE);


        // **************** CLICK ON BUTTONS ********************
        tv_english.setOnClickListener(this);
        tv_kiswahili.setOnClickListener(this);
        tv_english.setVisibility(View.VISIBLE);
        tv_kiswahili.setVisibility(View.VISIBLE);

    }


    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_english:
                saveLang("en");
                showLanguage("en");
                break;
            case R.id.tv_kiswahili:
                saveLang("sw");
                showLanguage("sw");
                break;
        }

    }


    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
    }

    public String getEMIS_code_form_a(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}

        return getemiscode;
    }

    /**
     * Muestra una ventana de dialogo para elegir el nuevo idioma de la aplicacion
     * Cuando se hace clic en uno de los idiomas, se cambia el idioma de la aplicacion
     * y se recarga la actividad para ver los cambios
     * */
    public void showLanguage(String which){
        switch (which) {
            case "en":
                locale = new Locale("en");
                config.locale = locale;
                //MainActivity.language = 0;
                languageToLoadWizard="en";
                break;
            case "sw":
                locale = new Locale("sw");
                config.locale = locale;
                //MainActivity.language = 1;
                languageToLoadWizard="sw";
                break;
            case "es":
                locale = new Locale("es");
                config.locale = locale;
                languageToLoadWizard="es";
                break;
        }
        getResources().updateConfiguration(config, null);
        Intent refresh = new Intent(Language.this, Wizard.class);
        startActivity(refresh);
        this.overridePendingTransition(0, 0);
        finish();
    }

    private void saveLang(String lang){
        DBUtility dbUtility = new DBUtility(this);
        dbUtility.setLanguage(lang);
        dbUtility.updateLanguageInTables();
    }
}
