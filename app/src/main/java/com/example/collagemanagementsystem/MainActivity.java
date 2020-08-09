package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.collagemanagementsystem.Admin.AdminDepartmentActivity;
import com.example.collagemanagementsystem.Classes.MainGridAdapters;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private  FirebaseAuth mAuth;
    private  Toolbar toolbar;


    private  GridView gridView;

    private MainGridAdapters adapters;

    private  int[] images={R.drawable.present,R.drawable.present};
    private  String[] text={"Start Taking Presents","Teachers"};
    private SideWork sideWork;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        toolbar=findViewById(R.id.mainToolbarid);
        setSupportActionBar(toolbar);

        gridView=findViewById(R.id.mainGridViewid);

        adapters=new MainGridAdapters(this,text,images);

        gridView.setAdapter(adapters);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                        Intent intent=new Intent(MainActivity.this, AdminDepartmentActivity.class);
                    intent.putExtra("activity","main");
                        startActivity(intent);
                }
            }
        });





















    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mCurrentuser=mAuth.getCurrentUser();
        if(mCurrentuser==null){
            Intent intent=new Intent(MainActivity.this,StartActivity.class);

       startActivity(intent); }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.mainMenu_LogoutMenuButtonid){
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }





        return super.onOptionsItemSelected(item);
    }
}