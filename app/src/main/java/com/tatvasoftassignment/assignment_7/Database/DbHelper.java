package com.tatvasoftassignment.assignment_7.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
