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
public class UP_old_vrs_SettingsMenuInfra extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_old_vrs_settings_menu_infra);

        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        final String[] datos =
                new String[]{"School Profile and Particulars", "Pre-Primary Operations", "Primary Curriculum and Instruction", "Primary Infrastructure and Furniture", "Secundary Textbooks TIE Syllabus", "Secondary Classrooms", "School Infrastructure", "Source of Funds", "Management and Teaching Staff"};
        ListView menu_infra = (ListView) findViewById(R.id.lv_infra);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.row_menu_select, datos);
        menu_infra.setAdapter(adaptador);

        menu_infra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent0 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_0.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_3.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_4.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_5.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_6.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_7.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(UP_old_vrs_SettingsMenuInfra.this, SettingsMenuInfra_8.class);
                        startActivity(intent8);
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
