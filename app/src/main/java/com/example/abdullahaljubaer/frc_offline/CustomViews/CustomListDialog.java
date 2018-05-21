package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 08-05-18.
 */

public class CustomListDialog {

    private Context context;
    private ArrayList<String> values;
    private ArrayList<String> currentValueList;
    private AlertDialog dialog = null;
    private int textLength = 0;
    private String selectedText = "";
    private TextView textView = null;
    public ArrayList<String> a = null;

    public CustomListDialog(){}

    public void init(Context context, ArrayList<String> values, AlertDialog dialog, TextView v) {
        this.dialog = dialog;
        this.context = context;
        this.values = values;
        this.textView = v;
        createView();
    }


    private void createView(){

        AlertDialog.Builder mDialog = new AlertDialog.Builder(context);
        final ListView listView = new ListView(context);
        currentValueList = new ArrayList<>();
        currentValueList = (ArrayList<String>) values.clone();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter((Activity) context, currentValueList);
        listView.setAdapter(arrayAdapter);
        layout.addView(listView);
        mDialog.setView(layout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick (AdapterView < ? > adapter, View view, int position, long arg){
                                                dialog.dismiss();
                                                if (currentValueList.size() > 0 && position >= 0 && position < currentValueList.size())
                                                    selectedText = currentValueList.get(position);
                                                else selectedText = values.get(position);
                                                textView.setText(selectedText);
                                            }
                                        }
        );

        mDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        
        dialog = mDialog.show();

    }


}
