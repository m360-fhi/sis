package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.util.CustomPagerAdapter;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.OnClick;

//public class AssistanceControlsActivity extends AppCompatActivity {
    public class AssistanceControlsActivity extends FragmentActivity {
    private String msjInformation;

    private CustomPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance_controls);
        this.initialisePaging();
    }

    private void initialisePaging() {
        List<Fragment> fragments = new Vector<Fragment>();

        fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek0.class.getName()));


        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpagerRepAtt);
        pager.setAdapter(this.mPagerAdapter);

    }

}
