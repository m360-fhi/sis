package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergio.sistz.R;

import java.util.HashMap;
import java.util.List;

public class TeacherAttendanceAdapter extends ArrayAdapter<HashMap<String, String>> {

    private List<HashMap<String, String>> list;
    private int layout;
    private Context context;


    public TeacherAttendanceAdapter(@NonNull Context context,  @NonNull List<HashMap<String, String>> objects) {
        super(context, R.layout.row_list_assing3, objects);
        this.list = objects;
        this.layout = R.layout.row_list_assing3;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        TextView txt1;
        TextView txt2;
        CheckBox cb;
        final Spinner sp;
        if (v == null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(layout, null);
        }
        if (list.get(position) != null) {
            txt1 = (TextView) v.findViewById(R.id.txt1);
            txt2 = (TextView) v.findViewById(R.id.txt2);
            cb = (CheckBox) v.findViewById(R.id.cb_absence);
            sp = (Spinner) v.findViewById(R.id.sp_reason);
            txt1.setText(list.get(position).get("code"));
            txt2.setText(list.get(position).get("fullname"));
            if (list.get(position).get("absence").equals("0")) {
                sp.setEnabled(true);
                sp.setSelection(Integer.parseInt(list.get(position).get("reason")), false);
                cb.setChecked(false);
            } else {
                sp.setEnabled(false);
                sp.setSelection(0,false);
                cb.setChecked(true);
            }
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (sp!= null) {
                        if (isChecked) {
                            sp.setSelection(0, false);
                        }
                        sp.setEnabled(!isChecked);
                    }
                }
            });
        }
        return v;
    }
}
