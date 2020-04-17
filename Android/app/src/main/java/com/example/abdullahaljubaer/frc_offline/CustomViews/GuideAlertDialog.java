package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.example.abdullahaljubaer.frc_offline.R;

/**
 * Created by ABDULLAH AL JUBAER on 16-04-18.
 */

public class GuideAlertDialog {

    private Context context;
    private LinearLayout layout;
    private AlertDialog dialog;


    public GuideAlertDialog(Context context, View v, AlertDialog dialog) {
        this.context = context;
        this.dialog = dialog;
        this.layout = v.findViewById(R.id.nutrient_guide);
        onCreate();
    }

    private void onCreate() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(layout);


        mBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = mBuilder.show();
    }

}
