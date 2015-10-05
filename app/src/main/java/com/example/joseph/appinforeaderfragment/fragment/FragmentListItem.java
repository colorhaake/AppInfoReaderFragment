package com.example.joseph.appinforeaderfragment.fragment;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.joseph.appinforeaderfragment.R;
import com.example.joseph.appinforeaderfragment.adapter.MultiLevelListViewAdapter;
import com.example.joseph.appinforeaderfragment.data.AppInfo;
import com.example.joseph.appinforeaderfragment.data.Item;
import com.example.joseph.appinforeaderfragment.data.ItemGroup;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentListItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentListItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListItem extends ListFragment {

    private int choice = -1;
    private AppInfo appInfo;

    //private OnFragmentInteractionListener mListener;

    public static FragmentListItem newInstance(int choice, AppInfo appInfo) {
        FragmentListItem fragment = new FragmentListItem();

        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);
        bundle.putParcelable("Item", appInfo);

        fragment.setArguments(bundle);
        return fragment;
    }

    public FragmentListItem() {
        // Required empty public constructor
    }

    public int getShowIndex() {
        return choice;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            choice = getArguments().getInt("choice");
            appInfo = getArguments().getParcelable("Item");
        }

        // data
        List<Item> data = prepareAppInfo();
        if (data == null) {
            return;
        }

        // adapter
        setListAdapter(new MultiLevelListViewAdapter(getActivity(), R.layout.multi_level_item, data));
    }

    public List<Item> prepareAppInfo() {
        List<Item> data = new ArrayList<>();

        Intent intent = getActivity().getIntent();
        AppInfo appInfo = (intent != null && intent.hasExtra("Item"))? (AppInfo)intent.getExtras().get("Item") : this.appInfo;

        PackageManager pm = getActivity().getPackageManager();

        try {
            PackageInfo packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);

            // App Name
            Item appName = new Item(appInfo.appName);
            ItemGroup appNameGroup = new ItemGroup("App name");
            appNameGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
            appNameGroup.addItem(appName);

            data.add(appNameGroup);

            // package name
            Item packageName = new Item(appInfo.packageName);
            ItemGroup packageNameGroup = new ItemGroup("Package name");
            packageNameGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
            packageNameGroup.addItem(packageName);

            data.add(packageNameGroup);

            // versionName
            Item versionName = new Item(appInfo.appVersion);
            ItemGroup versionNameGroup = new ItemGroup("Version");
            versionNameGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
            versionNameGroup.addItem(versionName);

            data.add(versionNameGroup);

            // firstInstalledTime
            Item firstInstalledTime = new Item(appInfo.installedTime);
            ItemGroup firstInstalledTimeGroup = new ItemGroup("Installed time");
            firstInstalledTimeGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
            firstInstalledTimeGroup.addItem(firstInstalledTime);

            data.add(firstInstalledTimeGroup);

            // lastUpdatedTime
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Item lastUpdatedTime = new Item(df.format(new Date(packageInfo.lastUpdateTime)));
            ItemGroup lastUpdatedTimeGroup = new ItemGroup("Last updated time");
            lastUpdatedTimeGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
            lastUpdatedTimeGroup.addItem(lastUpdatedTime);

            data.add(lastUpdatedTimeGroup);

            // sharedUserId
            if (null != packageInfo.sharedUserId) {
                Item sharedUserId = new Item(packageInfo.sharedUserId);
                ItemGroup sharedUserIdGroup = new ItemGroup("Shared user id");
                sharedUserIdGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                sharedUserIdGroup.addItem(sharedUserId);

                data.add(sharedUserIdGroup);
            }

            // sharedUserLabel
            if (0 < packageInfo.sharedUserLabel) {
                Item sharedUserLabel = new Item(String.valueOf(packageInfo.sharedUserLabel));
                ItemGroup sharedUserLabelGroup = new ItemGroup("Shared user label");
                sharedUserLabelGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                sharedUserLabelGroup.addItem(sharedUserLabel);

                data.add(sharedUserLabelGroup);
            }

            // signature
            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_SIGNATURES);
            if (null != packageInfo.signatures) {
                ItemGroup signatureGroup = new ItemGroup("Signatures");
                signatureGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (Signature signature : packageInfo.signatures) {
                    Item item = new Item(signature.toString());
                    signatureGroup.addItem(item);
                }
                data.add(signatureGroup);
            }

            // activity
            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_ACTIVITIES);
            if (null != packageInfo.activities) {
                ItemGroup activityInfoGroup = new ItemGroup("Activities");
                activityInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (ActivityInfo activityInfo : packageInfo.activities) {
                    Item item = new Item(activityInfo.name);
                    activityInfoGroup.addItem(item);
                }
                data.add(activityInfoGroup);
            }

            // application info
//                if (null != packageInfo.applicationInfo) {
//                    addTextViewAndSeparatoerLine(layout, "AppInfo");
//
//                    addTextViewAndSeparatoerLine(layout, packageInfo.applicationInfo.toString(), true);
//                }

            // configurationInfo
            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_CONFIGURATIONS);
            if (null != packageInfo.configPreferences) {
                ItemGroup configurationInfoGroup = new ItemGroup("Configuration info");
                configurationInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (ConfigurationInfo configurationInfo: packageInfo.configPreferences) {
                    Item item = new Item(configurationInfo.toString());
                    configurationInfoGroup.addItem(item);
                }
                data.add(configurationInfoGroup);
            }

            // instrumentation
            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_INSTRUMENTATION);
            if (null != packageInfo.instrumentation) {
                ItemGroup instrumentationInfoGroup = new ItemGroup("Instrumentation");
                instrumentationInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (InstrumentationInfo instrumentationInfo : packageInfo.instrumentation) {
                    Item item = new Item(instrumentationInfo.name);
                }
                data.add(instrumentationInfoGroup);
            }

            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
            // permissions
            if (null != packageInfo.permissions) {
                ItemGroup permissionInfoGroup = new ItemGroup("Permissions");
                permissionInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (PermissionInfo permissionInfo : packageInfo.permissions) {
                    Item item = new Item(permissionInfo.name);
                    permissionInfoGroup.addItem(item);
                }
                data.add(permissionInfoGroup);
            }

            // provider
            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PROVIDERS);
            if (null != packageInfo.providers) {
                ItemGroup providerInfoGroup = new ItemGroup("Providers");
                providerInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (ProviderInfo providerInfo : packageInfo.providers) {
                    Item item = new Item(providerInfo.name);
                    providerInfoGroup.addItem(item);
                }
                data.add(providerInfoGroup);
            }

            // receiver
            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_RECEIVERS);
            if (null != packageInfo.receivers) {
                ItemGroup receiverGroup = new ItemGroup("Receivers");
                receiverGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (ActivityInfo activityInfo : packageInfo.receivers) {
                    Item item = new Item(activityInfo.name);
                    receiverGroup.addItem(item);
                }
                data.add(receiverGroup);
            }

            // reqFeatures
            if (null != packageInfo.reqFeatures) {
                ItemGroup featureInfoGroup = new ItemGroup("Features info");
                featureInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (FeatureInfo featureInfo : packageInfo.reqFeatures) {
                    Item item = new Item(featureInfo.name);
                    featureInfoGroup.addItem(item);
                }
                data.add(featureInfoGroup);
            }

            // requestedPermission
            if (null != packageInfo.requestedPermissions) {
                ItemGroup requestPermissionsGroup = new ItemGroup("Requested permission");
                requestPermissionsGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (String requestPermissions : packageInfo.requestedPermissions) {
                    Item item = new Item(requestPermissions);
                    requestPermissionsGroup.addItem(item);
                }
                data.add(requestPermissionsGroup);
            }

            // requestedPermissionFlag
            if (null != packageInfo.requestedPermissionsFlags) {
                ItemGroup requestedPermissionFlagGroup = new ItemGroup("Requested permission flag");
                requestedPermissionFlagGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (int requestPermissionFlag : packageInfo.requestedPermissionsFlags) {
                    Item item = new Item(String.valueOf(requestPermissionFlag));
                    requestedPermissionFlagGroup.addItem(item);
                }
                data.add(requestedPermissionFlagGroup);
            }

            packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_SERVICES);
            // services
            if (null != packageInfo.services) {
                ItemGroup serviceInfoGroup = new ItemGroup("Services");
                serviceInfoGroup.icon = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_action_navigation_arrow_right);
                for (ServiceInfo serviceInfo : packageInfo.services) {
                    Item item = new Item(serviceInfo.name);
                    serviceInfoGroup.addItem(item);
                }
                data.add(serviceInfoGroup);
            }

            return data;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item)l.getItemAtPosition(position);
        item.isExpanded = !item.isExpanded;

        ((BaseAdapter)l.getAdapter()).notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
