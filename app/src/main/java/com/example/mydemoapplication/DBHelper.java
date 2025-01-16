package com.example.mydemoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // this is for the constructor for the parent class ;
        // we are taking only the Context as the parameter and the rest we are passing to the super class constructor
        // by hard coding the values .
        super(context,"Userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(name TEXT primary key,contact TEXT , dob TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists UserDetails");
    }

    // the Db name has to be unique in our DBHelper class

    public boolean insertUserData(String name , String contact , String dob){
        SQLiteDatabase DB = this.getWritableDatabase();
        // this content values is like an object to put data into our table
        ContentValues contentValues = new ContentValues();
        contentValues.put("name" , name);
        contentValues.put("contact" , contact);
        contentValues.put("dob" , dob);
        long result = DB.insert("UserDetails",null,contentValues);
        if(result == -1)
        {
            return  false ;
        }
        else {
            return true;
        }
    }
    public boolean updateUserData(String name , String contact , String dob){
        SQLiteDatabase DB = this.getWritableDatabase();
        // this content values is like an object to put data into our table
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact" , contact);
        contentValues.put("dob" , dob);
        // cursor is like selecting the row ; just like in excel
        // So basically it will check whether the name matches with the name given in the method
         Cursor cursor = DB.rawQuery("SELECT * FROM UserDetails WHERE name = ?",new String[]{name});
         if(cursor.getCount()>0) {
             // update(tablename , contentvalues,whereclause,where argument)
             long result = DB.update("UserDetails", contentValues, "name=?", new String[]{name});
             if (result == -1) {
                 return false;
             } else {
                 return true;
             }
         }else{
             return false;
         }
         // if the cursor does not have any data and it goes through the update method , it will
        // return true even if the cursor has null value or the name is not present in the DB
    }


    public boolean deleteUserData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM UserDetails WHERE name = ?",new String[]{name});
        if(cursor.getCount()>0) {
            // delete(tablename ,whereclause,where argument) -- > 3 arguments
            long result = DB.delete("UserDetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
        // if the cursor does not have any data and it goes through the update method , it will
        // return true even if the cursor has null value or the name is not present in the DB
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM UserDetails",null);
        return cursor;

    }
}
