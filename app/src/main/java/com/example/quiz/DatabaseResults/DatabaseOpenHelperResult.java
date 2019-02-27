package com.example.quiz.DatabaseResults;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelperResult extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Wyniki.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelperResult(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
