package com.example.collagemanagementsystem.Classes;

import android.app.ProgressDialog;
import android.content.Context;

public class SideWork {
    public          ProgressDialog progressDialog;


    public  void showProgressdiolouge(Context context,String message, String title){
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();


    }
    public void dismissProgressdiolouge(){
        progressDialog.dismiss();
    }













}
