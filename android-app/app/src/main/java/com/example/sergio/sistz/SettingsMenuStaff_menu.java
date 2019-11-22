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

public class SettingsMenuStaff_menu extends FragmentActivity   {

    private CustomPagerAdapter mPagerAdapter;
    private int position;
    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.settings_menu_staff_menu);
        // initialsie the pager
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        final List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, SettingsMenuStaffInformation.class.getName()));
        fragments.add(Fragment.instantiate(this, SettingsMenuStaffAssing.class.getName()));

        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SettingsMenuStaff_menu.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (SettingsMenuStaff_menu.this.position==1 && SettingsMenuStaffInformation.checked==1){
                    SettingsMenuStaffAssing assigFragment = ((SettingsMenuStaffAssing) mPagerAdapter.getFragmentAt(1));
                    assigFragment.disable();
                }
                else{
                    SettingsMenuStaffAssing assigFragment = ((SettingsMenuStaffAssing) mPagerAdapter.getFragmentAt(1));
                    assigFragment.enable();
                }
            }
        });
    }
}



