package com.agarwal.ashi.upes_app;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 500060150 on 10-02-2018.
 */

public class EventsAdapter extends BaseAdapter {
    List<EventsInformation> eventsList;
    LayoutInflater inflater;
    EventsAdapter(List<EventsInformation> eventList,LayoutInflater inflater) {
        this.inflater=inflater;
        this.eventsList=eventList;
    }
    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout=inflater.inflate(R.layout.event_layout,null);
        TextView titleView=(TextView)layout.findViewById(R.id.title);
        titleView.setText(eventsList.get(i).eventName);
        return layout;
    }
}
