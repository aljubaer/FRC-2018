package Fertilizer;

public class Crop {
	String name;
	Input [] input;
	Output [] output;
	public Crop() {
		input = new InputModified[3];
		output = new OutputModified[3];
		for(int i = 0; i < 3; i++) {
			input[i] = new InputModified();
			output[i] = new OutputModified();
		}
		input[0].name = "N";
		input[1].name = "P";
		input[2].name = "K";
		for(int i = 0; i < 3; i++) {
			output[i].name = input[i].name;
		}
	}
	public double getInputOutputDifferenceForSpecificNutrient(int i) {
		return input[i].total - output[i].total;
	}
	public double totalAdditionForSpecificNutrient(int i) {
		return input[i].amount + input[i].manureAmount;
	}
	public double totalSubstractionForSpecificNutrient(int i) {
		return output[i].harvestedProduct + output[i].residuesRemoved;
	}
	public double getPartialInputOutputDifferenceForSpecificNutrient(int i) {
		return totalAdditionForSpecificNutrient(i) - totalSubstractionForSpecificNutrient(i);
	}
	
	
	
}
