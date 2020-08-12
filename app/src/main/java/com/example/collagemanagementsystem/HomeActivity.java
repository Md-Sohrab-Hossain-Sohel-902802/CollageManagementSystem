package com.example.collagemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.collagemanagementsystem.Admin.AdminDepartmentActivity;
import com.example.collagemanagementsystem.Classes.MainGridAdapters;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity  extends AppCompatActivity {

    private FirebaseAuth mAuth;



    private Toolbar toolbar;


    private GridView gridView;

    private MainGridAdapters adapters;

    private  int[] images={R.drawable.present,R.drawable.present};
    private  String[] text={"Start Taking Presents","Teachers"};
    private SideWork sideWork;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth=FirebaseAuth.getInstance();

        gridView=findViewById(R.id.mainGridViewid);

        adapters=new MainGridAdapters(this,text,images);

        gridView.setAdapter(adapters);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(HomeActivity.this, AdminDepartmentActivity.class);
                    intent.putExtra("activity","main");
                    startActivity(intent);
                }
            }
        });











    }
}
