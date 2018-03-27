package com.agarwal.ashi.upes_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agarwal.ashi.upes_app.R;

/**
 * Created by Ashi on 18-03-2018.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] arrayList;
    LayoutInflater layoutInflater;
    public CustomAdapter(Context context, String[] arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.customadapter,null);
        TextView textView=convertView.findViewById(R.id.child_name);
        textView.setText(arrayList[position]);
        return convertView;
    }
}
