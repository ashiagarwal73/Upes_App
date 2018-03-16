package com.agarwal.ashi.upes_app;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.agarwal.ashi.upes_app.activity.EventDetailsActivity;
import com.agarwal.ashi.upes_app.adapter.EventsAdapter;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    private int layoutColorId;
    private ArrayList<EventsInformation> events=new ArrayList<>();
    ListView lV;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        layoutColorId=args.getInt("layoutColorId");
        events=args.getParcelableArrayList("events");
        lV=(ListView)inflater.inflate(R.layout.fragment_design,container,false);
        //add events here
        lV.setAdapter(new EventsAdapter(events,inflater));
        System.out.println("Setting on item click listener");
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("onItemClickListener");
                Intent intent=new Intent(getActivity(),EventDetailsActivity.class);
                intent.putExtra("actionbarColorId",layoutColorId);
                getActivity().startActivity(intent);
            }
        });
        return lV;
    }
}
