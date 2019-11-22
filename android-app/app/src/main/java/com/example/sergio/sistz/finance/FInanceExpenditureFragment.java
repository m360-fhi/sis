package com.example.sergio.sistz.finance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.sergio.sistz.R;
import com.example.sergio.sistz.adapter.FinanceDepositsArrayAdapter;
import com.example.sergio.sistz.data.FinanceTransaction;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.MoneyEditText;
import com.example.sergio.sistz.util.MyDateTimePicker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FInanceExpenditureFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View v;
    private Locale locale;
    @BindView(R.id.spn_deposit)
    Spinner sp;
    @BindView(R.id.money_edittext)
    MoneyEditText amount1;
    @BindView(R.id.txt_initial_date2)
    TextView txt_current_date;
    @BindView(R.id.finance_observation) EditText comments;
    @BindView(R.id.btn_initial_date)
    Button btn_current_date;
    @BindView(R.id.lv_activities)
    ListView lv;
    @BindView(R.id.btn_save)
    FloatingActionButton btn_save;
    private AlertDialog.Builder deleteDialog;
    public List<FinanceTransaction> booksguides;
    public DBUtility conn;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayAdapter<String> adap_section;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.finance_expenditures, null);
        ButterKnife.bind(this, v);
        load();

        return v;

    }
    public void load ()  {
        if (conn == null) {
            conn = new DBUtility(this.getActivity().getApplicationContext());
        }
        locale = conn.getCurrentLocale();
        if(txt_current_date.getText().toString()==""){

            txt_current_date.setText(DateUtility.formatLocale(df.format(Calendar.getInstance().getTime()), locale));
            txt_current_date.setHint(df.format(Calendar.getInstance().getTime()));
        };

        booksguides = new ArrayList<FinanceTransaction>();
        lv.setOnItemClickListener(this);
    }
    @OnClick({R.id.btn_save})
    public void addOrUpdate(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if(validation()==true){
                    FinanceTransaction finance = new FinanceTransaction();
                    int pos = getSubjectIndexList(( sp.getSelectedItemPosition()));

                    finance.setDate(txt_current_date.getHint().toString().equals("") ? "0" : txt_current_date.getHint().toString());
                    finance.setDescription(( sp.getSelectedItemPosition()));
                    finance.setDesc(sp.getSelectedItem().toString());
                    finance.setDeposit(String.valueOf(amount1.getMoneyValue()));
                    finance.setObservation((comments.getText().toString().equals("") ? "0" : comments.getText().toString()));
                    booksguides.add(finance);

                    amount1.setText("");
                    comments.setText("");
                    lv.setAdapter(new FinanceDepositsArrayAdapter(getActivity(), R.layout.items_list_finance, booksguides, false));
                    break;
                }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lv_activities) {
            final int pos = position;
            deleteDialog = new AlertDialog.Builder(getContext());
            deleteDialog.setCancelable(false);
            deleteDialog.setMessage(getResources().getString(R.string.delete_item) + " (" + booksguides.get(position).getDesc() + ")");
            deleteDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    booksguides.remove(getSubjectIndexList(booksguides.get(pos).getDescription()));
                    lv.setAdapter(new FinanceDepositsArrayAdapter(getActivity(), R.layout.items_list_finance, booksguides, false));
                    dialog.dismiss();
                }
            });
            deleteDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            deleteDialog.show();
        }
    }

    private  int getSubjectIndexSpinnner(int subject) {
        for (int i = 0; i < adap_section.getCount(); i++) {
            if (adap_section.getItemId(i) == subject) {

                return (int) adap_section.getItemId(i);
            }
        }
        return -1;
    }
    private  int getSubjectIndexList(int subject) {
        for (int i = 0; i < booksguides.size(); i++) {
            if (booksguides.get(i).getDescription() == subject) {
                return i;
            }
        }

        return -1;
    }

    @OnClick({R.id.btn_initial_date})
    public void setDate(View v) {
        DialogFragment dialogfragment = null;

        dialogfragment = new MyDateTimePicker(R.id.txt_initial_date2);
        dialogfragment.show(getActivity().getSupportFragmentManager(), getResources().getString(R.string.dialog_select_date));

    }

    public  void saveData() {
        if (booksguides==null){}
        else {
            for (int i = 0; i < booksguides.size(); i++) {
                conn.setFinanceExpenditure(booksguides.get(i).getDate(), String.valueOf((booksguides.get(i).getDescription())), booksguides.get(i).getDeposit().replace((Currency.getInstance(new Locale("sw", "TZ")).getSymbol(new Locale("sw", "KE"))),"").replace(",",""), booksguides.get(i).getObservation());

            }
        }
    }

    public boolean  validation(){

        if (!txt_current_date.getText().equals("") && !amount1.getText().toString().equals("") && Date.valueOf(txt_current_date.getHint().toString()).compareTo(Date.valueOf(df.format(Calendar.getInstance().getTime()))) <=0){
            return true;
        }
        else {
            deleteDialog = new AlertDialog.Builder(getContext());
            deleteDialog.setCancelable(false);
            deleteDialog.setMessage(R.string.finance_fill);
            deleteDialog.setPositiveButton(R.string.str_g_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            deleteDialog.show();
            return false;

        }

    }

    /**
     * Clears the focus to hide the keyboard if the focus was on an EditText
     */
    public void clearFocus() {
        Activity activity = getActivity();
        if(activity != null) {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            txt_current_date.requestFocus();
        }
    }
}