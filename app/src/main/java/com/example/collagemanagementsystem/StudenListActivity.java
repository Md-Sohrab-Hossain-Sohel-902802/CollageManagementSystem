package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.Sampler;

import com.example.collagemanagementsystem.Classes.Student;
import com.example.collagemanagementsystem.Classes.StudentListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudenListActivity extends AppCompatActivity {




    private  String className,subjectName,departmentName;

    private  RecyclerView recyclerView;



    private List<Student> studentList=new ArrayList<>();
    private DatabaseReference databaseReference;

    private StudentListAdapter adapter;
    private  Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studen_list);



        toolbar=findViewById(R.id.studentList_Toolbarid);
        setSupportActionBar(toolbar);


        className=getIntent().getStringExtra("classname");
        subjectName=getIntent().getStringExtra("subjectname");
        departmentName=getIntent().getStringExtra("departmentname");

        this.setTitle(className+"("+subjectName+")");



        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("Students").child(departmentName);





        recyclerView=findViewById(R.id.studentLIst_Recycclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter=new StudentListAdapter(this,studentList,className,departmentName,subjectName);
        recyclerView.setAdapter(adapter);




















    }


    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.child(className).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                studentList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    String name=snapshot1.child("name").getValue().toString();
                    String roll=snapshot1.child("roll").getValue().toString();
                    String key=snapshot1.child("key").getValue().toString();


                    Student student=new Student(name,roll,key);


                    studentList.add(student);

                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });














    }
}