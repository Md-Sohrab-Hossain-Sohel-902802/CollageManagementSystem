package com.example.collagemanagementsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.collagemanagementsystem.ClassListActivity;
import com.example.collagemanagementsystem.Classes.DepartmentList;
import com.example.collagemanagementsystem.R;
import com.example.collagemanagementsystem.sqlite.DepartmentDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminDepartmentActivity extends AppCompatActivity {



    private ListView listView ;

    private  AdminMainListAdapter adminMainListAdapter;


    private  List<DepartmentList> departmentLists=new ArrayList<>();
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_department);


        data=getIntent().getStringExtra("activity");


        listView=findViewById(R.id.adminDepartmentListviewid);
        adminMainListAdapter=new AdminMainListAdapter(this,departmentLists);


        listView.setAdapter(adminMainListAdapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    DepartmentList selecteditem=departmentLists.get(position);


                    if(data.equals("admain")){
                        Intent intent=new Intent(AdminDepartmentActivity.this,AdminClassList.class);
                        intent.putExtra("sname",selecteditem.getDepartment());
                        intent.putExtra("from","mngclasslist");
                        startActivity(intent);
                    }
                    else if(data.equals("main")){
                        Intent intent=new Intent(AdminDepartmentActivity.this, ClassListActivity.class);
                        intent.putExtra("sname",selecteditem.getDepartment());
                        startActivity(intent);
                    }
        else if(data.equals("formanagestudent")){
                        Intent intent=new Intent(AdminDepartmentActivity.this, AdminClassList.class);
                       intent.putExtra("from","managestudent");
                        intent.putExtra("sname",selecteditem.getDepartment());
                        startActivity(intent);
                    }






            }
        });













    }


    @Override
    protected void onStart() {
        super.onStart();


        DepartmentDatabase dataAccess = DepartmentDatabase.getInstance(AdminDepartmentActivity.this);
        dataAccess.open();

        Cursor cursor = dataAccess.getAllDepartmentList();
        if (cursor.getCount() != 0) {
            departmentLists.clear();
            while (cursor.moveToNext()) {
               String  serial = cursor.getString(0);
                String name = cursor.getString(1);
                DepartmentList dtList = new DepartmentList(serial,name);
                departmentLists.add(dtList);
                adminMainListAdapter.notifyDataSetChanged();

            }
        }







    }
}