package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sergio.sistz.util.BkDbUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PJYAC on 27/05/2016.
 */
public class FileListBKLocal extends Activity {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String STATICS_ROOT_BK = Environment.getExternalStorageDirectory() + File.separator + "sisdbBK";
    private List<String> fileList = new ArrayList<String>();
    ListView lv_list;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bk_local);
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "sisdbBK");
        lv_list = (ListView) findViewById(R.id.lv_list_bk);
        ListDir(folder);

    }
    void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            fileList.add(file.getName());
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
        dialogo1.setMessage("Confirm to restore?");

        dialogo1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                BkDbUtility.restoreFile(STATICS_ROOT_BK, database, STATICS_ROOT);
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
