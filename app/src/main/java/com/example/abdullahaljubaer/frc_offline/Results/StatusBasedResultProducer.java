package com.example.abdullahaljubaer.frc_offline.Results;

/**
 * Created by ABDULLAH AL JUBAER on 20-04-18.
 */

public class StatusBasedResultProducer {

    private String nutrient;
    private String fertilizer;
    private String interpretation;
    private double uf = 0.0;
    private double composition = 0.0;
    private double nutrientQuantity = 0.0;
    private double fertilizerQuantity = 0.0;

    public StatusBasedResultProducer(
            String nutrient, String fertilizer,
            String interpretation, double uf,
            double composition, double fertilizerQuantity) {

        this.nutrient = nutrient;
        this.fertilizer = fertilizer;
        this.interpretation = interpretation;
        this.uf = uf;
        this.composition = composition;
        this.fertilizerQuantity = fertilizerQuantity;
    }

    public String getInterpretation() {
        return "Interpretation = " + interpretation + "\n";
    }

    public String getUf() {
        return "Uf = " + uf;
    }

    public String getNutrientCalc (){
        return "N(kg/ha) = " + uf;
    }

    public String getComposition(){

        return String.format("%s composition of %s = %.2f%", nutrient, fertilizer, composition);

    }

    public String getFertilizer () {
        return String.format("Req. %s = %.3fX(%.2f/%.2f)\n%10s= %.3f", fertilizer, nutrientQuantity,
                100.0, composition, " ", fertilizerQuantity);
    }
}
