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

    public double calculateRequiredNutrient ( Crop crop, Texture texture, double soilTestValue ) {

        Interpretation testInterpretation = texture.getInterpretation(symbol, soilTestValue);

        Interpretation recommendationInterpretation = crop.getInterpretation(symbol, testInterpretation.getStatus());

        return calculateFr(recommendationInterpretation.getUpperLimit(), recommendationInterpretation.getInterval(), testInterpretation.getInterval(), soilTestValue , testInterpretation.getLowerLimit());
    }

    private double calculateFr (double Uf, double Ci, double Cs, double St, double Ls) {



        return Uf - ((Ci / Cs) * (St - Ls));

    }

}
