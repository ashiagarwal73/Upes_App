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
        else {
            int size = schools.get(groupPosition).size();
            System.out.println("getChildrenCount : "+size);
            return size;
        }
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
        ConstraintLayout groupHeader;
        TextView tV;
        ImageView iV;
        ImageView arrow;
        if(convertView==null) {
            groupHeader=(ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.nav_menu_group_header,null);
        }
        else {
            groupHeader=(ConstraintLayout) convertView;
        }
        iV=(ImageView)groupHeader.getChildAt(0);
        tV=(TextView)groupHeader.getChildAt(1);
        tV.setText(menuNames.get(groupPosition));
        if(groupPosition==0)
            iV.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_black_24dp,null));
        else
            iV.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_school_black_24dp));
        arrow = (ImageView) groupHeader.getChildAt(2);
        if(getChildrenCount(groupPosition)!=0) {
            arrow.setVisibility(View.VISIBLE);
        }
        else { // required because the view object received could be a recycled one
            arrow.setVisibility(View.INVISIBLE);
        }
        return groupHeader;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ConstraintLayout groupItem;
        TextView tV;
        if(convertView==null) {
            groupItem=(ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.nav_menu_group_item,null);
        }
        else {
            groupItem=(ConstraintLayout) convertView;
        }
        tV=(TextView)groupItem.getChildAt(0);
        tV.setText(schools.get(groupPosition).get(childPosition));
        return groupItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
