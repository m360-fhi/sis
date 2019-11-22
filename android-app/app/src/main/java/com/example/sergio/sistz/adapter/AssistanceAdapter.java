package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sergio.sistz.Assistance;
import com.example.sergio.sistz.R;
import java.util.ArrayList;
import java.util.List;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

public class AssistanceAdapter extends ArrayAdapter<Assistance> implements View.OnClickListener {

    private Context mContext;
    private int mResource;



    public AssistanceAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Assistance> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }


    private String fixSizeName(String x,int n)
    {
        String ret = " ";
        try {
            ret = x.substring(0, Math.min(x.length(), n));
            if (ret.length() >= n)
                return ret;
            else if (ret.length() < n)
                return String.format("%1$-" + n + "s", x);
        }catch (Exception ex)
        {
            ret = " ";
        }
        return ret;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String id = getItem(position).getID(),
        Name = getItem(position).getName();

        String Sunday = getItem(position).getSunday(),
                Monday = getItem(position).getMonday(),
                Tuesday = getItem(position).getTuesday(),
                Wednesday = getItem(position).getWednesday(),
                Thursday = getItem(position).getThursday(),
                Friday = getItem(position).getFriday(),
                Saturday = getItem(position).getSaturday();

        View v = convertView;
        Assistance asistencia = new Assistance(id,Name,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday);
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            v = inflater.inflate(mResource,null);
        }


        TextView txtName = (TextView) convertView.findViewById(R.id.texLastNameName);
        TextView txtMon = (TextView) convertView.findViewById(R.id.textMonday);
        TextView txtTue = (TextView) convertView.findViewById(R.id.textTuesday);
        TextView txtWed = (TextView) convertView.findViewById(R.id.textWednesday);
        TextView txtThur = (TextView) convertView.findViewById(R.id.textThursday);
        TextView txtFri = (TextView) convertView.findViewById(R.id.textFriday);
        TextView txtSat = (TextView) convertView.findViewById(R.id.textSaturday);
        TextView txtSon = (TextView) convertView.findViewById(R.id.textSunday);


        txtName.setText(fixSizeName(Name,35));
        txtMon.setText(fixSizeName(Monday,2));
        txtTue.setText(fixSizeName(Tuesday,2));
        txtWed.setText(fixSizeName(Wednesday,2));
        txtThur.setText(fixSizeName(Thursday,2));
        txtFri.setText(fixSizeName(Friday,2));
        txtSat.setText(fixSizeName(Saturday,2));
        txtSon.setText(fixSizeName(Sunday,2));


        return convertView;
    }




    @Override
    public void onClick(View v) {

    }
}
