package com.example.elisabeth.theapp;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

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
        int score;
        Intent mIntent = getIntent();
        score = mIntent.getIntExtra("score", 0);
        mTextMessage.setText(String.valueOf(score));
        scoreTextMessage.setText("Your score is:");
       /* if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                score= 0;
            } else {
                score = (int)extras.get("score");
            }
        } else {
            score = (int) savedInstanceState.getSerializable("score");
            }*/
    }

}
