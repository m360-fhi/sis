package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.adapter.SpinnerItemAdapter;
import com.example.sergio.sistz.data.SpinnerItem;
import com.example.sergio.sistz.data.TableData;
import com.example.sergio.sistz.mysql.DBUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sergio.sistz.ReportContainerActivity.REPORT_1;
import static java.util.Calendar.MONTH;

public class ReportAttendancePerWeek0 extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private int REPORT_PAGE = 0;
    private Intent result;
    private int report;
    private String reportTitle;
    private String reportInformation;
    private DBUtility conn = new DBUtility(getContext());
    private Spinner spMonth, spLevel, spGrade, spShift, spStream, spYear;
    private int year = Calendar.getInstance().get(Calendar.YEAR);//get current year
    private LinearLayout mLinearLayout;
    private DBUtility connDB;
    private Locale loc;
    private ImageButton btnInformation;


    private List<String> getStringListYear() {
        List<String> lista = new ArrayList<>();
        List<SpinnerItem> list = conn.getYears();
        for (int i = 0; i < list.size(); i++)
            lista.add(list.get(i).getValue());
        return lista;
    }


    private List<String> getStringListMonth(int yyyy) {
        Calendar calendar = Calendar.getInstance();
        int currSchoolYear = calendar.get(Calendar.YEAR);

        List<String> lista = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("MMMM");
        Calendar time = Calendar.getInstance();
        time.setFirstDayOfWeek(Calendar.SUNDAY);
        time.set(Calendar.DAY_OF_MONTH, 1);
        int month = Calendar.getInstance().get(MONTH);
        lista = new ArrayList<String>();
        System.out.println(yyyy + " " + currSchoolYear);
        if (yyyy == currSchoolYear) {
            for (int i = 0; i <= month; i++) {
                time.set(Calendar.MONTH, i);
                lista.add(df.format(time.getTime()));
            }
        } else {
            for (int i = 0; i <= 11; i++) {
                time.set(Calendar.MONTH, i);
                lista.add(df.format(time.getTime()));
            }

        }

        return lista;
    }

    private List<String> getStringListShift() {
        List<String> lista = new ArrayList<>();
        lista.add(getResources().getString(R.string.str_s_tv14));//str_s_tv14
        lista.add(getResources().getString(R.string.str_s_tv15));
        return lista;
    }

    private List<String> getStringListLevel() {
        List<String> lista = new ArrayList<>();
        lista.add(getResources().getString(R.string.pp));
        lista.add(getResources().getString(R.string.txt_primary));
        return lista;
    }

    private List<String> getStringListGrade(String level) {
        List<String> lista = new ArrayList<>();
        if (level.equals(getResources().getString(R.string.txt_primary))) {
            lista.add(getResources().getString(R.string.str_g_std1));
            lista.add(getResources().getString(R.string.str_g_std2));
            lista.add(getResources().getString(R.string.str_g_std3));
            lista.add(getResources().getString(R.string.str_g_std4));
            lista.add(getResources().getString(R.string.str_g_std5));
            lista.add(getResources().getString(R.string.str_g_std6));
            lista.add(getResources().getString(R.string.str_g_std7));
        } else {
            lista.add(getResources().getString(R.string.str_g_yeari));
            lista.add(getResources().getString(R.string.str_g_yearii));
        }
        return lista;
    }

    public static List<String> getStringListStream() {
        List<String> list = new ArrayList<>();
        char ch;
        for (ch = 'A'; ch <= 'Z'; ch++) {
            list.add(String.valueOf(ch));
        }
        return list;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.report_attendance_per_week_0,
                container, false);

        //control configuration per report.
        //TEACHER REPORT 17
        //STUDENT REPORT 18
        connDB = new DBUtility(mLinearLayout.getContext());


        Bundle args = getActivity().getIntent().getExtras();
        report = args.getInt("REPORT");
        reportTitle = args.getString("REPORT_TITLE");
        reportInformation = args.getString("REPORT_INFORMATION");
        result = new Intent();

        loc = connDB.getCurrentLocale();
        //System.out.println(loc.getLanguage());


        TextView txtTitle = (TextView) mLinearLayout.findViewById(R.id.attendanceTextTitle);
        txtTitle.setText(reportTitle);

        TextView txtSubTitle = (TextView) mLinearLayout.findViewById(R.id.attendanceTextSubtitle);
        txtSubTitle.setText("");
        spYear = (Spinner) mLinearLayout.findViewById(R.id.sp_AttendanceYear);
        spMonth = (Spinner) mLinearLayout.findViewById(R.id.sp_AttendanceMonth);
        spLevel = (Spinner) mLinearLayout.findViewById(R.id.sp_AttendanceLevel);
        spGrade = (Spinner) mLinearLayout.findViewById(R.id.sp_AttendanceGrade);
        spShift = (Spinner) mLinearLayout.findViewById(R.id.sp_AttendanceShift);
        spStream = (Spinner) mLinearLayout.findViewById(R.id.sp_AttendanceStream);
        btnInformation = (ImageButton) mLinearLayout.findViewById(R.id.btn_information);

        btnInformation.setOnClickListener(this);

        spYear.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListYear()));
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                saveSettings();
                spMonth.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListMonth(Integer.valueOf(spYear.getSelectedItem().toString()))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        spMonth.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListMonth(Integer.valueOf(spYear.getSelectedItem().toString()))));
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spLevel.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListLevel()));
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                spGrade.setAdapter(new ArrayAdapter<String>(getContext(),
                        R.layout.my_simple_list_one_item, getStringListGrade(spLevel.getSelectedItem().toString())));
                saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spShift.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListShift()));
        spShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        spGrade.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListGrade(spLevel.getSelectedItem().toString())));
        spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spStream.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_one_item, getStringListStream()));
        spStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        Button button = (Button) mLinearLayout.findViewById(R.id.ButtIntent);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //ToastMSG(getResources().getString(R.string.attendance_rep_msg));
                saveSettings();
                Intent intent = new Intent(getActivity(), AssistanceReportActivity.class);
                startActivity(intent);

            }
        });


        if (report == 18) {
            TextView txtShift = (TextView) mLinearLayout.findViewById(R.id.attendanceTextShift);
            txtShift.setVisibility(View.VISIBLE);
            TextView txtLevel = (TextView) mLinearLayout.findViewById(R.id.attendanceTextLevel);
            txtLevel.setVisibility(View.VISIBLE);
            TextView txtGrade = (TextView) mLinearLayout.findViewById(R.id.attendanceTextGrade);
            txtGrade.setVisibility(View.VISIBLE);
            TextView txtStream = (TextView) mLinearLayout.findViewById(R.id.attendanceTextStream);
            txtStream.setVisibility(View.VISIBLE);

            spLevel.setVisibility(View.VISIBLE);
            spGrade.setVisibility(View.VISIBLE);
            spShift.setVisibility(View.VISIBLE);
            spStream.setVisibility(View.VISIBLE);
        }
        saveSettings();
        return mLinearLayout;
    }


    private void saveSettings() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("ATTENDANCE", MODE_PRIVATE).edit();
        editor.putString("ATTENDANCE_TITLE", reportTitle);
        editor.putString("ATTENDANCE_MONTH", spMonth.getSelectedItem().toString());
        editor.putString("ATTENDANCE_YEAR", spYear.getSelectedItem().toString());
        editor.putString("ATTENDANCE_LEVEL", spLevel.getSelectedItem().toString());
        editor.putString("ATTENDANCE_GRADE", spGrade.getSelectedItem().toString());
        editor.putString("ATTENDANCE_SHIFT", spShift.getSelectedItem().toString());
        editor.putString("ATTENDANCE_STREAM", spStream.getSelectedItem().toString());
        editor.putInt("REPORT", report);
        editor.apply();
        editor.commit();
    }


    @Override
    public Lifecycle getLifecycle() {

        return super.getLifecycle();
    }

    @Override
    public void onResume() {

        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_information) {
            dialogReprotInformation(getContext(), getResources(), reportInformation);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void ToastMSG(String msg) {
        Context context = getContext().getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void dialogReprotInformation(Context context, Resources resources, String msj) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle(resources.getString(R.string.str_bl_msj1)); // Importante
        dialogo1.setCancelable(false);
        dialogo1.setMessage(msj);
        dialogo1.setNegativeButton(resources.getString(R.string.str_g_close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();
    }
}
