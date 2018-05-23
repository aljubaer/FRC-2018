package com.example.abdullahaljubaer.frc_offline.GUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.abdullahaljubaer.frc_offline.R;

/**
 * Created by ABDULLAH AL JUBAER on 23-05-18.
 */

public class OptionSelectFragment extends Fragment {

    private View view = null;
    private Button[] options;
    private Context context;

    @SuppressLint("ValidFragment")
    public OptionSelectFragment(){}

    @SuppressLint("ValidFragment")
    public OptionSelectFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_options, container, false);
        options = new Button[]{
                view.findViewById(R.id.btn_text),
                view.findViewById(R.id.btn_aez),
                view.findViewById(R.id.btn_balance)
        };
        options[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CropInputActivity.class);
                startActivity(intent);
            }
        });
        options[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AezCropPatternActivity.class);
                startActivity(intent);
            }
        });
        options[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BalanceActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
