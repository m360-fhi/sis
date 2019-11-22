package com.example.sergio.sistz.adapter;

import android.content.Context;
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
import com.example.sergio.sistz.data.TeacherStudentItem;

import java.util.List;


public class TeacherStudentListAdapter extends ArrayAdapter<TeacherStudentItem> {
    private Context context;
    private int layout;
    private List<TeacherStudentItem> list;

    public TeacherStudentListAdapter(@NonNull Context context, int resource, @NonNull List<TeacherStudentItem> objects) {
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
        TeacherStudentItem item = list.get(position);
        if (item  != null) {
            TextView txt1, txt2;
            ImageView img3;

            txt1 = (TextView) v.findViewById(R.id.txt1);
            txt2 = (TextView) v.findViewById(R.id.txt2);
            img3 = (ImageView) v.findViewById(R.id.img3);

            txt1.setText(item.getId());
            txt2.setText(item.getFull_name());

            if(item.getActive().equals("0")) {
                img3.setImageResource(R.drawable.data_transfer_done);
            } else {
                img3.setImageResource(R.drawable.data_transfer_ready);
            }
        }

        return v;
    }

}
