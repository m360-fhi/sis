package com.example.sergio.sistz.finance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.FinanceHome;
import com.example.sergio.sistz.data.FinanceTransactionHome;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.MoneyTextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FInanceHomeFragment extends Fragment {

    @BindView(R.id.txt_actual_date) TextView txt_actual_date;
    @BindView(R.id.finance_previous) MoneyTextView txt_previous;
    @BindView(R.id.finance_deposits) MoneyTextView txt_deposits;
    @BindView(R.id.finance_expenditures) MoneyTextView txt_expenditures;
    @BindView(R.id.finance_available) MoneyTextView txt_available;
    Double   balance;
    private List<FinanceTransactionHome> finance_amount, previous;
    private FinanceHome finance;
    private String month2, month_prev;
    private Locale locale;
    private DBUtility conn;
    private Double deposits, expenditure, deposits_prev, expenditure_prev;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.finance_home, null);
        ButterKnife.bind(this, v);
        deposits = Double.valueOf("0.0");
        expenditure = Double.valueOf("0.0");
        deposits_prev = Double.valueOf("0.0");
        expenditure_prev = Double.valueOf("0.0");
        load();
        return v;

    }

    public void load(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (conn == null) {
            conn = new DBUtility(this.getActivity().getApplicationContext());
        }
        locale = conn.getCurrentLocale();
        txt_actual_date.setText(DateUtility.formatLocale(df.format(Calendar.getInstance().getTime()), locale ));
        txt_actual_date.setHint(df.format(Calendar.getInstance().getTime()));

        Calendar rightNow = Calendar.getInstance();
        int year_pre = rightNow.get(Calendar.YEAR);
        int year_act = rightNow.get(Calendar.YEAR);
        int  month = rightNow.get(Calendar.MONTH)+1;
        int  month_pre = rightNow.get(Calendar.MONTH);
        if (month<10){
            month2 = "0"+month;
            if (month==1){year_pre=year_pre-1;};
        }else{
            month2 = String.valueOf(month);
        }
        if (month_pre<10){
            if (month_pre==0){month_prev="12";}
            else {month_prev = "0"+month_pre;}
        }else{
            month_prev = String.valueOf(month_pre);
        }
        int lastday=getLastDay(year_pre, month_pre);

        previous = new ArrayList<FinanceTransactionHome>();
        previous = conn.getTransactionReportHomePrevious(year_pre+"-"+month_prev+"-"+lastday);
        if (previous == null) {
            previous = new ArrayList<FinanceTransactionHome>();
            deposits_prev= Double.valueOf("0.0");
            expenditure_prev=Double.valueOf("0.0");

        } else {

            for (int counter = 0; counter < previous.size(); counter++) {
                deposits_prev = deposits_prev + Double.parseDouble(previous.get(counter).getDeposit().toString().replace(",",""));
                expenditure_prev = expenditure_prev + Double.parseDouble(previous.get(counter).getExpenditures().toString().replace(",",""));

            }

        }
        finance = conn.getFinanceHome(year_pre+"-"+month_prev+"-01",year_pre+"-"+month_prev+"-"+lastday,year_act+"-"+month2+"-01",txt_actual_date.getHint().toString());
        txt_previous.setAmount(deposits_prev-expenditure_prev);
        finance_amount = new ArrayList<FinanceTransactionHome>();
        finance_amount = conn.getTransactionReportHome(year_act+"-"+month2+"-01",txt_actual_date.getHint().toString());
        if (finance_amount == null) {
            finance_amount = new ArrayList<FinanceTransactionHome>();
            deposits= Double.valueOf("0.0");
            expenditure=Double.valueOf("0.0");

        } else {

            for (int counter = 0; counter < finance_amount.size(); counter++) {
                deposits = deposits + Double.parseDouble(finance_amount.get(counter).getDeposit().toString().replace(",",""));
                expenditure = expenditure + Double.parseDouble(finance_amount.get(counter).getExpenditures().toString().replace(",",""));

            }

        }
        txt_deposits.setAmount(deposits);
        txt_expenditures.setAmount(expenditure);
        txt_available.setAmount(deposits_prev-expenditure_prev+deposits-expenditure);
    }

    public int getLastDay (int anio, int month) {

        Calendar cal=Calendar.getInstance();
        cal.set(anio, month-1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    }
}
