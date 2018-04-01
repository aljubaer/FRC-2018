package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.abdullahaljubaer.frc_offline.GUI.Interpretation;


/**
 * Created by ABDULLAH AL JUBAER on 23-03-18.
 */

public class STVIDBHelper extends DBHelper {

    private Context context;

    public static final String TABLE_NAME = "soil_test_value_interpretation";
    public static final String COLUMN_TEXTURE_CLASS = "texture_class";
    public static final String COLUMN_NUTRIENT = "nutrient";
    public static final String COLUMN_INTERPRETATION = "interpretation";
    public static final String COLUMN_LOWER_LIMIT = "lower_limit";
    public static final String COLUMN_UPPER_LIMIT = "upper_limit";
    public static final String COLUMN_INTERVAL = "interval";

    public STVIDBHelper(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int numberOfRows(){
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

    public Interpretation getInterpretation (String cls, String nutrient, double val){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_TEXTURE_CLASS + "= '" + cls + "'"
                + " AND " + COLUMN_NUTRIENT + "= '" + nutrient + "'"
                + " AND " + COLUMN_LOWER_LIMIT + "<= '" + val + "'"
                + " AND " + COLUMN_UPPER_LIMIT + ">= '" + val + "'", null);

        String d1 = "0", d2 = "0", d3 = "0", status = "";

        if (res.moveToFirst()) {
            status = res.getString(res.getColumnIndex(COLUMN_INTERPRETATION));
            d1 = res.getString(res.getColumnIndex(COLUMN_LOWER_LIMIT));
            d2 = res.getString(res.getColumnIndex(COLUMN_UPPER_LIMIT));
            d3 = res.getString(res.getColumnIndex(COLUMN_INTERVAL));
        }

        return new Interpretation(status, Double.parseDouble(d1), Double.parseDouble(d2), Double.parseDouble(d3));

    }

}
