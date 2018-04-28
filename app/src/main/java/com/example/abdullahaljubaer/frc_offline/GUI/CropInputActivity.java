package com.example.abdullahaljubaer.frc_offline.GUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Crop;
import com.example.abdullahaljubaer.frc_offline.BusinessClasses.Texture;
import com.example.abdullahaljubaer.frc_offline.CustomViews.CustomSearchableDialog;
import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.CropClassDBHelper;
import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.TextureClassDBHelper;
import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;


/**
 * Created by ABDULLAH AL JUBAER on 04-04-18.
 */

public class CropInputActivity extends AppCompatActivity {


    public static Crop mCrop = null;
    public static Texture mTexture = null;

    private TextView textViewCrop;
    private TextView textViewVariety;
    private TextView textViewTexture;

    private TextView textErrorCrop;
    private TextView textErrorVar;
    private TextView textErrorTexture;

    private AlertDialog mAlertDialog = null;
    private ArrayList<String> varietyList = new ArrayList<>();
    private ArrayList<String> textureList = new ArrayList<>();

    private String errC = "* Must select a crop!!!";
    private String errV = "* Must select a variety!!!";
    private String errT = "* Must select a texture!!!";

    private Boolean isChecked = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frc_crop_input);

        textViewCrop = findViewById(R.id.txt_cropname);
        textViewVariety = findViewById(R.id.txt_var);
        textViewTexture = findViewById(R.id.txt_texture);

        textErrorCrop = findViewById(R.id.error_crop);
        textErrorVar = findViewById(R.id.error_var);
        textErrorTexture = findViewById(R.id.error_texture);

    }

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


    public ArrayList<String> readCursor(Cursor cursor, String column ) {
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


    public void selectCrop ( View view ){
        ArrayList<String> cropSeason;
        cropSeason = readCursor( MainActivity.cropClassDbHelper.getAllCropSeason(), CropClassDBHelper.COLUMN_SEASON);
        CustomSearchableDialog searchableDialog = new CustomSearchableDialog();
        searchableDialog.init(this, cropSeason, mAlertDialog, textViewCrop);

        textViewCrop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isChecked = true;
                textErrorCrop.setText("");
            }
        });
    }

    public void selectVar ( View view ) {
        final String crop = textViewCrop.getText().toString();
        varietyList = readCursor(MainActivity.cropClassDbHelper.getAllVariety(crop), CropClassDBHelper.COLUMN_VARIETY);
        if (varietyList.size() == 0) textErrorCrop.setText(errC);
        else new CustomSearchableDialog().init(this, varietyList, mAlertDialog, textViewVariety);
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
                String gg = MainActivity.cropGroupDBHelper.getCropGroup(crop);
                mCrop = new Crop(crop, var, gg);
                //mCrop.setDB(MainActivity.cropClassDbHelper, MainActivity.nutrientRecommendationDBHelper);
                textErrorVar.setText("");
            }
        });
    }

    public void selectTexture ( View view ) {
        textureList = readCursor(MainActivity.textureClassDBHelper.getDistTexture(), TextureClassDBHelper.COLUMN_TEXTURE);
        if (!isChecked) textErrorCrop.setText(errC);
        if (mCrop == null) textErrorVar.setText(errV);
        else    new CustomSearchableDialog().init(this, textureList, mAlertDialog, textViewTexture);
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
                mTexture.setDB(MainActivity.textureClassDBHelper, MainActivity.stvidbHelper);
                textErrorTexture.setText("");
            }
        });
    }


    public void proceed (View view) {
        if (!isChecked) textErrorCrop.setText(errC);
        if (mCrop == null) textErrorVar.setText(errV);
        if (mTexture == null) textErrorTexture.setText(errT);
        else {
            Intent intent = new Intent(this, NutrientInputActivityCopy.class);

            //intent.putExtra("crop", (Serializable) mCrop);
            //intent.putExtra("texture", (Serializable) mTexture);



            startActivity(intent);
        }
    }
}
