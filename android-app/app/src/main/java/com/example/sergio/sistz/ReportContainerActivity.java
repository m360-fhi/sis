package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.adapter.SpinnerItemAdapter;
import com.example.sergio.sistz.data.AttendanceStudent;
import com.example.sergio.sistz.data.GradeSex;
import com.example.sergio.sistz.data.SpinnerItem;
import com.example.sergio.sistz.data.TableData;
import com.example.sergio.sistz.fragment.IFragmentActions;
import com.example.sergio.sistz.fragment.ReportElementFragment;
import com.example.sergio.sistz.mysql.DBUtility;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ReportContainerActivity extends AppCompatActivity implements IFragmentActions, AdapterView.OnItemSelectedListener {
    public static final int REPORT_0 = 0;
    public static final int REPORT_1 = 1;
    public static final int REPORT_2 = 2;
    public static final int REPORT_3 = 3;
    public static final int REPORT_4 = 4;
    public static final int REPORT_5 = 5;
    public static final int REPORT_6 = 6;
    public static final int REPORT_7 = 7;
    public static final int REPORT_8 = 8;
    public static final int REPORT_9 = 9;
    public static final int REPORT_10 = 10;
    public static final int REPORT_11 = 11;
    public static final int REPORT_12 = 12;
    public static final int REPORT_13 = 13;
    public static final int REPORT_14 = 14;
    public static final int REPORT_15 = 15;
    public static final int REPORT_16 = 16; // Current year attendance report
    public static final int REPORT_17 = 17; // attendance students
    public static final int REPORT_18 = 18; // attendance teachers


    public static final int MONTHLY = 1;
    public static final int YEARLY = 2;

    @BindView(R.id.txt_report_title)
    TextView txtTitle;
    @BindView(R.id.txt_report_subtitle)
    TextView txtSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lyt_grade_stream)
    LinearLayout lytGradeStream;
    @BindView(R.id.rpt_1_options)
    LinearLayout  llrpt1Option;
    @BindView(R.id.btn_monthly_report1)
    Button btnMonthyReport1;
    @BindView(R.id.btn_yearly_report1)
    Button btnYearlyReport1;
    @BindView(R.id.spn_1_level)
    Spinner spnGrade;
    @BindView(R.id.spn_1_stream)
    Spinner spnStream;
    @BindView(R.id.fragment_report1)
    FrameLayout frame1;
    @BindView(R.id.fragment_report2)
    FrameLayout frame2;
    @BindView(R.id.fragment_report3)
    FrameLayout frame3;
    @BindView(R.id.ll_buttons_time)
    LinearLayout llbutton;
    @BindView(R.id.spn_1_exam)
    Spinner spnExam;
    @BindView(R.id.spn_1_subject)
    Spinner spnSubjet;
    @BindView(R.id.lyt_second_spinners)
    LinearLayout llSecondSpinner;
    @BindView(R.id.txtGrade)
    TextView txtGrade;
    @BindView(R.id.txtStream)
    TextView txtStream;
    @BindView(R.id.report_12_layout)
    LinearLayout lytReport12;
    @BindView(R.id.lyt_base_report)
    LinearLayout lytBaseReport;
    @BindView(R.id.rpt_option_Shift_Level)
    LinearLayout lyShiftandLevel;
    @BindView(R.id.spn_1_level0)
    Spinner spnLevel0;
    @BindView(R.id.spn_1_shift0)
    Spinner spnShift0;
    @BindView(R.id.ll_report5)
    LinearLayout layoutReport5;
    @BindView(R.id.report5_gauge1)
    FrameLayout gauge1;
    @BindView(R.id.report5_gauge2)
    FrameLayout gauge2;
    @BindView(R.id.report5_gauge3)
    FrameLayout gauge3;
    @BindView(R.id.report5_gauge4)
    FrameLayout gauge4;
    @BindView(R.id.report5_gauge5)
    FrameLayout gauge5;
    @BindView(R.id.report5_gauge6)
    FrameLayout gauge6;
    @BindView(R.id.report5_gauge7)
    FrameLayout gauge7;
    @BindView(R.id.spnMonthSelected)
    Spinner spnTitleMonth;
    @BindView(R.id.spnMonthSelectedText)
    TextView spnTitleMonthText;
    @BindView(R.id.spn_subject_12)
    Spinner spnSubject12;
    @BindView(R.id.btn_information)
    ImageButton btn_information;
    @BindView(R.id.spnYearSelected)
    Spinner spnYear;
    @BindView(R.id.spnYearSelectedText)
    TextView txtYear;

    private FragmentManager fm;
    private ReportElementFragment f1, f2, f3, f4, f5, f6, f7, f0, f8;
    private int currentReport;
    private int currentPeriod;
    private DBUtility conn;
    private List<AttendanceStudent> studentAttendance;
    private List<GradeSex> studentByGradeSex;
    private GradeSex teacherSex;
    private boolean yearly;
    private List<SpinnerItem> grades, stream, subject, exam, studentQuantity, Level, Shift, spYears;
    private JSONArray array;
    private String msjInformation;
    private boolean isFirstTimeLoading;
    Handler handlerData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 4) {
                dialogCarga.dismiss();
//                isFirstTimeLoading = true;
                spnYear.setOnItemSelectedListener(ReportContainerActivity.this);
//                isFirstTimeLoading = false;
                f1.setInformationTable(info.get(0), getString(R.string.title_table1_report4), ReportContainerActivity.this);
                f2.setInformationTable(info.get(1), getString(R.string.title_table2_report4), ReportContainerActivity.this);
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 775));
                frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
                fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
                fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
            }
            if (msg.what == 7) {
                dialogCarga.dismiss();
                //isFirstTimeLoading = true;
                spnGrade.setOnItemSelectedListener(ReportContainerActivity.this);
                spnStream.setOnItemSelectedListener(ReportContainerActivity.this);
                //spnSubject12.setOnItemSelectedListener(ReportContainerActivity.this);
                spnTitleMonth.setOnItemSelectedListener(ReportContainerActivity.this);
                //isFirstTimeLoading = false;
                //f1.setInformationAnyChartLineYRange(getString(R.string.title_grap1_report7) + " " + GradeLabel, getString(R.string.str_g_exam), getString(R.string.rpt_6_average), infoF.get(0), 550, 250, ReportContainerActivity.this);
                f1.setInformationAnyChartLineYRange(getString(R.string.title_grap1_report7) + " " , getString(R.string.str_g_exam), getString(R.string.rpt_6_average), reporte.get(0), 550, 250, ReportContainerActivity.this);
                f2.setInformationAnyChartLineYRange(getString(R.string.title_grap2_report7), getString(R.string.str_g_exam), getString(R.string.rpt_6_average), reporte.get(1), 550, 250, ReportContainerActivity.this);
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
                fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
            }
            if (msg.what == 5){
                dialogCarga.dismiss();
                f1.setInformationAnyChartLinearGauge(getString(R.string.txt_primary), "STR = 1:" + (int) rep5DSTRP, (int) rep5DSTRP, 550, 300, ReportContainerActivity.this);
                f2.setInformationAnyChartLinearGauge(getString(R.string.pp), "STR = 1:" + (int) rep5DSTRPP, (int) rep5DSTRPP, 550, 300, ReportContainerActivity.this);
                //f0.setInformationTable(reporte, "", this);
                f0.setInformationTable(rep5DGraph, "", ReportContainerActivity.this);
                fm.beginTransaction().replace(R.id.report5_gauge1, f1).commit();
                fm.beginTransaction().replace(R.id.report5_gauge2, f2).commit();
                fm.beginTransaction().replace(R.id.report5_legend, f0).commit();
            }
            if (msg.what == 12) {
                dialogCarga.dismiss();
                isFirstTimeLoading = true;
                spnSubject12.setOnItemSelectedListener(ReportContainerActivity.this);
                isFirstTimeLoading = false;

                /*f0.setInformationAnyChartGrayGauge("", ratio, totalbooks == 0 ? 0f : totalStudent / totalbooks, width, height, ReportContainerActivity.this);
                f1.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard1), ratio, Float.parseFloat(books.get(0).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(0).getValue()) / Float.parseFloat(books.get(0).getValue()), width, height, ReportContainerActivity.this);
                f2.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard2), ratio, Float.parseFloat(books.get(1).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(1).getValue()) / Float.parseFloat(books.get(1).getValue()), width, height, ReportContainerActivity.this);
                f3.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard3), ratio, Float.parseFloat(books.get(2).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(2).getValue()) / Float.parseFloat(books.get(2).getValue()), width, height, ReportContainerActivity.this);
                f4.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard4), ratio, Float.parseFloat(books.get(3).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(3).getValue()) / Float.parseFloat(books.get(3).getValue()), width, height, ReportContainerActivity.this);
                f5.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard5), ratio, Float.parseFloat(books.get(4).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(4).getValue()) / Float.parseFloat(books.get(4).getValue()), width, height, ReportContainerActivity.this);
                f6.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard6), ratio, Float.parseFloat(books.get(5).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(5).getValue()) / Float.parseFloat(books.get(5).getValue()), width, height, ReportContainerActivity.this);
                f7.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard7), ratio, Float.parseFloat(books.get(6).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(6).getValue()) / Float.parseFloat(books.get(6).getValue()), width, height, ReportContainerActivity.this);
                */
                lytReport12.setVisibility(View.VISIBLE);
                lytBaseReport.setVisibility(GONE);
                fm.beginTransaction().replace(R.id.fr_rpt12_8, f0).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_0, f1).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_1, f2).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_2, f3).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_3, f4).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_4, f5).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_5, f6).commit();
                fm.beginTransaction().replace(R.id.fr_rpt12_6, f7).commit();
            }
            if (msg.what == 11) {
                dialogCarga.dismiss();
                f1.setInformationTable(info.get(0), "", ReportContainerActivity.this);
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
                fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
            }
        }
    };
    ProgressDialog dialogCarga;
    List<List<TableData>> info;
    List<JSONArray>  reporte;

    int rep5DSTRP, rep5DSTRPP;
    List<TableData> rep5DGraph = new ArrayList<TableData>();

    List<SpinnerItem> books;
    float totalStudent = 0, totalbooks = 0;
    int width = 300;
    int height = 245;
    String ratio = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_container_layout);
        ButterKnife.bind(this);
        conn = new DBUtility(this);
        isFirstTimeLoading = true;
        init();
    }

    public void init() {
        String title, subtitle;
        currentReport = 0;

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getIntent().hasExtra("REPORT_TITLE")) {
            txtTitle.setText(getIntent().getStringExtra("REPORT_TITLE"));
            txtTitle.setVisibility(View.VISIBLE);
        } else {
            txtTitle.setVisibility(GONE);
        }
        if (getIntent().hasExtra("subtitle")) {
            txtSubtitle.setText(getIntent().getStringExtra("subtitle"));
            txtSubtitle.setVisibility(View.VISIBLE);
        } else {
            txtSubtitle.setVisibility(GONE);
        }
        if (getIntent().hasExtra("REPORT")) {
            currentReport = getIntent().getIntExtra("REPORT", 0);
        }
        if (getIntent().hasExtra("REPORT_INFORMATION")) {
            msjInformation = getIntent().getStringExtra("REPORT_INFORMATION");
        }
        fm = getSupportFragmentManager();
        initData();


    }

    public List<SpinnerItem> getShift() {
        List<SpinnerItem> list = new ArrayList<>();
        SpinnerItem elemento;
        elemento = new SpinnerItem();
        elemento.setId(1);
        elemento.setValue(getResources().getString(R.string.str_s_tv14));
        list.add(elemento);
        elemento = new SpinnerItem();
        elemento.setId(2);
        elemento.setValue(getResources().getString(R.string.str_s_tv15));
        list.add(elemento);
        return list;
    }


    public List<SpinnerItem> getLevel() {
        List<SpinnerItem> list = new ArrayList<>();
        SpinnerItem elemento;
        elemento = new SpinnerItem();
        elemento.setId(3);
        elemento.setValue(getResources().getString(R.string.pp));
        list.add(elemento);
        elemento = new SpinnerItem();
        elemento.setId(1);
        elemento.setValue(getResources().getString(R.string.txt_primary));
        list.add(elemento);
        return list;
    }


    public List<SpinnerItem> loadSubject() {//7,8,12,21,9,11,18,10,14,16,15,6,1,2,3,4,5,18
        List<SpinnerItem> subjects = new ArrayList<SpinnerItem>();
        subjects.add(new SpinnerItem(1, getResources().getString(R.string.str_bl3_f_subject)));
        subjects.add(new SpinnerItem(2, getResources().getString(R.string.str_bl3_g_subject)));
        subjects.add(new SpinnerItem(3, getResources().getString(R.string.str_bl3_h_subject)));
        subjects.add(new SpinnerItem(4, getResources().getString(R.string.str_bl3_i_subject)));
        subjects.add(new SpinnerItem(5, getResources().getString(R.string.str_bl3_j_subject)));
        subjects.add(new SpinnerItem(6, getResources().getString(R.string.str_bl3_k_subject)));
        subjects.add(new SpinnerItem(7, getResources().getString(R.string.str_bl3_l_subject)));
        subjects.add(new SpinnerItem(8, getResources().getString(R.string.str_bl3_m_subject)));
        subjects.add(new SpinnerItem(9, getResources().getString(R.string.str_bl3_n_subject)));
        subjects.add(new SpinnerItem(10, getResources().getString(R.string.str_bl3_o_subject)));
        subjects.add(new SpinnerItem(11, getResources().getString(R.string.str_bl3_p_subject)));
        subjects.add(new SpinnerItem(12, getResources().getString(R.string.str_bl3_p1_subject)));
        subjects.add(new SpinnerItem(13, getResources().getString(R.string.str_bl3_p2_subject)));
        subjects.add(new SpinnerItem(14, getResources().getString(R.string.str_bl3_p3_subject)));
        subjects.add(new SpinnerItem(15, getResources().getString(R.string.str_bl3_p4_subject)));
        subjects.add(new SpinnerItem(16, getResources().getString(R.string.str_bl3_p5_subject)));
        subjects.add(new SpinnerItem(17, getResources().getString(R.string.str_bl3_p6_subject)));
        return subjects;
    }

    public List<String> loadGrade() {
        List<String> grades = new ArrayList<String>();
        ;
        grades.add(getResources().getString(R.string.txt_standard1));
        grades.add(getResources().getString(R.string.txt_standard2));
        grades.add(getResources().getString(R.string.txt_standard3));
        grades.add(getResources().getString(R.string.txt_standard4));
        grades.add(getResources().getString(R.string.txt_standard5));
        grades.add(getResources().getString(R.string.txt_standard6));
        grades.add(getResources().getString(R.string.txt_standard7));
        return grades;
    }

    public List<String> booksGrade(int subjectID, int year) {
        List<String> bG = new ArrayList<String>();
        List<String> grades = loadGrade();
        String subject = conn.getStudents11(Integer.valueOf(subjectID), year);
        int currGradeCount = 1;
        List<SpinnerItem> books = getPrimaryBooksArray(((SpinnerItem) spnGrade.getSelectedItem()));// new ArrayList<SpinnerItem>();
        for (int i = 0; i < grades.size(); i++) {

            String bookVal = books.get(i).getValue();
            String Students = conn.getStudents11(currGradeCount, year);
            String divOp = "0";
            try {
                if (!bookVal.equals("0")) {
                    double d1 = Double.valueOf(Students);
                    double d2 = Double.valueOf(bookVal);
                    double total = d1 / d2;
                    divOp = String.valueOf(total);
                }
            } catch (Exception ex) {
                divOp = "0";
            }
            //[subject][grade][bookValue][Students][Students/bookValue]
            bG.add(subject + "|" + grades.get(i) + "|" + bookVal + "|" + Students + "|" + String.valueOf(divOp));
            currGradeCount++;
        }
        return bG;
    }

    private List<String> getStringListMonth() {
        List<String> lista = null;
        SimpleDateFormat df = new SimpleDateFormat("MMMM", conn.getCurrentLocale());
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DAY_OF_MONTH, 1);
        int month = Calendar.getInstance().get(MONTH);
        lista = new ArrayList<String>();
        for (int i = 0; i <= month; i++) {
            time.set(Calendar.MONTH, i);
            lista.add(df.format(time.getTime()));
        }
        return lista;
    }

    private List<String> getStringListMonthAll() {
        List<String> lista = null;
        SimpleDateFormat df = new SimpleDateFormat("MMMM", conn.getCurrentLocale());
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DAY_OF_MONTH, 1);
        int month = 11;//Calendar.getInstance().get(MONTH);
        lista = new ArrayList<String>();
        for (int i = 0; i <= month; i++) {
            time.set(Calendar.MONTH, i);
            lista.add(df.format(time.getTime()));
        }
        return lista;
    }

    public void initData() {
        spnYear.setOnItemSelectedListener(this);//yyyy  // TRASLADADO DE enableYearDisplay();
        switch (currentReport) {
            case REPORT_1:
                enableYearDisplay();

                SimpleDateFormat df = new SimpleDateFormat("MMMM-yyyy", conn.getCurrentLocale());
                layoutReport5.setVisibility(GONE);
                yearly = false;
                lyShiftandLevel.setVisibility(View.VISIBLE);
                Level = getLevel();
                Shift = getShift();
                spnLevel0.setAdapter(new SpinnerItemAdapter(this, Level));
                spnShift0.setAdapter(new SpinnerItemAdapter(this, Shift));
                spnLevel0.setOnItemSelectedListener(this);   // **** DOS LINNEAS 2190417
                spnShift0.setOnItemSelectedListener(this);
                frame3.setVisibility(GONE);
                frame2.setVisibility(GONE);
                grades = conn.getPrimaryGrades(((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                stream = conn.getStream();
                spnGrade.setAdapter(new SpinnerItemAdapter(this, grades));
                spnStream.setAdapter(new SpinnerItemAdapter(this, stream));
                spnTitleMonth.setAdapter(new ArrayAdapter<String>(this, R.layout.my_simple_list_one_item, getStringListMonthAll()));
                spnTitleMonth.setSelection(Calendar.getInstance().get(MONTH), false);
                spnTitleMonth.setOnItemSelectedListener(this);  // **** TRES LINNEAS 2190417
                spnGrade.setOnItemSelectedListener(this);
                spnStream.setOnItemSelectedListener(this);
                llrpt1Option.setVisibility(View.VISIBLE);
                //TODO: Cambiar tamaño fijo
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
                drawFragmentsReport1();
                break;
            case REPORT_2:
                enableYearDisplay();
                llbutton.setVisibility(GONE);
                layoutReport5.setVisibility(GONE);
                spnTitleMonth.setVisibility(GONE);
                Level = getLevel();
                Shift = getShift();
                spnLevel0.setAdapter(new SpinnerItemAdapter(this, Level));
                spnShift0.setAdapter(new SpinnerItemAdapter(this, Shift));
                spnShift0.setOnItemSelectedListener(this); // **** DOS LINNEAS 2190417
                spnLevel0.setOnItemSelectedListener(this);
                yearly = false;
                grades = conn.getPrimaryGrades(((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                stream = conn.getStream();
                spnGrade.setAdapter(new SpinnerItemAdapter(this, grades));
                spnStream.setAdapter(new SpinnerItemAdapter(this, stream));
                spnGrade.setOnItemSelectedListener(this);   // **** DOS LINNEAS 2190417
                spnStream.setOnItemSelectedListener(this);
                lyShiftandLevel.setVisibility(View.VISIBLE);
                drawFragmentsReport2();
                break;
            case REPORT_3:
                layoutReport5.setVisibility(GONE);
                yearly = false;
                llrpt1Option.setVisibility(GONE);
                drawFragmentsReport3();
                break;
            case REPORT_4:
                enableYearDisplay();
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);

                //layoutReport5.setVisibility(GONE);
                //frame3.setVisibility(GONE);
                //llrpt1Option.setVisibility(GONE);
                yearly = false;
                //drawFragmentsReport4();
                break;
            case REPORT_5:
                llrpt1Option.setVisibility(GONE);
                frame1.setVisibility(GONE);
                frame2.setVisibility(GONE);
                frame3.setVisibility(GONE);
                layoutReport5.setVisibility(View.VISIBLE);
                drawFragmentsReport5();
                break;
            case REPORT_6:
                enableYearDisplay();
                llbutton.setVisibility(GONE);
                layoutReport5.setVisibility(GONE);
                spnTitleMonth.setVisibility(GONE);
                llSecondSpinner.setVisibility(View.VISIBLE);
                spnTitleMonthText.setVisibility(View.VISIBLE);
                yearly = false;
                grades = conn.getPrimaryGrades();
                stream = conn.getStream();
                exam = getExamTitle();
                spnGrade.setAdapter(new SpinnerItemAdapter(this, grades));
                spnStream.setAdapter(new SpinnerItemAdapter(this, stream));
                spnExam.setAdapter(new SpinnerItemAdapter(this, exam));
                subject = conn.getSubjectByGrade(((SpinnerItem) spnGrade.getSelectedItem()).getId());
                spnSubjet.setAdapter(new SpinnerItemAdapter(this, subject));
                spnGrade.setOnItemSelectedListener(this);   // **** CUATRO LINNEAS 2190417
                spnStream.setOnItemSelectedListener(this);
                spnExam.setOnItemSelectedListener(this);
                spnSubjet.setOnItemSelectedListener(this);
                //TODO cambiar tamaño fijo
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
                drawFragmentsReport6();
                break;
            case REPORT_7:
                spnGrade.setVisibility(View.VISIBLE);
                spnStream.setVisibility(View.VISIBLE);
                enableYearDisplay();
                yearly = false;
                grades = conn.getPrimaryGrades();
                stream = conn.getStream();
                spnGrade.setAdapter(new SpinnerItemAdapter(this, grades));
                spnStream.setAdapter(new SpinnerItemAdapter(this, stream));
                subject = conn.getSubjectByGrade(((SpinnerItem) spnGrade.getSelectedItem()).getId());
                spnSubjet.setAdapter(new SpinnerItemAdapter(this, subject));

                //spnTitleMonth.setAdapter(new SpinnerItemAdapter(this, loadSubject()));
                spnTitleMonth.setAdapter(new SpinnerItemAdapter(this, subject));
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.VISIBLE);
                spnTitleMonthText.setVisibility(View.VISIBLE);
                drawFragmentsReport7();
                break;
            case REPORT_8:
                enableYearDisplay();
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
                frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
                frame3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                //TODO colocar un listener para saber cuando ya se refresco la WEB
                drawFragmentsReport8();
                break;
            case REPORT_9:
                enableYearDisplay();
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                frame3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                //TODO colocar un listener para saber cuando ya se refresco la WEB
                drawFragmentsReport9();
                break;
            case REPORT_10:
                enableYearDisplay();
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                //TODO colocar un listener para saber cuando ya se refresco la WEB
                drawFragmentsReport10();
                break;
            case REPORT_11:
                enableYearDisplay();

                spnYear.setVisibility(View.GONE);//yyyy
                txtYear.setVisibility(View.GONE);//yyyy


                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);

                /*llbutton.setVisibility(GONE);
                layoutReport5.setVisibility(GONE);
                spnTitleMonth.setVisibility(GONE);
                spnStream.setVisibility(GONE);
                txtStream.setVisibility(GONE);*/
                txtGrade.setText(getResources().getString(R.string.str_g_subject));
                yearly = false;
                spnGrade.setAdapter(new SpinnerItemAdapter(this, loadSubject()));
                spnGrade.setOnItemSelectedListener(this);   // **** DOS LINNEAS 2190417
                drawFragmentsReport11();
                break;
            case REPORT_12:
                enableYearDisplay();

                spnYear.setVisibility(View.GONE);//yyyy
                txtYear.setVisibility(View.GONE);//yyyy

                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);

                /*layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(GONE);*/
                txtGrade.setText(getResources().getString(R.string.str_g_subject));
                int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
                studentQuantity = getPrimaryStudentQuantity(year);
                spnSubject12.setAdapter(new SpinnerItemAdapter(this, getSubjectTitle()));
                 //spnSubject12.setOnItemSelectedListener(this);  // *********** 20190417
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900));
                drawFragmentsReport12();
                break;
            case REPORT_13:
                enableYearDisplay();
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);

                /*layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(GONE);*/
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
                drawFragmentsReport13();
                break;
            case REPORT_14:
                enableYearDisplay();
                layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(View.VISIBLE);
                btnMonthyReport1.setVisibility(GONE);
                btnYearlyReport1.setVisibility(GONE);
                spnTitleMonth.setVisibility(View.GONE);
                spnTitleMonthText.setVisibility(View.GONE);
                txtGrade.setVisibility(View.GONE);
                txtStream.setVisibility(View.GONE);
                spnGrade.setVisibility(View.GONE);
                spnStream.setVisibility(View.GONE);

                /*layoutReport5.setVisibility(GONE);
                llrpt1Option.setVisibility(GONE);*/
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
                drawFragmentsReport14();
                break;
            case REPORT_15:
                llrpt1Option.setVisibility(View.GONE);
                layoutReport5.setVisibility(GONE);
                drawFragmentsReport15();
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                frame3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                break;
            case REPORT_16:
                enableYearDisplay();

                df = new SimpleDateFormat("MMMM-yyyy", conn.getCurrentLocale());
                layoutReport5.setVisibility(GONE);
                yearly = false;
                frame3.setVisibility(GONE);
                frame2.setVisibility(GONE);
                lytGradeStream.setVisibility(GONE);
                spnTitleMonth.setAdapter(new ArrayAdapter<String>(this, R.layout.my_simple_list_one_item, getStringListMonthAll()));
                spnTitleMonth.setSelection(Calendar.getInstance().get(MONTH), false);
                spnTitleMonth.setOnItemSelectedListener(this);   // **** DOS LINNEAS 2190417
                llrpt1Option.setVisibility(View.VISIBLE);
                //TODO: Cambiar tamaño fijo
                frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
                drawFragmentsReport16();
                break;
            case REPORT_17:
                drawFragmentsReport17();
                break;

            case REPORT_18:
                drawFragmentsReport18();
                break;

            default:
                llrpt1Option.setVisibility(View.GONE);
                layoutReport5.setVisibility(GONE);
                break;
        }
    }

    private void enableYearDisplay() {
        spnYear.setVisibility(View.VISIBLE);//yyyy
        txtYear.setVisibility(View.VISIBLE);//yyyy
        spYears = conn.getYears();//yyyy
        //spnYear.setOnItemSelectedListener(this);//yyyy  // TRASLADADO AL PRINCIPIO DE INITDATA()
        spnYear.setAdapter(new SpinnerItemAdapter(this, spYears));//yyyy
    }

    private void drawFragmentsReport17() {
    }

    private void report17Data() {
    }

    private void drawFragmentsReport18() {
    }

    private void report18Data() {
    }

    public void drawFragmentsReport3() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        f3 = new ReportElementFragment();
        report3Data();
        //TODO:Cambiar tamaño constante
        frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 640));
        frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 280));
        frame3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
        fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
        fm.beginTransaction().replace(R.id.fragment_report3, f3).commit();
    }

    public void drawFragmentsReport1() {
        f1 = new ReportElementFragment();
        fm.executePendingTransactions();
        report1TableData();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
    }

    public void drawFragmentsReport2() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        f3 = new ReportElementFragment();
        report2Data();
        frame1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        frame2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = 60;
        frame3.setLayoutParams(lp);
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
        fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
        fm.beginTransaction().replace(R.id.fragment_report3, f3).commit();
    }

    public void drawFragmentsReport4() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        dialogCarga = new ProgressDialog(this);
        dialogCarga.setMessage(getResources().getString(R.string.attendance_rep_msg));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                 info = report4Data();
                handlerData.sendEmptyMessage(4);
            }
        }.start();
    }

    public void drawFragmentsReport7() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        dialogCarga = new ProgressDialog(this);
        dialogCarga.setMessage(getResources().getString(R.string.attendance_rep_msg));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        /*new Thread() {
            @Override
            public void run() {
                super.run();
                 report7Data();
                handlerData.sendEmptyMessage(7);
            }
        }.start();*/
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                report7Data();
                handlerData.sendEmptyMessage(7);
            }
        };
        if (t1.isAlive() || t1.isDaemon()){
            t1.stop();
            t1.interrupt();
        }
        else{
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void drawFragmentsReport5() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        f0 = new ReportElementFragment();
        dialogCarga = new ProgressDialog(this);
        dialogCarga.setMessage(getResources().getString(R.string.attendance_rep_msg));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                report5Data();
                handlerData.sendEmptyMessage(5);
            }
        }.start();
    }

    private void report7Data() {
        String year = String.valueOf(((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        String Grade = String.valueOf(((SpinnerItem) spnGrade.getSelectedItem()).getId());
        String GradeLabel = String.valueOf(((SpinnerItem) spnGrade.getSelectedItem()).getValue());
        String Stream = String.valueOf((((SpinnerItem) spnStream.getSelectedItem()).getId()));
        String SubjectID = String.valueOf(((SpinnerItem) spnTitleMonth.getSelectedItem()).getId());
        String SubjectVal = String.valueOf(((SpinnerItem) spnTitleMonth.getSelectedItem()).getValue());
        List<Float> monthAvgByGrade = new ArrayList<>();
        List<Float> monthAvgByGradeStream = new ArrayList<>();
        List<Float> monthAvgByStrem = new ArrayList<>();
        reporte= new ArrayList<JSONArray>();
        JSONArray row = new JSONArray();
        for (int i = 1; i < 13; i++)//months 1-12
            monthAvgByGrade.add(conn.getAverrageGradeRep7ByGrade(year, String.valueOf(i), Grade, Stream, SubjectID));
        Calendar calen = Calendar.getInstance();
        JSONArray temp = null;
        array = new JSONArray();
        SimpleDateFormat df = new SimpleDateFormat("MMM", conn.getCurrentLocale());
        float averageMonth;
        int currentMonth = 12;//Calendar.getInstance().get(MONTH);

        for (int i = 1; i <= currentMonth; i++) {
            averageMonth = monthAvgByGrade.get(i - 1);
            try {
                temp = new JSONArray();
                calen.set(Calendar.DAY_OF_MONTH, 1);
                calen.set(MONTH, i - 1);
                temp.put(df.format(calen.getTime()));
                temp.put(averageMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (temp != null) {
                array.put(temp);
            }
        }
        reporte.add(array);
        //f1.setInformationAnyChartLineYRange(getString(R.string.title_grap1_report7) + " " + GradeLabel, getString(R.string.str_g_exam), getString(R.string.rpt_6_average), array, 550, 250, this);
        for (int i = 1; i < 13; i++)//months 1-12
            monthAvgByGradeStream.add(conn.getAverrageGradeRep7ByStream(year, String.valueOf(i), Grade, Stream, SubjectID));
        Calendar calen1 = Calendar.getInstance();
        JSONArray temp1 = null;
        array = new JSONArray();
        SimpleDateFormat df1 = new SimpleDateFormat("MMM", conn.getCurrentLocale());
        float averageMonth1;
        int currentMonth1 = Calendar.getInstance().get(MONTH);
        for (int i = 1; i <= currentMonth; i++) {
            averageMonth = monthAvgByGradeStream.get(i - 1);
            try {
                temp = new JSONArray();
                calen.set(Calendar.DAY_OF_MONTH, 1);
                calen.set(MONTH, i - 1);
                temp.put(df.format(calen.getTime()));
                temp.put(averageMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (temp != null) {
                array.put(temp);
            }
        }
        reporte.add(array);
        //f2.setInformationAnyChartLineYRange(getString(R.string.title_grap2_report7), getString(R.string.str_g_exam), getString(R.string.rpt_6_average), array, 550, 250, this);
    }

    public void drawFragmentsReport6() {
        f1 = new ReportElementFragment();
        report6Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
    }

    public void drawFragmentsReport11() {
        f1 = new ReportElementFragment();
        dialogCarga = new ProgressDialog(this);
        dialogCarga.setMessage(getResources().getString(R.string.attendance_rep_msg));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                info = report11Data();
                handlerData.sendEmptyMessage(11);
            }
        }.start();
    }

    public void drawFragmentsReport8() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        report8Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
        fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
    }

    public void drawFragmentsReport10() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        report10Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
        fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
    }

    public void drawFragmentsReport9() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        f3 = new ReportElementFragment();
        report9Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
        fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
        fm.beginTransaction().replace(R.id.fragment_report3, f3).commit();
    }

    private void drawFragmentsReport12() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        f3 = new ReportElementFragment();
        f4 = new ReportElementFragment();
        f5 = new ReportElementFragment();
        f6 = new ReportElementFragment();
        f7 = new ReportElementFragment();
        f0 = new ReportElementFragment();

        dialogCarga = new ProgressDialog(this);
        dialogCarga.setMessage(getResources().getString(R.string.attendance_rep_msg));
        dialogCarga.setCancelable(false);
        dialogCarga.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                report12Data();
                handlerData.sendEmptyMessage(12);
            }
        }.start();
//        lytReport12.setVisibility(View.VISIBLE);
//        lytBaseReport.setVisibility(GONE);
//        fm.beginTransaction().replace(R.id.fr_rpt12_8, f0).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_0, f1).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_1, f2).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_2, f3).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_3, f4).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_4, f5).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_5, f6).commit();
//        fm.beginTransaction().replace(R.id.fr_rpt12_6, f7).commit();
    }

    private void drawFragmentsReport13() {
        f1 = new ReportElementFragment();
        report13Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
    }

    public void drawFragmentsReport14() {
        f1 = new ReportElementFragment();
        report14Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
    }

    public void drawFragmentsReport15() {
        f1 = new ReportElementFragment();
        f2 = new ReportElementFragment();
        f3 = new ReportElementFragment();
        report15Data();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
        fm.beginTransaction().replace(R.id.fragment_report2, f2).commit();
        fm.beginTransaction().replace(R.id.fragment_report3, f3).commit();
    }

    public void drawFragmentsReport16() {
        f1 = new ReportElementFragment();
        fm.executePendingTransactions();
        report16TableData();
        fm.beginTransaction().replace(R.id.fragment_report1, f1).commit();
    }


    public void report1TableData() {
        List<TableData> reporte = new ArrayList<TableData>();
        TableData row = new TableData();
        List<TableData.TableDataItem> listTable = new ArrayList<TableData.TableDataItem>();
        TableData.TableDataItem item = new TableData.TableDataItem(getString(R.string.table_1_report1_name), Color.GRAY, Color.WHITE, 15, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.table_1_report1_average), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_sick), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_excused), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_unexcused), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(5);
        row.setRow(listTable);
        reporte.add(row);

        String year = String.valueOf(((SpinnerItem) spnYear.getSelectedItem()).getId());

        if (Calendar.getInstance().get(MONTH) != 0) {
            studentAttendance = conn.getAttendanceMonth(spnTitleMonth.getSelectedItemPosition(), spnGrade.getSelectedItem() != null ? ((SpinnerItem) spnGrade.getSelectedItem()).getId() : grades.get(0).getId(),
                    spnStream.getSelectedItem() != null ? ((SpinnerItem) spnStream.getSelectedItem()).getId() : stream.get(0).getId(),
                    currentPeriod == YEARLY, ((SpinnerItem) spnShift0.getSelectedItem()).getId(), ((SpinnerItem) spnLevel0.getSelectedItem()).getId(), year);
            if (studentAttendance != null) {
                for (int i = 0; i < studentAttendance.size(); i++) {
                    row = new TableData();
                    listTable = new ArrayList<TableData.TableDataItem>();
                    item = new TableData.TableDataItem(studentAttendance.get(i).getName(), Color.parseColor("#EEEEEE"), Color.BLACK, 15, 3, Gravity.LEFT);
                    listTable.add(item);
                    if ((((float) studentAttendance.get(i).getAttendedTime() / studentAttendance.get(i).getTotalTime() * 100)) > 100) {
                        item = new TableData.TableDataItem(String.format("%.2f", ((float) 1 / 1 * 100)) + "%", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    } else {
                        item = new TableData.TableDataItem(String.format("%.2f", ((float) studentAttendance.get(i).getAttendedTime() / studentAttendance.get(i).getTotalTime() * 100)) + "%", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    }
                    listTable.add(item);
                    item = new TableData.TableDataItem(studentAttendance.get(i).getReason1() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    listTable.add(item);
                    item = new TableData.TableDataItem(studentAttendance.get(i).getReason2() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    listTable.add(item);
                    item = new TableData.TableDataItem(studentAttendance.get(i).getReason3() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    listTable.add(item);
                    row.setSize(5);
                    row.setRow(listTable);
                    reporte.add(row);
                }
            }
        }
        f1.setInformationTable(reporte, "", this);
    }

    public void report2Data() {
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());
        int ancho = 600;
        int alto = 800;
        Calendar calen = Calendar.getInstance();
        JSONArray temp = null;
        array = new JSONArray();
        SimpleDateFormat df = new SimpleDateFormat("MMM", conn.getCurrentLocale());
        float averageDays;
        int monthDays;
        int currentMonth = Calendar.getInstance().get(MONTH);
        if (currentMonth > 0) {
            for (int i = 0; i <= currentMonth; i++) {
                averageDays = conn.getAveragePrimaryAttendace(i, year, -1, -1,
                        ((SpinnerItem) spnShift0.getSelectedItem()).getId(), ((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                monthDays = conn.getDaysOfMonth(i + 1);
                try {
                    temp = new JSONArray();
                    calen.set(Calendar.DAY_OF_MONTH, 1);
                    calen.set(MONTH, i);
                    temp.put(df.format(calen.getTime()));
                    temp.put(averageDays / monthDays * 100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (temp != null) {
                    array.put(temp);
                }
            }
        }
        f1.setInformationAnyChartLineYRange(getString(R.string.title_grap1_report2), "Months", getString(R.string.str_attendance_axis), array, ancho, alto, this);
        array = new JSONArray();
        if (currentMonth > 0) {
            for (int i = 0; i <= currentMonth; i++) {
                averageDays = conn.getAveragePrimaryAttendace(i, year, ((SpinnerItem) spnGrade.getSelectedItem()).getId(), -1,
                        ((SpinnerItem) spnShift0.getSelectedItem()).getId(), ((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                monthDays = conn.getDaysOfMonth(i + 1);
                try {
                    temp = new JSONArray();
                    calen.set(Calendar.DAY_OF_MONTH, 1);
                    calen.set(MONTH, i);
                    temp.put(df.format(calen.getTime()));
                    temp.put((averageDays / monthDays) * 100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (temp != null) {
                    array.put(temp);
                }
            }
        }
        f2.setInformationAnyChartLineYRange(getString(R.string.title_grap2_report2), "Months", getString(R.string.str_attendance_axis), array, ancho, alto, this);
        array = new JSONArray();
        if (currentMonth > 0) {
            for (int i = 0; i <= currentMonth; i++) {
                averageDays = conn.getAveragePrimaryAttendace(i, year, ((SpinnerItem) spnGrade.getSelectedItem()).getId(), ((SpinnerItem) spnStream.getSelectedItem()).getId(),
                        ((SpinnerItem) spnShift0.getSelectedItem()).getId(), ((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                monthDays = conn.getDaysOfMonth(i + 1);
                try {
                    temp = new JSONArray();
                    calen.set(Calendar.DAY_OF_MONTH, 1);
                    calen.set(MONTH, i);
                    temp.put(df.format(calen.getTime()));
                    temp.put((averageDays / monthDays) * 100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (temp != null) {
                    array.put(temp);
                }
            }
        }
        f3.setInformationAnyChartLineYRange(getString(R.string.title_grap3_report2), "Months", getString(R.string.str_attendance_axis), array, ancho, alto, this);
    }


    public void report3Data() {
        int solid = 0, semiSolid = 0, makeshift = 0, partitioned = 0, openAir = 0, other = 0, total = 0, indicadorTable2 = 0, indicatorGauge = 67;

        List<TableData> reporte = new ArrayList<TableData>();
        TableData row = new TableData();
        List<TableData.TableDataItem> listTable = new ArrayList<TableData.TableDataItem>();
        TableData.TableDataItem item = new TableData.TableDataItem(getString(R.string.table_1_report3_classroom_type), Color.GRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_total), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_solid), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
        listTable.add(item);
        solid = Integer.parseInt(conn.getValueFromTable("g1a", "g", "0"));
        item = new TableData.TableDataItem(solid + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_semisolid), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
        listTable.add(item);
        semiSolid = Integer.parseInt(conn.getValueFromTable("g1b", "g", "0"));
        item = new TableData.TableDataItem(semiSolid + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_makeshift), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
        listTable.add(item);
        makeshift = Integer.parseInt(conn.getValueFromTable("g1c", "g", "0"));
        item = new TableData.TableDataItem(makeshift + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_partitioned), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
        listTable.add(item);
        partitioned = Integer.parseInt(conn.getValueFromTable("g1d", "g", "0"));
        item = new TableData.TableDataItem(partitioned + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_openair), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
        listTable.add(item);
        openAir = Integer.parseInt(conn.getValueFromTable("g1e", "g", "0"));
        item = new TableData.TableDataItem(openAir + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);


        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_other), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
        listTable.add(item);
        other = Integer.parseInt(conn.getValueFromTable("g1f", "g", "0"));
        item = new TableData.TableDataItem(other + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_1_report3_total), Color.GRAY, Color.WHITE, 23, 3, Gravity.LEFT);
        listTable.add(item);
        total = solid + semiSolid + makeshift + partitioned + openAir + other;
        item = new TableData.TableDataItem(total + "", Color.LTGRAY, Color.BLACK, 23, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem("", Color.WHITE, Color.WHITE, 20, 3, Gravity.LEFT);
        listTable.add(item);
        total = solid + semiSolid + makeshift + partitioned + openAir + other;
        item = new TableData.TableDataItem("", Color.WHITE, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        indicadorTable2 = conn.getCurrentPrimaryStudentEnrrolment();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.table_2_report3_title), Color.GRAY, Color.WHITE, 23, 3, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(indicadorTable2 + "", Color.LTGRAY, Color.BLACK, 23, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);

        f1.setInformationTable(reporte, "", this);
        f2.setInformationAnyChartLinearGauge("<b>" + getString(R.string.gauge_title_report3) + "</b>", "SCR = 1:" + ((total == 0) ? 0 : (int) indicadorTable2 / total), total == 0 ? 0 : ((int) indicadorTable2 / total), 590, 300, this);
        //f2.setInformationAnyChartLinearGauge( "<b>" + getString(R.string.gauge_title_report3) + "</b>", "SCR = 1:" + ((total ==0)?String.format("%.1f",0.0f):String.format("%.1f",((float)indicadorTable2 / (float)total))) , total ==0?0:((int)indicadorTable2 / total),590, 300, this);

        reporte = new ArrayList<TableData>();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem("", Color.WHITE, Color.BLACK, 10, 2, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.parseColor("#FFCC66"), Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.legend_linear_gauge_yellow), Color.WHITE, Color.BLACK, 14, 4, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.parseColor("#50C878"), Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.legend_linear_gauge_green), Color.WHITE, Color.BLACK, 14, 2, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.parseColor("#FF6961"), Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.legend_linear_gauge_red), Color.WHITE, Color.BLACK, 14, 4, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.WHITE, Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        row.setSize(8);
        row.setRow(listTable);
        reporte.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem("", Color.WHITE, Color.WHITE, 20, 3, Gravity.LEFT);
        listTable.add(item);
        row.setSize(1);
        row.setRow(listTable);
        reporte.add(row);
        f3.setInformationTable(reporte, "", this);
    }

    public List<List<TableData>> report4Data() {
        String year = String.valueOf(((SpinnerItem) spnYear.getSelectedItem()).getId());
        int totalWoman = 0, totalMan = 0, totalAll = 0;
        studentByGradeSex = conn.getStudentByGradeBySexLevel("1", year);
        List<GradeSex> studentByGradeSex3 = conn.getStudentByGradeBySexLevel("3", year);
        List<List<TableData>> listRep = new ArrayList<List<TableData>>();
        GradeSex teacherSex = null;
        List<TableData> reporte = new ArrayList<TableData>();
        TableData row = new TableData();
        List<TableData.TableDataItem> listTable = new ArrayList<TableData.TableDataItem>();
        TableData.TableDataItem item = new TableData.TableDataItem(getString(R.string.title_table1_report4_grade), Color.GRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table1_report4_girls), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table1_report4_boy), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table1_report4_total), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);
//PRIMARY
        if (studentByGradeSex != null) {
            for (int i = 0; i < studentByGradeSex.size(); i++) {
                row = new TableData();
                totalWoman = totalWoman + studentByGradeSex.get(i).getWomen();
                totalMan = totalMan + studentByGradeSex.get(i).getMen();
                listTable = new ArrayList<TableData.TableDataItem>();
                item = new TableData.TableDataItem(studentByGradeSex.get(i).getGrade(), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
                listTable.add(item);
                item = new TableData.TableDataItem(studentByGradeSex.get(i).getWomen() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
                listTable.add(item);
                item = new TableData.TableDataItem(studentByGradeSex.get(i).getMen() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
                listTable.add(item);
                item = new TableData.TableDataItem(studentByGradeSex.get(i).getTotal() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
                listTable.add(item);
                row.setSize(4);
                row.setRow(listTable);
                reporte.add(row);
            }
            totalAll = totalAll + totalMan + totalWoman;
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getResources().getString(R.string.title_table1_report4_total), Color.GRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(totalWoman + "", Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(totalMan + "", Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(totalAll + "", Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
            listTable.add(item);
            row.setSize(4);
            row.setRow(listTable);
            reporte.add(row);
        }
//PRE-PRIMARY
        if (studentByGradeSex3 != null) {
            totalAll = 0;
            totalMan = 0;
            totalWoman = 0;
            for (int i = 0; i < studentByGradeSex3.size(); i++) {
                row = new TableData();
                totalWoman = totalWoman + studentByGradeSex3.get(i).getWomen();
                totalMan = totalMan + studentByGradeSex3.get(i).getMen();
                listTable = new ArrayList<TableData.TableDataItem>();
                item = new TableData.TableDataItem(studentByGradeSex3.get(i).getGrade(), Color.parseColor("#EEEEEE"), Color.BLACK, 20, 3, Gravity.LEFT);
                listTable.add(item);
                item = new TableData.TableDataItem(studentByGradeSex3.get(i).getWomen() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
                listTable.add(item);
                item = new TableData.TableDataItem(studentByGradeSex3.get(i).getMen() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
                listTable.add(item);
                item = new TableData.TableDataItem(studentByGradeSex3.get(i).getTotal() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
                listTable.add(item);
                row.setSize(4);
                row.setRow(listTable);
                reporte.add(row);
            }
            totalAll = totalAll + totalMan + totalWoman;
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getResources().getString(R.string.title_table1_report4_total), Color.GRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(totalWoman + "", Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(totalMan + "", Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(totalAll + "", Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
            listTable.add(item);
            row.setSize(4);
            row.setRow(listTable);
            reporte.add(row);
        }
        listRep.add(reporte);


        reporte = new ArrayList<TableData>();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.title_table2_report4_female), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table2_report4_male), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table1_report4_total), Color.GRAY, Color.WHITE, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(3);
        row.setRow(listTable);
        reporte.add(row);
        teacherSex = conn.getTeacherBySex();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(teacherSex.getWomen() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(teacherSex.getMen() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(teacherSex.getTotal() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 20, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(3);
        row.setRow(listTable);
        reporte.add(row);

        listRep.add(reporte);
        return listRep;
    }

    public void report5Data() {
        float studentTeacherRatio = conn.getStudentTeacherRatio(0),
                studentTeacherRatioPrePrimary = conn.getStudentTeacherRatioPrePrimary(0);

        rep5DSTRP = (int) studentTeacherRatio;
        rep5DSTRPP = (int) studentTeacherRatioPrePrimary;

//        f1.setInformationAnyChartLinearGauge(getString(R.string.txt_primary), "STR = 1:" + (int) studentTeacherRatio, (int) studentTeacherRatio, 550, 300, this);
//        f2.setInformationAnyChartLinearGauge(getString(R.string.pp), "STR = 1:" + (int) studentTeacherRatioPrePrimary, (int) studentTeacherRatioPrePrimary, 550, 300, this);
        List<TableData> reporte = null;
        TableData row = null;
        List<TableData.TableDataItem> listTable = null;
        TableData.TableDataItem item = null;
        reporte = new ArrayList<TableData>();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem("", Color.WHITE, Color.BLACK, 10, 2, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.parseColor("#FFCC66"), Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.legend_linear_gauge_yellow), Color.WHITE, Color.BLACK, 14, 4, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.parseColor("#50C878"), Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.legend_linear_gauge_green), Color.WHITE, Color.BLACK, 14, 2, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.parseColor("#FF6961"), Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.legend_linear_gauge_red), Color.WHITE, Color.BLACK, 14, 4, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem("", Color.WHITE, Color.BLACK, 10, 1, Gravity.LEFT);
        listTable.add(item);
        row.setSize(8);
        row.setRow(listTable);
        reporte.add(row);
        rep5DGraph.add(row);

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem("", Color.WHITE, Color.WHITE, 20, 3, Gravity.LEFT);
        listTable.add(item);
        row.setSize(1);
        row.setRow(listTable);
        reporte.add(row);

        rep5DGraph.add(row);
//        f0.setInformationTable(reporte, "", this);
    }

    public void report6Data() {
        float avgScore = 0;
        int studentCount = 0;
        List<TableData> reporte = null;
        List<TableData.TableDataItem> listTable = null;
        TableData row;
        TableData.TableDataItem item;

        Cursor c = conn.getEvaluationByGrade(((SpinnerItem) spnGrade.getSelectedItem()).getId(),
                ((SpinnerItem) spnStream.getSelectedItem()).getId(),
                ((SpinnerItem) spnExam.getSelectedItem()).getId(),
                ((SpinnerItem) spnSubjet.getSelectedItem()).getId(),
                ((SpinnerItem) spnYear.getSelectedItem()).getId());
        reporte = new ArrayList<TableData>();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_6_name), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_6_score), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                row = new TableData();
                listTable = new ArrayList<TableData.TableDataItem>();
                item = new TableData.TableDataItem(c.getString(c.getColumnIndex("name")), Color.GRAY, Color.BLACK, 20, 5, Gravity.LEFT);
                listTable.add(item);
                item = new TableData.TableDataItem(c.getInt(c.getColumnIndex("grade")) + "", Color.LTGRAY, Color.BLACK, 20, 3, Gravity.CENTER);
                avgScore = avgScore + c.getInt(c.getColumnIndex("grade"));
                studentCount++;
                listTable.add(item);
                row.setSize(2);
                row.setRow(listTable);
                reporte.add(row);
                c.moveToNext();
            }
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_6_average), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(String.format("%.2f", avgScore / studentCount), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            row.setSize(2);
            row.setRow(listTable);
            reporte.add(row);
            c.close();
        }
        f1.setInformationTable(reporte, "", this);
    }

    private void report10Data() {
        float total = 0;
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        JSONArray array = conn.getUseOfResources(year, getResources().getStringArray(R.array.spn_expenditure));
        List<TableData> reporte = null;
        List<TableData.TableDataItem> listTable = null;
        TableData row;
        TableData.TableDataItem item;

        reporte = new ArrayList<TableData>();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_10_use_of_resources), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_10_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                row = new TableData();
                listTable = new ArrayList<TableData.TableDataItem>();
                try {
                    item = new TableData.TableDataItem(array.getJSONArray(i).getString(0), Color.LTGRAY, Color.BLACK, 20, 5, Gravity.LEFT);
                    listTable.add(item);
                    item = new TableData.TableDataItem("TSh " + String.format("%,.2f", array.getJSONArray(i).getDouble(1)), Color.LTGRAY, Color.BLACK, 20, 3, Gravity.RIGHT);
                    listTable.add(item);
                    total = total + (float) array.getJSONArray(i).getDouble(1);
                    row.setSize(2);
                    row.setRow(listTable);
                    reporte.add(row);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_10_total), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem("TSh " + String.format("%,.2f", total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.RIGHT);
            listTable.add(item);
            row.setSize(2);
            row.setRow(listTable);
            reporte.add(row);
        }

        f1.setInformationTable(reporte, "", this);
        f2.setInformationAnyChartVerticalBarChart(getResources().getString(R.string.report_10_distribution_of_use_of_resources), getResources().getString(R.string.rpt_10_x_axe), "", array, 500, 300, this);
    }

    public List<List<TableData>> report11Data() {
        String pattern = "%.0f";//((SpinnerItem)spnGrade.getSelectedItem()).getId()
        int spItm = ((SpinnerItem) spnGrade.getSelectedItem()).getId();
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        List<List<TableData>> ListRep = new ArrayList<List<TableData>>(); // new 20190421

        List<String> repList = booksGrade(spItm, year);
        List<TableData> reporte = new ArrayList<TableData>();
        List<TableData.TableDataItem> listTable = null;
        TableData row;
        TableData.TableDataItem item;
//header
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.str_g_grade), Color.GRAY, Color.WHITE, 20, 2, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_textbooks), Color.GRAY, Color.WHITE, 20, 2, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table1_report4), Color.GRAY, Color.WHITE, 20, 2, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.title_table1_report4_1), Color.GRAY, Color.WHITE, 20, 2, Gravity.CENTER);
        listTable.add(item);

        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);
        ListRep.add(reporte); // new 20190421
        double t1 = 0, t2 = 0, t3 = 0, tmp = 0;
        for (int i = 0; i < repList.size(); i++) {
            String[] items = repList.get(i).split("\\|");

            t1 = t1 + Double.valueOf(items[2]);
            t2 = t2 + Double.valueOf(items[3]);
            t3 = t3 + Double.valueOf(items[4]);
            tmp = Double.valueOf(items[4]);

            //details
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(items[1], Color.WHITE, Color.BLACK, 20, 2, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(items[2], Color.WHITE, Color.BLACK, 20, 2, Gravity.RIGHT);
            listTable.add(item);
            item = new TableData.TableDataItem(items[3], Color.WHITE, Color.BLACK, 20, 2, Gravity.RIGHT);
            listTable.add(item);
            item = new TableData.TableDataItem("1:" + String.valueOf(String.format(pattern, (float) tmp)), Color.WHITE, Color.BLACK, 20, 2, Gravity.RIGHT);
            listTable.add(item);

            row.setSize(4);
            row.setRow(listTable);
            reporte.add(row);
        }
        ListRep.add(reporte); // new 20190421
//totals

        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.str_ff_i4), Color.GRAY, Color.WHITE, 20, 2, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(String.valueOf(String.format(pattern, (float) t1)), Color.GRAY, Color.WHITE, 20, 2, Gravity.RIGHT);
        listTable.add(item);
        item = new TableData.TableDataItem(String.valueOf(String.format(pattern, (float) t2)), Color.GRAY, Color.WHITE, 20, 2, Gravity.RIGHT);
        listTable.add(item);
        item = new TableData.TableDataItem("1:" + String.valueOf(String.format(pattern, (float) t3)), Color.GRAY, Color.WHITE, 20, 2, Gravity.RIGHT);
        listTable.add(item);

        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);
        ListRep.add(reporte); // new 20190421
        return ListRep;
        //f1.setInformationTable(reporte, "", this);
    }


    private void report8Data() {
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        float total = 0;
        JSONArray array = conn.getCapitationGrantsByMonth(year, getMonthsUntil(Calendar.getInstance().get(MONTH), "MMM"));
        List<TableData> reporte = new ArrayList<TableData>();
        List<TableData.TableDataItem> listTable =  new ArrayList<TableData.TableDataItem>();
        TableData row = new TableData();
        TableData.TableDataItem item;

        item = new TableData.TableDataItem(getString(R.string.rpt_month), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_10_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                row = new TableData();
                listTable = new ArrayList<TableData.TableDataItem>();
                try {
                    item = new TableData.TableDataItem(array.getJSONArray(i).getString(0), Color.LTGRAY, Color.BLACK, 20, 5, Gravity.LEFT);
                    listTable.add(item);
                    item = new TableData.TableDataItem("TSh " + String.format("%,.2f", array.getJSONArray(i).getDouble(1)), Color.LTGRAY, Color.BLACK, 20, 3, Gravity.RIGHT);
                    listTable.add(item);
                    total = total + (float) array.getJSONArray(i).getDouble(1);
                    row.setSize(2);
                    row.setRow(listTable);
                    reporte.add(row);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_10_total), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem("TSh " + String.format("%,.2f", total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            row.setSize(2);
            row.setRow(listTable);
            reporte.add(row);
        }
        f1.setInformationTable(reporte, "", this);

        if (array == null) {
            array = new JSONArray();
        }
        f2.setInformationAnyChartLine(getResources().getString(R.string.report_9_monthly_distribution), "", "", array, 600, 350, this);
    }

    private void report9Data() {
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        float total = 0;
        String[] lbl_deposits = new String[]{
                getResources().getString(R.string.spn_dep_id16),
                getResources().getString(R.string.spn_dep_id17),
                getResources().getString(R.string.spn_dep_id18),
                getResources().getString(R.string.spn_dep_id19),
                getResources().getString(R.string.spn_dep_id20),
                getResources().getString(R.string.spn_dep_id21),
                getResources().getString(R.string.spn_dep_id1),
                getResources().getString(R.string.spn_dep_id2),
                getResources().getString(R.string.spn_dep_id3),
                getResources().getString(R.string.spn_dep_id4),
                getResources().getString(R.string.spn_dep_id5),
                getResources().getString(R.string.spn_dep_id6),
                getResources().getString(R.string.spn_dep_id7),
        };

        JSONArray array = conn.getUseOfResourcesBySource(year, lbl_deposits);
        List<TableData> reporte = null;
        List<TableData.TableDataItem> listTable = null;
        TableData row;
        TableData.TableDataItem item;

        reporte = new ArrayList<TableData>();
        row = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_101_source), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_10_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row.setSize(2);
        row.setRow(listTable);
        reporte.add(row);
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                row = new TableData();
                listTable = new ArrayList<TableData.TableDataItem>();
                try {
                    item = new TableData.TableDataItem(array.getJSONArray(i).getString(0), Color.LTGRAY, Color.BLACK, 20, 5, Gravity.LEFT);
                    listTable.add(item);
                    item = new TableData.TableDataItem("TSh " + String.format("%,.2f", array.getJSONArray(i).getDouble(1)), Color.LTGRAY, Color.BLACK, 20, 3, Gravity.RIGHT);
                    listTable.add(item);
                    total = total + (float) array.getJSONArray(i).getDouble(1);
                    row.setSize(2);
                    row.setRow(listTable);
                    reporte.add(row);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_10_total), Color.DKGRAY, Color.WHITE, 20, 5, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem("TSh " + String.format("%,.2f", total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.RIGHT);
            listTable.add(item);
            row.setSize(2);
            row.setRow(listTable);
            reporte.add(row);
        }

        f1.setInformationTable(reporte, "", this);
        f2.setInformationAnyChartPieChart(getResources().getString(R.string.report_9_distribution_of_resources_by_source), getResources().getString(R.string.rpt_10_x_axe), "", array, 500, 300, this);
        f3.setInformationAnyChartVerticalBarChart(getResources().getString(R.string.report_9_distribution_of_resources_by_source), getResources().getString(R.string.rpt_10_x_axe), "", array, 500, 300, this);
    }


    private String kidBooksRatio(String Books, String Kids) {
        //ninos/libros
        float iBook = 0;
        try {
            iBook = Float.parseFloat(Books);
        } catch (Exception ex) {
        }
        ;
        if (Books.isEmpty())
            Books = "0";
        if (Kids.isEmpty())
            Kids = "0";

        if (iBook == 0)
            return "0";
        else {
            float tmpB = Float.parseFloat(Books) / Float.parseFloat(Books);
            float tmpN = Float.parseFloat(Kids) / Float.parseFloat(Books);
            int ret = Math.round(tmpN / tmpB);
            return String.valueOf(String.format("%.0f", tmpB)) + ":" + String.valueOf(ret);
        }
    }

    private void report12Data() {
        //List<SpinnerItem> books = getPrimaryBooksArray(((SpinnerItem) spnSubject12.getSelectedItem()));
        books = getPrimaryBooksArray(((SpinnerItem) spnSubject12.getSelectedItem()));
        float totalStudent = 0, totalbooks = 0;
        for (int i = 0; i < 7; i++) {
            totalStudent = totalStudent + Float.parseFloat(((SpinnerItem) studentQuantity.get(i)).getValue());
            totalbooks = totalbooks + Float.parseFloat(((SpinnerItem) books.get(i)).getValue());
        }

//        int width = 300;
//        int height = 245;
//        String
        ratio = kidBooksRatio(String.valueOf(totalbooks), String.valueOf(totalStudent));
        f0.setInformationAnyChartGrayGauge("", ratio, totalbooks == 0 ? 0f : totalStudent / totalbooks, width, height, this);

        ratio = kidBooksRatio(books.get(0).getValue(), studentQuantity.get(0).getValue());
        f1.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard1), ratio, Float.parseFloat(books.get(0).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(0).getValue()) / Float.parseFloat(books.get(0).getValue()), width, height, this);

        ratio = kidBooksRatio(books.get(1).getValue(), studentQuantity.get(1).getValue());
        f2.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard2), ratio, Float.parseFloat(books.get(1).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(1).getValue()) / Float.parseFloat(books.get(1).getValue()), width, height, this);

        ratio = kidBooksRatio(books.get(2).getValue(), studentQuantity.get(2).getValue());
        f3.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard3), ratio, Float.parseFloat(books.get(2).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(2).getValue()) / Float.parseFloat(books.get(2).getValue()), width, height, this);

        ratio = kidBooksRatio(books.get(3).getValue(), studentQuantity.get(3).getValue());
        f4.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard4), ratio, Float.parseFloat(books.get(3).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(3).getValue()) / Float.parseFloat(books.get(3).getValue()), width, height, this);

        ratio = kidBooksRatio(books.get(4).getValue(), studentQuantity.get(4).getValue());
        f5.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard5), ratio, Float.parseFloat(books.get(4).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(4).getValue()) / Float.parseFloat(books.get(4).getValue()), width, height, this);

        ratio = kidBooksRatio(books.get(5).getValue(), studentQuantity.get(5).getValue());
        f6.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard6), ratio, Float.parseFloat(books.get(5).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(5).getValue()) / Float.parseFloat(books.get(5).getValue()), width, height, this);

        ratio = kidBooksRatio(books.get(6).getValue(), studentQuantity.get(6).getValue());
        f7.setInformationAnyChartGrayGauge(getResources().getString(R.string.txt_standard7), ratio, Float.parseFloat(books.get(6).getValue()) == 0 ? 0 : Float.parseFloat(studentQuantity.get(6).getValue()) / Float.parseFloat(books.get(6).getValue()), width, height, this);

    }

    private void report13Data() {
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        List<TableData> reporte = new ArrayList<TableData>();
        List<TableData.TableDataItem> listTable = new ArrayList<TableData.TableDataItem>();
        ;
        TableData row;
        TableData.TableDataItem item;
        HashMap<Integer, Integer> rowGrade;
        String[] grades;
        int[] totals = new int[4];

        HashMap<String, HashMap<Integer, Integer>> data1 = conn.getPrimaryStudentsDisabilities(year, "1");
        HashMap<String, HashMap<Integer, Integer>> data3 = conn.getPrimaryStudentsDisabilities(year, "3");

//        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_13_grade), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_13_vision), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_13_hearing), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_13_physical), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_13_intellectual_disability), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        row = new TableData();
        row.setSize(5);
        row.setRow(listTable);
        reporte.add(row);

        if (data1.size() > 0) {
            grades = data1.keySet().toArray(new String[0]);
            Arrays.sort(grades);
            for (String grade : grades) {
                rowGrade = data1.get(grade);
                listTable = new ArrayList<TableData.TableDataItem>();

                item = new TableData.TableDataItem(grade, Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);

                listTable.add(item);

                for (int disability = 1; disability <= 4; disability++) {
                    item = new TableData.TableDataItem(
                            ((rowGrade.get(disability) != null) ? rowGrade.get(disability) : 0) + "",
                            Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                    listTable.add(item);
                    totals[disability - 1] += (rowGrade.get(disability) != null) ? rowGrade.get(disability) : 0;
                }

                row = new TableData();
                row.setSize(5);
                row.setRow(listTable);
                reporte.add(row);
            }

            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_13_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[0]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[1]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[2]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[3]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);

            row.setSize(5);
            row.setRow(listTable);
            reporte.add(row);
        }

        if (data3.size() > 0) {
            grades = data3.keySet().toArray(new String[0]);
            Arrays.sort(grades);
            for (String grade : grades) {
                rowGrade = data3.get(grade);
                listTable = new ArrayList<TableData.TableDataItem>();

                item = new TableData.TableDataItem(grade, Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                listTable.add(item);

                for (int disability = 1; disability <= 4; disability++) {
                    item = new TableData.TableDataItem(
                            ((rowGrade.get(disability) != null) ? rowGrade.get(disability) : 0) + "",
                            Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                    listTable.add(item);
                    totals[disability - 1] += (rowGrade.get(disability) != null) ? rowGrade.get(disability) : 0;
                }

                row = new TableData();
                row.setSize(5);
                row.setRow(listTable);
                reporte.add(row);
            }

            row = new TableData();
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_13_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[0]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[1]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[2]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem(Integer.toString(totals[3]), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);

            row.setSize(5);
            row.setRow(listTable);
            reporte.add(row);
        }
        f1.setInformationTable(reporte, "", this);
    }


    public void report14Data() {
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy
        List<TableData> reporte = new ArrayList<TableData>();
        List<TableData.TableDataItem> listTable = new ArrayList<TableData.TableDataItem>();
        ;
        TableData row;
        TableData.TableDataItem item;
        int totalEnrolled = 0;
        int totalNewEntrant = 0;
        int totalRepeater = 0;

        // Add Table Column Titles
        //listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_14_grade), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_14_enrolled), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_14_new_entrant), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_14_repeater), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        row = new TableData();
        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);

        // Gather data from DB and fill the table
        List<List<String>> data = conn.getPrimaryRepetitionByGrade(year);
        if (data.size() > 0) {
            for (List<String> rowList : data) {
                listTable = new ArrayList<TableData.TableDataItem>();
                item = new TableData.TableDataItem(rowList.get(0), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                listTable.add(item);

                item = new TableData.TableDataItem(rowList.get(1), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                listTable.add(item);
                totalEnrolled += Integer.valueOf(rowList.get(1));

                item = new TableData.TableDataItem(rowList.get(2), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                listTable.add(item);
                totalNewEntrant += Integer.valueOf(rowList.get(2));

                item = new TableData.TableDataItem(rowList.get(3), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
                listTable.add(item);
                totalRepeater += Integer.valueOf(rowList.get(3));

                row = new TableData();
                row.setSize(4);
                row.setRow(listTable);
                reporte.add(row);
            }

            // Add totals row
            listTable = new ArrayList<TableData.TableDataItem>();
            item = new TableData.TableDataItem(getString(R.string.rpt_14_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);

            item = new TableData.TableDataItem(Integer.toString(totalEnrolled), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);

            item = new TableData.TableDataItem(Integer.toString(totalNewEntrant), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);

            item = new TableData.TableDataItem(Integer.toString(totalRepeater), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
            listTable.add(item);

            row = new TableData();
            row.setSize(4);
            row.setRow(listTable);
            reporte.add(row);
        }
        f1.setInformationTable(reporte, "", this);
    }

    public void report15Data() {
        List<TableData> reporte;
        List<TableData.TableDataItem> listTable = null;
        TableData row, emptyLine;
        TableData.TableDataItem item;

        emptyLine = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        listTable.add(new TableData.TableDataItem(" ", Color.WHITE, Color.WHITE, 20, 1, Gravity.CENTER));
        emptyLine.setSize(1);
        emptyLine.setRow(listTable);

        // LATRINE TABLE
        List<Integer> latrine = conn.getSchoolLatrineQuantity();

        int boysOnlyLatrine = 0;
        int girlsOnlyLatrine = 0;
        int combinedLatrine = 0;
        if (latrine != null) {
            boysOnlyLatrine = latrine.get(0);
            girlsOnlyLatrine = latrine.get(1);
            combinedLatrine = latrine.get(2);
        }

        reporte = new ArrayList<TableData>();
        // Add Table Column Titles
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_15_latrine), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_15_boys_only), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_15_girls_only), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_15_combined), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row = new TableData();
        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);

        // Add Table Values
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_15_total), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(Integer.toString(boysOnlyLatrine), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(Integer.toString(girlsOnlyLatrine), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(Integer.toString(combinedLatrine), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);

        row = new TableData();
        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);

        f1.setInformationTable(reporte, "", this);

        // ENROLLMENT TABLE
        List<GradeSex> gradeSexList = conn.getStudentByGradeBySex();
        reporte = new ArrayList<TableData>();
        ;
        int girlsEnrolled = 0;
        int boysEnrolled = 0;
        int total = 0;
        if (gradeSexList != null) {
            for (GradeSex gs : gradeSexList) {
                girlsEnrolled += gs.getWomen();
                boysEnrolled += gs.getMen();
            }
        }
        total = boysEnrolled + girlsEnrolled;

        // Add Table Column Titles
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_15_enrollment), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_15_boys), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_15_girls), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(getString(R.string.rpt_15_total), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);

        row = new TableData();
        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);


        // Add Table Values
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_15_total), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(Integer.toString(boysEnrolled), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(Integer.toString(girlsEnrolled), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);

        item = new TableData.TableDataItem(Integer.toString(total), Color.WHITE, Color.BLACK, 20, 3, Gravity.CENTER);
        listTable.add(item);

        row = new TableData();
        row.setSize(4);
        row.setRow(listTable);
        reporte.add(row);
        f2.setInformationTable(reporte, "", this);

        // RATIOS
        int gRatio = -1;
        if ((girlsOnlyLatrine + combinedLatrine) > 0)
            gRatio = girlsEnrolled / (girlsOnlyLatrine + combinedLatrine);
        int bRatio = -1;
        if ((boysOnlyLatrine + combinedLatrine) > 0)
            bRatio = boysEnrolled / (boysOnlyLatrine + combinedLatrine);

        reporte = new ArrayList<TableData>();

        // Boys Pit-Latrine Ratio
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_15_boys_pit_latrine_ratio), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row = new TableData();
        row.setSize(1);
        row.setRow(listTable);
        reporte.add(row);

        listTable = new ArrayList<TableData.TableDataItem>();
        Drawable indicator;


        if (bRatio >= 0) {
            if (bRatio <= 25) {
                indicator = getResources().getDrawable(R.drawable.ratio_good);
            } else if (bRatio <= 100) {
                indicator = getResources().getDrawable(R.drawable.ratio_regular);
            } else {
                indicator = getResources().getDrawable(R.drawable.ratio_bad);
            }
            item = new TableData.TableDataItem(indicator, Color.WHITE, 1, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem("1:" + bRatio, Color.WHITE, Color.BLACK, 40, 5, Gravity.CENTER);
        } else {
            item = new TableData.TableDataItem(getString(R.string.rpt_15_no_information), Color.WHITE, Color.BLACK, 20, 5, Gravity.CENTER);
        }
        listTable.add(item);
        row = new TableData();
        row.setSize(listTable.size());
        row.setRow(listTable);
        reporte.add(row);

        reporte.add(emptyLine);

        //Girls Pit-Latrine Ratio
        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem(getString(R.string.rpt_15_girls_pit_latrine_ratio), Color.DKGRAY, Color.WHITE, 20, 3, Gravity.CENTER);
        listTable.add(item);
        row = new TableData();
        row.setSize(1);
        row.setRow(listTable);
        reporte.add(row);

        listTable = new ArrayList<TableData.TableDataItem>();

        if (gRatio >= 0) {
            if (gRatio <= 25) {
                indicator = getResources().getDrawable(R.drawable.ratio_good);
            } else if (gRatio <= 100) {
                indicator = getResources().getDrawable(R.drawable.ratio_regular);
            } else {
                indicator = getResources().getDrawable(R.drawable.ratio_bad);
            }
            item = new TableData.TableDataItem(indicator, Color.WHITE, 1, Gravity.CENTER);
            listTable.add(item);
            item = new TableData.TableDataItem("1:" + gRatio, Color.WHITE, Color.BLACK, 40, 5, Gravity.CENTER);
        } else {
            item = new TableData.TableDataItem(getString(R.string.rpt_15_no_information), Color.WHITE, Color.BLACK, 20, 5, Gravity.CENTER);
        }

        listTable.add(item);
        row = new TableData();
        row.setSize(listTable.size());
        row.setRow(listTable);
        reporte.add(row);

        emptyLine = new TableData();
        listTable = new ArrayList<TableData.TableDataItem>();
        listTable.add(new TableData.TableDataItem(" ", Color.WHITE, Color.WHITE, 40, 1, Gravity.CENTER));
        emptyLine.setSize(1);
        emptyLine.setRow(listTable);
        reporte.add(emptyLine);

        listTable = new ArrayList<TableData.TableDataItem>();
        item = new TableData.TableDataItem("", Color.WHITE, Color.BLACK, 22, 2, Gravity.LEFT);
        listTable.add(item);
        item = new TableData.TableDataItem(getResources().getDrawable(R.drawable.ratio_good), Color.WHITE, 4, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_15_good), Color.WHITE, Color.BLACK, 22, 4, Gravity.LEFT);
        listTable.add(item);

        item = new TableData.TableDataItem(getResources().getDrawable(R.drawable.ratio_regular), Color.WHITE, 4, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_15_regular), Color.WHITE, Color.BLACK, 22, 5, Gravity.LEFT);
        listTable.add(item);

        item = new TableData.TableDataItem(getResources().getDrawable(R.drawable.ratio_bad), Color.WHITE, 4, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.rpt_15_bad), Color.WHITE, Color.BLACK, 22, 4, Gravity.LEFT);
        listTable.add(item);
        row = new TableData();
        row.setSize(7);
        row.setRow(listTable);
        reporte.add(row);

        f3.setInformationTable(reporte, "", this);
    }

    public void report16TableData() {
        int year = (((SpinnerItem) spnYear.getSelectedItem()).getId());//yyyy

        List<TableData> reporte = new ArrayList<TableData>();
        TableData row = new TableData();
        List<TableData.TableDataItem> listTable = new ArrayList<TableData.TableDataItem>();
        TableData.TableDataItem item = new TableData.TableDataItem(getString(R.string.table_1_report1_name), Color.GRAY, Color.WHITE, 15, 3, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem("%", Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_sick), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_excused), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        item = new TableData.TableDataItem(getString(R.string.str_g_unexcused), Color.GRAY, Color.WHITE, 15, 1, Gravity.CENTER);
        listTable.add(item);
        row.setSize(5);
        row.setRow(listTable);
        reporte.add(row);

        if (Calendar.getInstance().get(MONTH) != 0) {
            studentAttendance = conn.getTeacherAttendanceMonth(spnTitleMonth.getSelectedItemPosition(), currentPeriod == YEARLY, year);
            if (studentAttendance != null) {
                for (int i = 0; i < studentAttendance.size(); i++) {
                    row = new TableData();
                    listTable = new ArrayList<TableData.TableDataItem>();
                    item = new TableData.TableDataItem(studentAttendance.get(i).getName(), Color.parseColor("#EEEEEE"), Color.BLACK, 15, 3, Gravity.LEFT);
                    listTable.add(item);
                    if ((((float) studentAttendance.get(i).getAttendedTime() / studentAttendance.get(i).getTotalTime() * 100)) > 100) {
                        item = new TableData.TableDataItem(String.format("%.2f", ((float) 1 / 1 * 100)) + "%", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    } else {
                        item = new TableData.TableDataItem(String.format("%.2f", ((float) studentAttendance.get(i).getAttendedTime() / studentAttendance.get(i).getTotalTime() * 100)) + "%", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    }
                    listTable.add(item);
                    item = new TableData.TableDataItem(studentAttendance.get(i).getReason1() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    listTable.add(item);
                    item = new TableData.TableDataItem(studentAttendance.get(i).getReason2() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    listTable.add(item);
                    item = new TableData.TableDataItem(studentAttendance.get(i).getReason3() + "", Color.parseColor("#EEEEEE"), Color.BLACK, 15, 1, Gravity.CENTER);
                    listTable.add(item);
                    row.setSize(5);
                    row.setRow(listTable);
                    reporte.add(row);
                }
            }
        }
        f1.setInformationTable(reporte, "", this);
    }

    @Override
    public void onClickListener(TableData item) {
        if (currentReport == REPORT_1) {
            Toast.makeText(this, item.getRow().get(4).getValue() + "", Toast.LENGTH_LONG).show();
        }
    }


    public void toastMSG(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (currentReport == REPORT_1) {
            if ((parent.getId() == R.id.spn_1_shift0)
                    || (parent.getId() == R.id.spn_1_level) || (parent.getId() == R.id.spn_1_stream)
                    || (parent.getId() == R.id.spnMonthSelected) || (parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport1();
            } else if ((parent.getId() == R.id.spn_1_level0) || (parent.getId() == R.id.spnYearSelected)) {
                grades = conn.getPrimaryGrades(((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                spnGrade.setAdapter(new SpinnerItemAdapter(this, grades));
                drawFragmentsReport1();
            }
        }
        if (currentReport == REPORT_2) {
            if ((parent.getId() == R.id.spn_1_shift0) ||
                    (parent.getId() == R.id.spn_1_level) || (parent.getId() == R.id.spn_1_stream)) {
                drawFragmentsReport2();
            } else if ((parent.getId() == R.id.spn_1_level0) || (parent.getId() == R.id.spn_1_stream)) {
                grades = conn.getPrimaryGrades(((SpinnerItem) spnLevel0.getSelectedItem()).getId());
                spnGrade.setAdapter(new SpinnerItemAdapter(this, grades));
                drawFragmentsReport2();
            }
        }

        if (currentReport == REPORT_4) {
            if ((parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport4();
            }
        }


        if (currentReport == REPORT_6) {
            if ((parent.getId() == R.id.spn_1_level)||(parent.getId() == R.id.spnYearSelected)) {
                subject = conn.getSubjectByGrade(((SpinnerItem) spnGrade.getSelectedItem()).getId());
                spnSubjet.setAdapter(new SpinnerItemAdapter(this, subject));
                drawFragmentsReport6();
            } else if ((parent.getId() == R.id.spn_1_exam) || (parent.getId() == R.id.spn_1_subject) || (parent.getId() == R.id.spn_1_stream)
        ||(parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport6();
            }
        }
        if (currentReport == REPORT_7 ) {
            if ((parent.getId() == R.id.spn_1_level) || (parent.getId() == R.id.spn_1_stream)
                     || (parent.getId() == R.id.spnYearSelected)) {
                subject = conn.getSubjectByGrade(((SpinnerItem) spnGrade.getSelectedItem()).getId());
                spnTitleMonth.setAdapter(new SpinnerItemAdapter(this, subject));
                drawFragmentsReport7();
            }
            if ((parent.getId() == R.id.spnMonthSelected)){
                drawFragmentsReport7();
            }
        }

        if (currentReport == REPORT_8) {
            if ((parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport8();
            }
        }

        if (currentReport == REPORT_9) {
            if ((parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport9();
            }
        }

        if (currentReport == REPORT_10) {
            if ((parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport10();
            }
        }

        if (currentReport == REPORT_11) {
            if ((parent.getId() == R.id.spn_1_level) || (parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport11();
            }
        }

        if (currentReport == REPORT_12) {
            if (parent.getId() == spnSubject12.getId() || (parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport12();
            }
        }

        if (currentReport == REPORT_13) {
            if ((parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport13();
            }
        }

        if (currentReport == REPORT_14) {
            if ((parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport14();
            }
        }

        if (currentReport == REPORT_16) {
            if ((parent.getId() == R.id.spnMonthSelected) || (parent.getId() == R.id.spnYearSelected)) {
                drawFragmentsReport16();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @OnClick({R.id.btn_monthly_report1, R.id.btn_yearly_report1, R.id.btn_information})
    public void periodReport1Buttons(View v) {
        if (v.getId() == R.id.btn_monthly_report1) {
            spnTitleMonth.setVisibility(View.VISIBLE);
            btnMonthyReport1.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_report_button_background_select));
            btnMonthyReport1.setTextColor(Color.YELLOW);
            btnYearlyReport1.setBackgroundDrawable(getResources().getDrawable(R.drawable.right_report_button_background));
            btnYearlyReport1.setTextColor(Color.WHITE);
            currentPeriod = MONTHLY;
            if (currentReport == REPORT_1) {
                drawFragmentsReport1();
            } else if (currentReport == REPORT_16) {
                drawFragmentsReport16();
            }
        } else if (v.getId() == R.id.btn_yearly_report1) {
            spnTitleMonth.setVisibility(GONE);
            btnMonthyReport1.setBackgroundDrawable(getResources().getDrawable(R.drawable.left_report_button_background));
            btnMonthyReport1.setTextColor(Color.WHITE);
            btnYearlyReport1.setBackgroundDrawable(getResources().getDrawable(R.drawable.right_report_button_background_selected));
            btnYearlyReport1.setTextColor(Color.YELLOW);
            currentPeriod = YEARLY;
            if (currentReport == REPORT_1) {
                drawFragmentsReport1();
            } else if (currentReport == REPORT_16) {
                drawFragmentsReport16();
            }
        } else if (v.getId() == R.id.btn_information) {
            dialogReprotInformation(this, getResources(), msjInformation);
        }

    }

    private String[] getMonthsUntil(int month, String monthPattern) {
        //pattern could be MM  MMM MMMM
        Calendar cal = Calendar.getInstance();
        String[] array = new String[month + 1];
        DateFormat fmt = new SimpleDateFormat(monthPattern, conn.getCurrentLocale());
        for (int i = 0; i <= month; i++) {
            cal.set(MONTH, i);
            array[i] = fmt.format(cal.getTime());
        }
        return array;
    }

    private String[] getMonthsUntil(int month) {
        Calendar cal = Calendar.getInstance();
        String[] array = new String[month + 1];
        DateFormat fmt = new SimpleDateFormat("MMMM", conn.getCurrentLocale());
        for (int i = 0; i <= month; i++) {
            cal.set(MONTH, i);
            array[i] = fmt.format(cal.getTime());
        }
        return array;
    }

    private List<SpinnerItem> getExamTitle() {
        List<SpinnerItem> lista = new ArrayList<SpinnerItem>();
        lista.add(new SpinnerItem(1, getResources().getString(R.string.str_gtest_1)));
        lista.add(new SpinnerItem(2, getResources().getString(R.string.str_gtest_2)));
        lista.add(new SpinnerItem(3, getResources().getString(R.string.str_gtest_3)));
        lista.add(new SpinnerItem(4, getResources().getString(R.string.str_gtest_4)));
        lista.add(new SpinnerItem(5, getResources().getString(R.string.str_gtest_5)));
        lista.add(new SpinnerItem(6, getResources().getString(R.string.str_gtest_6)));
        lista.add(new SpinnerItem(7, getResources().getString(R.string.str_gtest_7)));
        lista.add(new SpinnerItem(8, getResources().getString(R.string.str_gtest_8)));
        lista.add(new SpinnerItem(9, getResources().getString(R.string.str_gtest_9)));
        lista.add(new SpinnerItem(10, getResources().getString(R.string.str_gtest_10)));
        lista.add(new SpinnerItem(11, getResources().getString(R.string.str_gtest_11)));
        lista.add(new SpinnerItem(12, getResources().getString(R.string.str_gtest_12)));
        lista.add(new SpinnerItem(13, getResources().getString(R.string.str_gtest_13)));
        lista.add(new SpinnerItem(14, getResources().getString(R.string.str_gtest_14)));
        lista.add(new SpinnerItem(15, getResources().getString(R.string.str_gtest_15)));
        lista.add(new SpinnerItem(16, getResources().getString(R.string.str_gtest_16)));
        return lista;
    }

    private List<SpinnerItem> getSubjectTitle() {
        List<SpinnerItem> subjects = new ArrayList<SpinnerItem>();
        subjects.add(new SpinnerItem(1, getResources().getString(R.string.str_bl3_f_subject)));//7,8,12,21,9,11,18,10,14,16,15,6,1,2,3,4,5,18
        subjects.add(new SpinnerItem(2, getResources().getString(R.string.str_bl3_g_subject)));
        subjects.add(new SpinnerItem(3, getResources().getString(R.string.str_bl3_h_subject)));
        subjects.add(new SpinnerItem(4, getResources().getString(R.string.str_bl3_i_subject)));
        subjects.add(new SpinnerItem(5, getResources().getString(R.string.str_bl3_j_subject)));
        subjects.add(new SpinnerItem(6, getResources().getString(R.string.str_bl3_k_subject)));
        subjects.add(new SpinnerItem(7, getResources().getString(R.string.str_bl3_l_subject)));
        subjects.add(new SpinnerItem(8, getResources().getString(R.string.str_bl3_m_subject)));
        subjects.add(new SpinnerItem(9, getResources().getString(R.string.str_bl3_n_subject)));
        subjects.add(new SpinnerItem(10, getResources().getString(R.string.str_bl3_o_subject)));
        subjects.add(new SpinnerItem(11, getResources().getString(R.string.str_bl3_p_subject)));
        subjects.add(new SpinnerItem(12, getResources().getString(R.string.str_bl3_p1_subject)));
        subjects.add(new SpinnerItem(13, getResources().getString(R.string.str_bl3_p2_subject)));
        subjects.add(new SpinnerItem(14, getResources().getString(R.string.str_bl3_p3_subject)));
        subjects.add(new SpinnerItem(15, getResources().getString(R.string.str_bl3_p4_subject)));
        subjects.add(new SpinnerItem(16, getResources().getString(R.string.str_bl3_p5_subject)));
        subjects.add(new SpinnerItem(17, getResources().getString(R.string.str_bl3_p6_subject)));
        //subjects.add(new SpinnerItem(18,getResources().getString(R.string.str_bl3_q_subject)));
        return subjects;
    }

    private List<SpinnerItem> getPrimaryBooksArray(SpinnerItem subject) {
        List<SpinnerItem> books = new ArrayList<SpinnerItem>();
        for (int i = 0; i < 7; i++) {
            books.add(new SpinnerItem(i, "" + conn.getValueFromTable("f2a" + ((subject.getId() - 1) * 7 + i + 1), "f", "0")));
        }
        return books;
    }

    private List<SpinnerItem> getPrimaryStudentQuantity(int year) {
        List<SpinnerItem> studentQuantity = new ArrayList<SpinnerItem>();

        for (int i = 1; i <= 7; i++) {
            studentQuantity.add(new SpinnerItem(i - 1, "" + conn.getStudents11(i, year)));
        }
        return studentQuantity;
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
