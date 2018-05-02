package com.example.abdullahaljubaer.frc_offline.GUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Crop;
import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Texture;
import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.*;
import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static CropClassDBHelper cropClassDbHelper = null;
    public static STVIDBHelper stvidbHelper = null;
    public static TextureClassDBHelper textureClassDBHelper = null;
    public static NutrientRecommendationDBHelper nutrientRecommendationDBHelper = null;
    public static CropGroupDBHelper cropGroupDBHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        // -----------------Database testing ---------------------

        DBHelper dbHelper = new DBHelper(this);

        cropGroupDBHelper = new CropGroupDBHelper(this);
        System.out.println( cropGroupDBHelper.numberOfRows() );

        nutrientRecommendationDBHelper = new NutrientRecommendationDBHelper(this);
        System.out.println( nutrientRecommendationDBHelper.numberOfRows() );

        textureClassDBHelper = new TextureClassDBHelper(this);
        System.out.println( textureClassDBHelper.numberOfRows() );

        stvidbHelper = new STVIDBHelper(this);
        System.out.println( stvidbHelper.numberOfRows() );

        cropClassDbHelper = new CropClassDBHelper(this);
        System.out.println( cropClassDbHelper.numberOfRows() );

    }

    public void start (View view) {
        Intent intent = new Intent(this, CropInputActivity.class);
        startActivity(intent);

    }
}
