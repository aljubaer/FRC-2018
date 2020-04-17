package com.example.abdullahaljubaer.frc_offline.DatabaseClasses;

import android.content.Context;

/**
 * Created by ABDULLAH AL JUBAER on 14-05-18.
 */

public class CompositionDBHelper extends DBHelper {

    public static final String TABLE_NAME = "nutrient_composition";
    public static final String COLUMN_FERTILIZER = "fertilizer";
    public static final String COLUMN_NUTRIENT = "nutrient";
    public static final String COLUMN_COMPOSITION = "composition";

    public CompositionDBHelper(Context context) {
        super(context);
    }
}
