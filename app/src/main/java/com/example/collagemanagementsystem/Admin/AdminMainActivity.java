package com.example.collagemanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.collagemanagementsystem.MainActivity;
import com.example.collagemanagementsystem.R;

public class AdminMainActivity extends AppCompatActivity {


    private ListView listView;

    private  String[] texts={"Manage Class list","Manage  Student"};


    AdminMainListAdapter adminMainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);





        listView=findViewById(R.id.adminmainListviewid);



        adminMainListAdapter=new AdminMainListAdapter(this,texts);
        listView.setAdapter(adminMainListAdapter);





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position==0){
                    Intent intent=new Intent(AdminMainActivity.this,AdminDepartmentActivity.class);
                    intent.putExtra("activity","admain");
                    startActivity(intent);
                }
                else if(position==1){
                    Intent intent=new Intent(AdminMainActivity.this,AdminDepartmentActivity.class);
                    intent.putExtra("activity","formanagestudent");
                    startActivity(intent);
                }










            }
        });







    }
}