package com.example.collagemanagementsystem;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.Adapters.TeacherListAdapter;
import com.example.collagemanagementsystem.Admin.AdminClassList;
import com.example.collagemanagementsystem.Admin.AdminDepartmentActivity;
import com.example.collagemanagementsystem.Admin.AdminMainListAdapter;
import com.example.collagemanagementsystem.Classes.DepartmentList;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.example.collagemanagementsystem.Classes.classlist;
import com.example.collagemanagementsystem.DataModuler.TeacherList;
import com.example.collagemanagementsystem.sqlite.DepartmentDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<TeacherList> teacherLists=new ArrayList<>();
    private TeacherListAdapter adapter;


    private DatabaseReference databaseReference;


    private Toolbar toolbar;

    //<------------------for custom diolouge------------->


    private  EditText teachernameEdittext;
    private EditText teacherTitleEdittext;
    private  EditText teacherCodeEdittext;
    private Spinner choseDepartmentSpinner;
    private Button saveButton;

    private  List<DepartmentList> departmentLists=new ArrayList<>();

    private AdminMainListAdapter adminMainListAdapter;

    private  boolean isFirstSelection=true;
    private  String selectedDepartment="null";


    //<------------------end custom diolouge------------->






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage");

         toolbar=findViewById(R.id.teacherlist_Toolbarid);

         setSupportActionBar(toolbar);
        this.setTitle("Teacher List");



        recyclerView=findViewById(R.id.teacherListRecyclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new TeacherListAdapter(TeacherListActivity.this,teacherLists);

        recyclerView.setAdapter(adapter);






    }


    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.child("Teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    teacherLists.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){


                        String id=dataSnapshot.child("id").getValue().toString();
                        String tc=dataSnapshot.child("tc").getValue().toString();
                        String department=dataSnapshot.child("department").getValue().toString();
                        String image=dataSnapshot.child("image").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String title=dataSnapshot.child("title").getValue().toString();



                        TeacherList datalist=new TeacherList(department,id,image,name,tc,title);

                        teacherLists.add(datalist);
                        adapter.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        DepartmentDatabase dataAccess = DepartmentDatabase.getInstance(TeacherListActivity.this);
        dataAccess.open();

        Cursor cursor = dataAccess.getAllDepartmentList();
        if (cursor.getCount() != 0) {
            departmentLists.clear();
            while (cursor.moveToNext()) {
                String  serial = cursor.getString(0);
                String name = cursor.getString(1);
                DepartmentList dtList = new DepartmentList(serial,name);
                departmentLists.add(dtList);

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
            showcustomdioloage();


        }


        return super.onOptionsItemSelected(item);
    }



    public void showcustomdioloage(){
        AlertDialog.Builder builder=new AlertDialog.Builder(TeacherListActivity.this);
        View view=getLayoutInflater().inflate(R.layout.add_teacher_diolouge_layout,null);
        builder.setView(view);
        teachernameEdittext=view.findViewById(R.id.addTeacher_nameEdittext);
        teacherCodeEdittext=view.findViewById(R.id.addTeacher_TeacherCodeEdittextid);
        teacherTitleEdittext=view.findViewById(R.id.addTeacher_TitleEdittextid);
        choseDepartmentSpinner=view.findViewById(R.id.addTeacher_chooseDepartmentTitleSpinnerid);
        saveButton=view.findViewById(R.id.addTeacher_SaveButtonid);

        adminMainListAdapter=new AdminMainListAdapter(TeacherListActivity.this,departmentLists);


        choseDepartmentSpinner.setAdapter(adminMainListAdapter);

        choseDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(isFirstSelection==true){
                    isFirstSelection=false;
                }else{
                    final DepartmentList selecteditem=departmentLists.get(position);
                    selectedDepartment=selecteditem.getDepartment();
             }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        final AlertDialog dialog=builder.create();
        dialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String teacherName=teachernameEdittext.getText().toString();
                    String teachercode=teacherCodeEdittext.getText().toString();
                    String teacherTitle=teacherTitleEdittext.getText().toString();

                    if(teacherName.isEmpty()){
                        teachernameEdittext.setError("Enter Teacher Name");
                        teachernameEdittext.requestFocus();
                        return;
                    }else if(teachercode.isEmpty()){
                        teacherCodeEdittext.setError("Enter Teacher Code");
                        teacherCodeEdittext.requestFocus();
                        return;
                    }else if(teacherTitle.isEmpty()){
                        teacherTitleEdittext.setError("Enter Teacher Title");
                        teacherTitleEdittext.requestFocus();
                        return;
                    }else if(selectedDepartment.equals("null")){
                        Toast.makeText(TeacherListActivity.this, "Please Choose Department", Toast.LENGTH_SHORT).show();
                    }else{
                            saveTeacherIntoDatabase(teacherName,teachercode,teacherTitle,selectedDepartment,dialog);
                    }




            }
        });
    }

    private void saveTeacherIntoDatabase(String teacherName, String teachercode, String teacherTitle, String selectedDepartment, final AlertDialog dialog) {

        final SideWork sideWork=new SideWork();
        sideWork.showProgressdiolouge(TeacherListActivity.this,"Saving Teacher","Please Wait");


        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("name",teacherName);
        hashMap.put("id",teacherName);
        hashMap.put("department",selectedDepartment);
        hashMap.put("image","null");
        hashMap.put("tc",teachercode);
        hashMap.put("title",teacherTitle);


        databaseReference.child("Teachers")
                .child(teacherName)
                .updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                                dialog.dismiss();
                                sideWork.dismissProgressdiolouge();
                            Toast.makeText(TeacherListActivity.this, "Teacher Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }


}