package com.example.abdullahaljubaer.frc_offline.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Crop;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Interpretation;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Nutrient;
import com.example.abdullahaljubaer.frc_offline.FunctionalClasses.Texture;
import com.example.abdullahaljubaer.frc_offline.CustomViews.DropDownAnim;
import com.example.abdullahaljubaer.frc_offline.CustomViews.GuideAlertDialog;
import com.example.abdullahaljubaer.frc_offline.CustomViews.ResultDialog;
import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.IResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.StatusBasedResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.TestBasedResultProducer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABDULLAH AL JUBAER on 18-04-18.
 */

public class NutrientInputActivityCopy extends BaseActivity {

    private Crop mCrop;
    private Texture mTexture;

    private TextView txtTop;

    private TextInputLayout[] inputLayout;
    private LinearLayout[] linearLayoutsDC;
    private EditText[] editTexts;
    private RadioGroup[] radioGroupF;
    private RadioGroup[] radioGroups;
    private TextView[] txtRes;
    private Button btnRecommendation;
    private Boolean[] isCalculated, isChecked;

    private AlertDialog dialog = null;
    private ScrollView mainLayout;
    private ResultProducer producer;

    private Map<String, ResultProducer> resultMap = new HashMap<>();
    private Map<String, IResultProducer> mResults = new HashMap<>();
    private Map<String, String> result = new HashMap<>();

    private String[] frr = new String[]{"Urea", "TSP", "MoP", "Gypsum", "Zinc Sulphate Mono", "Boric Acid"};
    private double[] cmp = new double[6];
    private double[] requiredNutrient = new double[6];
    private Interpretation[] ti = new Interpretation[6];
    private Interpretation[] ri = new Interpretation[6];
    private Nutrient[] nutrients = new Nutrient[6];
    private int[] last = new int[6];
    private double[] fq = new double[6];
    private Boolean[] isVisible = new Boolean[6];

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

        btnRecommendation = findViewById(R.id.btn_recomm);

        isCalculated = new Boolean[6];
        Arrays.fill(isCalculated, false);
        isChecked = new Boolean[6];
        Arrays.fill(isChecked, false);
        Arrays.fill(isVisible, false);

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

    /*
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
    */

    private double soilTextValue = 0.1;
    String r = "";
    public void calculate() {

        editTexts[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                double val = 0.0;
                String res1, res2;
                try {
                    soilTextValue = Double.parseDouble(editTexts[0].getText().toString());
                    if (soilTextValue > 0.6) throw new NumberFormatException();
                    Nutrient nitrogen = new Nutrient("Nitrogen", "N");
                    nutrients[0] = nitrogen;
                    val = nitrogen.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[0] = val;

                    ti[0] = nitrogen.getTestInterpretation();
                    ri[0] = nitrogen.getRecommendationInterpretation();

                    String c = "As, Nitrogen composition in Urea 46%";
                    int rid = radioGroupF[0].getCheckedRadioButtonId();

                    if (rid == R.id.radiofn1){
                        c = "As, Nitrogen composition in Urea 46%";
                        cmp[0] = 46.0;
                        frr[0] = "Urea";
                    }
                    else if (rid == R.id.radiofn2){
                        c = "As, Nitrogen composition in Urea 46%";
                        cmp[0] = 46.0;
                        frr[0] = "Guti Urea";
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        if (!isCalculated[0]) isCalculated[0] = true;
                        last[0] = 1;
                        prepareResult(soilTextValue, val, c, 0, "N");
                        RadioButton rb;
                        RadioGroup rg = findViewById(R.id.radio_n);
                        switch (ri[0].getStatus()) {
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
                            case "High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                            case "Very High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                        }
                    }
                } catch (NumberFormatException e) {
                    if (!editTexts[0].getText().toString().equals(""))
                        editTexts[0].setError("Invalid Input. Please provide value within the range.");
                } catch (NullPointerException ne) {

                }
            }
        });

        editTexts[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                double val = 0.0;
                try {
                    soilTextValue = Double.parseDouble(editTexts[1].getText().toString());
                    if (soilTextValue > 35) throw new NumberFormatException();
                    Nutrient phosphorus = new Nutrient("Phosphorus", "P");
                    nutrients[1] = phosphorus;
                    val = phosphorus.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[1] = val;
                    ti[1] = phosphorus.getTestInterpretation();
                    ri[1] = phosphorus.getRecommendationInterpretation();

                    int rid = radioGroupF[1].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofp1){
                        c = "As, Phosphorus composition in TSP 20%";
                        cmp[1] = 20.0;
                        frr[1] = "TSP";
                    }
                    else if (rid == R.id.radiofp2){
                        c = "As, Phosphorus composition in DAP 20%";
                        cmp[1] = 20.0;
                        frr[1] = "DAP";
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        if (!isCalculated[1]) isCalculated[1] = true;
                        last[1] = 1;
                        prepareResult(soilTextValue, val, c, 1, phosphorus.getSymbol());

                        RadioButton rb;
                        RadioGroup rg = findViewById(R.id.radio_p);
                        switch (ri[1].getStatus()) {
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
                            case "High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                            case "Very High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                        }
                    }
                } catch (NumberFormatException e) {
                    if (!editTexts[0].getText().toString().equals(""))
                        editTexts[0].setError("Invalid Input. Please provide value within the range.");
                } catch (NullPointerException ne) {

                }
            }
        });

        editTexts[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                double val = 0.0;
                try {
                    soilTextValue = Double.parseDouble(editTexts[2].getText().toString());
                    if (soilTextValue > 0.45) throw new NumberFormatException();
                    Nutrient potassium = new Nutrient("Potassium", "K");
                    nutrients[2] = potassium;
                    val = potassium.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[2] = val;
                    ti[2] = potassium.getTestInterpretation();
                    ri[2] = potassium.getRecommendationInterpretation();

                    int rid = radioGroupF[2].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofk1){
                        c = "As, Potassium composition in MoP 50%";
                        cmp[2]= 50.0; frr[2] = "MoP";
                    }
                    else if (rid == R.id.radiofk2){
                        c = "As, Potassium composition in SoP 42%";
                        cmp[2] = 42.0; frr[2] = "SoP";
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        if (!isCalculated[2]) isCalculated[2] = true;
                        last[2] = 1;
                        prepareResult(soilTextValue, val, c, 2, potassium.getSymbol());

                        RadioButton rb;
                        RadioGroup rg = findViewById(R.id.radio_k);
                        switch (ri[2].getStatus()) {
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
                            case "High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                            case "Very High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                        }
                    }
                } catch (NumberFormatException e) {
                    if (!editTexts[2].getText().toString().equals(""))
                        editTexts[2].setError("Invalid Input. Please provide value within the range.");
                } catch (NullPointerException ne) {

                }
            }
        });

        editTexts[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                double val = 0.0;
                try {
                    soilTextValue = Double.parseDouble(editTexts[3].getText().toString());
                    if (soilTextValue > 55.0) throw new NumberFormatException();
                    Nutrient sulphur = new Nutrient("Sulphur", "S");
                    nutrients[3] = sulphur;
                    val = sulphur.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[3] = val;
                    ti[3] = sulphur.getTestInterpretation();
                    ri[3] = sulphur.getRecommendationInterpretation();

                    int rid = radioGroupF[3].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofs1){
                        cmp[3]= 18.0; frr[3] = "Gypsum";
                        c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
                    }
                    else if (rid == R.id.radiofs2){
                        cmp[3] = 23.5; frr[3] = "Ammonium Sulphate";
                        c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        if (!isCalculated[3]) isCalculated[3] = true;
                        last[3] = 1;
                        prepareResult(soilTextValue, val, c, 3, sulphur.getSymbol());

                        RadioButton rb;
                        RadioGroup rg = findViewById(R.id.radio_s);
                        switch (ri[3].getStatus()) {
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
                            case "High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                            case "Very High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                        }
                    }

                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[3].getText().toString().equals(""))
                        editTexts[3].setError("Invalid Input. Please provide value within the range.");
                } catch (NullPointerException ne) {

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
                try {
                    soilTextValue = Double.parseDouble(editTexts[4].getText().toString());
                    if (soilTextValue > 3.0) throw new NumberFormatException();
                    Nutrient zinc = new Nutrient("Zinc", "Zn");
                    nutrients[4] = zinc;
                    val = zinc.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[4] = val;
                    ti[4] = zinc.getTestInterpretation();
                    ri[4] = zinc.getRecommendationInterpretation();


                    int rid = radioGroupF[4].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofzn1){
                        cmp[4] = 36.0; frr[4] = "Zinc Sulphate Mono";
                        c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
                    }
                    else if (rid == R.id.radiofzn2){
                        cmp[4] = 23.0; frr[4] = "Zinc Sulphate Hepta";
                        c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        if (!isCalculated[4]) isCalculated[4] = true;
                        last[4] = 1;
                        prepareResult(soilTextValue, val, c, 4, zinc.getSymbol());

                        RadioButton rb;
                        RadioGroup rg = findViewById(R.id.radio_zn);
                        switch (ri[4].getStatus()) {
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
                            case "High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                            case "Very High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                        }
                        //DropDownAnim.expand(txtRes[1]);

                    }

                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[4].getText().toString().equals(""))
                        editTexts[4].setError("Invalid Input. Please provide value within the range.");
                } catch (NullPointerException ne) {

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
                try {
                    soilTextValue = Double.parseDouble(editTexts[5].getText().toString());
                    if (soilTextValue > 1.0) throw new NumberFormatException();
                    Nutrient boron = new Nutrient("Boron", "B");
                    nutrients[5] = boron;
                    val = boron.calculateRequiredNutrient(mCrop, mTexture, soilTextValue);
                    requiredNutrient[5] = val;
                    ti[5] = boron.getTestInterpretation();
                    ri[5] = boron.getRecommendationInterpretation();


                    int rid = radioGroupF[5].getCheckedRadioButtonId();
                    RadioButton rbt = findViewById(rid);

                    String c = "";

                    if (rid == R.id.radiofb1){
                        cmp[5] = 17.0; frr[5] = "Boric Acid";
                        c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
                    }
                    else if (rid == R.id.radiofb2){
                        cmp[5] = 20.0; frr[5] = "Solubor";
                        c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
                    }

                    if (val == -1) {
                        throw new NumberFormatException();
                    }
                    else {
                        if (!isCalculated[5]) isCalculated[5] = true;
                        last[5] = 1;
                        prepareResult(soilTextValue, val, c, 5, boron.getSymbol());

                        RadioButton rb;
                        RadioGroup rg = findViewById(R.id.radio_b);
                        switch (ri[5].getStatus()) {
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
                            case "High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                            case "Very High":
                                if (!editTexts[0].getText().toString().equals(""))
                                    editTexts[0].setError("Input value too high. Does not required any dose.");
                                rb = findViewById(rg.getCheckedRadioButtonId());
                                rb.setChecked(false);
                                break;
                        }
                    }
                } catch (NumberFormatException e) {
                    //Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    if (!editTexts[5].getText().toString().equals(""))
                        editTexts[5].setError("Invalid Input. Please provide value within the range.");
                } catch (NullPointerException ne) {

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
        View v;

        switch (view.getId()){
            case R.id.dn: {
                if (last[0] == 1) {
                    v = inflater.inflate(R.layout.result_view, mainLayout, false);
                    new ResultDialog(this, v, dialog, (TestBasedResultProducer) mResults.get("N"));
                } else if (last[0] == 2) {
                    v = inflater.inflate(R.layout.result_view1, mainLayout, false);
                    new ResultDialog(this, v, dialog, (StatusBasedResultProducer) mResults.get("N"));
                }
                break;
            }
            case R.id.dp: {
                if (last[1] == 1) {
                    v = inflater.inflate(R.layout.result_view, mainLayout, false);
                    new ResultDialog(this, v, dialog, (TestBasedResultProducer) mResults.get("P"));
                } else if (last[1] == 2) {
                    v = inflater.inflate(R.layout.result_view1, mainLayout, false);
                    new ResultDialog(this, v, dialog, (StatusBasedResultProducer) mResults.get("P"));
                }
                break;
            }
            case R.id.dk: {
                if (last[2] == 1) {
                    v = inflater.inflate(R.layout.result_view, mainLayout, false);
                    new ResultDialog(this, v, dialog, (TestBasedResultProducer) mResults.get("K"));
                } else if (last[2] == 2) {
                    v = inflater.inflate(R.layout.result_view1, mainLayout, false);
                    new ResultDialog(this, v, dialog, (StatusBasedResultProducer) mResults.get("K"));
                }
                break;
            }
            case R.id.ds:
            {
                if (last[3] == 1) {
                    v = inflater.inflate(R.layout.result_view, mainLayout, false);
                    new ResultDialog(this, v, dialog, (TestBasedResultProducer) mResults.get("S"));
                } else if (last[3] == 2) {
                    v = inflater.inflate(R.layout.result_view1, mainLayout, false);
                    new ResultDialog(this, v, dialog, (StatusBasedResultProducer) mResults.get("S"));
                }
                break;
            }
            case R.id.dzn:
            {
                if (last[4] == 1) {
                    v = inflater.inflate(R.layout.result_view, mainLayout, false);
                    new ResultDialog(this, v, dialog, (TestBasedResultProducer) mResults.get("Zn"));
                } else if (last[4] == 2) {
                    v = inflater.inflate(R.layout.result_view1, mainLayout, false);
                    new ResultDialog(this, v, dialog, (StatusBasedResultProducer) mResults.get("Zn"));
                }
                break;
            }
            case R.id.db:
            {
                if (last[5] == 1) {
                    v = inflater.inflate(R.layout.result_view, mainLayout, false);
                    new ResultDialog(this, v, dialog, (TestBasedResultProducer) mResults.get("B"));
                } else if (last[5] == 2) {
                    v = inflater.inflate(R.layout.result_view1, mainLayout, false);
                    new ResultDialog(this, v, dialog, (StatusBasedResultProducer) mResults.get("B"));
                }
                break;
            }
        }
    }

    // --------------------------- Details view ---------------------- //


    // ------------------------ Status Based Calculation --------------------- //


    public void onClickRN (View view){
        int selectedId = radioGroups[0].getCheckedRadioButtonId();
        if (!isChecked[0])isChecked[0] = true;
        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;
        String res1, res2;

        editTexts[0].getText().clear();
        Nutrient nitrogen = new Nutrient("Nitrogen", "N");
        nutrients[0] = nitrogen;
        String status = radioButton.getText().toString();
        val = nitrogen.calculateRequiredNutrient(mCrop, status);
        requiredNutrient[0] = val;
        ri[0] = nitrogen.getRecommendationInterpretation();

        String c = "As, Nitrogen composition in Urea 46%";
        int rid = radioGroupF[0].getCheckedRadioButtonId();

        if (rid == R.id.radiofn1){
            c = "As, Nitrogen composition in Urea 46%";
            cmp[0] = 46.0;
            frr[0] = "Urea";
        }
        else if (rid == R.id.radiofn2){
            c = "As, Nitrogen composition in Urea 46%";
            cmp[0] = 46.0;
            frr[0] = "Guti Urea";
        }
        prepareResult(val, c, 0, "N");
    }

    public void onClickRP (View view){
        int selectedId = radioGroups[1].getCheckedRadioButtonId();
        if (!isChecked[1])isChecked[1] = true;
        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;
        String res1, res2;

        editTexts[1].getText().clear();
        Nutrient phosphorus = new Nutrient("Phosphorus", "P");
        nutrients[1] = phosphorus;
        String status = radioButton.getText().toString();
        val = phosphorus.calculateRequiredNutrient(mCrop, status);
        requiredNutrient[1] = val;
        ri[1] = phosphorus.getRecommendationInterpretation();

        int rid = radioGroupF[1].getCheckedRadioButtonId();
        RadioButton rbt = findViewById(rid);

        String c = "";

        if (rid == R.id.radiofp1){
            c = "As, Phosphorus composition in TSP 20%";
            cmp[1] = 20.0;
            frr[1] = "TSP";
        }
        else if (rid == R.id.radiofp2){
            c = "As, Phosphorus composition in DAP 20%";
            cmp[1] = 20.0;
            frr[1] = "DAP";
        }
        prepareResult(val, c, 1, "P");

    }

    public void onClickRK (View view){
        int selectedId = radioGroups[2].getCheckedRadioButtonId();
        if (!isChecked[2])isChecked[2] = true;
        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;
        String res1, res2;

        editTexts[2].getText().clear();
        Nutrient potassium = new Nutrient("Potassium", "K");
        nutrients[2] = potassium;
        String status = radioButton.getText().toString();
        val = potassium.calculateRequiredNutrient(mCrop, status);
        requiredNutrient[2] = val;
        ri[2] = potassium.getRecommendationInterpretation();

        int rid = radioGroupF[2].getCheckedRadioButtonId();
        RadioButton rbt = findViewById(rid);

        String c = "";

        if (rid == R.id.radiofk1){
            c = "As, Potassium composition in MoP 50%";
            cmp[2]= 50.0;
            frr[2] = "MoP";
        }
        else if (rid == R.id.radiofk2){
            c = "As, Potassium composition in SoP 42%";
            cmp[2] = 42.0;
            frr[2] = "SoP";
        }

        prepareResult(val, c, 2, "K");

    }

    public void onClickRS (View view){

        int selectedId = radioGroups[3].getCheckedRadioButtonId();
        if (!isChecked[3])isChecked[3] = true;
        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;

        editTexts[3].getText().clear();
        Nutrient sulphur = new Nutrient("Sulphur", "S");
        nutrients[3] = sulphur;
        String status = radioButton.getText().toString();
        val = sulphur.calculateRequiredNutrient(mCrop, status);
        requiredNutrient[3] = val;
        ri[3] = sulphur.getRecommendationInterpretation();

        int rid = radioGroupF[3].getCheckedRadioButtonId();
        RadioButton rbt = findViewById(rid);

        String c = "";

        if (rid == R.id.radiofs1){
            cmp[3]= 18.0; frr[3] = "Gypsum";
            c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
        }
        else if (rid == R.id.radiofs2){
            cmp[3] = 23.5; frr[3] = "Ammonium Sulphate";
            c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
        }

        prepareResult(val, c, 3, "S");

    }

    public void onClickRZn (View view){

        int selectedId = radioGroups[4].getCheckedRadioButtonId();
        if (!isChecked[4])isChecked[4] = true;
        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;

        editTexts[4].getText().clear();
        Nutrient zinc = new Nutrient("Zinc", "Zn");
        nutrients[4] = zinc;
        String status = radioButton.getText().toString();
        val = zinc.calculateRequiredNutrient(mCrop, status);
        requiredNutrient[4] = val;
        ri[4] = zinc.getRecommendationInterpretation();

        int rid = radioGroupF[4].getCheckedRadioButtonId();
        RadioButton rbt = findViewById(rid);

        String c = "";

        if (rid == R.id.radiofzn1){
            cmp[4] = 36.0; frr[4] = "Zinc Sulphate Mono";
            c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
        }
        else if (rid == R.id.radiofzn2){
            cmp[4] = 23.0; frr[4] = "Zinc Sulphate Hepta";
            c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
        }

        prepareResult(val, c, 4, "Zn");

    }

    public void onClickRB (View view){

        int selectedId = radioGroups[5].getCheckedRadioButtonId();
        if (!isChecked[5])isChecked[5] = true;
        RadioButton radioButton = findViewById(selectedId);

        double val = 0.0;

        editTexts[5].getText().clear();
        Nutrient boron = new Nutrient("Boron", "B");
        nutrients[5] = boron;
        String status = radioButton.getText().toString();
        val = boron.calculateRequiredNutrient(mCrop, status);
        requiredNutrient[5] = val;
        ri[5] = boron.getRecommendationInterpretation();

        int rid = radioGroupF[5].getCheckedRadioButtonId();
        RadioButton rbt = findViewById(rid);

        String c = "";

        if (rid == R.id.radiofb1){
            cmp[5] = 17.0; frr[5] = "Boric Acid";
            c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
        }
        else if (rid == R.id.radiofb2){
            cmp[5] = 20.0; frr[5] = "Solubor";
            c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
        }
        prepareResult(val, c, 5, "B");

    }

    public void onClickFN (View view){
        int rid = radioGroupF[0].getCheckedRadioButtonId();
        String c;
        //if ( !isCalculated[0] && !isChecked[0])expandOne(0);
        expandOne(0);
        if (isCalculated[0] || isChecked[0]){

            double val = requiredNutrient[0];

            try{
                if (val == -1) {
                    throw new NumberFormatException();
                }

                if ( rid == R.id.radiofn1 ){
                    c = "As, Nitrogen composition in Urea 46%";
                    cmp[0] = 46.0;
                    frr[0] = "Urea";
                }

                else {
                    c = "As, Nitrogen composition in Urea 46%";
                    cmp[0] = 46.0; frr[0] = "Guti Urea";
                }
                if (last[0] == 1) {
                    double soilT = Double.parseDouble(editTexts[0].getText().toString());
                    prepareResult(soilT, val, c, 0, "N");
                }
                else {
                    prepareResult(val, c, 0, "N");
                }

            }catch (NumberFormatException e){
                if (!editTexts[0].getText().toString().equals(""))
                    editTexts[0].setError("Invalid Input. Please provide value within the range.");
            }
        }
    }

    public void onClickFP (View view){
        int rid = radioGroupF[1].getCheckedRadioButtonId();
        String c;
        //if ( !isCalculated[1] && !isChecked[1])expandOne(1);
        expandOne(1);
        if (isCalculated[1] || isChecked[1]){

            double val = requiredNutrient[1];

            try{
                if (val == -1) {
                    throw new NumberFormatException();
                }

                if ( rid == R.id.radiofp1 ){
                    c = "As, Phosphorus composition in TSP 20%";
                    cmp[1] = 20.0;
                    frr[1] = "TSP";
                }

                else {
                    c = "As, Phosphorus composition in DAP 20%";
                    cmp[1] = 20.0;
                    frr[1] = "DAP";
                }

                if (last[1] == 1) {
                    double soilT = Double.parseDouble(editTexts[1].getText().toString());
                    prepareResult(soilT, val, c, 1, "P");
                }
                else {
                    prepareResult(val, c, 1, "P");
                }

            }catch (NumberFormatException e){
                if (!editTexts[1].getText().toString().equals(""))
                    editTexts[1].setError("Invalid Input. Please provide value within the range.");
            }
        }
    }

    public void onClickFK (View view){
        int rid = radioGroupF[2].getCheckedRadioButtonId();
        String c;
        //if ( !isCalculated[2] && !isChecked[2])expandOne(2);
        expandOne(2);
        if (isCalculated[2] || isChecked[2]){

            double val = requiredNutrient[2];

            try{
                if (val == -1) {
                    throw new NumberFormatException();
                }

                if ( rid == R.id.radiofk1 ){
                    c = "As, Potassium composition in MoP 50%";
                    cmp[2]= 50.0; frr[2] = "MoP";
                }

                else {
                    c = "As, Potassium composition in SoP 42%";
                    cmp[2] = 42.0; frr[2] = "SoP";
                }
                if (last[1] == 1) {
                    double soilT = Double.parseDouble(editTexts[2].getText().toString());
                    prepareResult(soilT, val, c, 2, "K");
                }
                else {
                    prepareResult(val, c, 2, "K");
                }

            }catch (NumberFormatException e){
                if (!editTexts[2].getText().toString().equals(""))
                    editTexts[2].setError("Invalid Input. Please provide value within the range.");
            }
        }

    }

    public void onClickFS (View view){
        int rid = radioGroupF[3].getCheckedRadioButtonId();
        String c;
        //if ( !isCalculated[3] && !isChecked[3])expandOne(3);
        expandOne(3);
        if (isCalculated[3] || isChecked[3]){

            double val = requiredNutrient[3];

            try{
                if (val == -1) {
                    throw new NumberFormatException();
                }

                if ( rid == R.id.radiofs1 ){
                    cmp[3]= 18.0; frr[3] = "Gypsum";
                    c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
                }

                else {
                    cmp[3] = 23.5; frr[3] = "Ammonium Sulphate";
                    c = "As, Sulphur composition in " + frr[3] + " " + cmp[3] + "%";
                }

                if (last[3] == 1) {
                    double soilT = Double.parseDouble(editTexts[3].getText().toString());
                    prepareResult(soilT, val, c, 3, "S");
                }
                else {
                    prepareResult(val, c, 3, "S");
                }

            }catch (NumberFormatException e){
                if (!editTexts[3].getText().toString().equals(""))
                    editTexts[3].setError("Invalid Input. Please provide value within the range.");
            }
        }

    }

    public void onClickFZn (View view){
        int rid = radioGroupF[4].getCheckedRadioButtonId();
        String c;
        //if ( !isCalculated[4] && !isChecked[4])expandOne(4);
        expandOne(4);
        if ( isCalculated[4] || isChecked[4] ){

            double val = requiredNutrient[4];

            try{
                if (val == -1) {
                    throw new NumberFormatException();
                }

                if ( rid == R.id.radiofzn1 ){
                    cmp[4] = 36.0; frr[4] = "Zinc Sulphate Mono";
                    c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
                }

                else {
                    cmp[4] = 23.0; frr[4] = "Zinc Sulphate Hepta";
                    c = "As, Zinc composition in " + frr[4] + " " + cmp[4] + "%";
                }

                if (last[4] == 1) {
                    double soilT = Double.parseDouble(editTexts[4].getText().toString());
                    prepareResult(soilT, val, c, 4, "Zn");
                }
                else {
                    prepareResult(val, c, 4, "Zn");
                }

            }catch (NumberFormatException e){
                if (!editTexts[4].getText().toString().equals(""))
                    editTexts[4].setError("Invalid Input. Please provide value within the range.");
            }
        }

    }

    public void onClickFB (View view){
        int rid = radioGroupF[5].getCheckedRadioButtonId();
        String c;
        //if ( !isCalculated[5] && !isChecked[5])expandOne(5);
        expandOne(5);
        if (isCalculated[5] || isChecked[5]){

            double val = requiredNutrient[5];

            try{
                if (val == -1) {
                    throw new NumberFormatException();
                }

                if ( rid == R.id.radiofb1 ){
                    cmp[5] = 17.0; frr[5] = "Boric Acid";
                    c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
                }

                else {
                    cmp[5] = 20.0; frr[5] = "Solubor";
                    c = "As, Boron composition in " + frr[5] + " " + cmp[5] + "%";
                }

                if (last[5] == 1) {
                    double soilT = Double.parseDouble(editTexts[5].getText().toString());
                    prepareResult(soilT, val, c, 5, "B");
                }
                else {
                    prepareResult(val, c, 5, "B");
                }

            }catch (NumberFormatException e){
                if (!editTexts[5].getText().toString().equals(""))
                    editTexts[5].setError("Invalid Input. Please provide value within the range.");
            }
        }
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
        if (!isVisible[i]) return;
        isVisible[i] = false;
        DropDownAnim.collapse(inputLayout[i]);
        DropDownAnim.collapse(radioGroups[i]);
        DropDownAnim.collapse(linearLayoutsDC[i]);
    }

    private void expandOne(int i){
        if (isVisible[i])return;
        isVisible[i] = true;
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

    private void prepareResult ( double val, String c, int i, String symbol ){
        fq[i] = 0.0;
        last[i] = 2;
        if (frr[i].equals("Guti Urea"))
            fq[i] = val*(100.0/cmp[i])*.7;
        else fq[i] = val*(100.0/cmp[i]);
        String res = String.format("%.3f (kg/ha)", fq[i]);

        DropDownAnim.expand(txtRes[i]);

        result.put(symbol, String.valueOf(val));
        String rr = String.format("%15s: %s", "Req. " + frr[i], res);
        txtRes[i].setText(rr);

        mResults.put(symbol, new StatusBasedResultProducer(symbol, nutrients[i].getName(), frr[i],
                ri[i].getStatus(), ri[i].getUpperLimit(), cmp[i], fq[i], val));
    }

    private void prepareResult (double soilTextValue, double val, String c, int i, String symbol) {
        last[i] = 1;
        fq[i] = 0.0;
        if (frr[i].equals("Guti Urea"))
            fq[i] = val*(100.0/cmp[i])*.7;
        else fq[i] = val*(100.0/cmp[i]);
        String res = String.format("%.3f (kg/ha)", fq[i]);

        DropDownAnim.expand(txtRes[i]);

        result.put(symbol, String.valueOf(val));
        String rr = String.format("%15s: %s", "Req. " + frr[i], res);
        txtRes[i].setText(rr);

        mResults.put(symbol, new TestBasedResultProducer(symbol, nutrients[i].getName(), frr[i],
                soilTextValue, ti[i].getStatus(), ri[i].getUpperLimit(),
                ri[i].getInterval(), ti[i].getInterval(),
                ti[i].getLowerLimit(), cmp[i], fq[i], val));
    }

    public void freq (View view) {

        double[] rn = new double[6];

        System.arraycopy(requiredNutrient, 0, rn, 0, 6);

        if (frr[4].equals("Zinc Sulphate Hepta")){
            rn[3] -= (fq[4] * 0.11);
        }
        else if (frr[4].equals("Zinc Sulphate Mono")){
            rn[3] -= (fq[4] * 0.18);
        }
        if (frr[2].equals("SoP")){
            rn[3] -= (fq[2] * 0.17);
        }
        if (frr[1].equals("TSP")){
            rn[3] -= (fq[1] * .013);
        }
        if (frr[1].equals("DAP")){
            rn[0] -= (fq[1] * .18);
        }
        if (rn[3] < 0.0)rn[3] = 0.0;

        if (frr[3].equals("Ammonium Sulphate")){
            fq[3] = rn[3] * (100.0 / 23.5);
            rn[0] -= (fq[3] * .211);
        }
        else if (frr[3].equals("Gypsum")){
            fq[3] = rn[3] * (100.0 / 18);
        }
        if (rn[0] < 0.0)rn[0] = 0.0;
        //System.out.println(rn[0]);
        if (frr[0].equals("Urea")){
            fq[0] = rn[0] * (100.0 / 46.0);
        }
        else if (frr[0].equals("Guti Urea")){
            fq[0] = rn[0] * (100.0 / 46.6) * 0.7;
        }

        Intent intent = new Intent(this, RecommendationActivity.class);

        intent.putExtra("fertilizer", frr);
        intent.putExtra("amg", fq);

        startActivity(intent);

    }
}
