package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collagemanagementsystem.Admin.AdminMainActivity;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {


    private  EditText emailEdittext;
    private  EditText passwordEdittex;
    private  Button loginButton;





    private  DatabaseReference databaseReference;


    private  String email,password;




    SideWork sideWork;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("Users").child("admin");


        emailEdittext=findViewById(R.id.adlogin_emailEdittextid);
        passwordEdittex=findViewById(R.id.adlogin_PasswordEdittextid);
        loginButton=findViewById(R.id.adlogin_LoginButtonid);






        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sideWork=new SideWork();

                sideWork.showProgressdiolouge(AdminLoginActivity.this,"Please Wait","We are Checking your Account");


                    email=emailEdittext.getText().toString();
                    password=passwordEdittex.getText().toString();


                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapshot1: snapshot.getChildren()){
                                String temail=snapshot1.child("email").getValue().toString();

                                if(temail.equals(email)){
                                    String tpassword=snapshot1.child("password").getValue().toString();
                                    if(tpassword.equals(password)){



                                        Intent intent=new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                                        startActivity(intent);


                                        Toast.makeText(AdminLoginActivity.this, "login Successful", Toast.LENGTH_SHORT).show();

                                            sideWork.dismissProgressdiolouge();
                                    }else{
                                        Toast.makeText(AdminLoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                        sideWork.dismissProgressdiolouge();

                                    }


                                }else{
                                    Toast.makeText(AdminLoginActivity.this, "Your Provided mail is wrong", Toast.LENGTH_SHORT).show();

                                    sideWork.dismissProgressdiolouge();
                                }









                            }






                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });










            }
        });
























    }
}