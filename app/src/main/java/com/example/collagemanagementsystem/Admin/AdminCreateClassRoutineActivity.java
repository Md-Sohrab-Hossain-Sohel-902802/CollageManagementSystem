package com.example.collagemanagementsystem.Admin;


import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.collagemanagementsystem.Admin.Adapter.CreateroutineClasslistSpinnerAdapter;
import com.example.collagemanagementsystem.Admin.Classes.ClassList;
import com.example.collagemanagementsystem.Admin.Classes.ShhortcutClasslist;
import com.example.collagemanagementsystem.Classes.DepartmentList;
import com.example.collagemanagementsystem.Classes.MainAdapter;
import com.example.collagemanagementsystem.Classes.Spinner2Adapter;
import com.example.collagemanagementsystem.R;
import com.example.collagemanagementsystem.sqlite.DepartmentDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCreateClassRoutineActivity extends AppCompatActivity {


    private  Toolbar toolbar;
    private  Spinner createRoutineDepartmentSpinner,classlistSpinner,daysSpinner,periodSpinner,subjectSpinner,teacherSpinner,classNoSpinner;
    private  Button okButton,classselectButton,dayperiodSelectButton,saveandnextButton;
    private TextView createroutinDetailsTextview;


    private List<DepartmentList> departmentLists=new ArrayList<>();
    private List<DepartmentList> dayLIst=new ArrayList<>();
    private List<DepartmentList> spdList=new ArrayList<>();
    private List<ShhortcutClasslist> classlists=new ArrayList<>();


    private AdminMainListAdapter mainAdapter,dayAdapter,periodAdapter;
    private CreateroutineClasslistSpinnerAdapter classListadapter;
    private   String selectedDepartment="null";
    private  String selectedClassName="null";
    private  String selectedDays="null";
    private  String selectedPeriod="null";
    private String  cDepartment,cGroup,cShift,cName,cSession;








    private  DatabaseReference classlistdatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_class_routine);

      actionbar("Chose a department");

        classlistdatabaseRef= FirebaseDatabase.getInstance().getReference().child("collage").child("classlist");



        createRoutineDepartmentSpinner=findViewById(R.id.createroutineDepartmentListSpinnerid);
        okButton=findViewById(R.id.createRoutineOKButtonid);
        classlistSpinner=findViewById(R.id.createroutineClassListSpinnerid);
        classselectButton=findViewById(R.id.createroutineclasslistSelectButtonid);
        daysSpinner=findViewById(R.id.cTdaylistSpinnerid);
        periodSpinner=findViewById(R.id.cTperiodListSpinnerid);
        dayperiodSelectButton=findViewById(R.id.cTdayperiodSelectButtonid);
        createroutinDetailsTextview=findViewById(R.id.createroutinDetailsTextviewid);
        subjectSpinner=findViewById(R.id.crtSubjectListSpinnerid);
        teacherSpinner=findViewById(R.id.crtTeacherSpinnerid);
        classNoSpinner=findViewById(R.id.crtClassNoSpinnerid);
        saveandnextButton=findViewById(R.id.crtsaveAndNextButtonid);







        mainAdapter=new AdminMainListAdapter(this,departmentLists);
       dayAdapter=new AdminMainListAdapter(this,dayLIst);
     periodAdapter=new AdminMainListAdapter(this,spdList);



       createRoutineDepartmentSpinner.setAdapter(mainAdapter);
        daysSpinner.setAdapter(dayAdapter);
        periodSpinner.setAdapter(periodAdapter);



        createRoutineDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    DepartmentList departmentList=departmentLists.get(position);
                    selectedDepartment=departmentList.getDepartment();
                    okButton.setVisibility(View.VISIBLE);



            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    DepartmentList currentlist=dayLIst.get(position);
                    selectedDays=currentlist.getDepartment();
                dayperiodSelectButton.setVisibility(View.VISIBLE);
            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    DepartmentList currentlist=spdList.get(position);
                    selectedPeriod=currentlist.getDepartment();
                    dayperiodSelectButton.setVisibility(View.VISIBLE);

            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        classlistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ShhortcutClasslist currentlist=classlists.get(position);
                    selectedClassName=currentlist.getName();
                   classselectButton.setVisibility(View.VISIBLE);

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
                    classlistSpinner.setVisibility(View.VISIBLE);

                    createRoutineDepartmentSpinner.setVisibility(View.GONE);

                    fatchClasslist(selectedDepartment);




                }
            }
        });
        dayperiodSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDays.equals("null")){
                    Toast.makeText(AdminCreateClassRoutineActivity.this, "Select a Day", Toast.LENGTH_SHORT).show();

                }else if(selectedPeriod.equals("null")){
                    Toast.makeText(AdminCreateClassRoutineActivity.this, "Select Period", Toast.LENGTH_SHORT).show();
                }else{

                    createRoutineDepartmentSpinner.setVisibility(View.GONE);
                    okButton.setVisibility(View.GONE);
                    classlistSpinner.setVisibility(View.GONE);
                    classselectButton.setVisibility(View.GONE);
                    daysSpinner.setVisibility(View.GONE);
                    periodSpinner.setVisibility(View.GONE);
                    dayperiodSelectButton.setVisibility(View.GONE);
                    createroutinDetailsTextview.setVisibility(View.VISIBLE);


                    createroutinDetailsTextview.setText(selectedClassName+" \n "+selectedDays+" At "+selectedPeriod);







                    Toast.makeText(AdminCreateClassRoutineActivity.this, "Department: "+selectedDepartment+"Class : "+selectedClassName+"Day : "+selectedDays+"Time : "+selectedPeriod, Toast.LENGTH_LONG).show();
                }
            }
        });
        classselectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedClassName.equals("null")){
                    Toast.makeText(AdminCreateClassRoutineActivity.this, "Please Select Class", Toast.LENGTH_SHORT).show();
                }else{
                    classselectButton.setVisibility(View.GONE);
                    classlistSpinner.setVisibility(View.GONE);
                    daysSpinner.setVisibility(View.VISIBLE);
                    periodSpinner.setVisibility(View.VISIBLE);
                    getClassDetails(selectedClassName);

                    actionbar("Dep: "+selectedDepartment+"Class : "+selectedClassName);



                }
            }
        });

















    }

    private void getClassDetails(String selectedClassName) {

                DatabaseReference classRef=FirebaseDatabase.getInstance().getReference().child("collage").child("classlist").child(selectedDepartment);

                classRef.child(selectedClassName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            cDepartment=snapshot.child("department").getValue().toString();
                            cGroup=snapshot.child("group").getValue().toString();
                            cName=snapshot.child("name").getValue().toString();
                            cSession=snapshot.child("name").getValue().toString();
                            cShift=snapshot.child("shift").getValue().toString();




                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    public void actionbar(String title){
        toolbar=findViewById(R.id.createclassroutineToolbarid);
        setSupportActionBar(toolbar);
        this.setTitle(""+title);
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

       Cursor cursor1=dataAccess.getAllDay();
        if(cursor1.getCount()!=0){
            dayLIst.clear();

            while (cursor1.moveToNext()){
                String serial=cursor1.getString(0);
                String dayname=cursor1.getString(1);
                DepartmentList  days=new DepartmentList(serial,dayname);

                dayLIst.add(days);
                dayAdapter.notifyDataSetChanged();




            }


        }
        Cursor cursor2=dataAccess.getAllSpd();
        if(cursor2.getCount()!=0){
            spdList.clear();

            while (cursor2.moveToNext()){
                String serial=cursor2.getString(0);
                String dayname=cursor2.getString(1);
                DepartmentList  spd=new DepartmentList(serial,dayname);

                spdList.add(spd);
                periodAdapter.notifyDataSetChanged();




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
            if(daysSpinner.getVisibility()==View.VISIBLE || okButton.getVisibility()==View.VISIBLE  || classlistSpinner.getVisibility()==View.VISIBLE || classselectButton.getVisibility()==View.VISIBLE || periodSpinner.getVisibility()==View.VISIBLE ||  dayperiodSelectButton.getVisibility()==View.VISIBLE || createRoutineDepartmentSpinner.getVisibility()==View.VISIBLE  || createroutinDetailsTextview.getVisibility()==View.VISIBLE){


                    createRoutineDepartmentSpinner.setVisibility(View.GONE);
                    okButton.setVisibility(View.GONE);
                    classlistSpinner.setVisibility(View.GONE);
                    classselectButton.setVisibility(View.GONE);
                    daysSpinner.setVisibility(View.GONE);
                    periodSpinner.setVisibility(View.GONE);
                    dayperiodSelectButton.setVisibility(View.GONE);
                    createroutinDetailsTextview.setVisibility(View.GONE);






            }else if(!(daysSpinner.getVisibility()==View.VISIBLE || okButton.getVisibility()==View.VISIBLE  || classlistSpinner.getVisibility()==View.VISIBLE || classselectButton.getVisibility()==View.VISIBLE || periodSpinner.getVisibility()==View.VISIBLE ||  dayperiodSelectButton.getVisibility()==View.VISIBLE || createRoutineDepartmentSpinner.getVisibility()==View.VISIBLE  || createroutinDetailsTextview.getVisibility()==View.VISIBLE)){
                createRoutineDepartmentSpinner.setVisibility(View.VISIBLE);
        }
        }


        return super.onOptionsItemSelected(item);
    }


    public  void  fatchClasslist(String department){
        classlists.clear();

            classlistdatabaseRef.child(department).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                                for(DataSnapshot snapshot1:snapshot.getChildren()){
                                    String name=snapshot1.child("name").getValue().toString();
                                    String key=snapshot1.child("key").getValue().toString();

                                    ShhortcutClasslist classlist=new ShhortcutClasslist(name,key);
                                    classlists.add(classlist);
                                }
                            classListadapter=new CreateroutineClasslistSpinnerAdapter(AdminCreateClassRoutineActivity.this,classlists);
                            classlistSpinner.setAdapter(classListadapter);


                        }else{
                            Toast.makeText(AdminCreateClassRoutineActivity.this, "For "+selectedDepartment+" Department have no class created.", Toast.LENGTH_SHORT).show();
                            classlistSpinner.setVisibility(View.GONE);

                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


    }




}