package com.example.sergio.sistz.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.EmisInformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SchoolDescriptionFromEmisDialog extends Dialog {
    @BindView (R.id.lyt_content) LinearLayout content;
    @BindView(R.id.txt_council) TextView txtCouncil;
    @BindView(R.id.txt_region) TextView txtRegion;
    @BindView(R.id.txt_school) TextView txtSchool;
    @BindView(R.id.txt_ward) TextView txtWard;
    @BindView(R.id.txt_emis) TextView txtEmis;
    @BindView(R.id.chk_review_information) CheckBox chkReviewInformation;

    private EmisInformation emis;
    private IGetEmisCode emisOperator;
    private Context context;

    public SchoolDescriptionFromEmisDialog(@NonNull Context context, IGetEmisCode emisOperator, EmisInformation emis, int style) {
        super(context);
        this.context = context;

        this.emisOperator = emisOperator;
        this.emis = emis;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.school_description_from_emis_code_layout);
        ButterKnife.bind(this);
        if (emis == null) {
            emisOperator.emisCodeCorrect(null, false, context.getString(R.string.error_emis));
        } else {
            txtCouncil.setText(emis.getCouncil());
            txtEmis.setText(emis.getEmisCode());
            txtRegion.setText(emis.getRegion());
            txtWard.setText(emis.getWard());
            txtSchool.setText(emis.getSchool());
            content.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 425));
        }
    }



    @OnClick(R.id.btn_yes)
    public void clickYes(View v) {
        emisOperator.emisCodeCorrect(emis, chkReviewInformation.isChecked(), chkReviewInformation.isChecked()?context.getString(R.string.error_emis):context.getString(R.string.not_checked_emis_code));
    }

    @OnClick(R.id.btn_no)
    public void clickNo(View v) {
        emisOperator.emisCodeCorrect(null, false, "");
    }

}
