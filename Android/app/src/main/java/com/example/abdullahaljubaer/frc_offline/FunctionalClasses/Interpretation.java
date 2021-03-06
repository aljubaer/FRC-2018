package com.example.abdullahaljubaer.frc_offline.FunctionalClasses;

/**
 * Created by ABDULLAH AL JUBAER on 25-03-18.
 */

public class Interpretation {

    private String status = "";
    private double upperLimit = 0.0;
    private double lowerLimit = 0.0;
    private double interval = 0.0;


    public Interpretation(String status, String lowerLimit, String upperLimit, String interval) {
        this.status = status;

        if (!upperLimit.equals(""))this.upperLimit = Double.valueOf(upperLimit);
        if (!lowerLimit.equals(""))this.lowerLimit = Double.valueOf(lowerLimit);
        if (!interval.equals(""))this.interval = Double.valueOf(interval);
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
