package com.craft.PostaEbox;

import android.app.Application;

import org.ksoap2.serialization.SoapPrimitive;

/**
 * Created by mansa on 3/17/16.
 */
public class App extends Application {
    public  String APP_NAME = "eposta";
    public SoapPrimitive customerId;

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

}
