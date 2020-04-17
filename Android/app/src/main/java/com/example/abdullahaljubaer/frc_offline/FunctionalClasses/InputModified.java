package com.example.abdullahaljubaer.frc_offline.FunctionalClasses;

public class InputModified extends Input {
	public double calculateBnfType1(String varietyName) {
		return db.getBnf(varietyName);
	}
	public double calculateBnfType2(String varietyName, int season, double yield, double res) { //thik kora lagbe //handled for now
		Output o = new Output();
		o.name = "N";
		o.yield = yield;
		o.residues = res;
		o.calculateHarvestedProduct(varietyName);
		o.calculateResiduesRemoved(varietyName, season);
		System.out.println("OOO " + o.harvestedProduct + " " + o.residuesRemoved + " " + o.yield);
		return 0.70 * (o.harvestedProduct + o.residuesRemoved);
	}
	public double calculateBnfType3(String varietyName, double yield) {
		return 0.70 * db.getBnf(varietyName) * yield;
	}
	public void calculateBnf(String varietyName, double yield, int season, double res) {
		int type = db.getBnfType(varietyName);
		if(type == 1) {
			bnf = calculateBnfType1(varietyName);
		}
		else if(type == 2) {
			bnf = calculateBnfType2(varietyName, season, yield, res);
		}
		else if(type == 3){
			bnf = calculateBnfType3(varietyName, yield);
		}
		else bnf = Math.max(3.0, bnf);
		//System.out.println("Bnf type and value: " + type + " " + bnf);
	}
	
}
