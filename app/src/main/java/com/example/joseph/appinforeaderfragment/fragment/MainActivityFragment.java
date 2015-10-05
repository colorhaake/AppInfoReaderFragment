package com.example.joseph.appinforeaderfragment.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.joseph.appinforeaderfragment.R;
import com.example.joseph.appinforeaderfragment.adapter.ListViewAdapter;
import com.example.joseph.appinforeaderfragment.data.AppInfo;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    private OnMainActivityFragmentClickListener mListener;

    public MainActivityFragment() {
    }

    boolean mDualPane = false;
    int choice = 0;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // data
        List<AppInfo> appInfoList = getAppInfo();

        // adapter
        setListAdapter(new ListViewAdapter(getActivity(), appInfoList));

        // show choice in fragment B
        if (savedInstanceState != null) {
            choice = savedInstanceState.getInt("choice", 0);
        }

        View vDetail = getActivity().findViewById(R.id.details);
        mDualPane = vDetail != null && vDetail.getVisibility() == View.VISIBLE;

        if (mDualPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            onButtonPressed(choice, appInfoList.get(choice));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("choice", choice);
    }

    public List<AppInfo> getAppInfo() {
        List<AppInfo> appInfoList = new ArrayList<AppInfo>();

        PackageManager pm = getActivity().getPackageManager();

        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
//            if ((packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM) > 0) {
//                continue;
//            }

            AppInfo appInfo = new AppInfo();

            appInfo.icon = pm.getApplicationIcon(packageInfo.applicationInfo);
            appInfo.appName = pm.getApplicationLabel(packageInfo.applicationInfo).toString();
            appInfo.packageName = packageInfo.packageName;
            appInfo.appVersion = "Version: " + packageInfo.versionName;
            appInfo.appSize = "APK size: " + new File(packageInfo.applicationInfo.publicSourceDir).length() + " bytes";

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            appInfo.installedTime = "Installed: " + df.format(new Date(packageInfo.firstInstallTime));

            appInfo.androidVersion = "Android: " + getAndroidVersion(packageInfo.applicationInfo.targetSdkVersion);

            appInfoList.add(appInfo);
        }

        return appInfoList;
    }

    public String getAndroidVersion(int apiLevel) {
        switch (apiLevel) {
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                return "4.0";
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                return "4.0.3";
            case Build.VERSION_CODES.JELLY_BEAN:
                return "4.1";
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                return "4.2";
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                return "4.3";
            case Build.VERSION_CODES.KITKAT:
                return "4.4";
            case Build.VERSION_CODES.LOLLIPOP:
                return "5.0";
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                return "5.1";
            case Build.VERSION_CODES.M:
                return "6.0";
            default:
                return "unknown";

        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Append the clicked item's row ID with the content provider Uri
        AppInfo appInfo = (AppInfo)l.getItemAtPosition(position);
        // Send the event and Uri to the host activity
        //setDetails(position, appInfo);
        onButtonPressed(position, appInfo);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int choice, AppInfo appInfo) {
        if (mListener != null) {
            mListener.onFragmentClick(choice, appInfo);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMainActivityFragmentClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMainActivityFragmentClickListener {
        // TODO: Update argument type and name
        public void onFragmentClick(int choice, AppInfo appInfo);
    }
}
