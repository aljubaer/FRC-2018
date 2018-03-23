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

import com.example.abdullahaljubaer.frc_offline.GUI.MainActivity;
import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 12-02-18.
 */

public class CustomSearchableDialog {
    
    private Context context;
    private ArrayList<String> values;
    private ArrayList<String> currentValueList;
    private AlertDialog dialog = null;
    private int textLength = 0;
    private String selectedText = "";
    private TextView textView = null;
    public ArrayList<String> a = null;
    public int flag;
    
    public CustomSearchableDialog (){}

    public void init(Context context, ArrayList<String> values, AlertDialog dialog, TextView v) {
        this.dialog = dialog;
        this.context = context;
        this.values = values;
        this.textView = v;
        this.flag = flag;
        createView();
    }


    private void createView(){

        AlertDialog.Builder mDialog = new AlertDialog.Builder(context);
        final EditText editText = new EditText(context);
        final ListView listView = new ListView(context);
        editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0);
        editText.setPadding(10,10,10,10);
        editText.setTextSize(30);
        currentValueList = values;

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editText);
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
                                                MainActivity.INPUT_FLAG = true;
                                            }
                                        }
        );

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                textLength = editText.getText().length();
                currentValueList.clear();
                for (int i = 0; i < values.size(); i++) {
                    if (textLength <= values.get(i).length()) {
                        if(values.get(i).toLowerCase().contains(editText.getText().toString().toLowerCase().trim())) {
                            currentValueList.add(values.get(i));
                        }
                    }
                }
                listView.setAdapter(new CustomAlertAdapter((Activity) context, currentValueList));
            }
        });

        mDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = mDialog.show();

    }

    public String getSelectedText(){
        return selectedText;
    }
    
    
}
