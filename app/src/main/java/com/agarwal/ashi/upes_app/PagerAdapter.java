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
                  Design design=new Design();
                  return design;
//                EventsFragment eventsFragment = new EventsFragment();
//                return eventsFragment;
            case 1:
                  Design design1=new Design();
                  return  design1;
//                SocietyFragment societyFragment = new SocietyFragment();
//                return societyFragment;
            case 2:
                Design design2=new Design();
                return  design2;
            case 3:
                Design design3=new Design();
                return  design3;
            case 4:
                Design design4=new Design();
                return  design4;
            case 5:
                Design design5=new Design();
                return  design5;
            case 6:
                Design design6=new Design();
                return design6;
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
    //String[] tabText = new String[]{"School of Computer Science","School of Engineering","School of Design","School of Law","School of Business"};
    //String[] tabText = new String[]{"SOCS","SOE","SOD","SOL","SOB"};
    String[] tabText=new String[]{"Workshops","Seminars","Competitions","Cultural","Sports","Webminars"};
}
