package com.example.abdullahaljubaer.frc_offline.GUI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.*;
import com.example.abdullahaljubaer.frc_offline.R;


public class MainActivity extends AppCompatActivity {

    public static CropClassDBHelper cropClassDbHelper = null;
    public static STVIDBHelper stvidbHelper = null;
    public static TextureClassDBHelper textureClassDBHelper = null;
    public static NutrientRecommendationDBHelper nutrientRecommendationDBHelper = null;
    public static CropGroupDBHelper cropGroupDBHelper = null;
    public static AezCropPatternDBHelper patternDBHelper = null;
    public static Database database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

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

        patternDBHelper = new AezCropPatternDBHelper(this);
        System.out.println( patternDBHelper.numberOfRows());

        database = new Database(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StartUpFragment startUpFragment = new StartUpFragment();
        fragmentTransaction.add( R.id.fragment_container, startUpFragment);
        fragmentTransaction.commit();

    }

    public void start (View view) {
        //Intent intent = new Intent(this, CropInputActivity.class);
        //startActivity(intent);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OptionSelectFragment optionSelectFragment = new OptionSelectFragment(this);
        fragmentTransaction.replace( R.id.fragment_container, optionSelectFragment);
        fragmentTransaction.commit();

    }
}
