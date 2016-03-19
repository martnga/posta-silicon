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
    String TAG = "Partners_Class Response";
    SoapPrimitive resultString;

    ArrayList<HashMap<String, String>> partnersMenuItems = new ArrayList<HashMap<String, String>>();
    // XML node keys
    static final String KEY_PARTNER = "Partner"; // parent node
    static final String KEY_PARTNER_ID = "PartnerID";
    static final String KEY_PARTNER_NAME = "PartnerName";
    static final String KEY_ACCOUNT_QUERY = "AccountQuery";



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

        //Intiate XML fetching
        AsyncCallWS task = new AsyncCallWS();
        task.execute();

        ArrayList<PartnersModel> partners = prepareData();
        PartnersDataAdapter adapter = new PartnersDataAdapter(getContext(),partners);
        recyclerView.setAdapter(adapter);

        //Click Listener
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                Bundle b = new Bundle();
                b.putString("title", partnersMenuItems.get(position).get(KEY_PARTNER_NAME));
                b.putString("partnerID", partnersMenuItems.get(position).get(KEY_PARTNER_ID));
                Intent i = new Intent(getActivity(), Partners_Services.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Intiate parners fetching
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private ArrayList<PartnersModel> prepareData(){

        ArrayList<PartnersModel> partnersModelArrayList = new ArrayList<>();
        for(int i=0;i<partnersMenuItems.size();i++){
            PartnersModel partnersModel = new PartnersModel();

            partnersModel.setPartner_name(partnersMenuItems.get(i).get(KEY_PARTNER_NAME));
            //partnersModel.setPartner_image_url(android_image_urls[i]);
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
            fetchPartners();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
           // Toast.makeText(getActivity(), "Response" + resultString.toString(), Toast.LENGTH_LONG).show();

            XMLParser parser = new XMLParser();
            String xml = resultString.toString(); // getting XML
            Document doc = parser.getDomElement(xml); // getting DOM element

            NodeList nl = doc.getElementsByTagName(KEY_PARTNER);
            // looping through all item nodes <item>

            for (int i = 0; i < nl.getLength(); i++) {

                // creating add items to HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key => value
                map.put(KEY_PARTNER_ID, parser.getValue(e, KEY_PARTNER_ID));
                map.put(KEY_PARTNER_NAME, parser.getValue(e, KEY_PARTNER_NAME));
               // map.put(KEY_ACCOUNT_QUERY, parser.getValue(e, KEY_ACCOUNT_QUERY));

                // adding HashList to ArrayList
                partnersMenuItems.add(map);
            }


        }

    }


    public void fetchPartners() {
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

            Log.i(TAG, "response: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }



}

