package com.craft.PostaEbox.GCMConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.craft.PostaEbox.CustomActivity.MainActivity;
import com.craft.PostaEbox.R;
import com.craft.PostaEbox.Utils.Utility;
import com.craft.PostaEbox.Utils.XMLParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class GCMRegistration extends Activity {
    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    String regId = "";
    String TAG = "GCMRegistration_Class Response";
    SoapPrimitive resultString;
 
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
 
    AsyncTask<Void, Void, String> createRegIdTask;
 
    public static final String REG_ID = "regId";
    public static final String PHONE_NUMBER = "mobile";
    // XML node keys
    static final String KEY_PARTNER = "Partner"; // parent node
    static final String KEY_PARTNER_ID = "PartnerID";
    static final String KEY_PARTNER_NAME = "PartnerName";
    static final String KEY_ACCOUNT_QUERY = "AccountQuery";
    public static ArrayList<HashMap<String, String>> partnersMenuItems  = new ArrayList<HashMap<String, String>>();


    String possibleEmail,userphone,name,customerid,account;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!partnersMenuItems.isEmpty()) {
            Intent i = new Intent(GCMRegistration.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_gcm_registration);

        
       try{
        //possibleEmail += "************* Get Registered Gmail Account *************nn";
        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
         
        for (Account account : accounts) {
           
          possibleEmail =account.name;
          
          Log.d("possibleEmail", possibleEmail);
           
        }
       }
       catch(Exception e)
       {
            Log.i("Exception", "Exception:"+e) ; 
       }
        
 
        applicationContext = getApplicationContext();
 
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
 
        SharedPreferences prefs = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        userphone = prefs.getString(PHONE_NUMBER, "");

        //When Email ID is set in Sharedpref, User will be taken to HomeActivity
        if (!TextUtils.isEmpty(registrationId)) {
            /*Intent i = new Intent(applicationContext, HomeActivity.class);
            i.putExtra("regId", registrationId);
            i.putExtra("from", "main");
            startActivity(i);*/
        	//Toast.makeText(applicationContext,"Welcome to CHATPAY", Toast.LENGTH_LONG).show();
            finish();
        }else{
        	RegisterUser(possibleEmail,userphone);
        }
    }
 
    // When Register Me button is clicked
    public void RegisterUser(String emailID,String phone) {
 
        if (!TextUtils.isEmpty(emailID) && Utility.validate(emailID)) {
            if (checkPlayServices()) {
                registerInBackground(emailID,phone);
            }
        }
        else {
            Toast.makeText(applicationContext, "Couldn't get a valid email address",Toast.LENGTH_LONG).show();
        }
    }
 
    // AsyncTask to register Device in GCM Server
    private void registerInBackground(final String emailID,final String phone) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(GCMRegistration.this);
                    }
                    regId = gcmObj.register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;
 
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
 
            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    // Store RegId created by GCM Server in SharedPref
                    storeRegIdinServer(applicationContext, regId, emailID,phone);
                } else {
                	new AlertDialog.Builder(GCMRegistration.this)
					.setTitle("Error")
					.setMessage("Service not activated. Please check your internet connection and try again later")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
									dialog.cancel();
									finish();

								}
							}).create().show();
                }
            }
        }.execute(null, null, null);
    }
 
    // Store  RegId and Email entered by User in SharedPref
    
 
    // Share RegID with GCM Server Application (Php)
    private void storeRegIdinServer(Context context, final String regId,final String emailID,final String phone) {
        prgDialog.show();
        params.put("regId", regId);
        params.put("regEmail", possibleEmail);
        params.put("regPhone", userphone);
        params.put("OS", "android");
        params.put("appName", "eposta");

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ApplicationConstants.APP_SERVER_URL, params,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        /*// Hide Progress Dialog
                        prgDialog.hide();*/
                        if (prgDialog != null) {

                            if (partnersMenuItems.isEmpty()) {
                                //Fetching Partners
                                AsyncCallWS task = new AsyncCallWS();
                                task.execute();
                            } else {
                                //transition
                                Intent intent = new Intent(GCMRegistration.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                            }


                        }
                    }

                    // When the response returned by REST has Http
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        if (statusCode == 404) {
                            Toast.makeText(applicationContext,
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(applicationContext,
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(applicationContext, "Unexpected Error occcured! [Most common Error: Device might " + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
 
    // Check if Google Playservices is installed in Device or not
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
            	new AlertDialog.Builder(GCMRegistration.this)
				.setTitle("Error")
				.setMessage("You need to have google play services installed to use this service")
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								dialog.cancel();
								finish();

							}
						}).create().show();
                finish();
            }
            return false;
        } else {
            /*Toast.makeText(
                    applicationContext,
                    "This device supports Play services, App will work normally",
                    Toast.LENGTH_LONG).show();*/
        }
        return true;
    }
 
    // When Application is resumed, check for Play services support to make sure app will be running normally
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

     //Fetching Partners
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


             //transition
             Intent intent = new Intent(GCMRegistration.this, MainActivity.class);
             startActivity(intent);
             overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

             // Hide Progress Dialog
             prgDialog.hide();
             prgDialog.dismiss();


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


