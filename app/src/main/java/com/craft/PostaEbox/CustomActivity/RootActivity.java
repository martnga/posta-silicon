package com.craft.PostaEbox.CustomActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.craft.PostaEbox.App;
import com.craft.PostaEbox.R;
import com.craft.PostaEbox.sqliteStorage.DatabaseOperations;

public class RootActivity extends AppCompatActivity {
    TextView Register,Signin;
    String userphone;
    public static final String PHONE_NUMBER = "mobile";
    //Create database
    public static DatabaseOperations DB = new DatabaseOperations(App.getInstance());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Getting phone number from shared prefences
        SharedPreferences prefs = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        userphone = prefs.getString(PHONE_NUMBER, "");

        //Take User to login page if not registered
        if(!userphone.isEmpty()){
            Intent i = new Intent(RootActivity.this, Login.class);
            startActivity(i);
            finish();
        }

        setContentView(R.layout.activity_root_activity);
        Register=(TextView) findViewById(R.id.signup);
        Signin=(TextView) findViewById(R.id.login);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RootActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RootActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
