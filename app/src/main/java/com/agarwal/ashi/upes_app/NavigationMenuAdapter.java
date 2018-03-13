package com.agarwal.ashi.upes_app;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 500060150 on 10-03-2018.
 */

public class NavigationMenuAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<String> menuNames;
    ArrayList<ArrayList<String>> schools;
    NavigationMenuAdapter(Context context,ArrayList<String> menuNames,ArrayList<ArrayList<String>> schools){
        this.context=context;
        this.schools=schools;
        this.menuNames=menuNames;
    }

    @Override
    public int getGroupCount() {
        return schools.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition==0) // Home
            return 0;
        else
            return schools.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return schools.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return schools.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        switch(groupPosition) {
            case 0 : return context.getResources().getInteger(R.integer.home);
            case 1 : return context.getResources().getInteger(R.integer.socs);
            case 2 : return context.getResources().getInteger(R.integer.soe);
            case 3 : return context.getResources().getInteger(R.integer.sob);
            case 4 : return context.getResources().getInteger(R.integer.sol);
            case 5 : return context.getResources().getInteger(R.integer.sod);
            default: return groupPosition;
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (10000000)*groupPosition+childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        LinearLayout groupHeader;
        TextView tV;
        if(convertView==null) {
            groupHeader=(LinearLayout)LayoutInflater.from(context).inflate(R.layout.nav_menu_group_header,null);
        }
        else {
            groupHeader=(LinearLayout)convertView;
        }
        tV=(TextView)((ViewGroup)groupHeader.getChildAt(0)).getChildAt(0);
        tV.setText(menuNames.get(groupPosition));

        return groupHeader;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        LinearLayout groupItem;
        ConstraintLayout innerContainer;
        TextView tV;
        ImageView imageView;
        if(convertView==null) {
            groupItem=(LinearLayout)LayoutInflater.from(context).inflate(R.layout.nav_menu_group_item,null);
        }
        else {
            groupItem=(LinearLayout)convertView;
        }
        innerContainer=(ConstraintLayout)groupItem.getChildAt(0);
        imageView=(ImageView)innerContainer.getChildAt(0);
        tV=(TextView)innerContainer.getChildAt(1);
        tV.setText(schools.get(groupPosition).get(childPosition));

        return groupItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
