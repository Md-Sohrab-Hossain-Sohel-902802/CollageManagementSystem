package com.example.collagemanagementsystem.ui.gallery;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.collagemanagementsystem.Classes.SideWork;
import com.example.collagemanagementsystem.R;
import com.example.collagemanagementsystem.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {






    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    private CircleImageView profileimageview;
    private TextView userNameTextview,userEmailTextview,userPhoneTextview;
    private Button uploadImageButton;

    private String currentUser;
    private Uri imageUri;

    private  TextView genderTextview,teacheratTextview,worksAtTextview,relationshipTextview;



    private EditText diolougeClassNameEdittext;
    private  Button diolougeOkButtonid;
    private  TextView diolougeTextviewid;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_profile, container, false);




        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage");
        storageReference= FirebaseStorage.getInstance().getReference().child("collage").child("ProfileImage");


        currentUser=mAuth.getCurrentUser().getUid();

        profileimageview=root.findViewById(R.id.profile_userProfileCircleImageviewid);
        userNameTextview=root.findViewById(R.id.profile_UserNameTextviewid);
        userEmailTextview=root.findViewById(R.id.profile_userEmailTextviewid);
        userPhoneTextview=root.findViewById(R.id.profile_UserPoneTextviewid);
        uploadImageButton=root.findViewById(R.id.profile_ProfileImageUploadButtonid);


        genderTextview=root.findViewById(R.id.profile_GenderTextviewid);

        worksAtTextview=root.findViewById(R.id.profile_WorksAtTextviewid);
        teacheratTextview=root.findViewById(R.id.profile_teacherOfTextviewid);
        relationshipTextview=root.findViewById(R.id.profile_RelationShipTextviewid);






        loadUserdate();




        profileimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });




        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        genderTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateCustomDiolouge("Gender");
            }
        });

        relationshipTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateCustomDiolouge("Relationship");
            }
        });
        worksAtTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateCustomDiolouge("WorksAt");
            }
        });
        teacheratTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateCustomDiolouge("TeacherAt");
            }
        });






















        return root;
    }







    private void loadUserdate() {

        databaseReference.child("Users").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){


                    if(snapshot.hasChild("image")){
                        String image1=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image1).placeholder(R.drawable.profile).into(profileimageview);
                    }else{

                        Picasso.get().load(R.drawable.profile).into(profileimageview);


                    }

                    String fname=snapshot.child("fName").getValue().toString();
                    String lName=snapshot.child("lName").getValue().toString();
                    String email=snapshot.child("email").getValue().toString();
                    String mobile=snapshot.child("mobile").getValue().toString();


                    if(snapshot.hasChild("gender")){
                        String gender=snapshot.child("gender").getValue().toString();
                        genderTextview.setText(Html.fromHtml("<h2>Gender : </h2>"+"\n"+gender));

                    }else{
                        genderTextview.setText(Html.fromHtml("<h2>Gender : </h2>"+"\n Set Your Gender"));

                    }
                    if(snapshot.hasChild("teacherat")){
                        String teacherat=snapshot.child("teacherat").getValue().toString();
                        teacheratTextview.setText(Html.fromHtml("<h2>Teacher At  : </h2>"+"\n "+teacherat));


                    }else{
                        teacheratTextview.setText(Html.fromHtml("<h2>Teacher At  : </h2>"+"\n Set UP this"));

                    }
                    if(snapshot.hasChild("worksat")){
                        String worksat=snapshot.child("worksat").getValue().toString();
                        worksAtTextview.setText(Html.fromHtml("<h2>Works At  : </h2>"+"\n"+worksat));


                    }else{
                        worksAtTextview.setText(Html.fromHtml("<h2>Works At  : </h2>"+"\n Choose One"));

                    }
                    if(snapshot.hasChild("relationship")){
                        String relationship=snapshot.child("relationship").getValue().toString();
                        relationshipTextview.setText(Html.fromHtml("<h2>Relationship : </h2>"+"\n "+relationship));


                    }else{
                        relationshipTextview.setText(Html.fromHtml("<h2>Relationship : </h2>"+"\n Setup Your Relationship"));


                    }




                    String sourceString ="<h2> Phone : </h2>"+"\n           "+mobile;
                    userPhoneTextview.setText(Html.fromHtml(sourceString));
                    userNameTextview.setText(fname+"("+lName+")");
                    userEmailTextview.setText(email);

/*
                        if(gender.equals("")){


                        }
                      else   if(teacherat.equals("")){


                        }    else   if(worksat.equals("")){


                        }
                    else   if(relationship.equals("")){

               }*/





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }



    public String getFileExtension(Uri imageuri){
        ContentResolver contentResolver=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageuri));
    }

    private void openfilechooser() {

        Intent intentf=new Intent();
        intentf.setType("image/*");
        intentf.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentf,1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null){

            uploadImageButton.setVisibility(View.VISIBLE);
            imageUri=data.getData();
            Picasso.get().load(imageUri.toString()).into(profileimageview);


        }




    }



    private void saveImage() {


        final SideWork sideWork=new SideWork();
        sideWork.showProgressdiolouge(getContext(),"Uploading Profile Image","Please Wait");



        StorageReference filePath=storageReference.child(mAuth.getCurrentUser().getUid()+"."+getFileExtension(imageUri));
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!urlTask.isSuccessful());
                Uri downloaduri=urlTask.getResult();

                databaseReference.child("Users").child(mAuth.getCurrentUser().getUid())
                        .child("image").setValue(downloaduri.toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Profile Image Added Successful", Toast.LENGTH_SHORT).show();

                                    sideWork.dismissProgressdiolouge();
                                    loadUserdate();

                                    uploadImageButton.setVisibility(View.GONE);


                                }
                            }
                        });
            }
        });





    }






    private void showUpdateCustomDiolouge(final String title) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.manageclasslist_customdiolouge,null);
        builder.setView(view);
        diolougeClassNameEdittext=view.findViewById(R.id.classlistDiolouge_ClassnameEdittextid);
        diolougeOkButtonid=view.findViewById(R.id.classlistDiolouge_OkButtonid);
        diolougeTextviewid=view.findViewById(R.id.classlistDiolouge_tilteTextviewid);

        diolougeTextviewid.setText(title);
        diolougeClassNameEdittext.setHint("Enter Your  "+title);

        final AlertDialog dialog=builder.create();
        dialog.show();
        diolougeOkButtonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String enteredValue=diolougeClassNameEdittext.getText().toString();


                if(enteredValue.isEmpty()){
                    diolougeClassNameEdittext.setError("Please Write Something Then click ok Button");
                    diolougeClassNameEdittext.requestFocus();
                    return;
                }
                else{
                    databaseReference.child("Users").child(mAuth.getCurrentUser().getUid())
                            .child(title.toLowerCase()).setValue(enteredValue).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loadUserdate();
                                Toast.makeText(getContext(), title+" Updated Successful", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });

                }

            }
        });





    }





















}