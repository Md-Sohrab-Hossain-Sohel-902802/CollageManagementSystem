package com.example.collagemanagementsystem.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collagemanagementsystem.Classes.DepartmentList;
import com.example.collagemanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AdminMainListAdapter extends BaseAdapter {



    private  Context context;
    private List<DepartmentList> departmentLists=new ArrayList<>();


    public AdminMainListAdapter(Context context, List<DepartmentList> departmentLists) {
        this.context = context;
        this.departmentLists = departmentLists;
    }

    @Override
    public int getCount() {
        return departmentLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.admin_main_list_item_layout, parent, false);



            TextView textView=convertView.findViewById(R.id.admain_list_Textviewid);
            DepartmentList selectedItem=departmentLists.get(position);

            textView.setText(selectedItem.getDepartment());





        }





        return convertView;
    }
}
