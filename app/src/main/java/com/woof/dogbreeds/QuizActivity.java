package com.woof.dogbreeds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by singerm on 6/22/2016.
 */
public class QuizActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "QuizActivity";
    int correctIndex = 0, falseIndex1 = 0, falseIndex2 = 0, falseIndex3 = 0, scoreBoard = 0;
    Button button1, button2, button3, button4;
    ImageView image1;
    TextView text2, scoreBoardTextView;
    Random rand;
    public static Activity activity;
    ArrayList<Integer> usedIndexes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quiz);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Quiz");
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

        activity = this;

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        //buttonQuit = (Button) findViewById(R.id.buttonQuit);
        scoreBoardTextView = (TextView) findViewById(R.id.scoreBoard);
        scoreBoardTextView.setText("0 / 0");

        image1 = (ImageView) findViewById(R.id.imageViewDogBreed);
        text2 = (TextView) findViewById(R.id.detailtext2);

        rand = new Random();

        //randomBreeds = dogBreedAdapter.origBreeds;

        goQuiz(dogBreedAdapter.origBreeds);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.v(TAG, "currentPage = " + currentPage);
        //mDrawerList.setItemChecked(2, true);
    }

    @Override
    public void onClick(View view) {
        int intID = view.getId();
        final Button button = (Button) findViewById(intID);
        String message = button.getText().toString();
        //Log.v(TAG, "button message: " + message + ", adapter: " + randomBreeds.get(correctIndex).getName());

        if (message.equals("Quit")) {
            QuizActivity.this.finish();
        }

        final AnimationDrawable drawable = new AnimationDrawable();
        final AnimationDrawable drawable1 = new AnimationDrawable();
        final Handler handler = new Handler();
        final Drawable d = button.getBackground();

        Runnable r2;
        if (message.equals(dogBreedAdapter.origBreeds.get(correctIndex).getName())) {
            //Toast.makeText(QuizActivity.this, "CORRECT", Toast.LENGTH_LONG).show();
            scoreBoard++;
            drawable.addFrame(new ColorDrawable(Color.GREEN), 400);

            r2 = new Runnable() {
                @Override
                public void run() {
                    //button.setBackgroundResource(android.R.drawable.btn_default);
                    button.setBackground(d);
                    goQuiz(dogBreedAdapter.origBreeds);
                }
            };

        }
        else {
            drawable.addFrame(new ColorDrawable(Color.RED), 400);
            drawable1.addFrame(new ColorDrawable(Color.GREEN), 400);

            if (button1.getText().toString().equals(dogBreedAdapter.origBreeds.get(correctIndex).getName()))
                button1.setBackground(drawable1);
            else if (button2.getText().toString().equals(dogBreedAdapter.origBreeds.get(correctIndex).getName()))
                button2.setBackground(drawable1);
            else if (button3.getText().toString().equals(dogBreedAdapter.origBreeds.get(correctIndex).getName()))
                button3.setBackground(drawable1);
            else if (button4.getText().toString().equals(dogBreedAdapter.origBreeds.get(correctIndex).getName()))
                button4.setBackground(drawable1);

            r2 = new Runnable() {
                @Override
                public void run() {
                    button1.setBackground(d);
                    button2.setBackground(d);
                    button3.setBackground(d);
                    button4.setBackground(d);
                    //button.setBackgroundResource(android.R.drawable.btn_default);
                    //button.setBackground(buttonQuit.getBackground());
                    goQuiz(dogBreedAdapter.origBreeds);
                }
            };

            //Toast.makeText(QuizActivity.this, "Sorry, try again.", Toast.LENGTH_LONG).show();
        }
        scoreBoardTextView.setText(scoreBoard + "/" + usedIndexes.size());
        drawable.setOneShot(false);
        button.setBackground(drawable);

        final Runnable r1 = new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        };

        handler.post(r1);

        handler.postDelayed(r2, 2000);

    }

    public void goQuiz(ArrayList<DogBreed> allBreeds) {

        do {
            correctIndex = rand.nextInt(allBreeds.size());
            falseIndex1 = rand.nextInt(allBreeds.size());
            falseIndex2 = rand.nextInt(allBreeds.size());
            falseIndex3 = rand.nextInt(allBreeds.size());
            Log.v(TAG, correctIndex + " " + falseIndex1 + " " + falseIndex2 + " " + falseIndex3);
            Log.v(TAG, "areDups: " + areDups(correctIndex, falseIndex1, falseIndex2, falseIndex3));
        } while (areDups(correctIndex, falseIndex1, falseIndex2, falseIndex3) || noImage(allBreeds, correctIndex) || usedIndex(correctIndex));

        usedIndexes.add(correctIndex);
        image1.setImageBitmap(allBreeds.get(correctIndex).getBitmapLarge());
        //text2.setText(allBreeds.get(correctIndex).getName());

        List<Integer> dataList = new ArrayList<Integer>();
        dataList.add(correctIndex);
        dataList.add(falseIndex1);
        dataList.add(falseIndex2);
        dataList.add(falseIndex3);
        Collections.shuffle(dataList);

        button1.setText(allBreeds.get(dataList.get(0)).getName());
        button2.setText(allBreeds.get(dataList.get(1)).getName());
        button3.setText(allBreeds.get(dataList.get(2)).getName());
        button4.setText(allBreeds.get(dataList.get(3)).getName());

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        //buttonQuit.setOnClickListener(this);

    }

    public boolean areDups(int i1, int i2, int i3, int i4) {
        if (i1 == i2 || i1 == i3 || i1 == i4 || i2 == i3 || i2 == i4 || i3 == i4)
            return true;
        else
            return false;
    }

    public boolean noImage(ArrayList<DogBreed> allBreeds, int i1) {
        if (allBreeds.get(i1).getId() == -1)
            return true;
        return false;
    }

    public boolean usedIndex(int i1) {
        if (usedIndexes.contains(i1))
            return true;
        return false;
    }

}
