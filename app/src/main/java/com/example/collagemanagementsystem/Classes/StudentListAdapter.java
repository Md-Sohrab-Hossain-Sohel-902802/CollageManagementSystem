package com.example.collagemanagementsystem.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.PresentListActivity;
import com.example.collagemanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class StudentListAdapter extends  RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    private  DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private  String currentUser;




    private  Context context;
    private  List<Student> studentList=new ArrayList<>();

    private  String classname;
    private  String departmentName;
    private  String subjectName;


    public StudentListAdapter(Context context, List<Student> studentList, String classname, String departmentName, String subjectName) {
        this.context = context;
        this.studentList = studentList;
        this.classname = classname;
        this.departmentName = departmentName;
        this.subjectName = subjectName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_sample_layoute, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {




            holder.nameTextview.setText(studentList.get(position).getName().toUpperCase());
            holder.rollTextview.setText(studentList.get(position).getRoll());




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PresentListActivity.class);


                    intent.putExtra("departmentname",departmentName);
                    intent.putExtra("roll",studentList.get(position).getRoll());
                    intent.putExtra("classname",classname);
                    intent.putExtra("subjectname",subjectName);
                    intent.putExtra("name",studentList.get(position).getName());



                    context.startActivity(intent);
                }
            });

            holder.presentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        setPresentAbsent("present",studentList.get(position).getRoll());
                }
            });


            holder.absentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPresentAbsent("absent",studentList.get(position).getRoll());
                }
            });





    }



    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nameTextview,rollTextview;
        Button presentButton,absentButton;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextview=itemView.findViewById(R.id.studentList_nameTextviewid);
            rollTextview=itemView.findViewById(R.id.studentList_RollTextviewid);
            presentButton=itemView.findViewById(R.id.studentList_PresentButtonid);
            absentButton=itemView.findViewById(R.id.studentList_AbsentButtonid);









        }
    }




    private void setPresentAbsent(final String present, final String roll) {


        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child(currentUser).child("mypresentList");

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());



        String key=databaseReference.push().getKey();

        HashMap<String,Object> presentMap=new HashMap<>();

        presentMap.put("date",currentDate);
        presentMap.put("time",currentTime);
        presentMap.put("key",key);
        presentMap.put("roll",roll);
        presentMap.put("subjectName",subjectName);
        presentMap.put("presentance",present);


        databaseReference.child(departmentName)
                                        .child(classname)
                                            .child(subjectName)
                                            .child(roll)
                                            .child(key)
                                            .updateChildren(presentMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(context, roll+" is "+present, Toast.LENGTH_SHORT).show();
                                                        }
                                                }
                                            });























    }








}
