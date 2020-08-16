package com.example.collagemanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.collagemanagementsystem.Classes.MainAdapter;
import com.example.collagemanagementsystem.R;
import com.example.collagemanagementsystem.TeacherListActivity;

public class AdminMainActivity extends AppCompatActivity {


    private ListView listView;

    private  String[] texts={"Manage Class list","Manage  Student","Manage Teacher","Create Class Routine"};


    MainAdapter adminMainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);





        listView=findViewById(R.id.adminmainListviewid);



        adminMainListAdapter=new MainAdapter(this,texts);
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
                }else if(position==2){
                    startActivity(new Intent(AdminMainActivity.this, TeacherListActivity.class));
                }else if(position==3){
                    startActivity(new Intent(AdminMainActivity.this, AdminCreateClassRoutineActivity.class));
                }










            }
        });







    }
}