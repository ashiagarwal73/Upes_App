package com.agarwal.ashi.upes_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agarwal.ashi.upes_app.R;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;

import java.util.List;

/**
 * Created by 500060150 on 10-02-2018.
 */

public class EventsAdapter extends BaseAdapter {
    List<EventsInformation> events;
    LayoutInflater inflater;
    public EventsAdapter(List<EventsInformation> eventList, LayoutInflater inflater) {
        this.inflater=inflater;
        this.events=eventList;
    }
    @Override
    public int getCount() {
        return events.size();
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
        titleView.setText(events.get(i).getEventName());
        return layout;
    }
}
