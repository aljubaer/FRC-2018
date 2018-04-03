package com.example.abdullahaljubaer.frc_offline.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABDULLAH AL JUBAER on 04-04-18.
 */

public class NutrientInputActivity extends AppCompatActivity {

    private Crop mCrop;
    private Texture mTexture;

    private EditText editTextN;
    private EditText editTextP;
    private EditText editTextK;
    private EditText editTextS;
    private EditText editTextZn;
    private EditText editTextB;

    private Map<String, String> result = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_soil_test);

        editTextN = findViewById(R.id.edtxt_N1);
        editTextP = findViewById(R.id.edtxt_P1);
        editTextK = findViewById(R.id.edtxt_K1);
        editTextS = findViewById(R.id.edtxt_S1);
        editTextZn = findViewById(R.id.edtxt_Zn1);
        editTextB = findViewById(R.id.edtxt_B1);

        Bundle extras = getIntent().getExtras();

        mCrop = CropInputActivity.mCrop;
        mTexture = CropInputActivity.mTexture;

        if (extras != null) {

            //mCrop = (Crop) getIntent().getSerializableExtra("crop");
            //mTexture = (Texture) getIntent().getSerializableExtra("texture");
        }

        calculate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_account) {
            Toast.makeText(getApplicationContext(), "Account selected", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.item_setting) {
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.item_logout) {
            Toast.makeText(getApplicationContext(), "Logout selected", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private double soilTextValue = 0.1;

    public void calculate() {

        editTextN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double val = 0.0;
                try {
                    soilTextValue = Double.parseDouble(editTextN.getText().toString());
                    Nutrient nitrogen = new Nutrient("Nitrogen", "N");
                    val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    if (val == Double.NaN) editTextN.setError("Invalid Input");
                    //double x = val * 1.0;
                    result.put("N", String.valueOf(val));
                    Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextN.setError("Invalid Input");
                }
            }
        });

        editTextP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    soilTextValue = Double.parseDouble(editTextP.getText().toString());
                    Nutrient nitrogen = new Nutrient("Phosphorus", "P");
                    double val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    if (val == Double.NaN) editTextP.setError("Invalid Input");
                    //double x = val * 1.0;
                    result.put("P", String.valueOf(val));
                    Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextP.setError("Invalid Input");
                }
            }
        });

        editTextK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    soilTextValue = Double.parseDouble(editTextK.getText().toString());
                    Nutrient nitrogen = new Nutrient("Potassium", "K");
                    double val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    if (val == Double.NaN) editTextK.setError("Invalid Input");
                    double x = val * 1.0;
                    result.put("K", String.valueOf(val));
                    Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextK.setError("Invalid Input");
                }
            }
        });

        editTextS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    soilTextValue = Double.parseDouble(editTextS.getText().toString());
                    Nutrient nitrogen = new Nutrient("Sulphur", "S");
                    double val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    //if (val == Double.NaN) editTextS.setError("Invalid Input");
                    double x = val * 1.0;
                    result.put("S", String.valueOf(val));
                    Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextS.setError("Invalid Input");
                }
            }
        });

        editTextZn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    soilTextValue = Double.parseDouble(editTextZn.getText().toString());
                    Nutrient nitrogen = new Nutrient("Zinc", "Zn");
                    double val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    //if (val == Double.NaN) editTextZn.setError("Invalid Input");
                    double x = val * 1.0;
                    result.put("Zn", String.valueOf(val));
                    Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextZn.setError("Invalid Input");
                }
            }
        });

        editTextB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    soilTextValue = Double.parseDouble(editTextB.getText().toString());
                    Nutrient nitrogen = new Nutrient("Boron", "B");
                    double val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    //if (val == Double.NaN) editTextN.setError("Invalid Input");
                    double x = val * 1.0;
                    result.put("B", String.valueOf(val));
                    Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextB.setError("Invalid Input");
                }
            }
        });

    }

    public void createRecommendation(View view) {
        String[] v = {"N", "P", "K", "S", "Zn", "B"};

        Intent intent = new Intent(this, RecommendationActivity.class);

        int len = 0;

        try {
            len = result.size();

            for (int i = 0; i < len; i++) {
                intent.putExtra(v[i], result.get(v[i]));
            }

            startActivity(intent);

        } catch (Exception e) {
            e.getMessage();
        }


    }
}
