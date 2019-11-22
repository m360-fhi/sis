package com.example.sergio.sistz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Sergio on 2/15/2016.
 */
public class Collect extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect);

        ImageButton btn_attendance = (ImageButton) findViewById(R.id.btn_attendance);
        ImageButton btn_evaluation = (ImageButton) findViewById(R.id.btn_evaluation);
        ImageButton btn_behavior = (ImageButton) findViewById(R.id.btn_behavior);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);


        btn_attendance.setOnClickListener(this);
        btn_evaluation.setOnClickListener(this);
        btn_behavior.setOnClickListener(this);
        btn_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_attendance:
                Toast.makeText(getApplicationContext(),"Assistance",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_evaluation:
                Toast.makeText(getApplicationContext(),"Evaluaton",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_behavior:
                Toast.makeText(getApplicationContext(),"Behavior",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
