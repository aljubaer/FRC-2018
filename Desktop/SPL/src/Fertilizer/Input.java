package Fertilizer;

public class Input extends Nutrient{
	//public String cropName, nutrientName, manureName;
	//public amount and name comes from nutrient
	public double bnf, manureAmount, sedimentation, irrigation, total, deposition;
	public Input() {
		
	}
	public Input(String name, double amount, double manureAmount) {
		this.name = name;
		this.amount = amount;
		this.manureAmount = manureAmount;
		
	}
	
	 

	public double calculateBnfType1(String varietyName) {
		return db.getBnf(varietyName);
	}
	public double calculateBnfType2(String varietyName) { //thik kora lagbe
		return 0.70 * db.getNutrientUptake(varietyName, name);
	}
	public double calculateBnfType3() {
		return 0.70 * amount;
	}
	public void calculateBnf(String varietyName, double yield, int season, double res) {
		int type = db.getBnfType(varietyName);
		if(type == 1) {
			bnf = calculateBnfType1(varietyName);
		}
		else if(type == 2) {
			bnf = calculateBnfType2(varietyName);
		}
		else {
			bnf = calculateBnfType3();
		}
		bnf = Math.max(3.0, bnf);
		//System.out.println("Bnf type and value: " + type + " " + bnf);
	}

	public void calculateSedimentation(String landType, int season) {
		if(season != 3)return;
		sedimentation = db.getSedimentation(landType, name);
		//System.out.println("Sedimentation " + sedimentation);
		
	}

	public void calculateIrrigation(String varietyName, int season) {
		if(varietyName.equals("Boro rice")) {
			if(season == 1) {
				if(name.equals("N"))irrigation = 5.0;
				else if(name.equals("P"))irrigation = 10.0;
				else irrigation = 24.0;
			}
		}
	}
	public void addAll() {
		total = bnf + irrigation + sedimentation + amount + manureAmount + deposition;
	}
	
	public void calculateTotal(String varietyName, String landType, int season, double yield, double res) {
		if(name.equals("N"))calculateBnf(varietyName, yield, season, res);
		calculateIrrigation(varietyName, season);
		calculateSedimentation(landType, season);
		addAll();
	}
	/*public static void main(String[] args) {
		Input i = new Input("N", 100, 100);
		i.calculateTotal("T.Aus rice", "LL", 2);
	}*/
	public String toString() {
		return "name: " + name + " amount: " + amount + " bnf: " + bnf + " irrigation: " + irrigation + " sedi: " + sedimentation + " manure: " + 
				manureAmount + " deposition: " + deposition + " TOTAL: " + total;
	}
}
