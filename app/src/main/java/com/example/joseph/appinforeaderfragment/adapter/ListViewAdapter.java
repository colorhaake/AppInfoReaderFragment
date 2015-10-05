package com.example.joseph.appinforeaderfragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joseph.appinforeaderfragment.R;
import com.example.joseph.appinforeaderfragment.data.AppInfo;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<AppInfo> {

    private static class ViewHolder {
        ImageView icon;
        TextView appName;
        TextView packageName;
        TextView appVersion;
        TextView appSize;
        TextView installedTime;
        TextView androidVersion;
    }

    public ListViewAdapter(Context context, List<AppInfo> objects) {
        super(context, R.layout.listitem, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppInfo appInfo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listitem, parent, false);

            viewHolder.icon = (ImageView)convertView.findViewById(R.id.icon);
            viewHolder.appName = (TextView)convertView.findViewById(R.id.app_name);
            viewHolder.packageName = (TextView)convertView.findViewById(R.id.package_name);
            viewHolder.appVersion = (TextView)convertView.findViewById(R.id.app_version);
            viewHolder.appSize = (TextView)convertView.findViewById(R.id.app_size);
            viewHolder.installedTime = (TextView)convertView.findViewById(R.id.installed_time);
            viewHolder.androidVersion = (TextView)convertView.findViewById(R.id.android_version);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.icon.setImageDrawable(appInfo.icon);
        viewHolder.appName.setText(appInfo.appName);
        viewHolder.packageName.setText(appInfo.packageName);
        viewHolder.appVersion.setText(appInfo.appVersion);
        viewHolder.appSize.setText(appInfo.appSize);
        viewHolder.installedTime.setText(appInfo.installedTime);
        viewHolder.androidVersion.setText(appInfo.androidVersion);

        return convertView;
    }
}
