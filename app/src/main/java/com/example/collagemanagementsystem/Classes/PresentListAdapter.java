package com.example.collagemanagementsystem.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collagemanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class PresentListAdapter extends RecyclerView.Adapter<PresentListAdapter.MyViewHolder> {


    private  Context context;

    private  List<Present> presentList=new ArrayList<>();

    public PresentListAdapter(Context context, List<Present> presentList) {
        this.context = context;
        this.presentList = presentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.present_list_item_layout, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Present selectedItem=presentList.get(position);

                holder.nameTextview.setText(selectedItem.getDate()+"\n      "+selectedItem.getTime());
                holder.rollTextview.setText(selectedItem.getRoll());
                holder.attendenceTextview.setText(selectedItem.getPresentance());
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






        }
    }
}
