package com.example.sergio.sistz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.sergio.sistz.util.CustomPagerAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by PJYAC on 15/03/2016.
 */

public class SettingsMenuStudent_menu extends FragmentActivity   {

    private CustomPagerAdapter mPagerAdapter;

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.settings_menu_student_menu);
        // initialsie the pager
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, SettingsMenuStudentInformation.class.getName()));
        fragments.add(Fragment.instantiate(this, SettingsMenuStudentAssing.class.getName()));
        if (ReportS.S_report_enable.equals("1")) {
            fragments.add(Fragment.instantiate(this, SettingsMenuStudentAttendance.class.getName()));
            fragments.add(Fragment.instantiate(this, SettingsMenuStudentEvaluation.class.getName()));
            fragments.add(Fragment.instantiate(this, SettingsMenuStudentBehaviour.class.getName()));
        }

        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

    }
}



