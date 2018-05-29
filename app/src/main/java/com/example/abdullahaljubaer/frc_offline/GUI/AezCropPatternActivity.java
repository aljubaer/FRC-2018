package com.example.abdullahaljubaer.frc_offline.GUI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.App;
import com.example.abdullahaljubaer.frc_offline.CustomViews.CustomAlertAdapter;
import com.example.abdullahaljubaer.frc_offline.CustomViews.CustomSearchableDialog;
import com.example.abdullahaljubaer.frc_offline.CustomViews.ResultDialog;
import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.AezBasedResultProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABDULLAH AL JUBAER on 21-05-18.
 */

public class AezCropPatternActivity extends BaseActivity {


    private ListView listViewCropPattern;
    private ArrayList<String> croppingPattern = new ArrayList<>();
    private AlertDialog dialog = null;
    private ArrayList<String> aez = new ArrayList<>();
    private Spinner spnAez, spnUnit;
    private String currAez = "", currCP = "";
    private double landCoEfficient = 1.0, landArea = 1.0;
    private List<String> units = new ArrayList<>();
    private AezBasedResultProducer producer = null;
    private EditText editArea;
    private ArrayList<String> districtList = null;
    private TextView dropDownDistrict;
    private android.app.AlertDialog mAlertDialog = null;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aez_crop_pattern);

        dropDownDistrict = findViewById(R.id.dist);

        districtList = MainActivity.patternDBHelper.getAllDistrict();

        listViewCropPattern = findViewById(R.id.aez_cp);

        croppingPattern = MainActivity.patternDBHelper.getAllCropPatterns("1");
        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(AezCropPatternActivity.this, croppingPattern);
        listViewCropPattern.setAdapter(arrayAdapter);


        aez.add("1");
        aez.add("2");
        aez.add("3");
        aez.add("8");
        aez.add("9");

        //spnAez = findViewById(R.id.spn_aez_cp);

        /*
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(
                AezCropPatternActivity.this, android.R.layout.simple_spinner_item, aez);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAez.setAdapter(spnAdapter);

        spnAez.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        */

        units.add("hector");
        units.add("decimal");

        spnUnit = findViewById(R.id.spn_unit_land1);

        ArrayAdapter<String> spnAdapter1 = new ArrayAdapter<String>(
                AezCropPatternActivity.this, android.R.layout.simple_spinner_item, units);
        spnAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUnit.setAdapter(spnAdapter1);

        spnUnit.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v;


        listViewCropPattern.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currCP = croppingPattern.get(i);
                View v = inflater.inflate(R.layout.pattern_nutrient, null, false);
                producer = new AezBasedResultProducer(
                        MainActivity.patternDBHelper.getPatternRecommendation(currAez, currCP), landCoEfficient * landArea);
                new ResultDialog(AezCropPatternActivity.this, v, dialog, producer);
                //new PatternAlertDialog(AezCropPatternActivity.this, v, dialog);
            }
        });

        editArea = findViewById(R.id.edit_area1);
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
                    landArea = Double.parseDouble( editable.toString() );
                } catch (NumberFormatException e) {
                    editArea.setError("Invalid input");
                }
            }
        });
    }

    private void initList (String c) {
        croppingPattern = MainActivity.patternDBHelper.getAllCropPatterns(c);
        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(AezCropPatternActivity.this, croppingPattern);
        listViewCropPattern.setAdapter(arrayAdapter);
    }

    public void selectDistrict (View view) {

        new CustomSearchableDialog().init(AezCropPatternActivity.this, districtList, mAlertDialog, (TextView) view);

        dropDownDistrict.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    currAez = MainActivity.patternDBHelper.getAezByDistrict(editable.toString());
                    initList(currAez);
                }
            }
        });

    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            /*
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
                    */
            /*
            if (parent.getId() == R.id.spn_aez_cp){
                currAez = parent.getItemAtPosition(pos).toString();
                initList(currAez);
            }
            */

            if (parent.getId() == R.id.spn_unit_land1) {
                if (parent.getItemAtPosition(pos).toString().equals(units.get(0))) {
                    landCoEfficient = 1.0;
                }
                else if (parent.getItemAtPosition(pos).toString().equals(units.get(1))) {
                    landCoEfficient = 0.004046;
                }
                if (producer != null) producer.setAez(landArea * landCoEfficient);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
