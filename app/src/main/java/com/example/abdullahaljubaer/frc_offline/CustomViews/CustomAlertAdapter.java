package com.example.abdullahaljubaer.frc_offline.CustomViews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abdullahaljubaer.frc_offline.R;

import java.util.ArrayList;

/**
 * Created by ABDULLAH AL JUBAER on 09-02-18.
 */

public class CustomAlertAdapter extends BaseAdapter {

    private Context ctx = null;
    private ArrayList<String> list = null;

    private LayoutInflater mInflater = null;

    public CustomAlertAdapter(Activity activity, ArrayList<String> list) {
        this.ctx = activity;
        mInflater = activity.getLayoutInflater();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        final ViewHolder holder;

        if (convertView == null ) {
            holder = new ViewHolder();
            convertView = (View) mInflater.inflate(R.layout.list_view, null);
            holder.titleName = (TextView) convertView.findViewById(R.id.textView_title_name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        String dataValue = list.get(position);
        holder.titleName.setText(dataValue);
        return convertView;
    }
    private static class ViewHolder {

        TextView titleName;

    }

}
