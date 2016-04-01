package com.craft.PostaEbox.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craft.PostaEbox.R;

/**
 * Created by mansa on 4/1/16.
 */
public class Inbox extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_inbox, container, false);
        return rootView;
    }

}
