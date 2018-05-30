package Fertilizer;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	static java.sql.Connection conn;
	
	public Database() {
		connectDatabase();
	}

	private void connectDatabase() {
		String url = "jdbc:sqlite:" + "test.db";
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				//System.out.println("The driver name is " + meta.getDriverName());
				//System.out.println("Successfully Connected.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectAll() {
		String sql = "SELECT * from crop order by yield desc";

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("crop_name") + "->" + rs.getString("yield"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public int getBnfType(String varietyName) {
		String sql = "SELECT bnf_type from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";
		//System.out.println(sql);
		int type = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				type = rs.getInt("bnf_type");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return type;
	}

	public double getBnf(String varietyName) {
		String sql = "SELECT bnf from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";
		double val = 3;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				val = rs.getDouble("bnf");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return val;
	}

	public double getNutrientUptake(String varietyName, String nutrientId) {
		String cropName = getCropName(varietyName);
		String sql = "SELECT value from nutrient_uptake" + " where " + " crop_name " + " = " + "'" + cropName + "'"
				+ " and " + " nutrient_id " + " = " + "'" + nutrientId + "'";
		double val = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				val = rs.getDouble("value");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return val;
	}

	public double getSedimentation(String landType, String nutrientId) {
		String sql = "SELECT value from sedimentation" + " where " + " land_type " + " = " + "'" + landType + "'"
				+ " and " + " nutrient_id " + " = " + "'" + nutrientId + "'";
		double val = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				val = rs.getDouble("value");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return val;
	}

	public double getNutrientConcentration(String varietyName, String nutrientId, String partNo) {
		String sql = "SELECT part1, part2 from nutrient_concentration" + " where " + " variety_name " + " = " + "'" + varietyName + "'"
				+ " and " + " nutrient_id " + " = " + "'" + nutrientId + "'";
		double val = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				val = rs.getDouble(partNo);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return val;
	}
	
	public String getCropName(String varietyName) {
		String sql = "SELECT crop_name from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";
		String cropName = "";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cropName = rs.getString("crop_name");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return cropName;
	}
	
	public double get_p_r_ratio(String varietyName) {
		String cropName = getCropName(varietyName);
		String sql = "SELECT p_r_ratio from crop" + " where " + " crop_name " + " = " + "'" + cropName + "'";
		//System.out.println(sql);
		double val = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				val = rs.getDouble("p_r_ratio");
			}
		} catch (SQLException e) {
			System.out.println("In get_p_r_ratio function");
			System.out.println(e.getMessage());
		}
		return val;
	}
	public String getSoilFertility(int aezId) {
		
		String sql = "SELECT fertility_class from aez" + " where " + " aez_id " + " = " + "'" + aezId + "'";
		//System.out.println(sql);
		String fertilityClass = "";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				fertilityClass = rs.getString("fertility_class");
			}
		} catch (SQLException e) {
			System.out.println("In getSoilFertility function");
			System.out.println(e.getMessage());
		}
		return fertilityClass;
	}
	
	public double getDenitrificationBase(String varietyName) {
		String sql = "SELECT denitrification_base from variety" + " where " + " variety_name " + " = " + "'" + varietyName + "'";
		//System.out.println(sql);
		double denitrificationBase = 0.0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				denitrificationBase = rs.getDouble("denitrification_base");
			}
		} catch (SQLException e) {
			System.out.println("In getDenitrificationBase function");
			System.out.println(e.getMessage());
		}
		return denitrificationBase;
	}
	
	public double getErosion(int aezId, String nutrientId) {
		String sql = "SELECT value from erosion" + " where " + " aez_id " + " = " + "'" + aezId + "'" + " and "
				+ " nutrient_id " + " = " + "'" + nutrientId + "'";
		//System.out.println(sql);
		double value = 0.0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = rs.getDouble("value");
			}
		} catch (SQLException e) {
			System.out.println("In getErosion function");
			System.out.println(e.getMessage());
		}
		return value;
	}
	public double getAvgRainfall(int aezId) {
		String sql = "SELECT avg_rainfall from aez" + " where " + " aez_id " + " = " + "'" + aezId + "'";
		//System.out.println(sql);
		double value = 0.0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = rs.getDouble("avg_rainfall");
			}
		} catch (SQLException e) {
			System.out.println("In getAvgRainfall function");
			System.out.println(e.getMessage());
		}
		return value;
	}
	
	public double getManure(String manureName, String nutrient) {
		String sql = "SELECT value from fertilizer" + " where " + " fertilizer_name " + " = " + "'" + manureName + "'" + " and "
					+ " nutrient_id " + " = " + "'" + nutrient + "'";
		//System.out.println(sql);
		double value = 0.0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = rs.getDouble("value");
			}
		} catch (SQLException e) {
			System.out.println("In getManure function");
			System.out.println(e.getMessage());
		}
		return value;
	}
	public ArrayList<String> getAllManure(){
		String sql = "SELECT distinct fertilizer_name from fertilizer";
		ArrayList<String> manure = new ArrayList();
	//System.out.println(sql);
	
	try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String s = rs.getString("fertilizer_name");
			manure.add(s);
		}
	} catch (SQLException e) {
		System.out.println("In getAllManure function");
		System.out.println(e.getMessage());
	}
	return manure;
	}
	public ArrayList<String> getAllVariety(){
		String sql = "SELECT variety_name from variety";
		ArrayList<String> variety = new ArrayList();
	//System.out.println(sql);
	
	try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String s = rs.getString("variety_name");
			variety.add(s);
		}
	} catch (SQLException e) {
		System.out.println("In getAllVariety function");
		System.out.println(e.getMessage());
	}
	return variety;
	}
}
