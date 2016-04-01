package com.craft.PostaEbox.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.craft.PostaEbox.App;
import com.craft.PostaEbox.CustomActivity.RootActivity;
import com.craft.PostaEbox.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mansa on 4/1/16.
 */
public class Inbox extends Fragment {

    ArrayList<String> partNerName = new ArrayList<>();
    ArrayList<String> partNerID = new ArrayList<>();
    ArrayList<String> accountNumber = new ArrayList<>();
    SoapPrimitive response;
    public String TAG = "InboxClass";
    int _ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        final Cursor CR = RootActivity.DB.getInformation(RootActivity.DB);

        CR.moveToFirst();
        while (!CR.isAfterLast()) {
            partNerID.add(CR.getString(1));
            partNerName.add(CR.getString(2));
            accountNumber.add(CR.getString(3));

            CR.moveToNext();
        }

        // Each row in the list stores country name, currency and flag
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < CR.getCount(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt", partNerName.get(i));
            hm.put("cur", accountNumber.get(i));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"txt", "cur"};

        // Ids of views in listview_layout
        int[] to = {R.id.txt, R.id.cur};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.inbox_single_partner_row, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) rootView.findViewById(R.id.ProvidersList);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                _ID = position;
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
            //Toast.makeText(getActivity(), "Response " + response, Toast.LENGTH_LONG).show();

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("" + response);

            alertDialogBuilder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    public void ValidateAccountID() {
        String SOAP_ACTION = "http://tempuri.org/ValidateAccountID";
        String METHOD_NAME = "ValidateAccountID";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://196.43.248.10:8250/EPosta/Service.asmx?op=ValidateAccountID";
        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("SessionID", App.getInstance().SessionId);
            Request.addProperty("PartnerID", partNerID.get(_ID));
            Request.addProperty("AccountID", accountNumber.get(_ID));


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
}
