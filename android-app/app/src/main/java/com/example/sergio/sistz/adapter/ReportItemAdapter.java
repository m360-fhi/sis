package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.ReportItem;

import java.util.List;

public class ReportItemAdapter extends ArrayAdapter<ReportItem> {
    private Context context;
    private List<ReportItem> list;
    public ReportItemAdapter(@NonNull Context context, @NonNull List<ReportItem> objects) {
        super(context, R.layout.row_report_item, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_report_item, null);
        }
        if (list.get(position) != null) {
            TextView title, description;
            ImageView reportImage;
            title = (TextView) v.findViewById(R.id.txt_report_title);
            description = (TextView) v.findViewById(R.id.txt_report_description);
            reportImage = (ImageView) v.findViewById(R.id.img_report_icon);

            title.setText(list.get(position).getTitle());
            description.setText(list.get(position).getDescription());
            reportImage.setImageResource(list.get(position).getImagen());
        }
        return v;
    }
}
