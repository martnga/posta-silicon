package com.craft.PostaEbox.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craft.PostaEbox.Utils.PartnersDataAdapter;
import com.craft.PostaEbox.R;
import com.craft.PostaEbox.models.PartnersModel;

import java.util.ArrayList;

public class Partners extends Fragment {

    FloatingActionButton moreProviderFab;
    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_partners, container, false);
        moreProviderFab =(FloatingActionButton) rootView.findViewById(R.id.moreProviderFab);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<PartnersModel> partners = prepareData();
        PartnersDataAdapter adapter = new PartnersDataAdapter(getContext(),partners);
        recyclerView.setAdapter(adapter);
        return rootView;
    }


    private void initViews(){


    }
    private ArrayList<PartnersModel> prepareData(){

        ArrayList<PartnersModel> partnersModelArrayList = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            PartnersModel partnersModel = new PartnersModel();
            partnersModel.setPartner_name(android_version_names[i]);
            partnersModel.setPartner_image_url(android_image_urls[i]);
            partnersModelArrayList .add(partnersModel);
        }
        return partnersModelArrayList;
    }
}
