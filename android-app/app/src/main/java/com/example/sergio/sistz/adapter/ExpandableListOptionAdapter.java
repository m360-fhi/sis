package com.example.sergio.sistz.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergio.sistz.R;
import com.example.sergio.sistz.data.ExpandableItemMenu;

import java.util.ArrayList;

public class ExpandableListOptionAdapter extends BaseExpandableListAdapter {

    public ArrayList<ExpandableItemMenu> groupItem;
    public ArrayList<ExpandableItemMenu> tempChild;
    public ArrayList<Object> Childtem = new ArrayList<Object>();
    public LayoutInflater minflater;
    public Activity activity;

    public ExpandableListOptionAdapter(ArrayList<ExpandableItemMenu> grList, ArrayList<Object> childItem) {
        groupItem = grList;
        this.Childtem = childItem;
    }

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (ArrayList<ExpandableItemMenu>) Childtem.get(groupPosition);
        TextView text = null;
        ImageView image = null;
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.expandable_list_submenu_row_layout, null);
        }
        text = (TextView) convertView.findViewById(R.id.txtSubMenu);
        image = (ImageView) convertView.findViewById(R.id.childImage);
        text.setText(tempChild.get(childPosition).getText());
        image.setImageResource(tempChild.get(childPosition).getImage());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) Childtem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return groupItem.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView textMenu;
        ImageView iconoMenu;
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.expandable_list_menu_row_layout, null);
        }
        textMenu = (TextView) convertView.findViewById(R.id.txtMenu);
        iconoMenu = (ImageView) convertView.findViewById(R.id.menuImage);
        textMenu.setText(groupItem.get(groupPosition).getText());
        iconoMenu.setImageResource(groupItem.get(groupPosition).getImage());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
