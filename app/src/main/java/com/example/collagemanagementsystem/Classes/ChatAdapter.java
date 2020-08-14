package com.example.collagemanagementsystem.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private  int counter=0;
    private  DatabaseReference databaseReference;
    private  Context context;
    private List<ChatData> dataList=new ArrayList<>();

    public ChatAdapter(Context context, List<ChatData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_layout, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final ChatData selecteditem=dataList.get(position);



        String  onlineUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String fromUserid=selecteditem.getFrom();

        String messageType=selecteditem.getType();



        databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("Users");
        databaseReference.child(fromUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {


                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(holder.reciverProfileImage);
                    } else {

                        Picasso.get().load(R.drawable.profile).into(holder.reciverProfileImage);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.receiverMessageTExt.setVisibility(View.GONE);
        holder.reciverProfileImage.setVisibility(View.GONE);
        holder.senderMessageTExt.setVisibility(View.GONE);
        holder.messageSenderPicture.setVisibility(View.GONE);
        holder.messageReceiverPicture.setVisibility(View.GONE);
        holder.receiverMessageTime.setVisibility(View.GONE);

if(messageType.equals("text")){
    if(fromUserid.equals(onlineUserid)){
        holder.senderMessageTExt.setVisibility(View.VISIBLE);
        holder.senderMessageTExt.setBackgroundResource(R.drawable.sender_messages_layout);
        holder.senderMessageTExt.setText(selecteditem.getMessage());


    }else{

        holder.reciverProfileImage.setVisibility(View.VISIBLE);
        holder.receiverMessageTExt.setVisibility(View.VISIBLE);
        holder.receiverMessageTExt.setBackgroundResource(R.drawable.recever_message_layout);



        holder.receiverMessageTExt.setText(selecteditem.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if(counter%2==0){

                    holder.receiverMessageTime.setVisibility(View.VISIBLE);
                    holder.receiverMessageTime.setText(selecteditem.getTime());
                }else{
                    holder.receiverMessageTime.setVisibility(View.GONE);
                }

            }
        });





    }









}














    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder  extends  RecyclerView.ViewHolder{
        public TextView senderMessageTExt,receiverMessageTExt;
        public CircleImageView reciverProfileImage;
        public ImageView messageSenderPicture,messageReceiverPicture;
        public TextView receiverMessageTime;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            senderMessageTExt=itemView.findViewById(R.id.sender_message_Text);
            receiverMessageTExt=itemView.findViewById(R.id.receiver_message_TExt);
            reciverProfileImage=itemView.findViewById(R.id.chatItem_ProfileImageviewid);
            messageReceiverPicture=itemView.findViewById(R.id.message_receiver_Imageview);
            messageSenderPicture=itemView.findViewById(R.id.message_sender_Imageview);
            receiverMessageTime=itemView.findViewById(R.id.receiver_message_Time);




        }
    }
}
