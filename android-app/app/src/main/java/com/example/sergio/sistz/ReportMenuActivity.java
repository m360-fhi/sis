package com.example.sergio.sistz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sergio.sistz.adapter.ReportItemAdapter;
import com.example.sergio.sistz.data.ReportItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.listOpciones) ListView listOpciones;

    private List<ReportItem> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_menu_layout);
        ButterKnife.bind(this);
        ReportItem item;
        list = new ArrayList<ReportItem>();
        switch (getIntent().getStringExtra("REPORT_GROUP")) {
            case "G1":
                item = new ReportItem(ReportContainerActivity.REPORT_0,getString(R.string.report_0_student_level_report), "", R.drawable.dashboard,false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_13, getString(R.string.report_13_primary_student_with_disabilities), "", R.drawable.dashboard, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_14, getString(R.string.report_14_primary_repetition_by_grade),"", R.drawable.dashboard, false );
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_4,getString(R.string.report_4_student_and_teacher_summary), "", R.drawable.dashboard, false);
                list.add(item);
                break;
            case "G2":
                item = new ReportItem(ReportContainerActivity.REPORT_17,getString(R.string.report_17_title), "", R.drawable.dashboard,false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_18,getString(R.string.report_18_title), "", R.drawable.dashboard,false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_1,getString(R.string.report_1_student_daily_attendance_report), "", R.drawable.dashboard,false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_2,getString(R.string.report_2_teacher_daily_attendance_report), "", R.drawable.dashboard,false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_16, getString(R.string.report_16_teacher_attendance), "",R.drawable.dashboard, false);
                list.add(item);
                break;
            case "G3":
                item = new ReportItem(ReportContainerActivity.REPORT_6, getString(R.string.report_6_primary_evaluation_by_pupil), "", R.drawable.dashboard, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_7, getString(R.string.report_7_Primary_evaluation_by_Subject), "", R.drawable.dashboard, false);
                list.add(item);
                break;
            case "G4":
                item = new ReportItem(ReportContainerActivity.REPORT_3,getString(R.string.report_3_classroom_to_pupil), "", R.drawable.measurer, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_5, getString(R.string.report_5_textbook_to_pupil_ratio), "", R.drawable.measurer, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_12, getString(R.string.report_12_primary_textbooks_by_grade_and_subject), "", R.drawable.measurer, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_15, getString(R.string.report_15_primary_student_pit_latrine_ratio),"", R.drawable.dashboard, false );
                list.add(item);
                break;
            case "G5":
                item = new ReportItem(ReportContainerActivity.REPORT_11, getString(R.string.report_11_primary_textbooks_by_grade_and_subject), "", R.drawable.dashboard, false);
                list.add(item);
                break;
            case "G6":
                item = new ReportItem(ReportContainerActivity.REPORT_8, getString(R.string.report_8_capitation_grants_by_month), "", R.drawable.dashboard, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_9, getString(R.string.report_9_distribution_of_resources_by_source), "", R.drawable.dashboard, false);
                list.add(item);
                item = new ReportItem(ReportContainerActivity.REPORT_10, getString(R.string.report_10_distribution_of_use_of_resources), "", R.drawable.dashboard, false);
                list.add(item);
                break;
        }

        listOpciones.setAdapter(new ReportItemAdapter(this, list));
        listOpciones.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ReportItem item = list.get(position);
        Intent intent = null;
        switch  (item.getIdReporte()) {
            case ReportContainerActivity.REPORT_0 :
                intent = new Intent(ReportMenuActivity.this, ReportS.class);
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_1 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_1);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_1_student_daily_attendance_report));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_1_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_2 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_2);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_2_teacher_daily_attendance_report));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_2_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_3 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_3);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_3_classroom_to_pupil));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_3_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_4 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_4);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_4_student_and_teacher_summary));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_4_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_5 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_5);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_5_textbook_to_pupil_ratio));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_5_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_6 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_6);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_6_primary_evaluation_by_pupil));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_6_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_7 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_7);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_7_Primary_evaluation_by_Subject));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_7_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_8:
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_8);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_8_capitation_grants_by_month));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_8_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_9:
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_9);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_9_distribution_of_resources_by_source));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_9_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_10 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_10);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_10_distribution_of_use_of_resources));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_10_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_11 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_11);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_11_primary_textbooks_by_grade_and_subject));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_11_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_12 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_12);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_12_primary_textbooks_by_grade_and_subject));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_12_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_13 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_13);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_13_primary_student_with_disabilities));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_13_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_14:
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_14);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_14_primary_repetition_by_grade));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_14_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_15:
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_15);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_15_primary_student_pit_latrine_ratio));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_15_information));
                startActivity(intent);
                break;
            case ReportContainerActivity.REPORT_16 :
                intent = new Intent(ReportMenuActivity.this, ReportContainerActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_16);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_16_teacher_attendance));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_16_information));
                startActivity(intent);
                break;

            case ReportContainerActivity.REPORT_17 :
                intent = new Intent(ReportMenuActivity.this, AssistanceControlsActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_17);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_17_title));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_17_information));
                startActivity(intent);
                break;

            case ReportContainerActivity.REPORT_18 :
                intent = new Intent(ReportMenuActivity.this, AssistanceControlsActivity.class);
                intent.putExtra("REPORT", ReportContainerActivity.REPORT_18);
                intent.putExtra("REPORT_TITLE", getString(R.string.report_18_title));
                intent.putExtra("REPORT_INFORMATION",getString(R.string.report_18_information));
                startActivity(intent);
                break;
        }

    }
}
