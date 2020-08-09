package com.example.collagemanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.collagemanagementsystem.ClassListActivity;
import com.example.collagemanagementsystem.R;

public class AdminDepartmentActivity extends AppCompatActivity {



    private ListView listView ;

    private  AdminMainListAdapter adminMainListAdapter;

    private  String[] texts={"Computer","Electrical","Civil","Mechanical","Power","Electro-medical"};


    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_department);


        data=getIntent().getStringExtra("activity");


        listView=findViewById(R.id.adminDepartmentListviewid);
        adminMainListAdapter=new AdminMainListAdapter(this,texts);


        listView.setAdapter(adminMainListAdapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String selecteditem=texts[position];


                    if(data.equals("admain")){
                        Intent intent=new Intent(AdminDepartmentActivity.this,AdminClassList.class);
                        intent.putExtra("sname",selecteditem);
                        intent.putExtra("from","mngclasslist");
                        startActivity(intent);
                    }
                    else if(data.equals("main")){
                        Intent intent=new Intent(AdminDepartmentActivity.this, ClassListActivity.class);
                        intent.putExtra("sname",selecteditem);
                        startActivity(intent);
                    }
        else if(data.equals("formanagestudent")){
                        Intent intent=new Intent(AdminDepartmentActivity.this, AdminClassList.class);
                       intent.putExtra("from","managestudent");
                        intent.putExtra("sname",selecteditem);
                        startActivity(intent);
                    }






            }
        });













    }
}