package com.example.sergio.sistz.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pabloyac on 3/20/18.
 */

public class FinanceTransaction {
    private String date;
    private int description;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;
    private String deposit;
    private String observation;
    private String balance;
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }





    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


    //public static Float current_balance;
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

    private String expenditures;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

     public static FinanceTransaction getFistElementFronCursor(Cursor c, boolean type ) {
        FinanceTransaction item = null;
        if (c!= null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                item = new FinanceTransaction();
                item.setDescription(c.getInt(c.getColumnIndex("type")));

                if (type) {
                    item.setExpenditures(c.getString(c.getColumnIndex("amount")));
                    item.setDeposit("0");
                } else {
                    item.setDeposit(c.getString(c.getColumnIndex("amount")));
                    item.setExpenditures("0");
                }
                item.setDate(c.getString(c.getColumnIndex("date")));
            }
        }
        return item;
    }

    public static List<FinanceTransaction> getListFromCursor (Cursor c) {
        List<FinanceTransaction> list = null;
        FinanceTransaction item;
        if (c != null && c.getCount() > 0) {
            list =  new ArrayList<FinanceTransaction>();
            c.moveToFirst();
            while (! c.isAfterLast()) {
                if (c.isFirst()){

                }
                item = new FinanceTransaction();
                item.setDescription((c.getInt(c.getColumnIndex("type"))));
                item.setDeposit(c.getString(c.getColumnIndex("deposit")));
                item.setDesc(c.getString(c.getColumnIndex("desc")));
                item.setExpenditures(c.getString(c.getColumnIndex("expenditure")));
                item.setDate(c.getString(c.getColumnIndex("date")));
                item.setObservation(c.getString(c.getColumnIndex("comment")));
                item.setBalance(c.getString(c.getColumnIndex("balance")));
                list.add(item);
                c.moveToNext();
            }
        }
        return list;
    }
}