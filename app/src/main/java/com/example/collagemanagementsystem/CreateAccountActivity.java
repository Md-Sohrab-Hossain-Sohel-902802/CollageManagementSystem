package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    private  EditText emialEdittext;
    private  EditText firstnameEdittext;
    private  EditText lastNameEdittext;
    private  EditText mobileEdittext;
    private  EditText passwordEdittext;


    private  Button  nextButton;
    private  Button  createButton;




    private  String email,firstName,lastName,mobile,password;

    private  FirebaseAuth mAuth;
    private DatabaseReference mRootref;




    private  ProgressDialog mProgressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        mAuth=FirebaseAuth.getInstance();
        mRootref=FirebaseDatabase.getInstance().getReference().child("collage");


        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait........");
        mProgressDialog.setTitle("We Are Creating Your Account.");


        firstnameEdittext=findViewById(R.id.createAccount_firstnameEdittextid);
        emialEdittext=findViewById(R.id.createAccount_emailEdittextid);
        lastNameEdittext=findViewById(R.id.createAccount_lastnameEdittextid);
        mobileEdittext=findViewById(R.id.createAccount_MobileEdittextid);
        passwordEdittext=findViewById(R.id.createAccount_PasswordEdittextid);
        nextButton=findViewById(R.id.createAccountNextButtonid);
        createButton=findViewById(R.id.createAccount_createButtonid);




        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName=firstnameEdittext.getText().toString();
                lastName=lastNameEdittext.getText().toString();
                email=emialEdittext.getText().toString();
                mobile=mobileEdittext.getText().toString();
                password=passwordEdittext.getText().toString();


                if((firstName.isEmpty() || lastName.isEmpty() || email.isEmpty())){

                    Toast.makeText(CreateAccountActivity.this, "Please Fill Up All The Filed", Toast.LENGTH_SHORT).show();

                }else{
                    firstnameEdittext.setVisibility(View.GONE);
                    lastNameEdittext.setVisibility(View.GONE);
                    emialEdittext.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    mobileEdittext.setVisibility(View.VISIBLE);
                    passwordEdittext.setVisibility(View.VISIBLE);
                    createButton.setVisibility(View.VISIBLE);

                }



                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                                mobile=mobileEdittext.getText().toString();
                                password=passwordEdittext.getText().toString();


                                if((mobile.isEmpty()||password.isEmpty())){
                                    Toast.makeText(CreateAccountActivity.this, "Please Enter All The Filed", Toast.LENGTH_SHORT).show();
                                }else{
                                    mProgressDialog.show();
                                        createAccount();
                                }





                    }
                });





            }
        });











    }

    private void createAccount() {


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    HashMap<String, Object> userdatalist = new HashMap<>();

                    userdatalist.put("email", email);
                    userdatalist.put("password", password);
                    userdatalist.put("mobile", mobile);
                    userdatalist.put("fName", firstName);
                    userdatalist.put("lName", lastName);
                    userdatalist.put("uid", mAuth.getCurrentUser().getUid());


                    mRootref.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(userdatalist).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mProgressDialog.dismiss();
                                Intent intent=new Intent(CreateAccountActivity.this,MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(CreateAccountActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });




    }
}