package com.example.abdullahaljubaer.frc_offline.BusinessClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.abdullahaljubaer.frc_offline.GUI.Interpretation;
import com.example.abdullahaljubaer.frc_offline.GUI.MainActivity;

import java.io.Serializable;

/**
 * Created by ABDULLAH AL JUBAER on 25-03-18.
 */

public class Crop implements Serializable, Parcelable {

    private String seasonName;
    private String varietyName;
    private String cropType;

    public Crop (String seasonName, String varietyName, String cropType) {

        this.seasonName = seasonName;
        this.varietyName = varietyName;
        this.cropType = cropType;

    }

    protected Crop(Parcel in) {
        seasonName = in.readString();
        varietyName = in.readString();
        cropType = in.readString();
    }

    public static final Creator<Crop> CREATOR = new Creator<Crop>() {
        @Override
        public Crop createFromParcel(Parcel in) {
            return new Crop(in);
        }

        @Override
        public Crop[] newArray(int size) {
            return new Crop[size];
        }
    };

    private String getCClass () {
        return MainActivity.cropClassDbHelper.getClass(seasonName, varietyName);
    }

    public Interpretation getInterpretation (String nutrientName, String status){
        return MainActivity.nutrientRecommendationDBHelper.getInterpretation(seasonName, getCClass(), status, nutrientName);
    }

    public String getCropType() {
        return cropType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(seasonName);
        parcel.writeString(varietyName);
        parcel.writeString(cropType);
    }

    public static final Parcelable.Creator<Crop> CROP_CREATOR = new Parcelable.Creator<Crop>(){

        @Override
        public Crop createFromParcel(Parcel parcel) {
            return new Crop(parcel);
        }

        @Override
        public Crop[] newArray(int i) {
            return new Crop[i];
        }
    };

    public String getSeasonName() {
        return seasonName;
    }

    public String getVarietyName() {
        return varietyName;
    }
}
