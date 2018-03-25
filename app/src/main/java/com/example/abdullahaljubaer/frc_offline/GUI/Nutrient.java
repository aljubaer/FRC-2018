package com.example.abdullahaljubaer.frc_offline.GUI;

/**
 * Created by ABDULLAH AL JUBAER on 25-03-18.
 */

public class Nutrient {

    private String nutrientName;
    private String symbol;

    public Nutrient (String nutrientName, String symbol) {
        this.nutrientName = nutrientName;
        this.symbol = symbol;
    }

    public double calculateRequiredNutrient ( Crop crop, String status ) {

        Interpretation RecommendationInterpretation = crop.getInterpretation(symbol, status);

        return calculateFr(0, 0, 0 , 0 , 0);
    }

    private double calculateFr (double Uf, double Ci, double Cs, double St, double Ls) {
        return 0.0;
    }

}
