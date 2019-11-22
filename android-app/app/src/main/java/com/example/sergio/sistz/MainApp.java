package com.example.sergio.sistz;

import android.app.Application;
import android.os.Environment;

import com.example.sergio.sistz.data.SISConstants;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.toolsfncs;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import java.io.File;

/**
 * Created by pjyac on 20/03/2017.
 */
@ReportsCrashes(
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON,
        formUri = SISConstants.ACRA_URL,
        formUriBasicAuthLogin = "root",
        formUriBasicAuthPassword = "piolin2010"
)

public class MainApp extends android.support.multidex.MultiDexApplication{
    File dbFile = new File (Environment.getExternalStorageDirectory() + File.separator + "sisdb" + File.separator + "sisdb.sqlite");
    @Override
    public void onCreate() {
        super.onCreate();

        ACRA.init(this);
        if (!dbFile.exists()) {}
        else {
            DBUtility conn = new DBUtility(this);
            conn.flush();
            String emis = toolsfncs.getEMIS_code(this);
            ACRA.getErrorReporter().putCustomData("EMIS", emis);
        }
    }

}
