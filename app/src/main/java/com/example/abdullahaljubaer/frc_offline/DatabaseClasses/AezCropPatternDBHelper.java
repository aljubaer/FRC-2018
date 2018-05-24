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
        return super.readCursor(res, "crop_pattern");
    }

    public ArrayList<ArrayList<String>> getPatternRecommendation (String aezNo, String cp) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        ArrayList<ArrayList<String>> recommendation = new ArrayList<>();
        res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE aez_no = "
                + aezNo + " AND crop_pattern = '" + cp + "'", null);
        String[] rows = {"crop", "yield_goal", "N", "P", "K", "S", "Mg", "Zn", "B", "Mo", "cowdung", "poultry_manure"};
        for (String row : rows) {
            recommendation.add(readCursor(res, row));
        }
        res.close();
        return recommendation;
    }

    ArrayList<String> readCursor(Cursor cursor, String column ) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex(column));
                arrayList.add(data);
            }while(cursor.moveToNext());
        }
        //cursor.close();
        return arrayList;
    }

}
