package com.craft.PostaEbox.Utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.craft.PostaEbox.Fragments.Files;
import com.craft.PostaEbox.Fragments.Notification_411;
import com.craft.PostaEbox.Fragments.Partners;

/**
 * Created by Oliver on 2/17/2016.
 */
public class HomeTabsAdapter extends FragmentPagerAdapter {

    Context context;
    private String[] tabs = {"PARTNERS", "FILES", "411"};

    public HomeTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new Partners();
            case 1:
               return new Files();
            case 2:
              return new Notification_411();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
