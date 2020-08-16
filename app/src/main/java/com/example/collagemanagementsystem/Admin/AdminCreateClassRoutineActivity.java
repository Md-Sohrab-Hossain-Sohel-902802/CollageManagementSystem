package com.example.collagemanagementsystem.Admin;


import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.collagemanagementsystem.Classes.DepartmentList;
import com.example.collagemanagementsystem.Classes.MainAdapter;
import com.example.collagemanagementsystem.R;
import com.example.collagemanagementsystem.sqlite.DepartmentDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminCreateClassRoutineActivity extends AppCompatActivity {


    private  Toolbar toolbar;
    private  Spinner createRoutineDepartmentSpinner;
    private  Button okButton;

    private List<DepartmentList> departmentLists=new ArrayList<>();
    private AdminMainListAdapter mainAdapter;


    private   String selectedDepartment="null";
    private  boolean isdepartmentselected=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_class_routine);


        toolbar=findViewById(R.id.createclassroutineToolbarid);
        setSupportActionBar(toolbar);

        createRoutineDepartmentSpinner=findViewById(R.id.createroutineDepartmentListSpinnerid);
        okButton=findViewById(R.id.createRoutineOKButtonid);


        mainAdapter=new AdminMainListAdapter(this,departmentLists);

        createRoutineDepartmentSpinner.setAdapter(mainAdapter);




        createRoutineDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          /*      if(isdepartmentselected==true){
                    isdepartmentselected=false;
                }else{*/
                    DepartmentList departmentList=departmentLists.get(position);
                    selectedDepartment=departmentList.getDepartment();
                    okButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDepartment.equals("null")){
                    Toast.makeText(AdminCreateClassRoutineActivity.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }else{
                    okButton.setVisibility(View.GONE);
                    createRoutineDepartmentSpinner.setVisibility(View.GONE);

                    Toast.makeText(AdminCreateClassRoutineActivity.this, ""+selectedDepartment, Toast.LENGTH_SHORT).show();
                }
            }
        });















    }



    @Override
    protected void onStart() {
        super.onStart();


        DepartmentDatabase dataAccess = DepartmentDatabase.getInstance(AdminCreateClassRoutineActivity.this);
        dataAccess.open();

        Cursor cursor = dataAccess.getAllDepartmentList();
        if (cursor.getCount() != 0) {
            departmentLists.clear();
            while (cursor.moveToNext()) {
                String  serial = cursor.getString(0);
                String name = cursor.getString(1);
                DepartmentList dtList = new DepartmentList(serial,name);
                departmentLists.add(dtList);
                mainAdapter.notifyDataSetChanged();

            }
        }







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.admin_classlist_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.classlistMenu_AddButtonid){
                createRoutineDepartmentSpinner.setVisibility(View.VISIBLE);
        }


        return super.onOptionsItemSelected(item);
    }
}