package com.example.sergio.sistz.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.Alerts;

import java.util.List;


public class AlertsAdapter extends ArrayAdapter<Alerts> {

    private Context context;
    private int layout;
    private List<Alerts> list;

    public AlertsAdapter(@NonNull Context context, int resource, @NonNull List<Alerts> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.list = objects;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout,null);
        }

        if (list.get(position) != null) {
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.alerts_row_layout);
            ImageView img;
            TextView txt;


            img = (ImageView) v.findViewById(R.id.imgAlert);
            txt = (TextView) v.findViewById(R.id.txtAlert);
            img.setImageResource(list.get(position).getImage());
            txt.setText(list.get(position).getText());

        }
        return v;
    }

}
