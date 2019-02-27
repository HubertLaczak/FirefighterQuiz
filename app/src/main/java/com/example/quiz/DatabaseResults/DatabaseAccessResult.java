package com.example.quiz.DatabaseResults;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccessResult {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccessResult instance;
    Cursor c = null;

    private DatabaseAccessResult(Context context){
        this.openHelper = new DatabaseOpenHelperResult(context);

    }

    public static DatabaseAccessResult getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccessResult(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    public String[] getResult(int questionId) {
        String[] columns = new String[]{ "Remember", "Correct"};
        Cursor c = db.query("Table1", columns, "Number='"+questionId+"'", null, null, null, null);
        c.moveToFirst();
        StringBuilder stringBuilder = new StringBuilder();
        String[] newResult = new String[2];
        for(int i = 0; i < 2; i++){
            newResult[i] = c.getString(i);
        }
        c.close();
        return newResult;
    }

    public boolean updateCorrect(int questionID, int correct, String tableName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Number",questionID);
//        contentValues.put("Remember", 1);
        contentValues.put("Correct", correct);
        db.update(tableName, contentValues, "Number = ?",new String[] { String.valueOf(questionID) });
        return true;
    }

    public boolean updateRemember(int questionID, int remember, String tableName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Number",questionID);
        contentValues.put("Remember", remember);
        db.update(tableName, contentValues, "Number = ?",new String[] { String.valueOf(questionID) });
        return true;
    }

    public int getRecordCount(String tableName) {
        int qCount = (int) DatabaseUtils.queryNumEntries(db, tableName);
        return qCount;
    }

    public int getCorrectCount(String tableName){
        c = db.rawQuery("SELECT * FROM  '"+tableName+"' WHERE Correct = '"+1+"'", null);
        int counter = 0;
        while (c.moveToNext()){
            counter++;
        }
        return counter;
    }

    public int getRememberCount(String tableName){
        c = db.rawQuery("SELECT * FROM '"+tableName+"' WHERE Remember = '"+1+"'", null);
        int counter = 0;
        while (c.moveToNext()){
            counter++;
        }
        return counter;
    }

    public int getIfRemembered(int questionID, String tableName) { //pobiera czy dane pytanie jest zapamiętane!
        c = db.rawQuery("SELECT Remember FROM '"+tableName+"' WHERE Number = '"+questionID+"'", null);
        int counter = 0;
        String one = null;
        while (c.moveToNext()){
//            counter = Integer.parseInt(c.getString(0));
            one = c.getString(0);
        }
        if (one.equals("1")){
            counter = 1;
        } else if (one.equals("0")){
            counter = 0;
        }
        return counter;
    }

    public void deleteResults() { //wysyłanie wszędzie zer, mam nadzieję :)
        ContentValues contentValues = new ContentValues();
        contentValues.put("Remember", 0);
        contentValues.put("Correct", 0);
        db.update("Table1", contentValues, null, null);
    }
}
