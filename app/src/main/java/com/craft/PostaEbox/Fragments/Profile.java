package com.craft.PostaEbox.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.craft.PostaEbox.GCMConfig.GCMRegistration;
import com.craft.PostaEbox.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * Created by mansa on 3/31/16.
 */
public class Profile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        MaterialBetterSpinner mPartnersSpinner = (MaterialBetterSpinner) rootView.findViewById(R.id.partners_spinner);
        String[] list = new String[] {
                "Belgium", "France", "Italy", "Germany", "Spain"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list);

        mPartnersSpinner.setAdapter(adapter);



       /* b.putString("title", GCMRegistration.partnersMenuItems.get(position).get(KEY_PARTNER_NAME));
        b.putString("partnerID", GCMRegistration.partnersMenuItems.get(position).get(KEY_PARTNER_ID));*/
        return rootView;
    }
}
