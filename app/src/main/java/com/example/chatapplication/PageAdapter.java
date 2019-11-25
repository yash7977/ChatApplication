package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    int NumOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                OnlineUserTab tab2 = new OnlineUserTab();
                return tab2;
            case 1:
                GroupsActivityTab tab1 = new GroupsActivityTab();
                return tab1;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return NumOfTabs;
    }
}
