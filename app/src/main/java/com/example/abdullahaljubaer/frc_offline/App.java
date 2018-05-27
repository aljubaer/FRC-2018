package com.example.abdullahaljubaer.frc_offline;

import android.app.Application;

import com.binjar.prefsdroid.Preference;

public class App extends Application {

    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Preference.load().using(this).with("frc.pref").prepare();
    }

    public static App getContext() {
        return context;
    }
}
