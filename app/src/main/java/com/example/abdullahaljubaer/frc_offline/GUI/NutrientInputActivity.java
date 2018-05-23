package com.example.abdullahaljubaer.frc_offline.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Crop;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Interpretation;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Nutrient;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Texture;
import com.example.abdullahaljubaer.frc_offline.CustomViews.DropDownAnim;
import com.example.abdullahaljubaer.frc_offline.CustomViews.GuideAlertDialog;
import com.example.abdullahaljubaer.frc_offline.CustomViews.ResultAlertDialog;
import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABDULLAH AL JUBAER on 04-04-18.
 */

public class NutrientInputActivity extends AppCompatActivity {

    private Crop mCrop;
    private Texture mTexture;

    private TextView txtTop;

    private EditText editTextN;
    private EditText editTextP;
    private EditText editTextK;
    private EditText editTextS;
    private EditText editTextZn;
    private EditText editTextB;

    private RadioGroup radioGroupN;
    private RadioGroup radioGroupP;
    private RadioGroup radioGroupK;
    private RadioGroup radioGroupS;
    private RadioGroup radioGroupZn;
    private RadioGroup radioGroupB;

    private TextView txtResN;
    private TextView txtResP;
    private TextView txtResK;
    private TextView txtResS;
    private TextView txtResZn;
    private TextView txtResB;

    private AlertDialog dialog = null;
    private RelativeLayout mainLayout;
    private ResultProducer producer;

    private Map<String, ResultProducer> resultMap = new HashMap<>();

    private Map<String, String> result = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_soil_test);

        mainLayout = findViewById(R.id.soil);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_guide, mainLayout, false);

        new GuideAlertDialog(this, v, dialog);


        editTextN = findViewById(R.id.edtxt_N1);
        editTextP = findViewById(R.id.edtxt_P1);
        editTextK = findViewById(R.id.edtxt_K1);
        editTextS = findViewById(R.id.edtxt_S1);
        editTextZn = findViewById(R.id.edtxt_Zn1);
        editTextB = findViewById(R.id.edtxt_B1);

        radioGroupN = findViewById(R.id.radio_n);
        radioGroupP = findViewById(R.id.radio_P);
        radioGroupK = findViewById(R.id.radio_K);
        radioGroupS = findViewById(R.id.radio_S);
        radioGroupZn = findViewById(R.id.radio_Zn);
        radioGroupB = findViewById(R.id.radio_B);

        txtResN = findViewById(R.id.rn);
        txtResP = findViewById(R.id.rp);
        txtResK = findViewById(R.id.rk);
        txtResS = findViewById(R.id.rs);
        txtResZn = findViewById(R.id.rzn);
        txtResB = findViewById(R.id.rb);

        Bundle extras = getIntent().getExtras();

        mCrop = CropInputActivity.mCrop;
        mTexture = CropInputActivity.mTexture;

        if (extras != null) {

            //mCrop = (Crop) getIntent().getSerializableExtra("crop");
            //mTexture = (Texture) getIntent().getSerializableExtra("texture");
        }

        //DropDownAnim.collapse(txtResN);

        txtTop = findViewById(R.id.txt_top);
        txtTop.setText("Crop season: " + mCrop.getSeasonName()
                + "\nVariety: " + mCrop.getVarietyName()
                + "\nYield goal: -"
                + "\nTexture: " + mTexture.getTexture()
                + "\nLand type: " + mTexture.getLandType());



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
    String r = "";
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
                String res1, res2;
                try {
                    soilTextValue = Double.parseDouble(editTextN.getText().toString());
                    Nutrient nitrogen = new Nutrient("Nitrogen", "N");
                    val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    Interpretation ti = nitrogen.getTestInterpretation();
                    Interpretation ri = nitrogen.getRecommendationInterpretation();

                    String c = "As, Nitrogen composition in Urea 46%";

                    resultMap.put("N", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                            ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                    resultMap.get("N").setFrFinal("Urea", val, 46.0, val*(100.0/46.0));

                    if (val == -1) {
                        throw new NumberFormatException();
                        //res1 = "Invalid Input. Please provide value within the range.";
                        //res2 = res1;
                        //DropDownAnim.collapse(txtResN);
                    }
                    else {

                        res1 = String.format("%.3f (kg/ha)", val);
                        res2 = String.format("%.3f (kg/ha)", val*(100.0/46.0));

                        RadioButton rb;

                        switch (ri.getStatus()) {
                            case "Very Low":
                                rb = findViewById(R.id.radioNVL);
                                rb.setChecked(true);
                                break;
                            case "Low":
                                rb = findViewById(R.id.radioNL);
                                rb.setChecked(true);
                                break;
                            case "Medium":
                                rb = findViewById(R.id.radioNM);
                                rb.setChecked(true);
                                break;
                            case "Optimum":
                                rb = findViewById(R.id.radioNO);
                                rb.setChecked(true);
                                break;
                        }



                        DropDownAnim.expand(txtResN);

                    }
                    //double x = val * 1.0;
                    result.put("N", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. Urea", res2);
                    Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtResN.setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextN.setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
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
                    editTextZn.setError("Invalid Input. Please provide value within the range.");
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

    public void showDetails(View view) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.result_view, mainLayout, false);

        //LinearLayout linearLayout = v.findViewById(R.id.final_result);
        new ResultAlertDialog(this, v, dialog, resultMap.get("N"));
    }

    public void onClickRN (View view){
        int selectedId = radioGroupN.getCheckedRadioButtonId();

        RadioButton radioButtonN = findViewById(selectedId);

        double val = 0.0;
        String res1, res2;
        try {
            //soilTextValue = Double.parseDouble(editTextN.getText().toString());
            Nutrient nitrogen = new Nutrient("Nitrogen", "N");
            String status = radioButtonN.getText().toString();
            val = nitrogen.calculateRequiredNutrient(mCrop, status);
            //Interpretation ti = nitrogen.getTestInterpretation();
            //Interpretation ri = nitrogen.getRecommendationInterpretation();

            /*
            String c = "As, Nitrogen composition in Urea 46%";

            resultMap.put("N", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                    ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
            resultMap.get("N").setFrFinal("Urea", val, 46.0, val*(100.0/46.0));
            */

            if (val == -1) {
                throw new NumberFormatException();
                //res1 = "Invalid Input. Please provide value within the range.";
                //res2 = res1;
                //DropDownAnim.collapse(txtResN);
            }
            else {

                res1 = String.format("%.3f (kg/ha)", val);
                res2 = String.format("%.3f (kg/ha)", val*(100.0/46.0));

                DropDownAnim.expand(txtResN);

            }
            //double x = val * 1.0;
            result.put("N", String.valueOf(val));
            //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
            String rr = String.format("%15s: %s",
                    "Req. Urea", res2);
            Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
            txtResN.setText(rr);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
            editTextN.setError("Invalid Input. Please provide value within the range.");
            //DropDownAnim.collapse(txtResN);
        }

    }

}
