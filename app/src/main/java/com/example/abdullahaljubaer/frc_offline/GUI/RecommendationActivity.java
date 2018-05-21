package com.example.abdullahaljubaer.frc_offline.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationActivity extends AppCompatActivity {

    Spinner spinnerUnitLand = null;
    private EditText editArea;
    //private String[] units = {"hector", "decimal"};
    private List<String> units = new ArrayList<>();
    private String unit = "hector";
    private String[] frr;
    private double[] fq = new double[6];
    private TextView[][] cells;
    private Map <String, Double> mpArea = new HashMap<>();
    private double currentArea = 1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        //System.out.println( MainActivity.nutrientRecommendationDBHelper.numberOfRows() );
        cells = new TextView[][]{
                {findViewById(R.id.n_1), findViewById(R.id.n_2), findViewById(R.id.n_3)},
                {findViewById(R.id.p_1), findViewById(R.id.p_2), findViewById(R.id.p_3)},
                {findViewById(R.id.k_1), findViewById(R.id.k_2), findViewById(R.id.k_3)},
                {findViewById(R.id.s_1), findViewById(R.id.s_2), findViewById(R.id.s_3)},
                {findViewById(R.id.zn_1), findViewById(R.id.zn_2), findViewById(R.id.zn_3)},
                {findViewById(R.id.b_1), findViewById(R.id.b_2), findViewById(R.id.b_3)}
        };

        editArea = findViewById(R.id.edit_area);

        frr = new String[]{
                "Urea", "TSP", "MoP", "Gypsum", "Zinc Sulphate Mono", "Solubor"
        };

        Arrays.fill(fq, -1);

        mpArea.put("hector", 1.0);
        mpArea.put("decimal", 0.004046);

        units.add("hector");
        units.add("decimal");
        spinnerUnitLand = findViewById(R.id.spn_unit_land);
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(
                RecommendationActivity.this, android.R.layout.simple_spinner_item, units);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitLand.setAdapter(spnAdapter);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            frr = extras.getStringArray("fertilizer");
            fq = extras.getDoubleArray("amg");
        }
        spinnerUnitLand.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        editArea.setText("1");

        editArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    currentArea = Double.valueOf(editable.toString());
                    calculate();
                } catch (NumberFormatException e) {
                    editArea.setError("Invalid Input");
                }
            }
        });

        init();
    }

    private void init () {
        for (int i = 0; i < 6; i++){
            cells[i][0].setText(frr[i]);
        }
        calculate();
    }

    private void calculate () {
        for (int i = 0; i < 6; i++){
            double val = fq[i] * currentArea * mpArea.get(unit);
            System.out.println(fq[i] + " " + currentArea + mpArea.get(unit));
            String s = String.format("%.2f", val);
            cells[i][1].setText(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_account){
            Toast.makeText(getApplicationContext(), "Account selected", Toast.LENGTH_LONG).show();
        }
        else if (item.getItemId() == R.id.item_setting){
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_LONG).show();
        }
        else if (item.getItemId() == R.id.item_logout){
            Toast.makeText(getApplicationContext(), "Logout selected", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            unit = parent.getItemAtPosition(pos).toString();
            calculate();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}