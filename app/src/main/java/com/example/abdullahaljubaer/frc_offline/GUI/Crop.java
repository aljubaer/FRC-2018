package com.example.abdullahaljubaer.frc_offline.GUI;

import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.CropClassDBHelper;
import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.NutrientRecommendationDBHelper;

/**
 * Created by ABDULLAH AL JUBAER on 25-03-18.
 */

public class Crop {

    private String seasonName;
    private String varietyName;
    private String cropType;
    private CropClassDBHelper cropClassDBHelper;
    private NutrientRecommendationDBHelper recommendationDBHelper;

    public Crop (String seasonName, String varietyName, String cropType) {

        this.seasonName = seasonName;
        this.varietyName = varietyName;
        this.cropType = cropType;

    }

    public void setDB (CropClassDBHelper cropClassDBHelper, NutrientRecommendationDBHelper recommendationDBHelper) {
        this.cropClassDBHelper = cropClassDBHelper;
        this.recommendationDBHelper = recommendationDBHelper;
    }

    private synchronized String getCClass () {
        return cropClassDBHelper.getClass(seasonName, varietyName);
    }

    public Interpretation getInterpretation (String nutrientName, String status){
        return recommendationDBHelper.getInterpretation(seasonName, getCClass(), status, nutrientName);
    }


}
