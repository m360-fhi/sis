package com.example.sergio.sistz.finance;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.sergio.sistz.R;
import com.example.sergio.sistz.adapter.FinanceDepositsArrayAdapter;
import com.example.sergio.sistz.adapter.FinanceReportArrayAdapter;
import com.example.sergio.sistz.adapter.IReloadDataListener;
import com.example.sergio.sistz.data.FinanceTransaction;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.MyDateTimePicker;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FInanceActivityFragment extends Fragment implements AdapterView.OnItemClickListener,TextWatcher, IReloadDataListener{
    @BindView(R.id.finance_current_balance)
    CurrencyEditText balance;
    @BindView(R.id.txt_initial_date)
    TextView txt_initial_date;
    @BindView(R.id.txt_final_date) TextView txt_final_date;
    @BindView(R.id.btn_initial_date)
    Button btn_initial_date;
    @BindView(R.id.btn_final_date) Button btn_final_date;
    @BindView(R.id.lv_activities)
    ListView lv;
    @BindView(R.id.lyt_current_balance) LinearLayout lyt_current_balance;
    private Float current_balance;
    private String  month_prev;
    private int year_pre, year, month, month_pre, lastday;
    private View v;
    private DBUtility conn;
    private List<FinanceTransaction> booksguides;
    private AlertDialog.Builder deleteDialog;
    private Locale locale;
    private FinanceReportArrayAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         v = inflater.inflate(R.layout.finance_activity, null);
        ButterKnife.bind(this, v);


        load();
        txt_initial_date.addTextChangedListener(this);
        txt_final_date.addTextChangedListener(this);
        return v;

    }
    public void setTxt_final_date(TextView txt_final_date) {
        this.txt_final_date = txt_final_date;
        load();
    }
    @OnClick({R.id.btn_initial_date, R.id.btn_final_date})
    public void setDate(View v) {
        DialogFragment dialogfragment = null;
        switch (v.getId()) {
            case R.id.btn_initial_date :
                dialogfragment = new MyDateTimePicker(R.id.txt_initial_date);
                dialogfragment.show(getActivity().getSupportFragmentManager(), getResources().getString(R.string.dialog_select_date));
                break;
            case R.id.btn_final_date :
                dialogfragment = new MyDateTimePicker(R.id.txt_final_date);
                dialogfragment.show(getActivity().getSupportFragmentManager(), getResources().getString(R.string.dialog_select_date));
                break;
        }
    }

    public void load(){

        lyt_current_balance.setVisibility(View.GONE);
        if (conn == null && this.getActivity() != null ) {
            conn = new DBUtility(this.getActivity().getApplicationContext());
        }

        if (conn != null) {
            locale = conn.getCurrentLocale();
            balance.configureViewForLocale(new Locale("sw", "TZ"));
            if (txt_initial_date.getText().toString() == "") {
                String month2;
                Calendar rightNow = Calendar.getInstance();
                year = rightNow.get(Calendar.YEAR);
                month = rightNow.get(Calendar.MONTH) + 1;
                year_pre = rightNow.get(Calendar.YEAR);
                month_pre = rightNow.get(Calendar.MONTH);
                if (month < 10) {
                    month2 = "0" + month;
                    if (month == 1) {
                        year_pre = year_pre - 1;
                    }
                    ;
                } else {
                    month2 = "" + month;
                }
                txt_initial_date.setText(year + "-" + month2 + "-01");
                txt_initial_date.setHint(year + "-" + month2 + "-01");
                txt_initial_date.setText(DateUtility.formatLocale(txt_initial_date.getText().toString(), locale));

                if (month_pre < 10) {
                    month_prev = "0" + month_pre;
                } else {
                    month_prev = String.valueOf(month_pre);
                }
                lastday = getLastDay(year_pre, month_pre);
            } else {
                month_prev = txt_initial_date.getHint().subSequence(5, 7).toString();
                month_pre = Integer.valueOf(month_prev) - 1;
                if (month_pre < 10) {
                    month_prev = "0" + month_pre;
                } else {
                    month_prev = String.valueOf(month_pre);
                }
                lastday = getLastDay(year_pre, month_pre);
            }

            if (txt_final_date.getText().toString() == "") {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                txt_final_date.setText(df.format(Calendar.getInstance().getTime()));
                txt_final_date.setHint(df.format(Calendar.getInstance().getTime()));
                txt_final_date.setText(DateUtility.formatLocale(txt_final_date.getText().toString(), locale));
            }

            booksguides = new ArrayList<FinanceTransaction>();
            booksguides = conn.getTransactionReport(txt_initial_date.getHint().toString(), txt_final_date.getHint().toString(), year_pre + "-" + month_prev + "-" + lastday);
            if (booksguides == null) {
                booksguides = new ArrayList<FinanceTransaction>();
                adapter = new FinanceReportArrayAdapter(getActivity(), R.layout.items_list_report_finance, booksguides);
                lv.setAdapter(adapter);
                adapter.setReloadListener(this);
                balance.setText("");
            } else {
                adapter = new FinanceReportArrayAdapter(getActivity(), R.layout.items_list_report_finance, booksguides);
                adapter.setReloadListener(this);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(this);

            }
        }

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lv_activities) {
            final int pos = position;

        }
    }


    public int getLastDay (int anio, int month) {

        Calendar cal=Calendar.getInstance();
        cal.set(anio, month-1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        load();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void reloadData() {
        booksguides = new ArrayList<FinanceTransaction>();
        booksguides = conn.getTransactionReport(txt_initial_date.getHint().toString(), txt_final_date.getHint().toString(), year_pre + "-" + month_prev + "-" + lastday);
        if (booksguides == null) {
            booksguides = new ArrayList<FinanceTransaction>();
            adapter = new FinanceReportArrayAdapter(getActivity(), R.layout.items_list_report_finance, booksguides);
            lv.setAdapter(adapter);
            adapter.setReloadListener(this);
            balance.setText("");
        } else {
            adapter = new FinanceReportArrayAdapter(getActivity(), R.layout.items_list_report_finance, booksguides);
            adapter.setReloadListener(this);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);

            current_balance = (float) 0;
            String deposits = "";
            String expenditures = "";
            for (int counter = 0; counter < booksguides.size(); counter++) {
                expenditures = booksguides.get(counter).getExpenditures().replace(",","");
                deposits = booksguides.get(counter).getDeposit().replace(",","");
                current_balance = current_balance + Float.parseFloat(deposits.equals("") ? "0.00" : deposits) - Float.parseFloat(expenditures.equals("") ? "0.00" : expenditures);
            }
            balance.setText(NumberFormat.getNumberInstance(new Locale("sw", "TZ")).format(current_balance).toString());
        }
    }
}