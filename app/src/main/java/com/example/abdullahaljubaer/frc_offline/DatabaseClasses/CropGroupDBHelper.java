package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

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

    public synchronized String getCropGroup (String season) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + "*" + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_SEASON + " = '" + season + "'", null);



        String data = "";
        if (res.moveToFirst()) {
            data = res.getString(res.getColumnIndex(COLUMN_CROP_TYPE));
        }
        return data;
    }
}
