package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABDULLAH AL JUBAER on 23-03-18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "frc.db";
    public static final Integer DATABASE_VERSION = 14;
    public final Context context;

    public static Map<Fertilizer, Double> composition = new HashMap<>();


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //initComposition();

        // ............ Crop Class ...........

        String queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL)",
                CropClassDBHelper.TABLE_NAME,
                CropClassDBHelper.COLUMN_SEASON,
                CropClassDBHelper.COLUMN_VARIETY,
                CropClassDBHelper.COLUMN_CLASS);

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
        String columns = CropClassDBHelper.COLUMN_SEASON + ", " + CropClassDBHelper.COLUMN_VARIETY + ", "
                + CropClassDBHelper.COLUMN_CLASS;
        String str1 = "INSERT INTO " + CropClassDBHelper.TABLE_NAME + " (" + columns + ") values(";
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

        // ..............

        // .............. Texture Class .............

        queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL)",
                TextureClassDBHelper.TABLE_NAME,
                TextureClassDBHelper.COLUMN_TEXTURE,
                TextureClassDBHelper.COLUMN_CROP_TYPE,
                TextureClassDBHelper.COLUMN_CLASS);

        db.execSQL(queryCreateDB);

        mCSVFile = "texture_class.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = TextureClassDBHelper.COLUMN_TEXTURE + ", " + TextureClassDBHelper.COLUMN_CROP_TYPE
                + ", " + TextureClassDBHelper.COLUMN_CLASS;
        str1 = "INSERT INTO " + TextureClassDBHelper.TABLE_NAME + " (" + columns + ") values(";
        str2 = ");";

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

        // ..............

        // .............. Crop Group ..............

        queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL)",
                CropGroupDBHelper.TABLE_NAME,
                CropGroupDBHelper.COLUMN_GROUP,
                CropGroupDBHelper.COLUMN_SEASON,
                CropGroupDBHelper.COLUMN_CROP_TYPE);

        db.execSQL(queryCreateDB);

        mCSVFile = "crop_group.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = CropGroupDBHelper.COLUMN_GROUP + ", "
                + CropGroupDBHelper.COLUMN_SEASON + ", " + CropGroupDBHelper.COLUMN_CROP_TYPE;
        str1 = "INSERT INTO " + CropGroupDBHelper.TABLE_NAME + " (" + columns + ") values(";
        str2 = ");";

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

        // .............

        // ............. STVI .............

        queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL, " +
                        "%s float NOT NULL, %s float NOT NULL, %s float NOT NULL)",
                STVIDBHelper.TABLE_NAME,
                STVIDBHelper.COLUMN_TEXTURE_CLASS,
                STVIDBHelper.COLUMN_NUTRIENT,
                STVIDBHelper.COLUMN_INTERPRETATION,
                STVIDBHelper.COLUMN_LOWER_LIMIT,
                STVIDBHelper.COLUMN_UPPER_LIMIT,
                STVIDBHelper.COLUMN_INTERVAL);

        db.execSQL(queryCreateDB);

        mCSVFile = "soil_test_values_interpretation.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = STVIDBHelper.COLUMN_TEXTURE_CLASS + ", "
                + STVIDBHelper.COLUMN_NUTRIENT + ", "
                + STVIDBHelper.COLUMN_INTERPRETATION + ", "
                + STVIDBHelper.COLUMN_LOWER_LIMIT + ", "
                + STVIDBHelper.COLUMN_UPPER_LIMIT + ", "
                + STVIDBHelper.COLUMN_INTERVAL;
        str1 = "INSERT INTO " + STVIDBHelper.TABLE_NAME + " (" + columns + ") values(";
        str2 = ");";

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


        // ............ Nutrient Recommendation

        queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "( %s text NOT NULL, %s text NOT NULL, %s text NOT NULL, %s text NOT NULL, " +
                        "%s float NOT NULL, %s float NOT NULL, %s float NOT NULL)",
                NutrientRecommendationDBHelper.TABLE_NAME,
                NutrientRecommendationDBHelper.COLUMN_SEASON,
                NutrientRecommendationDBHelper.COLUMN_CLASS,
                NutrientRecommendationDBHelper.COLUMN_NUTRIENT,
                NutrientRecommendationDBHelper.COLUMN_INTERPRETATION,
                NutrientRecommendationDBHelper.COLUMN_LOWER_LIMIT,
                NutrientRecommendationDBHelper.COLUMN_UPPER_LIMIT,
                NutrientRecommendationDBHelper.COLUMN_INTERVAL);

        db.execSQL(queryCreateDB);

        mCSVFile = "soil_analysis_interpretation.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = NutrientRecommendationDBHelper.COLUMN_SEASON + ", "
                + NutrientRecommendationDBHelper.COLUMN_CLASS + ", "
                + NutrientRecommendationDBHelper.COLUMN_NUTRIENT + ", "
                + NutrientRecommendationDBHelper.COLUMN_INTERPRETATION + ", "
                + NutrientRecommendationDBHelper.COLUMN_LOWER_LIMIT + ", "
                + NutrientRecommendationDBHelper.COLUMN_UPPER_LIMIT + ", "
                + NutrientRecommendationDBHelper.COLUMN_INTERVAL;
        str1 = "INSERT INTO " + NutrientRecommendationDBHelper.TABLE_NAME + " (" + columns + ") values(";
        str2 = ");";

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
                sb.append("'" + str[5] + "', ");
                sb.append("'" + str[6] + "' ");
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
        db.execSQL("DROP TABLE IF EXISTS " + CropClassDBHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CropGroupDBHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STVIDBHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NutrientRecommendationDBHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TextureClassDBHelper.TABLE_NAME);
        onCreate(db);
    }

    public int numberOfRows() {
        return 0;
    }

    public Cursor getData() {
        return null;
    }

    private void initComposition () {
        String mCSVFile = "soil_analysis_interpretation.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";

        try {
            while ((line = buffer.readLine()) != null){
                String words[] = line.split(",");
                composition.put(new Fertilizer(words[1], words[0]), Double.parseDouble(words[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Fertilizer {
    private String nutrient;
    private String fertilizer;

    public Fertilizer(String nutrient, String fertilizer) {
        this.nutrient = nutrient;
        this.fertilizer = fertilizer;
    }

    public String getNutrient() {
        return nutrient;
    }

    public String getFertilizer() {
        return fertilizer;
    }
}
