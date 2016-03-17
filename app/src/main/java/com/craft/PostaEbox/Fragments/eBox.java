package com.craft.PostaEbox.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craft.PostaEbox.Utils.EboxTabsAdapter;
import com.craft.PostaEbox.R;


public class eBox extends Fragment {
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_e_box, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(new EboxTabsAdapter(this.getChildFragmentManager(),getActivity()));
        return rootView;
    }

}
