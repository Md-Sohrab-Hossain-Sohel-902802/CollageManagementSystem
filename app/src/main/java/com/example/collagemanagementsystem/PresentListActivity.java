package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;


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
    private FirebaseAuth mAuth;
    private String currentUser;
    private  TextView presentDetailsTextview;

    public int totalCounter=0,presentCounter=0,absentcounter=0;

    private  Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_list);


        toolbar=findViewById(R.id.presentLIstToolbarid);
        setSupportActionBar(toolbar);


        roll=getIntent().getStringExtra("roll");
        name=getIntent().getStringExtra("name");
        department=getIntent().getStringExtra("departmentname");
        classname=getIntent().getStringExtra("classname");
        subjectname=getIntent().getStringExtra("subjectname");

        this.setTitle(name+"("+roll+")");

        mAuth=FirebaseAuth.getInstance();
    currentUser=mAuth.getCurrentUser().getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage")
        .child(currentUser)
        .child("mypresentList")
        .child(department)
        .child(classname);

        recyclerView=findViewById(R.id.presentList_RecyclerViewid);
        presentDetailsTextview=findViewById(R.id.presentDetailstTextviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adapter=new PresentListAdapter(this,PresentListActivity.this,presentList,roll,department,classname,subjectname);
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

                                totalCounter++;


                                String roll=snapshot1.child("roll").getValue().toString();
                                String date=snapshot1.child("date").getValue().toString();
                                String key=snapshot1.child("key").getValue().toString();
                                String time=snapshot1.child("time").getValue().toString();
                                String presentance=snapshot1.child("presentance").getValue().toString();
                                String subjectName=snapshot1.child("subjectName").getValue().toString();

                                if(presentance.equals("present")){
                                    presentCounter++;
                                }else if(presentance.equals("absent")){
                                    absentcounter++;
                                }


                                Present present=new Present(date,key,roll,time,presentance,subjectName);

                                presentList.add(present);
                                adapter.notifyDataSetChanged();


                            }


                            presentDetailsTextview.setText(name+": \nTotal  Class : "+totalCounter+"\nPresent: "+presentCounter+"              Absent : "+absentcounter);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });












    }
}