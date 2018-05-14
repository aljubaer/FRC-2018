package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Interpretation;


/**
 * Created by ABDULLAH AL JUBAER on 23-03-18.
 */

public class NutrientRecommendationDBHelper extends DBHelper {

    private Context context;

    public static final String TABLE_NAME = "nutrient_recommendation";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_NUTRIENT = "nutrient";
    public static final String COLUMN_INTERPRETATION = "interpretation";
    public static final String COLUMN_LOWER_LIMIT = "lower_limit";
    public static final String COLUMN_UPPER_LIMIT = "upper_limit";
    public static final String COLUMN_INTERVAL = "interval";

    public NutrientRecommendationDBHelper(Context context) {
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

    public Interpretation getInterpretation (String season, String cls, String status, String nutrient) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_SEASON + " = '" + season + "' AND "
                + COLUMN_CLASS + " = '" + cls + "' " + "AND " + COLUMN_INTERPRETATION
                + " = '" + status + "' AND "
                + COLUMN_NUTRIENT + " = '" + nutrient + "'", null );

        String d1 = "0", d2 = "0", d3 = "0";

        if (res.moveToFirst()) {
            d1 = res.getString(res.getColumnIndex(COLUMN_LOWER_LIMIT));
            d2 = res.getString(res.getColumnIndex(COLUMN_UPPER_LIMIT));
            d3 = res.getString(res.getColumnIndex(COLUMN_INTERVAL));
        }

        return new Interpretation(status, d1, d2, d3);

    }


}
