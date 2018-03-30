package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ABDULLAH AL JUBAER on 28-03-18.
 */

public class CropGroupDBHelper extends DBHelper {

    private Context context;
    public static final String TABLE_NAME = "crop_group";
    public static final String COLUMN_GROUP = "crop_group";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_CROP_TYPE = "crop_type";

    public CropGroupDBHelper(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public synchronized void onCreate(SQLiteDatabase db) {
        String queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL)",
                TABLE_NAME,
                COLUMN_GROUP,
                COLUMN_SEASON,
                COLUMN_CROP_TYPE);

        db.execSQL(queryCreateDB);

        String mCSVFile = "crop_group.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        String columns = COLUMN_GROUP + ", " + COLUMN_SEASON + ", " + COLUMN_CROP_TYPE;
        String str1 = "INSERT INTO " + TABLE_NAME + " (" + columns + ") values(";
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
    public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    @Override
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        return res;
    }

    public String getCropGroup (String season) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + COLUMN_CROP_TYPE + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_SEASON + " = '" + season + "'", null);

        String data = "";
        if (res.moveToFirst()) {
            data = res.getString(res.getColumnIndex(COLUMN_CROP_TYPE));
        }
        return data;
    }
}
