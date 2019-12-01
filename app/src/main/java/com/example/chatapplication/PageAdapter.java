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
                UserTab tab1 = new UserTab();
                return tab1;
            case 1:
                GroupTab tab2 = new GroupTab();
                return tab2;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return NumOfTabs;
    }
}
