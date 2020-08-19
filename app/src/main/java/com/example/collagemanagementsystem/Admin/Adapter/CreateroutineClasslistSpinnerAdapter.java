package com.example.collagemanagementsystem.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collagemanagementsystem.Admin.Classes.ShhortcutClasslist;
import com.example.collagemanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class CreateroutineClasslistSpinnerAdapter extends BaseAdapter {



    private  Context context;
    private List<ShhortcutClasslist>  classlists=new ArrayList<>();

    public CreateroutineClasslistSpinnerAdapter(Context context, List<ShhortcutClasslist> classlists) {
        this.context = context;
        this.classlists = classlists;
    }

    @Override
    public int getCount() {
        return classlists.size();
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

            ShhortcutClasslist currentItem=classlists.get(position);



            textView.setText(currentItem.getName());





        }





        return convertView;
    }
}
