package com.craft.PostaEbox.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.craft.PostaEbox.Utils.Ebox_Notification_Row;
import com.craft.PostaEbox.R;


public class Ebox_Notification extends Fragment {
    ListView Notification_Ebox_List;
    Ebox_Notification_Row adapter;
    String [] Title={"Court Surmon","Court Surmon","Court Surmon"};
    String [] Body={"The NCAJ is established under Section 34 of the Judicial Service Act(No 1 of 2011)","The NCAJ is established under Section 34 of the Judicial Service Act(No 1 of 2011)","The NCAJ is established under Section 34 of the Judicial Service Act(No 1 of 2011)"};
    String [] Duration={"1 hour ago","1 hour ago","1 hour ago"};
    String [] Action={"3\nAttachments","3\nAttachments","3\nAttachments"};
    int [] Images={R.drawable.judiciary,R.drawable.judiciary,R.drawable.judiciary};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_ebox__notification, container, false);
        Notification_Ebox_List=(ListView) rootView.findViewById(R.id.Notification_Ebox_List);
        adapter=new Ebox_Notification_Row(getActivity(),Title,Body,Duration,Action,Images);
        Notification_Ebox_List.setAdapter(adapter);
        Notification_Ebox_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        return rootView;
    }

}
