package com.example.elisabeth.theapp;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
   // int score = 0;
    private TextView mTextMessage;
    private TextView scoreTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);

                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mTextMessage = (TextView) findViewById(R.id.message);
        scoreTextMessage = (TextView) findViewById(R.id.scoremessage);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //define our things
        int score = 0;
        ArrayList<List<Object>> playedWords = null ;

        //get extras from previous activity
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            playedWords = (ArrayList<List<Object>>) extra.get("playedWords");
            score =  extra.getInt("score");
        }

        //show the score
        scoreTextMessage.setText("Your score is:");
        mTextMessage.setText(String.valueOf(score));

        //display our list of words with their true(correct) or false(pass) status
        ArrayAdapter adapter = new ArrayAdapter<>(ScoreActivity.this, android.R.layout.simple_list_item_1, playedWords);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }

}
