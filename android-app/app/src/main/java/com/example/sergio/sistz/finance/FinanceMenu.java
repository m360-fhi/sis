package com.example.sergio.sistz.finance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.finance.FInanceDepositFragment;
import com.example.sergio.sistz.util.CustomPagerAdapter;

import java.util.List;
import java.util.Vector;



/**
 * Created by PJYAC on 15/03/2016.
 */

public class FinanceMenu extends FragmentActivity    {
    public static int VIEWINSTRUCTION = 0;
    private int position;
    private CustomPagerAdapter mPagerAdapter;
    private int state;
    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.finance_menu);
        // initialsie the pager
        this.VIEWINSTRUCTION = LoadPreferences();
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, FInanceInstructionFragment.class.getName())); // page #0
        fragments.add(Fragment.instantiate(this, FInanceDepositFragment.class.getName())); // page #1
        fragments.add(Fragment.instantiate(this, FInanceExpenditureFragment.class.getName())); // page #2
        fragments.add(Fragment.instantiate(this, FInanceActivityFragment.class.getName())); // page #3
        fragments.add(Fragment.instantiate(this, FInanceHomeFragment.class.getName())); // page #4




        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);
        //

        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);
        if(VIEWINSTRUCTION>2){
            pager.setCurrentItem(1);
        }else{
            this.VIEWINSTRUCTION++;

            SavePreferences("VIEWS",""+this.VIEWINSTRUCTION);
        }


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FinanceMenu.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (FinanceMenu.this.position == 3 || FinanceMenu.this.position == 2 || FinanceMenu.this.position == 1) {
                    FInanceDepositFragment depositFragment = ((FInanceDepositFragment) mPagerAdapter.getFragmentAt(1));
                    depositFragment.clearFocus();
                    depositFragment.saveData();

                    FInanceExpenditureFragment expenditureFragment = ((FInanceExpenditureFragment) mPagerAdapter.getFragmentAt(2));
                    expenditureFragment.clearFocus();
                    expenditureFragment.saveData();

                    if (FinanceMenu.this.position == 3) {
                        ((FInanceActivityFragment) mPagerAdapter.getFragmentAt(3)).load();
                    }
                }

            }
        });
    }
    private void SavePreferences(String key, String value){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private int LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        int viewsnumber = (sharedPreferences.getString("VIEWS", "").equals("")?0:Integer.parseInt(sharedPreferences.getString("VIEWS", "")));
        return viewsnumber;
    }



}



