package com.example.collagemanagementsystem;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.Classes.AllUser;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserActivity extends AppCompatActivity {

    private  RecyclerView recyclerView;

    private  DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);



        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("Users");

        recyclerView=findViewById(R.id.alluserRecyclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


















    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<AllUser> options=new FirebaseRecyclerOptions.Builder<AllUser>()
            .setQuery(databaseReference,AllUser.class)
                .build();



        FirebaseRecyclerAdapter<AllUser,AllUserViewHolder> adapter=new FirebaseRecyclerAdapter<AllUser, AllUserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final AllUserViewHolder holder, int position, @NonNull final AllUser alluserdataList) {
                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(alluserdataList.getUid())){

                    holder.sendMessageButton.setVisibility(View.GONE);

                }

                Picasso.get().load(alluserdataList.getImage()).placeholder(R.drawable.profile).into(holder.profileImage);
                holder.userNameTextview.setText(alluserdataList.getfName()+"("+alluserdataList.getlName()+")");


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(AllUserActivity.this,UserProfileActivity.class);
                        intent.putExtra("from","user");
                        intent.putExtra("uid",alluserdataList.getUid());
                        startActivity(intent);
                    }
                });



                holder.sendMessageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                            Intent intent = new Intent(AllUserActivity.this, ChatActivity.class);
                            intent.putExtra("name", alluserdataList.getfName() + "(" + alluserdataList.getlName() + ")");
                            intent.putExtra("image", alluserdataList.getImage());
                            intent.putExtra("uid", alluserdataList.getUid());

                            startActivity(intent);




                    }
                });







            }

            @NonNull
            @Override
            public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_item_layout,parent,false);



                return new AllUserViewHolder(view);
            }
        };



        recyclerView.setAdapter(adapter);
        adapter.startListening();







    }





    public class AllUserViewHolder extends  RecyclerView.ViewHolder{

        ImageView  profileImage;
        TextView userNameTextview;
        Button sendMessageButton;

        public AllUserViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.allUser_profileImageviewid);
            userNameTextview=itemView.findViewById(R.id.allUser_NameTextviewid);
            sendMessageButton=itemView.findViewById(R.id.allUser_sendMessageButtonid);



        }
    }









}