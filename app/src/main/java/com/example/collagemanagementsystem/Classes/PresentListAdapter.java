package com.example.collagemanagementsystem.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.ClassListActivity;
import com.example.collagemanagementsystem.PresentListActivity;
import com.example.collagemanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PresentListAdapter extends RecyclerView.Adapter<PresentListAdapter.MyViewHolder> {



    private EditText diolougeClassNameEdittext;
    private TextView diolougeTextviewid;
    private Button diolougeOkButtonid;


    private  Context context;

    private  List<Present> presentList=new ArrayList<>();
    Activity activity;
    String roll,department,classname,subjectname;

    public PresentListAdapter(PresentListActivity presentListActivity, Context context, List<Present> presentList, String roll, String department, String classname, String subjectname) {
        this.activity=presentListActivity;
        this.context = context;
        this.presentList = presentList;
        this.roll=roll;
        this.department=department;
        this.classname=classname;
        this.subjectname=subjectname;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.present_list_item_layout, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Present selectedItem=presentList.get(position);

                holder.nameTextview.setText("Date: "+selectedItem.getDate()+"\nTime: "+selectedItem.getTime());
                holder.rollTextview.setText("Roll : "+selectedItem.getRoll());
                holder.attendenceTextview.setText(selectedItem.getPresentance());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePresentDiolouge(selectedItem.getKey(),selectedItem.getDate(),selectedItem.getPresentance());
                    }
                });



    }


    @Override
    public int getItemCount() {
        return presentList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView nameTextview,rollTextview,attendenceTextview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            nameTextview=itemView.findViewById(R.id.presentListNameTextviewid);
            rollTextview=itemView.findViewById(R.id.presentListRollTextviewid);
            attendenceTextview=itemView.findViewById(R.id.presentlist_PresentTextviewid);

            attendenceTextview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            attendenceTextview.setSelected(true);




        }
    }
    private void updatePresentDiolouge(final String key,String date,String pattendence) {


            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            View view=activity.getLayoutInflater().inflate(R.layout.manageclasslist_customdiolouge,null);
            builder.setView(view);
            diolougeClassNameEdittext=view.findViewById(R.id.classlistDiolouge_ClassnameEdittextid);
            diolougeOkButtonid=view.findViewById(R.id.classlistDiolouge_OkButtonid);
            diolougeTextviewid=view.findViewById(R.id.classlistDiolouge_tilteTextviewid);

            diolougeTextviewid.setText("Date : "+date+"("+pattendence+")");

            diolougeClassNameEdittext.setHint("Enter Attendance (Present or Absent)");

            final AlertDialog dialog=builder.create();
            dialog.show();
            diolougeOkButtonid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String attendance=diolougeClassNameEdittext.getText().toString().toLowerCase();


                    if(attendance.isEmpty()){
                        diolougeClassNameEdittext.setError("Enter Attendance (Present or Absent)");
                        diolougeClassNameEdittext.requestFocus();
                        return;
                    }else if(!(attendance.equals("present") || attendance.equals("absent"))){
                        diolougeClassNameEdittext.setError("Attendance Can't be different (present or absent) ");
                        diolougeClassNameEdittext.requestFocus();
                        return;
                    }
                    else{
                        String currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference()
                                .child("collage")
                                .child(currentUser)
                                .child("mypresentList")
                                .child(department)
                                .child(classname);

                        databaseReference.child(subjectname).child(roll).child(key).child("presentance").setValue(attendance)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });

                    }

                }
            });
    }
}
