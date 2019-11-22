package com.example.sergio.sistz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.TableData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ReporTableDataAdapter extends ArrayAdapter<TableData> {
    private Context context;
    private List<TableData> list;
    private ArrayList<TextView> arrayText;
    private ArrayList<ImageView> arrayImg;
    public ReporTableDataAdapter(@NonNull Context context, @NonNull List<TableData> objects) {
        super(context, R.layout.row_table_information, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(R.layout.row_table_information, null);
        }
        if (list.get(position) != null) {
            v.setBackgroundColor(Color.TRANSPARENT);
            arrayText = new ArrayList<TextView>();
            arrayText.add((TextView)v.findViewById(R.id.table_text_1));
            arrayText.add((TextView)v.findViewById(R.id.table_text_2));
            arrayText.add((TextView) v.findViewById(R.id.table_text_3));
            arrayText.add((TextView) v.findViewById(R.id.table_text_4));
            arrayText.add((TextView) v.findViewById(R.id.table_text_5));
            arrayText.add((TextView) v.findViewById(R.id.table_text_6));
            arrayText.add((TextView) v.findViewById(R.id.table_text_7));
            arrayText.add((TextView) v.findViewById(R.id.table_text_8));
            arrayText.add((TextView) v.findViewById(R.id.table_text_9));
            arrayText.add((TextView) v.findViewById(R.id.table_text_10));

            arrayImg = new ArrayList<>(10);
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_1));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_2));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_3));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_4));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_5));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_6));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_7));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_8));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_9));
            arrayImg.add((ImageView)v.findViewById(R.id.table_img_10));

            setTextChanges(arrayText, arrayImg, position);
        }
        return v;
    }

    private void setTextChanges(List<TextView> texto, List<ImageView> images, int j) {
        TableData.TableDataItem item;
        TextView textView;
        ImageView imageView;
        for (int i = 0; i < 10; i++) {
            if (i >= list.get(j).getSize()) {
                texto.get(i).setVisibility(View.GONE);
                images.get(i).setVisibility(View.GONE);
            } else {
                item = list.get(j).getRow().get(i);
                if(item.getType() == TableData.TableDataItem.TEXT) {
                    images.get(i).setVisibility(View.GONE);
                    textView = texto.get(i);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(item.getValue());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, item.getWeight());
                    textView.setLayoutParams(params);
                    textView.setBackgroundColor(Color.parseColor( String.format("#%08X", item.getBackGroundColor())));
                    textView.setTextColor(Color.parseColor(String.format("#%08X", item.getFontColor())));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) item.getFontSize());
                    textView.setGravity(item.getAligment());
                } else if (item.getType() == TableData.TableDataItem.IMG){
                    texto.get(i).setVisibility(View.GONE);
                    imageView = images.get(i);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setBackgroundColor(Color.parseColor( String.format("#%08X", item.getBackGroundColor())));
                    imageView.setImageDrawable(item.getValueImg());
                }

            }
        }
    };
}
