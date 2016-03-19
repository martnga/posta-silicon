package com.craft.PostaEbox.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.craft.PostaEbox.App;
import com.craft.PostaEbox.CustomActivity.MainActivity;
import com.craft.PostaEbox.CustomActivity.Partners_Services;
import com.craft.PostaEbox.Utils.ItemClickSupport;
import com.craft.PostaEbox.Utils.PartnersDataAdapter;
import com.craft.PostaEbox.R;
import com.craft.PostaEbox.Utils.XMLParser;
import com.craft.PostaEbox.model.PartnersModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class Partners extends Fragment {

    TextView moreProvider;
    String KEY_PARTNER_ID = "PartnerID";
    String KEY_PARTNER_NAME = "PartnerName";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_partners, container, false);
        moreProvider =(TextView) rootView.findViewById(R.id.moreProvider);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);


        ArrayList<PartnersModel> partners = prepareData();
        PartnersDataAdapter adapter = new PartnersDataAdapter(getContext(),partners);
        recyclerView.setAdapter(adapter);

        //Click Listener
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                Bundle b = new Bundle();
                b.putString("title", MainActivity.partnersMenuItems.get(position).get(KEY_PARTNER_NAME));
                b.putString("partnerID", MainActivity.partnersMenuItems.get(position).get(KEY_PARTNER_ID));
                Intent i = new Intent(getActivity(), Partners_Services.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        return rootView;
    }

    private ArrayList<PartnersModel> prepareData(){

        ArrayList<PartnersModel> partnersModelArrayList = new ArrayList<>();
        for(int i=0;i<MainActivity.partnersMenuItems.size();i++){
            PartnersModel partnersModel = new PartnersModel();

            partnersModel.setPartner_name(MainActivity.partnersMenuItems.get(i).get(KEY_PARTNER_NAME));
            //partnersModel.setPartner_image_url(android_image_urls[i]);
            partnersModelArrayList .add(partnersModel);
        }
        return partnersModelArrayList;
    }






}

