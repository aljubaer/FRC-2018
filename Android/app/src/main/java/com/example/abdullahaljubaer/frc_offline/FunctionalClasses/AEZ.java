package com.example.abdullahaljubaer.frc_offline.FunctionalClasses;

import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.Database;
import com.example.abdullahaljubaer.frc_offline.GUI.MainActivity;

public class AEZ {
	public int aezNo;
	public String [] season;
	public String landType;
	public double avgRainfall;
	String manureName [];
	public BalanceCrop[] balanceCrops;
	BalanceCrop input, output;
	double [] residues;
	double [] manureRate;
	Database db = MainActivity.database;
	private final double rainConstant [] = {0.14, 0.023, 0.092};
	private final double rainConstantForSpecificNutrient [] = {0.078, 0.369, 0.553};
	double [] yield;
	double [] balanceSheet;
	double [] partialBalanceSheet;
	public double getTotalPartialBalanceForSpecificNutrient(int i) {
		double val = 0.0;
		for(int j = 0; j < 3; j++) {
			val += balanceCrops[j].getPartialInputOutputDifferenceForSpecificNutrient(i);
		}
		return val;
	}
	public double getTotalBalanceForSpecificNutrient(int i) {
		double val = 0.0;
		for(int j = 0; j < 3; j++) {
			val += balanceCrops[j].getInputOutputDifferenceForSpecificNutrient(i);
		}
		return val;
	}
	
	public AEZ(int aezNo, String landType, String [] cropName, double [] crop_yield, double [][] nutrientAmount, String [] manure, double [] rateOfManure, double [] cropResidues) {
		this.aezNo = aezNo;
		this.landType = landType;
		input = new BalanceCrop();
		output = new BalanceCrop();
		this.avgRainfall = db.getAvgRainfall(aezNo);
		//System.out.println("AEZ: " + this.aezNo + " -> " + this.avgRainfall);
		for(int i = 0; i < 3; i++) {
			input.input[i].deposition = (double) Math.sqrt(avgRainfall) * rainConstant[i];
			//System.out.println("I " + i + " " + input.input[i].deposition);
		}
		
		manureName = new String [3];
		manureRate = new double[3];
		residues = new double[3];
		yield = new double[3];
		yield = crop_yield;
		manureRate = rateOfManure;
		residues = cropResidues;
		manureName = manure;
		
		balanceCrops = new BalanceCrop[3];
		initializeCrop(aezNo, landType, cropName, nutrientAmount);
		for(int i = 0; i < 3; i++) {
			input.input[i].deposition = 0.0;
		//	System.out.println("I " + i + " " + input.input[i].deposition);
		}
		calculateTotal();
		
		/*balanceSheet = new double[3];
		for(int i = 0; i < 3; i++) {
			balanceSheet[i] = input.input[i].total - output.output[i].total;
		}
		partialBalanceSheet = new double[3];
		for(int i = 0; i < 3; i++) {
			partialBalanceSheet[i] = input.input[i].amount + input.input[i].manureAmount - output.output[i].harvestedProduct - output.output[i].residuesRemoved;
		}*/
		
	}

	private void calculateTotal() {
		for(int i = 0; i < 3; i++) {
			
			for(int j = 0; j < 3; j++) {
				input.input[j].name = balanceCrops[i].input[j].name;
				input.input[j].amount += balanceCrops[i].input[j].amount;
				input.input[j].bnf += balanceCrops[i].input[j].bnf;
				input.input[j].deposition += balanceCrops[i].input[j].deposition;
				input.input[j].irrigation += balanceCrops[i].input[j].irrigation;
				input.input[j].sedimentation += balanceCrops[i].input[j].sedimentation;
				input.input[j].manureAmount += balanceCrops[i].input[j].manureAmount;
				input.input[j].total += balanceCrops[i].input[j].total;
				
				
				output.output[j].name = balanceCrops[i].output[j].name;
				output.output[j].harvestedProduct += balanceCrops[i].output[j].harvestedProduct;
				output.output[j].residuesRemoved += balanceCrops[i].output[j].residuesRemoved;
				output.output[j].denitrification += balanceCrops[i].output[j].denitrification;
				output.output[j].erosion += balanceCrops[i].output[j].erosion;
				output.output[j].leeching += balanceCrops[i].output[j].leeching;
				output.output[j].volitilation += balanceCrops[i].output[j].volitilation;
				output.output[j].total += balanceCrops[i].output[j].total;
			}
		}
	}

	private void initializeCrop(int aezNo, String landType, String[] cropName, double[][] nutrientAmount) {
		for(int i = 0; i < 3; i++) {
			balanceCrops[i] = new BalanceCrop();
			balanceCrops[i].name = cropName[i];
			for(int j = 0; j < 3; j++) {
				balanceCrops[i].input[j].amount = nutrientAmount[i][j];
				balanceCrops[i].input[j].deposition = input.input[j].deposition * rainConstantForSpecificNutrient[i];
				balanceCrops[i].input[j].manureAmount = db.getManure(manureName[i], balanceCrops[i].input[j].name) * manureRate[i];
				balanceCrops[i].output[j].residues = residues[i];
				balanceCrops[i].output[j].yield = yield[i];
				balanceCrops[i].input[j].calculateTotal(balanceCrops[i].name, landType, i + 1, yield[i], residues[i]);
				balanceCrops[i].output[j].calculateTotal(balanceCrops[i].name, balanceCrops[i].input[j].amount + balanceCrops[i].input[j].manureAmount, avgRainfall * rainConstantForSpecificNutrient[i], aezNo, i + 1);
			}
		}
	//	printAmount();
	}
	private void printAmount() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(balanceCrops[i].input[j].amount);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	public AEZ(int aez_id) {
		this.aezNo = aez_id;
		this.avgRainfall = db.getAvgRainfall(aezNo);
		System.out.println(this.avgRainfall);
	}
	/*
	public static void main(String[] args) {
		
		String [] cropName = {"Chickpea_Any", "T.Aus rice", "T.Aman rice"};
		double [] cropYield = {1000, 3000, 4820};
		String [] manure = {"Cowdung", "" ,""};
		
		double [] rate = {100.0, 0.0, 100.0};
		double [] res = {100.0, 100.0, 100.0};
		double [] [] nutrientAmount = { {90, 15, 40} , {70, 15, 35} , {90, 35, 66} };
		AEZ aez = new AEZ(25, "MHL", cropName, cropYield, nutrientAmount, manure, rate, res);
		
		System.out.println("Aez id: " + aez.aezNo);
		for(int i = 0; i < 3; i++) {
			System.out.println("BalanceCrop Name: " + aez.balanceCrops[i].name);
			for(int j = 0; j < 3; j++) {
				System.out.println(aez.balanceCrops[i].input[j].toString());
			}
			
		}
		for(int i = 0; i < 3; i++) {
			System.out.println("BalanceCrop Name: " + aez.balanceCrops[i].name);
			for(int j = 0; j < 3; j++) {
				System.out.println(aez.balanceCrops[i].output[j].toString());
			}
			
		}
		for(int i = 0; i < 3; i++) {
			System.out.println("BalanceNutrient Input Name: " + aez.input.input[i].name);
			System.out.println(aez.input.input[i].toString());
			
		}
		for(int i = 0; i < 3; i++) {
			System.out.println("BalanceNutrient Output Name: " + aez.output.output[i].name);
			System.out.println(aez.output.output[i].toString());
			
		}
		
		for(int i = 0; i < 3; i++) {
			System.out.println("Balance Sheet BalanceNutrient Name: " + aez.output.output[i].name);
			System.out.println(aez.balanceSheet[i]);
			
		}
		
		for(int i = 0; i < 3; i++) {
			System.out.println("Partial Balance Sheet BalanceNutrient Output Name: " + aez.output.output[i].name);
			System.out.println(aez.partialBalanceSheet[i]);
			
		}
		
		
	}*/
}
