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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABDULLAH AL JUBAER on 23-03-18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "frg-2018-fina.db";
    public static final Integer DATABASE_VERSION = 2;
    public final Context context;

    //public static Map<Fertilizer, Double> composition = new HashMap<>();


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


        // ---------------- Nutrient Recommendation ----------------


        // ---------- VARIETY ---------------

        queryCreateDB = String.format(
                "CREATE TABLE IF NOT EXISTS `variety` (" +
                        " `variety_name` TEXT NOT NULL, " +
                        " `crop_name` TEXT, " +
                        " `bnf_type` INTEGER, " +
                        " `bnf` REAL, " +
                        " `denitrification_base` REAL, " +
                        " PRIMARY KEY(`variety_name`) " +
                        ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "variety.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO variety (variety_name,crop_name,bnf_type,bnf,denitrification_base) values(";
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
                sb.append("'" + str[4] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // ----------------- VARIETY ---------------------


        // ----------------- UPTAKE ----------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `nutrient_uptake` (\n" +
                "\t`crop_name`\tTEXT NOT NULL,\n" +
                "\t`nutrient_id`\tTEXT NOT NULL,\n" +
                "\t`value`\tREAL,\n" +
                "\tPRIMARY KEY(`nutrient_id`,`crop_name`)\n" +
                ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "nutrient_uptake.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `nutrient_uptake` (crop_name,nutrient_id,value) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // -----------------UPTAKE -----------------------


        // ----------------- CONCENTRATION ------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `nutrient_concentration` (\n" +
                "\t`variety_name`\tTEXT NOT NULL,\n" +
                "\t`nutrient_id`\tTEXT NOT NULL,\n" +
                "\t`part1`\tREAL,\n" +
                "\t`part2`\tREAL,\n" +
                "\tPRIMARY KEY(`variety_name`,`nutrient_id`)\n" +
                ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "nutrient_concentration.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `nutrient_concentration` (variety_name,nutrient_id,part1,part2) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "', ");
                sb.append("'" + str[3] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // ---------------------- CONCENTRATION ------------------------


        // --------------------- FERTILIZER ----------------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `fertilizer` (\n" +
                "\t`fertilizer_name`\tTEXT NOT NULL,\n" +
                "\t`nutrient_id`\tTEXT NOT NULL,\n" +
                "\t`value`\tREAL,\n" +
                "\tPRIMARY KEY(`fertilizer_name`,`nutrient_id`)\n" +
                ");\n");
        db.execSQL(queryCreateDB);

        mCSVFile = "fertilizer.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `fertilizer` (fertilizer_name,nutrient_id,value) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // --------------------- FERTILIZER ----------------------


        // ----------------- EROSION ------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `erosion` (\n" +
                "\t`aez_id`\tINTEGER NOT NULL,\n" +
                "\t`nutrient_id`\tTEXT NOT NULL,\n" +
                "\t`value`\tREAL,\n" +
                "\tPRIMARY KEY(`aez_id`,`nutrient_id`)\n" +
                ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "erosion.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `erosion` (aez_id,nutrient_id,value) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // ---------------------- EROSION ------------------------


        // --------------------- CROP ----------------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `crop` (\n" +
                "\t`crop_name`\tTEXT NOT NULL,\n" +
                "\t`p_r_ratio`\tREAL,\n" +
                "\t`yield`\tREAL,\n" +
                "\tPRIMARY KEY(`crop_name`)\n" +
                ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "crop.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `crop` (crop_name,p_r_ratio,yield) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // --------------------- CROP ----------------------

        // ----------------- AEZ ------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `aez` (\n" +
                "\t`aez_id`\tINTEGER NOT NULL,\n" +
                "\t`avg_rainfall`\tREAL,\n" +
                "\t`fertility_class`\tTEXT,\n" +
                "\tPRIMARY KEY(`aez_id`)\n" +
                ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "aez.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `aez` (aez_id,avg_rainfall,fertility_class) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // ---------------------- EROSION ------------------------


        // --------------------- CROP ----------------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `sedimentation` (\n" +
                "\t`land_type`\tTEXT NOT NULL,\n" +
                "\t`nutrient_id`\tTEXT NOT NULL,\n" +
                "\t`value`\tREAL,\n" +
                "\tPRIMARY KEY(`nutrient_id`,`land_type`)\n" +
                ");");
        db.execSQL(queryCreateDB);

        mCSVFile = "sedimentation.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `sedimentation` (land_type,nutrient_id,value) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        // --------------------- CROP ----------------------


        // --------------------- Crop pattern ------------------

        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `crop_patterns` " +
                "( `season` text NOT NULL, `aez_no` text NOT NULL, `crop_pattern` text NOT NULL, `crop` text NOT NULL, " +
                " `yield_goal` float NOT NULL, `N` float NOT NULL, `P` float NOT NULL, `K` float NOT NULL, `S` float NOT NULL," +
                " `Mg` float NOT NULL, `Zn` float NOT NULL, `B` float NOT NULL, `Mo` float NOT NULL, " +
                " `cowdung` float NOT NULL, `poultry_manure` float NOT NULL);");
        db.execSQL(queryCreateDB);

        mCSVFile = "crop_pattern_modified.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `crop_patterns` (season, aez_no, crop_pattern, crop, yield_goal, N, P, K, S, Mg, Zn, B, Mo, cowdung, poultry_manure) VALUES (";
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
                sb.append("'" + str[6] + "', ");
                sb.append("'" + str[7] + "', ");
                sb.append("'" + str[8] + "', ");
                sb.append("'" + str[9] + "', ");
                sb.append("'" + str[10] + "', ");
                sb.append("'" + str[11] + "', ");
                sb.append("'" + str[12] + "', ");
                sb.append("'" + str[13] + "', ");
                sb.append("'" + str[14] + "'");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();


        // --------------------- Crop pattern ------------------


        queryCreateDB = String.format("CREATE TABLE IF NOT EXISTS `district_aez` " +
                "( `district` text NOT NULL, `division` text NOT NULL, `aez_no` text NOT NULL);");
        db.execSQL(queryCreateDB);

        mCSVFile = "district_aez.csv";
        manager = context.getAssets();
        inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = new BufferedReader(new InputStreamReader(inStream));
        line = "";
        columns = "";
        str1 = "INSERT INTO `district_aez` (district, division, aez_no) VALUES (";
        str2 = ");";

        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append("'" + str[0] + "', ");
                sb.append("'" + str[1] + "', ");
                sb.append("'" + str[2] + "'");
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
        db.execSQL("DROP TABLE IF EXISTS variety");
        db.execSQL("DROP TABLE IF EXISTS nutrient_uptake");
        db.execSQL("DROP TABLE IF EXISTS nutrient_concentration");
        db.execSQL("DROP TABLE IF EXISTS fertilizer");
        db.execSQL("DROP TABLE IF EXISTS sedimentation");
        db.execSQL("DROP TABLE IF EXISTS aez");
        db.execSQL("DROP TABLE IF EXISTS crop");
        db.execSQL("DROP TABLE IF EXISTS erosion");
        db.execSQL("DROP TABLE IF EXISTS crop_patterns");
        db.execSQL("DROP TABLE IF EXISTS district_aez");
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
                //composition.put(new Fertilizer(words[1], words[0]), Double.parseDouble(words[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<String> readCursor(Cursor cursor, String column ) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex(column));
                arrayList.add(data);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }
}