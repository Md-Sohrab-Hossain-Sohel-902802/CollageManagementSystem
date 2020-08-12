package com.example.collagemanagementsystem.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.collagemanagementsystem.Admin.AdminDepartmentActivity;
import com.example.collagemanagementsystem.AllUserActivity;
import com.example.collagemanagementsystem.Classes.MainGridAdapters;
import com.example.collagemanagementsystem.Classes.SideWork;
import com.example.collagemanagementsystem.HomeActivity;
import com.example.collagemanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {


    private FirebaseAuth mAuth;



    private Toolbar toolbar;


    private GridView gridView;

    private MainGridAdapters adapters;

    private  int[] images={R.drawable.present,R.drawable.present};
    private  String[] text={"Start Taking Presents","Teachers"};
    private SideWork sideWork;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_home, container, false);



        mAuth=FirebaseAuth.getInstance();

        gridView=root.findViewById(R.id.mainGridViewid);

        adapters=new MainGridAdapters(getContext(),text,images);

        gridView.setAdapter(adapters);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(getContext(), AdminDepartmentActivity.class);
                    intent.putExtra("activity","main");
                    startActivity(intent);
                }     if(position==1){
                    Intent intent=new Intent(getContext(), AllUserActivity.class);
                   startActivity(intent);
                }
            }
        });





        return root;
    }
}