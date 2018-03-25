package com.example.abdullahaljubaer.frc_offline.GUI;

/**
 * Created by ABDULLAH AL JUBAER on 25-03-18.
 */

public class Interpretation {

    private String status;
    private double upperLimit;
    private double lowerLimit;
    private double interval;


    public Interpretation(String status, double lowerLimit,double upperLimit, double interval) {
        this.status = status;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.interval = interval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }
}
