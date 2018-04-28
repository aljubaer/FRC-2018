package com.example.abdullahaljubaer.frc_offline.Results;

/**
 * Created by ABDULLAH AL JUBAER on 21-04-18.
 */

public class TestBasedResultProducer extends StatusBasedResultProducer {

    private double st;
    private double cs;
    private double ci;
    private double ls;

    public TestBasedResultProducer(
            String nutrient, String fertilizer,
            double st, String interpretation,
            double uf, double cs,
            double ci, double ls,
            double composition, double fertilizerQuantity) {
        super(nutrient, fertilizer, interpretation, uf, composition, fertilizerQuantity);

        this.st = st;
        this.cs = cs;
        this.ci = ci;
        this.ls = ls;
    }

    public String getTopLabel (){

        return String.format("St = %.3f\nInterpretation = %s\nUf = %.3f\nCs = %.3f\nCi = %.3f\nLs = %.3f",
                st, getInterpretation(), getUf(), cs, ci, ls);

    }

    public String getFrRight () {
        return "X(" + st + "-" + ls + ")";
    }

}
