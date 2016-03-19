package com.craft.PostaEbox.GCMConfig;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;


public class GCMNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    NotificationCompat.Builder builder;
    String mobilenumber,IMEI;
    /*String PublicKey = "BG17D966";
    String RandomKey = "HDK94*21AX";*/



    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //  RandomKey = RKey.GetKey();

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        TelephonyManager telMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telMgr.getDeviceId();

        if (!extras.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
            String registrationId = prefs.getString("regId", null);
            mobilenumber = prefs.getString("phoneId", "");
            //if(registrationId!=null){
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                try{
                	sendNotification(extras.get(ApplicationConstants.MSG_KEY).toString());
                }catch(Exception ex){
                	//Log.d("GCM Error",ex.getMessage().toString());
                }
            	
            }
            /*}else{
            	if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                    
                    Log.d("logger1", extras.toString());
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                	Log.d("logger2", extras.toString());
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                	Log.d("logger3", extras.toString());
                }
            }*/

        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }
}
