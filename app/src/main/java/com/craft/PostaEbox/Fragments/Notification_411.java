package com.craft.PostaEbox.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.craft.PostaEbox.Utils.Notification_411_RowAdapter;
import com.craft.PostaEbox.R;


public class Notification_411 extends Fragment {
    ListView List_Notification_411;
    String [] Titles={"Voter registration","Voter registration","Voter registration"};
    String [] Bodies={"The NCAJ os establised under section 34...","The NCAJ os establised under section 34...","The NCAJ os establised under section 34..."};
    String [] Froms={"IEBC","IEBC","IEBC"};
    String [] Durations={"1 hour ago","1 hour ago","1 hour ago"};
    Notification_411_RowAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_notification_411, container, false);
        List_Notification_411=(ListView) rootView.findViewById(R.id.List_Notification_411);
        adapter=new Notification_411_RowAdapter(getActivity(),Titles,Bodies,Froms,Durations);
        List_Notification_411.setAdapter(adapter);
        return rootView;
    }


}
