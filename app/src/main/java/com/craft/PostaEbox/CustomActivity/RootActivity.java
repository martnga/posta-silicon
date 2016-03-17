package com.craft.PostaEbox.CustomActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.craft.PostaEbox.R;

public class RootActivity extends AppCompatActivity {
    TextView Register,Signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
