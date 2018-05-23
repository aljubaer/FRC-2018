package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database extends DBHelper {

	private Context context;
    SQLiteDatabase db;

    public Database(Context context) {
		super(context);
		this.context = context;
        db = this.getReadableDatabase();
	}

	public int getBnfType(String varietyName) {

		String sql = "SELECT bnf_type from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";

		Cursor res = db.rawQuery(sql, null);

		int val = 0;

		if (res.moveToFirst()){
		    val = res.getInt(res.getColumnIndex("bnf_type"));
        }

		return val;
	}

	public double getBnf(String varietyName) {
		String sql = "SELECT bnf from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";

        double val = 3;

        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("bnf"));
        }

        return val;

	}

	public double getNutrientUptake(String varietyName, String nutrientId) {
		String cropName = getCropName(varietyName);
		String sql = "SELECT value from nutrient_uptake" + " where " + " crop_name " + " = " + "'" + cropName + "'"
				+ " and " + " nutrient_id " + " = " + "'" + nutrientId + "'";
		double val = 0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("value"));
        }

        return val;
	}

	public double getSedimentation(String landType, String nutrientId) {
		String sql = "SELECT value from sedimentation" + " where " + " land_type " + " = " + "'" + landType + "'"
				+ " and " + " nutrient_id " + " = " + "'" + nutrientId + "'";

		double val = 0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("value"));
        }

        return val;
	}

	public double getNutrientConcentration(String varietyName, String nutrientId, String partNo) {
		String sql = "SELECT part1, part2 from nutrient_concentration" + " where " + " variety_name " + " = " + "'" + varietyName + "'"
				+ " and " + " nutrient_id " + " = " + "'" + nutrientId + "'";
		double val = 0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex(partNo));
        }

        return val;
	}
	
	public String getCropName(String varietyName) {
		String sql = "SELECT crop_name from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";
		String cropName = "";
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            cropName = res.getString(res.getColumnIndex("crop_name"));
        }

		return cropName;
	}
	
	public double get_p_r_ratio(String varietyName) {
		String cropName = getCropName(varietyName);
		String sql = "SELECT p_r_ratio from crop" + " where " + " crop_name " + " = " + "'" + cropName + "'";
		//System.out.println(sql);
		double val = 0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("p_r_ratio"));
        }

        return val;
	}
	public String getSoilFertility(int aezId) {
		
		String sql = "SELECT fertility_class from aez" + " where " + " aez_id " + " = " + "'" + aezId + "'";
		//System.out.println(sql);
		String fertilityClass = "";
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            fertilityClass = res.getString(res.getColumnIndex("fertility_class"));
        }
        return fertilityClass;
	}
	
	public double getDenitrificationBase(String varietyName) {
		String sql = "SELECT denitrification_base from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";
		//System.out.println(sql);
		double val = 0.0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("denitrification_base"));
        }

        return val;
	}
	
	public double getErosion(int aezId, String nutrientId) {
		String sql = "SELECT value from erosion" + " where " + " aez_id " + " = " + "'" + aezId + "'" + " and "
				+ " nutrient_id " + " = " + "'" + nutrientId + "'";
		//System.out.println(sql);
        double val = 0.0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("value"));
        }

        return val;
	}
	public double getAvgRainfall(int aezId) {
		String sql = "SELECT avg_rainfall from aez" + " where " + " aez_id " + " = " + "'" + aezId + "'";
		//System.out.println(sql);
        double val = 0.0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("avg_rainfall"));
        }

        return val;
	}
	
	public double getManure(String manureName, String nutrient) {
		String sql = "SELECT value from fertilizer" + " where " + " fertilizer_name " + " = " + "'" + manureName + "'" + " and "
					+ " nutrient_id " + " = " + "'" + nutrient + "'";
		//System.out.println(sql);
        double val = 0.0;
        Cursor res = db.rawQuery(sql, null);

        if (res.moveToFirst()){
            val = res.getDouble(res.getColumnIndex("value"));
        }

        return val;
	}
}
