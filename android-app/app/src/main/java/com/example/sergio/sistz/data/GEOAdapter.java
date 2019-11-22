package com.example.sergio.sistz.data;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Sergio on 2/22/2016.
 */
public class GEOAdapter extends ArrayAdapter<GEO>{
    private Context context;
    private int layout;
    private List<GEO> data;

    public GEOAdapter(Context context, int resource, List<GEO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.data = objects;
    }

}
