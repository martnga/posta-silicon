package com.craft.PostaEbox.CustomActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class Partners_Services extends AppCompatActivity {
    CollapsingToolbarLayout toolbar_layout;
    String TAG = "Partners_Services_Class Response";
    String userphone, account_id, paymentAmount, mPartnerID, title;
    SoapObject response;

    public static final String PHONE_NUMBER = "mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners__services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Getting phone number from shared prefences
        SharedPreferences prefs = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        userphone = prefs.getString(PHONE_NUMBER, "");

        final EditText mAccountID = (EditText) findViewById(R.id.account_id);
        final EditText mAmountToPay = (EditText) findViewById(R.id.amount_txt);
        final TextView mPayBtn = (TextView) findViewById(R.id.pay_btn);

        toolbar_layout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Bundle bundle = getIntent().getExtras();
        mPartnerID = bundle.getString("partnerID");
        title = bundle.getString("title");

        // toolbar_layout.setBackground(getResources().getDrawable(bundle.getInt("Partner_Logo")));
        toolbar_layout.setTitle(title);

        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAccountID.getText().toString().isEmpty()) {
                    mAccountID.setError("Account ID required");
                } else if (mAmountToPay.getText().toString().isEmpty()) {
                    mAmountToPay.setError("Amount required");
                } else {

                    account_id = mAccountID.getText().toString().trim();
                    paymentAmount = mAmountToPay.getText().toString().trim();

                    //Make Payments.
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();

                }
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            fetchSessionID();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            //Toast.makeText(Partners_Services.this, "Response" + response.getName(), Toast.LENGTH_LONG).show();
            Toast.makeText(Partners_Services.this, "Please wait. Transaction is being processed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Partners_Services.this, GCMRegistration.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            finish();


        }

    }


    public void fetchSessionID() {
        String SOAP_ACTION = "http://tempuri.org/MakePayment";
        String METHOD_NAME = "MakePayment";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://196.43.248.10:8250/Eposta/Service.asmx?op=MakePayment";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("SessionID", App.getInstance().SessionId);
            Request.addProperty("PartnerID", mPartnerID);
            Request.addProperty("TAccountID", account_id);
            Request.addProperty("MobileNumber", userphone);
            Request.addProperty("Amount", paymentAmount);
            Request.addProperty("UniqueID", getUnixTimeStamp());
            Request.addProperty("ExtraInfo", title);


            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            response = (SoapObject) soapEnvelope.getResponse();

            Log.i(TAG, "response: " + response);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    public String getUnixTimeStamp(){

        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }
}
