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

    private Crop mCrop;
    private Texture mTexture;

    private TextView textViewCrop;
    private TextView textViewVariety;
    private TextView textViewTexture;
    private EditText editTextN;
    private EditText editTextP;
    private EditText editTextK;
    private EditText editTextS;
    private EditText editTextZn;
    private EditText editTextB;



    private AlertDialog mAlertDialog = null;
    private ArrayList<String> varietyList = new ArrayList<>();
    private ArrayList<String> textureList = new ArrayList<>();

    private Map <String, String> result = new HashMap<>();



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


        // -----------------Database testing ---------------------


        // ----------------- Initializing Views ------------------

        /*
        textViewCrop = findViewById(R.id.txt_cropname);
        textViewVariety = findViewById(R.id.txt_var);
        textViewTexture = findViewById(R.id.txt_texture);

        editTextN = findViewById(R.id.edtxt_N);
        editTextP = findViewById(R.id.edtxt_P);
        editTextK = findViewById(R.id.edtxt_K);
        editTextS = findViewById(R.id.edtxt_S);
        editTextZn = findViewById(R.id.edtxt_Zn);
        editTextB = findViewById(R.id.edtxt_B);

        */

        //calculate();

        // ----------------- Initializing Views ------------------
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_account){
            Toast.makeText(getApplicationContext(), "Account selected", Toast.LENGTH_LONG).show();
        }
        else if (item.getItemId() == R.id.item_setting){
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_LONG).show();
        }
        else if (item.getItemId() == R.id.item_logout){
            Toast.makeText(getApplicationContext(), "Logout selected", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    */

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

    // --------------------- Action listeners ----------------------
    /*
    public void selectCrop ( View view ){
        ArrayList<String> cropSeason;
        cropSeason = readCursor(cropClassDbHelper.getAllCropSeason(), CropClassDBHelper.COLUMN_SEASON);
        CustomSearchableDialog searchableDialog = new CustomSearchableDialog();
        searchableDialog.init(MainActivity.this, cropSeason, mAlertDialog, textViewCrop);
    }

    public void selectVar ( View view ) {
        final String crop = textViewCrop.getText().toString();
        varietyList = readCursor(cropClassDbHelper.getAllVariety(crop), CropClassDBHelper.COLUMN_VARIETY);
        new CustomSearchableDialog().init(MainActivity.this, varietyList, mAlertDialog, textViewVariety);
        textViewVariety.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String var = textViewVariety.getText().toString();
                String gg = cropGroupDBHelper.getCropGroup(crop);
                mCrop = new Crop(crop, var, gg);
            }
        });
    }

    public void selectTexture ( View view ) {
        textureList = readCursor(textureClassDBHelper.getDistTexture(), TextureClassDBHelper.COLUMN_TEXTURE);
        new CustomSearchableDialog().init(MainActivity.this, textureList, mAlertDialog, textViewTexture);
        textViewTexture.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(textViewTexture.getText().toString());
                mTexture = new Texture(textViewTexture.getText().toString(), mCrop.getCropType());
                mTexture.setDB(textureClassDBHelper, stvidbHelper);
            }
        });
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
                    Toast.makeText(MainActivity.this,  String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e){
                    //Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,  String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e){
                    //Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,  String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e){
                    //Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,  String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e){
                    //Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,  String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e){
                    //Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this,  String.valueOf(val), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                    editTextB.setError("Invalid Input");
                }
            }
        });

    }


    public void createRecommendation (View view) {
        String[] v = {"N", "P", "K", "S", "Zn", "B"};

        Intent intent = new Intent(this, RecommendationActivity.class);

        int len = 0;

        try{
            len = result.size();

            for (int i = 0; i < len; i++){
                intent.putExtra(v[i], result.get(v[i]));
            }

            startActivity(intent);

        } catch (Exception e){
            e.getMessage();
        }

    }
    */

    public void start (View view) {
        Intent intent = new Intent(this, CropInputActivity.class);
        startActivity(intent);

    }


    // --------------------- Action listeners ----------------------

}
