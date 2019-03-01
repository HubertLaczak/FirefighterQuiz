package com.example.quiz.DatabaseQuestions;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quiz.Others.ExQuestion;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    ExQuestion exQuestion;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
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

    public String getRaw(String number){
        c = db.rawQuery("select Question from Table1 where Number = '"+number+"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()){
            String raw = c.getString(0);
            buffer.append(""+ raw);
        }
        return buffer.toString();
    }

    public String[] getAllQuestions(String tableName) {
        c = db.rawQuery("select Question from " + tableName, new String[]{});
        String [] questionArray = new String[c.getCount()];
        int i = 0;
        while (c.moveToNext()){
            String quest = c.getString(0);
            questionArray[i] = quest;
            i++;
        }
        return questionArray;
    }



    public ExQuestion getOneQuestion(int number) {
        c = db.rawQuery("SELECT * FROM Table1 WHERE Number = '"+number+"'", null);
        String [] rawArray = new String[c.getColumnCount()];
        int i = 0;
        while (c.moveToNext()){
            String quest = c.getString(0);
            rawArray[i] = quest;
            i++;
        }

        return exQuestion;
    }


    public String[] getName(int number, String tableName) {
        String[] columns = new String[]{ "Question", "WrongAnswer1", "WrongAnswer2", "WrongAnswer3", "WrongAnswer4", "CorrectAnswer" };
        Cursor c = db.query(tableName, columns, "Number='"+number+"'", null, null, null, null);
        c.moveToFirst();
        String[] names = new String[6];
        for(int i = 0; i < 6; i++){
            names[i] = c.getString(i);
        }
        c.close();
        return names;
    }

    public int getRecordsCount(String tableName) {
        int qCount = (int) DatabaseUtils.queryNumEntries(db, tableName);
        return qCount;
    }


    public String[] getQuestionText(String tableName, String number) {
        String[] columns = new String[]{ "Question", "WrongAnswer1", "WrongAnswer2", "WrongAnswer3", "WrongAnswer4", "CorrectAnswer" };
        Cursor c = db.query(tableName, columns, "Number='"+number+"'", null, null, null, null);
        c.moveToFirst();
        String[] names = new String[6];
        for(int i = 0; i < 6; i++){
            names[i] = c.getString(i);
        }
        c.close();
        return names;
    }
}
