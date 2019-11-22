package com.example.sergio.sistz.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 2/22/2016.
 */
public class GEO {
    private int _g1, _g2, _g3, _g4;
    private String _name1, _name2, _name3, _name4;

    public int get_g1() {return _g1;}
    public void set_g1(int g1) {this._g1 = g1;}

    public int get_g2() {return _g2;}
    public void set_g2(int g2) {this._g2 = g2;}

    public int get_g3() {return _g3;}
    public void set_g3(int g3) {this._g3 = g3;}

    public String get_name1() {return _name1;}
    public void set_name1( String name1) {this._name1 = name1;}

    public String get_name2() {return _name2;}
    public void set_name2( String name2) {this._name2 = name2;}

    public String get_name3() {return _name3;}
    public void set_name3( String name3) {this._name3 = name3;}


    public static List<GEO> getGEOCursor (Cursor c) {
        List<GEO> result = new ArrayList<GEO>();
        GEO temp;
        c.moveToFirst();
        while(!c.isAfterLast()) {
            temp = new GEO();
            temp.set_g1(c.getInt(c.getColumnIndex("_g1")));
            temp.set_g2(c.getInt(c.getColumnIndex("_g2")));
            temp.set_g3(c.getInt(c.getColumnIndex("_g3")));
            temp.set_name1(c.getString(c.getColumnIndex("name1")));
            temp.set_name2(c.getString(c.getColumnIndex("name2")));
            temp.set_name3(c.getString(c.getColumnIndex("name3")));
            result.add(temp);
            c.moveToNext();
        }
        return result;
    }


}
