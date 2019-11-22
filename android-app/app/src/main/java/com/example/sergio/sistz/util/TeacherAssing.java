package com.example.sergio.sistz.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Sergio on 3/21/2016.
 */
public class TeacherAssing {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String DB_NAME = STATICS_ROOT + File.separator + "sisdb.sqlite";
    public static final int DB_VERSION = 4;
    public static final String TABLE="_ta";

    public class Columns {
        public static final String emis = "emis";
        public static final String tc = "tc";
        public static final String shift = "shift";
        public static final String lvl = "level";
        public static final String grade = "grade";
        public static final String section = "section";
        public static final String subject = "subject";
        //public static final String _EMIS = BaseColumns._ID;
    }
}
