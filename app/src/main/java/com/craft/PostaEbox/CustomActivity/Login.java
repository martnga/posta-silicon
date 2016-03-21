package com.craft.PostaEbox.CustomActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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

public class Login extends AppCompatActivity {

    TextView Login;
    EditText mPassword;
    String TAG = "Login_Class Response";
    String password;
    SoapPrimitive resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=(TextView) findViewById(R.id.Login);
        mPassword=(EditText) findViewById(R.id.password);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPassword.getText().toString().isEmpty()){
                    mPassword.setError("Password required");
                }
                else {

                    password = mPassword.getText().toString();


                    Toast.makeText(Login.this, "Successful Login. ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, GCMRegistration.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    finish();

                    /*AsyncCallWS task = new AsyncCallWS();
                    task.execute();*/

                    /*Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);*/
                }
            }
        });
    }

    //validate  email
    /*public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }*/


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
            Toast.makeText(Login.this, "Response" + App.getInstance().customerId.toString(), Toast.LENGTH_LONG).show();

            //User going to GcM registration
            Intent intent = new Intent(Login.this, GCMRegistration.class);
            startActivity(intent);
            finish();
        }

    }


    public void login() {
        String SOAP_ACTION = "http://tempuri.org/LogIn";
        String METHOD_NAME = "LogIn";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://196.43.248.10:8250/EPosta/Service.asmx?op=LogIn";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("CustomerID", App.getInstance().customerId);
            Request.addProperty("IMEINumber", getIMEI());
            Request.addProperty("APPName", App.getInstance().APP_NAME);
            Request.addProperty("Password", mPassword);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            App.getInstance().customerId = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, "Login response: " + App.getInstance().customerId.toString());
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }



    public String getIMEI(){

        TelephonyManager mngr = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;

    }

    public void savingToSharedPreferences(String string){
        //saving credentials in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", string);
        editor.putString("customerID", App.getInstance().customerId.toString());
        editor.commit();
    }

    }

