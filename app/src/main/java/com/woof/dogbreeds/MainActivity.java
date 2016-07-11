package com.woof.dogbreeds;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity";
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_main);
/*
        Intent intent = getIntent();
        String queryValue = intent.getStringExtra("searchViewQuery");

        Log.v(TAG, "queryValue: " + queryValue);

        if (queryValue != null && queryValue.toLowerCase().equals("top breeds")) {
            mDrawerList.setItemChecked(1, true);
            //searchView.setQuery("Top Breeds", true);
        }
        else {
            mDrawerList.setItemChecked(0, true);
        }
*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v(TAG, "currentPage = " + currentPage);
        // prevPage was Quiz
        if (currentPage.equals("Top Breeds")) {
            searchView.setQuery("Top Breeds", true);
            mDrawerList.setItemChecked(1, true);
        }
        // just started app
        else {
            mDrawerList.setItemChecked(0, true);
        }
        /*if (searchView != null) {
            String searchText = searchView.getQuery().toString();
            Log.v(TAG, "searchText: " + searchText);
            if (searchText.toLowerCase().equals("top breeds")) {
                mDrawerList.setItemChecked(1, true);
            } else {
                mDrawerList.setItemChecked(0, true);
            }
        }*/
    }

}
