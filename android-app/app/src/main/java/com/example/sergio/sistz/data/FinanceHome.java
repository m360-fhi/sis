package com.example.sergio.sistz.data;

import android.database.Cursor;


/**
 * Created by pabloyac on 3/23/18.
 */

public class FinanceHome {
    private String previusBalance;
    private String deposist;
    private String expenditure;



    public String getPreviusBalance() {
        return previusBalance;
    }

    public void setPreviusBalance(String previusBalance) {
        this.previusBalance = previusBalance;
    }

    public String getDeposist() {
        return deposist;
    }

    public void setDeposist(String deposist) {
        this.deposist = deposist;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }



    public static FinanceHome getValueFromCursor(Cursor c) {
        FinanceHome values = null;

        if (c.getCount() > 0) {
            values = new FinanceHome();
            c.moveToFirst();
            values.setPreviusBalance(c.getString(c.getColumnIndex("previousbalance")));
            values.setDeposist(c.getString(c.getColumnIndex("deposits")));
            values.setExpenditure(c.getString(c.getColumnIndex("expenditures")));

        }
        return values;
    }
}
