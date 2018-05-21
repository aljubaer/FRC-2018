package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by ABDULLAH AL JUBAER on 23-03-18.
 */

public class TextureClassDBHelper extends DBHelper {

    private Context context;
    public static final String TABLE_NAME = "texture_class";
    public static final String COLUMN_TEXTURE = "texture";
    public static final String COLUMN_CROP_TYPE = "crop_type";
    public static final String COLUMN_CLASS = "class";


    public TextureClassDBHelper(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        return res;
    }

    public Cursor getDistTexture() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT DISTINCT " + COLUMN_TEXTURE + " FROM " + TABLE_NAME, null );
        return res;
    }

    public String getClass ( String text, String type ) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT " + COLUMN_CLASS + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_TEXTURE + " = '" + text
                + "' AND " + COLUMN_CROP_TYPE + " = '" + type + "'", null );

        String data = "";
        if (res.moveToFirst()) {
            data = res.getString(res.getColumnIndex(COLUMN_CLASS));
        }
        res.close();
        return data;
    }
}
