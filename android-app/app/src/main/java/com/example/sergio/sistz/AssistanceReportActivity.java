package com.example.sergio.sistz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sergio.sistz.util.CustomPagerAdapter;

import java.util.List;
import java.util.Vector;

//public class AssistanceReportActivity extends AppCompatActivity {
public class AssistanceReportActivity extends FragmentActivity {

    private CustomPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance_controls);

        this.initialisePaging();
    }

    private void initialisePaging() {
        List<Fragment> fragments = new Vector<Fragment>();

        fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek1.class.getName()));
        /*fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek2.class.getName()));
        fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek3.class.getName()));
        fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek4.class.getName()));
        fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek5.class.getName()));
        fragments.add(Fragment.instantiate(this, ReportAttendancePerWeek6.class.getName()));*/


        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpagerRepAtt);
        pager.setAdapter(this.mPagerAdapter);

    }
}
