package com.example.abdullahaljubaer.frc_offline.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Crop;
import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Nutrient;
import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Texture;
import com.example.abdullahaljubaer.frc_offline.CustomViews.DropDownAnim;
import com.example.abdullahaljubaer.frc_offline.CustomViews.GuideAlertDialog;
import com.example.abdullahaljubaer.frc_offline.CustomViews.ResultAlertDialog;
import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABDULLAH AL JUBAER on 18-04-18.
 */

public class NutrientInputActivityCopy extends AppCompatActivity {

    private Crop mCrop;
    private Texture mTexture;

    private TextView txtTop;

    private TextInputLayout[] inputLayout;
    private LinearLayout[] linearLayoutsDC;
    private EditText[] editTexts;

    private RadioGroup[] radioGroupF;

    private RadioGroup[] radioGroups;

    private TextView[] txtRes;

    private AlertDialog dialog = null;
    private ScrollView mainLayout;
    private ResultProducer producer;

    private Map<String, ResultProducer> resultMap = new HashMap<>();

    private Map<String, String> result = new HashMap<>();

    private String[] frr = new String[]{"Urea", "TSP", "MoP", "Gypsum", "Zinc Sulphate Mono", "Boric Acid"};
    private double[] cmp = new double[6];
    private double[] requiredNutrient = new double[6];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frc_nutrient_input);

        mainLayout = findViewById(R.id.soil);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_guide, mainLayout, false);

        new GuideAlertDialog(this, v, dialog);


        inputLayout = new TextInputLayout[]{
                findViewById(R.id.nWrapper), findViewById(R.id.pWrapper),
                findViewById(R.id.kWrapper), findViewById(R.id.sWrapper),
                findViewById(R.id.znWrapper), findViewById(R.id.bWrapper)
        };

        linearLayoutsDC = new LinearLayout[]{
                findViewById(R.id.res_n), findViewById(R.id.res_p),
                findViewById(R.id.res_k), findViewById(R.id.res_s),
                findViewById(R.id.res_zn), findViewById(R.id.res_b)
        };

        radioGroupF = new RadioGroup[]{
                findViewById(R.id.radio_fn), findViewById(R.id.radio_fp),
                findViewById(R.id.radio_fk), findViewById(R.id.radio_fs),
                findViewById(R.id.radio_fzn), findViewById(R.id.radio_fb)
        };

        radioGroups = new RadioGroup[]{
                findViewById(R.id.radio_n), findViewById(R.id.radio_p),
                findViewById(R.id.radio_k), findViewById(R.id.radio_s),
                findViewById(R.id.radio_zn), findViewById(R.id.radio_b)
        };
        
        editTexts = new EditText[]{
                findViewById(R.id.edtxt_N1), findViewById(R.id.edtxt_P1),
                findViewById(R.id.edtxt_K1), findViewById(R.id.edtxt_S1),
                findViewById(R.id.edtxt_Zn1), findViewById(R.id.edtxt_B1)
        };

        txtRes = new TextView[]{
                findViewById(R.id.rn), findViewById(R.id.rp),
                findViewById(R.id.rk), findViewById(R.id.rs),
                findViewById(R.id.rzn), findViewById(R.id.rb),
        };

        Bundle extras = getIntent().getExtras();

        mCrop = CropInputActivity.mCrop;
        mTexture = CropInputActivity.mTexture;

        if (extras != null) {

            //mCrop = (Crop) getIntent().getSerializableExtra("crop");
            //mTexture = (Texture) getIntent().getSerializableExtra("texture");
        }

        collapseAllRes();

        txtTop = findViewById(R.id.txt_top);
        txtTop.setText("Crop season: " + mCrop.getSeasonName()
                + "\nVariety: " + mCrop.getVarietyName()
                + "\nYield goal: -"
                + "\nTexture: " + mTexture.getTexture()
                + "\nLand type: " + mTexture.getLandType());

        collapseAll();

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



        editTexts[0].addTextChangedListener(new TextWatcher() {
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
                    soilTextValue = Double.parseDouble(editTexts[0].getText().toString());
                    Nutrient nitrogen = new Nutrient("Nitrogen", "N");
                    val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[0] = val;
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



                        DropDownAnim.expand(txtRes[0]);

                    }
                    //double x = val * 1.0;
                    result.put("N", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. Urea", res2);
                    //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtRes[0].setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[0].getText().toString().equals(""))
                        editTexts[0].setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
                }
            }
        });


        editTexts[1].addTextChangedListener(new TextWatcher() {
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
                    soilTextValue = Double.parseDouble(editTexts[1].getText().toString());
                    Nutrient phosphorus = new Nutrient("Phosphorus", "P");
                    val = phosphorus.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[1] = val;
                    Interpretation ti = phosphorus.getTestInterpretation();
                    Interpretation ri = phosphorus.getRecommendationInterpretation();

                    int rid = radioGroupF[1].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofp1){
                        c = "As, Phosphorus composition in TSP 20%";
                        cmp[1] = 20.0;
                        frr[1] = "TSP";
                        resultMap.put("P", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("P").setFrFinal("TSP", val, 20.0, val*(100.0/20.0));
                    }
                    else if (rid == R.id.radiofp2){
                        c = "As, Phosphorus composition in DAP 20%";
                        cmp[1] = 20.0; frr[1] = "DAP";
                        resultMap.put("P", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("P").setFrFinal("DAP", val, 20.0, val*(100.0/20.0));
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        res1 = String.format("%.3f (kg/ha)", val);
                        res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[1]));

                        RadioButton rb;

                        switch (ri.getStatus()) {
                            case "Very Low":
                                rb = findViewById(R.id.radioPVL);
                                rb.setChecked(true);
                                break;
                            case "Low":
                                rb = findViewById(R.id.radioPL);
                                rb.setChecked(true);
                                break;
                            case "Medium":
                                rb = findViewById(R.id.radioPM);
                                rb.setChecked(true);
                                break;
                            case "Optimum":
                                rb = findViewById(R.id.radioPO);
                                rb.setChecked(true);
                                break;
                        }
                        DropDownAnim.expand(txtRes[1]);

                    }
                    //double x = val * 1.0;
                    result.put("P", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. " + frr[1], res2);
                    //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtRes[1].setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[1].getText().toString().equals(""))
                        editTexts[1].setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
                }
            }
        });

        editTexts[2].addTextChangedListener(new TextWatcher() {
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
                    soilTextValue = Double.parseDouble(editTexts[2].getText().toString());
                    Nutrient potassium = new Nutrient("Potassium", "K");
                    val = potassium.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[2] = val;
                    Interpretation ti = potassium.getTestInterpretation();
                    Interpretation ri = potassium.getRecommendationInterpretation();

                    int rid = radioGroupF[2].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofk1){
                        c = "As, Potassium composition in MoP 50%";
                        cmp[2]= 50.0; frr[2] = "MoP";
                        resultMap.put("K", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("K").setFrFinal("MoP", val, cmp[2], val*(100.0/cmp[2]));
                    }
                    else if (rid == R.id.radiofp2){
                        c = "As, Potassium composition in SoP 42%";
                        cmp[2] = 42.0; frr[2] = "SoP";
                        resultMap.put("K", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("K").setFrFinal("SoP", val, cmp[2], val*(100.0/cmp[2]));
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        res1 = String.format("%.3f (kg/ha)", val);
                        res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[2]));

                        RadioButton rb;

                        switch (ri.getStatus()) {
                            case "Very Low":
                                rb = findViewById(R.id.radioKVL);
                                rb.setChecked(true);
                                break;
                            case "Low":
                                rb = findViewById(R.id.radioKL);
                                rb.setChecked(true);
                                break;
                            case "Medium":
                                rb = findViewById(R.id.radioKM);
                                rb.setChecked(true);
                                break;
                            case "Optimum":
                                rb = findViewById(R.id.radioKO);
                                rb.setChecked(true);
                                break;
                        }
                        DropDownAnim.expand(txtRes[2]);

                    }
                    //double x = val * 1.0;
                    result.put("K", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. " + frr[2], res2);
                    //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtRes[2].setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[2].getText().toString().equals(""))
                        editTexts[2].setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
                }
            }
        });

        editTexts[3].addTextChangedListener(new TextWatcher() {
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
                    soilTextValue = Double.parseDouble(editTexts[3].getText().toString());
                    Nutrient sulphur = new Nutrient("Sulphur", "S");
                    val = sulphur.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[3] = val;
                    Interpretation ti = sulphur.getTestInterpretation();
                    Interpretation ri = sulphur.getRecommendationInterpretation();

                    int rid = radioGroupF[3].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofs1){
                        cmp[3]= 18.0; frr[3] = "Gypsum";
                        c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
                        resultMap.put("S", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("S").setFrFinal(frr[3], val, cmp[3], val*(100.0/cmp[3]));
                    }
                    else if (rid == R.id.radiofs2){
                        cmp[3] = 23.5; frr[3] = "Ammonium Sulphate";
                        c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
                        resultMap.put("S", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("S").setFrFinal(frr[3], val, cmp[3], val*(100.0/cmp[3]));
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        res1 = String.format("%.3f (kg/ha)", val);
                        res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[3]));

                        RadioButton rb;

                        switch (ri.getStatus()) {
                            case "Very Low":
                                rb = findViewById(R.id.radioSVL);
                                rb.setChecked(true);
                                break;
                            case "Low":
                                rb = findViewById(R.id.radioSL);
                                rb.setChecked(true);
                                break;
                            case "Medium":
                                rb = findViewById(R.id.radioSM);
                                rb.setChecked(true);
                                break;
                            case "Optimum":
                                rb = findViewById(R.id.radioSO);
                                rb.setChecked(true);
                                break;
                        }
                        DropDownAnim.expand(txtRes[3]);

                    }
                    //double x = val * 1.0;
                    result.put("S", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. " + frr[3], res2);
                    //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtRes[3].setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[3].getText().toString().equals(""))
                        editTexts[3].setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
                }
            }
        });

        editTexts[4].addTextChangedListener(new TextWatcher() {
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
                    soilTextValue = Double.parseDouble(editTexts[4].getText().toString());
                    Nutrient zinc = new Nutrient("Zinc", "Zn");
                    val = zinc.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[4] = val;
                    Interpretation ti = zinc.getTestInterpretation();
                    Interpretation ri = zinc.getRecommendationInterpretation();

                    int rid = radioGroupF[4].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofzn1){
                        cmp[4] = 36.0; frr[4] = "Zinc Sulphate Mono";
                        c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
                        resultMap.put("Zn", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("Zn").setFrFinal(frr[4], val, cmp[4], val*(100.0/cmp[4]));
                    }
                    else if (rid == R.id.radiofzn2){
                        cmp[4] = 23.0; frr[4] = "Zinc Sulphate Hepta";
                        c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
                        resultMap.put("Zn", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("Zn").setFrFinal(frr[4], val, cmp[4], val*(100.0/cmp[4]));
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        res1 = String.format("%.3f (kg/ha)", val);
                        res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[4]));

                        RadioButton rb;

                        switch (ri.getStatus()) {
                            case "Very Low":
                                rb = findViewById(R.id.radioZnVL);
                                rb.setChecked(true);
                                break;
                            case "Low":
                                rb = findViewById(R.id.radioZnL);
                                rb.setChecked(true);
                                break;
                            case "Medium":
                                rb = findViewById(R.id.radioZnM);
                                rb.setChecked(true);
                                break;
                            case "Optimum":
                                rb = findViewById(R.id.radioZnO);
                                rb.setChecked(true);
                                break;
                        }
                        DropDownAnim.expand(txtRes[4]);

                    }
                    //double x = val * 1.0;
                    result.put("Zn", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. " + frr[4], res2);
                    //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtRes[4].setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[4].getText().toString().equals(""))
                        editTexts[4].setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
                }
            }
        });

        editTexts[5].addTextChangedListener(new TextWatcher() {
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
                    soilTextValue = Double.parseDouble(editTexts[5].getText().toString());
                    Nutrient boron = new Nutrient("Boron", "B");
                    val = boron.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[5] = val;
                    Interpretation ti = boron.getTestInterpretation();
                    Interpretation ri = boron.getRecommendationInterpretation();

                    int rid = radioGroupF[5].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofb1){
                        cmp[5] = 17.0; frr[5] = "Boric Acid";
                        c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
                        resultMap.put("B", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("B").setFrFinal(frr[5], val, cmp[5], val*(100.0/cmp[5]));
                    }
                    else if (rid == R.id.radiofb2){
                        cmp[5] = 20.0; frr[5] = "Solubor";
                        c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
                        resultMap.put("B", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                                ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
                        resultMap.get("B").setFrFinal(frr[5], val, cmp[5], val*(100.0/cmp[5]));
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        res1 = String.format("%.3f (kg/ha)", val);
                        res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[5]));

                        RadioButton rb;

                        switch (ri.getStatus()) {
                            case "Very Low":
                                rb = findViewById(R.id.radioBVL);
                                rb.setChecked(true);
                                break;
                            case "Low":
                                rb = findViewById(R.id.radioBL);
                                rb.setChecked(true);
                                break;
                            case "Medium":
                                rb = findViewById(R.id.radioBM);
                                rb.setChecked(true);
                                break;
                            case "Optimum":
                                rb = findViewById(R.id.radioBO);
                                rb.setChecked(true);
                                break;
                        }
                        DropDownAnim.expand(txtRes[5]);

                    }
                    //double x = val * 1.0;
                    result.put("B", String.valueOf(val));
                    //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
                    String rr = String.format("%15s: %s",
                            "Req. " + frr[5], res2);
                    //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
                    txtRes[5].setText(rr);
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[5].getText().toString().equals(""))
                        editTexts[5].setError("Invalid Input. Please provide value within the range.");
                    //DropDownAnim.collapse(txtResN);
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

    // -------------------- Details view ---------------------- //

    public void showDetails(View view) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.result_view, mainLayout, false);

        //LinearLayout linearLayout = v.findViewById(R.id.final_result);

        switch (view.getId()){
            case R.id.dn:
                new ResultAlertDialog(this, v, dialog, resultMap.get("N"));
                break;
            case R.id.dp:
                new ResultAlertDialog(this, v, dialog, resultMap.get("P"));
                break;
            case R.id.dk:
                new ResultAlertDialog(this, v, dialog, resultMap.get("K"));
                break;
            case R.id.ds:
                new ResultAlertDialog(this, v, dialog, resultMap.get("S"));
                break;
            case R.id.dzn:
                new ResultAlertDialog(this, v, dialog, resultMap.get("Zn"));
                break;
            case R.id.db:
                new ResultAlertDialog(this, v, dialog, resultMap.get("B"));
                break;

        }


    }


    // --------------------------- Details view ---------------------- //


    // ------------------------ Status Based Calculation --------------------- //


    public void onClickRN (View view){
        int selectedId = radioGroups[0].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;
        String res1, res2;
        try {
            //soilTextValue = Double.parseDouble(editTexts[0].getText().toString());
            editTexts[0].getText().clear();
            Nutrient nitrogen = new Nutrient("Nitrogen", "N");
            String status = radioButton.getText().toString();
            val = nitrogen.calculateRequiredNutrient(mCrop, status);
            requiredNutrient[0] = val;
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

                DropDownAnim.expand(txtRes[0]);

            }
            //double x = val * 1.0;
            result.put("N", String.valueOf(val));
            //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
            String rr = String.format("%15s: %s",
                    "Req. Urea", res2);
            //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
            txtRes[0].setText(rr);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
            editTexts[0].setError("Invalid Input. Please provide value within the range.");
            //DropDownAnim.collapse(txtResN);
        }

    }


    public void onClickRP (View view){
        int selectedId = radioGroups[1].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;
        String res1, res2;
        try {
            //soilTextValue = Double.parseDouble(editTexts[0].getText().toString());
            editTexts[1].getText().clear();
            Nutrient phosphorus = new Nutrient("Phosphorus", "P");
            String status = radioButton.getText().toString();
            val = phosphorus.calculateRequiredNutrient(mCrop, status);
            requiredNutrient[1] = val;
            //Interpretation ti = nitrogen.getTestInterpretation();
            //Interpretation ri = nitrogen.getRecommendationInterpretation();

            /*
            String c = "As, Nitrogen composition in Urea 46%";

            resultMap.put("N", new ResultProducer(soilTextValue, ri.getStatus(), ri.getUpperLimit(),
                    ti.getInterval(), ri.getInterval(), ti.getLowerLimit(), val, c));
            resultMap.get("N").setFrFinal("Urea", val, 46.0, val*(100.0/46.0));
            */
            String c;


            if (val == -1) {
                throw new NumberFormatException();
                //res1 = "Invalid Input. Please provide value within the range.";
                //res2 = res1;
                //DropDownAnim.collapse(txtResN);
            }
            else {

                res1 = String.format("%.3f (kg/ha)", val);
                res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[1]));

                DropDownAnim.expand(txtRes[1]);

            }
            //double x = val * 1.0;
            result.put("P", String.valueOf(val));
            //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
            String rr = String.format("%15s: %s",
                    "Req. " + frr[1], res2);
            //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
            txtRes[1].setText(rr);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
            editTexts[1].setError("Invalid Input. Please provide value within the range.");
            //DropDownAnim.collapse(txtResN);
        }

    }


    public void onClickFN (View view){
        int selectedId = radioGroupF[0].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        expandOne(0);

    }

    public void onClickFP (View view){
        int rid = radioGroupF[1].getCheckedRadioButtonId();

        String c, res1 = "", res2 = "";

        double val = requiredNutrient[1];

        if (rid == R.id.radiofp1){
            c = "As, Phosphorus composition in TSP 20%";
            cmp[1] = 20.0;
            frr[1] = "TSP";
            //resultMap.get("P").setFrFinal("TSP", val, 20.0, val*(100.0/20.0));

            if (val == -1) {
                throw new NumberFormatException();
            }
            else {
                res1 = String.format("%.3f (kg/ha)", val);
                res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[1]));

                RadioButton rb;
                DropDownAnim.expand(txtRes[1]);

            }
            //double x = val * 1.0;
            result.put("P", String.valueOf(val));
            //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
            String rr = String.format("%15s: %s",
                    "Req. " + frr[1], res2);
            //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
            txtRes[1].setText(rr);
        }
        else if (rid == R.id.radiofp2){
            c = "As, Phosphorus composition in DAP 20%";
            cmp[1] = 20.0; frr[1] = "DAP";
            //resultMap.get("P").setFrFinal("DAP", val, 20.0, val*(100.0/20.0));

            if (val == -1) {
                throw new NumberFormatException();
            }
            else {
                res1 = String.format("%.3f (kg/ha)", val);
                res2 = String.format("%.3f (kg/ha)", val*(100.0/cmp[1]));

                RadioButton rb;
                DropDownAnim.expand(txtRes[1]);

            }
            //double x = val * 1.0;
            result.put("P", String.valueOf(val));
            //Toast.makeText(getApplicationContext(), String.valueOf(val), Toast.LENGTH_LONG).show();
            String rr = String.format("%15s: %s",
                    "Req. " + frr[1], res2);
            //Toast.makeText(getApplicationContext(), rr, Toast.LENGTH_LONG).show();
            txtRes[1].setText(rr);

        }

        expandOne(1);

    }

    public void onClickFK (View view){
        int selectedId = radioGroupF[2].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        expandOne(2);

    }

    public void onClickFS (View view){
        int selectedId = radioGroupF[3].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        expandOne(3);

    }


    public void onClickFZn (View view){
        int selectedId = radioGroupF[4].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        expandOne(4);

    }

    public void onClickFB (View view){
        int selectedId = radioGroupF[5].getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedId);

        expandOne(5);

    }

    private void collapseAll(){
        for (int i = 0; i < 6; i++) {
            DropDownAnim.collapse(inputLayout[i]);
            DropDownAnim.collapse(radioGroups[i]);
            DropDownAnim.collapse(linearLayoutsDC[i]);
        }
    }

    private void collapseAllRes(){
        for (int i = 0; i < 6; i++) {
            txtRes[i].setVisibility(View.GONE);
        }
    }

    private void collapseOne(int i){
        DropDownAnim.collapse(inputLayout[i]);
        DropDownAnim.collapse(radioGroups[i]);
        DropDownAnim.collapse(linearLayoutsDC[i]);
    }

    private void expandOne(int i){
        DropDownAnim.expand(inputLayout[i]);
        DropDownAnim.expand(radioGroups[i]);
        DropDownAnim.expand(linearLayoutsDC[i]);
    }

    public void close(View view){

        if (view.getId() == R.id.cn)
            collapseOne(0);
        else if (view.getId() == R.id.cp)
            collapseOne(1);
        else if (view.getId() == R.id.ck)
            collapseOne(2);
        else if (view.getId() == R.id.cs)
            collapseOne(3);
        else if (view.getId() == R.id.czn)
            collapseOne(4);
        else if (view.getId() == R.id.cb)
            collapseOne(5);
    }

}
