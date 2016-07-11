package com.woof.dogbreeds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by singerm on 6/22/2016.
 */
public class DogBreedListActivity extends AppCompatActivity {

    private final String TAG = "DogBreedListActivity";
    TextView headerText;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_breed_list);

        Intent intent = getIntent();

        headerText = (TextView) findViewById(R.id.headerText);
        listView = (ListView) findViewById(R.id.listView);

        String listName = intent.getStringExtra("listName");
        headerText.setText(listName);

        DogBreedListAdapter dogBreedListAdapter = new DogBreedListAdapter(this);

        listView.setAdapter(dogBreedListAdapter);

        Log.v("hi", "hi");
    }
}
