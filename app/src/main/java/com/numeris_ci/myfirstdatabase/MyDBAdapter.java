package com.numeris_ci.myfirstdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBAdapter {
    Context context;
    private myDBHelper dbHelper;
    private SQLiteDatabase db;
    private String DATABASE_NAME = "data";
    private int DATABASE_VERSION = 3;

    public MyDBAdapter(Context context) {
        this.context = context;
        dbHelper = new myDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE students (id integer primary key autoincrement, name text, faculty integer)";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS students;";
            db.execSQL(query);
            onCreate(db);
        }
    }

    public void insertStudent(String name, int faculty) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("faculty", faculty);
        db.insert("students", null, cv);
    }

    public ArrayList<String> selectAllStudents() {
        ArrayList<String> allStudents = new ArrayList<String>();
        Cursor cursor = db.query("students", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                allStudents.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return allStudents;
    }

    public void deleteAllEngineers() {
        db.delete("students", "faculty=1", null);
    }
}
