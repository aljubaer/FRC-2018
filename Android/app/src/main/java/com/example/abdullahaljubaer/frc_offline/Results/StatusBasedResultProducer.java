package com.example.abdullahaljubaer.frc_offline.Results;

/**
 * Created by ABDULLAH AL JUBAER on 20-04-18.
 */

public class StatusBasedResultProducer implements IResultProducer {

    private String symbol;
    private String nutrient;
    private String fertilizer;
    private String interpretation;
    private double uf = 0.0;
    private double composition = 0.0;
    private double nutrientQuantity = 0.0;
    private double fertilizerQuantity = 0.0;

    public StatusBasedResultProducer(
            String symbol, String nutrient, String fertilizer,
            String interpretation, double uf,
            double composition, double fertilizerQuantity, double nutrientQuantity) {

        this.symbol = symbol;
        this.nutrient = nutrient;
        this.fertilizer = fertilizer;
        this.interpretation = interpretation;
        this.uf = uf;
        this.composition = composition;
        this.fertilizerQuantity = fertilizerQuantity;
        this.nutrientQuantity = nutrientQuantity;
    }

    public String getNutrient() {
        return nutrient;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public double getUf() {
        return uf;
    }

    public double getNutrientQuantity() {
        return nutrientQuantity;
    }

    public double getFertilizerQuantity() {
        return fertilizerQuantity;
    }

    public String getNutrientCalc (){
        return symbol + "(kg/ha) = " + uf;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public void setComposition(double composition) {
        this.composition = composition;
    }

    public void setFertilizerQuantity(double fertilizerQuantity) {
        this.fertilizerQuantity = fertilizerQuantity;
    }

    public String getComposition(){
        return String.format("%s composition of %s = %.2f%s", nutrient, fertilizer, composition, "%");
    }

    public String getFertilizer () {
        if (fertilizer.equals("Guti Urea"))
            return String.format("Req. %s = %.3f X (%.2f/%.2f) X 0.7\n%10s= %.3f", fertilizer, nutrientQuantity,
                    100.0, composition, " ", fertilizerQuantity);
        return String.format("Req. %s = %.3f X (%.2f/%.2f)\n%10s = %.3f", fertilizer, nutrientQuantity,
                100.0, composition, " ", fertilizerQuantity);
    }
}
