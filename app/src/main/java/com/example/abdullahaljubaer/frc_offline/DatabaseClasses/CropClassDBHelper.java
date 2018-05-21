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
 * Created by ABDULLAH AL JUBAER on 24-02-18.
 */

public class CropClassDBHelper extends DBHelper {

    private Context context;

    public static final String TABLE_NAME = "crop_class";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_VARIETY = "variety";
    public static final String COLUMN_CLASS = "class";

    public CropClassDBHelper(Context context) {
        super(context);
        this.context = context;
    }

    public Cursor getAllCropSeason(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT DISTINCT `season` FROM "
                + TABLE_NAME
                + " ORDER BY `season`", null );
        //res.close();
        return res;
    }

    public Cursor getAllVariety( String season ){
        String s = "'" + season + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT DISTINCT `variety` FROM "
                + TABLE_NAME
                + " WHERE `season` = " + s, null );
        //res.close();
        return res;
    }

    @Override
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.close();
        return res;
    }

    @Override
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public String getClass (String season, String var) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT " + COLUMN_CLASS + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_SEASON + " = '" + season + "' AND " + COLUMN_VARIETY
                + " = '" + var + "'", null );
        String cClass = "NULL";

        if (res.moveToFirst()){
            cClass = res.getString(res.getColumnIndex(COLUMN_CLASS));;
        }
        res.close();
        return cClass;
    }

}
