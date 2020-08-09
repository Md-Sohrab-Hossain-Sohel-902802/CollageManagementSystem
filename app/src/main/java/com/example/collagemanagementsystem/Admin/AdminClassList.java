package com.example.collagemanagementsystem.Admin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collagemanagementsystem.Admin.Classes.ClassList;
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

public class AdminClassList extends AppCompatActivity {



    private RecyclerView recyclerView;

    private  Toolbar toolbar;

    private  DatabaseReference databaseReference;
    private  String selectedItemname;


    //<M------------------------For Custom Diolouge----------------------------------->


    private  EditText  diolougeClassNameEdittext;
    private  Button diolougeOkButtonid;
    private  TextView diolougeTextviewid;


    String fromWhere;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class_list);


        selectedItemname=getIntent().getStringExtra("sname");
        fromWhere=getIntent().getStringExtra("from");

        if(fromWhere.equals("mngclasslist")){
            toolbar=findViewById(R.id.adminclasslsitToolbarid);

            setSupportActionBar(toolbar);

        }else if(     fromWhere.equals("managestudent")){
            toolbar=findViewById(R.id.adminclasslsitToolbarid);
            toolbar.setVisibility(View.GONE);
        }



        this.setTitle(""+selectedItemname);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("classlist");


        recyclerView=findViewById(R.id.adminClasslistRecyclerviweid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));















    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<ClassList> options=new FirebaseRecyclerOptions.Builder<ClassList>()
                .setQuery(databaseReference.child(selectedItemname),ClassList.class).build();


        FirebaseRecyclerAdapter<ClassList,ClassListViewHolder> adapter=new FirebaseRecyclerAdapter<ClassList, ClassListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ClassListViewHolder holder, int position, @NonNull final ClassList datalist) {

                holder.textView.setText(datalist.getName());





                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        final String selectedItems=datalist.getKey();
                        manageDeleteAndUpdate(selectedItems);

                        return false;
                    }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(fromWhere.equals("managestudent")){
                          final String selectedClassname=datalist.getName();

                          Intent intent=new Intent(AdminClassList.this,AdminStudentListActivity.class);
                          intent.putExtra("className",selectedClassname);
                          intent.putExtra("department",selectedItemname);

                            startActivity(intent);

                      }else if(fromWhere.equals("mngclasslist")){
                          Toast.makeText(AdminClassList.this, "click", Toast.LENGTH_SHORT).show();
                      }
                    }
                });







            }

            @NonNull
            @Override
            public ClassListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_main_list_item_layout, parent, false);

                return new ClassListViewHolder(view);
            }
        };



    recyclerView.setAdapter(adapter);
    adapter.startListening();









    }

    private void manageDeleteAndUpdate(final String selectedItems) {



        CharSequence options[]=new CharSequence[]{
                "Update",
                "Delete"
        };



        AlertDialog.Builder builder=new AlertDialog.Builder(AdminClassList.this);
        builder.setTitle("Choose An Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    updateCustomDiolouge("Update Class Name",selectedItems);

                }else if(which==1){
                    databaseReference.child(selectedItemname)
                            .child(selectedItems).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AdminClassList.this, "Succesfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }






            }
        });

        builder.show();




    }

    private void updateCustomDiolouge(String update_class_name, final String skey) {

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminClassList.this);
        View view=getLayoutInflater().inflate(R.layout.manageclasslist_customdiolouge,null);
        builder.setView(view);
        diolougeClassNameEdittext=view.findViewById(R.id.classlistDiolouge_ClassnameEdittextid);
        diolougeOkButtonid=view.findViewById(R.id.classlistDiolouge_OkButtonid);
        diolougeTextviewid=view.findViewById(R.id.classlistDiolouge_tilteTextviewid);

        diolougeTextviewid.setText(update_class_name);


        final AlertDialog dialog=builder.create();
        dialog.show();
        diolougeOkButtonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String classname=diolougeClassNameEdittext.getText().toString();


                if(classname.isEmpty()){
                    diolougeClassNameEdittext.setError("Enter A Class Name Like(Computer 5cmtB2)");
                    diolougeClassNameEdittext.requestFocus();
                    return;
                }
                else{

                    databaseReference.child(selectedItemname)
                            .child(skey)
                            .child("name")
                            .setValue(classname)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(AdminClassList.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.admin_classlist_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.classlistMenu_AddButtonid){
                        showcustomdioloage("Add New Class");
        }



        return super.onOptionsItemSelected(item);
    }





    public void showcustomdioloage(String title){
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminClassList.this);
        View view=getLayoutInflater().inflate(R.layout.manageclasslist_customdiolouge,null);
        builder.setView(view);
        diolougeClassNameEdittext=view.findViewById(R.id.classlistDiolouge_ClassnameEdittextid);
        diolougeOkButtonid=view.findViewById(R.id.classlistDiolouge_OkButtonid);
        diolougeTextviewid=view.findViewById(R.id.classlistDiolouge_tilteTextviewid);

        diolougeTextviewid.setText(title);


        final AlertDialog dialog=builder.create();
        dialog.show();
        diolougeOkButtonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String classname=diolougeClassNameEdittext.getText().toString();


                if(classname.isEmpty()){
                    diolougeClassNameEdittext.setError("Enter A Class Name Like(Computer 5cmtB2)");
                    diolougeClassNameEdittext.requestFocus();
                    return;
                }
                else{
                    saveClassname(classname,diolougeClassNameEdittext);
                }



            }
        });
    }

    private void saveClassname(final String classname, final EditText diolougeClassNameEdittext) {



        databaseReference.child(selectedItemname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    if(snapshot.hasChild(classname)){
                        Toast.makeText(AdminClassList.this, "This Class is Already Exists", Toast.LENGTH_SHORT).show();
                    }else if(!snapshot.hasChild(classname)){
                        HashMap<String, String>  classlisthashMap=new HashMap<>();



                        classlisthashMap.put("name",classname);
                        classlisthashMap.put("key",classname);


                        databaseReference.child(selectedItemname)
                                .child(classname)
                                .setValue(classlisthashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AdminClassList.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }

                    else{



                  HashMap<String, String>  classlisthashMap=new HashMap<>();



                    classlisthashMap.put("name",classname);
                    classlisthashMap.put("key",classname);



                    databaseReference.child(selectedItemname)
                            .child(classname)
                            .setValue(classlisthashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AdminClassList.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
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



    public class ClassListViewHolder extends  RecyclerView.ViewHolder{

        private  TextView textView;

        public ClassListViewHolder(@NonNull View itemView) {
            super(itemView);




            textView=itemView.findViewById(R.id.admain_list_Textviewid);






        }
    }


















}