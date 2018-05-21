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
import android.widget.ListView;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aez_crop_pattern);

        listViewCropPattern = findViewById(R.id.aez_cp);
        cropingPattern.add("Boro rice - T. Aman rice - T. Aus rice");
        cropingPattern.add("Wheat - T. Aman rice - T. Aus rice");
        cropingPattern.add("Jute - T. Aman rice - T. Aus rice");
        cropingPattern.add("Boro rice - Fallow - T. Aus rice");
        cropingPattern.add("Fallow - Fallow - T. Aus rice");


        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(AezCropPatternActivity.this, cropingPattern);

        listViewCropPattern.setAdapter(arrayAdapter);

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


}
