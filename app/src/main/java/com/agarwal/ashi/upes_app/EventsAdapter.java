package com.agarwal.ashi.upes_app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 500060150 on 10-02-2018.
 */

public class EventsAdapter extends BaseAdapter {
    private List<EventsInformation> events;
    private LayoutInflater inflater;
    private Fragment fragment;
    EventsAdapter(List<EventsInformation> events, LayoutInflater inflater, Fragment fragment) {
        this.inflater=inflater;
        this.events=events;
        this.fragment=fragment;
    }
    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout=inflater.inflate(R.layout.event_layout,null);
        TextView titleView=(TextView)layout.findViewById(R.id.title);
        TextView organizer=(TextView)layout.findViewById(R.id.organizer);
        TextView contact=(TextView)layout.findViewById(R.id.contact);
        TextView date=(TextView)layout.findViewById(R.id.date);
        ImageView iV=(ImageView)layout.findViewById(R.id.event_icon);

        titleView.setText(events.get(i).getEventName());
        organizer.setText(events.get(i).getOrganiser());
        contact.setText(events.get(i).getContact());
        date.setText(events.get(i).getDate());

        Glide.with(fragment).load(events.get(i).getImage()).into(iV);

        return layout;
    }
}
