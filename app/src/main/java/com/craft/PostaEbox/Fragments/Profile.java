package com.craft.PostaEbox.Fragments;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.craft.PostaEbox.CustomActivity.RootActivity;
import com.craft.PostaEbox.GCMConfig.GCMRegistration;
import com.craft.PostaEbox.R;



import java.util.ArrayList;


/**
 * Created by mansa on 3/31/16.
 */
public class Profile extends Fragment {

    static final String KEY_PARTNER_ID = "PartnerID";
    static final String KEY_PARTNER_NAME = "PartnerName";


    int spinnerValue;
    String[] partnerIDlist,partnerNamelist ;
    String  AccountID;
    int i  = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        final Spinner mPartnersSpinner = (Spinner) rootView.findViewById(R.id.partners_spinner);
        TextView mSubscribeBtn = (TextView) rootView.findViewById(R.id.subscribe_btn);
        final EditText mAccountNumberID = (EditText) rootView.findViewById(R.id.account_number_txt);
        ArrayList<String> partNerName = new ArrayList<>();
        ArrayList<String> partNerID = new ArrayList<>();

        partNerName.add("Select Provider");
        partNerID.add("Select ID");

        for(int i = 0; i < GCMRegistration.partnersMenuItems.size(); i++) {
            partNerName.add(GCMRegistration.partnersMenuItems.get(i).get(KEY_PARTNER_NAME));
            partNerID.add(GCMRegistration.partnersMenuItems.get(i).get(KEY_PARTNER_ID));


        }

        partnerNamelist = partNerName.toArray(new String[partNerName.size()]);
        partnerIDlist = partNerID.toArray(new String[partNerID.size()]);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, partnerNamelist){
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }};
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mPartnersSpinner.setAdapter(adapter);

        mPartnersSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        mSubscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountID = mAccountNumberID.getText().toString().trim();

                if(AccountID.length() > 5 && spinnerValue != 0) {
                    RootActivity.DB.saveInfo(RootActivity.DB, 0, partnerIDlist[spinnerValue], partnerNamelist[spinnerValue], AccountID);
                    mAccountNumberID.setText("");
                    mPartnersSpinner.setSelection(0);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(getActivity(), "Subscription Sent",Toast.LENGTH_LONG).show();

                }else{
                    mAccountNumberID.setError("Invalid Account");
                }
            }
        });

        return rootView;
    }


    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            spinnerValue = pos;
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
            Toast.makeText(getActivity(), "Please Select One Provider", Toast.LENGTH_LONG).show();
        }
    }



}
