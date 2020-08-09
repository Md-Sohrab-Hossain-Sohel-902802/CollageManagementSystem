package com.example.collagemanagementsystem.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collagemanagementsystem.R;

public class MainGridAdapters  extends BaseAdapter {

    private Context ctx;
    String[] text;
    int[]  images;
    LayoutInflater layoutInflater;


    public MainGridAdapters(Context ctx, String[] text, int[] images) {
        this.ctx = ctx;
        this.text = text;
        this.images=images;
    }

    @Override
    public int getCount() {
        return text.length;
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

                layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              convertView=layoutInflater.inflate(R.layout.maingridviewitemlayout,parent,false);



        }


        ImageView imageView;
        TextView textView;

        imageView=convertView.findViewById(R.id.mainGrid_ImageViewid);
        textView=convertView.findViewById(R.id.mainGrid_Textviewid);



        imageView.setImageResource(images[position]);
        textView.setText(text[position]);










        return  convertView;
    }
}
