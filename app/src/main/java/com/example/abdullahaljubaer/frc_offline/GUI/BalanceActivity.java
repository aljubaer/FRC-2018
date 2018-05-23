package com.example.abdullahaljubaer.frc_offline.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.*;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.*;
import com.example.abdullahaljubaer.frc_offline.R;


import java.util.ArrayList;
import java.util.Arrays;

public class BalanceActivity extends AppCompatActivity {

    private Spinner spnAez, spnLand;
    private Spinner[] spnCrop;
    private String[] crop = new String[3];
    private EditText[][] edtFertilizer;
    private double[][] fertilizer = new double[3][3];
    private Spinner[] spnManure;
    private String[] manure = new String[3];
    private EditText[] edtYield;
    private double[] yield = new double[3];
    private EditText[] edtRate;
    private double[] rate = new double[3];
    private EditText[] edtResidues;
    private double[] residues = new double[3];
    public static Database database = null;
    private String AEZ = "1", landType = "HL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        //Arrays.fill(crop, "Fallow");


        database = MainActivity.database;

        //System.out.println(database.getBnf("Boro rice"));

        spnAez = findViewById(R.id.spn_aez);
        spnLand = findViewById(R.id.spn_land);

        spnCrop = new Spinner[] {
                findViewById(R.id.spn_c1),
                findViewById(R.id.spn_c2),
                findViewById(R.id.spn_c3)
        };

        edtYield = new EditText[] {
                findViewById(R.id.edt_y1),
                findViewById(R.id.edt_y2),
                findViewById(R.id.edt_y3)
        };

        spnManure = new Spinner[] {
                findViewById(R.id.spn_m1),
                findViewById(R.id.spn_m2),
                findViewById(R.id.spn_m3)
        };

        edtFertilizer = new EditText[][] {
                {findViewById(R.id.edt_n1), findViewById(R.id.edt_n2), findViewById(R.id.edt_n3)},
                {findViewById(R.id.edt_p1), findViewById(R.id.edt_p2), findViewById(R.id.edt_p3)},
                {findViewById(R.id.edt_k1), findViewById(R.id.edt_k2), findViewById(R.id.edt_k3)}
        };

        edtRate = new EditText[] {
                findViewById(R.id.edt_r1),
                findViewById(R.id.edt_r2),
                findViewById(R.id.edt_r3)
        };

        edtResidues = new EditText[] {
                findViewById(R.id.edt_rr1),
                findViewById(R.id.edt_rr2),
                findViewById(R.id.edt_rr3)
        };

        for (int i = 0; i < 3; i++){
            spnCrop[i].setOnItemSelectedListener(new CustomOnItemSelectedListener());
            spnManure[i].setOnItemSelectedListener(new CustomOnItemSelectedListener());
        }

        String[][] iCrop = new String[][]{
                {"Fallow", "Boro rice", "Wheat", "Mustard", "Chickpea", "Potato", "Maize", "Cabbage"},
                {"Fallow", "Boro rice", "T.Aus rice", "Jute", "GM", "Mungbean"},
                {"Fallow", "T.Aman rice"}
        };

        String[][] iManure = new String[][] {
                {"Cowdung", "FYM", "Poultry manure", "BalanceCrop residues (Rice)"},
                {"Cowdung", "FYM", "Poultry manure", "BalanceCrop residues (Rice)", "BalanceCrop residues (Wheat)"},
                {"Cowdung", "FYM", "Poultry manure", "BalanceCrop residues (Rice)", "BalanceCrop residues (Wheat)", "GM (Dhaincha)", "Brown manure (Mungbean)"}
        };

        for (int i = 0; i < 3; i++) {
            ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(
                    BalanceActivity.this, android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList(iCrop[i])));
            spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCrop[i].setAdapter(spnAdapter);

            ArrayAdapter<String> spnAdapter1 = new ArrayAdapter<String>(
                    BalanceActivity.this, android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList(iManure[i])));
            spnAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnManure[i].setAdapter(spnAdapter1);

        }

        ArrayList<String> aez = new ArrayList<>();

        for (int i = 1; i <= 30; i++){
            aez.add(String.valueOf(i));
        }

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(
                BalanceActivity.this, android.R.layout.simple_spinner_item, aez);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAez.setAdapter(spnAdapter);

        ArrayList<String> land = new ArrayList<>();
        land.add("HL");
        land.add("MHL");
        land.add("MLL");
        land.add("LL");
        land.add("VLL");


        ArrayAdapter<String> spnAdapter1 = new ArrayAdapter<String>(
                BalanceActivity.this, android.R.layout.simple_spinner_item, land);
        spnAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLand.setAdapter(spnAdapter1);

    }

    public void generateGraph (View view) {

        for (int i = 0; i < 3; i++) {
            try {
                yield[i] = Double.valueOf(edtYield[i].getText().toString());
            } catch (NumberFormatException e) {
                yield[i] = 0.0;
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                rate[i] = Double.valueOf(edtRate[i].getText().toString());
            } catch (NumberFormatException e){
                rate[i] = 0.0;
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                residues[i] = Double.valueOf(edtResidues[i].getText().toString());
            }catch (NumberFormatException e) {
                residues[i] = 0.0;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    fertilizer[j][i] = Double.valueOf(edtFertilizer[i][j].getText().toString());
                } catch (NumberFormatException e) {
                    fertilizer[i][j] = 0.0;
                }
            }
        }



        AEZ aez = new AEZ(Integer.parseInt(AEZ), landType, crop, yield, fertilizer, manure, rate, residues);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                System.out.println(aez.balanceCrops[i].input[j].toString());
            }
        }

        double[] data = new double[3];

        data[0] = aez.getTotalBalanceForSpecificNutrient(0);
        data[1] = aez.getTotalBalanceForSpecificNutrient(1);
        data[2] = aez.getTotalBalanceForSpecificNutrient(2);

        for (int i = 0; i < 3; i++) {
            System.out.println("Data" + (i+1) + ": " + data[i]);
        }

        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            /*
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
                    */

            if (parent.getId() == R.id.spn_c1)crop[0] = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_c2)crop[1] = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_c3)crop[2] = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_m1)manure[0] = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_m2)manure[1] = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_m3)manure[2] = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_aez)AEZ = parent.getItemAtPosition(pos).toString();
            else if (parent.getId() == R.id.spn_land)landType = parent.getItemAtPosition(pos).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

}
