package com.example.collagemanagementsystem.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;







public class OpenDepartmentDatabase  extends SQLiteAssetHelper {

        private  static  final  String DATABASE_NAME="soh.db";
        private  static  final  String TABLE_NAME="";
        private  static  final  int VERSION_NUMBER=1;




    public OpenDepartmentDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);






    }
}
