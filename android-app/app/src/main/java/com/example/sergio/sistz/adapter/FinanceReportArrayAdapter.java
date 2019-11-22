package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.data.FinanceTransaction;
import com.example.sergio.sistz.finance.FInanceActivityFragment;
import com.example.sergio.sistz.mysql.DBUtility;

import com.tooltip.Tooltip;

import com.example.sergio.sistz.R;


import com.example.sergio.sistz.util.MoneyTextView;


import java.util.List;

import javax.xml.transform.dom.DOMLocator;

/**
 * Created by pabloyac on 3/20/18.
 */

public class FinanceReportArrayAdapter extends ArrayAdapter<FinanceTransaction> {
    private Context context;
    private int layout;
    private List<FinanceTransaction> list;
    public  DBUtility conn;
    private AlertDialog.Builder deleteDialog;
    private IReloadDataListener reloadData;

    public FinanceReportArrayAdapter(@NonNull Context context, int resource, @NonNull List<FinanceTransaction> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.list = objects;

    }

    public void setReloadListener(IReloadDataListener data) {
        this.reloadData = data;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (conn == null) {
            conn = new DBUtility(this.getContext().getApplicationContext());
        }
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        if (list.get(position)!= null) {
            LinearLayout layout = (LinearLayout)v.findViewById(R.id.row_1_item_layout);
            TextView date, description;
            MoneyTextView deposits,expenditures,balance;
            date = (TextView) v.findViewById(R.id.txt_report_date);
            description = (TextView) v.findViewById(R.id.txt_report_description);
            deposits = (MoneyTextView) v.findViewById(R.id.txt_report_deposit);
            expenditures = (MoneyTextView) v.findViewById(R.id.txt_report_expenditure);
            //balance = (TextView) v.findViewById(R.id.txt_report_balance);

            date.setText(list.get(position).getDate()+"");
                description.setText(list.get(position).getDesc()+"");
            deposits.setAmount(Double.valueOf(list.get(position).getDeposit().toString().replace(",","")));
            if (list.get(position).getExpenditures().contentEquals("0")){
                expenditures.setAmount(Double.valueOf(list.get(position).getExpenditures().toString().replace(",","")));
            }
            else
            {
                expenditures.setAmount(Double.valueOf(list.get(position).getExpenditures().toString().replace(",","")));
            }
            if (position % 2 == 0) {
                layout.setBackgroundColor(context.getResources().getColor(R.color.tzColorWhite));
            } else {
                layout.setBackgroundColor(context.getResources().getColor(R.color.tzColorGreyLight));
            }
            if (!list.get(position).getObservation().equals("")) {
                layout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Tooltip.Builder builder = new Tooltip.Builder(v, R.style.Tooltip2)
                                .setCancelable(true)
                                .setDismissOnClick(false)
                                .setCornerRadius(20f)
                                .setGravity(Gravity.BOTTOM)
                                .setText(list.get(position).getObservation() + "");
                        builder.show();
                    }
                });
                layout.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View v) {
                        deleteDialog = new AlertDialog.Builder(getContext());
                        deleteDialog.setCancelable(false);
                        deleteDialog.setMessage(getContext().getString(R.string.delete_item) + " (" + list.get(position).getDesc() + ")");
                        deleteDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                        if (list.get(position).getExpenditures().contentEquals("0")) {
                            conn.deleteDeposits(list.get(position).getDate()+"",list.get(position).getDescription()+"");
                        }
                        else{
                           conn.deleteExpenditure(list.get(position).getDate()+"",list.get(position).getDescription()+"");
                        }
                        reloadData.reloadData();
                            }
                        });
                        deleteDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        deleteDialog.show();
                        return false;
                    }


                });
            }
        }
        return v;
    }

}
