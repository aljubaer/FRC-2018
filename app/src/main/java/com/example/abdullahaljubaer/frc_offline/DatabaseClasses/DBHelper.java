package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ABDULLAH AL JUBAER on 23-03-18.
 */

public abstract class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crop_nutrient.db";
    private static final Integer DATABASE_VERSION = 13;
    private final Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public synchronized void onCreate(SQLiteDatabase db) {

    }

    @Override
    public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public abstract int numberOfRows();
    public abstract Cursor getData();
}
