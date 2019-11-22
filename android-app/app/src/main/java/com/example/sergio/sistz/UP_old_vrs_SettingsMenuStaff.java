package com.example.sergio.sistz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by Sergio on 2/15/2016.
 */
public class UP_old_vrs_SettingsMenuStaff extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_old_vrs_settings_menu_staff);

        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        final String[] datos =
                new String[]{"Personal Data", "Professional Data", "Level & Grade Assigment"};
        ListView menu_infra = (ListView) findViewById(R.id.lv_staff);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.row_menu_select, datos);
        menu_infra.setAdapter(adaptador);

        menu_infra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(UP_old_vrs_SettingsMenuStaff.this, SettingsMenuStaffInformation.class);
                        startActivity(intent0);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }

}
