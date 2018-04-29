package com.example.abdullahaljubaer.frc_offline.Results;

/**
 * Created by ABDULLAH AL JUBAER on 21-04-18.
 */

public class TestBasedResultProducer extends StatusBasedResultProducer {

    private double st;
    private double cs;
    private double ci;
    private double ls;

    public TestBasedResultProducer( String symbol,
            String nutrient, String fertilizer,
            double st, String interpretation,
            double uf, double cs,
            double ci, double ls,
            double composition, double fertilizerQuantity) {
        super(symbol, nutrient, fertilizer, interpretation, uf, composition, fertilizerQuantity);

        this.st = st;
        this.cs = cs;
        this.ci = ci;
        this.ls = ls;
    }

    public double getSt() {
        return st;
    }

    public double getCs() {
        return cs;
    }

    public double getCi() {
        return ci;
    }

    public double getLs() {
        return ls;
    }

    @Override
    public String getNutrient() {
        return super.getNutrient();
    }

    @Override
    public String getInterpretation() {
        return super.getInterpretation();
    }

    @Override
    public String getFertilizer() {
        return super.getFertilizer();
    }

    @Override
    public double getFertilizerQuantity() {
        return super.getFertilizerQuantity();
    }

    @Override
    public double getNutrientQuantity() {
        return super.getNutrientQuantity();
    }

    @Override
    public String getNutrientCalc() {
        return super.getNutrientCalc();
    }

    @Override
    public String getComposition() {
        return super.getComposition();
    }

    @Override
    public void setFertilizer(String fertilizer) {
        super.setFertilizer(fertilizer);
    }

    @Override
    public void setComposition(double composition) {
        super.setComposition(composition);
    }

    @Override
    public void setFertilizerQuantity(double fertilizerQuantity) {
        super.setFertilizerQuantity(fertilizerQuantity);
    }

    public String getTopLabel (){

        return String.format("St = %.3f\nInterpretation = %s\nUf = %.3f\nCs = %.3f\nCi = %.3f\nLs = %.3f",
                st, getInterpretation(), getUf(), cs, ci, ls);

    }

    public String getFrRight () {
        return "X(" + st + "-" + ls + ")";
    }

}
