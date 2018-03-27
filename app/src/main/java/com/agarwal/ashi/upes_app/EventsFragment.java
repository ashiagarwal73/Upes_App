package com.agarwal.ashi.upes_app;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agarwal.ashi.upes_app.activity.EventDetailsActivity;
import com.agarwal.ashi.upes_app.adapter.EventsAdapter;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;
import com.agarwal.ashi.upes_app.pojo.LayoutInformation;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    private LayoutInformation layoutInformation;
    private ArrayList<EventsInformation> events=new ArrayList<>();
    TextView dataStatus;
    ListView lV;
    EventsAdapter eventsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        LinearLayout linearLayout=(LinearLayout) inflater.inflate(R.layout.fragment_design,container,false);
        dataStatus=(TextView)linearLayout.findViewById(R.id.data_status);
        lV=(ListView)linearLayout.findViewById(R.id.list_view);

        layoutInformation=args.getParcelable("layoutinformation");
        events=args.getParcelableArrayList("events");

        if(events.size()==0) {
            dataStatus.setText(getResources().getString(R.string.nodata));
            dataStatus.setVisibility(View.VISIBLE);
        }
        else
            dataStatus.setVisibility(View.GONE);

        eventsAdapter=new EventsAdapter(events,inflater,this);
        lV.setAdapter(eventsAdapter);
        System.out.println("Setting on item click listener");
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("onItemClickListener");
                EventsInformation event=(EventsInformation)eventsAdapter.getItem(i);
                Intent intent=new Intent(getActivity(),EventDetailsActivity.class);
                intent.putExtra("layoutinformation",layoutInformation);
                intent.putExtra("event",event);
                getActivity().startActivity(intent);
            }
        });
        return linearLayout;
    }
}
