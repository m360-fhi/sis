package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.SpinnerItem;

import java.util.List;

public class SpinnerItemAdapter extends ArrayAdapter<SpinnerItem> {
    private Context context;
    private List<SpinnerItem> list;

    public SpinnerItemAdapter(@NonNull Context context, @NonNull List<SpinnerItem> objects) {
        super(context, R.layout.row_spinner_item_layout, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(R.layout.row_spinner_item_layout, null);
        }
        if (list.get(position) != null) {
            TextView texto = v.findViewById(R.id.txt_spinner_text);
            texto.setText(list.get(position).getValue());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(R.layout.row_spinner_item_layout, null);
        }
        if (list.get(position) != null) {
            TextView texto = v.findViewById(R.id.txt_spinner_text);
            texto.setText(list.get(position).getValue());
        }
        return v;
    }
}
