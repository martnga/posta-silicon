package com.craft.PostaEbox.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.craft.PostaEbox.App;
import com.craft.PostaEbox.GCMConfig.GCMRegistration;
import com.craft.PostaEbox.R;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mansa on 3/31/16.
 */
public class Profile extends Fragment {

    static final String KEY_PARTNER_ID = "PartnerID";
    static final String KEY_PARTNER_NAME = "PartnerName";
    public String TAG = "ProfileClass";
    SoapPrimitive  response;
    int spinnerValue;
    String[] partnerIDlist,partnerNamelist ;
    String  AccountID;

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
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        });

        return rootView;
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            ValidateAccountID();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Toast.makeText(getActivity(), "Response " + response, Toast.LENGTH_LONG).show();


        }

    }


    public void ValidateAccountID() {
        String SOAP_ACTION = "http://tempuri.org/ValidateAccountID";
        String METHOD_NAME = "ValidateAccountID";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://196.43.248.10:8250/EPosta/Service.asmx?op=ValidateAccountID";
        try {
            Log.d("Session ID", partnerNamelist[spinnerValue]);
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("SessionID", App.getInstance().SessionId);
            Request.addProperty("PartnerID", partnerIDlist[spinnerValue]);
            Request.addProperty("AccountID", AccountID);


            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            response = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, "ValidateAccountID response: " + response);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
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
