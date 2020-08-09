package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.collagemanagementsystem.Classes.Present;
import com.example.collagemanagementsystem.Classes.PresentListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PresentListActivity extends AppCompatActivity {


    private  String roll,name,department,classname,subjectname;


    private  RecyclerView recyclerView;
    private  DatabaseReference databaseReference;
    private PresentListAdapter adapter;
    private  List<Present> presentList=new ArrayList<>();

    private  Toolbar toolbar;
    private FirebaseAuth mAuth;
    private String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_list);

        roll=getIntent().getStringExtra("roll");
        name=getIntent().getStringExtra("name");
        department=getIntent().getStringExtra("departmentname");
        classname=getIntent().getStringExtra("classname");
        subjectname=getIntent().getStringExtra("subjectname");


        toolbar=findViewById(R.id.presentLIst_ToolbarId);
        setSupportActionBar(toolbar);

        this.setTitle(name+"("+roll+")");

        mAuth=FirebaseAuth.getInstance();
    currentUser=mAuth.getCurrentUser().getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage")
        .child(currentUser)
        .child("mypresentList")
        .child(department)
        .child(classname);

        recyclerView=findViewById(R.id.presentList_RecyclerViewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adapter=new PresentListAdapter(this,presentList);
        recyclerView.setAdapter(adapter);










    }


    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.child(subjectname).child(roll)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        presentList.clear();

                            for (DataSnapshot snapshot1: snapshot.getChildren()){



                                String roll=snapshot1.child("roll").getValue().toString();
                                String date=snapshot1.child("date").getValue().toString();
                                String key=snapshot1.child("key").getValue().toString();
                                String time=snapshot1.child("time").getValue().toString();
                                String presentance=snapshot1.child("presentance").getValue().toString();
                                String subjectName=snapshot1.child("subjectName").getValue().toString();



                                Present present=new Present(date,key,roll,time,presentance,subjectName);

                                presentList.add(present);
                                adapter.notifyDataSetChanged();


                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });












    }
}