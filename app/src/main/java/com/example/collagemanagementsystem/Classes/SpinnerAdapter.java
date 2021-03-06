package com.example.collagemanagementsystem.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collagemanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {


    private  Context ctx;
    private  List<classlist> datalist=new ArrayList<>();


    public SpinnerAdapter(Context ctx, List<classlist> datalist) {
        this.ctx = ctx;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return  datalist.size();
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


        if(convertView==null){

            LayoutInflater layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.spinner_sample_layoute,parent,false);


            TextView textView=convertView.findViewById(R.id.spinner_sample_layoutTextviewid);

            classlist data=datalist.get(position);

            textView.setText(data.getName());











        }





        return convertView;
    }
}
