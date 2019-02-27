package com.example.quiz.Others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "results.db";
    private static String TABLE_NAME = "myTable";
    private static final String COL1 = "Chapter";
    private static final String COL2 = "Number";
    private static final String COL3 = "Correct";
    private static final String COL4 = "Remember";

    private static DatabaseHelper mInstance = null;


    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (Chapter TEXT, Number TEXT PRIMARY KEY, " +
                "Correct INTEGER, Remember INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Chapter, String Number, int Correct, int Remember){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, Chapter);
        contentValues.put(COL2, Number);
        contentValues.put(COL3, Correct);
        contentValues.put(COL4, Remember);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public String getCorrect(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] kolumny = {"Correct"};
        Cursor res = db.query(TABLE_NAME, kolumny, null,null,null,null,null);

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append(res.getString(0));
        }
        String resp = buffer.toString();
        return resp;
    }


    public void setToUnCorrect(String Chapter, String Number, int Correct, int Remember) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, Chapter);
        contentValues.put(COL2, Number);
        contentValues.put(COL3, Correct);
        contentValues.put(COL4, Remember);
        db.update(TABLE_NAME, contentValues, "Number = ?",new String[] { Number });
    }
}
