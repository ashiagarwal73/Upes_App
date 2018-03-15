package com.agarwal.ashi.upes_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    private int layoutColorId;
    public PagerAdapter(FragmentManager fm, int tabCount,int layoutColorId) {
        super(fm);
        this.tabCount = tabCount;
        this.layoutColorId=layoutColorId;
    }

    @Override
    public Fragment getItem(int position) {
        EventsFragment eventsFragment=new EventsFragment();
        Bundle args=new Bundle();
        args.putInt("layoutColorId",layoutColorId);
        eventsFragment.setArguments(args);
        switch (position){
            case 0 :
                //add the intitializer arguments for the fragments here
                //by args.___________ methods
                return eventsFragment;
//              EventsFragment eventsFragment = new EventsFragment();
//              return eventsFragment;
            case 1:
                return eventsFragment;
//                SocietyFragment societyFragment = new SocietyFragment();
//                return societyFragment;
            case 2:
                return eventsFragment;
            case 3:
                return eventsFragment;
            case 4:
                return eventsFragment;
            case 5:;
                return eventsFragment;
            case 6:
                return eventsFragment;
            default:
                return null;
        }

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
