package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ABDULLAH AL JUBAER on 07-04-18.
 */

public class ResultAlertDialog {

    private Context context;
    private LinearLayout layout;
    private AlertDialog dialog;

    public ResultAlertDialog(Context context, LinearLayout layout, AlertDialog dialog) {
        this.context = context;
        this.layout = layout;
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
