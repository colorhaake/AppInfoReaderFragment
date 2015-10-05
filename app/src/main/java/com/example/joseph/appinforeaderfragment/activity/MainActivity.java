package com.example.joseph.appinforeaderfragment.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.joseph.appinforeaderfragment.R;
import com.example.joseph.appinforeaderfragment.data.AppInfo;
import com.example.joseph.appinforeaderfragment.fragment.FragmentListItem;
import com.example.joseph.appinforeaderfragment.fragment.MainActivityFragment;

public class MainActivity extends Activity implements MainActivityFragment.OnMainActivityFragmentClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentClick(int choice, AppInfo appInfo) {
        setDetails(choice, appInfo);
    }

    public void setDetails(int choice, AppInfo appInfo) {
        MainActivityFragment mainActivityFragment = (MainActivityFragment)getFragmentManager().findFragmentById(R.id.main_activity_fragment);
        mainActivityFragment.getListView().setItemChecked(choice, true);

        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);
        bundle.putParcelable("Item", appInfo);

        boolean mDualPane = findViewById(R.id.details) != null;
        if (mDualPane) {
            FragmentListItem fragmentListItem = (FragmentListItem)getFragmentManager().findFragmentById(R.id.details);
            boolean addFragment = fragmentListItem == null;
            if (fragmentListItem != null && fragmentListItem.getShowIndex() == choice) {
                return;
            }

            fragmentListItem = FragmentListItem.newInstance(choice, appInfo);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            if (addFragment) {
                ft.add(R.id.details, fragmentListItem, "details");
            } else {
                ft.replace(R.id.details, fragmentListItem, "details");
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent(this, FragmentListItemActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
