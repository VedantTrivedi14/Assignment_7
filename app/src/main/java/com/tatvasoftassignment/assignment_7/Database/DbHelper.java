package com.tatvasoftassignment.assignment_7.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    public static int id = 1;

    public DbHelper(Context context) {
        super(context, "City.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE City(id NUMBER, cityName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        id = id + 1;
        contentValues.put("cityName", cityName);
        db.insert("City", null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT DISTINCT * FROM City", null);
    }

    public void deleteData(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("City", "cityName=?", new String[]{cityName});
    }

    public boolean checkIfRecordExist(String columnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery("SELECT " + "cityName" + " FROM " + "City" + " WHERE " + "cityName" + "='" + columnValue + "'", null);
            if (cursor.moveToFirst()) {
                db.close();
                Log.d("Record  Already Exists", "Table is:" + "City" + " ColumnName:" + "cityName");
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:" + "City" + " ColumnName:" + "cityName" + " Column Value:" + columnValue);
            db.close();
        } catch (Exception errorException) {
            Log.d("Exception ", "Exception happen " + errorException);
            db.close();
        }
        return false;
    }
}
