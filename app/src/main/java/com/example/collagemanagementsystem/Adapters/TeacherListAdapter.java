package com.example.collagemanagementsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.DataModuler.TeacherList;
import com.example.collagemanagementsystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherListAdapter  extends RecyclerView.Adapter<TeacherListAdapter.MyViewHolder> {

private Context context;
private List<TeacherList> teacherLists=new ArrayList<>();

    public TeacherListAdapter(Context context, List<TeacherList> teacherLists) {
        this.context = context;
        this.teacherLists = teacherLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.teacher_list_item_layout,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                TeacherList currentItem=teacherLists.get(position);


                if(currentItem.getImage()==null){

                    Picasso.get().load(R.drawable.profile).into(holder.imageView);

                }else{
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/likefacebook-9445b.appspot.com/o/profileImage%2F1595948708659.jpg?alt=media&token=f8640d4f-246e-4489-9fb8-c7a877427c2e").into(holder.imageView);
                }


                holder.nameTextview.setText(currentItem.getName());
                holder.departmentTextview.setText(currentItem.getDepartment());






    }

    @Override
    public int getItemCount() {
        return teacherLists.size();
    }

    public class MyViewHolder  extends  RecyclerView.ViewHolder{


         CircleImageView imageView;
         TextView nameTextview;
         TextView departmentTextview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView=itemView.findViewById(R.id.teacherlist_circleImageviewid);
            nameTextview=itemView.findViewById(R.id.teacherlist_NameTextviewid);
            departmentTextview=itemView.findViewById(R.id.teacherlistDepartmentTextviewid);




        }
    }
}
