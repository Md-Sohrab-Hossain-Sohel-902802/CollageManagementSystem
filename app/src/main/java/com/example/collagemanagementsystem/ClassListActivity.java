package com.example.collagemanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.collagemanagementsystem.Admin.AdminClassList;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.example.collagemanagementsystem.Classes.SpinnerAdapter;
import com.example.collagemanagementsystem.Classes.TeacherClasslist;
import com.example.collagemanagementsystem.Classes.classlist;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class ClassListActivity extends AppCompatActivity {








    private  Spinner classlistSpinner;
    private  DatabaseReference databaseReference;

    private  List<classlist> datalist=new ArrayList<>();


    SpinnerAdapter adapter;

    String selectedDepartmentName;
    private  Button addButton;
    private  RecyclerView recyclerView;


    private  boolean isFirstSelection=true;

    public FirebaseAuth mAuth;
    private  String currentUserid,selectedClassname;


    //<---------------for custom diolouge>




    private  EditText diolougeClassNameEdittext;
    private  TextView diolougeTextviewid;
    private  Button diolougeOkButtonid;

    private SideWork sideWork;




    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);


        toolbar=findViewById(R.id.classlist1_Toolbarid);
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();

        sideWork=new SideWork();


        selectedDepartmentName=getIntent().getStringExtra("sname");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage");


        currentUserid=mAuth.getCurrentUser().getUid();


        classlistSpinner=findViewById(R.id.classList_SpinnerId);
        recyclerView=findViewById(R.id.classlist_Recyclerviewid);
        addButton=findViewById(R.id.classlist_AddButtonid);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new SpinnerAdapter(this,datalist);




        classlistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(isFirstSelection==true){
                    isFirstSelection=false;
                }else{
                    final classlist selecteditem=datalist.get(position);

                    selectedClassname=selecteditem.getName();
                    addButton.setVisibility(View.VISIBLE);







                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subjectNameCustomDiolouge();



            }
        });
















    }


    @Override
    protected void onStart() {
        super.onStart();


        //for showing Classlist into spinner;


        sideWork.showProgressdiolouge(ClassListActivity.this,"Please Wait","Loading");


        databaseReference.child("classlist").child(selectedDepartmentName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                    String name=dataSnapshot1.child("name").getValue().toString();


                    classlist classlist=new classlist(name);

                    datalist.add(classlist);


                }
                classlistSpinner.setAdapter(adapter);
                sideWork.dismissProgressdiolouge();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseRecyclerOptions<TeacherClasslist> options=new FirebaseRecyclerOptions.Builder<TeacherClasslist>()
                .setQuery(      databaseReference.child(currentUserid).child("myclasslist")
                        .child(selectedDepartmentName),TeacherClasslist.class)
                .build();




        FirebaseRecyclerAdapter<TeacherClasslist,MClassListViewHolder> adapter=new FirebaseRecyclerAdapter<TeacherClasslist, MClassListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MClassListViewHolder holder, int position, @NonNull final TeacherClasslist classlist) {

                holder.classnameTextview.setText(classlist.getClassname());
                holder.subjectnameTextview.setText(classlist.getSubjectName());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ClassListActivity.this,StudenListActivity.class);
                        intent.putExtra("classname",classlist.getClassname());
                        intent.putExtra("subjectname",classlist.getSubjectName());
                        intent.putExtra("departmentname",selectedDepartmentName);
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public MClassListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(classlistSpinner.getContext()).inflate(R.layout.teacher_class_list_itemlayoute, classlistSpinner, false);

                return new MClassListViewHolder(view);
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.admin_classlist_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.classlistMenu_AddButtonid){

                classlistSpinner.setVisibility(View.VISIBLE);
       }



        return super.onOptionsItemSelected(item);
    }





    private void subjectNameCustomDiolouge() {

        AlertDialog.Builder builder=new AlertDialog.Builder(ClassListActivity.this);
        View view=getLayoutInflater().inflate(R.layout.manageclasslist_customdiolouge,null);
        builder.setView(view);
        diolougeClassNameEdittext=view.findViewById(R.id.classlistDiolouge_ClassnameEdittextid);
        diolougeOkButtonid=view.findViewById(R.id.classlistDiolouge_OkButtonid);
        diolougeTextviewid=view.findViewById(R.id.classlistDiolouge_tilteTextviewid);

        diolougeTextviewid.setText("Enter A Subject Name for this class.");

        diolougeClassNameEdittext.setHint("Enter Subject Name");

        final AlertDialog dialog=builder.create();
        dialog.show();
        diolougeOkButtonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String subjectname=diolougeClassNameEdittext.getText().toString();


                if(subjectname.isEmpty()){
                    diolougeClassNameEdittext.setError("Enter a subject name for add this class into your class list.");
                    diolougeClassNameEdittext.requestFocus();
                    return;
                }
                else{

                    databaseReference.child(currentUserid).child("myclasslist")
                            .child(selectedDepartmentName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(selectedClassname)){

                            }else{

                                HashMap<String, Object> classlistHashmap=new HashMap<>();

                                classlistHashmap.put("classname",selectedClassname);
                                classlistHashmap.put("subjectname",subjectname);

                                databaseReference.child(currentUserid).child("myclasslist")
                                        .child(selectedDepartmentName)
                                        .child(selectedClassname)
                                        .setValue(classlistHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ClassListActivity.this, "Successfully Added into your Class List", Toast.LENGTH_SHORT).show();
                                            classlistSpinner.setVisibility(View.GONE);
                                            addButton.setVisibility(View.GONE);
                                            dialog.dismiss();

                                        }
                                    }
                                });
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });









    }








    public class MClassListViewHolder extends  RecyclerView.ViewHolder{

        private  TextView classnameTextview,subjectnameTextview;

        public MClassListViewHolder(@NonNull View itemView) {
            super(itemView);




            classnameTextview=itemView.findViewById(R.id.teacher_classlist_ClassNameTextviewid);
            subjectnameTextview=itemView.findViewById(R.id.teacher_classlist_subjectTextviewid);






        }
    }









}