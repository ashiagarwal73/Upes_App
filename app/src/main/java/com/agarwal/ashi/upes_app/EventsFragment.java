package com.agarwal.ashi.upes_app;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    Bundle args;
    ListView lV;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        args=getArguments();
        lV=(ListView)inflater.inflate(R.layout.fragment_design,container,false);
        ArrayList<Event> eventsList=new ArrayList<Event>();
        eventsList.add(new Event("Capture The Flag"));
        eventsList.add(new Event("Capture The Flag"));
        eventsList.add(new Event("Capture The Flag"));
        eventsList.add(new Event("Capture The Flag"));
        eventsList.add(new Event("Capture The Flag"));
        eventsList.add(new Event("Capture The Flag"));
        lV.setAdapter(new EventsAdapter(eventsList,inflater));
        System.out.println("Setting on item click listener");
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("onItemClickListener");
                getActivity().startActivity(new Intent(getActivity(),EventDetailsActivity.class));
            }
        });
        return lV;
    }
}
