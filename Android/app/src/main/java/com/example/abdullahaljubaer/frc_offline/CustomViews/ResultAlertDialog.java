package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;

/**
 * Created by ABDULLAH AL JUBAER on 07-04-18.
 */

public class ResultAlertDialog {

    private Context context;
    private LinearLayout layout;
    private AlertDialog dialog;
    private ResultProducer producer;


    private TextView txtInitT;
    private TextView txtFrL;
    private TextView txtFrMT;
    private TextView txtFrMB;
    private TextView txtFrR;
    private TextView txtNRes;
    private TextView txtComb;
    private TextView txtFReq;


    public ResultAlertDialog(Context context, View v, AlertDialog dialog, ResultProducer producer) {
        this.context = context;
        //this.layout = layout;
        this.dialog = dialog;
        this.layout = v.findViewById(R.id.final_result);
        this.producer = producer;


        txtInitT = v.findViewById(R.id.st);
        txtFrL = v.findViewById(R.id.frl);
        txtFrMT = v.findViewById(R.id.frmt);
        txtFrMB = v.findViewById(R.id.frmb);
        txtFrR = v.findViewById(R.id.frr);
        txtNRes = v.findViewById(R.id.frnf);
        txtComb = v.findViewById(R.id.comb);
        txtFReq = v.findViewById(R.id.freq);

        init();


        onCreate();
    }

    private void init() {

        txtInitT.setText(producer.getStrSt() + "\n" + producer.getStrInter() + "\n"
                + producer.getStrUf() + "\n" + producer.getStrCs() + "\n"
                + producer.getStrCi());

        txtFrL.setText(producer.getStrFrLeft());
        txtFrMT.setText(producer.getStrFrMiddleT());
        txtFrMB.setText(producer.getStrFrMiddleB());
        txtFrR.setText(producer.getStrFrRight());
        txtNRes.setText(producer.getNRes());
        txtComb.setText(producer.getComp());
        txtFReq.setText(producer.getFrFinal());

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
