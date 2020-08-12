package com.example.collagemanagementsystem.Admin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collagemanagementsystem.Admin.Classes.StudentList;
import com.example.collagemanagementsystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminStudentListActivity extends AppCompatActivity {

    private  String className,departmentName;



    private  RecyclerView recyclerView;



    private  DatabaseReference databaseReference;




    //<---------------------for custom diolouge-------------------->


    private  EditText studentNameEdittext,rollEditext;
    private  Button saveButton;
    private  String name,email;
    private  Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_list);



        toolbar=findViewById(R.id.admin_studentLIstToolbarid);
        setSupportActionBar(toolbar);


        className=getIntent().getStringExtra("className");
        departmentName=getIntent().getStringExtra("department");


        this.setTitle(""+className);









        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("Students").child(departmentName);


        recyclerView=findViewById(R.id.admin_studentListRecyclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
















    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<StudentList> options=new FirebaseRecyclerOptions.Builder<StudentList>()
                .setQuery(databaseReference.child(className),StudentList.class)
                .build();



        FirebaseRecyclerAdapter<StudentList,StudentListViewHolder> adapter=new FirebaseRecyclerAdapter<StudentList, StudentListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentListViewHolder holder, int position, @NonNull StudentList dataList) {


                holder.nameTextview.setText(dataList.getName());
                holder.rollTextview.setText(dataList.getRoll());




            }

            @NonNull
            @Override
            public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_student_list_item_layout, parent, false);

                StudentListViewHolder holder=new StudentListViewHolder(view);

                return holder;
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
            customDiolouge();
        }






        return super.onOptionsItemSelected(item);
    }

    private void customDiolouge() {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminStudentListActivity.this);
        View view=getLayoutInflater().inflate(R.layout.admin_addstudent_custom_diolouge,null);
        builder.setView(view);
        studentNameEdittext=view.findViewById(R.id.diolouge_StudentNameEdittextid);
        rollEditext=view.findViewById(R.id.diolouge_StudentRollEdittextid);
        saveButton=view.findViewById(R.id.diolouge_SaveButtonid);

        final AlertDialog dialog=builder.create();
        dialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sname=studentNameEdittext.getText().toString();
                final String sRoll=rollEditext.getText().toString();

                if(sname.isEmpty()){
                    studentNameEdittext.setError("Enter Student Name ");
                    studentNameEdittext.requestFocus();
                    return;
                }else if(sRoll.isEmpty())
                {

                    rollEditext.setError("Enter Roll");
                    rollEditext.requestFocus();
                    return;


                } else {

                    databaseReference.child(className).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(sRoll)){
                                rollEditext.setText("");
               }else{
                                HashMap<String, Object> dataList = new HashMap<>();
                                dataList.put("name",sname);
                                dataList.put("roll",sRoll);
                                dataList.put("key",sRoll);



                                databaseReference.child(className).child(sRoll).updateChildren(dataList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AdminStudentListActivity.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                            studentNameEdittext.setText("");
                                            rollEditext.setText("");
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









    public class StudentListViewHolder extends  RecyclerView.ViewHolder{


        TextView nameTextview;
        TextView rollTextview;



        public StudentListViewHolder(@NonNull View itemView) {
            super(itemView);



            nameTextview=itemView.findViewById(R.id.adstudentlist_nameTextviewid);
            rollTextview=itemView.findViewById(R.id.adstudentlist_RollTextviewid);









        }
    }








}