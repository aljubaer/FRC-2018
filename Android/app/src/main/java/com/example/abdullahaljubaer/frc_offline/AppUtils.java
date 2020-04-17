package com.example.abdullahaljubaer.frc_offline;

import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

public class AppUtils {
    public static int getPixel(@DimenRes int dimenRes) {
        return App.getContext().getResources().getDimensionPixelSize(dimenRes);
    }

    public String getString(@StringRes int stringRes) {
        return App.getContext().getResources().getString(stringRes);
    }
}
