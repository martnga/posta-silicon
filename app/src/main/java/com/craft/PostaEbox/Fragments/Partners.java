package com.craft.PostaEbox.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.craft.PostaEbox.Utils.PartnersDataAdapter;
import com.craft.PostaEbox.R;
import com.craft.PostaEbox.models.PartnersModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class Partners extends Fragment {

    TextView moreProvider;
    String TAG = "Partners_Class Response";
    SoapPrimitive resultString;

    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread"
    };


    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png"

    };

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
        return rootView;
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


    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            login();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Toast.makeText(getActivity(), "Response" + resultString.toString(), Toast.LENGTH_LONG).show();
        }

    }


    public void login() {
        String SOAP_ACTION = "http://tempuri.org/GetListOfPartners";
        String METHOD_NAME = "GetListOfPartners";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://196.43.248.10:8250/EPosta/Service.asmx?op=GetListOfPartners";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("TypeOfPartner", "utility");

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, "Login response: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
