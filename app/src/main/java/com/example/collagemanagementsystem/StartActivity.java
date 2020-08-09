package com.example.collagemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private  Button createAccountButton,loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        createAccountButton=findViewById(R.id.start_createButtonid);
        loginButton=findViewById(R.id.start_loginButtonid);




        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });





    }


}