package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collagemanagementsystem.Admin.AdminMainActivity;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    private  EditText emailEdittext;
    private  EditText passwordEdittext;
    private  Button loginButton;
    private  String email,password;
    private  FirebaseAuth mAuth;


    private TextView adminloginLink;





    private SideWork sideWork;

    private  DatabaseReference mRootref;















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth=FirebaseAuth.getInstance();


        mRootref= FirebaseDatabase.getInstance().getReference().child("collage");


        emailEdittext=findViewById(R.id.login_emailEdittextid);
        passwordEdittext=findViewById(R.id.login_PasswordEdittextid);
        loginButton=findViewById(R.id.login_LoginButtonid);
        adminloginLink=findViewById(R.id.login_adminTextviewid);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        email=emailEdittext.getText().toString();
                        password=passwordEdittext.getText().toString();



                        if(email.isEmpty()){
                            emailEdittext.setError("Please Enter Your mail first.");
                            emailEdittext.requestFocus();
                            return;
                        }
                       else if(password.isEmpty()){
                            passwordEdittext.setError("Please Enter Your Password first.");
                            passwordEdittext.requestFocus();
                            return;
                        }
                       else{

                           sideWork=new SideWork();
                           sideWork.showProgressdiolouge(LoginActivity.this,"Please Wait","We are checking your account");


                           mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if(task.isSuccessful()){
                                       Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                       startActivity(intent);
                                       Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                                        sideWork.dismissProgressdiolouge();
                                   }else{
                                       Toast.makeText(LoginActivity.this, "Something Wrong here", Toast.LENGTH_SHORT).show();

                                        sideWork.dismissProgressdiolouge();
                                   }
                               }
                           });



                        }





            }
        });






        adminloginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //    startActivity(new Intent(LoginActivity.this,AdminLoginActivity.class));
                            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
            }
        });








    }
}