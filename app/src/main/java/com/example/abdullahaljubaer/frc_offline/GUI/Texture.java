package com.example.abdullahaljubaer.frc_offline.GUI;

import com.example.abdullahaljubaer.frc_offline.DatabaseClasses.TextureClassDBHelper;

/**
 * Created by ABDULLAH AL JUBAER on 28-03-18.
 */

public class Texture {

    private String texture;
    private String landType;
    private String soilType;
    private String textureClass;
    private TextureClassDBHelper textureClassDBHelper;

    public Texture(String texture, String landType) {
        this.texture = texture;
        this.landType = landType;
    }

    private void setTextureClass () {
        this.textureClass = textureClassDBHelper.getClass(texture, landType);
    }

    public void setDB (TextureClassDBHelper textureClassDBHelper) {
        this.textureClassDBHelper = textureClassDBHelper;
    }

    public Interpretation getInterpretation () {
        return null;
    }

}
