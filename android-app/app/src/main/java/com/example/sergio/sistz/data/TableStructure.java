package com.example.sergio.sistz.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TableStructure {
    private String name;
    private String type;
    private boolean primary;
    private String defaultValue;
    private boolean notNull;
    private boolean newType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isNewType() {
        return newType;
    }

    public void setNewType(boolean newType) {
        this.newType = newType;
    }

    public static List<TableStructure> getListFormCursor(Cursor c, String newTypeColumn, String newType) {
        List<TableStructure> list = new ArrayList<TableStructure>();
        TableStructure temp;
        c.moveToFirst();
        while (!c.isAfterLast()) {
            temp = new TableStructure();
            temp.setName(c.getString(c.getColumnIndex("name")));
            temp.setDefaultValue(c.getString(c.getColumnIndex("dflt_value")));
            temp.setType(c.getString(c.getColumnIndex("type")));
            temp.setPrimary(c.getInt(c.getColumnIndex("pk"))== 1);
            temp.setNotNull(c.getInt(c.getColumnIndex("notnull"))== 1);
            temp.setNewType(false);
            if (temp.getName().equals(newTypeColumn)) {
                temp.setNewType(true);
                temp.setType(newType);
            }
            list.add(temp);
            c.moveToNext();
        }
        return list;
    }
}
