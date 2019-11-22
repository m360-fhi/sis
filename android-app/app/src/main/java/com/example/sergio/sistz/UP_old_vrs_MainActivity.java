package com.example.sergio.sistz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sergio.sistz.util.CopyAssetDBUtility;

public class UP_old_vrs_MainActivity extends Activity implements View.OnClickListener {
    private final static String DB_INDICATORS_NAME = "sisdb.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn_next = (ImageButton) findViewById(R.id.btn_next);
        ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);

        btn_next.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                Intent intent1 = new Intent(UP_old_vrs_MainActivity.this, MobileSuite.class);
                startActivity(intent1);
                break;
            case R.id.btn_exit:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
