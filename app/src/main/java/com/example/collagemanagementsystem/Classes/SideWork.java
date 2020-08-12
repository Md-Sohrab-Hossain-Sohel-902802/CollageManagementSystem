package com.example.collagemanagementsystem.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.collagemanagementsystem.CollageManagement;

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



    public void saveImage(Context context,String image){
            SharedPreferences sharedPreferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("image",image);
            editor.commit();
    }
    public void saveName(Context context,String name){
            SharedPreferences sharedPreferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("username",name);
            editor.commit();
    }

   public String getImage(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);

            String image=sharedPreferences.getString("image","null");




return  image;
    }
  public  String getName(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);

            String image=sharedPreferences.getString("username","null");




return  image;
    }











}
