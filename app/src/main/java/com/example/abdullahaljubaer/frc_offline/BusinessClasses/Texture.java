package com.example.abdullahaljubaer.frc_offline.BusinessClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.STVIDBHelper;
import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.TextureClassDBHelper;

import java.io.Serializable;

/**
 * Created by ABDULLAH AL JUBAER on 28-03-18.
 */

public class Texture implements Serializable, Parcelable {

    private String texture;
    private String landType;
    private String soilType;
    private String textureClass;
    private TextureClassDBHelper textureClassDBHelper;
    private STVIDBHelper stvidbHelper;

    public Texture(String texture, String landType) {
        this.texture = texture;
        this.landType = landType;

    }

    protected Texture(Parcel in) {
        texture = in.readString();
        landType = in.readString();
        soilType = in.readString();
        textureClass = in.readString();
    }

    public static final Creator<Texture> CREATOR = new Creator<Texture>() {
        @Override
        public Texture createFromParcel(Parcel in) {
            return new Texture(in);
        }

        @Override
        public Texture[] newArray(int size) {
            return new Texture[size];
        }
    };

    private void setTextureClass () {
        this.textureClass = textureClassDBHelper.getClass(texture, landType);
    }

    public void setDB (TextureClassDBHelper textureClassDBHelper, STVIDBHelper stvidbHelper) {
        this.textureClassDBHelper = textureClassDBHelper;
        this.stvidbHelper = stvidbHelper;
        setTextureClass();
    }

    public Interpretation getInterpretation (String nutrient, double val) {
        return stvidbHelper.getInterpretation(textureClass, nutrient, val);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(texture);
        parcel.writeString(landType);
        parcel.writeString(soilType);
        parcel.writeString(textureClass);
    }

    public static final Parcelable.Creator<Texture> TEXTURE_CREATOR = new Parcelable.Creator<Texture>(){

        @Override
        public Texture createFromParcel(Parcel parcel) {
            return new Texture(parcel);
        }

        @Override
        public Texture[] newArray(int i) {
            return new Texture[i];
        }


    };


    public String getTexture() {
        return texture;
    }

    public String getLandType() {
        return landType;
    }
}
