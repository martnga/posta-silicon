package com.craft.PostaEbox.Utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.craft.PostaEbox.Fragments.Documents;
import com.craft.PostaEbox.Fragments.Ebox_Notification;

/**
 * Created by Oliver on 2/18/2016.
 */
public class EboxTabsAdapter extends FragmentPagerAdapter {
    Context context;
    private String[] tabs = { "NOTIFICATION", "DOCUMENT"};
    public EboxTabsAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new Ebox_Notification();
            case 1:
                return new Documents();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
