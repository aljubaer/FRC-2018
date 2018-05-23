package com.example.abdullahaljubaer.frc_offline.GUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.abdullahaljubaer.frc_offline.CustomViews.CustomAlertAdapter;
import com.example.abdullahaljubaer.frc_offline.CustomViews.PatternAlertDialog;
import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 21-05-18.
 */

public class AezCropPatternActivity extends AppCompatActivity {


    private ListView listViewCropPattern;
    private ArrayList<String> cropingPattern = new ArrayList<>();
    private AlertDialog dialog = null;
    private ArrayList<String> aez = new ArrayList<>();
    private Spinner spnAez;
    private String currAez = "1";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aez_crop_pattern);

        listViewCropPattern = findViewById(R.id.aez_cp);

        cropingPattern = MainActivity.patternDBHelper.getAllCropPatterns("1");
        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(AezCropPatternActivity.this, cropingPattern);
        listViewCropPattern.setAdapter(arrayAdapter);

        aez.add("1");
        aez.add("2");
        aez.add("3");
        aez.add("8");
        aez.add("9");

        spnAez = findViewById(R.id.spn_aez_cp);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(
                AezCropPatternActivity.this, android.R.layout.simple_spinner_item, aez);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAez.setAdapter(spnAdapter);

        spnAez.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v;
        v = inflater.inflate(R.layout.pattern_nutrient, null, false);

        listViewCropPattern.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new PatternAlertDialog(AezCropPatternActivity.this, v, dialog);
            }
        });

    }

    private void initList (String c) {
        cropingPattern = MainActivity.patternDBHelper.getAllCropPatterns(c);
        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(AezCropPatternActivity.this, cropingPattern);
        listViewCropPattern.setAdapter(arrayAdapter);
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            /*
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
                    */

            currAez = parent.getItemAtPosition(pos).toString();
            initList(currAez);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }


}
