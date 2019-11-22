package com.example.sergio.sistz.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pabloyac on 3/20/18.
 */

public class FinanceTransactionHome {

    private String deposit;
    private String expenditures;
    private String balance;


    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(String expenditures) {
        this.expenditures = expenditures;
    }


    //type true expenditures, false deposits
    public static FinanceTransactionHome getFistElementFronCursor(Cursor c, boolean type ) {
        FinanceTransactionHome item = null;
        if (c!= null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                item = new FinanceTransactionHome();

                if (type) {
                    item.setExpenditures(c.getString(c.getColumnIndex("amount")));
                    item.setDeposit("0");
                } else {
                    item.setDeposit(c.getString(c.getColumnIndex("amount")));
                    item.setExpenditures("0");
                }

            }
        }
        return item;
    }

    public static List<FinanceTransactionHome> getListFromCursor (Cursor c) {
        List<FinanceTransactionHome> list = null;
        FinanceTransactionHome item;
        if (c != null && c.getCount() > 0) {
            list =  new ArrayList<FinanceTransactionHome>();
            c.moveToFirst();
            while (! c.isAfterLast()) {
                if (c.isFirst()){

                }
                item = new FinanceTransactionHome();
                item.setDeposit(c.getString(c.getColumnIndex("deposit")));
                item.setExpenditures(c.getString(c.getColumnIndex("expenditure")));
                list.add(item);
                c.moveToNext();
            }
        }
        return list;
    }

}