package com.example.elisabeth.theapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class gameActivity extends AppCompatActivity {
    ArrayList<String> List;
    ArrayList<String> tempList = new ArrayList<String>();
    int Score = 0;
    ArrayList<String> correctWords = new ArrayList<String>();
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_control);
        mContentView = findViewById(R.id.fullscreen_content);

        tempList = new ArrayList<String>();
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
      //  findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        //Läs in listan!!
          if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                List= null;
            } else {
                List= (ArrayList<String>)extras.get("mylist");
            }
        } else {
            List = (ArrayList<String>) savedInstanceState.getSerializable("mylist");
        }
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        goToScore();
                    }
                },
                10000
        );
          showWord();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    ArrayList<List<Object>> playedWords = new ArrayList();
    String currentWord;

    public void goToScore(){
        Log.d("timer", "game ended");
        Log.d("score", "score: " + this.Score);
        Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
        intent.putExtra("score", this.Score);
        intent.putExtra("playedWords", this.playedWords);
        Log.d("playedwords", this.playedWords.toString());
        startActivity(intent);
    }

    public void goToMain(View view){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }
    public void addPlayedWord(boolean ifCorrect){
        ArrayList<Object> listInner = new ArrayList();
        listInner.add(currentWord);
        listInner.add(ifCorrect);
        playedWords.add(listInner);
        Log.d("playedword", currentWord);
    }

    public void pass(View view)
    {
        Log.d("pass", currentWord);
        boolean ifcorrect = new Boolean("False");
        addPlayedWord(ifcorrect);
        //show the word
        showWord();
        //add to array
    }
    public void correct(View view){
        //show the word
        showWord();
        //update score
        this.Score = this.Score + 1;
        //add to list
        boolean ifcorrect = new Boolean("True");
        addPlayedWord(ifcorrect);
    }
    public void showWord() {
        //find textview
        final TextView theMessage = (TextView)findViewById(R.id.fullscreen_content);
        //make random number
        Random random = new Random();
        int indexAmount =  List.size()-1;
        int i = random.nextInt(indexAmount);
        //get random word
        String word = List.get(i);
        //if we've had it before dont show again
        if(tempList.contains(word)){
            word = findUnusedWord(word, random, indexAmount, List.size(), 0);
        }
        //if new word save it as current and set it in game
            currentWord = word;
            tempList.add(word);
            word = word.replace("-"," ");
            word = word.replace('-',' ');
            theMessage.setText(word);
    };

    public String findUnusedWord(String word, Random random, int indexAmount, int List_length, int max_recursing){
        //the word to replace
        String newWord = word;
        //if weve recursed less than 10 times
        if(max_recursing < 10){
            if(tempList.contains(word)){
                int i = random.nextInt(indexAmount);
                //set new word
                newWord = List.get(i);
                //count how many times we go through since recursive
                findUnusedWord(newWord, random, indexAmount, List_length, max_recursing+1);
            }
        }
                return newWord;
    }


    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
