package com.woof.dogbreeds;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;

public class DogBreedDetailActivity extends AppCompatActivity {

    private final String TAG = "DogBreedDetailActivity";
    TextView textView;
    TextView textView2;
    ImageView imageView;
    ImageView flagView;
    String breedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_breed_detail);

        Intent intent = getIntent();

        breedName = intent.getStringExtra("breed");
        imageView = (ImageView) findViewById(R.id.imageView);
        flagView = (ImageView) findViewById(R.id.flagView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(breedName);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int resId = intent.getIntExtra("flagResId", -1);
        if (resId != -1) {
            flagView.setImageResource(resId);
        }
        else
            flagView.setImageResource(R.drawable.q);

        Intent mIntent = getIntent();
        Bitmap bitmap = mIntent.getParcelableExtra("breedImage");

        imageView.setImageBitmap(bitmap);

        textView2 = (TextView) findViewById(R.id.detailtext2);
        String textBody = "";
        try {
            textBody = getBreedInfo(breedName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        textView2.setText(textBody);

    }

    private String getBreedInfo(String breedName) throws XmlPullParserException, IOException {
        Log.v(TAG, "ABOUT TO START getBreedInfo!!!");
        try {
            XmlPullParser parser = this.getResources().getXml(R.xml.dog_breeds);
            parser.next();
            Log.v(TAG, "FIRST XML TAG: " + parser.getName());
            parser.next();
            Log.v(TAG, "SECOND XML TAG: " + parser.getName());
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                Log.v(TAG, "in xml loop!");
                String tagName = parser.getName(); // should be breed
                Log.v(TAG, "tagName: " + tagName);
                parser.next(); // name
                String name = readText(parser);
                name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                parser.next(); // description
                Log.v(TAG, "name: " + name);
                if (name.equals(breedName)) {
                    Log.v(TAG, "MATCH!!");
                    String description = readText(parser);
                    Log.v(TAG, "description: " + description);
                    Log.v(TAG, "(match) tagName: " + parser.getName());
                    return description;
                }
                else {
                    Log.v(TAG, breedName + " != " + name);
                    parser.next(); // description
                    parser.next(); // breed
                    parser.next(); // breed
                    Log.v(TAG, "(non-match) tagName: " + parser.getName());
                }
            }
            return "not found";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        Log.v(TAG, "HI");

        //customAdapter = new DogBreedAdapter(this);

        //listView.setAdapter(customAdapter);

        //searchBar(menu);

        return true;
    }

*/
}
