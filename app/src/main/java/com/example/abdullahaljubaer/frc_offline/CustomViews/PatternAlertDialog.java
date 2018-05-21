package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;

/**
 * Created by ABDULLAH AL JUBAER on 22-05-18.
 */

public class PatternAlertDialog {

    private Context context;
    private LinearLayout layout;
    private AlertDialog dialog;

    public PatternAlertDialog(Context context, View v, AlertDialog dialog) {
        this.context = context;
        this.layout = v.findViewById(R.id.LL_t);
        this.dialog = dialog;

        onCreate();
    }


    private void onCreate() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(layout);


        mBuilder.setNegativeButton("DONE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = mBuilder.show();
    }

}



