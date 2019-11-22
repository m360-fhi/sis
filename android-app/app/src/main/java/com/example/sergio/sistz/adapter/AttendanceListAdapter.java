package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.AttendanceList;

import java.util.List;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

/**
 * Created by jlgarcia on 20/03/2018.
 */

public class AttendanceListAdapter extends ArrayAdapter<AttendanceList>{

    private Context context;
    private int layout;
    private List<AttendanceList> list;

    public AttendanceListAdapter(@NonNull Context context, int resource, @NonNull List<AttendanceList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout,null);
        }
        if (list.get(position) != null) {
            LinearLayout layout = (LinearLayout) v.findViewById(R.layout.row_list_assing3);
            TextView txt1, txt2;
            CheckBox cb_absence;
            Spinner sp_reason;

            txt1 = (TextView) v.findViewById(R.id.txt1);
            txt2 = (TextView) v.findViewById(R.id.txt1);
            cb_absence = (CheckBox) v.findViewById(R.id.cb_absence);
            sp_reason = (Spinner) v.findViewById(R.id.sp_reason);

            if (position % 2 == 0) {
                layout.setBackgroundColor(context.getResources().getColor(GRAY));
            } else {
                layout.setBackgroundColor(context.getResources().getColor(WHITE));
            }
        }
        return v;
    }
}
