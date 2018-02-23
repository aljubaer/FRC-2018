package com.example.abdullahaljubaer.frc_offline;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ABDULLAH AL JUBAER on 24-02-18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "crop_nutrient.db";
    public static final Integer DATABASE_VERSION = 1;
    public static final String CROP_CLASS_TABLE_NAME = "crop_class";
    public static final String CROP_CLASS_COLUMN_SEASON = "season";
    public static final String CROP_CLASS_COLUMN_VARIETY = "variety";
    public static final String CROP_CLASS_COLUMN_CLASS = "class";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL)",
                CROP_CLASS_TABLE_NAME,
                CROP_CLASS_COLUMN_SEASON,
                CROP_CLASS_COLUMN_VARIETY,
                CROP_CLASS_COLUMN_CLASS);

        db.execSQL(queryCreateDB);

        String mCSVFile = "crop_class.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        String columns = CROP_CLASS_COLUMN_SEASON + ", " + CROP_CLASS_COLUMN_VARIETY + ", " + CROP_CLASS_COLUMN_CLASS;
        String str1 = "INSERT INTO " + CROP_CLASS_TABLE_NAME + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "' ");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CROP_CLASS_TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + CROP_CLASS_TABLE_NAME, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CROP_CLASS_TABLE_NAME);
        return numRows;
    }
}
