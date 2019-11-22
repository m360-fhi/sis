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

public class SettingsMenuInfra extends FragmentActivity   {

    private CustomPagerAdapter mPagerAdapter;

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.settings_menu_infra);
        // initialsie the pager
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_0.class.getName())); // form a
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_1.class.getName())); // form d
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_3.class.getName())); // form g
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_2.class.getName())); // form f
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_6.class.getName())); // form q
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_7.class.getName())); // form r
        fragments.add(Fragment.instantiate(this, SettingsMenuInfra_8.class.getName())); // form s

        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

    }
}
