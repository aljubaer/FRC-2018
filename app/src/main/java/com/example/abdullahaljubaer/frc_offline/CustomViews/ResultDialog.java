package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.TestBasedResultProducer;

/**
 * Created by ABDULLAH AL JUBAER on 29-04-18.
 */

public class ResultDialog {

    private Context context;
    private LinearLayout layout;
    private AlertDialog dialog;
    private TestBasedResultProducer producer;


    private TextView txtInitT;
    private TextView txtFrL;
    private TextView txtFrMT;
    private TextView txtFrMB;
    private TextView txtFrR;
    private TextView txtNRes;
    private TextView txtComb;
    private TextView txtFReq;


    public ResultDialog(Context context, View v, AlertDialog dialog, TestBasedResultProducer producer) {
        this.context = context;
        this.dialog = dialog;
        this.layout = v.findViewById(R.id.final_result);
        this.producer = producer;

        init();
        onCreate();
    }

    private void init () {
        txtInitT.setText(producer.getTopLabel());
        txtFrL.setText(producer.getNutrientCalc());
        txtFrMT.setText(String.valueOf(producer.getCi()));
        txtFrMB.setText(String.valueOf(producer.getCs()));
        txtFrR.setText(producer.getFrRight());
        txtNRes.setText(String.format("%10s%.3f", " ", producer.getNutrientQuantity()));
        txtComb.setText(producer.getComposition());
        txtFReq.setText(producer.getFertilizer());
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
