package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sergio.sistz.data.FinanceTransaction;

import com.example.sergio.sistz.R;


import com.example.sergio.sistz.util.MoneyTextView;

import java.util.List;

/**
 * Created by pabloyac on 3/20/18.
 */

public class FinanceDepositsArrayAdapter extends ArrayAdapter<FinanceTransaction> {
    private Context context;
    private int layout;
    private List<FinanceTransaction> list;
    private boolean type;

    public FinanceDepositsArrayAdapter(@NonNull Context context, int resource, @NonNull List<FinanceTransaction> objects, boolean type) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.list = objects;
        this.type = type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        if (list.get(position)!= null) {
            TextView date, description;
            MoneyTextView amount;
            date = (TextView) v.findViewById(R.id.txt_finance_date);
            description = (TextView) v.findViewById(R.id.txt_finance_description);
            amount = (MoneyTextView) v.findViewById(R.id.txt_finance_amount);

            date.setText(list.get(position).getDate()+"");
            description.setText(list.get(position).getDesc()+"");
            if (type) {
                amount.setAmount(Double.valueOf(list.get(position).getExpenditures()));
            } else {
                amount.setAmount(Double.valueOf(list.get(position).getDeposit()));
            }


        }
        return v;
    }

}
