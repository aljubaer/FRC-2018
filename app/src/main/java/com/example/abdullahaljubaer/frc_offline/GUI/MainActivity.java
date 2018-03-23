package com.example.abdullahaljubaer.frc_offline.GUI;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.CustomViews.*;
import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.*;
import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static Boolean INPUT_FLAG = false;

    private CropClassDBHelper cropClassDbHelper = null;
    private STVIDBHelper stvidbHelper = null;
    private TextureClassDBHelper textureClassDBHelper = null;
    private NutrientRecommendationDBHelper nutrientRecommendationDBHelper = null;


    private TextView textViewCrop;
    private TextView textViewVariety;
    private AlertDialog mAlertDialog = null;
    private ArrayList<String> varietyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cropClassDbHelper = new CropClassDBHelper(this);
        //System.out.println( cropClassDbHelper.numberOfRows() );

        //stvidbHelper = new STVIDBHelper(this);
        //System.out.println( stvidbHelper.numberOfRows() );

        //textureClassDBHelper = new TextureClassDBHelper(this);
        //System.out.println( textureClassDBHelper.numberOfRows() );

        //nutrientRecommendationDBHelper = new NutrientRecommendationDBHelper(this);
        //System.out.println( nutrientRecommendationDBHelper.numberOfRows() );

        textViewCrop = findViewById(R.id.txt_crop_name);
        textViewVariety = findViewById(R.id.txt_var);
    }

    public ArrayList<String> readCursor( Cursor cursor, String column ) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex(column));
                arrayList.add(data);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public void selectCrop(View view){
        ArrayList<String> cropSeason;
        cropSeason = readCursor(cropClassDbHelper.getAllCropSeason(), "season");
        CustomSearchableDialog searchableDialog = new CustomSearchableDialog();
        searchableDialog.init(MainActivity.this, cropSeason, mAlertDialog, textViewCrop);
    }

    public void selectVar ( View view) {
        String crop = textViewCrop.getText().toString();
        varietyList = readCursor(cropClassDbHelper.getAllVariety(crop), "variety");
        new CustomSearchableDialog().init(MainActivity.this, varietyList, mAlertDialog, textViewVariety);
    }
}
