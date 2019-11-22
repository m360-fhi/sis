package com.example.sergio.sistz.finance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.example.sergio.sistz.R;
import com.example.sergio.sistz.mysql.DBUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FInanceInstructionFragment extends Fragment{
    @BindView(R.id.txt_balance_leyend) TextView txt_balance;
    @BindView(R.id.txt_activity_leyend) TextView txt_activity;
    @BindView(R.id.txt_deposits_leyend) TextView txt_deposits;
    @BindView(R.id.txt_expenditures_leyend) TextView txt_expenditures;

    private View v;
    private DBUtility conn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.finance_instruction, null);
        ButterKnife.bind(this, v);
        load();
        return v;
    }
    public void load(){
        if (conn == null) {
            conn = new DBUtility(this.getActivity().getApplicationContext());
        }
        txt_balance.setText((Spanned)Html.fromHtml(getString(R.string.str_balance_leyend)));
        txt_activity.setText((Spanned)Html.fromHtml(getString(R.string.str_activity_leyend)));
        txt_deposits.setText((Spanned)Html.fromHtml(getString(R.string.str_deposits_leyend)));
        txt_expenditures.setText((Spanned)Html.fromHtml(getString(R.string.str_expenditures_leyend)));
    }
}
