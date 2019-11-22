package com.example.sergio.sistz.data;

import android.database.Cursor;

public class HomeScreenStudentCount {
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private Integer count;

    public static HomeScreenStudentCount getValueFromCursor(Cursor c) {
        HomeScreenStudentCount values = null;

        if (c.getCount() > 0) {
            values = new HomeScreenStudentCount();
            c.moveToFirst();
            values.setCount(c.getInt(c.getColumnIndex("students")));
        }
        else{
            values = new HomeScreenStudentCount();
            values.setCount(0);
        }
        return values;
    }
}
