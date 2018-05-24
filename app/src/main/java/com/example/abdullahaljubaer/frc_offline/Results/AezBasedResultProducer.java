package com.example.abdullahaljubaer.frc_offline.Results;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 25-05-18.
 */

public class AezBasedResultProducer implements IResultProducer {

    public ArrayList<ArrayList<String>> aez;

    public AezBasedResultProducer (ArrayList<ArrayList<String>> aez) {

        this.aez = (ArrayList<ArrayList<String>>) aez.clone();

    }

    public ArrayList<ArrayList<String>> getAez() {
        return aez;
    }
}
