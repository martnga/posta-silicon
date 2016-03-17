package com.craft.PostaEbox.CustomActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.craft.PostaEbox.App;
import com.craft.PostaEbox.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SignUp extends AppCompatActivity {

    String Country = "KE";
    String TAG = "SignUp_Class Response";
    SoapPrimitive resultString;
    String email, mobileNumber,postalNumber, postalAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        final EditText mEmail = (EditText) findViewById(R.id.email);
        final EditText mMobileNumber = (EditText) findViewById(R.id.phone);
        final EditText mPostalAddress = (EditText) findViewById(R.id.postal);
        final EditText mPassword = (EditText) findViewById(R.id.password);
        final TextView mSignUp = (TextView) findViewById(R.id.signup_btn);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail(mEmail.getText())) {
                    mEmail.setError("Email required");
                } else if (mPassword.getText().toString().isEmpty()) {
                    mPassword.setError("Password required");
                } else {

                    email = mEmail.getText().toString().trim();
                    mobileNumber = mMobileNumber.getText().toString().trim();
                    postalAddress = mPostalAddress.getText().toString().trim();
                    password = mPassword.getText().toString().trim();

                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();

                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
            }
        });
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
            Toast.makeText(SignUp.this, "Response" + resultString.toString(), Toast.LENGTH_LONG).show();
        }

    }


    public void login() {
        String SOAP_ACTION = "http://tempuri.org/Register";
        String METHOD_NAME = "Register";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://196.43.248.10:8250/EPosta/Service.asmx?op=Register";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("MobileNumber", mobileNumber);
            Request.addProperty("Country", Country);
            Request.addProperty("Email", email);
            Request.addProperty("IMEI", getIMEI());
            Request.addProperty("APPName", App.getInstance().APP_NAME);
            Request.addProperty("Address", postalAddress);
            Request.addProperty("LoginPassword", password);

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

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public String getIMEI(){

        TelephonyManager mngr = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;

    }

}

