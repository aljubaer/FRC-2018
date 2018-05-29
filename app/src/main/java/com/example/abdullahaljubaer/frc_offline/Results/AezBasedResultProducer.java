package com.example.abdullahaljubaer.frc_offline.Results;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 25-05-18.
 */

public class AezBasedResultProducer implements IResultProducer {

    public ArrayList<ArrayList<String>> aez;

    public AezBasedResultProducer (ArrayList<ArrayList<String>> aez, double val) {

        this.aez = (ArrayList<ArrayList<String>>) aez.clone();
        setAez(val);

    }

    public ArrayList<ArrayList<String>> getAez() {
        return aez;
    }

    public void setAez (double val) {

        for (int i = 2; i < aez.size(); i++) {
            for (int j = 0; j < aez.get(i).size(); j++) {
                String s = String.valueOf(Double.parseDouble(aez.get(i).get(j)) * val);
                aez.set(i, aez.get(i)).set(j, s);
            }
        }
    }
}
