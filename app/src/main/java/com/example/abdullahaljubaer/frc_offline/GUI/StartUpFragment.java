package com.example.abdullahaljubaer.frc_offline.GUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdullahaljubaer.frc_offline.R;

/**
 * Created by ABDULLAH AL JUBAER on 23-05-18.
 */

public class StartUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fullscreen, container, false);
    }

}
