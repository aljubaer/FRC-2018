package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.abdullahaljubaer.frc_offline.GUI.Interpretation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    public synchronized void onCreate(SQLiteDatabase db) {

        String queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                "( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL, " +
                "%s float NOT NULL, %s float NOT NULL, %s float NOT NULL)",
                TABLE_NAME,
                COLUMN_TEXTURE_CLASS,
                COLUMN_NUTRIENT,
                COLUMN_INTERPRETATION,
                COLUMN_LOWER_LIMIT,
                COLUMN_UPPER_LIMIT,
                COLUMN_INTERVAL
                );

        db.execSQL(queryCreateDB);

        String mCSVFile = "soil_test_values_interpretation.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        String columns = COLUMN_TEXTURE_CLASS + ", "
                + COLUMN_NUTRIENT + ", "
                + COLUMN_INTERPRETATION + ", "
                + COLUMN_LOWER_LIMIT + ", "
                + COLUMN_UPPER_LIMIT + ", "
                + COLUMN_INTERVAL;
        String str1 = "INSERT INTO " + TABLE_NAME + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "', ");
                sb.append("'" + str[3] + "', ");
                sb.append("'" + str[4] + "', ");
                sb.append("'" + str[5] + "' ");
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
