package com.woof.dogbreeds;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";
    ListView listView, mDrawerList;
    public static DogBreedAdapter dogBreedAdapter;
    String[] mSideMenuArray;
    public static SearchView searchView;
    String mLayoutResName = "";
    public static String currentPage = "Home";
    ArrayList<Integer> quizCorrectIndexes = new ArrayList<>();
    ArrayList<Integer> quizInCorrectIndexes = new ArrayList<>();


    protected void onCreateDrawer(final int layoutResID) {
        setContentView(layoutResID);
        mLayoutResName = getResources().getString(layoutResID).toString();
        Log.v(TAG, mLayoutResName);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // activity_main.xml
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // activity_main.xml
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mSideMenuArray = getResources().getStringArray(R.array.side_menu);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mSideMenuArray));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(mDrawerLayout, mDrawerList));

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public DrawerLayout lDrawerLayout;
        public ListView lDrawerList;
        public DrawerItemClickListener(DrawerLayout mDrawerLayout, ListView mDrawerList) {
            lDrawerLayout = mDrawerLayout;
            lDrawerList =  mDrawerList;
        }

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            lDrawerLayout.closeDrawer(Gravity.LEFT);
            if (mSideMenuArray[position].equals("Quiz")) {
                currentPage = "Quiz";
                Log.v(TAG, "Quiz selected");
                searchView.setQuery("", false);
                Intent mIntent = new Intent(BaseActivity.this, QuizActivity.class);
                BaseActivity.this.startActivity(mIntent);
            }
            else if (mSideMenuArray[position].equals("Home")) {
                currentPage = "Home";
                Log.v(TAG, "Home selected");
                searchView.setQuery(" ", true);
                searchView.setQuery("", true);
                if (mLayoutResName.equals("res/layout/quiz.xml")) {
                    QuizActivity.activity.finish();
                }
            }
            else if (mSideMenuArray[position].equals("Top Breeds")) {
                currentPage = "Top Breeds";
                Log.v(TAG, "Top Breeds selected");
                //searchView.setQuery("Top Breeds", true);
                if (mLayoutResName.equals("res/layout/quiz.xml")) {
                    QuizActivity.activity.finish();
                }
                searchView.setQuery("Top Breeds", true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        final Menu menuLocal = menu;
        searchView = (SearchView) menuLocal.findItem(R.id.search).getActionView();

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        //final SearchView
        searchView.setQueryHint("Search breed or country...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        if (mLayoutResName.equals("res/layout/activity_main.xml")) {
            dogBreedAdapter = new DogBreedAdapter(BaseActivity.this, searchView);
            dogBreedAdapter.init();
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(dogBreedAdapter);
        }

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                Log.v(TAG, "text submit: " + arg0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                Log.v(TAG, "text change: " + arg0);
                dogBreedAdapter.getFilter().filter(arg0.toLowerCase());
                return true;
            }

        });

        return true;
    }

}
