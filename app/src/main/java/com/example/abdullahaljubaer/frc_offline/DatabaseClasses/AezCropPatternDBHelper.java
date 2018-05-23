package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 24-05-18.
 */

public class AezCropPatternDBHelper extends DBHelper {

    public static String TABLE_NAME = "crop_patterns";
    private Context context;

    public AezCropPatternDBHelper(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public ArrayList<String> getAllCropPatterns (String aezNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT DISTINCT crop_pattern FROM " + TABLE_NAME + " WHERE aez_no = " + aezNo, null);
        return readCursor(res, "crop_pattern");
    }
}
