package com.example.abdullahaljubaer.frc_offline.FunctionalClasses;

public class OutputModified extends Output{
	public double calculateLeechingN(String varietyName, double nutrientAddition, double rainFall, int aezId, int season){
		if(season == 1) {
			return 0.8 + (0.0021 + 0.0007 * getSoilFertilityValue(db.getSoilFertility(aezId))) * (rainFall + 1000)
					+ 0.3 * nutrientAddition - 0.1 * (harvestedProduct + residuesRemoved);
		}
		return 0.8 + (0.0021 + 0.0007 * getSoilFertilityValue(db.getSoilFertility(aezId))) * rainFall
				+ 0.3 * nutrientAddition - 0.1 * (harvestedProduct + residuesRemoved);
	}
	public double calculateLeechingP(String varietyName, double nutrientAddition, double rainFall, int aezId, int season){
		if(season == 1) {
			return (0.2 + (0.0011 + 0.0002 * getSoilFertilityValue(db.getSoilFertility(aezId))) * (rainFall + 1000)
					+ 0.5 * nutrientAddition - 0.1 * (harvestedProduct + residuesRemoved) * 1.2) * 0.83;
		}
		return (0.2 + (0.0011 + 0.0002 * getSoilFertilityValue(db.getSoilFertility(aezId))) * rainFall
				+ 0.5 * nutrientAddition - 0.1 * (harvestedProduct + residuesRemoved) * 1.2) * 0.83;
	}
	public void calculateLeeching(String varietyName, double nutrientAddition, double rainFall, int aezId, int season) {
		if(name.equals("N")) {
			if(season == 1) {
				if(varietyName.equals("Boro rice")) {
					leeching = calculateLeechingN(varietyName, nutrientAddition, rainFall, aezId, season);
				}
			}
			else leeching = calculateLeechingN(varietyName, nutrientAddition, rainFall, aezId, season);
		}
		else if(name.equals("K")) {
			if(season == 1) {
				if(varietyName.equals("Boro rice")) {
					leeching = calculateLeechingP(varietyName, nutrientAddition, rainFall, aezId, season);
				}
			}
			else leeching = calculateLeechingP(varietyName, nutrientAddition, rainFall, aezId, season);
		}
		leeching = Math.max(0.0, leeching);
		//System.out.println("Leeching: " + leeching);
	}
	
	public void calculateDenitrification(String varietyName, double nutrientAddition, int aezId, int season) {
		if(name.equals("N")) {}
		else return;
		double base = db.getDenitrificationBase(varietyName);
		if(varietyName.equals("Fallow")) {
			if(season == 3)base = 4;
			else base = 1.8;
		}
		//System.out.println(db.getDenitrificationBase(varietyName) + " uptake " + db.getNutrientUptake(varietyName, name));
		denitrification = base + (2.5 * getSoilFertilityValue(db.getSoilFertility(aezId)))/3.0 + 
				0.3 * nutrientAddition - 0.1 * (harvestedProduct + residuesRemoved);
		denitrification = Math.max(0.0, denitrification);
		//System.out.println("Nutrient addition: " + nutrientAddition);
		//System.out.println("Denitrification: " + denitrification + " soil fertility: " + db.getSoilFertility(aezId) + " " +  getSoilFertilityValue(db.getSoilFertility(aezId)) );
	}

	public void calculateVolitilation(int aezId, int season, String varietyName) {
		if(name.equals("N")) {}
		else return;
		if(aezId >= 11 && aezId <= 13) {
			if(varietyName.equals("Fallow"))volitilation = 10.0;
			else volitilation = 5.0;
		}
	}
	public void calculateErosion(int aezId, int season) {
		if(season != 3)return;
		if(name.equals("N")) {
			if(aezId == 22 || aezId == 29) {
				erosion = 2.0;
			}
		}
		else if(name.equals("P")) {
			if(aezId == 22 || aezId == 29) {
				erosion = 1.0;
			}
		}
		else {
			if(aezId == 22) {
				erosion = 10;
			}
			else if(aezId == 29) {
				erosion = 15;
			}
		}
		//System.out.println("Erosion: " + erosion);
	}
}
