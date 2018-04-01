package com.example.abdullahaljubaer.frc_offline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecommendationActivity extends AppCompatActivity {

    Spinner spinnerUnitLand = null;
    //private String[] units = {"hector", "decimal"};
    private List<String> units = new ArrayList<>();
    private String unit = "hector";

    private TextView txtCropName = null;
    private TextView txtVar = null;
    private TextView txtTexture = null;
    private TextView txtStatusN = null;

    private TextView txtN = null;
    private TextView txtP = null;
    private TextView txtK = null;
    private TextView txtS = null;
    private TextView txtZn = null;
    private TextView txtB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        units.add("hector");
        units.add("decimal");
        spinnerUnitLand = findViewById(R.id.spn_unit_land);
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(
                RecommendationActivity.this, android.R.layout.simple_spinner_item, units);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitLand.setAdapter(spnAdapter);

        txtN = findViewById(R.id.txt_nuN);
        txtP = findViewById(R.id.txt_nuP);
        txtK = findViewById(R.id.txt_nuK);
        txtS = findViewById(R.id.txt_nuS);
        txtZn = findViewById(R.id.txt_nuZn);
        txtB = findViewById(R.id.txt_nuB);

        Bundle extras = getIntent().getExtras();

        String[] v = {"N", "P", "K", "S", "Zn", "B"};

        if (extras != null){
            txtN.setText(extras.getString(v[0]));
            txtP.setText(extras.getString(v[1]));
            txtK.setText(extras.getString(v[2]));
            txtS.setText(extras.getString(v[3]));
            txtZn.setText(extras.getString(v[4]));
            txtB.setText(extras.getString(v[5]));
        }
    }
}