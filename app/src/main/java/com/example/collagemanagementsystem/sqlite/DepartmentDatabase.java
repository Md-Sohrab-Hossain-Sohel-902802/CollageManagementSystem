package com.example.collagemanagementsystem.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DepartmentDatabase {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private  static  DepartmentDatabase instance;
    Cursor c=null;


    private DepartmentDatabase(Context context){

        this.openHelper=new OpenDepartmentDatabase(context);

    }



    public static  DepartmentDatabase  getInstance(Context context){
        if(instance==null){
            instance=new DepartmentDatabase(context);
        }
        return  instance;
    }

    public void open(){
        this.db=openHelper.getReadableDatabase();
    }
    public void  close(){
        if(db!=null){
            this.db.close();
        }
    }



    public Cursor getAllDepartmentList(){
        String GETVALUE="SELECT * FROM dep";
        SQLiteDatabase sqLiteDatabase= openHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(GETVALUE,null);
        return cursor;

    }

}
