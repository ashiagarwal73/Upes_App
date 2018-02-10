package com.agarwal.ashi.upes_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                  EventsFragment eventsFragment =new EventsFragment();
                  //add the intitializer arguments for the fragments here
                  return eventsFragment;
//                EventsFragment eventsFragment = new EventsFragment();
//                return eventsFragment;
            case 1:
                  EventsFragment eventsFragment1 =new EventsFragment();
                  return eventsFragment1;
//                SocietyFragment societyFragment = new SocietyFragment();
//                return societyFragment;
            case 2:
                EventsFragment eventsFragment2 =new EventsFragment();
                return eventsFragment2;
            case 3:
                EventsFragment eventsFragment3 =new EventsFragment();
                return eventsFragment3;
            case 4:
                EventsFragment eventsFragment4 =new EventsFragment();
                return eventsFragment4;
            case 5:
                EventsFragment eventsFragment5 =new EventsFragment();
                return eventsFragment5;
            case 6:
                EventsFragment eventsFragment6 =new EventsFragment();
                return eventsFragment6;
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
