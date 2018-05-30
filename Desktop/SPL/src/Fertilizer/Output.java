package Fertilizer;

public class Output extends Nutrient{
	
	public double harvestedProduct, leeching, denitrification, volitilation, erosion, total, yield, residues, residuesRemoved;
	public Output(String name, double amount, double yield, double residues) {
		this.name = name;
		this.amount = amount;
		this.yield = yield;
		this.residues = residues;
		
	}
	public Output() {
		
	}
	public void calculateHarvestedProduct(String varietyName) {
		harvestedProduct = (double) yield * db.getNutrientConcentration(varietyName, name, "part1") / 100.0;
		//System.out.println("Harvested Product: " + harvestedProduct);
	}
	
	
	public void calculateResiduesRemoved(String varietyName, int season) {
		if(season == 2) {
			residuesRemoved = (yield  *	db.getNutrientConcentration(varietyName, name, "part2") * residues)
					/ (100 * 100);
		}
		else residuesRemoved = (yield * db.get_p_r_ratio(varietyName) * 
				db.getNutrientConcentration(varietyName, name, "part2") * residues)
				/ (100 * 100);
		//System.out.println("Residues Removed: " + residuesRemoved);
	}
	
	public int getSoilFertilityValue(String soilFertilityClass) {
		if(soilFertilityClass.equals("L"))return 1;
		else if(soilFertilityClass.equals("M"))return 2;
		else return 3;
	}
	public double calculateLeechingN(String varietyName, double nutrientAddition, double rainFall, int aezId, int season){
		return 2.3 + (0.0021 + 0.0007 * getSoilFertilityValue(db.getSoilFertility(aezId))) * rainFall
				+ 0.3 * nutrientAddition - 0.1 * db.getNutrientUptake(varietyName, name);
	}
	public double calculateLeechingP(String varietyName, double nutrientAddition, double rainFall, int aezId, int season){
		return 0.6 + (0.0011 + 0.0002 * getSoilFertilityValue(db.getSoilFertility(aezId))) * rainFall
				+ 0.5 * nutrientAddition - 0.1 * db.getNutrientUptake(varietyName, name);
	}
	public void calculateLeeching(String varietyName, double nutrientAddition, double rainFall, int aezId, int season) {
		if(name.equals("N")) {
			leeching = calculateLeechingN(varietyName, nutrientAddition, rainFall, aezId, season);
		}
		else if(name.equals("K")) {
			leeching = calculateLeechingP(varietyName, nutrientAddition, rainFall, aezId, season);
		}
		leeching = Math.max(0.0, leeching);
		//System.out.println("Leeching: " + leeching);
	}
	
	public void calculateDenitrification(String varietyName, double nutrientAddition, int aezId, int season) {
		if(name.equals("N")) {}
		else return;
		double base = db.getDenitrificationBase(varietyName);
		
		//System.out.println(db.getDenitrificationBase(varietyName) + " uptake " + db.getNutrientUptake(varietyName, name));
		denitrification = base + (2.5 * getSoilFertilityValue(db.getSoilFertility(aezId)))/3.0 + 
				0.3 * nutrientAddition - 0.1 * (harvestedProduct + residuesRemoved);
		//System.out.println("Nutrient addition: " + nutrientAddition);
		//System.out.println("Denitrification: " + denitrification + " soil fertility: " + db.getSoilFertility(aezId) + " " +  getSoilFertilityValue(db.getSoilFertility(aezId)) );
	}

	public void calculateVolitilation(int aezId, int season, String varietyName) {
		
	}

	

	public void calculateErosion(int aezId, int season) {
		erosion = db.getErosion(aezId, name) / 3.0;
		//System.out.println("Erosion: " + erosion);
	}
	
	public void addAll() {
		total = harvestedProduct + residuesRemoved + leeching + denitrification + volitilation + erosion;
	//	System.out.println("Total = " + total);
	}
	
	
	public void calculateTotal(String varietyName, double nutrientAddition, double seasonalRainfall, int aezId, int season) {
		System.out.println(varietyName);
		calculateHarvestedProduct(varietyName);
		calculateResiduesRemoved(varietyName, season);
		calculateLeeching(varietyName, nutrientAddition, seasonalRainfall, aezId, season);
		calculateDenitrification(varietyName, nutrientAddition, aezId, season);
		calculateVolitilation(aezId, season, varietyName);
		calculateErosion(aezId, season);
		addAll();
	}
	public String toString() {
		return "name: " + name  + " HP: " + harvestedProduct + " resRem: " + residuesRemoved + " leech: " + leeching + " denitri: " + 
				denitrification + " volatile: " + volitilation + " erosion: " + erosion + " TOTAL: " + total;
	}
	/*public static void main(String[] args) {
		Output o = new Output("N", 90, 1000, 100);
		o.calculateTotal("Chickpea_Any", 90, 1475 * 0.078, 25);
	}*/

}
