package com.example.sergio.sistz.mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.sergio.sistz.Assistance;
import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.AttendanceStudent;
import com.example.sergio.sistz.data.ClassCalendarInformation;
import com.example.sergio.sistz.data.EmisInformation;
import com.example.sergio.sistz.data.FinanceHome;
import com.example.sergio.sistz.data.FinanceTransaction;
import com.example.sergio.sistz.data.FinanceTransactionHome;
import com.example.sergio.sistz.data.GradeSex;
import com.example.sergio.sistz.data.HomeScreenStudentCount;
import com.example.sergio.sistz.data.SpinnerItem;
import com.example.sergio.sistz.data.TableStructure;
import com.example.sergio.sistz.data.TeacherStudentItem;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.FileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DBUtility {
    public static final String DB_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String DB_NAME = "sisdb.sqlite";
    public static final int DB_VER = 20;
    private static final String[] SECTION = {"A","B","C","D","E","F","G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";

    private static DBConexion conn;
    private Context context;

    public void flush() {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.close();
    }

    public DBUtility(Context context) {
        this.context = context;
        if (conn == null) {
            conn = new DBConexion(context);
        }
    }

    public HomeScreenStudentCount getCountStudents() {
        Cursor c = null;
        SQLiteDatabase db = null;
        HomeScreenStudentCount StudentCount;
        String query = "SELECT count(Distinct(s._id)) as students " +
                "FROM student AS s " +
                "JOIN _sa AS sa ON (s._id=sa.sc) " +
                "WHERE (reason_exit_1<>1 OR reason_exit_1 IS NULL)  AND (reason_exit_2<>1 OR reason_exit_2 IS NULL) AND (reason_exit_3<>1 OR reason_exit_3 IS NULL) AND (reason_exit_4<>1 OR reason_exit_4 IS NULL) AND (reason_exit_5<>1 OR reason_exit_5 IS NULL) " +
                "AND (reason_do_1<>1 OR reason_do_1 IS NULL) AND (reason_do_2<>1 OR reason_do_2 IS NULL) AND (reason_do_3<>1 OR reason_do_3 IS NULL) AND (reason_do_4<>1 OR reason_do_4 IS NULL) AND sa.year_ta=" + Calendar.getInstance().get(YEAR);
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        StudentCount = HomeScreenStudentCount.getValueFromCursor(c);
        c.close();
        db.close();
        return StudentCount;
    }

    public HomeScreenStudentCount getCountStudentsRegistered() {
        Cursor c = null;
        SQLiteDatabase db = null;
        HomeScreenStudentCount StudentCount;
        //db = conn.getWritableDatabase();
        String query = "SELECT students FROM a ";
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        StudentCount = HomeScreenStudentCount.getValueFromCursor(c);
        c.close();
        db.close();
        return StudentCount;
       /* c.close();
        db.close();
        return students == null ? "0" : students.equals("")?"0":students;*/
    }

    public String getTextBooks11(String column) {

        String query = "select " + column + " from f ";
        String textBooks = getOneValue(query, column);
        if (textBooks == null)
            textBooks = "0";
        return textBooks;
    }

    public void changeEMISCode(String emis) {
        SQLiteDatabase db = null;
        String schoolName = getEmisInformation(emis).getSchool().replace("'", "''");
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "UPDATE ms_0 SET emis='" + emis + "', flag='" + schoolName + "'";
        ;
        db.execSQL(query);
        query = "UPDATE a SET a1='" + emis + "', a2='" + schoolName + "'";
        ;
        db.execSQL(query);
        query = "UPDATE attendance SET emis='" + emis + "'";
        db.execSQL(query);
        query = "UPDATE evaluation SET emis='" + emis + "'";
        db.execSQL(query);
        query = "UPDATE behaviour SET emis='" + emis + "'";
        db.execSQL(query);
        db.close();

    }

    /**
     * Get Quantitiy of Latrine of the School
     *
     * @return List of Integer with index 0: boys_only, 1: girls_only, 2: combined
     */
    public List<Integer> getSchoolLatrineQuantity() {
        SQLiteDatabase db;
        ArrayList<Integer> result = null;
        Cursor c = null;
        String sql = "SELECT q3e1 boys_only, q3e2 girls_only, q3e3 combined FROM q LIMIT 1";
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        c = db.rawQuery(sql, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            result = new ArrayList<>();
            result.add(c.getInt(c.getColumnIndex("boys_only")));
            result.add(c.getInt(c.getColumnIndex("girls_only")));
            result.add(c.getInt(c.getColumnIndex("combined")));
        }
        return result;
    }

    public String getSubjectName(String id) {
        String query = "select subject from subject where level = 1 and id  = " + id;
        return getOneValue(query, "subject");
    }

    public String getStudents11(int currGradeCount, int year) {
        String yyyy = String.valueOf(year);
        String currGrade = null;
        //current grade
        if (currGradeCount == 1)
            currGrade = "'%1%'";
        else if (currGradeCount == 2)
            currGrade = "'%2%'";
        else if (currGradeCount == 3)
            currGrade = "'%3%'";
        else if (currGradeCount == 4)
            currGrade = "'%4%'";
        else if (currGradeCount == 5)
            currGrade = "'%5%'";
        else if (currGradeCount == 6)
            currGrade = "'%6%'";
        else if (currGradeCount == 7)
            currGrade = "'%7%'";

        /*String query = "SELECT count(*) total " +
                " FROM _sa A JOIN (SELECT  DISTINCT(_id),*  from student) B ON A.sc = B._id  JOIN grade C ON (C.id = A.grade AND C.level = A.level) " +
                " WHERE A.year_ta =" + yyyy +
                " AND A.grade IN (Select id FROM grade WHERE  level IN (1)) " +
                " and  (b.reason_exit_1 = 0 or b.reason_exit_1 is null ) " +
                " and  (b.reason_exit_2 = 0 or b.reason_exit_2 is null ) " +
                " and  (b.reason_exit_3 = 0 or b.reason_exit_3 is null )" +
                " and  (b.reason_exit_4 = 0 or b.reason_exit_4 is null )" +
                " and  (b.reason_exit_5 = 0 or b.reason_exit_5 is null ) " +
                " and  (b.reason_do_1 = 0 or b.reason_do_1 is null ) " +
                " and  (b.reason_do_2 = 0 or b.reason_do_2 is null ) " +
                " and  (b.reason_do_3 = 0 or b.reason_do_3 is null ) " +
                " and  (b.reason_do_4 = 0 or b.reason_do_4 is null ) " +
                " and c.level = 1 and c.id like " + currGrade +
                " AND A.level IN (1) GROUP BY C.grade ";*/

        String query = "SELECT count(*) total  FROM _sa A " +
                "JOIN " +
                "(SELECT  DISTINCT(_id),*  from student) B ON A.sc = B._id    " +
                " WHERE A.year_ta =" + yyyy +
                " AND A.grade = " + currGradeCount +
                " and a.level=1 and  (b.reason_exit_1 = 0 or b.reason_exit_1 is null ) " +
                " and  (b.reason_exit_2 = 0 or b.reason_exit_2 is null )  " +
                " and  (b.reason_exit_3 = 0 or b.reason_exit_3 is null ) " +
                " and  (b.reason_exit_4 = 0 or b.reason_exit_4 is null ) " +
                " and  (b.reason_exit_5 = 0 or b.reason_exit_5 is null )  " +
                " and  (b.reason_do_1 = 0 or b.reason_do_1 is null )  " +
                " and  (b.reason_do_2 = 0 or b.reason_do_2 is null )  " +
                " and  (b.reason_do_3 = 0 or b.reason_do_3 is null ) " +
                " and  (b.reason_do_4 = 0 or b.reason_do_4 is null ) ";

        String Students = getOneValue(query, "total");
        if (Students == null)
            Students = "0";
        return Students;
    }

    public void executeUpdates(JSONArray list, SQLiteDatabase UsedDb) {
        SQLiteDatabase db = UsedDb;
        JSONObject instruccion;
        String query;

        int pos;
        try {
            for (int i = 0; i < list.length(); i++) {
                pos = list.getString(i).indexOf("\n");
                query = list.getString(i);
                if (query.substring(0, 1).equals("{")) {
                    query = query.replace("'", "\"");
                    instruccion = new JSONObject(query);
                    if (instruccion.getString("action").equals("add_column")) {
                        addColumn(instruccion.getString("table"), instruccion.getString("column"), instruccion.getString("type"), db);
                    }
                    if (instruccion.getString("action").equals("change_column")) {
                        changeColumnType(instruccion.getString("table"), instruccion.getString("column"), instruccion.getString("new-type"), db);
                    }
                } else {
                    if (pos != -1) {
                        query = query.substring(0, pos);
                    }
                    db.execSQL(query);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getOneValue(String query, String ColName) {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        String value = null;
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                value = c.getString(c.getColumnIndex(ColName));
            }
            c.close();
            db.close();
        }
        return value;
    }


    public List<SpinnerItem> getSubjectByGrade(int idGrade) {
        SQLiteDatabase db = null;
        String query = "SELECT id id, subject value FROM subject WHERE level = 1 ";
        String condition = "";
        switch (idGrade) {
            case 1:
            case 2:
                condition = " AND id IN (1,2,3,4,5)";
                break;
            case 3:
            case 4:
                condition = " AND id IN (7,8,12,17,18,19)";
                break;
            case 5:
            case 6:
            case 7:
                condition = " AND id IN (7,8,9,10,11,12,13,14,15,16)";
                break;

        }
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        Cursor c = db.rawQuery(query + condition, null);
        List<SpinnerItem> list = SpinnerItem.getListFromCursor(c);
        c.close();
        db.close();
        return list;
    }

    public List<List<String>> getPrimaryRepetitionByGrade(int year) {
        List<List<String>> result = new ArrayList<>();
        List<String> row;
        SQLiteDatabase db = null;
        String grade, enrolled, repeaters, new_entrants;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        String condExit = "AND (s.reason_exit_1<>1 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2<>1 OR s.reason_exit_2 IS NULL) " +
                "  AND (s.reason_exit_3<>1 OR s.reason_exit_3 IS NULL)  AND (s.reason_exit_4<>1 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5<>1 OR s.reason_exit_5 IS NULL) " +
                "  AND (s.reason_do_1<>1 OR s.reason_do_1 IS NULL) AND (s.reason_do_2<>1 OR s.reason_do_2 IS NULL) AND (s.reason_do_3<>1 OR s.reason_do_3 IS NULL) ";
        String sql = "SELECT " +
                "G.grade, " +
                "COUNT(sc) enrolled, " +
                "(SELECT COUNT(sc) FROM _sa SA2  JOIN  (SELECT  DISTINCT(_id),*  from student) S ON S._id = SA2.sc WHERE level = 1 AND SA2.grade = SA.grade AND SA2.repeater = 1 " + condExit + " AND SA2.year_ta = " + year + " ) repeaters, " +
                "(SELECT COUNT(sc) FROM _sa SA2  JOIN  (SELECT  DISTINCT(_id),*  from student) S ON S._id = SA2.sc WHERE level = 1 AND SA2.grade = SA.grade AND SA2.new_entrant = 1 " + condExit + " AND SA2.year_ta = " + year + " )  new_entrants " +
                "FROM _sa SA " +
                " JOIN (SELECT  DISTINCT(_id),*  from student)  S ON S._id = SA.sc " +
                "LEFT JOIN grade G ON SA.grade = G.id " +
                "WHERE SA.level = 1 AND G.level=1 AND SA.year_ta = " + year + " AND (SA.repeater <>1 or SA.repeater is null) and (SA.new_entrant<>1 or SA.new_entrant is null) " +
                condExit +
                "GROUP BY G.grade ORDER BY G.id";

        Cursor c = db.rawQuery(sql, null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                grade = c.getString(c.getColumnIndex("grade"));
                enrolled = c.getString(c.getColumnIndex("enrolled"));
                new_entrants = c.getString(c.getColumnIndex("new_entrants"));
                repeaters = c.getString(c.getColumnIndex("repeaters"));

                row = new ArrayList<String>();
                row.add(grade);
                row.add(enrolled);
                row.add(new_entrants);
                row.add(repeaters);

                result.add(row);
                c.moveToNext();
            }
        }
        return result;
    }

    public float getAverrageGradeRep7ByGrade(String year, String month, String Grade, String Stream, String Subject) {
        float result = 0;
        String query = "SELECT avg(points) result FROM evaluation A LEFT JOIN _sa B ON B.sc = A.s_id " +
                " JOIN (SELECT  DISTINCT(_id),*  from student) C ON B.sc = C._id WHERE B.grade = " + Grade + //" AND B.section = " + Stream +
                " AND A.note = " + month + " AND A.subject = '" + Subject + "' AND year_ta = " + year +
                " and A.date like '%" + year + "%'";
        System.out.println(query);
        try {
            result = Float.valueOf(getOneValue(query, "result"));
        } catch (Exception ex) {
            result = 0;
        }
        return result;
    }

    public float getAverrageGradeRep7ByStream(String year, String month, String Grade, String Stream, String Subject) {
        float result = 0;
        String query = "SELECT avg(points) result FROM evaluation A LEFT JOIN _sa B ON B.sc = A.s_id " +
                " JOIN (SELECT  DISTINCT(_id),*  from student) C ON B.sc = C._id WHERE B.grade = " + Grade + " AND B.section = " + Stream +
                " AND A.note = " + month + " AND A.subject =  '" + Subject + "' AND year_ta = " + year +
                " and A.date like '%" + year + "%'";
        System.out.println(query);
        try {
            result = Float.valueOf(getOneValue(query, "result"));
        } catch (Exception ex) {
            result = 0;
        }
        return result;
    }


    public JSONArray parseJSONArrayFromCursor(Cursor c, String[] values, boolean isNeededArrayValue) {
        JSONArray array = new JSONArray();
        JSONArray temp = new JSONArray();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            array = new JSONArray();
            try {
                while (!c.isAfterLast()) {
                    temp = new JSONArray();
                    if (isNeededArrayValue) {
                        try {
                            temp.put(values[Integer.parseInt(c.getString(c.getColumnIndex("title")))]);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        temp.put(c.getString(c.getColumnIndex("title")));
                    }
                    temp.put(c.getFloat(c.getColumnIndex("value")));
                    c.moveToNext();
                    array.put(temp);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return array;
    }

    public JSONArray getCapitationGrantsByMonth(int year, String[] values) {
        Cursor c = null;
        JSONArray array = new JSONArray();
        SQLiteDatabase db = null;
        String query = "SELECT CAST(substr(date,6,2) AS INTEGER) - 1 title, SUM(amount) value FROM deposits WHERE date BETWEEN '" + DateUtility.calendarToString(DateUtility.firstDayOf(YEAR, year, 0)) + "' AND '" + DateUtility.calendarToString(DateUtility.lastDayOf(YEAR, year, 0)) + "' GROUP BY substr(date,6,2)";
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        array = parseJSONArrayFromCursor(c, values, true);
        db.close();
        return array;
    }

    //Deposits
    public JSONArray getUseOfResourcesBySource(int year, String[] values) {
        Cursor c = null;
        JSONArray array = null;
        SQLiteDatabase db = null;
        String query = "Select type title, SUM(amount) value from deposits where date BETWEEN '" + DateUtility.calendarToString(DateUtility.firstDayOf(YEAR, year, 0)) + "' AND '" + DateUtility.calendarToString(DateUtility.lastDayOf(YEAR, year, 0)) + "' GROUP BY type";
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        array = parseJSONArrayFromCursor(c, values, true);
        db.close();
        return array;
    }

    //Expenditures
    public JSONArray getUseOfResources(int year, String[] values) {
        Cursor c = null;
        JSONArray array = null;
        JSONArray temp = null;
        SQLiteDatabase db = null;
        String query = "Select type title, SUM(amount) value from expenditures where date BETWEEN '" + DateUtility.calendarToString(DateUtility.firstDayOf(YEAR, year, 0)) + "' AND '" + DateUtility.calendarToString(DateUtility.lastDayOf(YEAR, year, 0)) + "' GROUP BY type";
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        array = parseJSONArrayFromCursor(c, values, true);
        db.close();
        return array;
    }

    public Cursor getEvaluationByGrade(int idGrade, int idStream, int idExam, int idSubject, int year) {
        SQLiteDatabase db = null;
        String query = "SELECT C.family || ', ' || C.surname || ' ' || C.givenname  name, A.points grade FROM evaluation A LEFT JOIN _sa B ON B.sc = A.s_id " +
                " JOIN (SELECT  DISTINCT(_id),*  from student) C ON B.sc = C._id WHERE B.grade = " + idGrade + " AND B.section = " + idStream +
                " AND A.note = " + idExam + " AND A.subject = " + idSubject + " AND year_ta = " + year +
                " and A.date like '%" + year + "%' ORDER BY C.family";
        System.out.println(query);
        if (conn == null) {
            conn = new DBConexion(context);
        }
        Log.d("PRUEBA", query);
        db = conn.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        return c;
    }


    public HashMap<String, HashMap<Integer, Integer>> getPrimaryStudentsDisabilities(int year, String Level) {
        SQLiteDatabase db = null;
        HashMap<String, HashMap<Integer, Integer>> result = new HashMap<String, HashMap<Integer, Integer>>();
        HashMap<Integer, Integer> gradeRow;
        Cursor c = null;
        int cant;
        String grade;

        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();

        String sql = "SELECT G.grade, COUNT(S._id) cant" +
                " FROM _sa SA" +
                " LEFT JOIN (SELECT  DISTINCT(_id),*  from student) S" +
                "    ON SA.sc = S._id" +
                " LEFT JOIN grade G" +
                "    ON SA.grade = G.id" +
                " WHERE SA.level = " + Level + " AND G.level = " + Level + " AND S.disability = %d AND SA.year_ta = %d " +
                " GROUP BY G.grade;";
        for (int disability = 1; disability <= 4; disability++) {
            c = db.rawQuery(String.format(sql, disability, year), null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    grade = c.getString(c.getColumnIndex("grade"));
                    cant = c.getInt(c.getColumnIndex("cant"));

                    gradeRow = result.get(grade);
                    if (gradeRow == null) {
                        gradeRow = new HashMap<Integer, Integer>();
                        result.put(grade, gradeRow);
                    }

                    gradeRow.put(disability, cant);
                    c.moveToNext();
                }
            }
            c.close();
        }
        db.close();
        return result;
    }

    public int getDaysOfMonth(int month) {
        SQLiteDatabase db = null;
        int days = 0;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        String query = "Select days FROM MONTHLY_DAY_OF_CLASS WHERE month = " + month;
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                days = c.getInt(c.getColumnIndex("days"));
            }
        }
        c.close();
        db.close();
        return days;
    }

    public float getAveragePrimaryAttendace(int month, int year, int grade, int stream, int Shift, int Level) {
        int daysAttendance = 0, studentQuantity = 0;
        Calendar initialDate, finalDate, tempDate;
        initialDate = getDate(1, month, year);
        finalDate = getDate(28, month, year);
        tempDate = getDate(28, month, year);
        tempDate.add(Calendar.DAY_OF_MONTH, 1);
        while (tempDate.get(MONTH) == month) {
            tempDate.add(Calendar.DAY_OF_MONTH, 1);
            finalDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        SQLiteDatabase db = null;
        int days = 0;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        String query = "Select COUNT(*) conteo from attendance " +
                " where date BETWEEN '" + stringDate(initialDate) + "' AND '" + stringDate(finalDate) +
                "' and absence = 1 and level = " + String.valueOf(Level) +
                " and shift = " + String.valueOf(Shift);
        if (grade != -1) {
            query = query + " and grade = " + grade;
        }
        if (stream != -1) {
            query = query + " and section = " + stream;
        }
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                daysAttendance = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        c.close();
        query = "Select  COUNT(DISTINCT s_id) conteo FROM attendance where level = " +
                String.valueOf(Level) + " and shift = " + String.valueOf(Shift);
        if (grade != -1) {
            query = query + " and grade = " + grade;
        }
        if (stream != -1) {
            query = query + " and section = " + stream;
        }
        c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                studentQuantity = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        c.close();
        db.close();
        return studentQuantity == 0 ? (float) 0 : (float) daysAttendance / studentQuantity;
    }

    public FinanceHome getFinanceHome(String firstDay, String lastDay, String initialDate, String finalDate) {
        Cursor c = null;
        SQLiteDatabase db = null;
        FinanceHome financeValues;
        String query = "select previousbalance, deposits, expenditures from" +
                " (select '1' as id, ( case  when a.balance is null then '0' else a.balance end) as previousbalance from" +
                " (select a.deposit, b.expenditure, (a.deposit-b.expenditure) as balance from" +
                " (select  '1' as id, sum(amount) as deposit from deposits where date <=  '" + lastDay + "'" +
                " )  as a" +
                " JOIN" +
                " (select  '1' as id, sum(amount) as expenditure from expenditures where date <=  '" + lastDay + "' " +
                " ) as b on (a.id=b.id)) as a) as a" +
                " join" +
                " (select  '1' as id, (case when sum(amount) is null then '0' else sum(amount) end)  as deposits from deposits where date between  '" + initialDate + "' " +
                " AND '" + finalDate + "') as b on (a.id=b.id)" +
                " join" +
                " (select  '1' as id, (case when sum(amount) is null then '0' else sum(amount) end)  as expenditures from expenditures where date between  '" + initialDate + "' " +
                " AND '" + finalDate + "') as c on (a.id=c.id)";
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        financeValues = FinanceHome.getValueFromCursor(c);
        c.close();
        db.close();
        return financeValues;
    }

    public List<FinanceTransaction> getTransactionReport(String intialDate, String finalDate, String finalDateBalance) {
        List<FinanceTransaction> list = null;
        SQLiteDatabase db = null;
        String str1 = "";
        String query = "SELECT date, type, (" +
                "case type " +
                "when 0 then '" + context.getString(R.string.spn_exp_id1) + "' " +
                "when 1 then '" + context.getString(R.string.spn_exp_id2) + "' " +
                "when 2 then '" + context.getString(R.string.spn_exp_id3) + "' " +
                "when 3 then '" + context.getString(R.string.spn_exp_id4) + "' " +
                "when 4 then '" + context.getString(R.string.spn_exp_id5) + "' " +
                "when 5 then '" + context.getString(R.string.spn_exp_id6) + "' " +
                "when 6 then '" + context.getString(R.string.spn_exp_id7) + "' " +
                "when 7 then '" + context.getString(R.string.spn_exp_id8) + "' " +
                "when 8 then '" + context.getString(R.string.spn_exp_id9) + "' " +
                "when 9 then '" + context.getString(R.string.spn_exp_id10) + "' " +
                "when 10 then '" + context.getString(R.string.spn_exp_id11) + "' " +
                "when 11 then '" + context.getString(R.string.spn_exp_id12) + "' " +
                "when 12 then '" + context.getString(R.string.spn_exp_id13) + "' " +
                "when 13 then '" + context.getString(R.string.spn_exp_id14) + "' " +
                "when 14 then '" + context.getString(R.string.spn_exp_id15) + "' " +
                "end) as desc, " +
                "0 as deposit ,amount as expenditure, comment, 0 as balance " +
                "FROM expenditures  WHERE date BETWEEN '"
                + intialDate + "' AND '" + finalDate + "' UNION SELECT date, type,(" +
                "case type " +
                "when 0 then '" + context.getString(R.string.spn_dep_id16) + "' " +
                "when 1 then '" + context.getString(R.string.spn_dep_id17) + "' " +
                "when 2 then '" + context.getString(R.string.spn_dep_id18) + "' " +
                "when 3 then '" + context.getString(R.string.spn_dep_id19) + "' " +
                "when 4 then '" + context.getString(R.string.spn_dep_id20) + "' " +
                "when 5 then '" + context.getString(R.string.spn_dep_id21) + "' " +
                "when 6 then '" + context.getString(R.string.spn_dep_id1) + "' " +
                "when 7 then '" + context.getString(R.string.spn_dep_id2) + "' " +
                "when 8 then '" + context.getString(R.string.spn_dep_id3) + "' " +
                "when 9 then '" + context.getString(R.string.spn_dep_id4) + "' " +
                "when 10 then '" + context.getString(R.string.spn_dep_id5) + "' " +
                "when 11 then '" + context.getString(R.string.spn_dep_id6) + "' " +
                "when 12 then '" + context.getString(R.string.spn_dep_id7) + "' " +
                "end) as desc, amount as deposit ,0 as expenditure, comment, 0 as balance " +
                "FROM deposits  WHERE date BETWEEN '" + intialDate + "' AND '" + finalDate + "'";

        Cursor c = null;
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        list = FinanceTransaction.getListFromCursor(c);
        c.close();
        db.close();
        return list;
    }

    public List<FinanceTransactionHome> getTransactionReportHome(String intialDate, String finalDate) {
        List<FinanceTransactionHome> list = null;
        SQLiteDatabase db = null;
        String str1 = "";
        String query = "SELECT date, type," +
                "0 as deposit ,amount as expenditure, comment, 0 as balance " +
                "FROM expenditures  WHERE date BETWEEN '"
                + intialDate + "' AND '" + finalDate + "' UNION SELECT date, type," +
                " amount as deposit ,0 as expenditure, comment, 0 as balance " +
                "FROM deposits  WHERE date BETWEEN '" + intialDate + "' AND '" + finalDate + "'";

        Cursor c = null;
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        list = FinanceTransactionHome.getListFromCursor(c);
        c.close();
        db.close();
        return list;
    }

    public List<FinanceTransactionHome> getTransactionReportHomePrevious(String lastday) {
        List<FinanceTransactionHome> list = null;
        SQLiteDatabase db = null;
        String str1 = "";
        String query = "SELECT date, type," +
                "0 as deposit ,amount as expenditure, comment, 0 as balance " +
                "FROM expenditures  WHERE date <= '" + lastday + "' UNION SELECT date, type," +
                " amount as deposit ,0 as expenditure, comment, 0 as balance " +
                "FROM deposits  WHERE date <= '" + lastday + "'";

        Cursor c = null;
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        list = FinanceTransactionHome.getListFromCursor(c);
        c.close();
        db.close();
        return list;
    }

    public void setFinanceDeposit(String date, String type, String amount, String observation) {
        FinanceTransaction item = existsDeposit(date, type);
        if (item != null) {
            if (item.getDate().equals(date) && String.valueOf(item.getDescription()).equals(type)) {
                updateFinanceDeposit(date, type, amount, observation);
            }
        } else {
            insertFinanceDeposit(date, type, amount, observation);
        }
    }

    public void setFinanceExpenditure(String date, String type, String amount, String observation) {
        FinanceTransaction item = existsExpenditure(date, type);
        if (item != null) {
            if (item.getDate().equals(date) && String.valueOf(item.getDescription()).equals(type)) {
                updateFinanceExpenditure(date, type, amount, observation);
            }
        } else {
            insertFinanceExpenditure(date, type, amount, observation);
        }
    }

    //TODO : Mismas transacciones en la misma fecha pero distinto monto
    public FinanceTransaction existsDeposit(String date, String type) {
        FinanceTransaction values = null;
        Cursor c = null;
        SQLiteDatabase db = null;
        String query = "Select date,type,amount from deposits WHERE date ='" + date + "' and type = '" + type + "'";
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);

        values = FinanceTransaction.getFistElementFronCursor(c, false);
        c.close();
        db.close();
        return values;
    }

    public FinanceTransaction existsExpenditure(String date, String type) {
        FinanceTransaction values = null;
        Cursor c = null;
        SQLiteDatabase db = null;
        String query = "Select date,type,amount from expenditures WHERE date ='" + date + "' and type = '" + type + "'";
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);

        values = FinanceTransaction.getFistElementFronCursor(c, true);
        c.close();
        db.close();
        return values;
    }

    public void updateFinanceDeposit(String date, String type, String amount, String observation) {
        SQLiteDatabase db = null;
        String query = "UPDATE deposits SET amount = '" + amount + "', comment = '" + observation + "'   WHERE date ='" + date + "' and type = '" + type + "'";
        db = conn.getReadableDatabase();
        db.execSQL(query);
        String query_parse = "deposits%"+ getEMIS_code() +"%"+date+"%"+type+"%"+amount+"%"+observation+"%U";
        String query_bitacora ="INSERT INTO sisupdate (sis_sql, flag) values ('"+query_parse+"',1)";
        db.execSQL(query_bitacora);
        db.close();

    }

    public void insertFinanceDeposit(String date, String type, String amount, String observation) {
        SQLiteDatabase db = null;
        String query = "INSERT INTO deposits (date, type, amount, comment) VALUES ('" + date + "','" + type + "','" + amount + "','" + observation + "')";
        db = conn.getReadableDatabase();
        db.execSQL(query);
        String query_parse = "deposits%"+ getEMIS_code() +"%"+date+"%"+type+"%"+amount+"%"+observation+"%I";
        String query_bitacora ="INSERT INTO sisupdate (sis_sql, flag) values ('"+query_parse+"',1)";
        db.execSQL(query_bitacora);
        db.close();
    }

    public void updateFinanceExpenditure(String date, String type, String amount, String observation) {
        SQLiteDatabase db = null;
        String query = "UPDATE expenditures SET amount = '" + amount + "', comment = '" + observation + "'   WHERE date ='" + date + "' and type = '" + type + "'";
        db = conn.getReadableDatabase();
        db.execSQL(query);
        String query_parse = "expenditures%"+ getEMIS_code() +"%"+date+"%"+type+"%"+amount+"%"+observation+"%U";
        String query_bitacora ="INSERT INTO sisupdate (sis_sql, flag) values ('"+query_parse+"',1)";
        db.execSQL(query_bitacora);
        db.close();
    }

    public void insertFinanceExpenditure(String date, String type, String amount, String observation) {
        SQLiteDatabase db = null;
        String query = "INSERT INTO expenditures (date, type, amount, comment) VALUES ('" + date + "','" + type + "','" + amount + "','" + observation + "')";
        db = conn.getReadableDatabase();
        db.execSQL(query);
        String query_parse = "expenditures%"+ getEMIS_code() +"%"+date+"%"+type+"%"+amount+"%"+observation+"%I";
        String query_bitacora ="INSERT INTO sisupdate (sis_sql, flag) values ('"+query_parse+"',1)";
        db.execSQL(query_bitacora);
        db.close();
    }

    public int getCurrentDaysOfYear(int month) {
        SQLiteDatabase db = null;
        int days = 0;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        String query = "Select SUM(days) days FROM MONTHLY_DAY_OF_CLASS WHERE month <= " + month;
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                days = c.getInt(c.getColumnIndex("days"));
            }
        }
        c.close();
        db.close();
        return days;
    }

    public static Calendar getDate(int day, int month, int year) {
        Calendar date = Calendar.getInstance();
        date.set(YEAR, year);
        date.set(MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    //TODO: Actualmente envio el numero de maestros por las clases que el maestro tiene asignadas.
    public float getStudentTeacherRatio(int standardLevel) {
        float student = 0, teacher = 0;
        String query = null;
        if (standardLevel == 0) {
            query = "SELECT COUNT(*) conteo  FROM teacher WHERE t_s in (1,2)";
        } else {
            query = "Select Count(*) conteo from _ta WHERE level = 1";
        }

        if (standardLevel > 0 && standardLevel <= 6) {
            query = query + " and grade = " + standardLevel;
        }
        SQLiteDatabase db = null;
        List<SpinnerItem> list = null;
        SpinnerItem elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                teacher = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        c.close();
        String condExit = "AND (s.reason_exit_1<>1 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2<>1 OR s.reason_exit_2 IS NULL) " +
                "  AND (s.reason_exit_3<>1 OR s.reason_exit_3 IS NULL)  AND (s.reason_exit_4<>1 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5<>1 OR s.reason_exit_5 IS NULL) " +
                "  AND (s.reason_do_1<>1 OR s.reason_do_1 IS NULL) AND (s.reason_do_2<>1 OR s.reason_do_2 IS NULL) AND (s.reason_do_3<>1 OR s.reason_do_3 IS NULL) ";

        query = "SELECT Count(*) conteo FROM _sa A JOIN (SELECT  DISTINCT(_id),*  from student) S ON A.sc = S._id WHERE A.year_ta = " + Calendar.getInstance().get(YEAR) + " AND A.level = 1 " + condExit;
        if (standardLevel > 0 && standardLevel <= 6) {
            query = query + " and A.grade = " + standardLevel;
        }
        c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                student = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        return teacher == 0 ? 0 : (student / teacher);
    }

    public float getStudentTeacherRatioPrePrimary(int standardLevel) {
        float student = 0, teacher = 0;
        String query = null;
        if (standardLevel == 0) {
            query = "SELECT COUNT(*) conteo  FROM teacher WHERE t_s in (1,2)";
        } else {
            query = "Select Count(*) conteo from _ta WHERE level = 3";
        }

        if (standardLevel > 0 && standardLevel <= 2) {
            query = query + " and grade = " + standardLevel;
        }
        SQLiteDatabase db = null;
        List<SpinnerItem> list = null;
        SpinnerItem elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                teacher = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        c.close();
        String condExit = "AND (s.reason_exit_1<>1 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2<>1 OR s.reason_exit_2 IS NULL) " +
                "  AND (s.reason_exit_3<>1 OR s.reason_exit_3 IS NULL)  AND (s.reason_exit_4<>1 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5<>1 OR s.reason_exit_5 IS NULL) " +
                "  AND (s.reason_do_1<>1 OR s.reason_do_1 IS NULL) AND (s.reason_do_2<>1 OR s.reason_do_2 IS NULL) AND (s.reason_do_3<>1 OR s.reason_do_3 IS NULL) ";

        query = "SELECT Count(*) conteo FROM _sa A JOIN (SELECT  DISTINCT(_id),*  from student) S ON A.sc = S._id WHERE A.year_ta = " + Calendar.getInstance().get(YEAR) + " AND A.level = 3 " + condExit;
        if (standardLevel > 0 && standardLevel <= 2) {
            query = query + " and A.grade = " + standardLevel;
        }
        c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                student = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        return teacher == 0 ? 0 : (student / teacher);
    }


    public Locale getCurrentLocale() {
        Cursor c = null;
        SQLiteDatabase db = null;
        String query = "Select lang FROM lang";
        db = conn.getReadableDatabase();
        c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getString(c.getColumnIndex("lang")).equals("en")) {
                return Locale.US;
            } else {
                return new Locale("sw", "TZ");
            }
        }
        c.close();
        db.close();
        return null;
    }

    public static String stringDate(Calendar cal) {
        return String.format("%04d", cal.get(YEAR)) + "-" + String.format("%02d", (cal.get(MONTH) + 1)) + "-" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
    }

    public static String stringDate2(Calendar cal) {
        Calendar calendar = Calendar.getInstance();
        int currSchoolYear = calendar.get(Calendar.YEAR);
        if (currSchoolYear == cal.get(YEAR))
            return String.format("%04d", cal.get(YEAR)) + "-" + String.format("%02d", (cal.get(MONTH) + 1)) + "-" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
        else {
            if (cal.get(MONTH) == 0)
                return String.format("%04d", cal.get(YEAR)) + "-" + String.format("%02d", (01)) + "-" + String.format("%02d", (01));
            else
                return String.format("%04d", cal.get(YEAR)) + "-" + String.format("%02d", (12)) + "-" + String.format("%02d", (31));
        }
    }

    public List<SpinnerItem> getYears() {
        boolean ascendingOrder = false;//true= de menor a mayor

        SQLiteDatabase db = null;
        int currYear = Calendar.getInstance().get(YEAR);
        List<SpinnerItem> list = new ArrayList<SpinnerItem>();
        SpinnerItem elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT distinct  year_ta from _sa order by year_ta asc ";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                while (!c.isAfterLast()) {
                    elemento = new SpinnerItem();
                    elemento.setId(c.getInt(c.getColumnIndex("year_ta")));
                    elemento.setValue(c.getString(c.getColumnIndex("year_ta")));
                    list.add(elemento);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();

        //add current year if not present
        Calendar calendar = Calendar.getInstance();
        int currSchoolYear = calendar.get(Calendar.YEAR);
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == currSchoolYear)
                found = true;
        }
        if (!found) {
            elemento = new SpinnerItem();
            elemento.setValue(String.valueOf(currSchoolYear));
            elemento.setId(currSchoolYear);
            list.add(elemento);
        }

        //return at least an element if the list is empty
        if (list.isEmpty()) {
            //por si no hay nada en la tabla agrega el anio actual
            elemento = new SpinnerItem();
            elemento.setId(currYear);
            elemento.setValue(String.valueOf(currYear));
            list.add(elemento);
        }

        if (ascendingOrder)
            return list;
        else {
            List<SpinnerItem> list2 = new ArrayList<SpinnerItem>();
            for (int i = list.size() - 1; i >= 0; i--)
                list2.add(list.get(i));
            return list2;
        }

    }


    public List<SpinnerItem> getPrimaryGrades(int Level) {
        SQLiteDatabase db = null;
        List<SpinnerItem> list = null;
        SpinnerItem elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT id, grade FROM grade WHERE level = " + String.valueOf(Level);
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<SpinnerItem>();
                while (!c.isAfterLast()) {
                    elemento = new SpinnerItem();
                    elemento.setId(c.getInt(c.getColumnIndex("id")));
                    elemento.setValue(c.getString(c.getColumnIndex("grade")));
                    list.add(elemento);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }


    public List<SpinnerItem> getPrimaryGrades() {
        SQLiteDatabase db = null;
        List<SpinnerItem> list = null;
        SpinnerItem elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT id, grade FROM grade WHERE level = 1";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<SpinnerItem>();
                while (!c.isAfterLast()) {
                    elemento = new SpinnerItem();
                    elemento.setId(c.getInt(c.getColumnIndex("id")));
                    elemento.setValue(c.getString(c.getColumnIndex("grade")));
                    list.add(elemento);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public List<SpinnerItem> getPrimarySubjects() {
        SQLiteDatabase db = null;
        List<SpinnerItem> list = null;
        SpinnerItem elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT id, subject FROM subject WHERE level = 1";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<SpinnerItem>();
                while (!c.isAfterLast()) {
                    elemento = new SpinnerItem();
                    elemento.setId(c.getInt(c.getColumnIndex("id")));
                    elemento.setValue(c.getString(c.getColumnIndex("subject")));
                    list.add(elemento);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public List<SpinnerItem> getStream() {
        List<SpinnerItem> list = null;
        SpinnerItem elemento;
        int i = 0;
        list = new ArrayList<SpinnerItem>();
        while (!SECTION[i].equals("Z")) {
            elemento = new SpinnerItem();
            elemento.setId(i + 1);
            elemento.setValue(SECTION[i]);
            list.add(elemento);
            i++;
        }
        elemento = new SpinnerItem();
        elemento.setId(i + 1);
        elemento.setValue(SECTION[i]);
        list.add(elemento);
        return list;
    }

    //TODO : HAy estudiantes que guardan NULL en el sexo, por que no lo configuraron por default
    //TODO : HAy estudiantes repetidos en las tablas
    public int getCurrentPrimaryStudentEnrrolment() {
        SQLiteDatabase db = null;
        int valor = 0;
        int days = 0;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        String condExit = "AND (s.reason_exit_1<>1 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2<>1 OR s.reason_exit_2 IS NULL) " +
                "  AND (s.reason_exit_3<>1 OR s.reason_exit_3 IS NULL)  AND (s.reason_exit_4<>1 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5<>1 OR s.reason_exit_5 IS NULL) " +
                "  AND (s.reason_do_1<>1 OR s.reason_do_1 IS NULL) AND (s.reason_do_2<>1 OR s.reason_do_2 IS NULL) AND (s.reason_do_3<>1 OR s.reason_do_3 IS NULL) ";

        String query = "SELECT count(*) conteo  FROM _sa A JOIN (SELECT  DISTINCT(_id),*  from student) S ON A.sc = S._id  JOIN grade C ON (C.id = A.grade AND C.level = A.level) WHERE A.year_ta = " + Calendar.getInstance().get(YEAR) + " AND A.grade IN (Select id FROM grade WHERE  level IN (1)) AND A.level IN (1) " + condExit;//+ " GROUP BY C.grade";

        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                valor = c.getInt(c.getColumnIndex("conteo"));
            }
        }
        c.close();
        db.close();
        return valor;
    }

    public String getValueFromTable(String field, String table, String defaultValue) {
        String val = defaultValue;
        List<GradeSex> list = null;
        SQLiteDatabase db = null;
        int totalTime = 0;
        AttendanceStudent elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "Select " + field + " value FROM " + table;
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                val = c.getString(c.getColumnIndex("value"));
            }
        }
        c.close();
        db.close();
        return val == null ? "0" : val;
    }

    public GradeSex getTeacherBySex() {
        GradeSex item = null;
        SQLiteDatabase db = null;
        int totalTime = 0;
        AttendanceStudent elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT SUM(CASE WHEN sex = 1 THEN 1 ELSE 0 END) M , SUM(CASE WHEN sex = 2 THEN 1 ELSE 0 END) F  FROM teacher WHERE t_s in ( 1, 2 ) AND (exit=0 OR yearexit='')";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                item = new GradeSex();
                if (c.getCount() != 1 || c.getString(c.getColumnIndex("M")) != null) {
                    item.setMen(Integer.parseInt(c.getString(c.getColumnIndex("M"))));
                    item.setWomen(Integer.parseInt(c.getString(c.getColumnIndex("F"))));
                    item.setTotal(item.getMen() + item.getWomen());
                }
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return item;

    }

    public List<TeacherStudentItem> getStudentList() {
        List<TeacherStudentItem> list = null;
        SQLiteDatabase db = null;
        TeacherStudentItem item;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "select distinct(a._id) as id, ((CASE WHEN a.surname  IS NULL THEN '' ELSE a.surname END) || '  ' || (CASE WHEN a.givenname IS NULL THEN '' ELSE a.givenname END) || ' ' || (CASE WHEN a.family IS NULL THEN '' ELSE a.family END)) AS full_name, 99 as active from student as a where a._id not in " +
                "(SELECT DISTINCT(s._id) AS id FROM student AS s " +
                "  JOIN  (select * from _sa where year_ta<=" + Calendar.getInstance().get(YEAR) + ") as sa  ON (s._id=sa.sc) " +
                " WHERE (s.reason_exit_1 IS NULL OR s.reason_exit_1=0)  AND (s.reason_exit_2 IS NULL OR s.reason_exit_2=0) AND (s.reason_exit_3 IS NULL OR s.reason_exit_3=0) AND (s.reason_exit_4 IS NULL OR s.reason_exit_4=0) AND (s.reason_exit_5 IS NULL OR s.reason_exit_5=0)" +
                " AND (s.reason_do_1 IS NULL OR s.reason_do_1=0) AND (s.reason_do_2 IS NULL OR s.reason_do_2=0) AND (s.reason_do_3 IS NULL OR s.reason_do_3=0) AND (s.reason_do_4 IS NULL OR s.reason_do_4=0)) " +
                " and (a.reason_exit_1 IS NULL OR a.reason_exit_1=0)  AND (a.reason_exit_2 IS NULL OR a.reason_exit_2=0) AND (a.reason_exit_3 IS NULL OR a.reason_exit_3=0) AND (a.reason_exit_4 IS NULL OR a.reason_exit_4=0) AND (a.reason_exit_5 IS NULL OR a.reason_exit_5=0)" +
                " AND (a.reason_do_1 IS NULL OR a.reason_do_1=0) AND (a.reason_do_2 IS NULL OR a.reason_do_2=0) AND (a.reason_do_3 IS NULL OR a.reason_do_3=0) AND (a.reason_do_4 IS NULL OR a.reason_do_4=0) " +
                "ORDER BY a.family, a.surname";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<TeacherStudentItem>();
                while (!c.isAfterLast()) {
                    item = new TeacherStudentItem();
                    item.setId(c.getString(c.getColumnIndex("id")));
                    item.setFull_name(c.getString(c.getColumnIndex("full_name")));
                    item.setActive(c.getString(c.getColumnIndex("active")));
                    list.add(item);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public List<TeacherStudentItem> getTeacherList(boolean showInactives) {
        List<TeacherStudentItem> list = null;
        SQLiteDatabase db = null;
        TeacherStudentItem item;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT  DISTINCT(_id) AS id, (surname || ', ' || givenname) AS full_name,  (CASE WHEN exit is null THEN 0 ELSE exit END) AS active FROM teacher  ";
        if (!showInactives)
            query += " WHERE exit NOT IN (1, 2, 3, 4, 5, 6) OR exit IS NULL ";
        query += " ORDER BY surname, givenname ";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<TeacherStudentItem>();
                while (!c.isAfterLast()) {
                    item = new TeacherStudentItem();
                    item.setId(c.getString(c.getColumnIndex("id")));
                    item.setFull_name(c.getString(c.getColumnIndex("full_name")));
                    item.setActive(c.getString(c.getColumnIndex("active")));
                    list.add(item);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public List<GradeSex> getStudentByGradeBySex() {
        List<GradeSex> list = null;
        GradeSex item = null;
        SQLiteDatabase db = null;
        int totalTime = 0;
        AttendanceStudent elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String condExit = "AND (s.reason_exit_1<>1 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2<>1 OR s.reason_exit_2 IS NULL) " +
                "  AND (s.reason_exit_3<>1 OR s.reason_exit_3 IS NULL)  AND (s.reason_exit_4<>1 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5<>1 OR s.reason_exit_5 IS NULL) " +
                "  AND (s.reason_do_1<>1 OR s.reason_do_1 IS NULL) AND (s.reason_do_2<>1 OR s.reason_do_2 IS NULL) AND (s.reason_do_3<>1 OR s.reason_do_3 IS NULL) ";
        String query = "UPDATE student SET sex = 1 WHERE sex IS NULL";
        db.execSQL(query);
        query = "SELECT C.grade, SUM(CASE WHEN S.sex = 1 THEN 1 ELSE 0 END) M, SUM(CASE WHEN S.sex = 2 THEN 1 ELSE 0 END) F  FROM _sa A JOIN (SELECT  DISTINCT(_id),*  from student) S ON A.sc = S._id  JOIN grade C ON (C.id = A.grade AND C.level = A.level) WHERE A.year_ta = " + Calendar.getInstance().get(YEAR) + " AND A.grade IN (Select id FROM grade WHERE  level IN (1)) AND A.level IN (1) " + condExit + " GROUP BY C.grade";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<GradeSex>();
                while (!c.isAfterLast()) {
                    item = new GradeSex();
                    item.setGrade(c.getString(c.getColumnIndex("grade")));
                    item.setMen(Integer.parseInt(c.getString(c.getColumnIndex("M"))));
                    item.setWomen(Integer.parseInt(c.getString(c.getColumnIndex("F"))));
                    item.setTotal(item.getMen() + item.getWomen());
                    list.add(item);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public List<GradeSex> getStudentByGradeBySexLevel(String Level, String Year) {

        List<GradeSex> list = null;
        GradeSex item = null;
        SQLiteDatabase db = null;
        int totalTime = 0;
        AttendanceStudent elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "UPDATE student SET sex = 1 WHERE sex IS NULL";
        db.execSQL(query);
        query = "SELECT a.grade, SUM(M) AS M, SUM(F) AS F FROM " +
                " (SELECT  DISTINCT(s._id), (CASE WHEN s.sex = 1 THEN 1 ELSE 0 END) M, (CASE WHEN s.sex = 2 THEN 1 ELSE 0 END) F, g.grade, g.id " +
                " FROM student s " +
                " LEFT JOIN _sa sa ON (sa.sc=s._id AND sa.year_ta=" + Year + " AND sa.level= " + Level + ") " +
                " JOIN grade g ON (g.id=sa.grade AND g.level=sa.level) " +
                " WHERE (s.reason_exit_1<>1 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2<>1 OR s.reason_exit_2 IS NULL) " +
                " AND (s.reason_exit_3<>1 OR s.reason_exit_3 IS NULL)  AND (s.reason_exit_4<>1 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5<>1 OR s.reason_exit_5 IS NULL) " +
                " AND (s.reason_do_1<>1 OR s.reason_do_1 IS NULL) AND (s.reason_do_2<>1 OR s.reason_do_2 IS NULL) AND (s.reason_do_3<>1 OR s.reason_do_3 IS NULL) AND (s.reason_do_4<>1 OR s.reason_do_4 IS NULL) " +
                " GROUP BY s._id) AS a " +
                " GROUP BY a.grade " +
                " ORDER BY a.id";
        query =" SELECT g.grade, SUM(z.male) M, SUM(Z.female) F FROM\n" +
                " (SELECT a.shift, a.level, a.grade, studentage, SUM(M+F) AS total,  SUM(M) AS male, SUM(F) AS female FROM  (\n" +
                " SELECT  DISTINCT(s._id), sa.shift, sa.level, sa.grade, (Date() - Date(s.yearob)) as studentage,\n" +
                " (CASE WHEN s.sex = 1 THEN 1 ELSE 0 END) M, (CASE WHEN s.sex = 2 THEN 1 ELSE 0 END) F FROM student s  \n" +
                "  JOIN _sa sa ON (sa.sc=s._id AND sa.year_ta=" +Year + " AND (sa.level=" + Level + "))  \n" +
                " WHERE (s.reason_exit_1=0 OR s.reason_exit_1 IS NULL) AND (s.reason_exit_2=0 OR s.reason_exit_2 IS NULL)  AND (s.reason_exit_3=0 OR s.reason_exit_3 IS NULL)  \n" +
                " AND (s.reason_exit_4=0 OR s.reason_exit_4 IS NULL) AND (s.reason_exit_5=0 OR s.reason_exit_5 IS NULL)  AND (s.reason_do_1=0 OR s.reason_do_1 IS NULL) \n" +
                " AND (s.reason_do_2=0 OR s.reason_do_2 IS NULL) AND (s.reason_do_3=0 OR s.reason_do_3 IS NULL) AND (s.reason_do_4=0 OR s.reason_do_4 IS NULL)  GROUP BY  s._id) AS a  \n" +
                " GROUP BY a.shift, a.level, a.grade, studentage) Z\n" +
                "  JOIN grade g ON (g.id=z.grade AND g.level=z.level)\n" +
                "GROUP BY z.grade ";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<GradeSex>();
                while (!c.isAfterLast()) {
                    item = new GradeSex();
                    item.setGrade(c.getString(c.getColumnIndex("grade")));
                    item.setMen(Integer.parseInt(c.getString(c.getColumnIndex("M"))));
                    item.setWomen(Integer.parseInt(c.getString(c.getColumnIndex("F"))));
                    item.setTotal(item.getMen() + item.getWomen());
                    list.add(item);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }


    public List<AttendanceStudent> getAttendanceMonth(int month, int grade, int stream, boolean yearly, int Shift, int Level, String CurrentYear) {
        SQLiteDatabase db = null;
        List<AttendanceStudent> list = null;
        int totalTime = 0;
        AttendanceStudent elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        int year = Integer.valueOf(CurrentYear);// Calendar.getInstance().get(YEAR);
        Calendar initialDate, finalDate, tempDate;
        if (yearly) {
            initialDate = getDate(1, 0, year);
            totalTime = getCurrentDaysOfYear(Calendar.getInstance().get(MONTH) + 1);
        } else {
            initialDate = getDate(1, month, year);
            totalTime = getDaysOfMonth(month + 1);
        }
        db = conn.getWritableDatabase();
        finalDate = getDate(28, yearly ? Calendar.getInstance().get(MONTH) : month, year);
        tempDate = getDate(28, yearly ? Calendar.getInstance().get(MONTH) : month, year);
        tempDate.add(Calendar.DAY_OF_MONTH, 1);
        while (tempDate.get(MONTH) == (yearly ? Calendar.getInstance().get(MONTH) : month)) {
            tempDate.add(Calendar.DAY_OF_MONTH, 1);
            finalDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        String query = "select a._id student_id, a.family || ', ' || a.surname name , SUM(B.absence) total\n" +
                " ,SUM((CASE WHEN B.reason = 1 THEN 1 ELSE 0 END)) AS reason1\n" +
                " ,SUM((CASE WHEN B.reason = 2 THEN 1 ELSE 0 END)) AS reason2\n" +
                " ,SUM((CASE WHEN B.reason = 3 THEN 1 ELSE 0 END)) AS reason3 from\n" +
                "(Select DISTINCT(_id) student_id, _id, family, surname\n" +
                "FROM student) as a\n" +
                "JOIN (SELECT *, count(*) as filas FROM attendance\n" +
                "WHERE grade = " + grade + " AND section = " + stream +
                " and Level = " + Level + " and Shift = " + Shift +
                " AND date BETWEEN '" + stringDate(initialDate) + "' AND '" + stringDate(finalDate) + "' " +
                "GROUP BY s_id,shift,level,grade,section,date ORDER BY  s_id,shift,level,grade,section,date)  AS b ON (a._id=b.s_id)\n" +
                "where b.filas=1\n" +
                "GROUP BY a._id, a.family, a.surname\n" +
                "ORDER BY a._id;";
        System.out.println(query);
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<AttendanceStudent>();
                while (!c.isAfterLast()) {
                    elemento = new AttendanceStudent();
                    elemento.setAttendedTime(c.getInt(c.getColumnIndex("total")));
                    elemento.setId(c.getString(c.getColumnIndex("student_id")));
                    elemento.setName(c.getString(c.getColumnIndex("name")));
                    elemento.setTotalTime(totalTime);
                    elemento.setReason1(c.getInt(c.getColumnIndex("reason1")));
                    elemento.setReason2(c.getInt(c.getColumnIndex("reason2")));
                    elemento.setReason3(c.getInt(c.getColumnIndex("reason3")));
                    list.add(elemento);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public List<AttendanceStudent> getTeacherAttendanceMonth(int month, boolean yearly, int year) {
        SQLiteDatabase db = null;
        List<AttendanceStudent> list = null;
        int totalTime = 0;
        AttendanceStudent elemento;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        //int year = Calendar.getInstance().get(YEAR);
        Calendar initialDate, finalDate, tempDate;
        if (yearly) {
            initialDate = getDate(1, 0, year);
            totalTime = getCurrentDaysOfYear(Calendar.getInstance().get(MONTH) + 1);
        } else {
            initialDate = getDate(1, month, year);
            totalTime = getDaysOfMonth(month + 1);
        }
        db = conn.getWritableDatabase();
        finalDate = getDate(28, yearly ? Calendar.getInstance().get(MONTH) : month, year);
        tempDate = getDate(28, yearly ? Calendar.getInstance().get(MONTH) : month, year);
        tempDate.add(Calendar.DAY_OF_MONTH, 1);
        while (tempDate.get(MONTH) == (yearly ? Calendar.getInstance().get(MONTH) : month)) {
            tempDate.add(Calendar.DAY_OF_MONTH, 1);
            finalDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        String query = "select a._id student_id, a.surname || ', ' || a.givenname name , SUM(B.absence) total\n" +
                " ,SUM((CASE WHEN B.reason = 1 THEN 1 ELSE 0 END)) AS reason1\n" +
                " ,SUM((CASE WHEN B.reason = 2 THEN 1 ELSE 0 END)) AS reason2\n" +
                " ,SUM((CASE WHEN B.reason = 3 THEN 1 ELSE 0 END)) AS reason3 from\n" +
                "(Select DISTINCT(_id) student_id, _id, givenname, surname\n" +
                "FROM teacher) as a\n" +
                "JOIN (SELECT *, count(*) as filas FROM attendance\n" +
                "WHERE shift IS NULL AND level IS NULL AND date BETWEEN '" + stringDate(initialDate) + "' AND '" + stringDate(finalDate) + "' " +
                "GROUP BY s_id,shift,level,grade,section,date ORDER BY  t_id,shift,level,grade,section,date)  AS b ON (a._id=b.t_id)\n" +
                "where b.filas=1\n" +
                "GROUP BY a._id, a.surname,a.givenname \n" +
                "ORDER BY a._id;";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                list = new ArrayList<AttendanceStudent>();
                while (!c.isAfterLast()) {
                    elemento = new AttendanceStudent();
                    elemento.setAttendedTime(c.getInt(c.getColumnIndex("total")));
                    elemento.setId(c.getString(c.getColumnIndex("student_id")));
                    elemento.setName(c.getString(c.getColumnIndex("name")));
                    elemento.setTotalTime(totalTime);
                    elemento.setReason1(c.getInt(c.getColumnIndex("reason1")));
                    elemento.setReason2(c.getInt(c.getColumnIndex("reason2")));
                    elemento.setReason3(c.getInt(c.getColumnIndex("reason3")));
                    list.add(elemento);
                    c.moveToNext();
                }
            }
        }
        c.close();
        db.close();
        return list;
    }

    public void updateEmisCode(EmisInformation emis) {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "SELECT COUNT (*) value FROM a";
        db = conn.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                if (c.getInt(c.getColumnIndex("value")) == 0) {
                    query = "INSERT INTO a (_id,a1,a3a, a3b, a3c,a2) VALUES (1, '" + emis.getEmisCode() + "','" + emis.getIdRegion() + "','" + emis.getIdCouncil() + "','" + emis.getIdWard() + "','" + emis.getSchool().replace("'", "''") + "')";
                } else {
                    query = "UPDATE a SET a1 ='" + emis.getEmisCode() + "', a3a='" + emis.getEmisCode() + "', a3b = '" + emis.getIdCouncil() + "', a3c = '" + emis.getIdWard() + "', a2 ='" + emis.getSchool().replace("'", "''") + "' WHERE _id=1";
                    //updateSetGeoCode();
                }
            } else {
                query = "INSERT INTO a (_id, a1,a3a, a3b, a3c,a2) VALUES (1, '" + emis.getEmisCode() + "','" + emis.getIdRegion() + "','" + emis.getIdCouncil() + "','" + emis.getIdWard() + "','" + emis.getSchool().replace("'", "''") + "')";
            }
        }
        db.execSQL(query);
        c.close();
        db.close();
    }

    public void updateSetGeoCode() {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String str_sql = "DELETE FROM set_geo_codes";
        db.execSQL(str_sql);
        str_sql = "INSERT INTO  set_geo_codes(g1, g2, g3, name1, name2, name3) \n" +
                "SELECT  regioncode, councilcode, warcode, regionname,  councilname,  warname  FROM EMIS_CATALOG GROUP BY regioncode, councilcode, warcode ORDER BY regioncode, councilcode, warcode";
        db.execSQL(str_sql);
        db.close();
    }


    /**
     * Set language in database
     *
     * @param lang
     * @author Diego Calderon 23/10/2018
     */
    public void setLanguage(String lang) {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        db.execSQL("UPDATE lang SET lang='" + lang + "'");
        db.close();
    }

    /**
     * Update the texts in grade table to the current language
     *
     * @author Jorge Garcia, Diego Calderon 23/10/2018
     */
    public void updateLanguageInTables() {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std1) + "' WHERE level=1 and id=1");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std2) + "' WHERE level=1 and id=2");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std3) + "' WHERE level=1 and id=3");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std4) + "' WHERE level=1 and id=4");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std5) + "' WHERE level=1 and id=5");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std6) + "' WHERE level=1 and id=6");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_std7) + "' WHERE level=1 and id=7");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_yeari) + "' WHERE level=3 and id=1");
        db.execSQL("UPDATE grade SET grade='" + context.getResources().getString(R.string.str_g_yearii) + "' WHERE level=3 and id=2");

        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_reading) + "' WHERE level=1 and id=1");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_writing) + "' WHERE level=1 and id=2");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_arithmetic) + "' WHERE level=1 and id=3");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_healt) + "' WHERE level=1 and id=4");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_games) + "' WHERE level=1 and id=5");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_religion) + "' WHERE level=1 and id=6");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_mathematics) + "' WHERE level=1 and id=7");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_english) + "' WHERE level=1 and id=8");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_science) + "' WHERE level=1 and id=9");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_history) + "' WHERE level=1 and id=10");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_geography) + "' WHERE level=1 and id=11");

        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_all) + "' WHERE level=3 and id=1");

        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_kiswahili) + "' WHERE level=1 and id=12");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_civics) + "' WHERE level=1 and id=13");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_vocational_skills) + "' WHERE level=1 and id=14");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_ict) + "' WHERE level=1 and id=15");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_personality) + "' WHERE level=1 and id=16");

        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_social_studies) + "' WHERE level=1 and id=17");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_civics_and_moral) + "' WHERE level=1 and id=18");
        db.execSQL("UPDATE subject SET subject='" + context.getResources().getString(R.string.str_g_cience_and_technology) + "' WHERE level=1 and id=19");
        db.close();
    }

    public void deleteExpenditure(String date, String type) {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String str_sql = "DELETE FROM expenditures where date='" + date + "' and type=" + type;
        db.execSQL(str_sql);

        db.close();
    }

    public void deleteDeposits(String date, String type) {
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String str_sql = "DELETE FROM deposits where date='" + date + "' and type=" + type;
        db.execSQL(str_sql);

        db.close();
    }

    public ArrayList<ClassCalendarInformation> ClassCalendarInformation(int currentyear) {
        ArrayList<ClassCalendarInformation> list = new ArrayList<>();
        ClassCalendarInformation ccData = null;
        SQLiteDatabase db = null;
        String query = "SELECT month AS ccMonth, days  AS ccDays FROM MONTHLY_DAY_OF_CLASS WHERE year = " + currentyear;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ccData = new ClassCalendarInformation();
                ccData.setCcMoth(c.getInt(c.getColumnIndex("ccMonth")));
                ccData.setCcdays(c.getInt(c.getColumnIndex("ccDays")));
                list.add(ccData);
                c.moveToNext();
            }
        }
        c.close();
        db.close();
        return list;
    }

    public EmisInformation getEmisInformation(String emisCode) {
        EmisInformation emisData = null;
        SQLiteDatabase db = null;
        String query = "SELECT id, emiscode, regioncode, regionname,councilcode , councilname , warcode , warname ,schoolname FROM EMIS_CATALOG WHERE emiscode LIKE '" + emisCode + "'";
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            emisData = new EmisInformation();
            c.moveToFirst();
            emisData.setId(c.getInt(c.getColumnIndex("id")));
            emisData.setCouncil(c.getString(c.getColumnIndex("councilname")));
            emisData.setEmisCode(c.getString(c.getColumnIndex("emiscode")));
            emisData.setIdCouncil(c.getInt(c.getColumnIndex("councilcode")));
            emisData.setIdRegion(c.getInt(c.getColumnIndex("regioncode")));
            emisData.setRegion(c.getString(c.getColumnIndex("regionname")));
            emisData.setIdWard(c.getInt(c.getColumnIndex("warcode")));
            emisData.setWard(c.getString(c.getColumnIndex("warname")));
            emisData.setSchool(c.getString(c.getColumnIndex("schoolname")));
        }
        c.close();
        db.close();
        return emisData;
    }

    private boolean changeColumnType(String table, String column, String newType, SQLiteDatabase database) {
        SQLiteDatabase db = database;
        String fields = "";
        List<TableStructure> tableStructure = null;
        String query = "PRAGMA table_info('" + table + "')";
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            tableStructure = TableStructure.getListFormCursor(c, column, newType);
            query = "DROP TABLE IF EXISTS " + table + "_BAK";
            db.execSQL(query);
            query = "ALTER TABLE " + table + " RENAME TO " + table + "_BAK";
            db.execSQL(query);
            query = "CREATE TABLE " + table + "(";
            for (TableStructure element : tableStructure) {
                fields = fields + element.getName() + ",";
                query = query + element.getName() + " " + element.getType()
                        + " " + (element.isPrimary() ? " PRIMARY KEY " : "") + (element.isNotNull() ? " NOT NULL " : "") + ",";
            }
            query = query.substring(0, query.length() - 2) + ")";
            fields = fields.substring(0, fields.length() - 1);
            db.execSQL(query);
            query = "INSERT INTO " + table + " (" + fields + ") SELECT " + fields + " FROM " + table + "_BAK";
            db.execSQL(query);
        }
        return false;
    }

    private void addColumn(String table, String column, String type, SQLiteDatabase db) {
        String query = "";
        if (!columnExists(table, column, db)) {
            query = "ALTER TABLE " + table + " ADD COLUMN " + column + " " + type;
            db.execSQL(query);
        }
    }

    public int getCountSetGeoCodesTable(SQLiteDatabase db1) {
        int cantidad = 0;
        SQLiteDatabase db = db1;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        String query = "SELECT Count(*) valor FROM set_geo_codes";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                cantidad = c.getInt(0);
            }
        }
        c.close();
        db.close();
        return cantidad;
    }

    public boolean columnExists(String table, String colum, SQLiteDatabase db) {
        if (!db.isOpen() || db == null) {
            db = conn.getWritableDatabase();
        }
        boolean result = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + table + " LIMIT 0"
                    , null);
            result = cursor != null && cursor.getColumnIndex(colum) != -1;
        } catch (Exception e) {
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }


    /**
     * function that returns the specific character to display the attendance per day per person.
     * <p>
     * this function returns the specific attendance character per person per day.
     * for instance, if there is no attendance, the character returned is ' ', if there was
     * no attendance it will return 'A' and if there is a positive attendance, the character
     * returned is '●'
     * </p>
     *
     * @param att if the value is 0, it is Absent, if there is 1 it is attendend. the default
     *            value is ' '.
     * @return the character associated to that particular day per person.
     * @author Herlich Steven Gonzalez Zambrano
     * @lastChange 15-10-2018
     */

    public String retAttendanceTypeIcon(int att) {
        String ret = " ";
        if (att == 0)
            ret = "A";
        else if (att == 1)
            ret = "●";
        return ret;
    }


    /**
     * function that returns the specific attendance per day.
     * <p>
     * this function returns the specific attendance per person per day.
     * </p>
     *
     * @param report     17 if this is for teachers and 18 if this is for students.
     * @param weekNumber 1,2,3,4,5,6 (each week according to calendar)
     * @param month      month number according to calendar.
     * @param YYYY       current year.
     * @param Shift      current students' shift.
     * @param Level      current students' level.
     * @param Grade      current students' grade.
     * @param Stream     current students' stream.
     * @param YYYY       current year.
     * @return the character associated to that particular day per person.
     * @author Herlich Steven Gonzalez Zambrano
     * @lastChange 15-10-2018
     */

    public String getDayAttendance(String id, String DDDD, int MM, int YYYY, int reporte) {
        String ret = " ";
        String F = YYYY + "-" + String.format("%02d", Integer.valueOf(MM)) + "-" + String.format("%02d", Integer.valueOf(DDDD));
        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "";
        String q2 = "";

        if (reporte == 17) {
            query = " select absence from attendance where date = '" + F + "' and t_id = '" + id + "' " +
                    " and shift is null and level is null and grade is null ";
        } else if (reporte == 18) {
            query = " select absence from attendance where date = '" + F + "' and s_id = '" + id + "' " +
                    " and shift is not  null and level is not null and grade is not null ";
        }


        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ret = String.valueOf(retAttendanceTypeIcon(c.getInt(0)));
                c.moveToNext();
            }

        }
        c.close();
        db.close();

        return ret;
    }

    /**
     * function that returns all students per week for the Student Attendance list report
     * <p>
     * this function shows all the students listed per month,shift, level, grade
     * and stream listed per week. each week starts in Sunday and ends in Saturday.
     * A is Absent, * is attended, ' ' data doesn't exist.
     * </p>
     *
     * @param report     17 if this is for teachers and 18 if this is for students.
     * @param weekNumber 1,2,3,4,5,6 (each week according to calendar)
     * @param month      month number according to calendar.
     * @param YYYY       current year.
     * @param Shift      current students' shift.
     * @param Level      current students' level.
     * @param Grade      current students' grade.
     * @param Stream     current students' stream.
     * @param YYYY       current year.
     * @return Assistance class that is the person with all the days of that particular week.
     * @author Herlich Steven Gonzalez Zambrano
     * @lastChange 15-10-2018
     */
    public ArrayList<Assistance> getStudentAttendancePerWeek(int report, int weekNumber, Assistance W, int MM, int YYYY
            , String Shift, String Level, String Grade, String Stream) {
        Assistance A = new Assistance(context.getString(R.string.table_1_report1_name));
        ArrayList<Assistance> list = new ArrayList<>();

        String ShiftN = "";
        if (Shift.equals("Morning") || Shift.equals("Asubuhi"))
            ShiftN = "1";
        else
            ShiftN = "2";

        String LevelN = "";
        if (Level.equals("Pre-Primary") || Level.equals("Awali"))
            LevelN = "3";
        else
            LevelN = "1";

       /* if (Grade.equals("Darasa la Kwanza"))
            Grade = "STD 1";
        else if (Grade.equals("Darasa la Pili"))
            Grade = "STD 2";
        else if (Grade.equals("Darasa la Tatu"))
            Grade = "STD 3";
        else if (Grade.equals("Darasa la Nne"))
            Grade = "STD 4";
        else if (Grade.equals("Darasa la Tano"))
            Grade = "STD 5";
        else if (Grade.equals("Darasa la Sita"))
            Grade = "STD 6";
        else if (Grade.equals("Darasa la Saba"))
            Grade = "STD 7";
        else if (Grade.equals("Mwaka I"))
            Grade = "Year I";
        else if (Grade.equals("Mwaka II"))
            Grade = "Year II";*/

        String GradeN = " select id from grade where grade = '" + Grade + "' ";

        char character = Stream.charAt(0);
        int StreamC = (int) character - 64;
        String StreamN = String.valueOf(StreamC);

        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "";
        //query = " select distinct s.family ||\" \"|| s.surname , s._id " +
        query = " select distinct s.surname ||\" \"|| s.family , s._id " +
                "  from student s left join  _sa a on s._id = a.sc " +
                "  where  a.year_ta =  " + String.valueOf(YYYY) +
                " and a.shift = " + ShiftN + " and a.level = " + LevelN +
                " and a.grade in( " + GradeN + " )  and a.section =  " + StreamN +
                " and reason_exit_1=0 AND reason_exit_2=0 AND reason_exit_3=0 AND reason_exit_4=0 " +
                " AND reason_exit_5=0 AND reason_do_1=0 AND reason_do_2=0 AND reason_do_3=0 AND reason_do_4=0 " +
                " order by s.surname "
                //" order by s.family, s.surname "
        ;
        System.out.println(query);
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {

            c.moveToFirst();
            while (!c.isAfterLast()) {
                A = new Assistance(context.getString(R.string.table_1_report1_name));
                A.setName(c.getString(0));
                A.setID(c.getString(1));
                if (!W.getSunday().equals(" "))
                    A.setSunday(getDayAttendance(c.getString(1), W.getSunday(), MM, YYYY, report));
                if (!W.getMonday().equals(" "))
                    A.setMonday(getDayAttendance(c.getString(1), W.getMonday(), MM, YYYY, report));
                if (!W.getTuesday().equals(" "))
                    A.setTuesday(getDayAttendance(c.getString(1), W.getTuesday(), MM, YYYY, report));
                if (!W.getWednesday().equals(" "))
                    A.setWednesday(getDayAttendance(c.getString(1), W.getWednesday(), MM, YYYY, report));
                if (!W.getThursday().equals(" "))
                    A.setThursday(getDayAttendance(c.getString(1), W.getThursday(), MM, YYYY, report));
                if (!W.getFriday().equals(" "))
                    A.setFriday(getDayAttendance(c.getString(1), W.getFriday(), MM, YYYY, report));
                if (!W.getSaturday().equals(" "))
                    A.setSaturday(getDayAttendance(c.getString(1), W.getSaturday(), MM, YYYY, report));

                list.add(A);
                c.moveToNext();
            }

        }
        c.close();
        db.close();

        return list;

    }

    /**
     * function that returns all teachers per week for the teacher Attendance list report
     * <p>
     * this function shows all the teachers listed per month per week.
     * Each week starts in Sunday and ends in Saturday.
     * A is Absent, * is attended, ' ' data doesn't exist.
     * </p>
     *
     * @param report     17 if this is for teachers and 18 if this is for students.
     * @param weekNumber 1,2,3,4,5,6 (each week according to calendar)
     * @param month      month number according to calendar.
     * @param YYYY       current year.
     * @return Assistance class that is the person with all the days of that particular week.
     * @author Herlich Steven Gonzalez Zambrano
     * @lastChange 15-10-2018
     */
    public ArrayList<Assistance> getTeacherAttendancePerWeek(int report, int weekNumber, Assistance W, int MM, int YYYY) {
        Assistance A = new Assistance(context.getString(R.string.table_1_report1_name));
        ArrayList<Assistance> list = new ArrayList<>();

        SQLiteDatabase db = null;
        if (conn == null) {
            conn = new DBConexion(context);
        }
        db = conn.getWritableDatabase();
        String query = "";
        query = " select  distinct t.givenname||\" \"|| t.surname name , _id  " +
                "  from teacher t " +
                " where t.t_s in (1,2) " +
                " and exit = '' or exit = 0 OR exit IS NULL  " +
                " order by t.givenname, t.surname,_id "
        ;
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {

            c.moveToFirst();
            while (!c.isAfterLast()) {
                A = new Assistance(context.getString(R.string.table_1_report1_name));
                A.setName(c.getString(0));
                A.setID(c.getString(1));
                if (!W.getSunday().equals(" "))
                    A.setSunday(getDayAttendance(c.getString(1), W.getSunday(), MM, YYYY, report));
                if (!W.getMonday().equals(" "))
                    A.setMonday(getDayAttendance(c.getString(1), W.getMonday(), MM, YYYY, report));
                if (!W.getTuesday().equals(" "))
                    A.setTuesday(getDayAttendance(c.getString(1), W.getTuesday(), MM, YYYY, report));
                if (!W.getWednesday().equals(" "))
                    A.setWednesday(getDayAttendance(c.getString(1), W.getWednesday(), MM, YYYY, report));
                if (!W.getThursday().equals(" "))
                    A.setThursday(getDayAttendance(c.getString(1), W.getThursday(), MM, YYYY, report));
                if (!W.getFriday().equals(" "))
                    A.setFriday(getDayAttendance(c.getString(1), W.getFriday(), MM, YYYY, report));
                if (!W.getSaturday().equals(" "))
                    A.setSaturday(getDayAttendance(c.getString(1), W.getSaturday(), MM, YYYY, report));

                list.add(A);
                c.moveToNext();
            }

        }
        c.close();
        db.close();

        return list;
    }


    public class DBConexion extends SQLiteOpenHelper {

        public SQLiteDatabase myDatabase;
        private final Context myContext;

        public DBConexion(Context context) {
            super(context, DB_ROOT + File.separator + DB_NAME, null, DBUtility.DB_VER);
            this.myContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String json = FileUtility.getQueriesUpdates(context);
            try {
                JSONArray jsonQueries = new JSONArray(json);
                executeUpdates(jsonQueries, db);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String json = FileUtility.getQueriesUpdates(context);
            try {
                JSONArray jsonQueries = new JSONArray(json);
                executeUpdates(jsonQueries, db);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(context,STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return getemiscode;
    }
}
