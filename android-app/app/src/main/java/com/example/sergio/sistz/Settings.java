package com.example.sergio.sistz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sergio on 2/15/2016.
 */
public class Settings extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ImageButton btn_infra = (ImageButton) findViewById(R.id.btn_infra);
        ImageButton btn_staff = (ImageButton) findViewById(R.id.btn_staff);
        ImageButton btn_student = (ImageButton) findViewById(R.id.btn_student);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);


        btn_infra.setOnClickListener(this);
        btn_staff.setOnClickListener(this);
        btn_student.setOnClickListener(this);
        btn_back.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_infra:
                Intent intent1 = new Intent(Settings.this, SettingsMenuInfra.class);
                startActivity(intent1);
                break;
            case R.id.btn_staff:
                Intent intent2 = new Intent(Settings.this, UP_old_vrs_SettingsMenuStaff.class);
                startActivity(intent2);
                break;
            case R.id.btn_student:
                Intent intent3 = new Intent(Settings.this, SettingsMenuStudent.class);
                startActivity(intent3);
                break;
            case R.id.btn_back:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
