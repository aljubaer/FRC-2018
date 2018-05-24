package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdullahaljubaer.frc_offline.R;
import com.example.abdullahaljubaer.frc_offline.Results.AezBasedResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.ResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.StatusBasedResultProducer;
import com.example.abdullahaljubaer.frc_offline.Results.TestBasedResultProducer;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 29-04-18.
 */

public class ResultDialog {

    private Context context;
    private LinearLayout layout;
    private AlertDialog dialog;
    private TestBasedResultProducer producer;
    private StatusBasedResultProducer producer1;


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

    public ResultDialog(Context context, View v, AlertDialog dialog, StatusBasedResultProducer producer){
        this.context = context;
        this.dialog = dialog;
        this.layout = v.findViewById(R.id.final_result1);
        this.producer1 = producer;

        TextView txtInitT1 = v.findViewById(R.id.st1);
        TextView txtFrL1 = v.findViewById(R.id.frl1);
        TextView txtComb1 = v.findViewById(R.id.comb1);
        TextView txtFReq1 = v.findViewById(R.id.freq1);

        txtInitT1.setText("Interpretation = " + producer1.getInterpretation() + "\n" + "Uf = " + producer1.getUf());
        txtFrL1.setText(producer1.getNutrientCalc());
        txtComb1.setText(producer1.getComposition());
        txtFReq1.setText(producer1.getFertilizer());
        onCreate();

    }

    public ResultDialog(Context context, View v, AlertDialog dialog, AezBasedResultProducer producer) {
        this.context = context;
        this.dialog = dialog;
        this.layout = v.findViewById(R.id.LL_t);
        //this.producer = producer;
        TextView[][] txt;
        txt = new TextView[][] {
                {v.findViewById(R.id.txt_c1), v.findViewById(R.id.txt_c2), v.findViewById(R.id.txt_c3)},
                {v.findViewById(R.id.txt_y1), v.findViewById(R.id.txt_y2), v.findViewById(R.id.txt_y3)},
                {v.findViewById(R.id.txt_n1), v.findViewById(R.id.txt_n2), v.findViewById(R.id.txt_n3)},
                {v.findViewById(R.id.txt_p1), v.findViewById(R.id.txt_p2), v.findViewById(R.id.txt_p3)},
                {v.findViewById(R.id.txt_k1), v.findViewById(R.id.txt_k2), v.findViewById(R.id.txt_k3)},
                {v.findViewById(R.id.txt_s1), v.findViewById(R.id.txt_s2), v.findViewById(R.id.txt_s3)},
                {v.findViewById(R.id.txt_mg1), v.findViewById(R.id.txt_mg2), v.findViewById(R.id.txt_mg3)},
                {v.findViewById(R.id.txt_zn1), v.findViewById(R.id.txt_zn2), v.findViewById(R.id.txt_zn3)},
                {v.findViewById(R.id.txt_b1), v.findViewById(R.id.txt_b2), v.findViewById(R.id.txt_b3)},
                {v.findViewById(R.id.txt_mo1), v.findViewById(R.id.txt_mo2), v.findViewById(R.id.txt_mo3)},
                {v.findViewById(R.id.txt_cd1), v.findViewById(R.id.txt_cd2), v.findViewById(R.id.txt_cd3)},
                {v.findViewById(R.id.txt_pm1), v.findViewById(R.id.txt_pm2), v.findViewById(R.id.txt_pm3)},

        };
        double[] ratio = {-1, 0, 100.0 / 46, 100.0 / 20.0, 100.0 / 50.0, 100.0 / 18.0,
                    100.0 / 9.5, 100.0 / 36.0, 100.0 / 20.0, 100.0 / 54.0, 1.0, 1.0};
        ArrayList<ArrayList<String>> mAez = (ArrayList<ArrayList<String>>) producer.getAez().clone();

        for (int i = 0; i < mAez.size(); i++) {
            for (int j = 0; j < mAez.get(i).size(); j++) {
                double val = 0.0;
                String str = "";
                if (ratio[i] > 0.0){
                    try {
                        val = Double.parseDouble( mAez.get(i).get(j) ) * ratio[i];
                        str = String.valueOf( val );
                    } catch (NumberFormatException ne){
                        Toast.makeText(this.context, "Value error", Toast.LENGTH_SHORT).show();
                    }
                }
                else str = mAez.get(i).get(j);
                txt[i][j].setText(str);
            }
        }
        onCreate();
    }

    private void init () {
        txtInitT.setText(producer.getTopLabel());
        txtFrL.setText(producer.getNutrientCalc() + " - ");
        txtFrMT.setText(String.valueOf(producer.getCi()));
        txtFrMB.setText(String.valueOf(producer.getCs()));
        txtFrR.setText(producer.getFrRight());
        txtNRes.setText(String.format("%10s%.3f", "=", producer.getNutrientQuantity()));
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
