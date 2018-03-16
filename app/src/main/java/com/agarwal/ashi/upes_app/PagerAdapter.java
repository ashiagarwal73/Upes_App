package com.agarwal.ashi.upes_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    private int layoutColorId;
    ArrayList<EventsInformation> events;
    public PagerAdapter(FragmentManager fm, int tabCount, int layoutColorId, ArrayList<EventsInformation> events) {
        super(fm);
        this.tabCount = tabCount;
        this.layoutColorId=layoutColorId;
        this.events=events;
    }

    @Override
    public Fragment getItem(int position) {
        EventsFragment eventsFragment=new EventsFragment();
        Bundle args=new Bundle();
        args.putInt("layoutColorId",layoutColorId);
        ArrayList<EventsInformation> selectedEvt=new ArrayList<>();
        for(int i=0;i<events.size();i++){
            EventsInformation temp=events.get(i);
            String tempstr;
            switch(position) {
                case 0 :
                    tempstr=temp.getWorkshop();
                    if(tempstr!=null) {
                        if (tempstr.equalsIgnoreCase("yes"))
                            selectedEvt.add(temp);
                    }
                    break;

                case 1 :
                    tempstr=temp.getSeminar();
                    if(tempstr!=null) {
                        if (tempstr.equalsIgnoreCase("yes"))
                            selectedEvt.add(temp);
                    }
                    break;

                case 2 :
                    tempstr=temp.getCompetition();
                    if(tempstr!=null) {
                        if (tempstr.equalsIgnoreCase("yes"))
                            selectedEvt.add(temp);
                    }
                    break;

                case 3 :
                    tempstr=temp.getCultural();
                    if(tempstr!=null) {
                        if (tempstr.equalsIgnoreCase("yes"))
                            selectedEvt.add(temp);
                    }
                    break;

                case 4 :
                    tempstr=temp.getSports();
                    if(tempstr!=null) {
                        if (tempstr.equalsIgnoreCase("yes"))
                            selectedEvt.add(temp);
                    }
                    break;

                case 5 :
                    tempstr=temp.getWebminar();
                    if(tempstr!=null) {
                        if (tempstr.equalsIgnoreCase("yes"))
                            selectedEvt.add(temp);
                    }
                    break;

                default :
                    break;
            }
        }
        args.putParcelableArrayList("events",selectedEvt);
        Log.i("PagerAdapter","selected Events in Pager adapter : "+selectedEvt.size());
        eventsFragment.setArguments(args);
        return eventsFragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabText[position];
    }
    //String[] tabText = new String[]{"School of Computer Science","School of Engineering","School of EventsFragment","School of Law","School of Business"};
    //String[] tabText = new String[]{"SOCS","SOE","SOD","SOL","SOB"};
    String[] tabText=new String[]{"Workshops","Seminars","Competitions","Cultural","Sports","Webminars"};
}
