package com.example.sergio.sistz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MobileSuite extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobilesuite);

        ImageButton btn_settings = (ImageButton) findViewById(R.id.btn_settings);
        ImageButton btn_collect = (ImageButton) findViewById(R.id.btn_collect);
        ImageButton btn_reports = (ImageButton) findViewById(R.id.btn_report);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);

        btn_settings.setOnClickListener(this);
        btn_collect.setOnClickListener(this);
        btn_reports.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });


    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings1:
                        Intent intent1 = new Intent(MobileSuite.this, SettingsMenu_0.class);
                        startActivity(intent1);
                        return true;
                    case R.id.action_settings2:

                        return true;
                    case R.id.action_settings4:
                        Intent intent4 = new Intent(MobileSuite.this, login.class);
                        startActivity(intent4);
                        return true;
                    default:

                }
                return true;
            }
        });
        popup.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_settings:
                Intent intent1 = new Intent(MobileSuite.this, Settings.class);
                startActivity(intent1);
                break;
            case R.id.btn_collect:
                Intent intent2 = new Intent(MobileSuite.this, Collect.class);
                startActivity(intent2);
                break;
            case R.id.btn_report:
                Toast.makeText(getApplicationContext(),"Ud. presiono reports", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
