package com.example.sergio.sistz.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class SpinnerItem {
    private int id;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SpinnerItem() {
        id=0;
        value = "";
    }
    public SpinnerItem(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static List<SpinnerItem> getListFromCursor(Cursor c) {
        List<SpinnerItem> list = null;
        SpinnerItem item;
        if (c != null && c.getCount() > 0 ) {
            c.moveToFirst();
            list = new ArrayList<SpinnerItem>();
            while(!c.isAfterLast()) {
                item = new SpinnerItem();
                item.setValue(c.getString(c.getColumnIndex("value")));
                item.setId(c.getInt(c.getColumnIndex("id")));
                c.moveToNext();
                list.add(item);
            }
        }
        return list;
    }
}
