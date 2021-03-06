package com.example.abdullahaljubaer.frc_offline.FunctionalClasses;

import org.jetbrains.annotations.Contract;

/**
 * Created by ABDULLAH AL JUBAER on 25-03-18.
 */

public class Nutrient {

    private String nutrientName;
    private String symbol;
    private String status;

    private Interpretation testInterpretation;
    private Interpretation recommendationInterpretation;

    public Nutrient (String nutrientName, String symbol) {
        this.nutrientName = nutrientName;
        this.symbol = symbol;
    }

    public double calculateRequiredNutrient (Crop crop, Texture texture, double soilTestValue ) {

        testInterpretation = texture.getInterpretation(symbol, soilTestValue);

        this.status = testInterpretation.getStatus();

        recommendationInterpretation = crop.getInterpretation(symbol, testInterpretation.getStatus());

        return calculateFr(recommendationInterpretation.getUpperLimit(),
                recommendationInterpretation.getInterval(),
                testInterpretation.getInterval(),
                soilTestValue,
                testInterpretation.getLowerLimit());
    }

    public double calculateRequiredNutrient ( Crop crop, String status ) {
        this.status = status;
        recommendationInterpretation = crop.getInterpretation(symbol, status);
        return calculateFr(recommendationInterpretation.getUpperLimit(), recommendationInterpretation.getInterval());
    }

    @Contract(pure = true)
    private double calculateFr (double Uf, double Ci, double Cs, double St, double Ls) {
        if (Cs <= 0.00000001) return -1.0;
        return Uf - ((Ci / Cs) * (St - Ls));
    }

    @Contract(pure = true)
    private double calculateFr (double Uf, double Ci) {
        if (Ci < 0.000001) return 0.0;
        return Uf - (Ci / 2.0);
    }

    public String getName() {
        return nutrientName;
    }

    public String getStatus () {
        return this.status;
    }

    public Interpretation getTestInterpretation() {
        return testInterpretation;
    }

    public Interpretation getRecommendationInterpretation() {
        return recommendationInterpretation;
    }

    public String getSymbol() {
        return symbol;
    }
}
